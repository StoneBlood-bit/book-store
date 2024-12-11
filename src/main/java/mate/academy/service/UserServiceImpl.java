package mate.academy.service;

import lombok.RequiredArgsConstructor;
import mate.academy.dto.CreateUserRequestDto;
import mate.academy.dto.UserDto;
import mate.academy.mapper.UserMapper;
import mate.academy.model.User;
import mate.academy.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserDto save(CreateUserRequestDto requestDto) {
        User user = userMapper.toModel(requestDto);
        return userMapper.toDto(userRepository.save(user));
    }
}
