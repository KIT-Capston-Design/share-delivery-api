package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.friend.Friend;

public interface FriendService {
    Friend friendRequest(Long accountId);
}
