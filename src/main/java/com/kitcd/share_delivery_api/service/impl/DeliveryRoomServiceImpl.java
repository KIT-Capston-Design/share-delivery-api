package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomDTO;
import com.kitcd.share_delivery_api.dto.deliveryroom.ParticipatedDeliveryRoomDTO;
import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import com.kitcd.share_delivery_api.service.LoggedOnInformationService;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.service.EntryOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryRoomServiceImpl implements DeliveryRoomService {

    private final DeliveryRoomRepository deliveryRoomRepository;
    private final EntryOrderService entryOrderService;
    private final LoggedOnInformationService loggedOnInformationService;


    public List<DeliveryRoomDTO> getDeliveryRooms(Location location, Double distance){
        return deliveryRoomRepository.findDeliveryRoomDTOWithLocation(location, distance);
    }

    @Override
    public List<ParticipatedDeliveryRoomDTO> getDeliveryHistory(Long accountId) {
        return deliveryRoomRepository.getDeliveryHistory(accountId);
    }

    @Override
    public DeliveryRoom findByLeaderId(Long accountId) {

        DeliveryRoom deliveryRoom = deliveryRoomRepository.findByLeader_AccountId(accountId);

        if(deliveryRoom == null) throw new EntityNotFoundException(DeliveryRoom.class.toString());

        return deliveryRoom;
    }

    @Override
    public DeliveryRoom findByDeliveryRoomId(Long roomId) {
        DeliveryRoom deliveryRoom = deliveryRoomRepository.findByDeliveryRoomId(roomId);

        if(deliveryRoom == null) throw new EntityNotFoundException(DeliveryRoom.class.toString());

        return deliveryRoom;
    }



    @Override
    public DeliveryRoom deliveryRoomCreate(DeliveryRoom deliveryRoom, List<OrderMenuRequestDTO> menuList) {

        DeliveryRoom room = deliveryRoomRepository.save(deliveryRoom);

        entryOrderService.enrollEntryOrder(room, menuList, EntryOrderType.LEAD, State.ACCEPTED);

        return room;
    }

    @Override
    public List<String> getParticipantFCMTokens(Long roomId){

        List<Long> participantsIds = getParticipantsIds(roomId);

        return participantsIds.stream()
                .map(loggedOnInformationService::getFcmTokenByAccountId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getParticipantsIds(Long roomId){

        List<Long> participantsIds = deliveryRoomRepository.getParticipantsIds(roomId);

        if(participantsIds == null) throw new IllegalArgumentException("해당 delivery room id에 대한 참여자 ids 가져오기 실패");

        return participantsIds;
    }

    @Override
    public DeliveryRoom closeRecruit(Long deliveryRoomId) {

        // 모집글 상태 변경
        DeliveryRoom deliveryRoom = findByDeliveryRoomId(deliveryRoomId);
        deliveryRoom.closeRecruit();


        // 참여자 상태 변경 Pending -> Accepted
        entryOrderService.acceptOrders(deliveryRoomId);

        return deliveryRoom;
    }
}
