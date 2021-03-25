package org.jeecg.modules.ord;

public enum BillRefundEnum {
    UN_REFUND("unrefund","未退单"),
    REFUND("refund","退单");

    private String value;
    private String name;

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    private BillRefundEnum(String value, String name){
        this.value = value;
        this.name = name;
    }
}
