package com.kitcd.share_delivery_api.utils.url;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.PlatformType;
import lombok.Getter;

/**
 * 고객이 공유하기로 복사한 전체 링크가 있으면 해당하는 링크를 parsing해서 가게 명을 얻는다.
 * 가게 명이 없으면, 해당하는 어플에 request해서 해당하는 가게명을 얻어온다.
*/

@Getter
public class UrlParserFindStoreName {
    private String storeName;
    private String url;

    private PlatformType platformType;

    public UrlParserFindStoreName(String copiedUrl, PlatformType platformType){
        int httpIndex = copiedUrl.indexOf("http");
        url = copiedUrl.substring(httpIndex);
        if (PlatformType.BAEMIN == platformType){
            storeName = findStoreNameWithBaemin(copiedUrl.substring(0, httpIndex)); //http 기준으로 앞뒤로 쪼개기.
        }

        else if(PlatformType.YOGIYO == platformType){
            storeName = findStoreNameWithYogiyo(copiedUrl.substring(0, httpIndex));
        }
    }

    private String findStoreNameWithBaemin(String subStringWithStoreName){
        String answer = subStringWithStoreName;
        answer = answer.replaceAll("'", "");
        answer = answer.replace(" 어때요? 배달의민족 앱에서 확인해보세요.","");

        if(subStringWithStoreName.length()==0){
            //탐색하러 가기.
        }



        return answer;
    }
    private String findStoreNameWithYogiyo(String subStringWithStoreName){
        String answer = subStringWithStoreName;
        answer = answer.replaceAll("'", "");
        answer = answer.replace(" 요기요 앱에서 확인해보세요.","");

        if(subStringWithStoreName.length()==0){
            //탐색하러 가기.
        }



        return answer;
    }
}
