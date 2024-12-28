package mate.academy.service.shoppingcart;

import mate.academy.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart getByUserId(Long userId);

    void addBook(Long userId, Long bookId, int quantity);

    void updateCartItem(Long cartItemId, int quantity);

    void removeCartItem(Long cartItemId);
}
