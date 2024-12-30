package mate.academy.dto.orderitem;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderItemRequestDto {
    @NotBlank
    private String status;
}
