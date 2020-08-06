import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ManageEmployee extends JFrame implements ActionListener
{
	JLabel welcomeLabel;
	JButton addEmployeeBtn, viewEmployeeBtn, backBtn, logoutBtn;
	JPanel panel;
	
	String userId;
	
	public ManageEmployee(String userId)
	{
		super("Manage Employee Window");
		
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
		logoutBtn.setBounds(400, 300, 100, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		addEmployeeBtn = new JButton("Add Employee");
		addEmployeeBtn.setBounds(313, 200, 150, 30);
		addEmployeeBtn.addActionListener(this);
		panel.add(addEmployeeBtn);
		
		viewEmployeeBtn = new JButton("View Employee");
		viewEmployeeBtn.setBounds(313, 150, 150, 30);
		viewEmployeeBtn.addActionListener(this);
		panel.add(viewEmployeeBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(280, 300, 100, 30);
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
		else if(text.equals(addEmployeeBtn.getText()))
		{
			RcrtEmplyee re = new RcrtEmplyee(userId);
			re.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(viewEmployeeBtn.getText()))
		{
			ViewEmployee ve = new ViewEmployee(userId);
			ve.setVisible(true);
			this.setVisible(false);
		}
		else{}
	}
}