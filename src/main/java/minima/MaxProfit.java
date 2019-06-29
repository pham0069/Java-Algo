package minima;

/**
 * You are given an array of prices in ascending order of time, i.e. prices[i] occurs before
 * prices[j] for i < j. A trade is defined by making a buy followed by a sell.
 * Find the maximum profit earn if:
 * (i) u can only make one single trade
 * (ii) u can make at most two trades
 * (iii) u can make multiple trades, given that only 1 trade is opened at a time, i.e. you can
 * buy, sell, buy, sell, ... alternatively in the order of time sequence
 */
public class MaxProfit {
    public static void main(String[] args){
        double[] prices = {6, 4, 2, 7, 5, 1, 8, 15, 20, 2, 5, 8, 12, 13, 17, 11};
        //double[] prices = {1.0, 1.0, 0.9, 0.8, 0.8, 1.2, 1.8, 1.8, 0.5, 1.2, 1.4, 10};
        System.out.println(getSingleTradeMaxProfit_YuZhen(prices));
        System.out.println(getSingleTradeMaxProfit_Quan(prices));
        System.out.println(getAtMostTwoTradesMaxProfit(prices));
        System.out.println(getMultipleTradesMaxProfit(prices));
    }
    static double getSingleTradeMaxProfit_YuZhen(double[] prices){
        if (prices.length < 2)
            return 0;
        double profit = 0;
        double maxProfit = 0;
        for (int i = 1; i < prices.length; i++){
            double delta = prices[i] - prices[i-1];
            profit += delta;
            if (profit < 0)
                profit = 0;	//profit reset from the lowest prices up to now
            if (delta > 0)
                maxProfit  = Math.max(maxProfit, profit);
        }
        return maxProfit;
    }

    /**
     * If you sell at prices[i], you can make max profit by buying at the minimum price in the range of 0 to (i-1)
     * Finding the minimum price in such sub-array can be done together with finding maximum profit when traversing the
     * prices array
     * @param prices
     * @return
     */
    static double getSingleTradeMaxProfit_Quan(double[] prices){
        if (prices.length < 2)
            return 0;
        double maxProfit = 0;
        double min = prices[0];
        for (int i = 1; i < prices.length; i++){
            maxProfit = Math.max(maxProfit, prices[i] - min);
            if (prices[i] < min)
                min = prices[i];
        }
        return maxProfit;
    }

    /**
     * If you make the first trade at selling point of prices[i], then you can only make the second trade in the range
     * of (i+1) to (length-1). We can calculate the max profit achievable in range 0-i and max profit achievable in
     * range (i+1)-(length-1) to get the max profit achievable when we have to sell for the first time at prices[i]
     * @param prices
     * @return
     */
    static double getAtMostTwoTradesMaxProfit(double[] prices){
        int length = prices.length;
        if (length < 2)
            return 0;
        if (length < 4)
            return getSingleTradeMaxProfit_Quan(prices);

        // upper[i] is max profit achievable if selling at or before prices[i]
        double[] upper = new double[length];
        double min = prices[0];
        for (int i = 1; i < length; i++){
            upper[i] = Math.max(upper[i-1], prices[i] - min);
            min = Math.min(min, prices[i]);
        }

        // lower[i] is max profit achievable if buying at or after prices[i]
        double[] lower = new double[length];
        double max = prices[length-1];
        for (int i = length-2; i >= 0; i--){
            lower[i] = Math.max(lower[i+1], max - prices[i]);
            max = Math.max(max, prices[i]);
        }

        /**
         * it is possible to merge this loop with previous loop to save space (lower) and time
         * keep here for easier understanding
         */
        double totalMaxProfit = 0;
        //i = 0 is equivalent to make single trade
        for (int i = 0; i < length-1; i++) {
            totalMaxProfit = Math.max(totalMaxProfit, upper[i] + lower[i+1]);
        }

        return totalMaxProfit;
    }

    public static double getMultipleTradesMaxProfit(double[] prices){
        if (prices.length < 2)
            return 0;
        double maxProfit = 0;
        boolean increasing = prices[1] > prices[0];
        Double buy = increasing?prices[0]:null;
        int i;
        for (i = 1; i < prices.length-1; i++){
            if (increasing && (prices[i] > prices[i+1])) {
                maxProfit += (prices[i]-buy);
                increasing = false;
            } else if (!increasing && (prices[i] < prices[i+1])){
                buy = prices[i];
                increasing = true;
            }
        }
        if (buy != null)
            maxProfit += (prices[i]-buy);
        return maxProfit;
    }
}
