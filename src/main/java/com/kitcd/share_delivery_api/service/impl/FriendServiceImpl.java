package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.friend.Friend;
import com.kitcd.share_delivery_api.domain.jpa.friend.FriendRepository;
import com.kitcd.share_delivery_api.service.AccountService;
import com.kitcd.share_delivery_api.service.FriendService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final AccountService accountService;

    public Friend getByTargetAccountId(Long targetId){
        return friendRepository.getByAccountIds(ContextHolder.getAccountId(), targetId);
    }

    public boolean isFriend(Long targetId){
        return friendRepository.isFriend(ContextHolder.getAccountId(), targetId);
    }

    @Override
    public Friend friendRequest(Long targetId) {

        Friend friend = getByTargetAccountId(targetId);

        if(friend != null){
            State status = friend.getStatus();
            if(status == State.PENDING || status == State.ACCEPTED){
                //예외
                //  1. 이미 친구인 경우 == State.ACCEPTED
                //  2. 내가 이미 친구 신청 걸어둔 경우 == State.PENDING
                //  3. 상대방이 이미 친구 신청한 경우 == State.PENDING
                throw new IllegalStateException("친구 신청을 할 수 없습니다.");
            }
            //REJECTED, CANCELLED의 경우 기존 객체 삭제
            friendRepository.delete(friend);
        }

        //친구 객체 생성 후 저장
        return friendRepository.save(
                Friend.builder()
                        .account(ContextHolder.getAccount())
                        .friendAccount(accountService.findByAccountId(targetId))
                        .status(State.PENDING)
                        .build()
        );
    }
}
