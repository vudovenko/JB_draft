package ru.javabegin.hibernate;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

@Log4j2
public class Main {

    public static void main(String[] args) {

        log.info("Hibernate tutorial started");

        Session session = HibernateUtil.getSessionFactory().openSession();

        String query = """
                select
                    count(*),
                    substring(ud.email, position('@' in ud.email) + 1, length(ud.email))
                from todolist.user_data ud
                where email like '%@%'
                group by substring(ud.email, position('@' in ud.email) + 1, length(ud.email));
                """;
        NativeQuery<Object[]> sqlQuery = session.createNativeQuery(query, Object[].class);

        sqlQuery.getResultList().forEach(row -> {
            log.info(row[0] + " - " + row[1] + "\n-------------------------------");
        });

        session.close();
        HibernateUtil.close();
    }

}
