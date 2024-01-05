package by.clevertec.service.impl;

import by.clevertec.dao.UserDao;
import by.clevertec.dto.UserDto;
import by.clevertec.entity.User;
import by.clevertec.mapper.UserMapper;
import by.clevertec.proxy.annotation.Cacheable;
import by.clevertec.service.UserService;
import by.clevertec.validation.UserDtoValidator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

/**
 * Класс UserServiceImpl реализует интерфейс UserService и предоставляет базовые операции CRUD (создание, чтение, обновление, удаление) для пользователей.
 * Этот класс использует UserDao для взаимодействия с базой данных и UserMapper для преобразования между User и UserDto.
 * Кроме того, он использует UserDtoValidator для проверки UserDto перед сохранением или обновлением.
 */

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserDtoValidator userDtoValidator;
    private final UserDao userDao;

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
     * Получает список пользователей с учетом пагинации.
     *
     * @param page     Номер страницы, начиная с 0.
     * @param pageSize Количество пользователей на странице.
     * @return Список пользователей на указанной странице, преобразованный в DTO.
     */
    @Override
    public List<UserDto> getAllUsers(int page, int pageSize) {
        List<User> users = userDao.getAll(page, pageSize);
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
        User user = userMapper.convertToEntity(userDto);
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
     * @param id ID пользователя для удаления.
     */

    @Override
    @Cacheable
    public void deleteUser(int id) {
        User user = userDao.get(id);
        if (user != null) {
            userDao.delete(user);
        } else {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
    }

}
