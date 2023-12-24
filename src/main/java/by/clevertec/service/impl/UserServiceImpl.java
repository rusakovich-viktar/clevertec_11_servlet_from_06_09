package by.clevertec.service.impl;

import by.clevertec.dao.UserDao;
import by.clevertec.dto.UserDto;
import by.clevertec.entity.User;
import by.clevertec.mapper.UserFactory;
import by.clevertec.mapper.UserMapper;
import by.clevertec.proxy.annotation.Cacheable;
import by.clevertec.service.UserService;
import by.clevertec.validation.UserDtoValidator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Класс UserServiceImpl реализует интерфейс UserService и предоставляет базовые операции CRUD (создание, чтение, обновление, удаление) для пользователей.
 * Этот класс использует UserDao для взаимодействия с базой данных и UserMapper для преобразования между User и UserDto.
 * Кроме того, он использует UserDtoValidator для проверки UserDto перед сохранением или обновлением.
 */

@NoArgsConstructor
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserMapper userMapper = new UserMapper();
    private UserDtoValidator userDtoValidator;
    private UserDao userDao;

    /**
     * Возвращает UserDto для пользователя с указанным ID.
     *
     * @param id ID пользователя.
     * @return UserDto для пользователя с указанным ID или null, если такого пользователя нет.
     */
    @Override
    @Cacheable
    public UserDto getUser(int id) {
        return Optional.ofNullable(userDao.get(id)).map(userMapper::convertToDto).orElse(null);
    }

    /**
     * Возвращает список всех пользователей в виде UserDto.
     *
     * @return Список всех пользователей в виде UserDto.
     */
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userDao.getAll();
        return users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    /**
     * Сохраняет пользователя в базе данных. Перед сохранением UserDto проверяется на валидность.
     *
     * @param userDto UserDto пользователя для сохранения.
     */
    @Override
    @Cacheable
    public void saveUser(UserDto userDto) {
        userDtoValidator.validate(userDto);
        User user = UserFactory.createUser(userDto);
        userDao.save(user);
    }

    /**
     * Обновляет пользователя в базе данных. Перед обновлением UserDto проверяется на валидность.
     *
     * @param userDto UserDto пользователя для обновления.
     */
    @Override
    @Cacheable
    public void updateUser(UserDto userDto) {
        userDtoValidator.validate(userDto);
        User user = userMapper.convertToEntity(userDto);
        userDao.update(user);
    }

    /**
     * Удаляет пользователя из базы данных.
     *
     * @param userDto UserDto пользователя для удаления.
     */
    @Override
    @Cacheable
    public void deleteUser(UserDto userDto) {
        User user = userMapper.convertToEntity(userDto);
        userDao.delete(user);
    }

}
