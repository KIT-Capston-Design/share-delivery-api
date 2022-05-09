package com.kitcd.share_delivery_api.controller.storecategory;


import com.kitcd.share_delivery_api.service.StoreCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreCategoryController {
    private final StoreCategoryService storeCategoryService;

    @GetMapping("/store-category")
    public ResponseEntity<?> findAllStoreCategoryList(){
        return ResponseEntity.status(HttpStatus.OK).body(storeCategoryService.findAllStoreCategoryOfName());
    }
}
