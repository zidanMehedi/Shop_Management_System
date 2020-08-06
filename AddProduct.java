import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class AddProduct extends JFrame implements ActionListener
{
	JLabel pNameLabel, pIdLabel, pQuantityLabel, priceLabel;
	JTextField pNameTF, pIdTF, pQuantityTF, priceTF;
	JButton addBtn, backBtn, logoutBtn;
	JPanel panel;
	
	String userId;
	
	public AddProduct(String userId)
	{
		super("Add New Product");
		
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		Color myColor = new Color(240, 230, 170);
		Color panelColor = new Color(0, 128, 255);
		
		panel = new JPanel();
		panel.setBackground(panelColor);
		panel.setLayout(null);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(570, 150, 100, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		pNameLabel = new JLabel("Product Name : ");
		pNameLabel.setBounds(250, 150, 120, 30);
		panel.add(pNameLabel);
		
		pNameTF = new JTextField();
		pNameTF.setBounds(400, 150, 120, 30);
		pNameTF.setBackground(myColor);
		panel.add(pNameTF);
		
		pIdLabel = new JLabel("product Id : ");
		pIdLabel.setBounds(250, 200, 120, 30);
		panel.add(pIdLabel);
		
		pIdTF = new JTextField();
		pIdTF.setBounds(400, 200, 120, 30);
		pIdTF.setBackground(myColor);
		panel.add(pIdTF);
		
		pQuantityLabel = new JLabel("Quantity : ");
		pQuantityLabel.setBounds(250, 250, 120, 30);
		panel.add(pQuantityLabel);
		
		pQuantityTF = new JTextField();
		pQuantityTF.setBounds(400, 250, 120, 30);
		pQuantityTF.setBackground(myColor);
		panel.add(pQuantityTF);
		
		priceLabel = new JLabel("Price : ");
		priceLabel.setBounds(250, 300, 120, 30);
		panel.add(priceLabel);
		
		priceTF = new JTextField();
		priceTF.setBounds(400, 300, 120, 30);
		priceTF.setBackground(myColor);
		panel.add(priceTF);
		
		addBtn = new JButton("Add");
		addBtn.setBounds(250, 400, 120, 30);
		addBtn.addActionListener(this);
		panel.add(addBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(400, 400, 120, 30);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
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
		
		else if(text.equals(addBtn.getText()))
		{
			insertIntoDB();
		}
		
		else{}
	}
	
	public void insertIntoDB()
	{
		String ProId = pIdTF.getText();
		String proName = pNameTF.getText();
		String Quantity = pQuantityTF.getText();
		int Price = Integer.parseInt(priceTF.getText());
		
		//String query1 = "SELECT `ProId` FROM `product`;";
		String query = "INSERT INTO product VALUES ('"+ProId+"','"+proName+"','"+Quantity+"',"+Price+");";
		//System.out.println(query1);
		System.out.println(query);
        
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/b12", "root", "");
			Statement stm = con.createStatement();
			
			stm.execute(query);
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			stm.close();
			con.close();
			
		}
        catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
    }
	
}