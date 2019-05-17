package com.friday.colini.firdaycoliniaccountapi.domain;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String userName;
    private String imageUrl;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private Set<RoleType> roles;
    private boolean mailYn;
    private boolean status;
    private String providerId;
    @Enumerated(value = EnumType.STRING)
    private AuthProvider provider;
    //        @CreatedDate
//    @Column(name = "create_at")
    private LocalDateTime createAt;
    //    @LastModifiedDate
//    @Column(name = "update_at")
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
        this.provider = AuthProvider.local;
    }

    public Account(
            String userName,
            String email,
            String imageUrl,
            boolean mailYn,
            String providerId,
            AuthProvider provider
    ) {
        this.userName = userName;
        this.email = email;
        this.password = "Auth2 SignUp";
        this.imageUrl = imageUrl;
        this.roles = new HashSet<>(Arrays.asList(RoleType.USER));
        this.mailYn = mailYn;
        this.status = true;
        this.providerId = providerId;
        this.provider = provider;
    }
}
