package org.aaa.cypto.Trade;


import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import org.aaa.cypto.Coin.Coin;
import org.aaa.cypto.CryptoAccount.CryptoAccount;
import org.json.JSONException;

import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import java.io.IOException;
import java.time.LocalDate;

public class Trade extends JFrame implements ActionListener{
    private CryptoAccount stAcc;
    private String userId;
    private String transaction;
    private String coinId;
    private int qty;
    private JFrame frmBuyCoin;
    private JTextField CoinId;
    private JTextField textField;
    private JButton btnClear;
    private JButton btnConfirm;
    private JComboBox tradeType;
    private JTextArea billArea;
    private JButton billButton;
    private JLabel account;
    private JCheckBox chckbxNewCheckBox;


    // public static void main(String[] args) {

    // }

    public Trade(String userId) {
        this.userId=userId;
        try {
            stAcc=new CryptoAccount(userId);
            System.out.println("This is me "+ stAcc);
        } catch (JSONException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        initialize();
    }

    public boolean qtyExists(String coin,int qty) {
        int no=stAcc.qtyCoin(coin);
        if(no>=qty)return true;
        return false;
    }

    private void initialize() {
        frmBuyCoin = this;
        frmBuyCoin.setTitle("Trade Crypto");
        frmBuyCoin.setBounds(100, 100, 800, 600);
        frmBuyCoin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmBuyCoin.getContentPane().setLayout(null);
        frmBuyCoin.getContentPane().setBackground(new Color(75, 203, 215));

        CoinId = new JTextField();
        CoinId.setFont(new Font("Tahoma", Font.PLAIN, 18));
        CoinId.setBounds(161, 116, 147, 36);
        frmBuyCoin.getContentPane().add(CoinId);
        CoinId.setColumns(10);

        JLabel lblNewLabel = new JLabel("Coin Id");
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel.setBounds(28, 119, 127, 33);
        frmBuyCoin.getContentPane().add(lblNewLabel);

        JLabel lblNoOfShares = new JLabel("No of Coin");
        lblNoOfShares.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNoOfShares.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNoOfShares.setBounds(28, 177, 127, 33);
        frmBuyCoin.getContentPane().add(lblNoOfShares);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        textField.setColumns(10);
        textField.setBounds(161, 175, 147, 36);
        frmBuyCoin.getContentPane().add(textField);

        JLabel lblTransaction = new JLabel("Transaction");
        lblTransaction.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTransaction.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblTransaction.setBounds(28, 72, 127, 33);
        frmBuyCoin.getContentPane().add(lblTransaction);

        tradeType = new JComboBox();
        tradeType.setModel(new DefaultComboBoxModel(new String[] {"Buy", "Sell"}));
        tradeType.setFont(new Font("Tahoma", Font.PLAIN, 18));
        tradeType.setToolTipText("Buy");
        tradeType.setEditable(true);
        tradeType.setBackground(new Color(255, 255, 255));
        tradeType.setBounds(161, 71, 127, 34);
        frmBuyCoin.getContentPane().add(tradeType);

        chckbxNewCheckBox = new JCheckBox("Send confirmation message");
        chckbxNewCheckBox.setSelected(true);
        chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 22));
        chckbxNewCheckBox.setBounds(130,231 , 346, 50);
        frmBuyCoin.getContentPane().add(chckbxNewCheckBox);

