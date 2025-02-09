package com.franmunozbetanzos.portfolio.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Table(name = TableColumnsConstants.CONTACT)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Contact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String message;
}