package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.account.AccountRepository;
import com.kitcd.share_delivery_api.domain.jpa.account.BankAccount;
import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFile;
import com.kitcd.share_delivery_api.dto.account.AccountDTO;
import com.kitcd.share_delivery_api.dto.account.AccountModificationDTO;
import com.kitcd.share_delivery_api.dto.account.AccountProfileDTO;
import com.kitcd.share_delivery_api.dto.account.AccountRegistrationDTO;
import com.kitcd.share_delivery_api.service.AccountService;
import com.kitcd.share_delivery_api.service.ImageFileService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import org.hibernate.FetchNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
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

    public Account findByAccountId(Long accountId){
        Account account = accountRepository.findByAccountId(accountId);

        if(account == null) throw new FetchNotFoundException(Account.class.toString(), accountId);

        return account;
    }
    @Override
    public AccountProfileDTO getAccountProfile(Long accountId) {
        return findByAccountId(accountId).toAccountProfileDTO();
    }

    @Override
    public AccountDTO modifyMyAccountInformation(AccountModificationDTO dto, MultipartFile profileImage) {

        Account account = findByAccountId(ContextHolder.getAccountId());

        account.updateAccountInformation(dto, imageFileService.save(profileImage);

        return account.toDTO();
    }
}
