import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;

public class ViewPurchaseInfo extends JFrame implements ActionListener
{
	JLabel userLabel, productLabel, proIdLabel, purchaseLabel, quantityLabel, totalPriceLabel;
	JTextField userTF, productTF, proIdTF, purchaseTF, quantityTF, totalPriceTF;
	JButton logoutBtn, backBtn, loadBtn;
	JPanel panel;
	String userId;
	public ViewPurchaseInfo(String userId)
	{
		
		super("View Purchase Information");
		this.setSize(800,450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		Color myColor = new Color(240, 230, 170);
		Color panelColor = new Color(0, 128, 255);
		
		panel = new JPanel();
		panel.setBackground(panelColor);
		panel.setLayout(null);
		
		userLabel = new JLabel("User Id : ");
		userLabel.setBounds(250, 70, 120, 30);
		panel.add(userLabel);
		
		userTF = new JTextField();
		userTF.setBounds(400, 70, 120, 30);
		userTF.setBackground(myColor);
		panel.add(userTF);
		
		productLabel = new JLabel("Product Name : ");
		productLabel.setBounds(250, 100, 120, 30);
		panel.add(productLabel);
		
		productTF = new JTextField();
		productTF.setBounds(400, 100, 120, 30);
		productTF.setBackground(myColor);
		panel.add(productTF);
		
		proIdLabel = new JLabel("Product Id : ");
		proIdLabel.setBounds(250, 130, 120, 30);
		panel.add(proIdLabel);
		
		proIdTF = new JTextField();
		proIdTF.setBounds(400, 130, 120, 30);
		proIdTF.setBackground(myColor);
		panel.add(proIdTF);
		
		purchaseLabel = new JLabel("Purchase Id : ");
		purchaseLabel.setBounds(250, 160, 120, 30);
		panel.add(purchaseLabel);
		
		purchaseTF = new JTextField();
		purchaseTF.setBounds(400, 160, 150, 30);
		purchaseTF.setBackground(myColor);
		panel.add(purchaseTF);
		
		quantityLabel = new JLabel("Quantity : ");
		quantityLabel.setBounds(250, 190, 120, 30);
		panel.add(quantityLabel);
		
		quantityTF = new JTextField();
		quantityTF.setBounds(400, 190, 120, 30);
		quantityTF.setBackground(myColor);
		panel.add(quantityTF);
		
		totalPriceLabel = new JLabel("Total Price : ");
		totalPriceLabel.setBounds(250, 220, 120, 30);
		panel.add(totalPriceLabel);
		
		totalPriceTF = new JTextField();
		totalPriceTF.setBounds(400, 220, 120, 30);
		totalPriceTF.setBackground(myColor);
		panel.add(totalPriceTF);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(300,310,120,30);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(440,310,120,30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		loadBtn = new JButton("Load");
		loadBtn.setBounds(360,370,120,30);
		loadBtn.addActionListener(this);
		panel.add(loadBtn);
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			EmployeeHome eh = new EmployeeHome(userId);
			eh.setVisible(true);
			this.setVisible(false);
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
		else{}
	}
	
	public void loadFromDB()
	{
		String userId = userTF.getText();
		String query = "SELECT `PurId`, `ProId`, `ProName`, `Quantity`, `totalPrice` FROM `Purchase` WHERE customerId = '"+userId+"';";     
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
					proName = rs.getString("ProName");
					proId = rs.getString("ProId");
					quantity = rs.getString("Quantity");
					double TotalPrice = rs.getDouble("totalPrice");
				
					purchaseTF.setText(purId);
					productTF.setText(proName);
					proIdTF.setText(proId);
					quantityTF.setText(quantity);
					totalPriceTF.setText(Double.toString(TotalPrice));
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

