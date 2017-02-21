package com.cn.mis.domain.enums;

/**
 * Created by yuejia on 2017/2/15.
 */
public enum  DbcSelect3Enum {
    type1("1","收益实惠型"),
    type2("2","堵漏增益型"),
    type3("3","傍随先进型"),
    type4("4","品牌外拓型"),
    type5("5","房屋溢价型"),
    type6("6","试水合作型"),
    type7("7","观望成效型"),
    type8("8","自我探索型");

    private String name;
    private String type;

    private DbcSelect3Enum(String type,String name){
        this.type = type;
        this.name = name;

    }

    public static String getName(String type){
        for (DbcSelect3Enum pt : DbcSelect3Enum.values()){
            if (pt.getType().equals(type)){
                return pt.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
