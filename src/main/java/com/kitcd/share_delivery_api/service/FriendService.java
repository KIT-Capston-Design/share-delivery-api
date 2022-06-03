package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.controller.friend.FriendState;
import com.kitcd.share_delivery_api.controller.friend.OperationType;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.friend.Friend;
import com.kitcd.share_delivery_api.dto.account.AccountProfileDTO;

import java.util.List;

public interface FriendService {

    void deleteAllMyFriend();

    List<Friend> getAllEntitiesByAccountId(Long accountId);

    Friend getByTargetAccountId(Long targetId);

    boolean isFriend(Long targetId);

    Friend friendRequest(Long accountId);

    List<AccountProfileDTO> getFriendList(FriendState status);

    void deleteFriend(Long accountId);

    State processFriendRequest(Long accountId, OperationType type);
}
