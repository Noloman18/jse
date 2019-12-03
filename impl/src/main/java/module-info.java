module jse.impl.main {
    requires jse.core.main;
    requires org.jsoup;
    provides com.gmail.noloxtreme.investments.core.StockService with com.gmail.noloxtreme.investments.core.impl.ReutersStockService;
}