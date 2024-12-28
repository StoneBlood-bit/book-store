package mate.academy.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mate.academy.annotation.FieldMatch;
import mate.academy.model.ShoppingCart;

@Data
@FieldMatch(
        first = "password",
        second = "repeatPassword",
        message = "Passwords do not match"
)
public class UserRegistrationRequestDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String repeatPassword;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String shippingAddress;

    @NotNull
    private ShoppingCart shoppingCart;
}
