package org.jeecg.modules.cash;

public enum RefundAgreeEnum {
    AGREE("已确认","agree"),
    DISAGREE("未确认","disagree");
    private String name;
    private String value;

    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }

    private RefundAgreeEnum(String name, String value){
        this.name = name;
        this.value = value;
    }
}
