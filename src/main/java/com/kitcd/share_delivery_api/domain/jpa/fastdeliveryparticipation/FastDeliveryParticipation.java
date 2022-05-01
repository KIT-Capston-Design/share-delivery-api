package com.kitcd.share_delivery_api.domain.jpa.fastdeliveryparticipation;

import com.kitcd.share_delivery_api.domain.jpa.common.BaseTimeEntity;

import com.kitcd.share_delivery_api.domain.jpa.fastdeliveryroom.FastDeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


//매칭은 레디스 통해 수행하고, 이 테이블은 기록 저장용으로 활용하는 것이 좋을 것 같다.
@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "FAST_DELIVERY_PARTICIPATION")
public class FastDeliveryParticipation extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAST_DELIVERY_PARTICIPATION_ID", nullable = false)
    private Long fastDeliveryParticipationId;

    @ManyToOne
    @JoinColumn(name = "FAST_DELIVERY_ROOM_ID", nullable = false)
    private FastDeliveryRoom fastDeliveryRoom;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;

}
