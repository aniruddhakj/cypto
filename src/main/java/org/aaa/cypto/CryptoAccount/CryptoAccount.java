package org.aaa.cypto.CryptoAccount;
import org.aaa.cypto.Account.Account;
import org.aaa.cypto.Coin.Coin;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.*;

public class CryptoAccount extends Account {

    JSONObject UserAccount_Data,USERDATA,dataUAD,dataUD;
    private final String UserID;
    public CryptoAccount(String UserID) throws JSONException, IOException {
        super(UserID);
        String s="";
        this.UserID = UserID;
        FileReader reader = new FileReader("/Users/aniruddhakj/Desktop/Java Lab/cypto/src/main/java/org/aaa/cypto/UserAccount_Data.json");
        Scanner scan = new Scanner(reader);
        while(scan.hasNext()){
            s = s + scan.nextLine();
        }
        reader.close();
        dataUAD = new JSONObject(s);
        UserAccount_Data = dataUAD.getJSONObject(UserID);

        String s3 = "";
        FileReader readerx = new FileReader("/Users/aniruddhakj/Desktop/Java Lab/cypto/src/main/java/org/aaa/cypto/USERDATA.json");
        Scanner scanx = new Scanner(readerx);
        while(scanx.hasNext()){
            s3  = s3 + scanx.nextLine();
        }
        readerx.close();
        dataUD = new JSONObject(s3);
        USERDATA = dataUD.getJSONObject(UserID);

    }

    public boolean coinExists(String coin) {
        LinkedList<String> list= CoinList();
        for(String item:list) {
            if(item.equals(coin))return true;
        }
        return false;
    }

    public int qtyCoin(String symbol) {
        if(!coinExists(symbol))return 0;
        int qty=0;
        try {
            qty = Integer.parseInt(UserAccount_Data.getJSONObject(symbol).get("CurrentHoldings").toString());
        } catch (NumberFormatException | JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return qty;
    }

    public String getUserID(){
        return this.UserID;
    }
    public String getUsername() throws JSONException {
        return USERDATA.getString("userName");
    }
    public double getBalance() throws JSONException {
        return Double.valueOf(USERDATA.getDouble("balance"));
    }

    public LinkedList<String> CoinList(){
        LinkedList<String> listOfCoins = new LinkedList<String>();
        Iterator it = UserAccount_Data.keys();
        while(it.hasNext()){
            listOfCoins.add(it.next().toString());
        }
        return listOfCoins;
    }

    public void addMoney(double amount) throws JSONException, IOException {
        double currentBalance = Double.valueOf(USERDATA.get("balance").toString()) ;
        USERDATA.put("balance",currentBalance + amount);

        dataUD.put(this.getUserID(),USERDATA);
        FileWriter file = new FileWriter("/Users/aniruddhakj/Desktop/Java Lab/cypto/src/main/java/org/aaa/cypto/USERDATA.json");
        file.write(dataUD.toString());
        file.close();

    }
    public void displayMessage(String message) {
        JFrame f=new JFrame();
        JOptionPane.showMessageDialog(f,message);
    }
    public void deductMoney(double amount) throws IOException, JSONException {
        double currentBalance = Double.valueOf(USERDATA.get("balance").toString()) ;
        if(currentBalance-amount<0)
        {
            displayMessage("Not enough balance in account");
            return;
        }
        USERDATA.put("balance",currentBalance - amount);

        dataUD.put(this.getUserID(),USERDATA);
        FileWriter file = new FileWriter("/Users/aniruddhakj/Desktop/Java Lab/cypto/src/main/java/org/aaa/cypto/USERDATA.json");
        file.write(dataUD.toString());
        file.close();
    }

    public void buyToken(String symbol, int noOfShares) throws JSONException, IOException {
        System.out.println("Buying "+noOfShares+" shares of "+symbol);
        Coin coin = new Coin(symbol);
        LinkedList<String> listOfCoins = this.CoinList();
        boolean exists = false;
        for(int i=0;i<listOfCoins.size();i++){
            if(listOfCoins.get(i).toString().equals(symbol)){
                exists = true;
                break;
            }
        }
        if(exists){
            UserAccount_Data.getJSONObject(symbol).getJSONObject("buys").put(String.valueOf(LocalDateTime.now().minusDays((long) 1)).substring(0,10),noOfShares);
            int currentHoldings = Integer.parseInt(UserAccount_Data.getJSONObject(symbol).get("CurrentHoldings").toString());
            UserAccount_Data.getJSONObject(symbol).put("CurrentHoldings",currentHoldings + noOfShares);
            System.out.println("Successful");
        }
        else{
            JSONObject coinJSONObject = new JSONObject();
            JSONObject buys = new JSONObject();
            JSONObject sells = new JSONObject();

            buys.put(String.valueOf(LocalDateTime.now().minusDays((long) 1)).substring(0,10),noOfShares);

            coinJSONObject.put("buys",buys);
            coinJSONObject.put("sells",sells);

            coinJSONObject.put("CurrentHoldings",noOfShares);

            UserAccount_Data.put(symbol,coinJSONObject);
        }

        double currPrice = new Coin(symbol).getClosePrice();
        this.deductMoney(currPrice*noOfShares);

        dataUAD.put(this.getUserID(),UserAccount_Data);
        FileWriter file = new FileWriter("/Users/aniruddhakj/Desktop/Java Lab/cypto/src/main/java/org/aaa/cypto/UserAccount_Data.json");
        file.write(dataUAD.toString());
        file.close();

    }

    public void sellToken(String symbol, int noOfShares) throws JSONException, IOException {
        Coin coin = new Coin(symbol);

        UserAccount_Data.getJSONObject(symbol).getJSONObject("sells").put(String.valueOf(LocalDateTime.now().minusDays((long) 1)).substring(0,10),noOfShares);
        int currentHoldings = Integer.parseInt(UserAccount_Data.getJSONObject(symbol).get("CurrentHoldings").toString());
        UserAccount_Data.getJSONObject(symbol).put("CurrentHoldings",currentHoldings - noOfShares);
        double currPrice = new Coin(symbol).getClosePrice();
        this.addMoney(currPrice*noOfShares);
        dataUAD.put(this.getUserID(),UserAccount_Data);
        FileWriter file = new FileWriter("/Users/aniruddhakj/Desktop/Java Lab/cypto/src/main/java/org/aaa/cypto/UserAccount_Data.json");
        file.write(dataUAD.toString());
        file.close();

    }
}

