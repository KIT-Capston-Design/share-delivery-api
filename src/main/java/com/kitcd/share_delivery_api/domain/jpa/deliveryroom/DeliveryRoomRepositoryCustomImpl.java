package com.kitcd.share_delivery_api.domain.jpa.deliveryroom;

import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomDTO;
import com.kitcd.share_delivery_api.utils.geometry.Direction;
import com.kitcd.share_delivery_api.utils.geometry.GeometryUtil;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Transactional
public class DeliveryRoomRepositoryCustomImpl implements DeliveryRoomRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private GeometryUtil geometryUtil;

    @Override
    public List<DeliveryRoomDTO> findDeliveryRoomDTOWithLocation(Location location, Double distance) {

        Location northEast = geometryUtil.calculate(location, distance, Direction.NORTHEAST.getAzimuth());
        Location southWest = geometryUtil.calculate(location, distance, Direction.SOUTHWEST.getAzimuth());

        // 여기서의 LINESTRING : northEast와 southWest를 잇는 선으로써 MBR(Minimum Bounding Rectangle)의 대각선이고
        // MBR 내 위치하는 DeliveryRoom을 조회

        String mbr =
                String.format("'LINESTRING(%f %f, %f %f)')",
                        northEast.getLatitude(), northEast.getLongitude(), southWest.getLatitude(), southWest.getLongitude());

        Query query = em.createNativeQuery("SELECT a.ACCOUNT_ID, r.CONTENT"
                        + "FROM DELIVERY_ROOM AS r LEFT JOIN ACCOUNT AS a ON r.LEADER_ID = a.ACCOUNT_ID"
                        + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + mbr + ", r.point)", DeliveryRoom.class).setMaxResults(10);

        List<Object[]> resultList = query.getResultList();

        List<DeliveryRoomDTO> deliveryRooms =
                resultList.stream().map(
                        room -> DeliveryRoomDTO.builder()
                                .leader(DeliveryRoomDTO.Leader.builder()
                                        .nickname((String) room[0])
                                        .mannerScore(36.5)
                                        .build())
                                .content((String) room[1])
//                                .person()
//                                .limitPerson()
//                                .storeLink()
//                                .platformType()
//                                .status()
//                                .createdDateTime()
//                                .limitDateTime()
//                                .receivingLocation()
                                .build()
                ).collect(Collectors.toList());

        return deliveryRooms;

    }
}
