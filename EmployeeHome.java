import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;

public class EmployeeHome extends JFrame implements ActionListener
{
	JLabel welcomeLabel;
	JButton manageEmployeeBtn, viewDetailsBtn, logoutBtn, viewProductBtn, viewCustomerBtn, addProductBtn, purchaseInfoBtn;
	JPanel panel;
	String userId;
	
	public EmployeeHome(String userId)
	{
		super("Employee Home Window");
		
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
		
		manageEmployeeBtn = new JButton("Manage Employee");
		manageEmployeeBtn.setBounds(400, 150, 150, 30);
		manageEmployeeBtn.addActionListener(this);
		panel.add(manageEmployeeBtn);
		
		viewDetailsBtn = new JButton("My Information");
		viewDetailsBtn.setBounds(400, 200, 150, 30);
		viewDetailsBtn.addActionListener(this);
		panel.add(viewDetailsBtn);
		
		purchaseInfoBtn = new JButton("Purchase Info");
		purchaseInfoBtn.setBounds(400, 250, 150, 30);
		purchaseInfoBtn.addActionListener(this);
		panel.add(purchaseInfoBtn);
		
		viewCustomerBtn = new JButton("View Customer");
		viewCustomerBtn.setBounds(230, 150, 150, 30);
		viewCustomerBtn.addActionListener(this);
		panel.add(viewCustomerBtn);
		
		viewProductBtn = new JButton("View Product");
		viewProductBtn.setBounds(230, 200, 150, 30);
		viewProductBtn.addActionListener(this);
		panel.add(viewProductBtn);
		
		addProductBtn = new JButton("Add Product");
		addProductBtn.setBounds(230, 250, 150, 30);
		addProductBtn.addActionListener(this);
		panel.add(addProductBtn);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(313, 300, 150, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
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
		
		else if(text.equals(addProductBtn.getText()))
		{
			AddProduct ap = new AddProduct(userId);
			ap.setVisible(true);
			this.setVisible(false);
		}
		
		else if(text.equals(viewCustomerBtn.getText()))
		{
			ViewCustomers vc = new ViewCustomers(userId);
			vc.setVisible(true);
			this.setVisible(false);
		}
		
		else if(text.equals(purchaseInfoBtn.getText()))
		{
			ViewPurchaseInfo vp = new ViewPurchaseInfo(userId);
			vp.setVisible(true);
			this.setVisible(false);
		}
		
		else if(text.equals(viewProductBtn.getText()))
		{
			ViewProduct vp = new ViewProduct(userId);
			vp.setVisible(true);
			this.setVisible(false);
		}
		
		else if(text.equals(viewDetailsBtn.getText()))
		{
			ViewEmployeeDetails ed= new ViewEmployeeDetails(userId);
			ed.setVisible(true);
			this.setVisible(false);
		}

		else if(text.equals(manageEmployeeBtn.getText()))
		{
			checkRole();
		}
		else{}
	}
	
	public void checkRole()
	{
		String loadId = userId;
		String query = "SELECT `status` FROM `login` WHERE userId = '"+loadId+"';";     
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
					
			String Status = null;
					
			while(rs.next())
			{
				Status = rs.getString("status");
				
				if(Status.equals("3"))
				{
					ManageEmployee me = new ManageEmployee(userId);
					me.setVisible(true);
					this.setVisible(false);
				}
				else
				{
					JOptionPane.showMessageDialog(this,"Access Denied");
				}
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