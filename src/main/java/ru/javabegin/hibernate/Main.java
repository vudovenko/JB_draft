package ru.javabegin.hibernate;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import ru.javabegin.hibernate.entity.User;

import java.util.List;

@Log4j2
public class Main {

    public static void main(String[] args) {

        log.info("Hibernate tutorial started");

        Session session = HibernateUtil.getSessionFactory().openSession();

        String query = "select * from todolist.user_data";
        NativeQuery<User> sqlQuery = session.createNativeQuery(query, User.class);

        sqlQuery.setMaxResults(10);

        List<User> list = sqlQuery.list();

        log.info("Users: " + list);

        session.close();
        HibernateUtil.close();
    }

}
