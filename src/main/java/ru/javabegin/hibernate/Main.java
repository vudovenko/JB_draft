package ru.javabegin.hibernate;



import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import ru.javabegin.hibernate.entity.User;

import java.util.List;

@Log4j2
public class Main {

    public static void main(String[] args) {

        log.info("Hibernate tutorial started");

        //сразу получаем готовый SessionFactory и сразу создаем сессию
        Session session = HibernateUtil.getSessionFactory().openSession();

        // подготовка запроса - получение всех пользователей
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root); // конечный запрос select из таблицы User

        Predicate p1 = criteriaBuilder.gt(root.get("id"), 10000);
        Predicate p2 = criteriaBuilder.lt(root.get("id"), 20000);

        criteriaQuery.select(root).where(criteriaBuilder.and(p1 ,p2));

        // выполнение запроса с получением результата
        Query query = session.createQuery(criteriaQuery);
        List<User> users = query.getResultList();

        // log.info(users); // если у вас в таблице много пользователей, то при выводе их в консоль, может немного подтормаживать
        log.info("users count = "+users.size()); // можно вывести просто кол-во полученных данных, чтобы не заполнять всю консоль пользователями

        session.close();// закрыть сессию

        HibernateUtil.close(); // закрыть Session Factory
    }

}
