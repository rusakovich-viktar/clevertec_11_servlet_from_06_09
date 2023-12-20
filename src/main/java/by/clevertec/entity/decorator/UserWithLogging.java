package by.clevertec.entity.decorator;

import by.clevertec.entity.User;
import lombok.extern.log4j.Log4j2;

/**
 * Декоратор User, добавляющий логирование.
 */
@Log4j2
public class UserWithLogging extends User {
    private final User user;

    /**
     * Конструктор класса UserWithLogging.
     *
     * @param user Объект класса User.
     */
    public UserWithLogging(User user) {
        this.user = user;
    }

    /**
     * Устанавливает id пользователя и логирует это действие.
     *
     * @param id ID пользователя.
     */
    @Override
    public void setId(int id) {
        log.info("Setting id");
        user.setId(id);
    }

    /**
     * Возвращает id пользователя и логирует это действие.
     *
     * @return ID пользователя.
     */
    @Override
    public int getId() {
        log.info("Getting id");
        return user.getId();
    }

}
