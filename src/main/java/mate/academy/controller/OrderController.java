package mate.academy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.order.OrderDto;
import mate.academy.dto.order.OrderRequestDto;
import mate.academy.dto.order.UpdateOrderStatusDto;
import mate.academy.model.User;
import mate.academy.service.order.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoint for managing orders")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Create order", description = "Create a new order")
    @PostMapping
    public OrderDto createOrder(@RequestBody @Valid OrderRequestDto requestDto,
                                @AuthenticationPrincipal User user) {
        return orderService.createOrder(requestDto, user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get user's orders", description = "Get a history of orders for user")
    @GetMapping
    public List<OrderDto> getUserOrderHistory(@AuthenticationPrincipal User user) {
        return orderService.getUserOrderHistory(user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get order", description = "Get order with a passed id")
    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order's status",
            description = "Update status for order with a passed id")
    @PatchMapping("/{id}")
    public OrderDto updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody UpdateOrderStatusDto updateOrderStatusDto
    ) {
        return orderService.updateOrderStatus(orderId, updateOrderStatusDto.getStatus());
    }
}
