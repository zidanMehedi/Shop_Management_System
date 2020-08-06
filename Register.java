import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Register extends JFrame implements ActionListener
{
	JLabel nameLabel, userLabel, passLabel, cpassLabel, phnLabel, statusLabel;
	JTextField nameTF, userTF, phnTF1, phnTF2;
	JPasswordField passPF, cpassPF;
	JButton confirmBtn, backBtn;
	JPanel panel;

	public Register()
	{
		super("Register Window");
		this.setSize(800,450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Color myColor = new Color(240, 230, 170);
		Color panelColor = new Color(0, 128, 255);
		
		panel = new JPanel();
		panel.setBackground(panelColor);
		panel.setLayout(null);
		
		nameLabel = new JLabel ("Name : ");
		nameLabel.setBounds(265, 50, 60, 30);
		panel.add(nameLabel);
		
		userLabel = new JLabel ("User Id : ");
		userLabel.setBounds(265, 100, 60, 30);
		panel.add(userLabel);
		
		passLabel = new JLabel ("Password : ");
		passLabel.setBounds(265, 150, 70, 30);
		panel.add(passLabel);
		
		cpassLabel = new JLabel ("Confirm Password : ");
		cpassLabel.setBounds(265, 200, 120, 30);
		panel.add(cpassLabel);
		
		phnLabel = new JLabel ("Phone No : ");
		phnLabel.setBounds(265, 250, 100, 30);
		panel.add(phnLabel);
		
		nameTF = new JTextField();
		nameTF.setBounds(420, 50, 120, 30);
		nameTF.setBackground(myColor);
		panel.add(nameTF);
		
		userTF = new JTextField();
		userTF.setBounds(420, 100, 120, 30);
		userTF.setBackground(myColor);
		panel.add(userTF);
		
		passPF = new JPasswordField();
		passPF.setBounds(420, 150, 120, 30);
		passPF.setBackground(myColor);
		panel.add(passPF);
		
		cpassPF = new JPasswordField();
		cpassPF.setBounds(420, 200, 120, 30);
		cpassPF.setBackground(myColor);
		panel.add(cpassPF);
		
		phnTF1 = new JTextField();
		phnTF1.setBounds(420, 250, 35, 30);
		phnTF1.setText("+880");
		phnTF1.setEnabled(false);
		phnTF1.setBackground(myColor);
		panel.add(phnTF1);
		
		phnTF2 = new JTextField();
		phnTF2.setBounds(455, 250, 120, 30);
		phnTF2.setBackground(myColor);
		panel.add(phnTF2);
		
		confirmBtn = new JButton("Confirm");
		confirmBtn.setBounds(300, 350,80,30);
		confirmBtn.addActionListener(this);
		panel.add(confirmBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(390, 350,80,30);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(confirmBtn.getText()))
		{
			if(userTF.getText().equals("") || passPF.getText().equals("") || cpassPF.getText().equals("") || phnTF2.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this,"Please provide all valid Informations"); 
			}
			else
			{
				if(passPF.getText().equals(cpassPF.getText()))
				{
					insertIntoDB(); 
				
					Login l = new Login();
					l.setVisible(true);
					this.setVisible(false);
				}
				else
				{
					JOptionPane.showMessageDialog(this,"Password did not match!!"); 
				}
			}
		}
		else if (text.equals(backBtn.getText()))
		{
			Login l = new Login();
			l.setVisible(true);
			this.setVisible(false);
		}
	}
	
	public void insertIntoDB()
	{
		String userId = userTF.getText();
		String password = passPF.getText();
		String Name = nameTF.getText();
		String PhoneNo =  phnTF1.getText()+phnTF2.getText();
		
		int status = 1;
		
		String query = "SELECT `userId` FROM `login`;";
		String query1 = "INSERT INTO login VALUES ('"+userId+"','"+password+"','"+status+"');";
		String query2 = "INSERT INTO customer VALUES ('"+userId+"','"+Name+"','"+PhoneNo+"');";
		
		System.out.println(query1);
		System.out.println(query2);
        
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/b12","root","");
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(query);
			System.out.println("results received");
			
			boolean flag = false;
			
			while(rs.next())
			{
				if(userTF.getText().equals(rs.getString("userId")))
				{
					JOptionPane.showMessageDialog(this, "User Id unavailable !!");
					flag = false;
					break;
				}
				else
				{
					flag = true;
				}
			}
			
			if(flag)
			{
				stm.execute(query1);
				stm.execute(query2);
				JOptionPane.showMessageDialog(this, "Success !!!");
				JOptionPane.showMessageDialog(this,"Your account has been Created");
			}
			
			rs.close();
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