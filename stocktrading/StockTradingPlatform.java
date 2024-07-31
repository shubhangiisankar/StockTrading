import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return symbol + ": $" + price;
    }
}

class Portfolio {
    private Map<String, Integer> holdings; // Maps stock symbol to quantity

    public Portfolio() {
        holdings = new HashMap<>();
    }

    public void buyStock(String symbol, int quantity) {
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
    }

    public void sellStock(String symbol, int quantity) {
        if (holdings.containsKey(symbol)) {
            int currentQuantity = holdings.get(symbol);
            if (currentQuantity >= quantity) {
                holdings.put(symbol, currentQuantity - quantity);
                if (holdings.get(symbol) == 0) {
                    holdings.remove(symbol);
                }
            } else {
                System.out.println("Insufficient quantity to sell.");
            }
        } else {
            System.out.println("Stock not found in portfolio.");
        }
    }

    public void printPortfolio() {
        if (holdings.isEmpty()) {
            System.out.println("No stocks in portfolio.");
            return;
        }
        System.out.println("Current Portfolio:");
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " shares");
        }
    }

    public double calculatePortfolioValue(Map<String, Stock> marketData) {
        double totalValue = 0.0;
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            String symbol = entry.getKey();
            int quantity = entry.getValue();
            Stock stock = marketData.get(symbol);
            if (stock != null) {
                totalValue += stock.getPrice() * quantity;
            }
        }
        return totalValue;
    }
}

public class StockTradingPlatform {
    private Map<String, Stock> marketData;
    private Portfolio portfolio;
    private Scanner scanner;

    public StockTradingPlatform() {
        marketData = new HashMap<>();
        portfolio = new Portfolio();
        scanner = new Scanner(System.in);

        // Initialize some stock data with random details
        marketData.put("AAPL", new Stock("AAPL", 150.0));
        marketData.put("GOOGL", new Stock("GOOGL", 2800.0));
        marketData.put("TSLA", new Stock("TSLA", 700.0));
        marketData.put("MSFT", new Stock("MSFT", 320.0));
        marketData.put("AMZN", new Stock("AMZN", 3300.0));
        marketData.put("NFLX", new Stock("NFLX", 450.0));
        marketData.put("META", new Stock("META", 280.0));
        marketData.put("DIS", new Stock("DIS", 100.0));
        marketData.put("NVDA", new Stock("NVDA", 580.0));
        marketData.put("BA", new Stock("BA", 200.0));
    }

    public void start() {
        while (true) {
            System.out.println("1. View market data");
            System.out.println("2. Buy stock");
            System.out.println("3. Sell stock");
            System.out.println("4. View portfolio");
            System.out.println("5. View portfolio value");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            
            int option = readInt();

            switch (option) {
                case 1:
                    viewMarketData();
                    break;
                case 2:
                    buyStock();
                    break;
                case 3:
                    sellStock();
                    break;
                case 4:
                    portfolio.printPortfolio();
                    break;
                case 5:
                    double portfolioValue = portfolio.calculatePortfolioValue(marketData);
                    System.out.println("Current portfolio value: $" + portfolioValue);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void viewMarketData() {
        System.out.println("Market Data:");
        for (Stock stock : marketData.values()) {
            System.out.println(stock);
        }
    }

    private void buyStock() {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.nextLine().toUpperCase();
        System.out.print("Enter quantity to buy: ");
        int quantity = readInt();

        if (marketData.containsKey(symbol)) {
            portfolio.buyStock(symbol, quantity);
            System.out.println("Bought " + quantity + " shares of " + symbol);
        } else {
            System.out.println("Stock symbol not found.");
        }
    }

    private void sellStock() {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.nextLine().toUpperCase();
        System.out.print("Enter quantity to sell: ");
        int quantity = readInt();

        portfolio.sellStock(symbol, quantity);
        System.out.println("Sold " + quantity + " shares of " + symbol);
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

    public static void main(String[] args) {
        StockTradingPlatform platform = new StockTradingPlatform();
        platform.start();
    }
}
