package com.example.cafe.model.event;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "canceled_events", schema = "public")
public class CanceledOrderEvent extends OrderEvent {

    @Column(name = "cancel_reason")
    private String cancelReason;

}
