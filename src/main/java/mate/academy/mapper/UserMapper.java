package mate.academy.mapper;

import mate.academy.config.MapperConfig;
import mate.academy.dto.CreateUserRequestDto;
import mate.academy.dto.UserDto;
import mate.academy.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto toDto(User user);

    User toModel(CreateUserRequestDto requestDto);
}
