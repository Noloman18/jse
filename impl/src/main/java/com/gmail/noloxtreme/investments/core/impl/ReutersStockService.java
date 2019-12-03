package com.gmail.noloxtreme.investments.core.impl;

import com.gmail.noloxtreme.investments.core.StockService;
import com.gmail.noloxtreme.investments.core.model.Stock;

public class ReutersStockService implements StockService {
    @Override
    public Stock lookup(String symbol) {
        return new Stock();
    }
}
