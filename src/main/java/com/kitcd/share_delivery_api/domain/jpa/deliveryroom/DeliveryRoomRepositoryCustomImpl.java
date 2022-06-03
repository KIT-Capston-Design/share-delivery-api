package com.kitcd.share_delivery_api.domain.jpa.deliveryroom;

import com.kitcd.share_delivery_api.dto.account.SimpleAccountDTO;
import com.kitcd.share_delivery_api.dto.common.LocationDTO;
import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomDTO;
import com.kitcd.share_delivery_api.utils.geometry.Direction;
import com.kitcd.share_delivery_api.utils.geometry.GeometryUtil;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Transactional
public class DeliveryRoomRepositoryCustomImpl implements DeliveryRoomRepositoryCustom {

    @PersistenceContext
    private EntityManager em;
    @Override
    public List<DeliveryRoomDTO> findDeliveryRoomDTOWithLocation(Location location, Double radius) {

        Location northEast = GeometryUtil.calculate(location, Math.sqrt(2) * radius, Direction.NORTHEAST.getAzimuth());
        Location southWest = GeometryUtil.calculate(location, Math.sqrt(2) * radius, Direction.SOUTHWEST.getAzimuth());

        // 여기서의 LINESTRING : northEast와 southWest를 잇는 선으로써 MBR(Minimum Bounding Rectangle)의 대각선이고
        // MBR 내 위치하는 DeliveryRoom을 조회

        String mbr =
                String.format("'LINESTRING(%f %f, %f %f)')",
                        northEast.getLongitude(), northEast.getLatitude(), southWest.getLongitude(), southWest.getLatitude());

        Query query = em.createNativeQuery("SELECT a.account_id, a.nickname, a.manner_score, r.CONTENT, r.limit_person, r.store_link, r.status, r.created_date, r.link_platform_type, rl.description, rl.address, rl.latitude, rl.longitude, r.delivery_room_id, r.estimated_delivery_tip, sc.category_name"
                        + " FROM ACCOUNT a JOIN DELIVERY_ROOM r ON r.LEADER_ID = a.ACCOUNT_ID JOIN RECEIVING_LOCATION rl ON r.receiving_location_id = rl.receiving_location_id JOIN STORE_CATEGORY sc ON r.store_category_id = sc.store_category_id"
                        + " WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + mbr + ", rl.location)").setMaxResults(10);

        List<Object[]> resultList = query.getResultList();

        System.out.println(resultList.size());

        List<DeliveryRoomDTO> deliveryRooms =
                resultList.stream().map(
                        room -> DeliveryRoomDTO.builder()
                                .leader(SimpleAccountDTO.builder()
                                        .accountId(bigIntegerObjToLong(room[0]))
                                        .nickname((String) room[1])
                                        .mannerScore((Double) room[2])
                                        .build())
                                .content((String) room[3])
                                .limitPerson(((BigInteger)room[4]).longValue())
                                .storeLink((String)room[5])
                                .status(DeliveryRoomState.valueOf((String) room[6]))
                                .createdDateTime(((Timestamp) room[7]).toLocalDateTime())
                                .platformType( PlatformType.valueOf((String) room[8]))
                                .receivingLocation(LocationDTO.builder()
                                        .description((String) room[9])
                                        .address((String)room[10])
                                        .longitude((Double) room[12])
                                        .latitude((Double) room[11])
                                        .build())
                                .roomId(bigIntegerObjToLong(room[13]))
                                .deliveryTip(bigIntegerObjToLong(room[14]))
                                .storeCategory((String)room[15])
                                .build()
                ).collect(Collectors.toList());

            return deliveryRooms;

    }


    private static Long bigIntegerObjToLong(Object val){
        return ((BigInteger) val).longValue();
    }

}
