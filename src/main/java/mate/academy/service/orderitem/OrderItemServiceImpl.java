package mate.academy.service.orderitem;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.orderitem.OrderItemDto;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.mapper.OrderItemMapper;
import mate.academy.model.OrderItem;
import mate.academy.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemDto> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findById(orderId).stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItemById(Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository
                .findByOrderIdAndId(orderId, itemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order item not found for orderId: %d and itemId: %d",
                                orderId, itemId)));
        return orderItemMapper.toDto(orderItem);
    }
}
