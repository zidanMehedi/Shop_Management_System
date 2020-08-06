import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.*;
import java.sql.*;
import java.awt.*;

public class PurchaseInfo extends JFrame implements ActionListener
{
	JLabel userLabel, productLabel, proIdLabel, purchaseLabel, quantityLabel, totalPriceLabel;
	JLabel userIdLabel, productNameLabel, pIdLabel, purchaseIdLabel, quantityAmountLabel, totalPriceAmountLabel;
	JButton logoutBtn, backBtn, viewPurInfoBtn;
	JPanel panel;
	String userId;
	public PurchaseInfo(String userId)
	{
		
		super("Purchase Information");
		this.setSize(800,450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		Color panelColor = new Color(0, 128, 255);
		
		panel = new JPanel();
		panel.setBackground(panelColor);
		panel.setLayout(null);
		
		userLabel = new JLabel("User Id : ");
		userLabel.setBounds(250, 50, 120, 30);
		panel.add(userLabel);
		
		userIdLabel = new JLabel(userId);
		userIdLabel.setBounds(400, 50, 120, 30);
		panel.add(userIdLabel);
		
		productLabel = new JLabel("Product Name : ");
		productLabel.setBounds(250, 80, 120, 30);
		panel.add(productLabel);
		
		productNameLabel = new JLabel();
		productNameLabel.setBounds(400, 80, 120, 30);
		panel.add(productNameLabel);
		
		proIdLabel = new JLabel("Product Id : ");
		proIdLabel.setBounds(250, 110, 120, 30);
		panel.add(proIdLabel);
		
		pIdLabel = new JLabel();
		pIdLabel.setBounds(400, 110, 120, 30);
		panel.add(pIdLabel);
		
		purchaseLabel = new JLabel("Purchase Id : ");
		purchaseLabel.setBounds(250, 140, 120, 30);
		panel.add(purchaseLabel);
		
		purchaseIdLabel = new JLabel();
		purchaseIdLabel.setBounds(400, 140, 150, 30);
		panel.add(purchaseIdLabel);
		
		quantityLabel = new JLabel("Quantity : ");
		quantityLabel.setBounds(250, 170, 120, 30);
		panel.add(quantityLabel);
		
		quantityAmountLabel = new JLabel();
		quantityAmountLabel.setBounds(400, 170, 120, 30);
		panel.add(quantityAmountLabel);
		
		totalPriceLabel = new JLabel("Total Price : ");
		totalPriceLabel.setBounds(250, 200, 120, 30);
		panel.add(totalPriceLabel);
		
		totalPriceAmountLabel = new JLabel();
		totalPriceAmountLabel.setBounds(400, 200, 120, 30);
		panel.add(totalPriceAmountLabel);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(300,300,120,30);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(440,300,120,30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		viewPurInfoBtn = new JButton("Purchase Info");
		viewPurInfoBtn.setBounds(360,360,120,30);
		viewPurInfoBtn.addActionListener(this);
		panel.add(viewPurInfoBtn);
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			SearchProduct sc= new SearchProduct(userId);
			sc.setVisible(true);
			this.setVisible(false);
		}
		
		else if(text.equals(logoutBtn.getText()))
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		
		else if(text.equals(viewPurInfoBtn.getText()))
		{
			loadFromDB();
		}
		else{}
	}
	
	public void loadFromDB()
	{
		String userId = userIdLabel.getText();
		String query = "SELECT `PurId`, `ProId`, `proName`, `Quantity`, `totalPrice` FROM `Purchase` WHERE customerId = '"+userId+"';";     
        Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;
		System.out.println(query);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			System.out.println("driver loaded");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/b12","root","");
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			rs = st.executeQuery(query);//getting result
			
			String purId = null;
			String proName = null;
			String proId = null;
			String quantity = null;
			
			while(rs.next())
			{
					purId = rs.getString("PurId");
					proName = rs.getString("proName");
					proId = rs.getString("ProId");
					quantity = rs.getString("Quantity");
					int TotalPrice = rs.getInt("totalPrice");
				
					purchaseIdLabel.setText(purId);
					productNameLabel.setText(proName);
					pIdLabel.setText(proId);
					quantityAmountLabel.setText(quantity);
					totalPriceAmountLabel.setText(Integer.toString(TotalPrice));
					
			}
		
             
		}
        catch(Exception ex)
		{
			System.out.println("Exception : " +ex.getMessage());
        }
        finally
		{
            try
			{
				if(rs!=null)
					rs.close();
				
                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex){}
        }
	}
}

