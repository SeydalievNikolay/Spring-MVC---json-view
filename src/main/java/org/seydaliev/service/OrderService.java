package org.seydaliev.service;

import org.seydaliev.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<Order> findByUserOrderByCreatedAtDesc(Long userId, Pageable pageable);
    Order save(Order order);
    void delete(Order order);
}
