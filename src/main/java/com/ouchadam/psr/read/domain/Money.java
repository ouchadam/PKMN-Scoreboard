package com.ouchadam.psr.read.domain;

public class Money {

    private final long money;

    public Money(String money) {
        this.money = Long.parseLong(money);
    }

    public long value() {
        return money;
    }

    public String formatted() {
        return "$" + money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money1 = (Money) o;
        if (money != money1.money) return false;
        return true;
    }
    @Override
    public int hashCode() {
        return (int) (money ^ (money >>> 32));


    }
}
