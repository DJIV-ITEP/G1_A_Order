package com.food_delivery.g1_a_order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.food_delivery.g1_a_order.service.base.BaseService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderItemService extends BaseService {
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
                .orElseThrow(() -> handleNotFound("no item found"));

        Order order = item.getOrder();

        if (order.getOrderStatus().getSequence() != OrderStatusEnum.CART.status.getSequence())
            handleNotFound("order already confirmed");

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

        order = orderRepository
                .findFirstByCustomerIdAndOrderStatusOrderByCreatedAtAsc(customerId, OrderStatusEnum.CART.status)
                .orElseGet(() -> {
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
                        handleServerError("contact developer team");
                    }
                    return createdOrder;
                });

        if (order.getRestaurantId() != restaurantId)
            handleNotAcceptable("there is an item exists from another restaurant");

        if (order.getOrderStatus().getSequence() != OrderStatusEnum.CART.status.getSequence())
            handleNotAcceptable("order already confirmed");

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

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> handleNotFound("no order found"));

        List<OrderItemShowDto> items = itemMapper.toOrderItemShowDto(order.getOrderItems());

        return items;

    }

}
