package org.jeecg.modules.cash;

public enum IncomePreEnum {

    CE("来款前缀","CE");
    private String name;
    private String value;

    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }

    private IncomePreEnum(String name, String value){
        this.name = name;
        this.value = value;
    }
}
