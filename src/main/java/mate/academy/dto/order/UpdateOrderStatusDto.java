package mate.academy.dto.order;

import lombok.Data;
import mate.academy.model.Order;

@Data
public class UpdateOrderStatusDto {
    private Order.Status status;
}
