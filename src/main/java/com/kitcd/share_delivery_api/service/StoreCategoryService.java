package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategory;

import java.util.List;

public interface StoreCategoryService {

    List<String> findAllStoreCategoryOfName();

    StoreCategory findStoreCategoryWithName(String storeCategory);
}
