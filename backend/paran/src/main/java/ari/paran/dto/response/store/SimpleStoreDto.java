package ari.paran.dto.response.store;

import ari.paran.domain.store.Address;
import ari.paran.domain.store.Store;
import lombok.Getter;

import java.util.List;

@Getter
public class SimpleStoreDto {

    private Long storeId;
    private String name;
    private Address address;
    private List<String> partnersName;
    private String image;
    
    private boolean privateEvent;
    private boolean stamp;

    public SimpleStoreDto(Store store){
        this.storeId = store.getId();
        this.name = store.getName();
        this.address = store.getAddress();
        this.privateEvent = store.getPrivateEvent();
    }

    public void setPartnersName(List<String> partnersName) {
        this.partnersName = partnersName;
    }

    public void setImage(String image){
        this.image = image;
    }

}
