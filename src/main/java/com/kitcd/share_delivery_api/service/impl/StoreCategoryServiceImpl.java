package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategory;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategoryRepository;
import com.kitcd.share_delivery_api.service.StoreCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class StoreCategoryServiceImpl implements StoreCategoryService {

    private final StoreCategoryRepository storeCategoryRepository;

    @Override
    public List<String> findAllStoreCategoryOfName() {
        return storeCategoryRepository.findAll().stream().map(i -> i.getCategoryName()).collect(Collectors.toList());
    }

    @Override
    public StoreCategory findStoreCategoryWithName(String storeCategory){
        StoreCategory result = storeCategoryRepository.findByCategoryName(storeCategory);

        if(null == result){
            throw new EntityNotFoundException(StoreCategory.class + "Entity is Not Found with " + storeCategory);
        }

        return result;
    }
}
