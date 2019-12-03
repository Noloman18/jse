package com.gmail.noloxtreme.investments;

import com.gmail.noloxtreme.investments.core.StockService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Runner {

    public static void main(String[] args) throws Exception {
        var stockServiceWrapper =
                ServiceLoader.load(StockService.class).findFirst();

        stockServiceWrapper.ifPresent(stockService -> {
            var listOfSymbols = Arrays.asList("ABG","ACG","ACL","ACT","ADH","ADI","ADR","AEL","AFE","AFH","AFT","AFX","AGL","AIP","AMS","ANG","ANH","APN","ARA","ARI","ARL","ART","ASC","ASHINF","ASHMID","ASHT40","ASO","ASR","ATT","AVI","AVV","BAT","BAW","BHP","BID","BLU","BTC","BTI","BVT","CAC","CCO","CFR","CGR","CIL","CLH","CLS","CMH","CML","CND","COH","CPI","CRP","CSB","CSEW40","CVH","DCP","DIVTRX","DRD","DST","DSY","DTC","EMI","EOH","ETFRHO","ETFSAP","ETFSWX","ETFT40","EXX","FBR","FFA","FFB","FGL","FSR","GFI","GLD","GLN","GND","GPL","GRT","HAR","HCI","HDC","HLM","HMN","HSP","HYP","IAP","IHL","IMP","INL","INP","IPF","IPL","ITE","ITU","IVT","JSE","KAP","KIO","KST","L4L","LBH","LEW","LHC","MAPPSG","MAPPSP","MDI","MEI","MFL","MIX","MNP","MPT","MRP","MSM","MTA","MTH","MTM","MTN","MUR","NED","NEP","NEWFSA","NEWUSD","NFEMOM","NFGOVI","NFILBI","NFSH40","NFSWIX","NFTRCI","NGPLT","NHM","NIV","NPK","NPN","NRP","NT1","NTC","OCE","OCT","OML","OMN","PAN","PFG","PGR","PIK","PPC","PPH","PRX","PSG","RAFFIN","RAFIND","RAFISA","RAFRES","RBP","RBX","RCL","RDF","REI","REM","RES","RFG","RLF","RLO","RMH","RMI","RPL","S32","SAC","SAP","SBK","SDO","SEP","SGL","SHP","SLM","SNT","SNV","SOL","SPG","SPP","SRE","SSS","STX40","STX500","STXDIV","STXEMG","STXFIN","STXIND","STXQUA","STXRAF","STXRES","STXSWX","STXWDM","SUI","SUR","SYG","SYGEU","SYGJP","SYGUK","SYGUS","SYGWD","TBS","TEX","TFG","TKG","TON","TRE","TRU","TWR","VKE","VOD","WBO","WHL","WSL","ZED");

            Collections.shuffle(listOfSymbols);

            var listOfStocks =
                    listOfSymbols.stream()
                    .map(stockService::lookupSymbol)
                    .filter(Objects::nonNull)
                    .filter(stockService::validate)
                    .map(stock->
                            String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                            stock.symbol, stock.name,stock.industry,stock.dividendYield,stock.marketCap,stock.revenue,stock.priceToBook,stock.priceToSales,stock.priceToEarnings,stock.freeCashFlow,stock.debtToEquity))
                    .collect(Collectors.toList());

            if (!listOfStocks.isEmpty()) {
                System.out.println("Writing to file...");
                try (BufferedWriter output = Files.newBufferedWriter(Paths.get("./stockData.csv"))) {
                    output.write(
                            String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\r\n",
                            "Symbol", "Name","Industry","Dividend","Market Cap","Revenue","P/B","P/S","P/E","Cashflow","Debt/Equity"));
                    for (String line:listOfStocks) {
                        output.write(String.format("%s\r\n",line));
                    }
                }
                catch(IOException io) {
                    io.printStackTrace();
                }
            }
        });
    }
}
