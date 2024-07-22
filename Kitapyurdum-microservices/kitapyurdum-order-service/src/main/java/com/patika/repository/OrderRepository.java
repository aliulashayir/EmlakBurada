package com.patika.repository;

import com.patika.model.Order;
import com.patika.constants.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class OrderRepository {
    private List<Order> orders = new ArrayList<>();
    private long currentId = 1;

    public void save(Order order) {
        order.setId(currentId++);
        orders.add(order);
    }

    public List<Order> findAll() {
        return orders;
    }

    public Optional<Order> findById(Long id) {
        return orders.stream().filter(order -> order.getId().equals(id)).findFirst();
    }

    public List<Order> findByCustomerId(Long customerId) {
        return orders.stream().filter(order -> order.getCustomerId().equals(customerId)).collect(Collectors.toList());
    }

    public Page<Order> findAll(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Order> list;

        if (orders.size() < startItem) {
            list = new ArrayList<>();
        } else {
            int toIndex = Math.min(startItem + pageSize, orders.size());
            list = orders.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), orders.size());
    }

    public Page<Order> searchOrders(String orderCode, OrderStatus orderStatus, Pageable pageable) {
        List<Order> filteredOrders = orders.stream()
                .filter(order -> (orderCode == null || order.getOrderCode().contains(orderCode)) &&
                        (orderStatus == null || order.getOrderStatus().equals(orderStatus)))
                .collect(Collectors.toList());

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Order> list;

        if (filteredOrders.size() < startItem) {
            list = new ArrayList<>();
        } else {
            int toIndex = Math.min(startItem + pageSize, filteredOrders.size());
            list = filteredOrders.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), filteredOrders.size());
    }
}
