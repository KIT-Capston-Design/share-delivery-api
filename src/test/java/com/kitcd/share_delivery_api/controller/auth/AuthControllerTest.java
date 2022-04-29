package com.kitcd.share_delivery_api.controller.auth;


import com.kitcd.share_delivery_api.controller.common.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WithMockUser //spring security 인증, 인가 패스 위해
@WebAppConfiguration //web과 연관된 구성요소들(controller, view resolver, …)등 다루는 기능 제공
@AutoConfigureMockMvc //자동으로 MockMvc를 사용해서 테스트할 수 있게끔 설정
@ContextConfiguration(classes = {AuthController.class, GlobalExceptionHandler.class}) // 테스트를 위해 ApplicationContext 설정, 로드 방법 정의 사용되는 메타데이터 정의

@ExtendWith(SpringExtension.class) //  Spring Bean 테스트하기 위해 필요
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenValidPhoneNumber_whenRequestVerificationSMS_thenHttpOK() throws Exception {
        //given
        String url = "/api/auth/verification-sms";
        String phoneNumber = "01085762079";
        //when
        mvc.perform(get(url).param("phoneNumber", phoneNumber))
                //then
                .andExpect(status().isOk())
                .andExpect(content().string(phoneNumber));

    }

    @Test
    public void givenInvalidPhoneNumber_whenRequestVerificationSMS_thenBadRequest() throws Exception {
        //given
        String url = "/api/auth/verification-sms";
        String phoneNumber = "010857620799";
        //when
        mvc.perform(get(url).param("phoneNumber", phoneNumber))
                //then
                .andExpect(status().isBadRequest());

    }

}
