package com.kitcd.share_delivery_api.controller.friend;


import com.kitcd.share_delivery_api.domain.jpa.friend.Friend;
import com.kitcd.share_delivery_api.service.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friends")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("")
    public ResponseEntity<?> friendRequest(@RequestBody Long accountId) {

        friendService.friendRequest(accountId);

        return ResponseEntity.status(HttpStatus.OK).body(accountId);
    }

}
