package com.kitcd.share_delivery_api.domain.jpa.friend;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("select f from Friend f " +
            "where f.firstAccount.accountId = :myId or f.secondAccount.accountId = :myId")
    List<Friend> getAllEntitiesByAccountId(Long myId);


    @Query("select f from Friend f " +
            "where (f.firstAccount.accountId = :myId and f.secondAccount.accountId = :targetId)" +
            "or (f.secondAccount.accountId = :myId and f.firstAccount.accountId = :targetId)")
    Friend getByAccountIds(Long myId, Long targetId);

    @Query("select true from Friend f " +
            "where (f.firstAccount.accountId = :myId and f.secondAccount.accountId = :targetId and f.status = com.kitcd.share_delivery_api.domain.jpa.common.State.ACCEPTED)" +
            "or (f.secondAccount.accountId = :myId and f.firstAccount.accountId = :targetId) and f.status = com.kitcd.share_delivery_api.domain.jpa.common.State.ACCEPTED")
    Boolean isFriend(Long myId, Long targetId);

    Friend findByFriendId(Long friendId);


    @Query("select f from Friend f " +
            "where (f.firstAccount.accountId = :myAccountId or f.secondAccount.accountId = :myAccountId) " +
            "and f.status = com.kitcd.share_delivery_api.domain.jpa.common.State.ACCEPTED")
    List<Friend> getAcceptedFriendList(Long myAccountId);

    @Query("select f from Friend f " +
            "where f.secondAccount.accountId = :myAccountId " +
            "and f.status = com.kitcd.share_delivery_api.domain.jpa.common.State.PENDING")
    List<Friend> getReceivedPendingRequest(Long myAccountId);

    @Query("select f from Friend f " +
            "where f.firstAccount.accountId = :myAccountId " +
            "and f.status = com.kitcd.share_delivery_api.domain.jpa.common.State.PENDING")
    List<Friend> getSentPendingRequest(Long myAccountId);
}
