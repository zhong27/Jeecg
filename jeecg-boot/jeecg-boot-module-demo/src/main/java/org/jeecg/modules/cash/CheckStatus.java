package org.jeecg.modules.cash;

public enum CheckStatus {
    FINISH("已审核","finish"),
    UNFINSH("待审核","unfinish");
    private String name;
    private String value;

    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }

    private CheckStatus(String name, String value){
        this.name = name;
        this.value = value;
    }
}
