import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;

public class CustomerHome extends JFrame implements ActionListener
{
	JLabel welcomeLabel;
	JButton logoutBtn, viewDetailsBtn, searchProductBtn;
	JPanel panel;
	String userId;
	
	public CustomerHome(String userId)
	{
		super("Customer Home Window");
		
		this.userId = userId;
		this.setSize(800,450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Color panelColor = new Color(0, 128, 255);
		
		panel = new JPanel();
		panel.setBackground(panelColor);
		panel.setLayout(null);
		
		welcomeLabel = new JLabel("Welcome, "+userId);
		welcomeLabel.setBounds(350, 50, 100, 30);
		panel.add(welcomeLabel);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(313, 250, 150, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		viewDetailsBtn = new JButton("My Information");
		viewDetailsBtn.setBounds(313, 150, 150, 30);
		viewDetailsBtn.addActionListener(this);
		panel.add(viewDetailsBtn);
		
		searchProductBtn = new JButton("Search Product");
		searchProductBtn.setBounds(313, 200, 150, 30);
		searchProductBtn.addActionListener(this);
		panel.add(searchProductBtn);
		
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(logoutBtn.getText()))
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(searchProductBtn.getText()))
		{
			SearchProduct sc= new SearchProduct(userId);
			sc.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(viewDetailsBtn.getText()))
		{
			ViewCustomerDetails vd= new ViewCustomerDetails(userId);
			vd.setVisible(true);
			this.setVisible(false);
		}
		else{}
	}
}