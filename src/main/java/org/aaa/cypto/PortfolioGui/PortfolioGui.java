package org.aaa.cypto.PortfolioGui;



import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import org.aaa.cypto.Coin.Coin;
import org.aaa.cypto.Portfolio.Portfolio;
import org.json.JSONException;

public class PortfolioGui extends JFrame implements ActionListener{

    private JFrame frmPortfolio;
    private String userId;
    JButton port;
    JButton purchase;
    JButton sale;
    JButton coinDetails;
    JTextField text;

    /**
    /**
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
//		PortfolioGui gui=new PortfolioGui("EsNN");
    }

    /**
     * Create the application.
     */
    public PortfolioGui(String userId) {
        this.userId=userId;
        initialize();
    }
    public void displayMessage(String message) {
        JFrame f=new JFrame();
        JOptionPane.showMessageDialog(f,message);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmPortfolio = this;
        frmPortfolio.setTitle("Portfolio");
        frmPortfolio.getContentPane().setForeground(Color.DARK_GRAY);
        frmPortfolio.setBounds(100, 100, 800, 600);
        frmPortfolio.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmPortfolio.getContentPane().setLayout(null);
        frmPortfolio.getContentPane().setBackground(new Color(133,205,202));
        frmPortfolio.setVisible(true);

        port = new JButton("View Portfolio");
        purchase = new JButton("Purchase History");
        sale = new JButton("Sale History");
        coinDetails = new JButton(" View Details");
        text = new JTextField();


        port.setFont(new Font("Tahoma", Font.PLAIN, 18));
        port.setBounds(71, 62, 177, 43);
        frmPortfolio.getContentPane().add(port);

        purchase.setFont(new Font("Tahoma", Font.PLAIN, 18));
        purchase.setBounds(71, 143, 210, 43);
        frmPortfolio.getContentPane().add(purchase);

        sale.setFont(new Font("Tahoma", Font.PLAIN, 18));
        sale.setBounds(71, 233, 177, 43);
        frmPortfolio.getContentPane().add(sale);

        coinDetails.setFont(new Font("Tahoma", Font.PLAIN, 18));
        coinDetails.setBounds(498, 143, 177, 43);
        frmPortfolio.getContentPane().add(coinDetails);

        text.setBounds(498, 62, 177, 43);
        frmPortfolio.getContentPane().add(text);

        port.addActionListener(this);
        sale.addActionListener(this);
        purchase.addActionListener(this);
        coinDetails.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Portfolio p=null;
        try {
            p=new Portfolio(userId);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            if(e.getSource()==port) {
                p.displayFullPortfolio();
            }
            if(e.getSource()==purchase) {
                p.displayPurchaseHistory();
            }
            if(e.getSource()==sale) {
                p.displaySaleHistory();
            }
            if(e.getSource()== coinDetails) {
                String coin = text.getText();
                if(coin.equals("")) {
                    displayMessage("Please enter a CryptoCurrency Name (e.g. polygon)");
                    return;
                }
                Coin s=new Coin(coin);
                p.displayCoinPortfolio(s);
            }
        }catch (JSONException e1) {
            e1.printStackTrace();
        }

    }
}

