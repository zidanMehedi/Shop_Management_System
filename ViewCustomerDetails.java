import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;

public class ViewCustomerDetails extends JFrame implements ActionListener
{
	JLabel userLabel, cNameLabel, phoneLabel;
	JTextField userTF, phoneTF1, phoneTF2, cNameTF;
	JButton editBtn, updateBtn, backBtn, logoutBtn, changePassBtn, delBtn, loadBtn;
	JPanel panel;
	
	String userId;
	
	public ViewCustomerDetails(String userId)
	{
		super("Customer Details");
		
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		Color myColor = new Color(240, 230, 170);
		Color panelColor = new Color(0, 128, 255);
		
		panel = new JPanel();
		panel.setBackground(panelColor);
		panel.setLayout(null);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(550, 100, 120, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		userLabel = new JLabel("User ID : ");
		userLabel.setBounds(250, 100, 120, 30);
		panel.add(userLabel);
		
		userTF = new JTextField();
		userTF.setBounds(400, 100, 120, 30);
		userTF.setBackground(myColor);
		userTF.setText(userId);
		userTF.setEnabled(false);
		panel.add(userTF);
		
		cNameLabel = new JLabel("Customer Name : ");
		cNameLabel.setBounds(250, 150, 120, 30);
		panel.add(cNameLabel);
		
		cNameTF = new JTextField();
		cNameTF.setBounds(400, 150, 120, 30);
		cNameTF.setBackground(myColor);
		cNameTF.setEnabled(false);
		panel.add(cNameTF);
		
		phoneLabel = new JLabel("Phone No. : ");
		phoneLabel.setBounds(250, 200, 120, 30);
		panel.add(phoneLabel);
		
		phoneTF1 = new JTextField();
		phoneTF1.setBounds(400, 200, 35, 30);
		phoneTF1.setBackground(myColor);
		phoneTF1.setEnabled(false);
		panel.add(phoneTF1);
		
		phoneTF2 = new JTextField();
		phoneTF2.setBounds(435, 200, 85, 30);
		phoneTF2.setBackground(myColor);
		phoneTF2.setEnabled(false);
		panel.add(phoneTF2);
		
		updateBtn = new JButton("Update");
		updateBtn.setBounds(300, 400, 120, 30);
		updateBtn.setEnabled(false);
		updateBtn.addActionListener(this);
		panel.add(updateBtn);
		
		delBtn = new JButton("Delete");
		delBtn.setBounds(550, 200, 120, 30);
		delBtn.addActionListener(this);
		panel.add(delBtn);
		
		editBtn = new JButton("Edit");
		editBtn.setBounds(550, 150, 120, 30);
		editBtn.addActionListener(this);
		panel.add(editBtn);
		
		changePassBtn = new JButton("Change Password");
		changePassBtn.setBounds(550, 250, 150, 30);
		changePassBtn.addActionListener(this);
		panel.add(changePassBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(440, 400, 120, 30);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		loadBtn = new JButton("Load");
		loadBtn.setBounds(550, 300, 120, 30);
		loadBtn.addActionListener(this);
		panel.add(loadBtn);
		
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
		if(text.equals(logoutBtn.getText()))
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(loadBtn.getText()))
		{
			loadFromDB();
		}
		else if(text.equals(updateBtn.getText()))
		{
			updateInDB();
		}
		else if(text.equals(editBtn.getText()))
		{
			cNameTF.setEnabled(true);
			phoneTF2.setEnabled(true);
			updateBtn.setEnabled(true);
		}
		else if(text.equals(changePassBtn.getText()))
		{
			ChangePassword cp = new ChangePassword(userId);
			cp.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(delBtn.getText()))
		{
			deleteFromDB();
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else{}
	}
	
	public void loadFromDB()
	{
		String loadId = userTF.getText();
		String query = "SELECT `Name`,`PhoneNo` FROM `customer` WHERE `userId`='"+loadId+"';";     
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
				
			String cName = null;
			String phnNo = null;
			boolean flag = false;
				
			while(rs.next())
			{
				cName = rs.getString("Name");
				phnNo = rs.getString("PhoneNo");
				flag = true;
				
				cNameTF.setText(cName);
				phoneTF1.setText("+880");
				phoneTF2.setText(phnNo.substring(0,11));
			}
			if(!flag)
			{
				cNameTF.setText("");
				userTF.setText("");
				phoneTF1.setText("");
				phoneTF2.setText("");
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
		String newId = userTF.getText();
		String cName = cNameTF.getText();
		String phnNo = phoneTF1.getText()+phoneTF2.getText();
		String query = "UPDATE customer SET Name='"+cName+"', PhoneNo = '"+phnNo+"' WHERE userId='"+newId+"'";	
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
			userTF.setText(userId);
			cNameTF.setText(cNameTF.getText());
			phoneTF1.setText(phoneTF1.getText());
			phoneTF2.setText(phoneTF2.getText());
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
		}
	}
	
	public void deleteFromDB()
	{
		String newId = userTF.getText();
		String query1 = "DELETE from customer WHERE userId='"+newId+"';";
		String query2 = "DELETE from login WHERE userId='"+newId+"';";
		System.out.println(query1);
		System.out.println(query2);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/b12", "root", "");
			Statement stm = con.createStatement();
			stm.execute(query1);
			stm.execute(query2);
			stm.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
	}
}