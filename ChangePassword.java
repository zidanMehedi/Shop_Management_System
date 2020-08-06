import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;

public class ChangePassword extends JFrame implements ActionListener
{
	JLabel oldPassLabel, newPassLabel, cpassLabel;
	JPasswordField oldPassPF, newPassPF, cpassPF;
	JButton changeBtn, logoutBtn;
	JPanel panel;
	String userId;
	
	
	public ChangePassword(String userId)
	{
		super("Change Password Window");
		
		this.userId = userId;
		this.setSize(800,450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Color myColor = new Color(240, 230, 170);
		Color panelColor = new Color(0, 128, 255);
		
		panel = new JPanel();
		panel.setBackground(panelColor);
		panel.setLayout(null);
		
		oldPassLabel = new JLabel ("Old Password : ");
		oldPassLabel.setBounds(265, 100, 100, 30);
		panel.add(oldPassLabel);
		
		newPassLabel = new JLabel ("New Password : ");
		newPassLabel.setBounds(265, 150, 100, 30);
		panel.add(newPassLabel);
		
		cpassLabel = new JLabel ("Confirm Password : ");
		cpassLabel.setBounds(265, 200, 120, 30);
		panel.add(cpassLabel);
		
		oldPassPF = new JPasswordField();
		oldPassPF.setBounds(420, 100, 120, 30);
		oldPassPF.setBackground(myColor);
		panel.add(oldPassPF);
		
		newPassPF = new JPasswordField();
		newPassPF.setBounds(420, 150, 120, 30);
		newPassPF.setBackground(myColor);
		panel.add(newPassPF);
		
		cpassPF = new JPasswordField();
		cpassPF.setBounds(420, 200, 120, 30);
		cpassPF.setBackground(myColor);
		panel.add(cpassPF);
		
		changeBtn = new JButton ("Change");
		changeBtn.setBounds(270, 290,80,30);
		changeBtn.addActionListener(this);
		panel.add(changeBtn);
		
		logoutBtn = new JButton ("Logout");
		logoutBtn.setBounds(360, 290,80,30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		this.add(panel);
	}
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if (text.equals(logoutBtn.getText()))
		{
			Login l = new Login();
			l.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(changeBtn.getText()))
		{
			if(oldPassPF.getText().equals("") || newPassPF.getText().equals("") || cpassPF.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this, "Please provide all informations");
			}
			else if(newPassPF.getText().equals(cpassPF.getText()))
			{
				changeInDB();
				
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Password did not match");
			}
		}
	}

	
	public void changeInDB()
	{
		String password = newPassPF.getText();
		String query1 = "SELECT `password` FROM `login` WHERE userId = '"+userId+"'";
		String query2 = "UPDATE `login` SET password='"+password+"' WHERE `userId` = '"+userId+"'";	
        Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;
        try
		{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/b12","root","");
			st = con.createStatement();//create statement
			rs = st.executeQuery(query1);
			while(rs.next())
			{
				String oldPass = rs.getString("password");
				if(oldPassPF.getText().equals(oldPass))
				{
					st.executeUpdate(query2);
					JOptionPane.showMessageDialog(this, "Success !!!");
					JOptionPane.showMessageDialog(this, "You must login Again!");
					
					Login lg = new Login();
					lg.setVisible(true);
					this.setVisible(false);
				}
				else
				{
					JOptionPane.showMessageDialog(this, "Old password did not match");
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
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