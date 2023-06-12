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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Guanliyuan.Guanliyuan;
import Util.DbUtil;
import Yonghu.Yonghuduan;
public class Login extends JFrame implements ActionListener{

	JTextField zhanghao_field;
	JPasswordField mima_field;
	JRadioButton yonghu_radioButton,guanliyuan_radioButton;
	JButton login_button,zhuce_button;
	Box box1,box2,box3,box4,basebBox;//�˺ţ����룬����radiobutton��������ť��������ʽ���Ӳ��֡� basebox����ʽ�����ǰ���������
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	public Login()
	{
		setLayout(new FlowLayout());
		init();
		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 500, 500);
		setTitle("��½����");
	}
	
	void init()
	{
		
		box1 = Box.createHorizontalBox();
		box1.add(new JLabel("�˺�:"));
		box1.add(Box.createHorizontalStrut(8));
		zhanghao_field = new JTextField(15);
		box1.add(zhanghao_field);//��½���� �˺ź�������һ��
		
		box2 = Box.createHorizontalBox();
		box2.add(new JLabel("����:"));
		box2.add(Box.createHorizontalStrut(8));
		mima_field = new JPasswordField(15);
		box2.add(mima_field);//��½���������������һ��
		
		box3 = Box.createHorizontalBox();
		ButtonGroup group = new ButtonGroup();
		yonghu_radioButton = new JRadioButton("�û�");
		group.add(yonghu_radioButton);
		yonghu_radioButton.addActionListener(this);
		box3.add(yonghu_radioButton);
		box3.add(Box.createHorizontalStrut(8));
		guanliyuan_radioButton = new JRadioButton("����Ա");
		group.add(guanliyuan_radioButton);
		guanliyuan_radioButton.addActionListener(this);
		box3.add(guanliyuan_radioButton);//��½���� ��ѡ��
		
		
		box4 = Box.createHorizontalBox();
		login_button = new JButton("��½");
		login_button.addActionListener(this);
		box4.add(login_button);
		box4.add(Box.createHorizontalStrut(8));
		zhuce_button = new JButton("ע��");
		zhuce_button.addActionListener(this);
		box4.add(zhuce_button);//��½����������ť
		
		
		basebBox = Box.createVerticalBox();
		basebBox.add(Box.createVerticalStrut(50));
		basebBox.add(box1);
		basebBox.add(Box.createVerticalStrut(10));
		basebBox.add(box2);
		basebBox.add(Box.createVerticalStrut(30));
		basebBox.add(box3);
		basebBox.add(Box.createVerticalStrut(80));
		basebBox.add(box4);//��4�����ӷ�һ�������
		
		add(basebBox);
			
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
	@Override
	public void actionPerformed (ActionEvent e) {
		Object source = e.getSource();
		String username = null;
		String adminname = null;
		String admin_password = null;
		String user_password = null;
		if (source == login_button)//���������ǵ�½��ť���ͻ��ж�radiobuttonѡ�����ʲô��������Ӧ����Ӧ
		{
			if(!yonghu_radioButton.isSelected()&&!guanliyuan_radioButton.isSelected())//radiobuttonûѡ��
			{
				JOptionPane.showMessageDialog(null, "��ѡ����ݣ�");
			}
			else if (zhanghao_field.getText().equals("") || mima_field.getText().equals(""))
			{// �ж��Ƿ��������û���������
				JOptionPane.showMessageDialog(null, "��¼�������벻��Ϊ�գ�");
			} 
			else 
			{
				this.connDB();
				try
				{
					stmt = con.createStatement();
				} 
				catch (SQLException e2)
				{
					e2.printStackTrace();
				}
				if(guanliyuan_radioButton.isSelected())//���ѡ����ǹ���Ա�İ�ť
				{	
				
				 try {
						
						String sql ="select * from admin where adminname ='"+zhanghao_field.getText()+"'";
						rs = stmt.executeQuery(sql);
						if(rs.next())
						{
							adminname = rs.getString(1);
							admin_password = rs.getString(2);
							if(!mima_field.getText().equals(admin_password))
							{
								JOptionPane.showMessageDialog(null, "�������");
								mima_field.setText("");
									
							}
							else 
							{
								this.dispose();
								new Guanliyuan(false);
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null, "���˺�û�й���ԱȨ�ޣ�");
							zhanghao_field.setText("");
							mima_field.setText("");
						}							
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
					e1.printStackTrace();
					}	
				}
				else //ѡ�����user��ť
				{
					
					try {
					
							String sql ="select * from user where username ='"+zhanghao_field.getText()+"'";
							rs = stmt.executeQuery(sql);
							if(rs.next())
							{
								username = rs.getString(1);
								user_password = rs.getString(2);
								if(!mima_field.getText().equals(user_password))
								{
									JOptionPane.showMessageDialog(null, "�������");
									mima_field.setText("");
										
								}
								else 
								{
									this.dispose();
									new Yonghuduan(zhanghao_field.getText());
								}
							}
							else
							{
								JOptionPane.showMessageDialog(null, "�����ڴ��˺ţ���ע�ᣡ");
								zhanghao_field.setText("");
								mima_field.setText("");
							}							
						} catch (HeadlessException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
						e1.printStackTrace();
						}
				}
		  }
		
		}
		else if(source == zhuce_button)
		{
		
			this.dispose();
			new Zhuce();
		}
	
	}
		
}
	
	
	
	

