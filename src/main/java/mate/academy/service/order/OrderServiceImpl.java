package mate.academy.service.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.order.OrderDto;
import mate.academy.dto.order.OrderRequestDto;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.mapper.OrderMapper;
import mate.academy.model.Order;
import mate.academy.model.OrderItem;
import mate.academy.model.ShoppingCart;
import mate.academy.repository.OrderRepository;
import mate.academy.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public OrderDto createOrder(OrderRequestDto requestDto, Long userId) {
        ShoppingCart shoppingCart = getShoppingCartForUser(userId);
        Order order = createNewOrder(requestDto, shoppingCart);

        Set<OrderItem> orderItems = buildOrderItems(shoppingCart, order);
        BigDecimal total = calculateTotal(orderItems);

        order.setTotal(total);
        order.setOrderItems(orderItems);

        orderRepository.save(order);
        clearShoppingCart(shoppingCart);

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

    private ShoppingCart getShoppingCartForUser(Long userId) {
        return shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Can not find user with id: " + userId)
        );
    }

    private Order createNewOrder(OrderRequestDto requestDto, ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setStatus(Order.Status.PENDING);
        order.setShippingAddress(requestDto.getShippingAddress());
        return order;
    }

    private Set<OrderItem> buildOrderItems(ShoppingCart shoppingCart, Order order) {
        return shoppingCart.getCartItems().stream()
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
                })
                .collect(Collectors.toSet());
    }

    private BigDecimal calculateTotal(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void clearShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);
    }
}
