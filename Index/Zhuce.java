package Index;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Util.DbUtil;


public class Zhuce extends JFrame implements ActionListener {

	JTextField field1;
	JPasswordField field2,field3;
	JButton buttonOfZhuce,buttonOfFanhui;
	Box box1,box2,box3,box4,baseBox;
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public Zhuce()
	{
		setLayout(new FlowLayout());
		init();
		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 500, 450);
		setTitle("注册界面");
	}
	
	void init()
	{
		
		box1= Box.createHorizontalBox();
		box1.add(new JLabel("新用户名:"));
		box1.add(Box.createHorizontalStrut(8));
		field1 = new JTextField(15);
		box1.add(field1);
		
		box2= Box.createHorizontalBox();
		box2.add(new JLabel("密         码:"));
		box2.add(Box.createHorizontalStrut(8));
		field2 = new JPasswordField(15);
		box2.add(field2);
		
		box3= Box.createHorizontalBox();
		box3.add(new JLabel("再次输入:"));
		box3.add(Box.createHorizontalStrut(8));
		field3 = new JPasswordField(15);
		box3.add(field3);
		
		box4= Box.createHorizontalBox();
		buttonOfZhuce = new JButton("注册");
		buttonOfZhuce.addActionListener(this);
		buttonOfFanhui = new JButton("返回");
		buttonOfFanhui.addActionListener(this);
		box4.add(buttonOfZhuce);
		box4.add(box4.createHorizontalStrut(5));
		box4.add(buttonOfFanhui);
		
		baseBox = Box.createVerticalBox();
		baseBox.add(Box.createVerticalStrut(50));
		baseBox.add(box1);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(box2);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(box3);
		baseBox.add(Box.createVerticalStrut(200));
		baseBox.add(box4);
		
		add(baseBox);
		
		
		
	}
	
	public void connDB() { // 连接数据库
		try {
			Class.forName("com.mysql.jdbc.Driver");//注册驱动
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {//创建连接
			con = DriverManager.getConnection(DbUtil.dbUrlString, DbUtil.dbUser, DbUtil.dbpassword);
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void closeDB() // 关闭连接
	{
		try {
			stmt.close();
			con.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String username = null;
		String user_password = null; 
		int success = 1;//如果已经存在了此账号，则赋值为0，作为进入创建账号方法的进入条件
		if(source == buttonOfZhuce)
		{
			if (field1.getText().equals("") || field2.getText().equals("")||field3.getText().equals("") )
			{// 判断是否输入了用户名和密码
				JOptionPane.showMessageDialog(null, "请填写完整！");
			}
			else//密码账号都不为空后
			{
				try {
						this.connDB();
						String sql = "select * from user where username ='"+field1.getText()+"'";
						rs = stmt.executeQuery(sql);
						if(rs.next())
						{
							JOptionPane.showMessageDialog(null, "此账号已经存在！");
							field1.setText("");
							success = 0;
							this.closeDB();
						}
					} catch (HeadlessException e2) {
						e2.printStackTrace();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}//这个trycatch模块用来判断  创建账号之前数据库有没有此账号！，如果有 success = 0，就进入不了下面创建账号的语句
				
				if(field2.getText().equals(field3.getText())&&success==1)
				{
					
					String str = "insert into user values('" + field1.getText() + "','" +field2.getText()+"')";
					try {
						stmt.executeUpdate(str);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "注册成功！");
					this.dispose();
					new Login();
				}
				else
				{
					this.closeDB();
					if(success == 1)
					{
						JOptionPane.showMessageDialog(null,"两次输入的密码不匹配!" );
					}
				}
					
			 
					
			}
		}
		else if(source == buttonOfFanhui)
		{
			this.dispose();
			new Login();
		}
		
		
	}

}
