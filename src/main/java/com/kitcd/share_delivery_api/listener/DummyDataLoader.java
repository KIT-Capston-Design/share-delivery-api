package com.kitcd.share_delivery_api.listener;

import com.kitcd.share_delivery_api.domain.jpa.account.*;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.PlatformType;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderRepository;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.domain.jpa.friend.Friend;
import com.kitcd.share_delivery_api.domain.jpa.friend.FriendRepository;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenuRepository;
import com.kitcd.share_delivery_api.domain.jpa.postcategory.PostCategory;
import com.kitcd.share_delivery_api.domain.jpa.postcategory.PostCategoryRepository;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocationRepository;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategory;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategoryRepository;
import com.kitcd.share_delivery_api.domain.redis.auth.loggedoninf.LoggedOnInformation;
import com.kitcd.share_delivery_api.service.AuthService;
import com.kitcd.share_delivery_api.utils.geometry.GeometriesFactory;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
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

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadAccountData();
        loadStoreCategoryData();
        loadReceivingLocationData();
        loadDeliveryRoomData();
        loadEntryOrderData();
        loadOrderMenuData();
        loadPostCategory();
        loadFriendData();
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
        createAccountDataIfNotFound(1L,"01000000001", "DUMMY USER 1", RoleType.ROLE_USER);
        createAccountDataIfNotFound(2L, "01000000002", "DUMMY USER 2", RoleType.ROLE_USER);
        createAccountDataIfNotFound(3L, "01000000003", "DUMMY USER 3", RoleType.ROLE_USER);
        createAccountDataIfNotFound(4L,"01000000004", "DUMMY USER 4", RoleType.ROLE_USER);
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
        createDeliveryRoomIfNotFound(1L, "DUMMY CONTENT 1", 1L,4L, 123456L,1L, "CHICKEN", 1L, DeliveryRoomState.OPEN, PlatformType.BAEMIN, "DUMMY STORE NAME 0", "https://baemin.me/JsDPOYYqUd");
        createDeliveryRoomIfNotFound(2L, "DUMMY CONTENT 2", 1L,4L, 123456L,2L, "DESERT", 2L, DeliveryRoomState.OPEN, PlatformType.BAEMIN,"DUMMY STORE NAME 1", "https://baemin.me/jpaPFsg-B");
        createDeliveryRoomIfNotFound(3L, "DUMMY CONTENT 3", 1L,4L, 123456L,3L, "FASTFOOD", 3L, DeliveryRoomState.OPEN, PlatformType.BAEMIN, "DUMMY STORE NAME 2", "https://baemin.me/gzJ_2H5-o");
        createDeliveryRoomIfNotFound(4L, "DUMMY CONTENT 4", 1L,4L, 123456L,1L, "LUNCHBOX", 1L, DeliveryRoomState.OPEN, PlatformType.YOGIYO, "DUMMY STORE NAME 3", "https://yogiyo.onelink.me/BlI7/im8nou2o");
    }

    private void loadEntryOrderData(){

        //LEAD
        createEntryOrderIfNotFound(1L, 1L, 1L, EntryOrderType.LEAD, State.ACCEPTED);
        createEntryOrderIfNotFound(2L, 1L, 4L, EntryOrderType.LEAD, State.ACCEPTED);
        createEntryOrderIfNotFound(3L, 2L, 2L, EntryOrderType.LEAD, State.ACCEPTED);
        createEntryOrderIfNotFound(4L, 3L, 3L, EntryOrderType.LEAD, State.ACCEPTED);

        //PARTICIPATION
        createEntryOrderIfNotFound(5L, 2L, 1L, EntryOrderType.PARTICIPATION, State.PENDING);
        createEntryOrderIfNotFound(6L, 3L, 1L, EntryOrderType.PARTICIPATION, State.PENDING);
        createEntryOrderIfNotFound(7L, 4L, 1L, EntryOrderType.PARTICIPATION, State.PENDING);
    }



    private void loadOrderMenuData(){
        createOrderMenuIfNotFound(1L, 1L, 16000L, 1L, "DUMMY MENU 1", null);
        createOrderMenuIfNotFound(2L,1L,500L, 1L,"DUMMY OPTION 1", 1L);
        createOrderMenuIfNotFound(3L, 1L, 4000L, 2L, "DUMMY MENU 2", null);
        createOrderMenuIfNotFound(4L, 1L, 6000L, 2L, "DUMMY MENU 3", null);
        createOrderMenuIfNotFound(5L, 1L, 1500L, 3L, "DUMMY MENU 4", null);
        createOrderMenuIfNotFound(6L, 1L, 300L, 3L, "DUMMY OPTION 2", 5L);
        createOrderMenuIfNotFound(7L, 1L, 6400L, 3L, "DUMMY MENU 5", null);
        createOrderMenuIfNotFound(8L, 1L, 1000L,3L, "DUMMY OPTION 3", 7L);
        createOrderMenuIfNotFound(9L, 1L, 16000L, 1L, "DUMMY MENU 6", null);

        createOrderMenuIfNotFound(10L,1L,500L, 5L,"DUMMY MENU 7", null);
        createOrderMenuIfNotFound(11L,1L,500L, 6L,"DUMMY MENU 8", null);
        createOrderMenuIfNotFound(12L,1L,500L, 7L,"DUMMY MENU 9", null);
        createOrderMenuIfNotFound(13L,1L,500L, 5L,"DUMMY MENU 10", null);
        createOrderMenuIfNotFound(14L,1L,500L, 6L,"DUMMY MENU 11", null);
        createOrderMenuIfNotFound(15L,1L,500L, 7L,"DUMMY MENU 12", null);
    }

    private void loadPostCategory(){
        createPostCategoryIfNotFound(1L, "동네질문");
        createPostCategoryIfNotFound(2L, "동네맛집");
        createPostCategoryIfNotFound(3L, "동네소식");
        createPostCategoryIfNotFound(4L, "취미생활");
        createPostCategoryIfNotFound(5L, "분실/실종");
        createPostCategoryIfNotFound(6L, "품앗이");

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

    private Account createAccountDataIfNotFound(Long userId, String phoneNum, String nickName, RoleType role){

        Account account = accountRepository.findByPhoneNumber(phoneNum);

        if(account != null){
            saveDummyLoginInformationToRedis(account);
            return account;
        }

        //DB 저장
        Account savedAccount = accountRepository.save(Account.builder()
                .phoneNumber(phoneNum)
                .nickname(nickName)
                .email("DUMMY EMAIL DATA")
                .status(State.NORMAL)
                .role(RoleType.ROLE_USER)
                        .mannerScore(36.5)
                .bankAccount(BankAccount.builder()
                        .accountHolder("DUMMY NAME")
                        .accountNumber("DUMMY ACCOUNT NUMBER")
                        .bank(BankType.KAKAO)
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
                .refreshToken("Dummy Refresh Token")
                .fcmToken("Dummy FCM Token")
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
}
