package com.kitcd.share_delivery_api.domain.jpa.post;

import com.kitcd.share_delivery_api.dto.account.SimpleAccountDTO;
import com.kitcd.share_delivery_api.dto.post.PostListDTO;
import com.kitcd.share_delivery_api.utils.geometry.Direction;
import com.kitcd.share_delivery_api.utils.geometry.GeometryUtil;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Transactional
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<PostListDTO> findPostListDTOWithLocationAndPagingWithLastCreatedDateTime(Location location, Long radius, LocalDateTime lastCreatedDateTime) {
        if ( null == lastCreatedDateTime ){
            lastCreatedDateTime = LocalDateTime.now();
        }

        Location northEast = GeometryUtil.calculate(location, Math.sqrt(2) * radius, Direction.NORTHEAST.getAzimuth());
        Location southWest = GeometryUtil.calculate(location, Math.sqrt(2) * radius, Direction.SOUTHWEST.getAzimuth());

        // 여기서의 LINESTRING : northEast와 southWest를 잇는 선으로써 MBR(Minimum Bounding Rectangle)의 대각선이고
        // MBR 내 위치하는 DeliveryRoom을 조회

        String mbr =
                String.format("'LINESTRING(%f %f, %f %f)')",
                        northEast.getLongitude(), northEast.getLatitude(), southWest.getLongitude(), southWest.getLatitude());

        Query query = em.createNativeQuery("SELECT p.post_id, a.nickname, a.manner_score, a.account_id, p.content, pc.category_name, p.created_date"
                                                + " FROM ACCOUNT a JOIN POST p ON  p.USER_ID = a.ACCOUNT_ID JOIN POST_CATEGORY pc ON p.POST_CATEGORY_ID = pc.POST_CATEGORY_ID"
                                                + " WHERE MBRContains(ST_LINESTRINGFROMTEXT("+ mbr + ", p.location) AND p.created_date <= :endDate ORDER BY p.created_date DESC").setMaxResults(20);

        query.setParameter("endDate", localToTimestamp(lastCreatedDateTime));

        List<Object[]> result = query.getResultList();

        List<PostListDTO> postListDTOs =
                result.stream().map(
                        post -> PostListDTO.builder()
                                .postId(bigIntegerObjToLong(post[0]))
                                .writer(SimpleAccountDTO.builder()
                                        .nickname((String) post[1])
                                        .mannerScore((Double) post[2])
                                        .accountId(bigIntegerObjToLong(post[3]))
                                        .build())
                                .content((String)post[4])
                                .category((String)post[5])
                                .createdDateTime(((Timestamp)post[6]).toLocalDateTime()).build()
                ).collect(Collectors.toList());
        return postListDTOs;
    }

    private static Timestamp localToTimestamp (LocalDateTime localDateTime){
        return Timestamp.valueOf(localDateTime);
    }

    private static Long bigIntegerObjToLong(Object val){
        return ((BigInteger) val).longValue();
    }

    @Override
    public List<PostListDTO> findPostListDTOWithLocationAndPagingWithLastCreatedDateTimeAndFiltWithCategoryName(Location location, Long radius, LocalDateTime lastCreatedDateTime, String categoryName) {
        if ( null == lastCreatedDateTime ){
            lastCreatedDateTime = LocalDateTime.now();
        }

        Location northEast = GeometryUtil.calculate(location, Math.sqrt(2) * radius, Direction.NORTHEAST.getAzimuth());
        Location southWest = GeometryUtil.calculate(location, Math.sqrt(2) * radius, Direction.SOUTHWEST.getAzimuth());

        // 여기서의 LINESTRING : northEast와 southWest를 잇는 선으로써 MBR(Minimum Bounding Rectangle)의 대각선이고
        // MBR 내 위치하는 DeliveryRoom을 조회

        String mbr =
                String.format("'LINESTRING(%f %f, %f %f)')",
                        northEast.getLongitude(), northEast.getLatitude(), southWest.getLongitude(), southWest.getLatitude());

        Query query = em.createNativeQuery("SELECT p.post_id, a.nickname, a.manner_score, a.account_id, p.content, pc.category_name, p.created_date"
                + " FROM ACCOUNT a JOIN POST p ON  p.USER_ID = a.ACCOUNT_ID JOIN POST_CATEGORY pc ON p.POST_CATEGORY_ID = pc.POST_CATEGORY_ID"
                + " WHERE MBRContains(ST_LINESTRINGFROMTEXT("+ mbr + ", p.location) AND p.created_date <= :endDate AND pc.category_name = :categoryName ORDER BY p.created_date DESC").setMaxResults(20);

        query.setParameter("endDate", localToTimestamp(lastCreatedDateTime));
        query.setParameter("categoryName", categoryName);

        List<Object[]> result = query.getResultList();

        List<PostListDTO> postListDTOs =
                result.stream().map(
                        post -> PostListDTO.builder()
                                .postId(bigIntegerObjToLong(post[0]))
                                .writer(SimpleAccountDTO.builder()
                                        .nickname((String) post[1])
                                        .mannerScore((Double) post[2])
                                        .accountId(bigIntegerObjToLong(post[3]))
                                        .build())
                                .content((String)post[4])
                                .category((String)post[5])
                                .createdDateTime(((Timestamp)post[6]).toLocalDateTime()).build()
                ).collect(Collectors.toList());
        return postListDTOs;
    }

}
