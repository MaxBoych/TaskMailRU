package com.example.lazyuser.utils;

public class Couple<F, S> {

    private F first;
    private S second;

    public Couple(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
}
