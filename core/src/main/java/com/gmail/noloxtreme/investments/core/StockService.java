package com.gmail.noloxtreme.investments.core;

import com.gmail.noloxtreme.investments.core.model.Stock;

public interface StockService {
    Stock lookupSymbol(String symbol);
    boolean validate(Stock stock);
}
