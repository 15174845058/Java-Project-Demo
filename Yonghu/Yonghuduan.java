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
	 * �û��˵Ľ���
	 */
	JTable table;
	JLabel label1,label2,label3;
	Object a[][];
	Object name[] = {"���","����","����","�۸�","��ɫ","�Ƿ�����"};
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
		setTitle("�û�����");
		this.username = username;
	}
	
	void init()
	{
		label1 = new JLabel("����������Ϣ���ϵͳ");
		buttonOfKe = new JButton("   �� �� �� �� ��   ");
		buttonOfKe.addActionListener(this);
		buttonOfXinxiliulan = new JButton("   ������Ϣ���   ");
		buttonOfXinxiliulan .addActionListener(this);
		buttonOfQyueding = new JButton("    ȷ	            ��       ");
		buttonOfQyueding.addActionListener(this);
		buttonOfLogout = new JButton("   ��   ��   ��   ¼   ");
		buttonOfLogout.addActionListener(this);
		buttonOfXiangXi = new JButton("   ��   ϸ   ��   Ϣ   ");
		buttonOfXiangXi.addActionListener(this);
		buttonOfWo = new JButton("   ��   ��   ��   ��   ");
		buttonOfWo.addActionListener(this);
		label2 = new JLabel("������Ҫ���õ�������");
		label3 = new JLabel("��ѯ��ϸ����Ϣ���");
		field = new JTextField();
		field2 = new JTextField();
		a = new Object[50][6];
		table = new JTable(a, name);//����Ĵ���
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
		box2.add(box1);   //��ߵİ�ť������ box����
		
		jPanel4 = new JPanel();
		jPanel5 = new JPanel();
		jPanel4.setLayout(new BorderLayout());
		jPanel4.add(box2,BorderLayout.NORTH);//����ߵİ�ť���ַŵ�jpanel4�С�
		
//		jPanel1 = new JPanel();
//		jPanel2 = new JPanel();
//		jPanel3 = new JPanel();
//		jPanel4 = new JPanel();
//		jPanel5 = new JPanel();
//		
//		jPanel1.setLayout(new BorderLayout());
//		jPanel1.add(label2,BorderLayout.NORTH);
//		jPanel1.add(field,BorderLayout.CENTER);
//		jPanel1.add(buttonOfDelete,BorderLayout.SOUTH);//��ɾ����ģ���һ��jpanel1��
//		
//		jPanel2.setLayout(new BorderLayout());
//		jPanel2.add(jPanel1,BorderLayout.NORTH);
//		jPanel2.add(buttonOfLogout,BorderLayout.CENTER);//��ɾ��ģ��� �˳���¼ģ���һ��jpanel2��
//		
//		jPanel3.setLayout(new BorderLayout());
//		jPanel3.add(buttonOfXinxiluru,BorderLayout.NORTH);
//		jPanel3.add(buttonOfXinxiliulan,BorderLayout.CENTER);//��Ϣ¼�������ť�ŵ�jpanel3��
//		
//		jPanel4.setLayout(new BorderLayout());
//		jPanel4.add(jPanel3,BorderLayout.NORTH);
//		jPanel4.add(jPanel2,BorderLayout.CENTER);//��jpanel 2 3 �ŵ� jpanel4��
//		
		jPanel5.setLayout(new BorderLayout());
		jPanel5.add(label1,BorderLayout.NORTH);
		jPanel5.add(scrollPane,BorderLayout.CENTER);//�ѱ�� ��jpanel5��
	
		this.setLayout(new BorderLayout());
		add(jPanel5,BorderLayout.EAST);
		add(jPanel4,BorderLayout.WEST);//���������panel�ŵ���������

		
		
	}
	
	
	public void connDB() { // �������ݿ�
		try {
			Class.forName("com.mysql.jdbc.Driver");//ע������
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {//��������
			con = DriverManager.getConnection(DbUtil.dbUrlString, DbUtil.dbUser, DbUtil.dbpassword);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void closeDB() // �ر�����
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
		if(source == buttonOfKe)//����������ó����İ�ť
		{
			int success = 0;//�����ж� ��û�� û�����õĳ���������ܷ��� ���� ֵ�ļ�����ֵΪ 1 
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
			 i=0;//i ��ֵΪ 0��Ϊ�����ѭ����׼��
			 this.connDB();
			 try {
				stmt = con.createStatement();
				 String sql= "select * from car_information where hire= '��';";//��ѯ������ û�����õĳ���
				 rs = stmt.executeQuery(sql);
				 while(rs.next())//�Ѳ�ѯ������Ϣд�� table
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
				 repaint();//ˢ��һ��
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 if(success == 0)
			 {
				 JOptionPane.showMessageDialog(null, "���Ѿ������ã�");
			 }
		}
		else if(source == buttonOfXinxiliulan)//�����Ϣ�����ť
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
		else if (source == buttonOfQyueding)//��� ���õ�ȷ����
		{
			if(field.getText().equals(""))//�Ƿ�Ϊ��
			{
				JOptionPane.showMessageDialog(null, "���������ó����ı�ţ�");
			}
			else//��Ϊ��
			{
				this.connDB();
				String sql;
				try {
					stmt = con.createStatement();
					sql = "select * from car_information  where number='"+field.getText()+"' and hire = '��'";//��ȡ����� ��Ų��� û�����õ���Ϣ
					rs = stmt.executeQuery(sql);
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				try {
					if(rs.next())//�ж��Ƿ���� �˳���
					{
						int n = JOptionPane.showConfirmDialog(this, "ȷ�����ô˳�����Ϣ��","ȷ�϶Ի���",JOptionPane.YES_NO_OPTION);
						if(n == JOptionPane.YES_OPTION)//ȷ�Ͽ�
						{	
							try
							{
								stmt = con.createStatement();
								String sql2 = "update  car_information set hire = '��',username = '"+username+"' where number='"+field.getText()+"';";//�����õĸ�ֵΪ �� 
								stmt.executeUpdate(sql2);
							}
							catch (SQLException e1)
							{
								e1.printStackTrace();
							}
							this.closeDB();
							repaint();
							field.setText("");
							JOptionPane.showMessageDialog(null,"���óɹ���");
							xinXiLiuLan();
							
						}
						else if(n == JOptionPane.NO_OPTION)
						{
							
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "�޷����ô˳�����");
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
		else if(source == buttonOfWo)//�������ĳ�����Ϣ
		{
			int success = 0;//�����ж� ��û�� û�Լ����õĳ���
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
			 i=0;//i ��ֵΪ 0��Ϊ�����ѭ����׼��
			 this.connDB();
			 try {
				stmt = con.createStatement();
				 String sql= "select * from car_information where username= '"+username+"';";//��ѯ������ �Լ��Ѿ����õĳ���
				 rs = stmt.executeQuery(sql);
				 while(rs.next())//�Ѳ�ѯ������Ϣд�� table
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
				 repaint();//ˢ��һ��
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 if(success == 0)
			 {
				 JOptionPane.showMessageDialog(null, "����û�������κγ�����");
			 }
		}
		
		
	}

}
