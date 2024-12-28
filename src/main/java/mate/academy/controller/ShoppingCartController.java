package mate.academy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.cartitem.CartItemDto;
import mate.academy.dto.shoppingcart.ShoppingCartDto;
import mate.academy.mapper.ShoppingCartMapper;
import mate.academy.model.ShoppingCart;
import mate.academy.model.User;
import mate.academy.service.shoppingcart.ShoppingCartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoint for managing shopping carts")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartMapper shoppingCartMapper;

    @Operation(summary = "Get a shopping cart", description = "Find a shopping cart for a user")
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ShoppingCartDto getCart(@AuthenticationPrincipal User user) {
        ShoppingCart cart = shoppingCartService.getByUserId(user.getId());
        return shoppingCartMapper.toDto(cart);
    }

    @Operation(summary = "Add a book to cart item",
            description = "Add a book to cart item for a user")
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public void addBookToCart(
            @AuthenticationPrincipal User user,
            @RequestBody CartItemDto cartItemDto
    ) {
        shoppingCartService.addBook(user.getId(), cartItemDto.getBookId(),
                cartItemDto.getQuantity());
    }

    @Operation(summary = "Update cart item", description = "Update cart item for a user")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/items/{id}")
    public void updateCartItem(@PathVariable Long id,
                               @RequestBody CartItemDto cartItemDto) {
        shoppingCartService.updateCartItem(id, cartItemDto.getQuantity());
    }

    @Operation(summary = "Remove cart item", description = "Remove cart item for a user")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/items/{id}")
    public void removeCartItem(@PathVariable Long id) {
        shoppingCartService.removeCartItem(id);
    }
}
