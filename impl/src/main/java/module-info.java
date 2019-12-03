module jse.impl.main {
    requires jse.core.main;
    provides com.gmail.noloxtreme.investments.core.StockService with com.gmail.noloxtreme.investments.core.impl.ReutersStockService;
}