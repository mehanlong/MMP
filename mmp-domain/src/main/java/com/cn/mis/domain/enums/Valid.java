package com.cn.mis.domain.enums;

/**
 * Created by yuejia on 2017/3/14.
 */
public enum  Valid {
    type1("0","A"),
    type2("1","A"),
    type3("2","I"),
    type4("3","A"),
    type5("4","I"),
    type6("5","I"),
    type7("6","I"),
    type8("7","I");
    private String name;
    private String type;

    private Valid(String type,String name){
        this.type = type;
        this.name = name;

    }

    public static String getName(String type){
        for (Valid pt : Valid.values()){
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
