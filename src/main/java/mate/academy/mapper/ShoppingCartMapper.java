package mate.academy.mapper;

import mate.academy.config.MapperConfig;
import mate.academy.dto.shoppingcart.ShoppingCartDto;
import mate.academy.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @Mapping(target = "id", ignore = true)
    ShoppingCart toModel(ShoppingCartDto shoppingCartDto);
}
