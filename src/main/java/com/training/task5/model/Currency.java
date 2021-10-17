package com.training.task5.model;

public enum Currency {

    USD(1.0),
    EUR(1.15),
    UAH(0.038);

    private final double rate;

    Currency(double value) {
        this.rate = value;
    }

    public double getRate() {
        return rate;
    }
}
