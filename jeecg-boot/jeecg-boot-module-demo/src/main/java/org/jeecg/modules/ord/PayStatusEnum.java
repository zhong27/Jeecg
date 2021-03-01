package org.jeecg.modules.ord;

public enum PayStatusEnum {
    PAY("pay","已支付"),
    UN_PAY("unpay","未支付") ;

    private String value;
    private String name;

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    private PayStatusEnum(String value, String name){
        this.value = value;
        this.name = name;
    }
}
