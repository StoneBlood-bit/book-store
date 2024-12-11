package mate.academy.service;

import mate.academy.dto.CreateUserRequestDto;
import mate.academy.dto.UserDto;

public interface UserService {
    UserDto save(CreateUserRequestDto requestDto);
}
