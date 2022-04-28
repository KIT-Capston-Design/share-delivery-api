package com.kitcd.share_delivery_api.domain.jpa.placeShare;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.common.Coordinate;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "PLACE_SHARE")
public class PlaceShare extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "PLACE_SHARE_ID", nullable = false)
   private Long placeShareId;

   @OneToOne
   @JoinColumn(name = "POST_ID", nullable = false)
   private Post post;

   @Column(name = "CONTENT", nullable = false)
   private String content;

   @Embedded
   private Coordinate coordinate;

   @Column(name = "ADDRESS", nullable = false)
   private String address;


}
