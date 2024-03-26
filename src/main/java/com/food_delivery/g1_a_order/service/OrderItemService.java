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
    private final OrderService orderService;

    @Autowired
    private final OrderItemMapper itemMapper;

    @Autowired
    private final OrderMapper orderMapper;

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

    public boolean addOrderItemToOrder(Long customerId, Long restaurantId, OrderItemsCreateDto itemDto) {

        Order order = null;

        try {

            order = orderRepository
                    .findFirstByCustomerIdAndOrderStatusOrderByCreatedAtAsc(customerId, OrderStatusEnum.CART.status)
                    .get();

        }

        catch (NoSuchElementException e) {
            System.out.println(e);
            // StatusResponseHelper.notFound("no order found");
            // handle create new order here
            List<OrderItemsCreateDto> itemDtoList = List.of(itemDto);

            OrderCreateDto newOrder = orderMapper.toOrderCreateDto(
                    Order.builder()
                            .customerId(customerId)
                            .restaurantId(restaurantId)
                            .orderItems(
                                    itemMapper.toOrderItem(itemDtoList))
                            .build());

            if (!orderService.createOrder(newOrder))
                StatusResponseHelper.serverErr("contact developer team");

            return true;

        }
        catch (Exception e) {

            System.out.println(e);
            StatusResponseHelper.serverErr("contact developer team");

        }

        if (order.getRestaurantId() != restaurantId)
            StatusResponseHelper.notAcceptable("item from another restaurant");

        if (order.getOrderStatus().getSequence() != OrderStatusEnum.CART.status.getSequence())
            StatusResponseHelper.notAcceptable("order already confirmed");

        OrderItem item = itemMapper.toOrderItem(itemDto);
        order.getOrderItems().add(item);
        order.setOrderItems(order.getOrderItems());
        order.setUpdatedAt(LocalDateTime.now());
        item.setOrder(order);

        orderRepository.save(order);
        return true;

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
