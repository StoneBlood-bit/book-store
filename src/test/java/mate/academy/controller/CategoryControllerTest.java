package mate.academy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import mate.academy.dto.PageResponse;
import mate.academy.dto.category.CategoryDto;
import mate.academy.security.JwtUtil;
import mate.academy.service.category.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {

    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtUtil jwtUtil;

    @Mock
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        when(jwtUtil.isValidToken(anyString())).thenReturn(true);
    }

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(
            scripts = "classpath:database/controller/category/delete-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("Create a new category")
    @Test
    void createCategory_ValidDto_Success() throws Exception {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Detective");

        CategoryDto expected = new CategoryDto();
        expected.setName(categoryDto.getName());

        String jsonRequest = objectMapper.writeValueAsString(categoryDto);

        MvcResult result = mockMvc.perform(post("/categories")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        CategoryDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), CategoryDto.class);

        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Sql(
            scripts = "classpath:database/controller/category/add-three-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = "classpath:database/controller/category/delete-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("Get all categories")
    @Test
    void getAll_GivenCategoriesInCatalog_Success() throws Exception {
        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setId(1L);
        categoryDto1.setName("Detective");

        CategoryDto categoryDto2 = new CategoryDto();
        categoryDto2.setId(2L);
        categoryDto2.setName("Thriller");

        CategoryDto categoryDto3 = new CategoryDto();
        categoryDto3.setId(3L);
        categoryDto3.setName("Horror");

        List<CategoryDto> expected = new ArrayList<>();
        expected.add(categoryDto1);
        expected.add(categoryDto2);
        expected.add(categoryDto3);

        MvcResult result = mockMvc.perform(get("/categories")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        PageResponse<CategoryDto> actualPage = objectMapper
                .readValue(jsonResponse, new TypeReference<>() {});

        List<CategoryDto> actualList = actualPage.getContent();

        assertEquals(3, actualList.size());
        assertEquals(expected, actualList);
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Sql(
            scripts = "classpath:database/controller/category/add-one-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = "classpath:database/controller/category/delete-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("Get category by id")
    @Test
    void getCategoryById_ValidId_Success() throws Exception {
        Long validId = 1L;

        CategoryDto expected = new CategoryDto();
        expected.setId(validId);
        expected.setName("Detective");

        when(categoryService.getById(validId)).thenReturn(expected);

        MvcResult result = mockMvc.perform(get("/categories/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        CategoryDto actual = objectMapper.readValue(jsonResponse, CategoryDto.class);

        assertEquals(expected, actual);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(
            scripts = "classpath:database/controller/category/add-one-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = "classpath:database/controller/category/delete-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("Update category")
    @Test
    void updateCategory_GivenValidIdAndDto_Success() throws Exception {
        Long categoryId = 1L;

        CategoryDto updated = new CategoryDto();
        updated.setId(categoryId);
        updated.setName("Detective");

        when(categoryService.update(eq(categoryId), any(CategoryDto.class)))
                .thenReturn(updated);

        MvcResult result = mockMvc.perform(put("/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        CategoryDto actualUpdated = objectMapper.readValue(jsonResponse, CategoryDto.class);

        assertEquals(updated.getId(), actualUpdated.getId());
        assertEquals(updated.getName(), actualUpdated.getName());
    }
}
