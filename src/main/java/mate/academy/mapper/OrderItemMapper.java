package mate.academy.mapper;

import mate.academy.config.MapperConfig;
import mate.academy.dto.orderitem.OrderItemDto;
import mate.academy.dto.orderitem.OrderItemRequestDto;
import mate.academy.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItem orderItem);

    OrderItem toModel(OrderItemRequestDto requestDto);
}
