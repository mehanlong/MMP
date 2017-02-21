package com.cn.mis.domain.enums;

/**
 * Created by yuejia on 2017/2/15.
 */
public enum LockStatusEnum {
    type1("1","未锁定"),
    type2("2","已锁定");
    private String name;
    private String type;

    private LockStatusEnum(String type,String name){
        this.type = type;
        this.name = name;

    }

    public static String getName(String type){
        for (LockStatusEnum pt : LockStatusEnum.values()){
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
