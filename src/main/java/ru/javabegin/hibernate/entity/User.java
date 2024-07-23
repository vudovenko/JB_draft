package ru.javabegin.hibernate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;
import java.util.Objects;
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
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, optional = false)
    public Activity activity; // активность пользователя (активация и любые другие)

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, optional = false)
    public Stat stat; // общая статистика пользователя по всем задачам

    // если нам не нужна таблица UserRole и ее данные - мы можем просто сразу получить все Role пользователя
    @ManyToMany(fetch = FetchType.LAZY) // таблица role ссылается на user через промежуточную таблицу user_role
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", updatable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", updatable = false))
    private Set<Role> roles;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return username;
    }
}
