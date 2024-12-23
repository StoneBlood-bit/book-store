package mate.academy.mapper;

import java.util.Set;
import mate.academy.config.MapperConfig;
import mate.academy.dto.book.BookDto;
import mate.academy.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.dto.book.CreateBookRequestDto;
import mate.academy.model.Book;
import mate.academy.model.Category;
import mate.academy.service.category.CategoryService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @AfterMapping
    default void setCategoryIds(@MappingTarget Book book,
                                CreateBookRequestDto bookDto,
                                @Context CategoryService categoryService
    ) {
        if (bookDto.getCategoryIds() != null) {
            Set<Category> categories = categoryService.findByIds(bookDto.getCategoryIds());
            book.setCategories(categories);
        }
    }

    BookDto toDto(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);

    void updateBookFromDto(CreateBookRequestDto requestDto, @MappingTarget Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);
}
