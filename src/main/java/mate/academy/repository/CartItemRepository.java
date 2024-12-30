package mate.academy.repository;

import java.util.Optional;
import mate.academy.model.Book;
import mate.academy.model.CartItem;
import mate.academy.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByShoppingCartAndBook(ShoppingCart shoppingCart, Book book);

    Optional<CartItem> findByIdAndShoppingCartId(Long id, Long shoppingCartId);
}
