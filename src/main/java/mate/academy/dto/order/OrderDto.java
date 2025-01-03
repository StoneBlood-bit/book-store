package mate.academy.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import mate.academy.dto.orderitem.OrderItemDto;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private BigDecimal total;
    private String shippingAddress;
    private LocalDateTime orderDate;
    private String status;
    private List<OrderItemDto> orderItems;
}
