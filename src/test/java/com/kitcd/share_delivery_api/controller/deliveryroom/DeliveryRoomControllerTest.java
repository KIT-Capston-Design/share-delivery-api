package com.kitcd.share_delivery_api.controller.deliveryroom;


import com.kitcd.share_delivery_api.controller.common.GlobalExceptionHandler;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
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

import javax.validation.constraints.NotBlank;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebAppConfiguration
@AutoConfigureMockMvc
@ContextConfiguration(classes = {DeliveryRoomController.class, GlobalExceptionHandler.class})

@ExtendWith(SpringExtension.class)
@WebMvcTest(DeliveryRoomController.class)
public class DeliveryRoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void name() throws Exception {
        //given
        String url = "/api/delivery-rooms";
        String latitude = "35.81934401";
        String longitude = "35.81934401";

        //when
        mvc.perform(get(url)
                        .param("latitude", latitude)
                        .param("longitude", longitude)
                )//then
                .andExpect(status().isOk());

    }

}
