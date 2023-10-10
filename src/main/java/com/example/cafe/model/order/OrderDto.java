package com.example.cafe.model.order;

import com.example.cafe.model.event.OrderEvent;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@Builder
public class OrderDto {

    @NonNull
    private Long id;
    @NonNull
    private OrderStatus status;
    @NonNull
    private List<OrderEvent> events;

}
