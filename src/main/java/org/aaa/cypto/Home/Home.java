package org.aaa.cypto.Home;


import org.aaa.cypto.MarketWatch.MarketWatch;
import org.aaa.cypto.MyAccount.MyAccount;
import org.aaa.cypto.Portfolio.Portfolio;
import org.aaa.cypto.PortfolioGui.PortfolioGui;
import org.aaa.cypto.Trade.Trade;
import org.aaa.cypto.Wishlist.Wishlist;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;


import org.json.JSONException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Home extends JFrame implements ActionListener{

    private JFrame frmCoinAccount;
    //	PossibleMain2 mainObj;
    JButton Trade_Button, My_Coins_Button , Portfolio_Button;
    JButton Market_Performance_Button, Wishlist_Button, My_Account_Button;
    JLabel textLabel,welcomeLabel;
    private Portfolio port;
    private JTable table;

    String UserID;
    public Home(String UserID) {
        this.UserID = UserID;

        try {
            port=new Portfolio(UserID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initialize();
//		mainObj=new PossibleMain2();
    }
    // public static void main(String args[]) {

    // }
    public void displayMessage(String message) {
        JFrame f=new JFrame();
        JOptionPane.showMessageDialog(f,message);
    }
    public void displayCoins() throws Exception {
//		CoinAccount acc=new CoinAccount(UserID);
        Portfolio port=new Portfolio(UserID);
        LinkedList<String> list=new LinkedList<>();
//		list=acc.CoinList();
        list=port.displayCoinList();
        String str="Your Crypto Tokens list";
        for(int i=0;i<list.size();i++) {
            str+="\n"+(i+1)+"."+list.get(i);
        }
        str+="\nPlease check the portfolio for more details";
        displayMessage(str);
//
    }

    private void initialize() {

        frmCoinAccount = this;
        frmCoinAccount.getContentPane().setForeground(Color.DARK_GRAY);
        frmCoinAccount.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 18));
        frmCoinAccount.getContentPane().setName("Buy coin");
        frmCoinAccount.setTitle("coin Account");
        frmCoinAccount.setBounds(100, 100, 900, 600);
        frmCoinAccount.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmCoinAccount.getContentPane().setBackground(new Color(220, 143,102));
        frmCoinAccount.setVisible(true);

        My_Coins_Button = new JButton("My coin");
        Portfolio_Button = new JButton("Portfolio");
        My_Account_Button = new JButton("My Account");
        Wishlist_Button = new JButton("Wishlist");
        Market_Performance_Button = new JButton("Market Watch");
        Trade_Button = new JButton("Trade");
        welcomeLabel = new JLabel("Welcome "+UserID);
        textLabel=new JLabel();

        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        welcomeLabel.setBounds(312, 10, 240, 30);
        frmCoinAccount.getContentPane().add(welcomeLabel);

        Trade_Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
        Trade_Button.setBounds(47, 73, 147, 38);
        frmCoinAccount.getContentPane().add(Trade_Button);

        My_Coins_Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
        My_Coins_Button.setBounds(312, 73, 147, 38);
        frmCoinAccount.getContentPane().add(My_Coins_Button);

        Portfolio_Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
        Portfolio_Button.setBounds(573, 73, 170, 38);
        frmCoinAccount.getContentPane().add(Portfolio_Button);

//        Wishlist_Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
//        Wishlist_Button.setBounds(47, 157, 170, 38);
//        frmCoinAccount.getContentPane().add(Wishlist_Button);

        My_Account_Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
        My_Account_Button.setBounds(312, 157, 170, 38);
        frmCoinAccount.getContentPane().add(My_Account_Button);

        Market_Performance_Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
        Market_Performance_Button.setBounds(47, 157, 170, 38);
        frmCoinAccount.getContentPane().add(Market_Performance_Button);

        textLabel.setBounds(47, 244, 702, 281);
        String intro="<html><br><br><br>1.Share of coin means you have ownership into a cryptocurrency.<br>2.Coin prices vary throughout the day and everyday.";
        intro+="<br>3.A coin is an investment that can lose money.<br>4.It's good to buy low and sell high.<br>";
        intro+="<br>Symbols of some popular coin are:<br>BTC - Bitcoin<br>ETH - Ethereum<br>XRP - Polygon</html>";
        textLabel.setText(intro);
        textLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        frmCoinAccount.getContentPane().add(textLabel);

        Trade_Button.addActionListener(this);
        My_Account_Button.addActionListener(this);
        Market_Performance_Button.addActionListener(this);
        Portfolio_Button.addActionListener(this);
        My_Coins_Button.addActionListener(this);
        Wishlist_Button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==Trade_Button){
            //trade here
            System.out.println("Trading ! ");
            Trade trade=new Trade(UserID);
        }
        if(e.getSource()==My_Coins_Button){

            System.out.println("My coins ! ");
            try {
                displayCoins();
            } catch (JSONException | IOException e2) {
                e2.printStackTrace();
            }
            catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            frmCoinAccount.getContentPane().add(textLabel);
        }
        if(e.getSource()==Market_Performance_Button){
            // market performance
            System.out.println("Market performance");
            MarketWatch watch=new MarketWatch();
        }
        if(e.getSource()==My_Account_Button){
            // My account
            System.out.println("My Account ! ");
            try {
                MyAccount acc=new MyAccount(UserID);
            } catch (JSONException | IOException e1) {
                e1.printStackTrace();
            }
        }
        if(e.getSource()==Portfolio_Button){
            //portfolio
            System.out.println("Portfolio ! ");
            PortfolioGui port=new PortfolioGui(UserID);
        }
        if(e.getSource()==Wishlist_Button){
            //wishlist
            System.out.println("Wishlist ! ");
            Wishlist wish=new Wishlist(UserID);
        }

    }
}
