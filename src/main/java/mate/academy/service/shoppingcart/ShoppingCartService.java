package mate.academy.service.shoppingcart;

import mate.academy.dto.cartitem.CartItemRequestDto;
import mate.academy.dto.shoppingcart.ShoppingCartDto;
import mate.academy.model.ShoppingCart;
import mate.academy.model.User;

public interface ShoppingCartService {
    ShoppingCartDto getByUserId(Long userId);

    ShoppingCartDto addBook(Long userId, CartItemRequestDto requestDto);

    ShoppingCartDto updateCartItem(Long cartItemId, CartItemRequestDto requestDto, Long userId);

    ShoppingCart createShoppingCart(User user);

    void removeCartItem(Long cartItemId);
}
