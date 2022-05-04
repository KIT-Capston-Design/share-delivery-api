package com.kitcd.share_delivery_api.listener;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.account.AccountRepository;
import com.kitcd.share_delivery_api.domain.jpa.common.Coordinate;
import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.PlatformType;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderTableRepository;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenu;
import com.kitcd.share_delivery_api.domain.jpa.ordermenu.OrderMenuRepository;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocationRepository;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategory;
import com.kitcd.share_delivery_api.domain.jpa.storecategory.StoreCategoryRepository;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Store;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@ConfigurationProperties(prefix = "property.test")
@Component
@AllArgsConstructor
public class Test implements ApplicationListener<ContextRefreshedEvent> {


    private StoreCategoryRepository storeCategoryRepository;
    private DeliveryRoomRepository deliveryRoomRepository;
    private EntryOrderTableRepository entryOrderTableRepository;
    private OrderMenuRepository orderMenuRepository;
    private ReceivingLocationRepository receivingLocationRepository;
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Account account = accountData();
        ReceivingLocation receivingLocation = loadReceivingRocationData(account);
        loadStoreCategoryData();
        DeliveryRoom room = loadDeliveryRoomData(account, receivingLocation);
        EntryOrder entryOrder = loadEntryOrderTableData(account, room);
        loadOrderMenuData(account, entryOrder);
    }

    private void loadStoreCategoryData(){
        List<String> categoryNames = new ArrayList<>();
        //"CHICKEN", "PIZZA", "CHINA", "JAPAN", "BOONSIK", "KOREA", "FASTFOOD", "LATE_NIGHT", "LUNCHBOX", "STEAM_AND_SOUP"
        categoryNames.add("CHICKEN");
        categoryNames.add("PIZZA");
        categoryNames.add("CHINA");
        categoryNames.add("JAPAN");
        categoryNames.add("BOONSIK");
        categoryNames.add("KOREA");
        categoryNames.add("FASTFOOD");
        categoryNames.add("LATE_NIGHT");
        categoryNames.add("LUNCHBOX");
        categoryNames.add("STEAM_AND_SOUP");

        List<StoreCategory> storeCategories = categoryNames.stream().map(i -> StoreCategory.builder().categoryName(i).build())
                .collect(Collectors.toList());

        storeCategoryRepository.saveAll(storeCategories);
    }
    private Account accountData(){
        Account account =Account.builder()
                .phoneNumber("01031798788")
                .nickname("김현진")
                .build();
        accountRepository.save(account);

        return account;
    }

    private DeliveryRoom loadDeliveryRoomData(Account account, ReceivingLocation receivingLocation){
        StoreCategory storeCategory = storeCategoryRepository.findByCategoryName("CHICKEN");
        DeliveryRoom deliveryRoom = DeliveryRoom.builder()
                .content("치킨 드실분")
                .leader(account)
                .limitPerson(4L)
                .receivingLocation(receivingLocation)
                .status(DeliveryRoomState.WAITING)
                .storeCategory(storeCategory)
                .linkPlatformType(PlatformType.BAEMIN)
                .shareStoreLink("https://baemin.me/T1&A&BgJXI")
                .build();
        deliveryRoomRepository.save(deliveryRoom);
        return deliveryRoom;
    }

    private EntryOrder loadEntryOrderTableData(Account account, DeliveryRoom deliveryRoom){
        EntryOrder entryOrder = EntryOrder.builder().orderType(EntryOrderType.APPLIED)
                .account(account)
                .deliveryRoom(deliveryRoom)
                .isRejected(State.NORMAL)
                .build();
        entryOrderTableRepository.save(entryOrder);
        return entryOrder;
    }

    private List<OrderMenu> loadOrderMenuData(Account account, EntryOrder entryOrder){
        OrderMenu orderMenu = OrderMenu.builder().menuName("뿌링클")
                .amount(1L)
                .order(entryOrder)
                .build();
        OrderMenu option = OrderMenu.builder().menuName("소스추가")
                .amount(1L)
                .order(entryOrder)
                .parentMenu(orderMenu)
                .build();
        List<OrderMenu> orderMenus = new ArrayList<>();
        orderMenus.add(orderMenu);
        orderMenus.add(option);
        orderMenuRepository.saveAll(orderMenus);
        return orderMenus;
    }

    private ReceivingLocation loadReceivingRocationData(Account account){
        Coordinate coordinate = new Coordinate(1001.281937, 1003.1424348);
        String description = "대충 집잎";
        ReceivingLocation receivingLocation = ReceivingLocation.builder()
                .account(account)
                .coordinate(coordinate)
                .description(description)
                .isFavorite(false)
                .address("대구광역시 수성구 무열로 47")
                .build();
        receivingLocationRepository.save(receivingLocation);
        return receivingLocation;
    }
}
