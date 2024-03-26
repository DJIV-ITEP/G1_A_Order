package com.food_delivery.g1_a_order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemShowDto;
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

    public void deleteOrderItem(Long id) {

        OrderItem item = null;
        Order order = null;
        try {

            item = itemRepository.findById(id).get();
            order = item.getOrder();

            if (order.getOrderStatus().getSequence() != OrderStatusEnum.CART.status.getSequence())
                StatusResponseHelper.notAcceptable("order already confirmed");

            itemRepository.deleteById(id);
            order.setUpdatedAt(LocalDateTime.now());

            // check order items if it is zero to delete the order
            if (0 == order.getOrderItems().size()) {
                orderRepository.deleteById(order.getId());
            } else {
                orderRepository.save(order);
            }

        }

        catch (NoSuchElementException e) {

            System.out.println(e);
            StatusResponseHelper.notFound("no item nither order found");
        }

        catch (ResponseStatusException e) {

            System.out.println(e);
            StatusResponseHelper.notAcceptable("order already confirmed");
        }

        catch (Exception e) {

            System.out.println(e);
            StatusResponseHelper.serverErr("contact developer team");
        }

    }

    public void addOrderItemToOrder(Long orderId, OrderItemsCreateDto itemDto) {

        Order order = null;

        try {
            order = orderRepository.findById(orderId).get();

        }

        catch (NoSuchElementException e) {
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

    public List<OrderItemShowDto> getOrderItemByOrder(Long orderId) {

        Order order = null;

        try {
            order = orderRepository.findById(orderId).get();

        } catch (NoSuchElementException e) {
            System.out.println(e);
            StatusResponseHelper.notFound("no order found");
        } catch (Exception e) {

            System.out.println(e);
            StatusResponseHelper.serverErr("contact developer team");
        }

        List<OrderItemShowDto> items = itemMapper.toOrderItemShowDto(order.getOrderItems());

        return items;

    }

}
