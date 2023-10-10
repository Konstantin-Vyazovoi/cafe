package com.example.cafe.service;

import com.example.cafe.exception.InvalidStatusException;
import com.example.cafe.exception.NotFoundException;
import com.example.cafe.model.event.*;
import com.example.cafe.model.order.Order;
import com.example.cafe.model.order.OrderDto;
import com.example.cafe.model.order.OrderStatus;
import com.example.cafe.storage.EventRepository;
import com.example.cafe.storage.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    private final EventRepository eventRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(EventRepository eventRepository, OrderRepository orderRepository) {
        this.eventRepository = eventRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void publishEvent(OrderEvent event) {
        Long orderId = event.getOrderId();
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) throw new NotFoundException(String.format("Заказ с id %s не найден", orderId));
        Order order = optionalOrder.get();
        List<OrderEvent> events = eventRepository.findAllByOrderId(orderId);
        switch (order.getStatus()) {
            case REGISTERED -> {
                if (event.getClass().equals(RegisteredOrderEvent.class) && events.isEmpty()) {
                    eventRepository.save(event);
                } else if (event.getClass().equals(InProgressEvent.class)) {
                    order.setStatus(OrderStatus.IN_PROGRESS);
                    eventRepository.save(event);
                } else if (event.getClass().equals(CanceledOrderEvent.class)) {
                    order.setStatus(OrderStatus.CANCELED);
                    eventRepository.save(event);
                } else throw new InvalidStatusException("Неправильный статус заказа или события");
            }
            case IN_PROGRESS -> {
                if (event.getClass().equals(ReadyForIssueEvent.class)) {
                    order.setStatus(OrderStatus.READY);
                    eventRepository.save(event);
                } else throw new InvalidStatusException("Неправильный статус заказа или события");
            }
            case READY -> {
                if (event.getClass().equals(IssuedEvent.class)) {
                    order.setStatus(OrderStatus.ISSUED);
                    eventRepository.save(event);
                } else throw new InvalidStatusException("Неправильный статус заказа или события");
            }
            case CANCELED -> {
                throw new InvalidStatusException("Заказ уже отменён");
            }
            case ISSUED -> {
                throw new InvalidStatusException("Заказ уже выдан");
            }
        }
    }

    @Override
    public OrderDto findOrder(int id) {
        Optional<Order> optionalOrder = orderRepository.findById((long) id);
        List<OrderEvent> events = eventRepository.findAllByOrderId((long) id);
        if (optionalOrder.isEmpty()) throw new NotFoundException(String.format("Заказ с id %s не найден", id));
        if (events.isEmpty()) throw new NotFoundException(String.format("События заказа с id %s не найдены", id));
        Order order = optionalOrder.get();
        return OrderDto.builder()
            .id(order.getId())
            .status(order.getStatus())
            .events(events)
            .build();
    }

}
