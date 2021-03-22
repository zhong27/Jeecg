package org.jeecg.modules.cash;

public enum RefundPreEnum {

    RE("退款","RE");
    private String name;
    private String value;

    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }

    private RefundPreEnum(String name, String value){
        this.name = name;
        this.value = value;
    }
}
