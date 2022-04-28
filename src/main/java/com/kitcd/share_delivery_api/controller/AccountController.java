package com.kitcd.share_delivery_api.controller;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.dto.account.AccountRegDTO;
import com.kitcd.share_delivery_api.service.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountServiceImpl accountService;

    @PostMapping("")
    public ResponseEntity<?> insertAccount(@RequestBody AccountRegDTO dto) {

        Account accountDB = accountService.saveAccount(dto.toEntity());

        return new ResponseEntity<>(accountDB, HttpStatus.CREATED);
    }
}
