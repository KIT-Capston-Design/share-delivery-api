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

        // 여기서의 LINESTRING : MBR(Minimum Bounding Rectangle)의 대각선
        String mbr =
                String.format("'LINESTRING(%f %f, %f %f)')",
                        northEast.getLatitude(), northEast.getLongitude(), southWest.getLatitude(), southWest.getLongitude());

        Query query = em.createNativeQuery("SELECT "
                        + "FROM DELIVERY_ROOM AS r LEFT JOIN ACCOUNT AS a ON r.LEADER_ID = a.ACCOUNT_ID"
                        + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + mbr + ", r.point)", DeliveryRoom.class)
                .setMaxResults(10);

        List<DeliveryRoom> deliveryRooms = query.getResultList();



        return null;

    }
}
