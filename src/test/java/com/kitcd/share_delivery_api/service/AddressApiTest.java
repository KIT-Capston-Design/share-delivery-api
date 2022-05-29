package com.kitcd.share_delivery_api.service;


import com.kitcd.share_delivery_api.utils.address.FindAddressWithLocation;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@ContextConfiguration(classes = {FindAddressWithLocation.class})
@ExtendWith(SpringExtension.class)
public class AddressApiTest {

    @Autowired
    private FindAddressWithLocation findAddressWithLocation;

    @Test
    void locationToAddressTest(){
        Location location = new Location(36.14153983156746,128.396133049821); //금오공대 cu
        String address = findAddressWithLocation.coordToAddr(location);

        assertEquals("값이 일치하지 않음","경상북도 구미시 대학로 60", address);
    }
}
