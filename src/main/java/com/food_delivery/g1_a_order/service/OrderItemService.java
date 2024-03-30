package com.food_delivery.g1_a_order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
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
    public void deleteOrderItem(Long id) {

        OrderItem item;
        Order order;
        try {

            item = itemRepository.findById(id).get();
            order = item.getOrder();

            if (order.getOrderStatus().getSequence() != OrderStatusEnum.CART.status.getSequence())
                StatusResponseHelper.notAcceptable("order already confirmed");

            // check order items if it is zero to delete the order
            if (1 == order.getOrderItems().size()) {
                orderRepository.deleteById(order.getId());
            } else {
                order.getOrderItems().remove(item); 
                order.setUpdatedAt(LocalDateTime.now());
                orderRepository.save(order);
                itemRepository.deleteById(id);
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
            // StatusResponseHelper.notFound("no order found");
            // handle create new order here
            List<OrderItemsCreateDto> itemDtoList = itemDto;

            // TODO: uncomment this code when customer service is ready
            // get customer address
            // Long customerAddressId = customerEndpoint.get()
            // .uri("/customer/address/" + order.getCustomerId())
            // .retrieve()
            // .bodyToMono(Long.class)
            // .block();

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

        } catch (Exception e) {

            System.out.println(e);
            StatusResponseHelper.serverErr("contact develop team");

        }

        if (order.getRestaurantId() != restaurantId)
            StatusResponseHelper.notAcceptable("item from another restaurant");

        if (order.getOrderStatus().getSequence() != OrderStatusEnum.CART.status.getSequence())
            StatusResponseHelper.notAcceptable("order already confirmed");

        List<OrderItem> items = itemMapper.toOrderItem(itemDto);
        order.getOrderItems().addAll(items);
        order.setOrderItems(order.getOrderItems());
        order.setUpdatedAt(LocalDateTime.now());

        final Order finalOrder = order;
        items.forEach(item -> item.setOrder(finalOrder));

        orderRepository.save(order);
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
