import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class AddEmployee extends JFrame implements ActionListener
{
	JLabel userLabel, passLabel, eNameLabel, phoneLabel, roleLabel, salaryLabel;
	JTextField userTF, passTF, phoneTF1, phoneTF2, eNameTF, salaryTF;
	JComboBox roleCombo;
	JButton autoPassBtn, addBtn, backBtn, logoutBtn;
	JPanel panel;
	
	String userId;
	
	public AddEmployee(String userId)
	{
		super("Add New Employee");
		
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		Color myColor = new Color(240, 230, 170);
		Color panelColor = new Color(0, 128, 255);
		
		panel = new JPanel();
		panel.setBackground(panelColor);
		panel.setLayout(null);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(575, 50, 100, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		userLabel = new JLabel("User ID : ");
		userLabel.setBounds(250, 50, 120, 30);
		panel.add(userLabel);
		
		userTF = new JTextField();
		userTF.setBounds(400, 50, 120, 30);
		userTF.setBackground(myColor);
		panel.add(userTF);
		
		passLabel = new JLabel("Password : ");
		passLabel.setBounds(250, 100, 120, 30);
		panel.add(passLabel);
		
		passTF = new JTextField();
		passTF.setBounds(400, 100, 120, 30);
		passTF.setBackground(myColor);
		passTF.setEnabled(false);
		panel.add(passTF);
		
		autoPassBtn = new JButton("Generate");
		autoPassBtn.setBounds(550, 100, 150, 30);
		autoPassBtn.addActionListener(this);
		panel.add(autoPassBtn);
		
		eNameLabel = new JLabel("Employee Name : ");
		eNameLabel.setBounds(250, 150, 120, 30);
		panel.add(eNameLabel);
		
		eNameTF = new JTextField();
		eNameTF.setBounds(400, 150, 120, 30);
		eNameTF.setBackground(myColor);
		panel.add(eNameTF);
		
		phoneLabel = new JLabel("Phone No. : ");
		phoneLabel.setBounds(250, 200, 120, 30);
		panel.add(phoneLabel);
		
		phoneTF1 = new JTextField("+880");
		phoneTF1.setBounds(400, 200, 35, 30);
		phoneTF1.setEnabled(false);
		phoneTF1.setBackground(myColor);
		phoneTF1.setForeground(Color.BLACK);
		panel.add(phoneTF1);
		
		phoneTF2 = new JTextField();
		phoneTF2.setBounds(435, 200, 85, 30);
		phoneTF2.setBackground(myColor);
		panel.add(phoneTF2);
		
		roleLabel = new JLabel("Role : ");
		roleLabel.setBounds(250, 250, 120, 30);
		panel.add(roleLabel);
		
		String []items = {"General","Manager"};
		roleCombo = new JComboBox(items);
		roleCombo.setBounds(400, 250, 120, 30);
		roleCombo.setBackground(myColor);
		panel.add(roleCombo);
		
		salaryLabel = new JLabel("Salary : ");
		salaryLabel.setBounds(250, 300, 120, 30);
		panel.add(salaryLabel);
		
		salaryTF = new JTextField();
		salaryTF.setBounds(400, 300, 120, 30);
		salaryTF.setBackground(myColor);
		panel.add(salaryTF);
		
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
			if(userId.equals("admin"))
			{
				Admin a = new Admin(userId);
				a.setVisible(true);
				this.setVisible(false);
			}
			else
			{
				ManageEmployee me = new ManageEmployee(userId);
				me.setVisible(true);
				this.setVisible(false);
			}
		}
		else if(text.equals(logoutBtn.getText()))
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(autoPassBtn.getText()))
		{
			Random r = new Random();
			passTF.setText(r.nextInt(89999999)+10000000+"");
			autoPassBtn.setEnabled(false);
		}
		else if(text.equals(addBtn.getText()))
		{
			insertIntoDB();
		}
		else{}
	}
	public void insertIntoDB()
	{
		String userId = userTF.getText();
		String password = passTF.getText();
		String Name = eNameTF.getText();
		String PhoneNo = phoneTF1.getText()+phoneTF2.getText();
		String role = roleCombo.getSelectedItem().toString();
		double salary = Double.parseDouble(salaryTF.getText());
		int status = 0;
		if(role.equals("General"))
		{
			 status = status + 2;
		}
		else if(role.equals("Manager"))
		{
			 status = status + 3;
		}
		
		
		
		String query1 = "INSERT INTO employee VALUES ('"+userId+"','"+Name+"','"+ PhoneNo+"','"+role+"',"+salary+");";
		String query2 = "INSERT INTO login VALUES ('"+userId+"','"+password+"',"+status+");";
		System.out.println(query1);
		System.out.println(query2);
        
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/b12", "root", "");
			Statement stm = con.createStatement();
			stm.execute(query1);
			stm.execute(query2);
			
			userTF.setText("");
			passTF.setText("");
			phoneTF1.setText("+880");
			phoneTF2.setText("");
			eNameTF.setText("");
			salaryTF.setText("");
			
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