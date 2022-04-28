package com.kitcd.share_delivery_api.domain.jpa.fastDeliveryTag;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "FAST_DELIVERY_TAG")
public class FastDeliveryTag extends BaseTimeEntity {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "FAST_DELIVERY_TAG_ID", nullable = false)
   private Long fastDeliveryTagId;

   @Column(name = "TAG_NAME", nullable = false)
   private String tagName;


}
