package com.kitcd.share_delivery_api.domain.jpa.entryorder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryOrderTableRepository extends JpaRepository<EntryOrder, Long> {
    EntryOrder findByAccount_AccountIdAndDeliveryRoom_DeliveryRoomId(Long accountId, Long deliveryRoomId);
}
