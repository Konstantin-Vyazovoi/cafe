package com.example.cafe.model;

import javax.persistence.*;

@Entity
@Table(name = "employees", schema = "public")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

}
