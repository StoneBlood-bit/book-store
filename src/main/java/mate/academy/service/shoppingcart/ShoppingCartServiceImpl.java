package mate.academy.service.shoppingcart;

import lombok.RequiredArgsConstructor;
import mate.academy.dto.cartitem.CartItemRequestDto;
import mate.academy.dto.shoppingcart.ShoppingCartDto;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.mapper.ShoppingCartMapper;
import mate.academy.model.Book;
import mate.academy.model.CartItem;
import mate.academy.model.ShoppingCart;
import mate.academy.model.User;
import mate.academy.repository.BookRepository;
import mate.academy.repository.CartItemRepository;
import mate.academy.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public ShoppingCartDto getByUserId(Long userId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Cart not found for user id: " + userId)
        );
        return shoppingCartMapper.toDto(cart);
    }

    @Override
    @Transactional
    public ShoppingCartDto addBook(Long userId, CartItemRequestDto requestDto) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Cart not found for user id: " + userId)
        );
        Book book = bookRepository.findById(requestDto.getBookId()).orElseThrow(
                () -> new EntityNotFoundException("Book not found: " + requestDto.getBookId())
        );

        CartItem existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getBook().equals(book))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            existingCartItem
                    .setQuantity(existingCartItem.getQuantity() + requestDto.getQuantity());
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setShoppingCart(cart);
            cartItem.setBook(book);
            cartItem.setQuantity(requestDto.getQuantity());
            cartItemRepository.save(cartItem);
        }
        return shoppingCartMapper.toDto(cart);
    }

    @Override
    @Transactional
    public ShoppingCartDto updateCartItem(Long cartItemId,
                                          CartItemRequestDto requestDto,
                                          Long userId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Shopping cart not found for user id: " + userId)
        );
        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, cart.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Cart item not found for id: "
                            + cartItemId + " and shopping cart id: " + cart.getId())
        );
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(cart);
    }

    @Override
    @Transactional
    public ShoppingCart createShoppingCart(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        return shoppingCartRepository.save(cart);
    }

    @Override
    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
