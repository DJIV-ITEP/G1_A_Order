package com.food_delivery.g1_a_order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemShowDto;
import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemsCreateDto;
import com.food_delivery.g1_a_order.config.mapper.OrderItemMapper;
import com.food_delivery.g1_a_order.config.mapper.OrderMapper;
import com.food_delivery.g1_a_order.helper.StatusResponseHelper;
import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.entity.OrderItem;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.persistent.repository.OrderItemRepository;
import com.food_delivery.g1_a_order.persistent.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderItemService {
    private final OrderItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Autowired
    private final OrderItemMapper itemMapper;

    @Autowired
    private final OrderMapper orderMapper;

    @Transactional
    public void deleteOrderItem(Long itemId) {

        OrderItem item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> StatusResponseHelper.getNotFound("no item found"));

        Order order = item.getOrder();

        if (order.getOrderStatus().getSequence() != OrderStatusEnum.CART.status.getSequence())
            StatusResponseHelper.notAcceptable("order already confirmed");

        // check order items if it is zero to delete the order
        if (1 >= order.getOrderItems().size()) {
            orderRepository.deleteById(order.getId());
        } else {
            order.getOrderItems().remove(item);
            order.setOrderItems(order.getOrderItems());
            // order.setUpdatedAt(LocalDateTime.now());
            orderRepository.saveAndFlush(order);
            itemRepository.deleteById(itemId);
        }

    }

    @Transactional
    public boolean addOrderItemToOrder(Long customerId, Long restaurantId, List<OrderItemsCreateDto> itemDto) {

        Order order = null;

        try {

            order = orderRepository
                    .findFirstByCustomerIdAndOrderStatusOrderByCreatedAtAsc(customerId, OrderStatusEnum.CART.status)
                    .get();

        }

        catch (NoSuchElementException e) {
            System.out.println(e);
            // handle create new order here
            List<OrderItemsCreateDto> itemDtoList = itemDto;

            OrderCreateDto newOrder = orderMapper.toOrderCreateDto(
                    Order.builder()
                            .customerId(customerId)
                            .restaurantId(restaurantId)
                            .orderItems(
                                    itemMapper.toOrderItem(itemDtoList))
                            .build());

            Order createdOrder = orderService.createOrder(newOrder);

            if (createdOrder == null) {
                StatusResponseHelper.serverErr("contact developer team");
            }

            return true;

        } catch (Exception e) {

            System.out.println(e);
            StatusResponseHelper.serverErr("contact develop team");

        }

        if (order.getRestaurantId() != restaurantId)
            StatusResponseHelper.notAcceptable("item from another restaurant");

        if (order.getOrderStatus().getSequence() != OrderStatusEnum.CART.status.getSequence())
            StatusResponseHelper.notAcceptable("order already confirmed");

        // update order
        List<OrderItem> items = itemMapper.toOrderItem(itemDto);
        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.addAll(items);
        order.setOrderItems(orderItems);
        order.setUpdatedAt(LocalDateTime.now());

        final Order finalOrder = order;
        items.forEach(item -> item.setOrder(finalOrder));

        orderRepository.saveAndFlush(finalOrder);
        return true;

    }

    @Transactional
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
