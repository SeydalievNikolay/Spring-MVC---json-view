package org.seydaliev.service.impl;

import org.seydaliev.model.Order;
import org.seydaliev.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class OrderServiceImpl implements OrderService {
    @Override
    public Page<Order> findByUserOrderByCreatedAtDesc(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public Order save(Order order) {
        return null;
    }

    @Override
    public void delete(Order order) {

    }
}
