package com.kitcd.share_delivery_api.service;

import okhttp3.Response;

import java.io.IOException;

public interface FirebaseCloudMessageService {
    Response sendMessageTo(String targetToken, String title, String body) throws IOException;
}
