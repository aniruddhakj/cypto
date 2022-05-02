package org.aaa.cypto;


import org.aaa.cypto.OnOpen.OnOpen;
import org.aaa.cypto.ParsingJSON.ParsingJSON;
import org.json.JSONException;
import org.json.JSONObject;


import org.json.JSONArray;

public class App {
    public static void main(String[] args) throws Exception {

        JSONObject js;
        JSONObject mainData;
        String link = "https://api.coincap.io/v2/rates/bitcoin";
        String main_api = "https://api.coincap.io/v2/candles?exchange=poloniex&interval=h8&baseId=litecoin&quoteId=bitcoin";
        ParsingJSON ps = new ParsingJSON(link);
        ParsingJSON main_ps = new ParsingJSON(main_api);
        String thisCryptoCurrencyData = ps.giveString();
        String mainThisCryptoCurrencyData = main_ps.giveString();
        try {
            js = new JSONObject(thisCryptoCurrencyData);
            mainData = new JSONObject(mainThisCryptoCurrencyData);
        } catch (JSONException e) {
            js = new JSONObject("GetInfo");
            mainData = new JSONObject("GetInfo");
        }


        double btc_price = Double.parseDouble(js.getJSONObject("data").getString("rateUsd"));
        JSONArray data = mainData.getJSONArray("data");

        JSONObject temp = data.getJSONObject(data.length()-1);
        String openPrice = String.valueOf(Double.parseDouble(temp.getString("open"))*btc_price);
        String closePrice = String.valueOf(Double.parseDouble(temp.getString("close"))*btc_price);

        OnOpen onOpen = new OnOpen();
    }
}
