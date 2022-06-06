package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomDTO;
import com.kitcd.share_delivery_api.dto.deliveryroom.ParticipatedDeliveryRoomDTO;
import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;
import com.kitcd.share_delivery_api.dto.payment.FinalOrderInformationDTO;
import com.kitcd.share_delivery_api.utils.geometry.Location;

import java.util.List;

public interface DeliveryRoomService {

    List<ParticipatedDeliveryRoomDTO> getParticipatingActivatedDeliveryRoom(Long accountId);
    DeliveryRoom deliveryRoomCreate(DeliveryRoom deliveryRoom, List<OrderMenuRequestDTO> menuList);

    DeliveryRoomDTO getDeliveryRoomDTO(Long deliveryRoomId);
    List<DeliveryRoomDTO> getDeliveryRooms(Location location, Double distance);


    List<ParticipatedDeliveryRoomDTO> getDeliveryHistory(Long accountId);

    DeliveryRoom findByLeaderId(Long accountId);

    DeliveryRoom findByDeliveryRoomId(Long roomId);

    List<String> getParticipantFCMTokens(Long roomId, State state);

    // state : 참여자의 주문 처리 상태에 따라 조회 ex) ACCEPTED, PENDING
    List<Long> getParticipantsIds(Long roomId, State state);

    /**
     모집글 상태 OPEN -> WAITING_PAYMENT <br/>
     참여자 상태 변경 PENDING -> ACCEPTED
     **/
    DeliveryRoom closeRecruit(Long deliveryRoomId);


     Long deleteDeliveryRoom(Long deliveryRoom);

    Long exitDeliveryRoom(Long deliveryRoomId);

    FinalOrderInformationDTO getFinalOrderInformation(Long deliveryRoomId);

    DeliveryRoomState deliveryComplete(Long deliveryRoomId);
}
