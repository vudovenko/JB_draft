package ru.javabegin.hibernate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/*

пользователь - основной объект, с которым связаны все остальные (через внешние ключи)

 */

@Entity
@Table(name = "user_data", schema = "todolist", catalog = "postgres")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    @Column(name = "userpassword")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Category> categories;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Priority> priorities;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    public Activity activity; // активность пользователя (активация и любые другие)

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    public Stat stat; // общая статистика пользователя по всем задачам

    // если нам не нужна таблица UserRole и ее данные - мы можем просто сразу получить все Role пользователя
    @ManyToMany(fetch = FetchType.LAZY) // таблица role ссылается на user через промежуточную таблицу user_role
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


}
