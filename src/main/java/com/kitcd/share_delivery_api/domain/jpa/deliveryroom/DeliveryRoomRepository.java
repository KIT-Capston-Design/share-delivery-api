package com.kitcd.share_delivery_api.domain.jpa.deliveryroom;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.dto.deliveryroom.ParticipatedDeliveryRoomDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliveryRoomRepository extends JpaRepository<DeliveryRoom, Long>, DeliveryRoomRepositoryCustom {

    //리더 ID 기반 조회
    //    @Query("select new com.kitcd.share_delivery_api.dto.deliveryroom.ParticipatedDeliveryRoomDTO" +
    //            "(dr.deliveryRoomId, ac.nickname, dr.content, dr.peopleNumber, dr.limitPerson, dr.storeName, dr.linkPlatformType, dr.createdDate, dr.status, rl.description, cat.categoryName) " +
    //            "from DeliveryRoom dr join dr.receivingLocation rl join dr.leader ac join dr.storeCategory cat where ac.accountId = :accountId")

    @Query("select new com.kitcd.share_delivery_api.dto.deliveryroom.ParticipatedDeliveryRoomDTO" +
        "(dr.deliveryRoomId, ac.nickname, dr.content, dr.peopleNumber, dr.limitPerson, dr.storeName, dr.linkPlatformType, dr.createdDate, dr.status, rl.description, cat.categoryName) " +
        "from EntryOrder o join o.account ac0 join o.deliveryRoom dr join dr.leader ac join dr.receivingLocation rl join dr.storeCategory cat " +
            "where ac0.accountId = :accountId and o.status = com.kitcd.share_delivery_api.domain.jpa.common.State.ACCEPTED")
    List<ParticipatedDeliveryRoomDTO> getDeliveryHistory(Long accountId);

    DeliveryRoom findByLeader_AccountId(Long accountId);

    DeliveryRoom findByDeliveryRoomId(Long roomId);

    @Query("select dr from DeliveryRoom dr join fetch dr.leader join fetch dr.receivingLocation where dr.deliveryRoomId = :deliveryRoomId")
    DeliveryRoom getDeliveryRoomByDeliveryRoomId(Long deliveryRoomId);

//    @Query("select eo.account.accountId from DeliveryRoom dr join EntryOrder eo where dr.deliveryRoomId = :roomId and eo.status = com.kitcd.share_delivery_api.domain.jpa.common.State.ACCEPTED")
    @Query("select eo.account.accountId from EntryOrder eo join eo.deliveryRoom dr where dr.deliveryRoomId = :roomId and eo.status = :state")
    List<Long> getParticipantsIds(Long roomId, State state);
}