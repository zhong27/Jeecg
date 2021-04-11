package org.jeecg.modules.man;

public enum AccountStatusEnum {
    NORMAL("正常","normal"),
    FROZEN("冻结","frozen");
    private String name;
    private String value;

    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }

    private AccountStatusEnum(String name, String value){
        this.name = name;
        this.value = value;
    }
}
