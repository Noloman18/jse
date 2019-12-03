package com.gmail.noloxtreme.investments;

import com.gmail.noloxtreme.investments.core.StockService;

import java.util.ServiceLoader;

public class Runner {
    public static void main(String[] args) {
        var stockServiceWrapper =
                ServiceLoader.load(StockService.class).findFirst();

        stockServiceWrapper.ifPresent(stockService -> {
            System.out.println("Success...:"+stockService.lookup("CML"));
        });
    }
}
