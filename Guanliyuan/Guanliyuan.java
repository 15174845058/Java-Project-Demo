package Guanliyuan;
import java.awt.BorderLayout;

import Util.DbUtil;
import Yonghu.*;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.FieldPosition;

import javax.naming.InitialContext;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import Index.*;
public class Guanliyuan extends JFrame implements ActionListener{

	/*
	 * 
	 * 管理员端的界面
	 */
	JTable table;
	JLabel label1,label2,label3,label4;
	Object a[][];
	Object name[] = {"编号","车型","车主","价格(元/天)","颜色","是否被租用","租用的用户"};
	JButton buttonOfXinxiluru,buttonOfXinxiliulan,buttonOfDelete,buttonOfLogout,buttonOfXiangXi,buttonOfXiugai;
	Box box1,box2;
	JTextField field,field2,field3;
	JPanel jPanel4,jPanel5;
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public Guanliyuan(Boolean success)
	{
		
		init();
		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 625, 490);
		setTitle("管理员界面");
		if(success)//successs是一个boolean类型，如果为true，打开此窗口直接信息浏览，false表里面没有信息，需要点击信息浏览！
		{
			xinXiLiuLan();
			
		}
	}
	
	void init()
	{
		label1 = new JLabel("汽车租赁信息管理系统");
		buttonOfXinxiluru = new JButton("  汽车信息录入  ");
		buttonOfXinxiluru.addActionListener(this);
		buttonOfXinxiliulan = new JButton("  汽车信息浏览  ");
		buttonOfXinxiliulan.addActionListener(this);
		buttonOfDelete = new JButton("    删	            除      ");
		buttonOfDelete.addActionListener(this);
		buttonOfLogout = new JButton("  退   出   登   录  ");
		buttonOfLogout.addActionListener(this);
		buttonOfXiugai = new JButton("    修	           改      ");
		buttonOfXiugai.addActionListener(this);
		buttonOfXiangXi = new JButton("  详   细   信   息  ");
		buttonOfXiangXi.addActionListener(this);
		label2 = new JLabel("待删除信息编号：");
		label3 = new JLabel("待修改信息的编号：");
		label4 = new JLabel("待查询详情的编号：");
		field = new JTextField();
		field2 = new JTextField();
		field3 = new JTextField();
		
		a = new Object[50][7];
		table = new JTable(a, name);//组件的创建
		table.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(table);
		
		box1 = Box.createVerticalBox();
		box1.add(Box.createVerticalStrut(20));
		box1.add(buttonOfXinxiluru);
		box1.add(Box.createVerticalStrut(10));
		box1.add(buttonOfXinxiliulan);
		box1.add(Box.createVerticalStrut(15));
		box1.add(label2);
		box1.add(Box.createVerticalStrut(5));
		box1.add(field);
		box1.add(Box.createVerticalStrut(5));
		box1.add(buttonOfDelete);
		box1.add(Box.createVerticalStrut(25));
		box1.add(label3);
		box1.add(Box.createVerticalStrut(5));
		box1.add(field2);
		box1.add(Box.createVerticalStrut(5));
		box1.add(buttonOfXiugai);
		box1.add(Box.createVerticalStrut(25));
		box1.add(label4);
		box1.add(Box.createVerticalStrut(5));
		box1.add(field3);
		box1.add(Box.createVerticalStrut(5));
		box1.add(buttonOfXiangXi);
		box1.add(Box.createVerticalStrut(40));
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
	
	public void xinXiLiuLan()//信息浏览的方法，因为删除数据后会刷新一下，自动调用此函数。
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
			 a[i][6]=" "; 
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
				 String username= rs.getString("username");
				 a[i][0]=number;
				 a[i][1]=cartype;
				 a[i][2]=carower;
				 a[i][3]=price;
				 a[i][4]=color;
				 a[i][5]=hire;
				 a[i][6]=username;
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
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == buttonOfXinxiluru)//点击信息修改按钮
		{
			this.dispose();
			new Luru();
		}
		else if(source == buttonOfXinxiliulan)//点击信息浏览按钮
		{
			xinXiLiuLan();
			
		}
		else if(source == buttonOfXiugai)//点击修改按钮
		{
			
			if(field2.getText().equals(""))
			{
				 JOptionPane.showMessageDialog(null, "输入修改车型的编号！");
			}
			else
			{
				this.dispose();
				new Xiugai(field2.getText());
			}
		}
		else if(source == buttonOfXiangXi)//点击详细信息按钮
		{
			this.dispose();
			new Xiangxi(field3.getText());
		}
		else if(source == buttonOfDelete)//点击删除按钮
		{
			if(field.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "请输入删除车辆的编号！");
			}
			else
			{
				this.connDB();
				String sql;
				try {
					stmt = con.createStatement();
					sql = "select * from car_information  where number='"+field.getText()+"'";//表里找到需要删除的车信息
					rs = stmt.executeQuery(sql);
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				try {
					if(rs.next())//判断是否有 输入编号的 车辆
					{
						
						int n = JOptionPane.showConfirmDialog(this, "确定删除此车辆信息？","确认对话框",JOptionPane.YES_NO_OPTION);//确认文本框
						if(n == JOptionPane.YES_OPTION)
						{	
							String hire2 = rs.getString("hire");
							if(hire2.equals("是"))
							{
								int m = JOptionPane.showConfirmDialog(this, "此车辆正在被租用，是否删除？","确认对话框",JOptionPane.YES_NO_OPTION);//确认文本框
								if(m == JOptionPane.YES_OPTION)
								{
									try
									{
										stmt = con.createStatement();
										String sql2 = "delete from car_information where number='"+field.getText()+"';";
										stmt.executeUpdate(sql2);
									}
									catch (SQLException e1)
									{
										e1.printStackTrace();
									}
									this.closeDB();
									repaint();
									field.setText("");
									JOptionPane.showMessageDialog(null,"删除成功！");
									xinXiLiuLan();
									return;
								}
								else 
								{
									return;
//									try
//									{
//										stmt = con.createStatement();
//										String sql2 = "delete from car_information where number='"+field.getText()+"';";
//										stmt.executeUpdate(sql2);
//									}
//									catch (SQLException e1)
//									{
//										e1.printStackTrace();
//									}
//									this.closeDB();
//									repaint();
//									field.setText("");
//									JOptionPane.showMessageDialog(null,"删除成功！");
//									xinXiLiuLan();
								
								}
								

							}
							try
							{
								stmt = con.createStatement();
								String sql2 = "delete from car_information where number='"+field.getText()+"';";
								stmt.executeUpdate(sql2);
							}
							catch (SQLException e1)
							{
								e1.printStackTrace();
							}
							this.closeDB();
							repaint();
							field.setText("");
							JOptionPane.showMessageDialog(null,"删除成功！");
							xinXiLiuLan();
							
						}
						else if(n == JOptionPane.NO_OPTION)
						{
							
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "没有此编号的车辆信息！");
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
				
				
			}
			
		}
		else if(source == buttonOfLogout)//退出
		{
			this.dispose();
			new Login();
			
		}
		
	}

}
