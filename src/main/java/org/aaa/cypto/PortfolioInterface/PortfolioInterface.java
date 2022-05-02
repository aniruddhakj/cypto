package org.aaa.cypto.PortfolioInterface;

import org.aaa.cypto.Coin.Coin;
import org.json.JSONException;

public interface PortfolioInterface {
    public int getCoinHolding(Coin s) throws JSONException;
    public double getCoinValue(Coin s) throws NumberFormatException, JSONException;
    public double getCoinInvestment(Coin s) throws JSONException;
    public double getCoinSales(Coin s) throws JSONException;
    public double getCoinProfit(Coin s) throws NumberFormatException, JSONException;
    public int getOverallHolding() throws JSONException;

    public double	getOverallValue() throws JSONException;
    public double getOverallInvestment() throws JSONException;
    public double getOverallSales() throws JSONException;
    public double getOverallProfit() throws NumberFormatException, JSONException;

}