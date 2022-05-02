package org.aaa.cypto.Coin;

import org.aaa.cypto.ParsingJSON.ParsingJSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.time.LocalDateTime;

public class Coin {
    JSONObject js;
    JSONObject mainData;
    JSONArray data;
    private String openPrice;
    private String closePrice;
    private String volume;
    private String Symbol;
    private double btc_price;
    public Coin(String Symbol) throws JSONException {
        this.Symbol = Symbol;
        String link = "https://api.coincap.io/v2/rates/bitcoin";
        String main_api = "https://api.coincap.io/v2/candles?exchange=poloniex&interval=h8&baseId="+Symbol+"&quoteId=bitcoin";
        ParsingJSON ps = new ParsingJSON(link);
        ParsingJSON main_ps = new ParsingJSON(main_api);
        String thisCryptoData = ps.giveString();
        String mainThisCoinData = main_ps.giveString();
        try {
            js = new JSONObject(thisCryptoData);
            mainData = new JSONObject(mainThisCoinData);
            data = mainData.getJSONArray("data");
        } catch (JSONException e) {
            js = new JSONObject("GetInfo");
            mainData = new JSONObject("GetInfo");
            JSONArray data = new JSONArray("GetInfo");
        }

        LocalDateTime date = LocalDateTime.now().minusDays((long)1.0);
        String d = date.toString().substring(0,10);
        String day = date.getDayOfWeek().toString();
        String LatestDay ;
        if(day.equals("SUNDAY")){
            LatestDay = date.minusDays((long) 2.0).toString().substring(0,10);
        }
        else if(day.equals("SATURDAY")){
            LatestDay = date.minusDays((long) 1.0).toString().substring(0,10);
        }
        else{
            LatestDay = d;
        }

        this.btc_price = Double.parseDouble(js.getJSONObject("data").getString("rateUsd"));

        JSONObject temp = data.getJSONObject(data.length()-1);

        this.openPrice = String.valueOf(Double.parseDouble(temp.getString("open"))*btc_price);
        this.closePrice = String.valueOf(Double.parseDouble(temp.getString("close"))*btc_price);
        this.volume = temp.getString("volume");

    }

    public double getOpenPrice() {
        return Double.parseDouble(openPrice);
    }
    public double getOpenPrice(String date) throws JSONException {
        return Double.parseDouble(data.getJSONObject(data.length()-1).getString("open"));
    }
    public double getClosePrice(){
        return Double.parseDouble(closePrice);
    }
    public double getClosePrice(String date) throws JSONException {
        return Double.parseDouble(data.getJSONObject(data.length()-1).getString("close"));
    }
    public double getClosePriceCoin(int i){
        return Double.parseDouble(data.getJSONObject(data.length()-i).getString("close"));
    }
    public double getVolume() throws JSONException {
        return Double.parseDouble(this.volume);
    }
    public double getVolume(String date) throws JSONException {
        return Double.parseDouble(data.getJSONObject(data.length()-1).getString("volume"));
    }
    public String getSymbol() {
        return Symbol;
    }

    //     CoinTrends()->String , returns how much the coin value has increased/decreased in the given time period
    public String CoinTrends() throws JSONException {
        double price1 = this.getClosePriceCoin(1);
        double price2 = this.getClosePriceCoin(2);
        if (price1>price2){
            return String.format("Coin Increased by %.4f ",(price1 - price2)*100/price2)+"%";
        }
        return String.format("Coin Decreased by %.4f ",Math.abs((price1 - price2)*100/price2))+"%";

    }
}
