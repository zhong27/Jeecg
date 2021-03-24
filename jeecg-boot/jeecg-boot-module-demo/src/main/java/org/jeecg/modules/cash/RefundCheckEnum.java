package org.jeecg.modules.cash;

public enum RefundCheckEnum {
    UN_CHECK("待审核","uncheck"),
    PASS("已通过","pass"),
    UN_PASS("未通过","unpass");
    private String name;
    private String value;

    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }

    private RefundCheckEnum(String name, String value){
        this.name = name;
        this.value = value;
    }
}
