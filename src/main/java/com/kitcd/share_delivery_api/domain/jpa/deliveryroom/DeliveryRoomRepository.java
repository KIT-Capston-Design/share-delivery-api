package com.kitcd.share_delivery_api.domain.jpa.deliveryroom;

import com.kitcd.share_delivery_api.dto.deliveryroom.ParticipatedDeliveryRoomDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliveryRoomRepository extends JpaRepository<DeliveryRoom, Long>, DeliveryRoomRepositoryCustom {

    @Query("select new com.kitcd.share_delivery_api.dto.deliveryroom.ParticipatedDeliveryRoomDTO" +
            "(ac.nickname, dr.content, dr.peopleNumber, dr.limitPerson, dr.storeName, dr.linkPlatformType, dr.createdDate, dr.status, rl.description, cat.categoryName) " +
            "from DeliveryRoom dr join dr.receivingLocation rl join dr.leader ac join dr.storeCategory cat where ac.accountId = :accountId")
    List<ParticipatedDeliveryRoomDTO> getDeliveryHistory(Long accountId);

    DeliveryRoom findByLeader_AccountId(Long accountId);

    DeliveryRoom findByDeliveryRoomId(Long roomId);

    @Query("select dr from DeliveryRoom dr join fetch dr.leader join fetch dr.receivingLocation where dr.deliveryRoomId = :deliveryRoomId")
    DeliveryRoom getDeliveryRoomByDeliveryRoomId(Long deliveryRoomId);
}