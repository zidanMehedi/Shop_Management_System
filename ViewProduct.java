import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;

public class ViewProduct extends JFrame implements ActionListener
{
	JLabel pNameLabel,  pIdLabel, pQuantityLabel, priceLabel;
	JTextField pNameTF, pIdTF, pQuantityTF, priceTF;
	JButton refreshBtn, loadBtn, updateBtn, delBtn, backBtn, logoutBtn;
	JPanel panel;
	
	String userId;
	
	public ViewProduct(String userId)
	{
		super("View Product");
		
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
		pNameTF.setEnabled(false);
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
		
		pQuantityLabel = new JLabel("Quantity : ");
		pQuantityLabel.setBounds(250, 200, 120, 30);
		
		panel.add(pQuantityLabel);
		
		pQuantityTF = new JTextField();
		pQuantityTF.setBounds(400, 200, 120, 30);
		pQuantityTF.setBackground(myColor);
		pQuantityTF.setEnabled(false);
		panel.add(pQuantityTF);
		
		priceLabel = new JLabel("Price : ");
		priceLabel.setBounds(250, 250, 120, 30);
		panel.add(priceLabel);
		
		priceTF = new JTextField();
		priceTF.setBounds(400, 250, 120, 30);
		priceTF.setBackground(myColor);
		priceTF.setEnabled(false);
		panel.add(priceTF);
		
		updateBtn = new JButton("Update");
		updateBtn.setBounds(200, 350, 120, 30);
		updateBtn.setEnabled(false);
		updateBtn.addActionListener(this);
		panel.add(updateBtn);
		
		delBtn = new JButton("Delete");
		delBtn.setBounds(350, 350, 120, 30);
		delBtn.setEnabled(false);
		delBtn.addActionListener(this);
		panel.add(delBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(500, 350, 120, 30);
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
		if(text.equals(refreshBtn.getText()))
		{
			updateBtn.setEnabled(false);
			delBtn.setEnabled(false);
			pIdTF.setEnabled(true);
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
			pNameTF.setEnabled(true);
			pQuantityTF.setEnabled(true);
			priceTF.setEnabled(true);
			pIdTF.setEnabled(false);
		}
		else if(text.equals(updateBtn.getText()))
		{
			updateInDB();
		}
		else if(text.equals(delBtn.getText()))
		{
			deleteFromDB();
		}
		else{}
	}
	
	public void loadFromDB()
	{
		String newProId = pIdTF.getText();
		String query = "SELECT `proName`, `Quantity`, `Price` FROM `Product` WHERE `ProId`='"+newProId+"';";     
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
                proName = rs.getString("pName");
				proQuantity = rs.getString("Quantity");
				price = rs.getString("Price");
				flag=true;
				
				pNameTF.setText(proName);
				pQuantityTF.setText(proQuantity);
				priceTF.setText(price);
				pIdTF.setEnabled(false);
				updateBtn.setEnabled(true);
				delBtn.setEnabled(true);
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
		String newProId = pIdTF.getText();
		String proName = pNameTF.getText();
		String proQuantity = pQuantityTF.getText();
		String price = priceTF.getText();
		String query = "UPDATE product SET proName='"+proName+"', Quantity = '"+proQuantity+"', Price = '"+price+"' WHERE ProId='"+newProId+"'";	
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
			
			updateBtn.setEnabled(false);
			delBtn.setEnabled(false);
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
	
	public void deleteFromDB()
	{
		String newId = pIdTF.getText();
		String query = "DELETE from product WHERE ProId='"+newId+"';";
		System.out.println(query);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/b12", "root", "");
			Statement stm = con.createStatement();
			stm.execute(query);
			stm.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			updateBtn.setEnabled(false);
			delBtn.setEnabled(false);
			pIdTF.setEnabled(true);
			pIdTF.setText("");
			pNameTF.setText("");
			pQuantityTF.setText("");
			priceTF.setText("");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
	}
}