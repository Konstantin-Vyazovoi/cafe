package com.example.cafe.storage;

import com.example.cafe.model.event.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<OrderEvent, Long> {

    List<OrderEvent> findAllByOrderId(Long orderId);

}
