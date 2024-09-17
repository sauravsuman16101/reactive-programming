package io.learn.reactiveprogramming.stockexample;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * StockPriceObserver implements a reactive trading strategy for stock prices.
 * It acts as a Subscriber in the Reactive Streams pattern, processing a stream of stock prices
 * and making buy/sell decisions based on predefined price thresholds.
 *
 * Key Features:
 * - Implements a simple trading strategy with fixed buy and sell points.
 * - Manages a virtual balance and stock quantity.
 * - Demonstrates backpressure handling through subscription request management.
 * - Showcases error handling and completion scenarios in reactive streams.
 */
public class StockPriceObserver implements Subscriber<Integer> {
    private static final Logger log = LoggerFactory.getLogger(StockPriceObserver.class);
    private static Integer BALANCE = 1000; // Initial trading balance
    private Subscription subscription; // Manages the subscription to the price stream
    private Integer quantity = 0; // Number of stocks currently held

    /**
     * Called when the Observer subscribes to a Publisher.
     * This method sets up the initial subscription and requests an unlimited number of items.
     *
     * @param subscription The Subscription that allows requesting items from the Publisher.
     */
    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(Long.MAX_VALUE); // Request all available items
        log.info("Subscribed to stock price stream. Initial balance: {}", BALANCE);
    }

    /**
     * Processes each new stock price emitted by the Publisher.
     * Implements the trading strategy:
     * - Buy when price < 90 and sufficient balance available
     * - Sell all when price > 110
     *
     * @param price The current stock price
     */
    @Override
    public void onNext(Integer price) {
        if (price < 90 && BALANCE >= price) {
            buyStock(price);
        } else if (price > 110 && quantity > 0) {
            sellAllStocks(price);
        } else {
            log.info("Current price: {}. No action taken.", price);
        }
    }

    /**
     * Handles errors in the price stream.
     * In a real-world scenario, this could involve more sophisticated error recovery strategies.
     *
     * @param throwable The error that occurred
     */
    @Override
    public void onError(Throwable throwable) {
        log.error("Error in stock price stream: {}", throwable.getMessage());
    }

    /**
     * Called when the price stream completes.
     * In this implementation, it logs the final trading status.
     */
    @Override
    public void onComplete() {
        log.info("Stock price stream completed. Final balance: {}, Stocks held: {}", BALANCE, quantity);
    }

    /**
     * Helper method to execute a stock purchase.
     *
     * @param price The price at which to buy the stock
     */
    private void buyStock(int price) {
        quantity++;
        BALANCE -= price;
        log.info("Bought 1 stock at {}. Total quantity: {}, Remaining balance: {}", price, quantity, BALANCE);
    }

    /**
     * Helper method to execute a sale of all held stocks.
     *
     * @param price The price at which to sell the stocks
     */
    private void sellAllStocks(int price) {
        int saleProceeds = price * quantity;
        int profit = saleProceeds + BALANCE - 1000; // Calculate profit
        BALANCE += saleProceeds;
        log.info("Sold {} stocks at {}. New balance: {}, Profit: {}", quantity, price, BALANCE, profit);
        quantity = 0;
        subscription.cancel(); // End the trading session after selling
    }
}

/**
 * Additional insights from the stockexample folder:
 *
 * 1. Integration with ExternalServiceClient:
 *    - This observer is designed to work with the price stream from ExternalServiceClient.
 *    - ExternalServiceClient simulates price changes every 500ms, allowing for frequent trading opportunities.
 *
 * 2. Relationship with StockPriceImpl:
 *    - StockPriceImpl creates an instance of this observer and connects it to the price stream.
 *    - The trading session duration is controlled externally by StockPriceImpl.
 *
 * 3. Trading Strategy Details:
 *    - Buy Threshold: Purchases stocks when the price drops below 90.
 *    - Sell Threshold: Sells all stocks when the price exceeds 110.
 *    - This simple strategy aims to "buy low, sell high" with fixed thresholds.
 *
 * 4. Balance and Quantity Management:
 *    - Starts with an initial balance of 1000 (currency units not specified).
 *    - Tracks the quantity of stocks owned and updates it with each transaction.
 *    - Ensures purchases are only made when sufficient balance is available.
 *
 * 5. Reactive Programming Aspects:
 *    - Backpressure: Requests unlimited items (Long.MAX_VALUE) from the subscription.
 *    - Error Handling: Includes an onError method to handle stream errors gracefully.
 *    - Completion Handling: onComplete method logs final trading status.
 *    - Cancellation: Cancels the subscription after selling all stocks, ending the trading session.
 *
 * 6. Logging and Monitoring:
 *    - Utilizes SLF4J for comprehensive logging of trading activities and status updates.
 *    - Logs each buy/sell action, current holdings, and balance for real-time monitoring.
 *
 * 7. Potential Enhancements:
 *    - Dynamic thresholds based on market trends or time-based strategies.
 *    - Support for multiple stock types or portfolio management.
 *    - Integration with real market data sources instead of simulated prices.
 *    - More sophisticated profit calculation and performance metrics.
 *
 * This implementation demonstrates a basic yet functional reactive trading system,
 * showcasing how reactive programming can be applied to financial applications.
 * It provides a foundation that can be extended to more complex trading strategies
 * and real-world market interactions.
 */
