package Yonghu;
import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import Index.Login;
import Util.DbUtil;
public class Yonghuduan extends JFrame implements ActionListener{

	/*
	 * 
	 * 用户端的界面
	 */
	JTable table;
	JLabel label1,label2,label3;
	Object a[][];
	Object name[] = {"序号","车型","车主","价格","颜色","是否被租用"};
	JButton buttonOfKe,buttonOfXinxiliulan,buttonOfQyueding,buttonOfLogout,buttonOfXiangXi,buttonOfWo;
	Box box1,box2;
	JTextField field,field2;
	JPanel jPanel4,jPanel5;
	
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	String username =null;
	public Yonghuduan(String username)
	{
		
		init();
		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 640, 420);
		setTitle("用户界面");
		this.username = username;
	}
	
	void init()
	{
		label1 = new JLabel("汽车租赁信息浏览系统");
		buttonOfKe = new JButton("   可 租 用 车 辆   ");
		buttonOfKe.addActionListener(this);
		buttonOfXinxiliulan = new JButton("   汽车信息浏览   ");
		buttonOfXinxiliulan .addActionListener(this);
		buttonOfQyueding = new JButton("    确	            定       ");
		buttonOfQyueding.addActionListener(this);
		buttonOfLogout = new JButton("   退   出   登   录   ");
		buttonOfLogout.addActionListener(this);
		buttonOfXiangXi = new JButton("   详   细   信   息   ");
		buttonOfXiangXi.addActionListener(this);
		buttonOfWo = new JButton("   我   租   的   车   ");
		buttonOfWo.addActionListener(this);
		label2 = new JLabel("输入需要租用的汽车：");
		label3 = new JLabel("查询详细的信息编号");
		field = new JTextField();
		field2 = new JTextField();
		a = new Object[50][6];
		table = new JTable(a, name);//组件的创建
		table.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(table);
		
		box1 = Box.createVerticalBox();
		box1.add(Box.createVerticalStrut(20));
		box1.add(buttonOfKe);
		box1.add(Box.createVerticalStrut(10));
		box1.add(buttonOfXinxiliulan);
		box1.add(Box.createVerticalStrut(15));
		box1.add(label2);
		box1.add(Box.createVerticalStrut(5));
		box1.add(field);
		box1.add(Box.createVerticalStrut(5));
		box1.add(buttonOfQyueding);
		box1.add(Box.createVerticalStrut(15));
		box1.add(label3);
		box1.add(Box.createVerticalStrut(5));
		box1.add(field2);
		box1.add(Box.createVerticalStrut(5));
		box1.add(buttonOfXiangXi);
		box1.add(Box.createVerticalStrut(20));
		
		box1.add(buttonOfWo);
		box1.add(Box.createVerticalStrut(10));
		box1.add(buttonOfLogout);
		
		box2 = Box.createHorizontalBox();
		box2.add(Box.createHorizontalStrut(10));
		box2.add(box1);   //左边的按钮部分用 box布局
		
		jPanel4 = new JPanel();
		jPanel5 = new JPanel();
		jPanel4.setLayout(new BorderLayout());
		jPanel4.add(box2,BorderLayout.NORTH);//把左边的按钮部分放到jpanel4中。
		
//		jPanel1 = new JPanel();
//		jPanel2 = new JPanel();
//		jPanel3 = new JPanel();
//		jPanel4 = new JPanel();
//		jPanel5 = new JPanel();
//		
//		jPanel1.setLayout(new BorderLayout());
//		jPanel1.add(label2,BorderLayout.NORTH);
//		jPanel1.add(field,BorderLayout.CENTER);
//		jPanel1.add(buttonOfDelete,BorderLayout.SOUTH);//把删除的模块放一个jpanel1里
//		
//		jPanel2.setLayout(new BorderLayout());
//		jPanel2.add(jPanel1,BorderLayout.NORTH);
//		jPanel2.add(buttonOfLogout,BorderLayout.CENTER);//把删除模块和 退出登录模块放一个jpanel2里
//		
//		jPanel3.setLayout(new BorderLayout());
//		jPanel3.add(buttonOfXinxiluru,BorderLayout.NORTH);
//		jPanel3.add(buttonOfXinxiliulan,BorderLayout.CENTER);//信息录入浏览按钮放到jpanel3里
//		
//		jPanel4.setLayout(new BorderLayout());
//		jPanel4.add(jPanel3,BorderLayout.NORTH);
//		jPanel4.add(jPanel2,BorderLayout.CENTER);//把jpanel 2 3 放到 jpanel4里
//		
		jPanel5.setLayout(new BorderLayout());
		jPanel5.add(label1,BorderLayout.NORTH);
		jPanel5.add(scrollPane,BorderLayout.CENTER);//把表格 放jpanel5里
	
		this.setLayout(new BorderLayout());
		add(jPanel5,BorderLayout.EAST);
		add(jPanel4,BorderLayout.WEST);//把两个大的panel放到窗口里面

		
		
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
	
	public void xinXiLiuLan()
	{
		int i=0;
		 while(i<50)
		 {
			 a[i][0]=" ";
			 a[i][1]=" ";
			 a[i][2]=" ";
			 a[i][3]=" ";
			 a[i][4]=" ";
			 a[i][5]=" ";
			 i++;
		 }
		 i=0;
		 this.connDB();
		 try {
			stmt = con.createStatement();
			 String sql= "select * from car_information";
			 rs = stmt.executeQuery(sql);
			 while(rs.next())
			 {
				 String number = rs.getString("number");
				 String cartype = rs.getString("cartype");
				 String carower = rs.getString("carower");
				 String price = rs.getString("price");
				 String color = rs.getString("color");
				 String  hire= rs.getString("hire");
				 a[i][0]=number;
				 a[i][1]=cartype;
				 a[i][2]=carower;
				 a[i][3]=price;
				 a[i][4]=color;
				 a[i][5]=hire;
				 i++;
				 
			 }
			 this.closeDB();
			 repaint();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 this.closeDB();
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == buttonOfKe)//点击可以租用车辆的按钮
		{
			int success = 0;//用来判断 有没有 没被租用的车辆，如果能返回 “否” 值的集，则赋值为 1 
			 int i=0;
			 while(i<50)
			 {
				 a[i][0]=" ";
				 a[i][1]=" ";
				 a[i][2]=" ";
				 a[i][3]=" ";
				 a[i][4]=" ";
				 a[i][5]=" ";
				 i++;
			 }
			 i=0;//i 赋值为 0，为下面的循环做准备
			 this.connDB();
			 try {
				stmt = con.createStatement();
				 String sql= "select * from car_information where hire= '否';";//查询表里面 没被租用的车辆
				 rs = stmt.executeQuery(sql);
				 while(rs.next())//把查询到的信息写入 table
				 {
					 String number = rs.getString("number");
					 String cartype = rs.getString("cartype");
					 String carower = rs.getString("carower");
					 String price = rs.getString("price");
					 String color = rs.getString("color");
					 String hire = rs.getString("hire");
					
					 a[i][0]=number;
					 a[i][1]= cartype;
					 a[i][2]=carower;
					 a[i][3]=price;
					 a[i][4]=color;
					 a[i][5]=hire;
					 
					 i++;
					 success = 1;
				 }
				 this.closeDB();
				 repaint();//刷新一下
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 if(success == 0)
			 {
				 JOptionPane.showMessageDialog(null, "都已经被租用！");
			 }
		}
		else if(source == buttonOfXinxiliulan)//点击信息浏览按钮
		{
			 int i=0;
			 while(i<50)
			 {
				 a[i][0]=" ";
				 a[i][1]=" ";
				 a[i][2]=" ";
				 a[i][3]=" ";
				 a[i][4]=" ";
				 a[i][5]=" ";
				 i++;
			 }
			 i=0;
			 this.connDB();
			 try {
				stmt = con.createStatement();
				 String sql= "select * from car_information";
				 rs = stmt.executeQuery(sql);
				 while(rs.next())
				 {
					 String number = rs.getString("number");
					 String cartype = rs.getString("cartype");
					 String carower = rs.getString("carower");
					 String price = rs.getString("price");
					 String color = rs.getString("color");
					 String hire = rs.getString("hire");
					 
					 a[i][0]=number;
					 a[i][1]= cartype;
					 a[i][2]=carower;
					 a[i][3]=price;
					 a[i][4]=color;
					 a[i][5]=hire;
					 
					 i++;
				 }
				 this.closeDB();
				 repaint();
			} catch (SQLException e1){
				e1.printStackTrace();
			}
			 
			
		}
		else if (source == buttonOfQyueding)//点击 租用的确定键
		{
			if(field.getText().equals(""))//是否为空
			{
				JOptionPane.showMessageDialog(null, "请输入租用车辆的编号！");
			}
			else//不为空
			{
				this.connDB();
				String sql;
				try {
					stmt = con.createStatement();
					sql = "select * from car_information  where number='"+field.getText()+"' and hire = '否'";//获取输入的 序号并且 没被租用的信息
					rs = stmt.executeQuery(sql);
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				try {
					if(rs.next())//判断是否存在 此车辆
					{
						int n = JOptionPane.showConfirmDialog(this, "确定租用此车辆信息？","确认对话框",JOptionPane.YES_NO_OPTION);
						if(n == JOptionPane.YES_OPTION)//确认框
						{	
							try
							{
								stmt = con.createStatement();
								String sql2 = "update  car_information set hire = '是',username = '"+username+"' where number='"+field.getText()+"';";//把租用的赋值为 是 
								stmt.executeUpdate(sql2);
							}
							catch (SQLException e1)
							{
								e1.printStackTrace();
							}
							this.closeDB();
							repaint();
							field.setText("");
							JOptionPane.showMessageDialog(null,"租用成功！");
							xinXiLiuLan();
							
						}
						else if(n == JOptionPane.NO_OPTION)
						{
							
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "无法租用此车辆！");
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}	
			}
		}

		else if(source == buttonOfXiangXi)
		{
			this.dispose();
			new Xiangxi_yonghu(field2.getText(),username);
		}

		else if(source == buttonOfLogout)
		{
			this.dispose();
			new Login();
			
		}
		else if(source == buttonOfWo)//点击我租的车辆信息
		{
			int success = 0;//用来判断 有没有 没自己租用的车辆
			 int i=0;
			 while(i<50)
			 {
				 a[i][0]=" ";
				 a[i][1]=" ";
				 a[i][2]=" ";
				 a[i][3]=" ";
				 a[i][4]=" ";
				 a[i][5]=" ";
				 i++;
			 }
			 i=0;//i 赋值为 0，为下面的循环做准备
			 this.connDB();
			 try {
				stmt = con.createStatement();
				 String sql= "select * from car_information where username= '"+username+"';";//查询表里面 自己已经租用的车辆
				 rs = stmt.executeQuery(sql);
				 while(rs.next())//把查询到的信息写入 table
				 {
					 String number = rs.getString("number");
					 String cartype = rs.getString("cartype");
					 String carower = rs.getString("carower");
					 String price = rs.getString("price");
					 String color = rs.getString("color");
					 String hire = rs.getString("hire");
					
					 a[i][0]=number;
					 a[i][1]= cartype;
					 a[i][2]=carower;
					 a[i][3]=price;
					 a[i][4]=color;
					 a[i][5]=hire;
					 
					 i++;
					 success = 1;
				 }
				 this.closeDB();
				 repaint();//刷新一下
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 if(success == 0)
			 {
				 JOptionPane.showMessageDialog(null, "您还没有租用任何车辆！");
			 }
		}
		
		
	}

}
