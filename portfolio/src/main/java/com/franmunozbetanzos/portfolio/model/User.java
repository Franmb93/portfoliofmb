package com.franmunozbetanzos.portfolio.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import static com.franmunozbetanzos.portfolio.model.TableColumnsConstants.PASSWORD;
import static com.franmunozbetanzos.portfolio.model.TableColumnsConstants.USERNAME;

@Entity
@Table(name = TableColumnsConstants.USERS)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = USERNAME, length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = PASSWORD, length = 100, nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
}