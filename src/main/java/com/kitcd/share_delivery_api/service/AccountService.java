package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.account.BankAccount;
import com.kitcd.share_delivery_api.dto.account.AccountDTO;
import com.kitcd.share_delivery_api.dto.account.AccountModificationDTO;
import com.kitcd.share_delivery_api.dto.account.AccountProfileDTO;
import com.kitcd.share_delivery_api.dto.account.AccountRegistrationDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {
    Account signUp(AccountRegistrationDTO dto);
    Account findByPhoneNumber(String phoneNumber);

    BankAccount getBankAccount(Long accountId);

    Account saveMyBankAccount(BankAccount toEntity);

    AccountProfileDTO getAccountProfile(Long accountId);

    AccountDTO modifyMyAccountInformation(AccountModificationDTO dto, MultipartFile profileImage);

    Long deleteBankAccount(Long accountId);
}
