package mate.academy.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDto(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        @NotBlank
        String repeatPassword,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        String shippingAddress
) {
}
