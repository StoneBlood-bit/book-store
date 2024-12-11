package mate.academy.mapper;

import mate.academy.config.MapperConfig;
import mate.academy.dto.user.UserResponseDto;
import mate.academy.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "shippingAddress", source = "shippingAddress")
    UserResponseDto toUserResponse(User user);
}
