package com.kitcd.share_delivery_api.controller.account;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.account.BankAccount;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.redis.auth.verificationsms.VerificationType;
import com.kitcd.share_delivery_api.dto.account.*;
import com.kitcd.share_delivery_api.service.AuthService;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import com.kitcd.share_delivery_api.service.FriendService;
import com.kitcd.share_delivery_api.service.impl.AccountServiceImpl;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    @Value("${open-api.naver-sms.activate}") private Boolean smsIsActivated;
    private final AccountServiceImpl accountService;
    private final AuthService authService;
    private final FriendService friendService;


    /**
     *  활성화 된 게시글 없을 경우 회원 탈퇴 진행
     *  현재 친구 삭제 시 DB에서 완전삭제 되나 이는 문제의 소지가 있으므로 추후 개선필요
     **/
    @DeleteMapping("")
    public ResponseEntity<?> deleteMyAccount(){

        State result = accountService.deleteMyAccount();

        //모든 친구 삭제
        friendService.deleteAllMyFriend();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("")
    public ResponseEntity<?> modifyMyAccountInformation(
            @RequestPart(value = "accountDetail") AccountModificationDTO dto,
            @RequestParam(required = false) MultipartFile profileImage){

        AccountDTO accountDTO = accountService.modifyMyAccountInformation(dto, profileImage);

        return ResponseEntity.status(HttpStatus.OK).body(accountDTO);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccountProfile(@PathVariable Long accountId) {

        AccountProfileDTO accountProfile = accountService.getAccountProfile(accountId);

        return ResponseEntity.status(HttpStatus.OK).body(accountProfile);
    }

    @PostMapping("")
    public ResponseEntity<?> signUp(@Validated @RequestBody AccountRegistrationDTO dto) {

        // 휴대폰 인증 성공 시 DB에 계정 데이터 저장 후 저장된 계정 정보 반환
        //  휴대폰 인증 비활성화 되어 있을 경우 바로 회원가입 진행
        if (!smsIsActivated || authService.verifyCode(dto.getPhoneNumber(), dto.getVerificationCode(), VerificationType.SIGNUP)){

            Account account = accountService.signUp(dto);

            return new ResponseEntity<>(account, HttpStatus.CREATED);

        } else { // 실패 시 401

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Verification Failed");
        }

    }

    @PostMapping("/bank-account")
    public ResponseEntity<?> enrollMyBankAccount(BankAccountDTO bankAccountDTO) {

        Account account = accountService.saveMyBankAccount(bankAccountDTO.toEntity());

        return ResponseEntity.status(HttpStatus.OK).body(account.toDTO());

    }

    @GetMapping("/bank-account")
    public ResponseEntity<?> getMyBankAccount() {

        BankAccount bankAccount = accountService.getBankAccount(ContextHolder.getAccountId());

        return ResponseEntity.status(HttpStatus.OK).body(bankAccount.toDTO());

    }

    @DeleteMapping("/bank-account")
    public ResponseEntity<?> deleteMyBankAccount() {

        accountService.deleteBankAccount(ContextHolder.getAccountId());

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/nick-name-dp-check/{nickName}")
    public ResponseEntity<?> nickNameDuplicateCheck(@PathVariable String nickName) {

        boolean result = accountService.nickNameDuplicateCheck(nickName);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
