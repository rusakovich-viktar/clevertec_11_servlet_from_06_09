package by.clevertec.service;

import by.clevertec.dto.UserDto;
import java.util.List;

public interface UserService {
    UserDto getUser(int id);

    List<UserDto> getAllUsers(int page, int pageSize);

    void saveUser(UserDto userDto);

    void updateUser(UserDto userDto);

    void deleteUser(int id);
}
