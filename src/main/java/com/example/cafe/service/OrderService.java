package com.example.cafe.service;

import com.example.cafe.model.event.OrderEvent;
import com.example.cafe.model.order.OrderDto;


public interface OrderService {

    void publishEvent(OrderEvent event);

    OrderDto findOrder(int id);

}
