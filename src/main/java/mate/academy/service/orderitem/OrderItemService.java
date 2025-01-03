package mate.academy.service.orderitem;

import java.util.List;
import mate.academy.dto.orderitem.OrderItemDto;

public interface OrderItemService {
    List<OrderItemDto> getOrderItemsByOrderId(Long orderId);

    OrderItemDto getOrderItemById(Long orderId, Long itemId);
}
