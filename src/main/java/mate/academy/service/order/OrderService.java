package mate.academy.service.order;

import java.util.List;
import mate.academy.dto.order.OrderDto;
import mate.academy.dto.order.OrderRequestDto;
import mate.academy.model.Order;

public interface OrderService {
    OrderDto createOrder(OrderRequestDto requestDto, Long userId);

    List<OrderDto> getUserOrderHistory(Long userId);

    OrderDto getOrderById(Long id);

    OrderDto updateOrderStatus(Long id, Order.Status status);
}
