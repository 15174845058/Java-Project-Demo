package Guanliyuan;
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
import javax.swing.JTextField;
/*
 * 信息录入界面
 */

import Util.DbUtil;

public class Luru extends JFrame implements ActionListener {

	JTextField field1,field2,field3,field4,field5,field6;
	Box box1,box2,box3,box4,box5,box6,box7,baseBox;
	JButton buttonOfQueDing,buttonOfReset,buttonOfQuXIAO;
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public Luru()
	{
		
		init();
		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(550, 200, 550, 280);
		setTitle("车辆信息录入界面");
	}
	
	void init()
	{
		JLabel label1 = new JLabel(" 序        号  : ");
		JLabel label2 = new JLabel(" 车        型  : ");
		JLabel label3 = new JLabel(" 车        主  : ");
		JLabel label4 = new JLabel("价格(元/天):");
		JLabel label5 = new JLabel(" 颜        色  : ");
		JLabel label6 = new JLabel("已租用(是/否):");
		
		field1 = new JTextField();
		field2 = new JTextField();
		field3 = new JTextField();
		field4 = new JTextField();
		field5 = new JTextField();
		field6 = new JTextField();
		
		buttonOfQueDing = new JButton("提交");
		buttonOfReset = new JButton("重置");
		buttonOfQuXIAO = new JButton("取消");
		buttonOfQueDing.addActionListener(this);
		buttonOfQuXIAO.addActionListener(this);
		buttonOfReset.addActionListener(this);
		
		
		box1 = Box.createHorizontalBox();
		box1.add(box1.createHorizontalStrut(8));
		box1.add(label1);
		box1.add(box1.createHorizontalStrut(8));
		box1.add(field1);
		box1.add(box1.createHorizontalStrut(8));
		
		box2 = Box.createHorizontalBox();
		box2.add(box2.createHorizontalStrut(8));
		box2.add(label2);
		box2.add(box2.createHorizontalStrut(8));
		box2.add(field2);
		box2.add(box2.createHorizontalStrut(8));
		
		box3 = Box.createHorizontalBox();
		box3.add(box3.createHorizontalStrut(8));
		box3.add(label3);
		box3.add(box3.createHorizontalStrut(8));
		box3.add(field3);
		box3.add(box3.createHorizontalStrut(8));
		
		box4 = Box.createHorizontalBox();
		box4.add(box4.createHorizontalStrut(8));
		box4.add(label4);
		box4.add(box4.createHorizontalStrut(8));
		box4.add(field4);
		box4.add(box4.createHorizontalStrut(8));
		
		box5 = Box.createHorizontalBox();
		box5.add(box5.createHorizontalStrut(8));
		box5.add(label5);
		box5.add(box5.createHorizontalStrut(8));
		box5.add(field5);
		box5.add(box5.createHorizontalStrut(8));
		
		box6 = Box.createHorizontalBox();
		box6.add(box6.createHorizontalStrut(8));
		box6.add(label6);
		box6.add(box6.createHorizontalStrut(8));
		box6.add(field6);
		box6.add(box6.createHorizontalStrut(8));
		
		box7 = Box.createHorizontalBox();
		box7.add(box7.createHorizontalStrut(8));
		box7.add(buttonOfQueDing);
		box7.add(box7.createHorizontalStrut(8));
		box7.add(buttonOfQuXIAO);
		box7.add(box7.createHorizontalStrut(8));
		box7.add(buttonOfReset);
		box7.add(box7.createHorizontalStrut(8));
		
		baseBox = Box.createVerticalBox();
		baseBox.add(Box.createVerticalStrut(15));
		baseBox.add(box1);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(box2);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(box3);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(box4);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(box5);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(box6);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(box7);
		baseBox.add(Box.createVerticalStrut(15));
		
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
	
	public static boolean isNumeric(String str){
		   for (int i = str.length();--i>=0;){   
		    if (!Character.isDigit(str.charAt(i))){
		     return false;
		    }
		   }
		   return true;
		  }//判断 编号输入的是不是整数！
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String number = field1.getText();
		String cartype = field2.getText() ;
		String carower = field3.getText() ;
		String price = field4.getText();
		String color = field5.getText();
		String hire = field6.getText();
//		if(field6.getText().equals("是")&&field6.getText().equals("已经")&&field6.getText().equals("已租用")&&field6.getText().equals("已"))
//		{
//			hire = "1";
//		}
//		else 
//		{
//			hire = "0";
//		}
		
		
		if(source == buttonOfQueDing)
		{
			if(number.equals("")||cartype.equals("")||carower.equals("")||price.equals("")||color.equals("")||hire.equals(""))
			{
				JOptionPane.showMessageDialog(null, "请填写完整！");
			}
			else if(!isNumeric(number))
			{
				JOptionPane.showMessageDialog(null, "序号 请输入整数！");
			}
			else
			{
				this.connDB();
				try {
					int numberint = Integer.parseInt(field1.getText());
					stmt = con.createStatement();
					String str = "insert into car_information (number,cartype,carower,price,color,hire)values('"+numberint+"','"+cartype+"','"+carower+"','"+price+"','"+color+"','"+hire+"');";
					stmt.executeUpdate(str);
					JOptionPane.showMessageDialog(null, "录入成功！");
					this.closeDB();
					this.dispose();
					new Guanliyuan(true);
				} catch (SQLException e1) {
//				e1.printStackTrace();
					
				JOptionPane.showMessageDialog(null, "此编号已经被使用，请换一个编号！");
				}
				
				
			}
			
		}
		else if(source == buttonOfQuXIAO)
		{
			this.dispose();
			new Guanliyuan(false);
			
		}
		else if(source == buttonOfReset)
		{
			//field1.setText("");
			field2.setText("");
			field3.setText("");
			field4.setText("");
			field5.setText("");
			field6.setText("");
			
			
		}
		
		
	}

	
}
