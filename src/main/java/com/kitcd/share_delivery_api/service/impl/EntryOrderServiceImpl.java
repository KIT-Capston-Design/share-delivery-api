package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderRepository;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.dto.entryorder.OrderResDTO;
import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;
import com.kitcd.share_delivery_api.service.EntryOrderService;
import com.kitcd.share_delivery_api.service.OrderMenuService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EntryOrderServiceImpl implements EntryOrderService {

    private final EntryOrderRepository entryOrderRepository;
    private final OrderMenuService orderMenuService;
    private final DeliveryRoomRepository deliveryRoomRepository;

    @Override
    public List<OrderResDTO> getOrderInformation(Long deliveryRoomId, State orderStatus){
        List<EntryOrder> orders = entryOrderRepository.getOrderInformation(deliveryRoomId, orderStatus);

        return orders.stream().map(EntryOrder::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public Long acceptOrders(Long deliveryRoomId) {

        List<EntryOrder> eos = getPendingEntryOrderByDeliveryRoomId(deliveryRoomId);

        eos.forEach(EntryOrder::accept);

        return (long) eos.size();
    }

    @Override
    public List<EntryOrder> getPendingEntryOrderByDeliveryRoomId(Long deliveryRoomId) {

        List<EntryOrder> orders = entryOrderRepository.getPendingEntryOrderByDeliveryRoomId(deliveryRoomId);

        if(orders == null) throw new EntityNotFoundException(EntryOrder.class.toString());

        return orders;
    }

    @Override
    public EntryOrder enrollEntryOrder(DeliveryRoom deliveryRoom, List<OrderMenuRequestDTO> menuList, EntryOrderType entryOrderType, State state) {

        EntryOrder entryOrder = entryOrderRepository.save(EntryOrder.builder()
                .account(ContextHolder.getAccount())
                .orderType(entryOrderType)
                .status(state)
                .deliveryRoom(deliveryRoom)
                .build());

        orderMenuService.enrollMenu(entryOrder, menuList);

        deliveryRoom.addPerson();
        deliveryRoomRepository.save(deliveryRoom);

        return entryOrder;
    }

    @Override
    public EntryOrder findByAccountIdAndDeliveryRoomId(Long accountId, Long deliveryRoomId) {

        EntryOrder entryOrder = entryOrderRepository.findByAccount_AccountIdAndDeliveryRoom_DeliveryRoomId(accountId, deliveryRoomId);

        if(entryOrder == null) throw new EntityNotFoundException(EntryOrder.class.toString());

        return entryOrder;
    }

    @Override
    public void rejectEntryOrder(Long userId, DeliveryRoom deliveryRoom) {

        //request에 해당하는 EntryOrder entity 조회
        EntryOrder entryOrder = findByAccountIdAndDeliveryRoomId(userId, deliveryRoom.getDeliveryRoomId());

        //현재 요청이 주도자의 요청인지 확인은 스프링 시큐리티 어노테이션 통해 수행

        //인원 모집 상태가 아닌 경우 (이미 주문 진행 단계로 넘어 간 경우)
        if(!(deliveryRoom.getStatus().equals(DeliveryRoomState.OPEN)))
            throw new IllegalStateException("Room status is not OPEN");
        entryOrder.reject();
    }
}
