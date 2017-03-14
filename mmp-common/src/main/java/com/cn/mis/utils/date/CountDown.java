package com.cn.mis.utils.date;

/**
 * Created by yuejia on 16/2/25.
 */
public class CountDown {
    private int dayTens;
    private int dayBasic;
    private int hourTens;
    private int hourBasic;
    private int minuteTens;
    private int minuteBasic;
    private int secondTens;
    private int secondBaisc;

    public CountDown(int dayTens, int dayBasic, int hourTens, int hourBasic, int minuteTens, int minuteBasic, int secondTens, int secondBaisc) {
        this.dayTens = dayTens;
        this.dayBasic = dayBasic;
        this.hourTens = hourTens;
        this.hourBasic = hourBasic;
        this.minuteTens = minuteTens;
        this.minuteBasic = minuteBasic;
        this.secondTens = secondTens;
        this.secondBaisc = secondBaisc;
    }

    public int getDayTens() {
        return dayTens;
    }

    public void setDayTens(int dayTens) {
        this.dayTens = dayTens;
    }

    public int getDayBasic() {
        return dayBasic;
    }

    public void setDayBasic(int dayBasic) {
        this.dayBasic = dayBasic;
    }

    public int getHourTens() {
        return hourTens;
    }

    public void setHourTens(int hourTens) {
        this.hourTens = hourTens;
    }

    public int getHourBasic() {
        return hourBasic;
    }

    public void setHourBasic(int hourBasic) {
        this.hourBasic = hourBasic;
    }

    public int getMinuteTens() {
        return minuteTens;
    }

    public void setMinuteTens(int minuteTens) {
        this.minuteTens = minuteTens;
    }

    public int getMinuteBasic() {
        return minuteBasic;
    }

    public void setMinuteBasic(int minuteBasic) {
        this.minuteBasic = minuteBasic;
    }

    public int getSecondTens() {
        return secondTens;
    }

    public void setSecondTens(int secondTens) {
        this.secondTens = secondTens;
    }

    public int getSecondBaisc() {
        return secondBaisc;
    }

    public void setSecondBaisc(int secondBaisc) {
        this.secondBaisc = secondBaisc;
    }
}
