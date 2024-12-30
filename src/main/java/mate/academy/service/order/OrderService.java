package mate.academy.service.order;

import java.util.List;
import mate.academy.dto.order.OrderDto;
import mate.academy.dto.order.OrderRequestDto;

public interface OrderService {
    OrderDto createOrder(OrderRequestDto requestDto);

    List<OrderDto> getUserOrderHistory(Long userId);

    OrderDto getOrderById(Long id);

    OrderDto updateOrderStatus(Long id, String status);
}
