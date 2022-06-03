package com.kitcd.share_delivery_api.domain.jpa.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("select f from Friend f " +
            "where (f.account.accountId = :myId and f.friendAccount.accountId = :targetId)" +
            "or (f.friendAccount.accountId = :myId and f.account.accountId = :targetId)")
    Friend getByAccountIds(Long myId, Long targetId);


//    @Query("select 1 from Account a where a.nickname = :nickName")

    @Query("select true from Friend f " +
            "where (f.account.accountId = :myId and f.friendAccount.accountId = :targetId and f.status = com.kitcd.share_delivery_api.domain.jpa.common.State.ACCEPTED)" +
            "or (f.friendAccount.accountId = :myId and f.account.accountId = :targetId) and f.status = com.kitcd.share_delivery_api.domain.jpa.common.State.ACCEPTED")
    Boolean isFriend(Long myId, Long targetId);
}
