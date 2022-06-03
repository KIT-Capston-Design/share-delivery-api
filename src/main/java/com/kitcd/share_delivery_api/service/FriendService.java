package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.controller.friend.FriendState;
import com.kitcd.share_delivery_api.domain.jpa.friend.Friend;
import com.kitcd.share_delivery_api.dto.account.AccountProfileDTO;

import java.util.List;

public interface FriendService {
    Friend friendRequest(Long accountId);

    List<AccountProfileDTO> getFriendList(FriendState status);
}
