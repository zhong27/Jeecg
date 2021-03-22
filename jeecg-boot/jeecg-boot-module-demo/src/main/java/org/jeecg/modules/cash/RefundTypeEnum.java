package org.jeecg.modules.cash;

public enum RefundTypeEnum {
    REFUND_GOODS("退货退款","refundgoods"),
    REFUND("仅退款","refund");
    private String name;
    private String value;

    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }

    private RefundTypeEnum(String name, String value){
        this.name = name;
        this.value = value;
    }
}
