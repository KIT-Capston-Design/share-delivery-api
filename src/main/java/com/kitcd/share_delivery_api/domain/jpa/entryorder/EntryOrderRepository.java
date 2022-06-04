package com.kitcd.share_delivery_api.domain.jpa.entryorder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntryOrderRepository extends JpaRepository<EntryOrder, Long> {
    EntryOrder findByAccount_AccountIdAndDeliveryRoom_DeliveryRoomId(Long accountId, Long deliveryRoomId);

    List<EntryOrder> findByDeliveryRoom_DeliveryRoomId(Long deliveryRoomId);

    @Query("select distinct o from EntryOrder o join fetch o.orderMenus join fetch o.account where o.deliveryRoom.deliveryRoomId = :deliveryRoomId")
    List<EntryOrder> getOrderInformation(Long deliveryRoomId);

    @Query("select o from EntryOrder o join o.deliveryRoom dr where o.status = com.kitcd.share_delivery_api.domain.jpa.common.State.PENDING and dr.deliveryRoomId = :deliveryRoomId")
    List<EntryOrder> getPendingEntryOrderByDeliveryRoomId(Long deliveryRoomId);


    @Query("select distinct o from EntryOrder o join fetch o.orderMenus join fetch o.account " +
            "where o.deliveryRoom.deliveryRoomId = :deliveryRoomId and o.status = com.kitcd.share_delivery_api.domain.jpa.common.State.ACCEPTED")
    List<EntryOrder> getAcceptedOrderInformation(Long deliveryRoomId);
}
