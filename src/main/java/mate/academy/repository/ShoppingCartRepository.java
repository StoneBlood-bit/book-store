package mate.academy.repository;

import java.util.Optional;
import mate.academy.model.ShoppingCart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(value = "ShoppingCart.itemsAndBooks", type = EntityGraph.EntityGraphType.LOAD)
    Optional<ShoppingCart> findByUserId(Long userId);
}
