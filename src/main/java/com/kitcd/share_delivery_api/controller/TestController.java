package com.kitcd.share_delivery_api.controller;

import com.kitcd.share_delivery_api.domain.account.Account;
import com.kitcd.share_delivery_api.dto.Account.AccountRegDTO;
import com.kitcd.share_delivery_api.service.Account.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("")
    public ResponseEntity<?> test() {
        return new ResponseEntity<>("success!", HttpStatus.OK);
    }
}
