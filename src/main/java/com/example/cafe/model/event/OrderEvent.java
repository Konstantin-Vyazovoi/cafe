package com.example.cafe.model.event;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "events", schema = "public")
public abstract class OrderEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    public Long id;
    @Column(name = "created")
    @NotNull
    public LocalDateTime createdTime;
    @Column(name = "order_id")
    @NotNull
    private Long orderId;
    @Column(name = "client_id")
    @NotNull
    private Long clientId;
    @Column(name = "employee_id")
    @NotNull
    private Long employeeId;

}
