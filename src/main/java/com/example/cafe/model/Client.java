package com.example.cafe.model;

import javax.persistence.*;

@Entity
@Table(name = "clients", schema = "public")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

}
