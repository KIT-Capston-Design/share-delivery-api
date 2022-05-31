package com.kitcd.share_delivery_api.domain.jpa.postcategory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
    PostCategory findByCategoryName(String categoryName);
}
