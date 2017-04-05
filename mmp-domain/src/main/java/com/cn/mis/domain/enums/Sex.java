package com.cn.mis.domain.enums;

/**
 * Created by yuejia on 2017/3/14.
 */
public enum Sex {
    type1("0","M"),
    type2("1","F");
    private String name;
    private String type;

    private Sex(String type,String name){
        this.type = type;
        this.name = name;

    }

    public static String getName(String type){
        for (Sex pt : Sex.values()){
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
