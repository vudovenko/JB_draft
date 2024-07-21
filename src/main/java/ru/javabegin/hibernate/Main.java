package ru.javabegin.hibernate;

import org.hibernate.Session;
import ru.javabegin.hibernate.entity.User;

public class Main {

    public static void main(String[] args) {

        //сразу получаем готовый SessionFactory и сразу создаем сессию
        Session session = HibernateUtil.getSessionFactory().openSession();

        // открыть транзакцию
        session.getTransaction().begin();

        User user = new User(); // состояние NEW (transient)
        user.setEmail("newf2roma10ppfdsd8@email.com");
        user.setUsername("nsdewf10rofsdsdmapp8");
        user.setPassword("2asdfsdfsdf");
        // id не заполняем, т.к. он сгенерится автоматически в БД и присвоится в поле

        session.persist(user); // состояние managed (persistent) - управляемый объект - контейнером Hibernate

        session.getTransaction().commit(); // сохранить изменения (транзакция завершается)

        System.out.println("user.getId() = " + user.getId());

        session.close();// закрыть сессию

        // состояние detached

        session = HibernateUtil.getSessionFactory().openSession();

        // открыть транзакцию
        session.getTransaction().begin();

        user.setEmail("10@mail.ru");
        user.setUsername("us10");

        // т.к. мы хотим обновить уже существующий объект в БД, то вместо persist используем метод merge,
        // который по id находит запись и обновляет для нее новые данные
        // При попытке вызвать persist - вы получите ошибку "detached entity passed to persist", т.к. User создавался еще в прошлой сессии
        session.merge(user); //  заново присоединяем ранее созданный объект User

        System.out.println("user.getId() = " + user.getId());


        session.getTransaction().commit();
        HibernateUtil.close(); // закрыть Session Factory
    }

}
