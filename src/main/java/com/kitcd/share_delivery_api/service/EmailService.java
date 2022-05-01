package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;

import org.springframework.scheduling.annotation.Async;

import javax.persistence.EntityExistsException;

public interface EmailService {
    @Async
    void sendEmail(String email) throws EntityExistsException ;

    boolean verificationEmail(String email, String randNum);

    Account AccountAddEmail(String phoneNum, String email) throws EntityExistsException;
}
