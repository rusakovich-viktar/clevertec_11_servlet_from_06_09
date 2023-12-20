package by.clevertec.mapper;

import by.clevertec.dto.UserDto;
import by.clevertec.entity.User;

/**
 * Фабрика для создания объектов User.
 */
public class UserFactory {

    /**
     * Создает и возвращает объект User на основе данных UserDto.
     *
     * @param userDto Объект UserDto, содержащий данные пользователя.
     * @return Объект User с данными из UserDto.
     */
    public static User createUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        return user;
    }
}
