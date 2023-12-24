package by.clevertec.dao;

import by.clevertec.entity.User;
import java.util.List;

public interface UserDao {
    User get(int id);

    List<User> getAll(int page, int pageSize);

    User save(User user);

    User update(User user);

    void delete(User user);

    void close();
}
