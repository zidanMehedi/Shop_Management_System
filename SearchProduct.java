import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.awt.*;


public class SearchProduct extends JFrame implements ActionListener
{
	JLabel pNameLabel,  pIdLabel, pQuantityLabel, sQuantityLabel, priceLabel;
	JTextField pNameTF, pIdTF, pQuantityTF, priceTF;
	JComboBox quantity;
	JButton refreshBtn, buyBtn, loadBtn, backBtn, logoutBtn;
	JPanel panel;
	
	String userId;
	
	public SearchProduct(String userId)
	{
		super("Product");
		
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		Color myColor = new Color(240, 230, 170);
		Color panelColor = new Color(0, 128, 255);
		
		panel = new JPanel();
		panel.setBackground(panelColor);
		panel.setLayout(null);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(550, 200, 100, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(550, 150, 100, 30);
		refreshBtn.addActionListener(this);
		panel.add(refreshBtn);
		
		pNameLabel = new JLabel("Product Name : ");
		pNameLabel.setBounds(250, 100, 120, 30);
		panel.add(pNameLabel);
		
		pNameTF = new JTextField();
		pNameTF.setBounds(400, 100, 120, 30);
		pNameTF.setBackground(myColor);
		panel.add(pNameTF);
		
		loadBtn = new JButton("Load");
		loadBtn.setBounds(550, 100, 100, 30);
		loadBtn.addActionListener(this);
		panel.add(loadBtn);
		
		pIdLabel = new JLabel("Product ID : ");
		pIdLabel.setBounds(250, 150, 120, 30);
		panel.add(pIdLabel);
		
		pIdTF = new JTextField();
		pIdTF.setBounds(400, 150, 120, 30);
		pIdTF.setBackground(myColor);
		panel.add(pIdTF);
		
		pQuantityLabel = new JLabel("Available Quantity : ");
		pQuantityLabel.setBounds(250, 200, 120, 30);
		panel.add(pQuantityLabel);
		
		pQuantityTF = new JTextField();
		pQuantityTF.setBounds(400, 200, 120, 30);
		pQuantityTF.setBackground(myColor);
		pQuantityTF.setEnabled(false);
		panel.add(pQuantityTF);
		
		sQuantityLabel = new JLabel("Select Quantity : ");
		sQuantityLabel.setBounds(250, 300, 120, 30);
		panel.add(sQuantityLabel);
		
		String []items = {"1","2","3","4","5"};
		quantity = new JComboBox(items);
		quantity.setBackground(myColor);
		quantity.setBounds(400, 300, 40, 30);
		panel.add(quantity);
		
		priceLabel = new JLabel("Price : ");
		priceLabel.setBounds(250, 250, 120, 30);
		panel.add(priceLabel);
		
		priceTF = new JTextField();
		priceTF.setBounds(400, 250, 120, 30);
		priceTF.setBackground(myColor);
		priceTF.setEnabled(false);
		panel.add(priceTF);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(440, 400, 120, 30);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		buyBtn = new JButton("Buy");
		buyBtn.setBounds(300, 400, 120, 30);
		buyBtn.addActionListener(this);
		panel.add(buyBtn);
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			CustomerHome ch = new CustomerHome(userId);
			ch.setVisible(true);
			this.setVisible(false);
		}
		if(text.equals(refreshBtn.getText()))
		{
			pIdTF.setText("");
			pNameTF.setText("");
			pQuantityTF.setText("");
		}
		else if(text.equals(logoutBtn.getText()))
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(loadBtn.getText()))
		{
			loadFromDB();			
		}
		else if(text.equals(buyBtn.getText()))
		{
			int selectQ = Integer.parseInt(quantity.getSelectedItem().toString());
			int availableQ = Integer.parseInt(pQuantityTF.getText());
			if(availableQ < selectQ)
			{
				JOptionPane.showMessageDialog(this,"Quantity exceeds Limits");
			}
			else
			{
				insertIntoPurchase();
				updateInDB();
			}
		}
		else{}
	}
	
	public void loadFromDB()
	{
		String newProId = pIdTF.getText();
		String query = "SELECT `proName`, `Quantity`, `Price` FROM `product` WHERE `ProId`='"+newProId+"';";     
        Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
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
			System.out.println("results received");
			
			boolean flag = false;
			String proName = null;
			String proQuantity = null;
			String price = null;
			while(rs.next())
			{
                proName = rs.getString("proName");
				proQuantity = rs.getString("Quantity");
				price = rs.getString("Price");
				flag=true;
				
				pNameTF.setText(proName);
				pQuantityTF.setText(proQuantity);
				priceTF.setText(price);
				pIdTF.setEnabled(false);
			}
			if(!flag)
			{
				pNameTF.setText("");
				pQuantityTF.setText("");
				priceTF.setText("");
				JOptionPane.showMessageDialog(this,"Invalid product ID"); 
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
	
	public void updateInDB()
	{
		int selectQ = Integer.parseInt(quantity.getSelectedItem().toString());
		int availableQ = Integer.parseInt(pQuantityTF.getText());
		String newProId = pIdTF.getText();
		int newQuantity = (availableQ-selectQ);
		String query = "UPDATE product SET `Quantity`='"+newQuantity+"' WHERE `ProId`='"+newProId+"'";	
        Connection con=null;//for connection
        Statement st = null;//for query execution
		System.out.println(query);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/b12","root","");
			st = con.createStatement();//create statement
			st.executeUpdate(query);
			st.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			pIdTF.setEnabled(true);
			pIdTF.setText("");
			pNameTF.setText("");
			pQuantityTF.setText("");
			priceTF.setText("");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
		}
	}
	
	public void insertIntoPurchase()
	{
		int selectQ = Integer.parseInt(quantity.getSelectedItem().toString());
		int availableQ = Integer.parseInt(pQuantityTF.getText());
		Random r = new Random();
		int randomId = r.nextInt(8999)+10000;
		int priceAmount = Integer.parseInt(priceTF.getText());
		
		String PurId = "Pro"+Integer.toString(randomId)+"xYz"; 
		String customerId = userId;
		String ProId = pIdTF.getText();
		String ProName = pNameTF.getText();
		String Quantity = Integer.toString(selectQ);
		int totalPrice =(selectQ*priceAmount);
		
		String query = "INSERT INTO purchase VALUES ('"+PurId+"','"+ProId+"','"+ProName+"','"+customerId+"','"+Quantity+"',"+totalPrice+");";
		System.out.println(query);
        
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/b12", "root", "");
			Statement stm = con.createStatement();
			stm.execute(query);
			
			PurchaseInfo pi = new PurchaseInfo(userId);
			pi.setVisible(true);
			this.setVisible(false);
			
			stm.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
		}
        catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
	}
}