package com.ouchadam.psr.read.domain;

public class Money {

    private final long money;

    public Money(String money) {
        this.money = Long.parseLong(money);
    }

    public String formatted() {
        return "$" + money;
    }
}
