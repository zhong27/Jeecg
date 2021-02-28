package org.jeecg.modules.ord;

public enum OrderNo {
    ORDER_NO_PRE("RD","订单编号前缀"),
    BILL_NO_PRE("SA","提单编号前缀") ;

    private String value;
    private String name;

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    private OrderNo(String value, String name){
        this.value = value;
        this.name = name;
    }
}
