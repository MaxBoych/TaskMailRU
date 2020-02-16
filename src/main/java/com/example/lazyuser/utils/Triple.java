package com.example.lazyuser.utils;

public class Triple<F, S, T> extends Couple<F, S>{

    private T third;

    public Triple(F first, S second, T third) {
        super(first, second);
        this.third = third;
    }

    public T getThird() {
        return third;
    }
}
