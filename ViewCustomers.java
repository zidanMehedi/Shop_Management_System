import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;

public class ViewCustomers extends JFrame implements ActionListener
{
	JLabel userLabel, cNameLabel, phoneLabel;
	JTextField userTF, phoneTF1, phoneTF2, cNameTF;
	JButton backBtn, logoutBtn, loadBtn, refreshBtn;
	JPanel panel;
	
	String userId;
	
	public ViewCustomers(String userId)
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
		
		backBtn = new JButton("Back");
		backBtn.setBounds(400, 300, 120, 30);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		loadBtn = new JButton("Load");
		loadBtn.setBounds(550, 150, 120, 30);
		loadBtn.addActionListener(this);
		panel.add(loadBtn);
		
		refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(250, 300, 120, 30);
		refreshBtn.addActionListener(this);
		panel.add(refreshBtn);
		
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
		else if(text.equals(refreshBtn.getText()))
		{
			userTF.setText("");
			cNameTF.setText("");
			phoneTF1.setText("");
			phoneTF2.setText("");
		}
		else if(text.equals(loadBtn.getText()))
		{
			loadFromDB();
		}
		else{}
	}
	
	public void loadFromDB()
	{
		String loadId = userTF.getText();
		String query = "SELECT `Name`, `PhoneNo` FROM `Customer` WHERE `userId`='"+loadId+"';";     
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
			while(rs.next())
			{
                cName = rs.getString("Name");
				phnNo = rs.getString("PhoneNo");
				
				cNameTF.setText(cName);
				phoneTF1.setText("+880");
				phoneTF2.setText(phnNo.substring(0,11));
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