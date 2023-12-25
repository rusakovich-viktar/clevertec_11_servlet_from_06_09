package by.clevertec.dao.impl;

import by.clevertec.dao.UserDao;
import by.clevertec.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

/**
 * Реализация интерфейса UserDao.
 * Предоставляет базовые CRUD операции (создание, чтение, обновление, удаление) для пользователей.
 */
@Log4j2
@ToString
public class UserDaoImpl implements UserDao {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("by.clevertec.persist");
    private final EntityManager em;

    /**
     * Конструктор класса UserDaoImpl.
     * Создает экземпляр EntityManager.
     */
    public UserDaoImpl() {
        em = emf.createEntityManager();
    }

    /**
     * Возвращает пользователя по указанному ID.
     *
     * @param id ID пользователя.
     * @return Пользователь с указанным ID или null, если такого пользователя нет.
     */
    @Override
    public User get(int id) {
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        em.getTransaction().commit();
        return user;
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return Список всех пользователей.
     */
    @Override
    public List<User> getAll() {
        em.getTransaction().begin();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        List<User> users = query.getResultList();
        em.getTransaction().commit();
        return users;
    }

    /**
     * Сохраняет пользователя в базу данных.
     *
     * @param user Пользователь для сохранения.
     * @return Сохраненный пользователь.
     */
    public User save(User user) {
        try {
            em.persist(user);
        } catch (Exception e) {
            log.error(("Ошибка при сохранении пользователя: " + e.getMessage()));
        }
        return user;
    }


    /**
     * Обновляет пользователя в базе данных.
     *
     * @param user Пользователь для обновления.
     * @return Обновленный пользователь.
     */
    @Override
    public User update(User user) {
        em.getTransaction().begin();
        User updatedUser = em.merge(user);
        em.getTransaction().commit();
        return updatedUser;
    }

    /**
     * Удаляет пользователя из базы данных.
     *
     * @param user Пользователь для удаления.
     */
    @Override
    public void delete(User user) {
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }

    /**
     * Закрывает EntityManagerFactory.
     */
    @Override
    public void close() {
        emf.close();
    }
}
