package com.food_delivery.g1_a_order.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemsCreateDto;
import com.food_delivery.g1_a_order.config.mapper.OrderItemMapper;
import com.food_delivery.g1_a_order.helper.StatusResponseHelper;
import com.food_delivery.g1_a_order.helper._PrintHelper;
import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.entity.OrderItem;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.persistent.repository.OrderItemRepository;
import com.food_delivery.g1_a_order.persistent.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderItemService {

    private final OrderItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Autowired
    private final OrderItemMapper itemMapper;
    public boolean deleteOrderItem(Long id) {

        OrderItem item = null;
        try {

            item = itemRepository.findById(id).get();

            itemRepository.deleteById(id);

            if (0 == item.getOrder().getOrderItems().size());
            orderRepository.deleteById(item.getOrder().getId());

        } catch (Exception e) {
            System.out.println(e);
            StatusResponseHelper.notFound("no item nither order found");
            return false;
        }

        return true;

    public void addOrderItemToOrder(Long orderId, OrderItemsCreateDto itemDto) {

        Order order = null;

        try {
            order = orderRepository.findById(orderId).get();

        } catch (NoSuchElementException e) {
            System.out.println(e);
            StatusResponseHelper.notFound("no order found");
        }
        catch (Exception e) {

            System.out.println(e);
            StatusResponseHelper.serverErr("contact developer team");
        }

        if (order.getOrderStatus().getSequence() != OrderStatusEnum.CART.status.getSequence())
            StatusResponseHelper.notAcceptable("order already confirmed");

        OrderItem item = itemMapper.toOrderItem(itemDto);
        order.getOrderItems().add(item);
        order.setOrderItems(order.getOrderItems());
        order.setUpdatedAt(LocalDateTime.now());
        item.setOrder(order);

        orderRepository.save(order);

    }

}
