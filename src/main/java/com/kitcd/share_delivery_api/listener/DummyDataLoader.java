package com.kitcd.share_delivery_api.listener;

import com.kitcd.share_delivery_api.domain.jpa.account.*;
import com.kitcd.share_delivery_api.domain.jpa.comment.Comment;
import com.kitcd.share_delivery_api.domain.jpa.comment.CommentRepository;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.PlatformType;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderRepository;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.domain.jpa.evaluationcategory.EvaluationCategory;
import com.kitcd.share_delivery_api.domain.jpa.evaluationcategory.EvaluationCategoryRepository;
import com.kitcd.share_delivery_api.domain.jpa.friend.Friend;
import com.kitcd.share_delivery_api.domain.jpa.friend.FriendRepository;
import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFile;
import com.kitcd.share_delivery_api.domain.jpa.imagefile.ImageFileRepository;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenuRepository;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.payment.PaymentRepository;
import com.kitcd.share_delivery_api.domain.jpa.paymentdiscount.PaymentDiscount;
import com.kitcd.share_delivery_api.domain.jpa.paymentdiscount.PaymentDiscountRepository;
import com.kitcd.share_delivery_api.domain.jpa.paymentorderform.PaymentOrderForm;
import com.kitcd.share_delivery_api.domain.jpa.paymentorderform.PaymentOrderFormRepository;
import com.kitcd.share_delivery_api.domain.jpa.placeshare.PlaceShare;
import com.kitcd.share_delivery_api.domain.jpa.placeshare.PlaceShareRepository;
import com.kitcd.share_delivery_api.domain.jpa.post.Post;
import com.kitcd.share_delivery_api.domain.jpa.post.PostRepository;
import com.kitcd.share_delivery_api.domain.jpa.postcategory.PostCategory;
import com.kitcd.share_delivery_api.domain.jpa.postcategory.PostCategoryRepository;
import com.kitcd.share_delivery_api.domain.jpa.postimage.PostImage;
import com.kitcd.share_delivery_api.domain.jpa.postimage.PostImageRepository;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocationRepository;
import com.kitcd.share_delivery_api.domain.jpa.remittance.Remittance;
import com.kitcd.share_delivery_api.domain.jpa.remittance.RemittanceRepository;
import com.kitcd.share_delivery_api.domain.jpa.reportcategory.ReportCategory;
import com.kitcd.share_delivery_api.domain.jpa.reportcategory.ReportCategoryRepository;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategory;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategoryRepository;
import com.kitcd.share_delivery_api.domain.redis.auth.loggedoninf.LoggedOnInformation;
import com.kitcd.share_delivery_api.service.AuthService;
import com.kitcd.share_delivery_api.utils.geometry.GeometriesFactory;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@ConfigurationProperties(prefix = "property.test")
@Component
@AllArgsConstructor
@Transactional
public class DummyDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final StoreCategoryRepository storeCategoryRepository;
    private final DeliveryRoomRepository deliveryRoomRepository;
    private final EntryOrderRepository entryOrderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final ReceivingLocationRepository receivingLocationRepository;
    private final AccountRepository accountRepository;
    private final AuthService authService;
    private final PostCategoryRepository postCategoryRepository;
    private final FriendRepository friendRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentOrderFormRepository paymentOrderFormRepository;
    private final PaymentDiscountRepository paymentDiscountRepository;
    private final PostRepository postRepository;
    private final ImageFileRepository imageFileRepository;
    private final PostImageRepository postImageRepository;
    private final PlaceShareRepository placeShareRepository;
    private final CommentRepository commentRepository;
    private final RemittanceRepository remittanceRepository;
    private final EvaluationCategoryRepository evaluationCategoryRepository;
    private final ReportCategoryRepository reportCategoryRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadImageFileData();
        loadAccountData();
        loadStoreCategoryData();
        loadReceivingLocationData();
        loadDeliveryRoomData();
        loadEntryOrderData();
        loadOrderMenuData();
        loadPostCategory();
        loadFriendData();
        loadPaymentData();
        loadPaymentDiscountData();
        loadPaymentOrderFormData();
        loadPostData();
        loadPlaceShareData();
        loadCommentData();
        loadRemittanceData();
        loadEvaluationCategory();
        loadReportCategory();
    }

    private void loadRemittanceData(){
        //3번 방
        createRemittanceIfNotFound(1L, 4L, 3L, 1L, 998800L, false);
        createRemittanceIfNotFound(2L, 5L, 3L, 1L, 998800L, false);
        createRemittanceIfNotFound(3L, 6L, 3L, 1L, 998800L, false);


        //4번 방
        createRemittanceIfNotFound(4L, 7L, 4L, 2L, 998800L, false);
        createRemittanceIfNotFound(5L, 8L, 4L, 2L, 998800L, false);
        createRemittanceIfNotFound(6L, 9L, 4L, 2L, 998800L, false);

        //5번 방
        createRemittanceIfNotFound(7L, 10L, 5L, 3L, 998800L, true);
        createRemittanceIfNotFound(8L, 11L, 5L, 3L, 998800L, true);
        createRemittanceIfNotFound(9L, 12L, 5L, 3L, 998800L, true);

    }

    private Remittance createRemittanceIfNotFound(Long remittanceId, Long remitterId, Long recipientId, Long paymentId, Long amount, boolean isRemitted) {

        Optional<Remittance> opt = remittanceRepository.findById(remittanceId);

        if(opt.isPresent()){
            return opt.get();
        }


        // 연관관계 위한 필요 객체 확인
        Optional<Account> remitter = accountRepository.findById(remitterId);
        Optional<Account> recipient = accountRepository.findById(recipientId);

        Optional<Payment> payment = paymentRepository.findById(paymentId);

        if(remitter.isEmpty() || recipient.isEmpty() || payment.isEmpty()){
            log.warn("DummyDataLoader.createRemittanceIfNotFound() : 필요 객체 null");
            log.warn("  remitter = " + remitter);
            log.warn("  recipient = " + recipient);
            log.warn("  payment = " + payment);
            return null;
        }
        return remittanceRepository.save(
                    Remittance.builder()
                        .remitter(remitter.get())
                        .recipient(recipient.get())
                        .payment(payment.get())
                        .amount(amount)
                        .isRemitted(isRemitted)
                        .build()
                );

    }

    private void loadPaymentData(){
        createPaymentIfNotFound(1L, 3L, 15000L);
        createPaymentIfNotFound(2L, 4L, 15000L);
        createPaymentIfNotFound(3L, 5L, 15000L);
    }

    private void loadPaymentDiscountData(){
        createPaymentDiscountIfNotFound(1L, 1L, "DUMMY_DISCOUNT_NAME 1", 10000L);
        createPaymentDiscountIfNotFound(2L, 1L, "DUMMY_DISCOUNT_NAME 2", 10000L);

        createPaymentDiscountIfNotFound(3L, 2L, "DUMMY_DISCOUNT_NAME 3", 10000L);
        createPaymentDiscountIfNotFound(4L, 2L, "DUMMY_DISCOUNT_NAME 4", 10000L);

        createPaymentDiscountIfNotFound(5L, 3L, "DUMMY_DISCOUNT_NAME 5", 10000L);
        createPaymentDiscountIfNotFound(6L, 3L, "DUMMY_DISCOUNT_NAME 6", 10000L);

    }

    private void loadCommentData(){
        createCommentIfNotFound(1L, 1L, null, 1L, "DUMMY_CONTENT_1");
        createCommentIfNotFound(2L, 1L, 1L, 1L, "DUMMY_CONTENT_2");
        createCommentIfNotFound(3L, 2L, 2L, 1L, "DUMMY_CONTENT_3");
        createCommentIfNotFound(4L, 1L, null, 2L, "DUMMY_CONTENT_1");

    }

    private void loadImageFileData(){
        createImageFileIfNotFound(1L, "DUMMYIMAGE_1", "DUMMYIMAGE_1","png", "images/", 0d);
        createImageFileIfNotFound(2L, "DUMMYIMAGE_2", "DUMMYIMAGE_2","png", "images/", 0d);
        createImageFileIfNotFound(3L, "DUMMYIMAGE_3", "DUMMYIMAGE_3","png", "images/", 0d);
        createImageFileIfNotFound(4L, "DUMMYIMAGE_4", "DUMMYIMAGE_4","png", "images/", 0d);
        createImageFileIfNotFound(5L, "DUMMYIMAGE_5", "DUMMYIMAGE_5","png", "images/", 0d);
        createImageFileIfNotFound(6L, "DUMMYIMAGE_6", "DUMMYIMAGE_6","png", "images/", 0d);
    }
    private void loadPaymentOrderFormData(){
        createPaymentOrderFormIfNotFound(1L,1L, 1L);
    }

    private void loadPostData(){
        Location location = new Location(36.14153983156746,128.396133049821);
        String address = "경북 구미시 대학로 60";
        createPostIfNotFound(1L, 1L, 1L, "DUMMY_CONTENT1", State.NORMAL, location, address);
        createPostIfNotFound(2L, 2L, 2L, "DUMMY_CONTENT2", State.NORMAL, location, address);
        createPostIfNotFound(3L, 3L, 3L, "DUMMY_CONTENT3", State.NORMAL, location, address);
    }

    private void loadPostImageData(){
        createPostImageFileIfNotFound(1L, 1L, 2L);
        createPostImageFileIfNotFound(2L, 1L, 2L);
        createPostImageFileIfNotFound(3L, 1L, 2L);
    }

    private void loadPlaceShareData(){
        Location location = new Location(36.137733124211955,128.39724317877784);
        createPlaceShareIfNotFound(1L, 1L, "DUMMY_CONTENT1", location, "DUMMY_ADDRESS_1");
        createPlaceShareIfNotFound(1L, 2L, "DUMMY_CONTENT1", location, "DUMMY_ADDRESS_2");
    }

    private void loadFriendData(){
        createFriendIfNotFound(1L, 1L, 2L, State.PENDING);
        createFriendIfNotFound(2L, 1L, 3L, State.ACCEPTED);
        createFriendIfNotFound(3L, 1L, 4L, State.REJECTED);
        createFriendIfNotFound(4L, 2L, 3L, State.PENDING);
    }

    private void loadStoreCategoryData(){
        List<String> categoryNames = new ArrayList<>();
        //"CHICKEN", "PIZZA", "CHINA", "JAPAN", "BOONSIK", "KOREA", "FASTFOOD", "LATE_NIGHT", "LUNCHBOX", "STEAM_AND_SOUP"
        createStoreCategoryIfNotFound("CHICKEN");
        createStoreCategoryIfNotFound("PIZZA");
        createStoreCategoryIfNotFound("CHINA");
        createStoreCategoryIfNotFound("JAPAN");
        createStoreCategoryIfNotFound("BOONSIK");
        createStoreCategoryIfNotFound("KOREA");
        createStoreCategoryIfNotFound("FASTFOOD");
        createStoreCategoryIfNotFound("LATE_NIGHT");
        createStoreCategoryIfNotFound("LUNCHBOX");
        createStoreCategoryIfNotFound("STEAM_AND_SOUP");
        createStoreCategoryIfNotFound("DESERT");
    }

    private void loadAccountData(){
        createAccountDataIfNotFound(1L,"01000000001", "DUMMY USER 1", RoleType.ROLE_USER, 1L, BankType.SHB);
        createAccountDataIfNotFound(2L, "01000000002", "DUMMY USER 2", RoleType.ROLE_USER, 2L, BankType.NONGHYUP);
        createAccountDataIfNotFound(3L, "01000000003", "DUMMY USER 3", RoleType.ROLE_USER, 3L, BankType.KAKAO);
        createAccountDataIfNotFound(4L,"01000000004", "DUMMY USER 4", RoleType.ROLE_USER, 4L, BankType.SUHYUP);
        createAccountDataIfNotFound(5L,"01000000005", "DUMMY USER 5", RoleType.ROLE_USER, 5L, BankType.EPOST);
        createAccountDataIfNotFound(6L,"01000000006", "DUMMY USER 6", RoleType.ROLE_USER, 6L, BankType.HANA);
        createAccountDataIfNotFound(7L,"01000000007", "DUMMY USER 7", RoleType.ROLE_USER, 1L, BankType.KAKAO);
        createAccountDataIfNotFound(8L,"01000000008", "DUMMY USER 8", RoleType.ROLE_USER, 2L, BankType.KAKAO);
        createAccountDataIfNotFound(9L,"01000000009", "DUMMY USER 9", RoleType.ROLE_USER, 3L, BankType.DGB);
        createAccountDataIfNotFound(10L,"010000000010", "DUMMY USER 10", RoleType.ROLE_USER, 4L, BankType.WOORI);
        createAccountDataIfNotFound(11L,"01000000011", "DUMMY USER 11", RoleType.ROLE_USER, 5L, BankType.WOORI);
        createAccountDataIfNotFound(12L,"01000000012", "DUMMY USER 12", RoleType.ROLE_USER, 6L, BankType.EPOST);
        createAccountDataIfNotFound(13L,"01000000013", "DUMMY USER 13", RoleType.ROLE_USER, 1L, BankType.JBB);
        createAccountDataIfNotFound(14L,"01000000014", "DUMMY USER 14", RoleType.ROLE_USER, 2L, BankType.IBK);
    }

    private void loadReceivingLocationData(){

        createReceivingLocationIfNotFound(1L,36.14153983156746,128.396133049821,"CU 앞","경북 구미시 대학로 60", false, 1L);
        createReceivingLocationIfNotFound(2L,36.137733124211955,128.39724317877784,"미니스톱 앞","경북 구미시 대학로 19", true, 1L);
        createReceivingLocationIfNotFound(3L,36.146051458091804,128.3925243410308 ,"디지털관","경북 구미시 대학로 61", false, 1L);
        createReceivingLocationIfNotFound(4L,36.14530838182648,128.39365557083093,"쿱스켓 앞","경북 구미시 대학로 61", false, 3L);
        createReceivingLocationIfNotFound(5L,36.14673898697836,128.39423085506434,"테크노관","경북 구미시 대학로 61", false, 4L);
        createReceivingLocationIfNotFound(6L,36.14675821967368, 128.38908700732264, "오름 3동", "경북 구미시 대학로 61", false, 4L);
    }

    private void loadDeliveryRoomData(){
        createDeliveryRoomIfNotFound(1L, "(WAITING_PAYMENT) DUMMY CONTENT 1 ", 4L,4L, 123456L,1L, "CHICKEN", 1L, DeliveryRoomState.WAITING_PAYMENT, PlatformType.BAEMIN, "DUMMY STORE NAME 0", "https://baemin.me/JsDPOYYqUd");
        createDeliveryRoomIfNotFound(2L, "(OPEN) DUMMY CONTENT 2 ", 3L,4L, 123456L,2L, "DESERT", 2L, DeliveryRoomState.OPEN, PlatformType.BAEMIN,"DUMMY STORE NAME 1", "https://baemin.me/jpaPFsg-B");
        createDeliveryRoomIfNotFound(3L, "(WAITING_DELIVERY) DUMMY CONTENT 3 ", 4L,4L, 123456L,3L, "FASTFOOD", 3L, DeliveryRoomState.WAITING_DELIVERY, PlatformType.BAEMIN, "DUMMY STORE NAME 2", "https://baemin.me/gzJ_2H5-o");
        createDeliveryRoomIfNotFound(4L, "(WAITING_REMITTANCE) DUMMY CONTENT 4 ", 4L,4L, 123456L,1L, "LUNCHBOX", 4L, DeliveryRoomState.WAITING_REMITTANCE, PlatformType.YOGIYO, "DUMMY STORE NAME 3", "https://yogiyo.onelink.me/BlI7/im8nou2o");
        createDeliveryRoomIfNotFound(5L, "(COMPLETED) DUMMY CONTENT 5 ", 4L,4L, 123456L,2L, "LUNCHBOX", 5L, DeliveryRoomState.COMPLETED, PlatformType.YOGIYO, "DUMMY STORE NAME 4", "https://yogiyo.onelink.me/BlI7/im8nou2o");
        createDeliveryRoomIfNotFound(6L, "(OPEN) DUMMY CONTENT 6 ", 1L,4L, 123456L,2L, "DESERT", 6L, DeliveryRoomState.OPEN, PlatformType.BAEMIN,"DUMMY STORE NAME 1", "https://baemin.me/jpaPFsg-B");

    }

    private void loadEntryOrderData(){

        //LEAD
        createEntryOrderIfNotFound(1L, 1L, 1L, EntryOrderType.LEAD, State.ACCEPTED);
        createEntryOrderIfNotFound(2L, 2L, 2L, EntryOrderType.LEAD, State.ACCEPTED);
        createEntryOrderIfNotFound(3L, 3L, 3L, EntryOrderType.LEAD, State.ACCEPTED);
        createEntryOrderIfNotFound(4L, 4L, 4L, EntryOrderType.LEAD, State.ACCEPTED);


        //1번 방 PARTICIPATION
        createEntryOrderIfNotFound(5L, 2L, 1L, EntryOrderType.PARTICIPATION, State.ACCEPTED);
        createEntryOrderIfNotFound(6L, 3L, 1L, EntryOrderType.PARTICIPATION, State.ACCEPTED);
        createEntryOrderIfNotFound(7L, 4L, 1L, EntryOrderType.PARTICIPATION, State.ACCEPTED);

        //3번 방 PARTICIPATION
        createEntryOrderIfNotFound(8L, 4L, 3L, EntryOrderType.PARTICIPATION, State.ACCEPTED);
        createEntryOrderIfNotFound(9L, 5L, 3L, EntryOrderType.PARTICIPATION, State.ACCEPTED);
        createEntryOrderIfNotFound(10L, 6L, 3L, EntryOrderType.PARTICIPATION, State.ACCEPTED);


        //4번 방 PARTICIPATION
        createEntryOrderIfNotFound(11L, 7L, 4L, EntryOrderType.PARTICIPATION, State.ACCEPTED);
        createEntryOrderIfNotFound(12L, 8L, 4L, EntryOrderType.PARTICIPATION, State.ACCEPTED);
        createEntryOrderIfNotFound(13L, 9L, 4L, EntryOrderType.PARTICIPATION, State.ACCEPTED);

        //5번 방 PARTICIPATION
        createEntryOrderIfNotFound(14L, 10L, 5L, EntryOrderType.PARTICIPATION, State.ACCEPTED);
        createEntryOrderIfNotFound(15L, 11L, 5L, EntryOrderType.PARTICIPATION, State.ACCEPTED);
        createEntryOrderIfNotFound(16L, 12L, 5L, EntryOrderType.PARTICIPATION, State.ACCEPTED);

        createEntryOrderIfNotFound(17L, 5L, 5L, EntryOrderType.LEAD, State.ACCEPTED);
        createEntryOrderIfNotFound(18L, 6L, 6L, EntryOrderType.LEAD, State.ACCEPTED);

        //2번 방 참여
        createEntryOrderIfNotFound(19L, 13L, 2L, EntryOrderType.PARTICIPATION, State.ACCEPTED);
        createEntryOrderIfNotFound(20L, 14L, 2L, EntryOrderType.PARTICIPATION, State.ACCEPTED);



    }



    private void loadOrderMenuData(){
        createOrderMenuIfNotFound(1L, 1L, 1600000L, 1L, "DUMMY MENU 1", null);
        createOrderMenuIfNotFound(2L,1L,500000L, 1L,"DUMMY OPTION 1", 1L);
        createOrderMenuIfNotFound(3L, 1L, 400000L, 2L, "DUMMY MENU 2", null);
        createOrderMenuIfNotFound(4L, 1L, 600000L, 2L, "DUMMY MENU 3", null);
        createOrderMenuIfNotFound(5L, 1L, 1500000L, 3L, "DUMMY MENU 4", null);
        createOrderMenuIfNotFound(6L, 1L, 30000L, 3L, "DUMMY OPTION 2", 5L);
        createOrderMenuIfNotFound(7L, 1L, 64000L, 3L, "DUMMY MENU 5", null);
        createOrderMenuIfNotFound(8L, 1L, 100000L,3L, "DUMMY OPTION 3", 7L);
        createOrderMenuIfNotFound(9L, 1L, 160000L, 1L, "DUMMY MENU 6", null);

        //1번 방 참여
        createOrderMenuIfNotFound(10L,1L,500000L, 5L,"DUMMY MENU 7", null);
        createOrderMenuIfNotFound(11L,1L,500000L, 6L,"DUMMY MENU 8", null);
        createOrderMenuIfNotFound(12L,1L,500000L, 7L,"DUMMY MENU 9", null);
        createOrderMenuIfNotFound(13L,1L,500000L, 5L,"DUMMY MENU 10", null);
        createOrderMenuIfNotFound(14L,1L,500000L, 6L,"DUMMY MENU 11", null);
        createOrderMenuIfNotFound(15L,1L,500000L, 7L,"DUMMY MENU 12", null);

        //3번 방 참여
        createOrderMenuIfNotFound(16L,1L,500000L, 8L,"DUMMY MENU 7", null);
        createOrderMenuIfNotFound(17L,1L,500000L, 8L,"DUMMY MENU 8", null);
        createOrderMenuIfNotFound(18L,1L,500000L, 9L,"DUMMY MENU 9", null);
        createOrderMenuIfNotFound(19L,1L,500000L, 9L,"DUMMY MENU 10", null);
        createOrderMenuIfNotFound(20L,1L,500000L, 10L,"DUMMY MENU 11", null);
        createOrderMenuIfNotFound(21L,1L,500000L, 10L,"DUMMY MENU 12", null);

        //4번 방 참여

        createOrderMenuIfNotFound(22L,1L,500000L, 4L,"DUMMY MENU 7", null);
        createOrderMenuIfNotFound(23L,1L,500000L, 4L,"DUMMY MENU 8", null);

        createOrderMenuIfNotFound(24L,1L,500000L, 11L,"DUMMY MENU 7", null);
        createOrderMenuIfNotFound(25L,1L,500000L, 11L,"DUMMY MENU 8", null);
        createOrderMenuIfNotFound(26L,1L,500000L, 12L,"DUMMY MENU 9", null);
        createOrderMenuIfNotFound(27L,1L,500000L, 12L,"DUMMY MENU 10", null);
        createOrderMenuIfNotFound(28L,1L,500000L, 13L,"DUMMY MENU 11", null);
        createOrderMenuIfNotFound(29L,1L,500000L, 13L,"DUMMY MENU 12", null);

        //5번 방 참여
        createOrderMenuIfNotFound(30L,1L,500000L, 17L,"DUMMY MENU 7", null);
        createOrderMenuIfNotFound(31L,1L,500000L, 17L,"DUMMY MENU 8", null);

        createOrderMenuIfNotFound(32L,1L,500000L, 14L,"DUMMY MENU 7", null);
        createOrderMenuIfNotFound(33L,1L,500000L, 14L,"DUMMY MENU 8", null);
        createOrderMenuIfNotFound(34L,1L,500000L, 15L,"DUMMY MENU 9", null);
        createOrderMenuIfNotFound(35L,1L,500000L, 15L,"DUMMY MENU 10", null);
        createOrderMenuIfNotFound(36L,1L,500000L, 16L,"DUMMY MENU 11", null);
        createOrderMenuIfNotFound(37L,1L,500000L, 16L,"DUMMY MENU 12", null);

        //6번 방 참여
        createOrderMenuIfNotFound(38L,1L,500000L, 18L,"DUMMY MENU 7", null);
        createOrderMenuIfNotFound(39L,1L,500000L, 18L,"DUMMY MENU 8", null);

        //2번 방 참여
        createOrderMenuIfNotFound(40L,1L,500000L, 19L,"DUMMY MENU 7", null);
        createOrderMenuIfNotFound(41L,1L,500000L, 19L,"DUMMY MENU 8", null);
        createOrderMenuIfNotFound(42L,1L,500000L, 20L,"DUMMY MENU 7", null);
        createOrderMenuIfNotFound(43L,1L,500000L, 20L,"DUMMY MENU 8", null);



    }

    private void loadPostCategory(){
        createPostCategoryIfNotFound(1L, "동네질문");
        createPostCategoryIfNotFound(2L, "동네맛집");
        createPostCategoryIfNotFound(3L, "동네소식");
        createPostCategoryIfNotFound(4L, "취미생활");
        createPostCategoryIfNotFound(5L, "분실/실종");
        createPostCategoryIfNotFound(6L, "품앗이");
        createPostCategoryIfNotFound(7L, "기타");

    }

    private void loadEvaluationCategory() {

        // Positive evaluation category
        createEvaluationCategoryIfNotFound("시간 약속을 잘 지켜요", 1L);
        createEvaluationCategoryIfNotFound("친절해요", 1L);
        createEvaluationCategoryIfNotFound("응답이 빨라요", 1L);

        // Negative evaluation category
        createEvaluationCategoryIfNotFound("시간 약속을 안 지켜요", -1L);
        createEvaluationCategoryIfNotFound("불친절해요", -1L);
        createEvaluationCategoryIfNotFound("연락이 안돼요", -1L);

    }

    private void loadReportCategory() {

        createReportCategoryIfNotFound("돈을 지불하지 않았어요");
        createReportCategoryIfNotFound("욕설을 해요");
        createReportCategoryIfNotFound("성희롱을 해요");
        createReportCategoryIfNotFound("시간 약속을 안 지켜요");
        createReportCategoryIfNotFound("비매너 행동을 했어요");
        createReportCategoryIfNotFound("주문을 제대로 진행하지 않았어요");
        createReportCategoryIfNotFound("연락이 두절됐어요");

    }

    private OrderMenu createOrderMenuIfNotFound(Long orderMenuId, Long quantity, Long price, Long entryOrderId, String menuName, Long parentId){
        Optional<OrderMenu> orderMenu = orderMenuRepository.findById(orderMenuId);

        if(orderMenu.isPresent()){
            return orderMenu.get();
        }

        //연관관계 확인 위한 필요 객체 확인
        Optional<EntryOrder> entryOrder = entryOrderRepository.findById(entryOrderId);

        Optional<OrderMenu> parentOrder = Optional.empty();
        if(parentId != null)  parentOrder = orderMenuRepository.findById(parentId);

        if(entryOrder.isEmpty()){
            log.warn("DummyDataLoader.createOrderMenuIfNotFound() : entryOder " + entryOrderId + " is null");
            return null;
        }

        return orderMenuRepository.save(OrderMenu.builder()
                .menuName(menuName)
                .parentMenu(parentOrder.orElse(null))
                .order(entryOrder.get())
                .price(price)
                .quantity(quantity)
                .build());
    }

    private Account createAccountDataIfNotFound(Long userId, String phoneNum, String nickName, RoleType role, Long profileImageId, BankType bankType){

        Account account = accountRepository.findByPhoneNumber(phoneNum);
        Optional<ImageFile> image = imageFileRepository.findById(profileImageId);

        if(account != null){
            saveDummyLoginInformationToRedis(account);
            return account;
        }

        if(image.isEmpty()){
            log.warn("DummyDataLoader.createAccountDataIfNotFound() : ImageFile " + profileImageId + " is null");
            return null;
        }


        //DB 저장
        Account savedAccount = accountRepository.save(Account.builder()
                .phoneNumber(phoneNum)
                .nickname(nickName)
                .email("DUMMY EMAIL DATA")
                .status(State.NORMAL)
                .profileImage(image.get())
                .role(RoleType.ROLE_USER)
                        .mannerScore(36.5)
                .bankAccount(BankAccount.builder()
                        .accountHolder("DUMMY NAME")
                        .accountNumber("DUMMY ACCOUNT NUMBER")
                        .bank(bankType)
                        .build()
                )
                .role(role)
                .build());

        saveDummyLoginInformationToRedis(savedAccount);

        return savedAccount;
    }

    private void saveDummyLoginInformationToRedis(Account account){
        //redis에 임시로 로그인 정보 저장
        LoggedOnInformation loggedOnInf = LoggedOnInformation.builder()
                .accountId(account.getAccountId())
                .phoneNumber(account.getPhoneNumber())
                .refreshToken("Dummy Refresh Token " + account.getAccountId())
                .fcmToken("Dummy FCM Token " + account.getAccountId())
                .build();

        authService.saveLoggedOnInformation(loggedOnInf);
    }

    private StoreCategory createStoreCategoryIfNotFound(String categoryName){
        StoreCategory storeCategory = storeCategoryRepository.findByCategoryName(categoryName);

        if(storeCategory != null){
            return storeCategory;
        }

        return storeCategoryRepository.save(StoreCategory.builder()
                .categoryName(categoryName)
                .build());
    }

    private ReceivingLocation createReceivingLocationIfNotFound(Long id, Double latitude, Double longitude, String description, String address, Boolean isFavorite, Long accountId) {

        Optional<ReceivingLocation> receivingLocation = receivingLocationRepository.findById(id);
        if (receivingLocation.isPresent()) {
            return receivingLocation.get();
        }
        //연관관계 확인 위한 필요 객체 확인
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            log.warn("DummyDataLoader.createReceivingLocationIfNotFound() : account" + accountId + " 가 존재하지 않습니다.");
            return null;
        }


        Point point = GeometriesFactory.createPoint(latitude, longitude);

        //참조용 좌표
        Location location = Location.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();

        return receivingLocationRepository.save(ReceivingLocation.builder()
                .pLocation(point)
                .locationRef(location)
                .description(description)
                .account(account.get())
                .isFavorite(isFavorite)
                .address(address)
                .isFavorite(isFavorite)
                .build());
    }

    private DeliveryRoom createDeliveryRoomIfNotFound(Long deliveryRoomId, String contents, Long peopleNumber, Long limitPerson, Long estimatedDeliveryTip, Long receivingLocationId, String storeCategoryName, Long leaderId, DeliveryRoomState state, PlatformType linkPlatformType, String storeName, String url){

        Optional<DeliveryRoom> deliveryRoom = deliveryRoomRepository.findById(deliveryRoomId);
        if(deliveryRoom.isPresent()){
            return deliveryRoom.get();
        }

        //연관관계 확인 위한 필요 객체 확인
        Optional<ReceivingLocation> receivingLocation = receivingLocationRepository.findById(receivingLocationId);

        StoreCategory storeCategory = storeCategoryRepository.findByCategoryName(storeCategoryName);

        Optional<Account> leader = accountRepository.findById(leaderId);


        if(receivingLocation.isEmpty() || storeCategory == null ||leader.isEmpty()){
            log.warn("DummyDataLoader.createDeliveryRoomIfNotFound() : 필요 객체 null");
            log.warn("  receivingLocation = " + receivingLocation);
            log.warn("  storeCategoryName = " + storeCategory);
            log.warn("  leader = " + leader);

            return null;
        }

        return deliveryRoomRepository.save(DeliveryRoom.builder()
                .receivingLocation(receivingLocation.get())
                .content(contents)
                .storeLink(url)
                .linkPlatformType(linkPlatformType)
                .status(state)
                        .storeName(storeName)
                .storeCategory(storeCategory)
                .estimatedDeliveryTip(estimatedDeliveryTip)
                .leader(leader.get())
                .peopleNumber(peopleNumber)
                .limitPerson(limitPerson)
                .build());
    }

    private EntryOrder createEntryOrderIfNotFound(Long entryOrderId, Long accountId, Long deliveryRoomId, EntryOrderType entryOrderType, State status){
        Optional<EntryOrder> entryOrder = entryOrderRepository.findById(entryOrderId);

        if(entryOrder.isPresent()){
            return entryOrder.get();
        }

        // 연관관계 위한 필요 객체 확인
        Optional<Account> account = accountRepository.findById(accountId);
        Optional<DeliveryRoom> deliveryRoom = deliveryRoomRepository.findById(deliveryRoomId);

        if(account.isEmpty() || deliveryRoom.isEmpty()){
            log.warn("DummyDataLoader.createEntryOrderIfNotFound() : 필요 객체 null");
            log.warn("  account = " + account);
            log.warn("  deliveryRoom = " + deliveryRoom);
            return null;
        }

        if(status.equals(State.PENDING)){
            deliveryRoom.get().addPerson();
        }

        return entryOrderRepository.save(EntryOrder.builder()
                .orderType(entryOrderType)
                .account(account.get())
                .orderType(entryOrderType)
                .deliveryRoom(deliveryRoom.get())
                .status(status)
                .build());
    }

    private PostCategory createPostCategoryIfNotFound(Long postCategoryId, String categoryName){
        Optional<PostCategory> findStoreCategory = postCategoryRepository.findById(postCategoryId);

        if(findStoreCategory.isPresent()){
            return findStoreCategory.get();
        }

        return postCategoryRepository.save(PostCategory.builder()
                .postCategoryId(postCategoryId)
                .categoryName(categoryName)
                .build());
    }

    private Payment createPaymentIfNotFound(Long paymentId, Long deliveryRoomId, Long deliveryFee){
        Optional<Payment> findPayment = paymentRepository.findById(paymentId);

        if(findPayment.isPresent()){
            return findPayment.get();
        }

        //연관
        Optional<DeliveryRoom> room = deliveryRoomRepository.findById(deliveryRoomId);

        if(room.isEmpty()){
            log.warn("DummyDataLoader.createPaymentIfNotFound() : 필요 객체 null");
            log.warn("  deliveryRoom = " + room);
            return null;
        }


        return paymentRepository.save(Payment.builder()
                .deliveryFee(deliveryFee)
                .deliveryRoom(room.get())
                .paymentId(paymentId)
                .build());
    }

    private PaymentDiscount createPaymentDiscountIfNotFound(Long paymentDiscountId, Long paymentId, String paymentDiscountName, Long amount){
        Optional<PaymentDiscount> findPaymentDiscount = paymentDiscountRepository.findById(paymentDiscountId);

        if(findPaymentDiscount.isPresent()){
            return findPaymentDiscount.get();
        }

        //연관관계 확인
        Optional<Payment> findPayment = paymentRepository.findById(paymentId);

        if(findPayment.isEmpty()){
            log.warn("DummyDataLoader.createPaymentIfNotFound() : 필요 객체 null");
            log.warn("  Payment = " + findPayment);
            return null;
        }

        return paymentDiscountRepository.save(PaymentDiscount.builder()
                .paymentDiscountId(paymentDiscountId)
                .payment(findPayment.get())
                .amount(amount)
                .paymentDiscountName(paymentDiscountName)
                .build());
    }

    private PaymentOrderForm createPaymentOrderFormIfNotFound(Long paymentOrderFormId, Long paymentId, Long imageFileId){
        Optional<PaymentOrderForm> findPaymentOrderForm = paymentOrderFormRepository.findById(paymentOrderFormId);

        if(findPaymentOrderForm.isPresent()){
            return findPaymentOrderForm.get();
        }

        //연관관계 확인
        Optional<Payment> findPayment = paymentRepository.findById(paymentId);
        Optional<ImageFile> findImageFile = imageFileRepository.findById(imageFileId);

        if(findPayment.isEmpty() || findImageFile.isEmpty()){
            log.warn("DummyDataLoader.createPaymentOrderFormIfNotFound() : 필요 객체 null");
            log.warn("  Payment = " + findPayment);
            log.warn("  imageFile = " + findImageFile);
            return null;
        }

        return paymentOrderFormRepository.save(PaymentOrderForm.toEntity(findPayment.get(), findImageFile.get()));
    }

    private ImageFile createImageFileIfNotFound(Long imageFileId, String originalFileName, String fileName, String fileExtension, String dirPath, Double fileSize){
        Optional<ImageFile> findImageFile = imageFileRepository.findById(imageFileId);

        if(findImageFile.isPresent()){
            return findImageFile.get();
        }

        return imageFileRepository.save(ImageFile.builder()
                .imageFileId(imageFileId)
                .dirPath(dirPath)
                .fileExtension(fileExtension)
                .fileName(fileName)
                .fileSize(fileSize)
                .originalFileName(originalFileName)
                .build());
    }

    private PostImage createPostImageFileIfNotFound(Long postImageId, Long imageFileId, Long postId){
        Optional<PostImage> findPostImages = postImageRepository.findById(postId);

        if(findPostImages.isPresent()){
            return findPostImages.get();
        }

        //연관관계 확인
        Optional<Post> findPost = postRepository.findById(postId);
        Optional<ImageFile> findImageFile = imageFileRepository.findById(imageFileId);

        if(findPost.isEmpty() || findImageFile.isEmpty()){
            log.warn("DummyDataLoader.createPostImageFileIfNotFound() : 필요 객체 null");
            log.warn("  post = " + findPost);
            log.warn("  imageFile = " + findImageFile);
            return null;
        }

        return postImageRepository.save(PostImage.builder()
                .post(findPost.get())
                .imageFile(findImageFile.get())
                .postImageId(postImageId)
                .build());
    }

    private PlaceShare createPlaceShareIfNotFound(Long placeShareId, Long postId, String content, Location coordinate, String address){
        Optional<PlaceShare> findPlaceShare = placeShareRepository.findById(placeShareId);

        if(findPlaceShare.isPresent()){
            return findPlaceShare.get();
        }

        Optional<Post> findPost = postRepository.findById(postId);

        if(findPost.isEmpty()){
            log.warn("DummyDataLoader.createPlaceShareIfNotFound() : 필요 객체 null");
            log.warn("  post = " + findPost);
            return null;
        }

        return placeShareRepository.save(PlaceShare.builder()
                .coordinate(coordinate)
                .post(findPost.get())
                .address(address)
                .content(content)
                .placeShareId(placeShareId)
                .build());
    }

    private Post createPostIfNotFound(Long postId, Long accountId, Long postCategoryId, String content, State state, Location accurateLocation, String address){
        Optional<Post> findPost = postRepository.findById(postId);
        if(findPost.isPresent()){
            return findPost.get();
        }

        //연관관계 확인
        Optional<PostCategory> findPostCategory = postCategoryRepository.findById(postCategoryId);
        Optional<Account> findAccount = accountRepository.findById(accountId);

        if(findPostCategory.isEmpty() || findAccount.isEmpty()){
            log.warn("DummyDataLoader.createPostIfNotFound() : 필요 객체 null");
            log.warn("  PostCategory = " + findPostCategory);
            log.warn("  Account = " + findAccount);
            return null;
        }
        Point point = GeometriesFactory.createPoint(accurateLocation.getLatitude(), accurateLocation.getLongitude());

        return postRepository.save(Post.builder()
                .status(State.NORMAL)
                .likeCount(0L)
                .coordinate(accurateLocation)
                .address(address)
                .postCategory(findPostCategory.get())
                .account(findAccount.get())
                .content(content)
                .postId(postId)
                .viewCount(0L)
                .pLocation(point)
                .build());
    }

    private Friend createFriendIfNotFound(Long friendId, Long accountId, Long account2, State status) {
        Friend friend = friendRepository.findByFriendId(friendId);
        if(friend != null) return friend;

        Account acc1 = accountRepository.findByAccountId(accountId);
        Account acc2 = accountRepository.findByAccountId(account2);

        if(acc1 == null || acc2 == null){
            log.warn("DummyDataLoader::createFriendIfNotFound() : 필요 객체 null");
            log.warn("  account1 = " + acc1);
            log.warn("  account2 = " + acc2);
            return null;
        }

        return friendRepository.save(
                Friend.builder()
                        .friendId(friendId)
                        .firstAccount(acc1)
                        .secondAccount(acc2)
                        .status(status)
                        .build()
        );
    }

    private Comment createCommentIfNotFound(Long commentId, Long accountId, Long parentId, Long postId, String content){
        Optional<Comment> findComment = commentRepository.findById(commentId);

        if(findComment.isPresent()){
            return findComment.get();
        }

        if(parentId == null) parentId = -1L;
        Optional<Comment> findParrentComment = commentRepository.findById(parentId);
        Optional<Account> findAccount = accountRepository.findById(accountId);
        Optional<Post> findPost = postRepository.findById(postId);

        if(findAccount.isEmpty() || findPost.isEmpty()){
            log.warn("DummyDataLoader::createCommentIfNotFound() : 필요 객체 null");
            log.warn("  writer         : " + findAccount);
            log.warn("  post           : " + findPost);
            return null;
        }

        return commentRepository.save(Comment.builder()
                .parent(findParrentComment.orElse(null))
                .likeCount(0L)
                .content(content)
                .account(findAccount.get())
                .commentId(commentId)
                .post(findPost.get())
                .status(State.NORMAL)
                .build());
    }

    private EvaluationCategory createEvaluationCategoryIfNotFound(String categoryName, Long value) {

        EvaluationCategory evaluationCategory = evaluationCategoryRepository.findByCategoryName(categoryName);

        if (evaluationCategory != null) {
            return evaluationCategory;
        }

        return evaluationCategoryRepository.save(EvaluationCategory.builder()
                .categoryName(categoryName)
                .value(value)
                .build());

    }

    private ReportCategory createReportCategoryIfNotFound(String categoryName) {

        ReportCategory reportCategory = reportCategoryRepository.findByCategoryName(categoryName);

        if (reportCategory != null) {
            return reportCategory;
        }

        return reportCategoryRepository.save(ReportCategory.builder()
                .categoryName(categoryName)
                .level(0L)
                .build());

    }
}
