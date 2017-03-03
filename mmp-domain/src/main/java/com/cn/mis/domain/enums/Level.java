package com.cn.mis.domain.enums;

public enum Level {
	type1("1","A类：已经递交合同，明确签约时间，一周内回合同"),
	type2("2","B类：对商务条款稍有些异议"),
	type3("3","C类：已接触并有合作意向"),
	type5("5","D类：初步接触且信息收集齐全"),
	type6("6","E类：已完成签约");
	private String name;
    private String type;
	
	private Level(String type,String name){
        this.type = type;
        this.name = name;

    }

    public static String getName(String type){
        for (Level pt : Level.values()){
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
