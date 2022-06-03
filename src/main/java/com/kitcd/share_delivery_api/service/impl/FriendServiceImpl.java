package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.controller.friend.FriendState;
import com.kitcd.share_delivery_api.controller.friend.OperationType;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.friend.Friend;
import com.kitcd.share_delivery_api.domain.jpa.friend.FriendRepository;
import com.kitcd.share_delivery_api.dto.account.AccountProfileDTO;
import com.kitcd.share_delivery_api.service.AccountService;
import com.kitcd.share_delivery_api.service.FriendService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final AccountService accountService;

    @Override
    public void deleteAllMyFriend() {
        friendRepository.deleteAll(getAllEntitiesByAccountId(ContextHolder.getAccountId()));
    }


    @Override
    public List<Friend> getAllEntitiesByAccountId(Long accountId){
        return friendRepository.getAllEntitiesByAccountId(accountId);
    }

    @Override
    public Friend getByTargetAccountId(Long targetId){
        Friend friend = friendRepository.getByAccountIds(ContextHolder.getAccountId(), targetId);

        if(friend == null) throw new EntityNotFoundException("해당 유저와 친구 관계가 없습니다.");

        return friend;
    }

    @Override
    public boolean isFriend(Long targetId){
        return friendRepository.isFriend(ContextHolder.getAccountId(), targetId);
    }

    @Override
    public Friend friendRequest(Long targetId) {

        try {
            Friend friend = getByTargetAccountId(targetId);

            //기존 targetId의 유저와의  Friend 객체가 존재하는 경우
            State status = friend.getStatus();
            if(status == State.PENDING || status == State.ACCEPTED){
                //예외
                //  1. 이미 친구인 경우 == State.ACCEPTED
                //  2. 내가 이미 친구 신청 걸어둔 경우 == State.PENDING
                //  3. 상대방이 이미 친구 신청한 경우 == State.PENDING
                throw new IllegalStateException("친구 신청을 할 수 없습니다.");
            }
            //REJECTED, CANCELLED의 경우 기존 객체 삭제 후 다시 친구 신청
            friendRepository.delete(friend);

        } catch (EntityNotFoundException ignored){}

        //친구 신청 (친구 객체 생성 후 저장)
        return friendRepository.save(
                Friend.builder()
                        .firstAccount(ContextHolder.getAccount())
                        .secondAccount(accountService.findByAccountId(targetId))
                        .status(State.PENDING)
                        .build());
    }

    @Override
    public List<AccountProfileDTO> getFriendList(FriendState status) {

        Long myAccountId = ContextHolder.getAccountId();

        List<Friend> friendList;

        if(status.equals(FriendState.ACCEPTED)) // 현재 친구인 관계
            friendList = friendRepository.getAcceptedFriendList(myAccountId);
        else if(status.equals(FriendState.RECEIVED_PENDING_REQUEST)) // 친구 요청 받은 대기중인
            friendList = friendRepository.getReceivedPendingRequest(myAccountId);
        else if(status.equals(FriendState.SENT_PENDING_REQUEST)) // 친구 요청한 대기중인
            friendList = friendRepository.getSentPendingRequest(myAccountId);
        else
            throw new IllegalArgumentException("Illegal type");

        return friendList.stream()
                .map(f -> f.getFriendAccount(myAccountId))
                .map(Account::toAccountProfileDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFriend(Long accountId) {
        Friend friend = getByTargetAccountId(accountId);

        friendRepository.delete(friend);
    }

    @Override
    public State processFriendRequest(Long accountId, OperationType type) {

        Friend friend = getByTargetAccountId(accountId);

        State result;

        if(type.equals(OperationType.ACCEPT))
            result = friend.accept(accountId);
        else if(type.equals(OperationType.REJECT))
            result = friend.reject(accountId);
        else if(type.equals(OperationType.CANCEL))
            result = friend.cancel(accountId);
        else
            throw new IllegalStateException("Unknown Operation Type");

        return result;
    }
}
