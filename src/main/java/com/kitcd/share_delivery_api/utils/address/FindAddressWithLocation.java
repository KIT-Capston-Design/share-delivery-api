package com.kitcd.share_delivery_api.utils.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Component
public class FindAddressWithLocation {

    //Value 어노테이션으로 application.yml에 참조 식으로 설정하려 했으나 ${open-api.kakao.rest-api-key}가 반환. 도움 부탁드립니다.
    @Value("033d34b2975f1102cc144098338a95ce") private String restApiKey;

    @Value("https://dapi.kakao.com/v2/local/geo/coord2address.json?input_coord=WGS84&") private String kakaoUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final RestTemplate restTemplate = new RestTemplate();

    //"경상북도 구미시 대학로 60" 형태로 반환.
    public String coordToAddr(Location location) {
        String lon = Double.toString(location.getLongitude());
        String lat = Double.toString(location.getLatitude());

        String url = kakaoUrl + "x=" + lon + "&y=" + lat;
        HttpEntity<?> entity = kakaoHeader();

        ResponseEntity<?> result =
                restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        String address = "";
        if (result.getStatusCode() == HttpStatus.OK && result.getBody() != null) {
            try {

                String response = objectMapper.writeValueAsString(result.getBody());
                address = getAddress(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return address;
    }
    // header 설정
    private HttpEntity<?> kakaoHeader() {
        HttpHeaders header = new HttpHeaders();
        // header.add("Authorization", "KaKaoAK RESTAPIKEY")
        header.add("Authorization", "KakaoAK "+restApiKey);
        return new HttpEntity<>(header);
    }

    // gson을 이용해 도로명 주소 or 주소 파싱
    private String getAddress(String json) throws Exception {
        String value = "";

        JsonObject addrResult = JsonParser.parseString(json).getAsJsonObject();
        JsonObject meta = addrResult.getAsJsonObject("meta");

        long totalCount = meta.get("total_count").getAsLong();

        if (totalCount <= 0) {
            return value;
        }

        JsonArray documents = addrResult.getAsJsonArray("documents");
        JsonObject addrObject = documents.get(0).getAsJsonObject();

        if (addrObject.get("road_address").isJsonNull()) {
            JsonObject address = addrObject.get("address").getAsJsonObject();
            value = address.get("address_name").getAsString();
            return value;
        }

        JsonObject roadAddress = addrObject.get("road_address").getAsJsonObject();
        value = roadAddress.get("address_name").getAsString();
        return value;
    }

}