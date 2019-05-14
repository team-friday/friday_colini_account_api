package com.friday.colini.firdaycoliniaccountapi.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account{

    @Id @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String userName;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private Set<RoleType> roles;
    private boolean mailYn;
    private boolean status;
    @CreatedDate
    @Column(name = "create_at", updatable = false)
    private LocalDateTime createAt;
    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Builder
    public Account(String email,
                   String password,
                   String userName,
                   Set<RoleType> roles,
                   boolean mailYn,
                   boolean status,
                   LocalDateTime createAt,
                   LocalDateTime updateAt) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.roles = roles;
        this.mailYn = mailYn;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
