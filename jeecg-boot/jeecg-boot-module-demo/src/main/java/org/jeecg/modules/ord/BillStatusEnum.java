package org.jeecg.modules.ord;

public enum BillStatusEnum {
    TAKING("taking","未取件"),
    PICKED_UP("pickedup","已取件") ,
    REFUNDING("refunding","退单中"),
    REFUNDED("refunded","已退单");

    private String value;
    private String name;

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    private BillStatusEnum(String value, String name){
        this.value = value;
        this.name = name;
    }
}
