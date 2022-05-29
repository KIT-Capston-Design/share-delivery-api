package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomDTO;
import com.kitcd.share_delivery_api.dto.deliveryroom.ParticipatedDeliveryRoomDTO;
import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface DeliveryRoomService {
    DeliveryRoom deliveryRoomCreate(DeliveryRoom deliveryRoom, List<OrderMenuRequestDTO> menuList);

    DeliveryRoomDTO getDeliveryRoom(Long deliveryRoomId);
    List<DeliveryRoomDTO> getDeliveryRooms(Location location, Double distance);


    List<ParticipatedDeliveryRoomDTO> getDeliveryHistory(Long accountId);

    DeliveryRoom findByLeaderId(Long accountId);

    DeliveryRoom findByDeliveryRoomId(Long roomId);

    List<String> getParticipantFCMTokens(Long roomId);

    List<Long> getParticipantsIds(Long roomId);


    /**
     모집글 상태 OPEN -> WAITING_PAYMENT <br/>
     참여자 상태 변경 PENDING -> ACCEPTED
     **/
    DeliveryRoom closeRecruit(Long deliveryRoomId) throws JSONException, IOException;


}
