package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.account.AccountRepository;
import com.kitcd.share_delivery_api.domain.jpa.account.BankAccount;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.domain.jpa.friend.Friend;
import com.kitcd.share_delivery_api.dto.account.AccountDTO;
import com.kitcd.share_delivery_api.dto.account.AccountModificationDTO;
import com.kitcd.share_delivery_api.dto.account.AccountProfileDTO;
import com.kitcd.share_delivery_api.dto.account.AccountRegistrationDTO;
import com.kitcd.share_delivery_api.dto.deliveryroom.ParticipatedDeliveryRoomDTO;
import com.kitcd.share_delivery_api.service.AccountService;
import com.kitcd.share_delivery_api.service.FriendService;
import com.kitcd.share_delivery_api.service.ImageFileService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import org.hibernate.FetchNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final DeliveryRoomRepository deliveryRoomRepository;
    private final ImageFileService imageFileService;

    @Override
    public BankAccount getBankAccount(Long accountId) {

        BankAccount bankAccount = accountRepository.getBankAccountById(accountId);

        if(bankAccount == null) throw new FetchNotFoundException(BankAccount.class.toString(), accountId);

        return bankAccount;
    }


    @Override
    public Account signUp(AccountRegistrationDTO dto) {
        Account account = this.accountRepository.save(dto.toEntity());

        //닉네임이 null일 시 "유저{accountId}"형식으로 default 닉네임 설정 (추후 더 멋진 기본 닉네임 제공하도록 수정 필요)
        if(account.getNickname() == null){
            account.setDefaultNickname();
        }

        return account;
    }

    @Override
    public Account findByPhoneNumber(String phoneNumber) {
        return accountRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Account saveMyBankAccount(BankAccount bankAccount) {
        return accountRepository.save(ContextHolder.getAccount().saveBankAccount(bankAccount));
    }

    @Override
    public Account findByAccountId(Long accountId){
        Account account = accountRepository.findByAccountId(accountId);

        if(account == null || account.getStatus().equals(State.NORMAL)) throw new FetchNotFoundException(Account.class.toString(), accountId);

        return account;
    }
    @Override
    public AccountProfileDTO getAccountProfile(Long accountId) {
        return findByAccountId(accountId).toAccountProfileDTO();
    }

    @Override
    public Long deleteBankAccount(Long accountId) {
        Account account = findByAccountId(accountId);

        account.deleteBankAccount();

        return accountId;
    }


    @Override
    public AccountDTO modifyMyAccountInformation(AccountModificationDTO dto, MultipartFile profileImage) {

        Account account = findByAccountId(ContextHolder.getAccountId());

        account.updateAccountInformation(dto, imageFileService.save(profileImage));

        return account.toDTO();
    }

    @Override
    public boolean nickNameDuplicateCheck(String nickName) {

        //있으면 1, 없으면 null
        Integer result = accountRepository.nickNameDuplicateCheck(nickName);

        return !(result == null);
    }

    @Override
    public State deleteMyAccount() {

        Account myAccount = ContextHolder.getAccount();

        //활성화 되어있는 모집글 fetch
        List<ParticipatedDeliveryRoomDTO> dtos = deliveryRoomRepository.getParticipatingActivatedDeliveryRoom(myAccount.getAccountId());

        if(!dtos.isEmpty()) throw new IllegalStateException("참여하고 있는 활성화된 모집글이 있어 탈퇴할 수 없습니다.");

        State result = myAccount.withdraw();

        accountRepository.save(myAccount);

        return result;

    }

}
