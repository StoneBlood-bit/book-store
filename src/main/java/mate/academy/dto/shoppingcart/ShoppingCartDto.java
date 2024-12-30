package mate.academy.dto.shoppingcart;

import java.util.Set;
import lombok.Data;
import mate.academy.model.CartItem;
import mate.academy.model.User;

@Data
public class ShoppingCartDto {
    private Long id;
    private User user;
    private Set<CartItem> cartItems;
}
