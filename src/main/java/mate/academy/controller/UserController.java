package mate.academy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mate.academy.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User management", description = "Endpoint for managing users")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
}
