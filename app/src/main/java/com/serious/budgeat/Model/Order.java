package com.serious.budgeat.Model;

/**
 * Created by thomas on 14/12/16.
 */

public class Order {

    public Integer vegetable;
    public Integer bread;
    public Integer meat;
    public Integer cheese;

    public String vegetablesName;
    public String breadName;
    public String meatName;

    public String getVegetablesName() {
        return vegetablesName;
    }

    public void setVegetablesName(String vegetablesName) {
        this.vegetablesName = vegetablesName;
    }

    public String getBreadName() {
        return breadName;
    }

    public void setBreadName(String breadName) {
        this.breadName = breadName;
    }

    public String getMeatName() {
        return meatName;
    }

    public void setMeatName(String meatName) {
        this.meatName = meatName;
    }

    public String getCheeseName() {
        return cheeseName;
    }

    public void setCheeseName(String cheeseName) {
        this.cheeseName = cheeseName;
    }

    public String cheeseName;

    public Integer getVegetable() {
        return vegetable;
    }

    public void setVegetable(Integer vegetable) {
        this.vegetable = vegetable;
    }

    public Integer getBread() {
        return bread;
    }

    public void setBread(Integer bread) {
        this.bread = bread;
    }

    public Integer getMeat() {
        return meat;
    }

    public void setMeat(Integer meat) {
        this.meat = meat;
    }

    public Integer getCheese() {
        return cheese;
    }

    public void setCheese(Integer cheese) {
        this.cheese = cheese;
    }
}
