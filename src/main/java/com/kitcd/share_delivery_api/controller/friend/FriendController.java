package com.kitcd.share_delivery_api.controller.friend;


import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.friend.Friend;
import com.kitcd.share_delivery_api.dto.account.AccountProfileDTO;
import com.kitcd.share_delivery_api.service.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("")
    public ResponseEntity<?> getFriendList(@RequestParam(value = "type") FriendState status) {

        List<AccountProfileDTO> result = friendService.getFriendList(status);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<?> deleteFriend(@PathVariable Long accountId) {

        friendService.deleteFriend(accountId);

        return ResponseEntity.status(HttpStatus.OK).body(accountId);
    }
}