        String text="<html>Account details<br><br>User Id: "+userId;
        try {
            double bal=stAcc.getBalance();
            String val=String.format("%.2f", bal);
            text+="<br>Balance: "+val+"</html>";

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        account = new JLabel(text);
        account.setHorizontalAlignment(SwingConstants.CENTER);
        account.setFont(new Font("Tahoma", Font.PLAIN, 18));
        account.setBounds(427, 20, 349, 190);
        frmBuyCoin.getContentPane().add(account);

        billButton = new JButton("Generate bill");
        billButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        billButton.setBounds(116, 301, 171, 44);
        frmBuyCoin.getContentPane().add(billButton);
        btnClear = new JButton("Clear");
        btnClear.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnClear.setBounds(511, 301, 171, 44);
        frmBuyCoin.getContentPane().add(btnClear);

        billArea = new JTextArea();
        billArea.setEnabled(true);
        billArea.setEditable(false);
        billArea.setLineWrap(true);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
        billArea.setBounds(117, 402, 504, 135);
        frmBuyCoin.getContentPane().add(billArea);

        btnConfirm = new JButton("Confirm");
        this.setVisible(true);
        btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnConfirm.setBounds(319, 301, 171, 44);
        frmBuyCoin.getContentPane().add(btnConfirm);
        btnConfirm.addActionListener(this);
        btnClear.addActionListener(this);
        billButton.addActionListener(this);

    }

    public void displayMessage(String message) {
        JFrame f=new JFrame();
        JOptionPane.showMessageDialog(f,message);
    }

    public double possibleAmount(String coinId,int qty) {
        double price;
        double total=0;
        try {
            Coin coin=new Coin(coinId);
            price = coin.getClosePrice();
            total = qty*price;
        } catch (JSONException e1) {
            System.out.println("Enter a valid crypto symbol");
            e1.printStackTrace();
        }
        return total;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed");
        transaction=tradeType.getSelectedItem().toString();
        coinId = CoinId.getText();
        String message="";
        if(coinId.equals("")) {
            displayMessage("Please enter a valid Crypto Token name");
            return;
        }
        String num=textField.getText();
        if(num.equals("")) {
            displayMessage("Please enter a valid number");
            return;
        }
        qty=Integer.parseInt(num);
        if(e.getSource()==btnConfirm) {
            System.out.println("Confirm button pressed");
            double diff=0;
            double prev=0;
            boolean success=true;
            try {
                prev=stAcc.getBalance();
                System.out.println("This is the prev balance"+prev);
                System.out.println("Balance before transaction:"+String.format("%.2f", prev));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            double after=prev;

            if(transaction.equals("Buy")) {
                if(possibleAmount(coinId, qty)>prev) {
                    displayMessage("Transaction failed!\nYou do not have enough balance");
                    return;
                }
                try {
                    Coin coin=new Coin(coinId);
                    stAcc.buyToken(coinId, qty);
                    after=stAcc.getBalance();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if(transaction.equals("Sell")) {
                if(!stAcc.coinExists(coinId)) {
                    success=false;
                }
                if(!qtyExists(coinId, qty)) {
                    success=false;
                }

                if(success) {
                    try {
                        stAcc.sellToken(coinId, qty);
                        after=stAcc.getBalance();
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            }

            String bal=String.format("%.2f", after);
            System.out.println("Balance after transaction:"+bal);
            diff=after-prev;
            String diffstr=String.format("%.2f", Math.abs(diff));

            String text="<html>Account Details<br><br>User Id: "+userId+"<br>Balance: "+bal+"</html>";
            account.setText(text);

            if(chckbxNewCheckBox.isSelected()) {
                message="Transaction successful!";
                if(!success||diff==0) {
                    if(transaction.equals("Sell"))message="Transaction failed!\nYou do not own enough coin";
                    else message="Transaction failed!";
                }
                else if(diff>0)message+="\r\n"+diffstr+" added to your account";
                else message+="\r\n"+diffstr+" deducted from your account";
                displayMessage(message);
            }
        }
        if(e.getSource()==billButton) {
            double price=0;
            double total=0;
            try {
                Coin s=new Coin(coinId);
                price=s.getClosePrice();
                total=qty*price;
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            String stprice=String.format("%.2f", price);
            String totalstr=String.format("%.2f", total);
            billArea.setText("crypto symbol:"+ coinId +"\r\nTransaction type: "+transaction+"\r\ncoin price: "+stprice+"\r\nQuantity: "+qty+"\r\nAmount transacted: "+totalstr+"\r\nDate: "+LocalDate.now().toString());
        }
        if(e.getSource()==btnClear) {
            CoinId.setText("");
            textField.setText("");
            billArea.setText("");
        }
    }
}




