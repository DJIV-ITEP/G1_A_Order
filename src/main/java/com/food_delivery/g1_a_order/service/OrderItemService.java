package com.food_delivery.g1_a_order.service;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemShowDto;
import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemsCreateDto;
import com.food_delivery.g1_a_order.config.mapper.OrderItemMapper;
import com.food_delivery.g1_a_order.config.mapper.OrderMapper;
import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.entity.OrderItem;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.persistent.repository.OrderItemRepository;
import com.food_delivery.g1_a_order.persistent.repository.OrderRepository;
import com.food_delivery.g1_a_order.service.base.BaseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderItemService extends BaseService {

    private final OrderItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    @Autowired
    OrderRestaurantService orderRestaurantService;
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
    public OrderShowDto addOrderItemToOrder(Long customerId, Long restaurantId, List<OrderItemsCreateDto> itemDto) {

        if (null == restaurantId || null == customerId || null == itemDto)
            throwHandleNotAcceptable("customer id, restaurant id or item is missing");

        itemDto.forEach((item) -> {
            if (null == item.price() || null == item.quantity() || 0 == item.price() || 0 == item.quantity())
                handleNotAcceptable("price or quantity is missing");
        });

        Order order = orderRepository
                .findFirstByCustomerIdAndOrderStatusOrderByCreatedAtAsc(customerId, OrderStatusEnum.CART.status)
                .orElseGet(() -> {
                    OrderCreateDto newOrder = orderMapper.toOrderCreateDto(
                            Order.builder()
                                    .customerId(customerId)
                                    .restaurantId(restaurantId)
                                    .totalPrice(0).build());

                    Order createdOrder = orderService.createOrder(newOrder);
                    return createdOrder;
                });

        if (order.getRestaurantId() != restaurantId)
            handleNotAcceptable("there is an item exists from another restaurant");

        if (order.getOrderStatus().getSequence() != OrderStatusEnum.CART.status.getSequence())
            handleNotAcceptable("order already confirmed");

        // update order

        // orderService.addNewOrderItemsToOrder(itemDto, order.getId());
        OrderShowDto orderShowDto = orderMapper.toOrderShowDto(
                orderService
                        .addNewOrderItemsToOrder(itemDto, order.getId()));

        return orderShowDto;

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
