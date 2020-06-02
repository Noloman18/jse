package com.gmail.noloxtreme.investments.core.impl;

import com.gmail.noloxtreme.investments.core.StockService;
import com.gmail.noloxtreme.investments.core.model.Stock;
import org.jsoup.Jsoup;

import java.io.IOException;

public class ReutersStockService implements StockService {
    @Override
    public Stock lookupSymbol(String symbol) {
        try {
            System.out.printf("Looking up %s\r\n",symbol);
            var stock = new Stock();
            populateNameAndIndustry(symbol, stock);
            populateValuationMetrics(symbol, stock);

            return stock;
        }
        catch(Exception e) {
            return null;
        }
    }

    @Override
    public boolean validate(Stock stock) {
        try {
            Double.parseDouble(stock.marketCap);
            Double.parseDouble(stock.revenue);
            Double.parseDouble(stock.freeCashFlow);
            Double.parseDouble(stock.priceToSales);
            Double.parseDouble(stock.priceToBook);
            Double.parseDouble(stock.quickRatio);
            Double.parseDouble(stock.price);
            Double.parseDouble(stock.yearHigh);
            Double.parseDouble(stock.yearLow);
            Double.parseDouble(stock.yearReturn);
            Double.parseDouble(stock.revenueGrowth);
            Double.parseDouble(stock.earningsGrowth);
            return true;
        }
        catch(NumberFormatException|NullPointerException n) {
            return false;
        }
    }

    private void populateValuationMetrics(String symbol, Stock stock) throws IOException {
        var jsoupConnection = Jsoup.connect(String.format("https://www.reuters.com/companies/%sJ.J/key-metrics",symbol));
        var document = jsoupConnection.get();
        var rows = document.select("table tr");
        rows.forEach(element -> {
          String heading = element.select("th div").text();
            String value = element.select("td span").text();
          if (!heading.isEmpty() && !value.isEmpty()) {
              value = value.replaceAll("\\,","");
              switch(heading) {
                  case "Market Capitalization":
                    stock.marketCap = value;
                    break;
                  case "P/E excl. Extra Items (Annual)":
                      stock.priceToEarnings = value;
                      break;
                  case "Price to sales (Annual)":
                      stock.priceToSales = value;
                      break;
                  case "Price to Book (Annual)":
                      stock.priceToBook = value;
                      break;
                  case "Dividend Yield":
                      stock.dividendYield = value;
                      break;
                  case "Free Cash Flow (Annual)":
                      stock.freeCashFlow = value;
                      break;
                  case "13 Week Price Return (Daily)":
                      stock.threeMonthReturn = value;
                      break;
                  case "Total Debt/Total Equity (Annual)":
                      stock.debtToEquity = value;
                      break;
                  case "Revenue (Annual)":
                      stock.revenue = value;
                      break;
                  case "Price closing or last bid":
                      stock.price = value;
                      break;
                  case "52 Week High":
                      stock.yearHigh = value;
                      break;
                  case "52 Week Low":
                      stock.yearLow = value;
                      break;
                  case "52 Week Price Return (Daily)":
                      stock.yearReturn = value;
                      break;
                  case "Quick Ratio (Annual)":
                      stock.quickRatio = value;
                      break;
                  case "Revenue Growth (Quarterly YoY)":
                      stock.revenueGrowth = value;
                      break;
                  case "EPS Growth (Quarterly YoY)":
                      stock.earningsGrowth = value;
                      break;
              }
          }
        });
    }

    private void populateNameAndIndustry(String symbol, Stock stock) throws IOException {
        var jsoupConnection = Jsoup.connect(String.format("https://www.reuters.com/companies/%sJ.J",symbol));
        var document = jsoupConnection.get();
        var name = document.select(".QuoteRibbon-name-ric-epp2J h1");
        var industry = document.select(".About-section-3ooPI.industry p:nth-child(2)");
        stock.symbol = symbol;
        stock.industry = industry.text();
        stock.name = name.text();
    }
}
