package org.aaa.cypto.MarketWatch;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.aaa.cypto.Coin.Coin;
import org.json.JSONException;
import java.time.LocalDateTime;

public class MarketWatch extends JFrame implements ActionListener {

    JButton button1, popCoinButton,marketIndicesButton;
    JTextField coinName;
    public void displayMessage(String message) {
        JFrame f=new JFrame();
        JOptionPane.showMessageDialog(f,message);
    }
    public MarketWatch(){

        JLabel coinNameLabel = new JLabel("Coin Name");
        coinNameLabel.setBounds(100,100 - 50,140,25);
        coinNameLabel.setFont(new Font("Comic Sans",Font.ITALIC,15));
        coinNameLabel.setForeground(Color.WHITE);

        coinName = new JTextField();
        coinName.setBounds(100+90,100- 50,140,25);
        coinName.setPreferredSize(new Dimension(120,25));

        button1 = new JButton("Get Data");
        button1.setBounds(246+90,100- 50,120,25);
        button1.addActionListener(this);
        button1.setBorder(BorderFactory.createEtchedBorder());
        button1.setBackground(new Color(23,23,23));
        button1.setFont(new Font("Comic Sans",Font.ITALIC,15));
        button1.setForeground(Color.WHITE);
        button1.setFocusable(false);

        popCoinButton = new JButton("Popular Coins");
        popCoinButton.setBounds(70+90,230 - 120,120,25);
        popCoinButton.addActionListener(this);
        popCoinButton.setBorder(BorderFactory.createEtchedBorder());
        popCoinButton.setBackground(new Color(23,23,23));
        popCoinButton.setFont(new Font("Comic Sans",Font.ITALIC,15));
        popCoinButton.setForeground(Color.WHITE);
        popCoinButton.setFocusable(false);

        marketIndicesButton = new JButton("Market Indices");
        marketIndicesButton.setBounds(210+90,230 - 120,120,25);
        marketIndicesButton.addActionListener(this);
        marketIndicesButton.setBorder(BorderFactory.createEtchedBorder());
        marketIndicesButton.setBackground(new Color(23,23,23));
        marketIndicesButton.setFont(new Font("Comic Sans",Font.ITALIC,15));
        marketIndicesButton.setForeground(Color.WHITE);
        marketIndicesButton.setFocusable(false);


        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600,400);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(133,205,202));
        this.setVisible(true);

        this.add(button1);
        this.add(coinNameLabel);
        this.add(coinName);
        this.add(popCoinButton);
        this.add(marketIndicesButton);


    }
    public void forEachCoin(Coin coin) throws JSONException {

        LocalDateTime date = LocalDateTime.now().minusDays((long)1.0);
        String d = date.toString().substring(0,10);
        String day = date.getDayOfWeek().toString();
        String LatestDay;
        String earlierDay =  date.minusDays((long) 7.0).toString().substring(0,10);
        if(day.equals("SUNDAY")){
            LatestDay = date.minusDays((long) 2.0).toString().substring(0,10);
            earlierDay = date.minusDays((long) 9.0).toString().substring(0,10);
        }
        else if(day.equals("SATURDAY")){
            LatestDay = date.minusDays((long) 1.0).toString().substring(0,10);
            earlierDay = date.minusDays((long) 8.0).toString().substring(0,10);
        }
        else{
            LatestDay = d;

        }

        System.out.println(String.format("%s | %.3f | %s",coin.getSymbol(), coin.getClosePrice(),   coin.CoinTrends()));

    }
    public void popularCryptoCurrencies() throws JSONException {

        System.out.println("*".repeat(50));
        System.out.println("Coin : Current price : Performance");
        Coin[] CoinArr = {new Coin("ethereum"), new Coin("litecoin"),new Coin("polygon") };
        for(Coin A : CoinArr){
            this.forEachCoin(A);
        }
        System.out.println("*".repeat(50));

    }
    public void marketIndices(){ // IS KACHRA KO EITHER CHANGE KARNA HAI YA HATANA HAI
        String s="";
        s+="\t\tMarket Indices";
        s+="\n"+"-".repeat(50);
        s+="\n"+"NYSE Composite index: $16,868.11";
        s+="\n"+"Dow Jones Industrial Average: $35,515.38";
        s+="\n"+"S&P 500 Index: $14,822.55";
        s+="\n"+"Russell 2000 Index: $2,223.11";
        s+="\n"+"Global Dow Realtime USD: $4,097.02";
        s+="\n"+"Dow Jones U.S. Total Stock Market Index: $46,820.65";
        s+="\n"+"NASDAQ 100 Index (NASDAQ Calculation): $15,136.68";
        s+="\n"+"NYSE Composite Index: $16,868.11";
        s+="\n"+"-".repeat(50);
        System.out.println(s);
        displayMessage(s);
    }
    public void completeCoinData(String symbol) throws JSONException {
        System.out.println("*".repeat(50));
        Coin coin = new Coin(symbol);
        forEachCoin(coin);
        System.out.println("*".repeat(50));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button1){
            try {
                System.out.println("*".repeat(50));
                System.out.println("Coin : Current price : Performance");
                forEachCoin(new Coin(coinName.getText().toString()));
                System.out.println("*".repeat(50));
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            displayMessage("Please check the console for the details");
        }
        if(e.getSource()==marketIndicesButton){
            this.marketIndices();
        }
        if(e.getSource()== popCoinButton){
            try {
                this.popularCryptoCurrencies();
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            displayMessage("Please check the console for the details");
        }
    }
}
