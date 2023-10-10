package com.example.cafe.model.event;

import com.example.cafe.model.Product;
import lombok.Builder;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Builder
@Setter
@Entity
@Table(name = "registered_events", schema = "public")
public class RegisteredOrderEvent extends OrderEvent {

    @Column(name = "expected_time")
    private LocalTime expectedTime;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
