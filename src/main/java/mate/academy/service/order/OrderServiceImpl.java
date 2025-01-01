package mate.academy.service.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.order.OrderDto;
import mate.academy.dto.order.OrderRequestDto;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.mapper.OrderItemMapper;
import mate.academy.mapper.OrderMapper;
import mate.academy.model.Order;
import mate.academy.model.OrderItem;
import mate.academy.model.ShoppingCart;
import mate.academy.repository.OrderRepository;
import mate.academy.repository.ShoppingCartRepository;
import mate.academy.service.orderitem.OrderItemService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemService orderItemService;

    @Override
    public OrderDto createOrder(OrderRequestDto requestDto, Long userId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Can not find user with id: " + userId)
        );
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus(Order.Status.PENDING);
        order.setShippingAddress(requestDto.getShippingAddress());

        Set<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setBook(cartItem.getBook());
                    orderItem.setQuantity(cartItem.getQuantity());
                    BigDecimal price = cartItem.getBook()
                            .getPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                    orderItem.setPrice(price);
                    return orderItem;
                }).collect(Collectors.toSet());
        BigDecimal total = orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotal(total);
        order.setOrderItems(orderItems);

        orderRepository.save(order);
        cart.getCartItems().clear();
        shoppingCartRepository.save(cart);

        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getUserOrderHistory(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can not find order by id: " + id)
        );
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto updateOrderStatus(Long id, Order.Status status) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can not find order by id: " + id)
        );
        order.setStatus(status);
        return orderMapper.toDto(orderRepository.save(order));
    }
}
