package db.oracle;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * JDBC��? Java DataBase Connectivity�� ���ڷ� �ڹپ��� �����ͺ��̽��� �ٷ�� ����� �ǹ��ϸ�,
 * ���õ� ��Ű���� java.sql�̴�!!
 * */

//select�� �����Ͽ� ���ڵ� ��������!
public class SelectApp extends JFrame implements ActionListener{
	JButton bt_connect,bt_insert,bt_select;
	JTextField t_insert;
	JTextArea area;
	JScrollPane scroll;
	
	
	String url="jdbc:oracle:thin:@localhost:1521:XE"; //�ϱ����
	String user ="c##java";
	String password="1234";
	
	//�ٸ� ������ ex)MS) Connection��ü�� ������ ���������,
	//�ڹپ����� Connection �� ��ü�� ������ �õ��ϴ� ��ü�� �ƴ϶� ������ �����Ǿ�� �޸𸮿� �ö���� �������̽��̴�!!
	//�׷��ٸ� ������ ���� �õ��ϳ�? DriverManager ��ü��!!
	//��� ) Connection ��ü�� ������ ������ ����, �� ������ ���� ��ü�̱� ������ ���� �� ��ü�� null�̶�� ������
	//���� ���� ���̴�!!
	Connection con; //���ӵ� ���� �� ������ ���� ��ü
	PreparedStatement pstmt; //���� ���� ��ü
	ResultSet rs; //�������� select�� ��� �� ��������� ������ ��ü
	
	public SelectApp() {
		bt_connect = new JButton("����");
		bt_insert = new JButton("insert");
		bt_select = new JButton("select");
		t_insert = new JTextField(40);
		area = new JTextArea(15,40);
		scroll = new JScrollPane(area);
		
		setLayout(new FlowLayout());
		add(bt_connect);
		add(bt_insert);
		add(bt_select);
		add(t_insert);
		add(scroll);
		
		setSize(500,400);
		setVisible(true);
		
		//������� ������ ����
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//�����ͺ��̽��� ���õ� �ڿ� ����!!
				closeDB();
				System.exit(0);
			}
		});
		
		//��ư�� ������ ����
		bt_connect.addActionListener(this);
		bt_select.addActionListener(this);
	}
	public void closeDB() {
		if(con!=null) { //��ü�� ���� �ƴϸ� �ݴ´�~!
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void connect() {
		/*
		 * [�����ͺ��̽� ���α׷��� ����]
		 * 1)����̹� �ε�!! (�����翡�� �����ϴ� java���� ���� ��鷯)
		 * 	mysql: mysql ���ۻ翡�� �ٿ�ε� == ����Ŭ��
		 *  oracle: oracle�翡�� �ٿ�ε� == ����Ŭ��
		 *  mssql: ms���� �ٿ�ε�
		 *  ��� db:
		 * 2)����
		 * 3)������ ����
		 * 4)�����ͺ��̽� ���� �ڿ� ����
		 * */
		
		//����̹� �ε�
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("����̹��ε� ����");
			//����
			con=DriverManager.getConnection(url,user,password);
			if(con!=null) {
				System.out.println("���Ӽ���");				
			}else {
				System.out.println("���ӽ���");
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("����̹��ε� ����");
			e.printStackTrace();
		} //string ������ ���� Ŭ�������� �ε������!!
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//EMP ���̺� ���ڵ� ��������!!
	public void select() {
		String sql="select * from emp";
		try {
			pstmt=con.prepareStatement(sql);
			
			//�غ�� �������� �����غ���!!
			rs=pstmt.executeQuery(); //select���� ������!!
			//insert,update,delect ��쿣 �� ������ΰ� 0�� �ƴϸ� �ɻ� ���� ������ ������ �����Ͱ� ����..
			//������ select���� ������ ��°���� ���� ���ø����̼��� ����ǰ� �ִ� pc�� �����;� �Ѵ�!!
			//sun������ �����ͺ��̽��� ��ȸ ����� ResultSet�̶�� �������̽��� �������ش�!!
		
			//ResultSet ��ü�� �̿��Ϸ���, �����ڰ� Ŀ���� �����ؾ��Ѵ�!
			//�׸��� ��Ŀ���� ����Ʈ ��ġ�� ù ���ڵ庸�� �����ִ�!!
			
			
			//area�� ���~!!
			area.append("ENAME\t"+"SAL\t"+"JOB\t"+"HIREDATE\n");
			area.append("-----------------------------------------------------------------------------------------\n");
			
			while(rs.next()) { //Ŀ���� ��ĭ ����!!
				String ename = rs.getString("ename"); //�÷������� �����͸� ��������!!
				String sal = Integer.toString(rs.getInt("sal"));
				String job = rs.getString("job");
				String hiredate = rs.getString("hiredate");
				
				area.append(ename+"\t"+sal+"\t"+job+"\t"+hiredate+"\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//EMP ���̺� ���ڵ� ��������!!
	public void select2() {
		String sql="select * from student";
		try {
			pstmt=con.prepareStatement(sql);
			
			//�غ�� �������� �����غ���!!
			rs=pstmt.executeQuery(); //select���� ������!!
			//insert,update,delect ��쿣 �� ������ΰ� 0�� �ƴϸ� �ɻ� ���� ������ ������ �����Ͱ� ����..
			//������ select���� ������ ��°���� ���� ���ø����̼��� ����ǰ� �ִ� pc�� �����;� �Ѵ�!!
			//sun������ �����ͺ��̽��� ��ȸ ����� ResultSet�̶�� �������̽��� �������ش�!!
		
			//ResultSet ��ü�� �̿��Ϸ���, �����ڰ� Ŀ���� �����ؾ��Ѵ�!
			//�׸��� ��Ŀ���� ����Ʈ ��ġ�� ù ���ڵ庸�� �����ִ�!!
			
			//area�� �����, ���� ������ �����!!
			area.setText("");
			
			//area�� ���~!!
			area.append("STUDENT_ID\t"+"NAME\t"+"PHONE\n");
			area.append("-----------------------------------------------------------------------------------------\n");
			
			while(rs.next()) { //Ŀ���� ��ĭ ����!!
				String student_id = Integer.toString(rs.getInt("student_id")); //�÷������� �����͸� ��������!!
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				
				area.append(student_id+"\t"+name+"\t"+phone+"\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void insert() {
		//insert ���� ������ ���� �� PreparedStatement ������
		//�� ������ �ϳ����� 1:1 �����ϴ� PreparedStatement �ʿ���
		//String sql ="insert into student(student_id,name,phone)"; 
		//sql+=" values(seq_student.nextval, 'test','1234')";
		String sql = t_insert.getText();
		try {
			pstmt = con.prepareStatement(sql);
			
			//�غ�� ������ ����!!
			//DML(insert,update,delete) �� ��쿣 executeUpdate()
			//select �� ��쿣 ����� �����;� �ϹǷ� executeQuery() �̿�
			
			//��ȯ���� ������ ������ DML�� ���� ������ ���� ���ڵ���� ��ȯ��
			//���� insert�� ��쿣 ������ 1�Ǹ� ���Ƿ� ������ ��� 1��ȯ 
			//update,delete�� ��쿣 �ݿ��� ���ڵ���� �˼� �����Ƿ� 1�̻��� ���� ���´�...
			//�� ������ ��쿣 ��� 0�� ��ȯ�ȴ�!! ���� DML�������θ� 0�� ��ȯ�ϴ� �� ���η� �Ǵ��ϸ� �ȴ�!!
			
			//0�� ���Դٰ� �Ͽ� ������ �ƴϴ�. ���� ���ǿ� �´� DML�� ������� �ʾ��� ���̴�..������ ��쿣 Exception�� �߻�
			int result = pstmt.executeUpdate(); //DML ����!!
			if(result==0) {
				JOptionPane.showMessageDialog(this, "��ϵ��� ����");
			}else {
				JOptionPane.showMessageDialog(this, "��ϼ���");
				select2();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==bt_connect) { //���� ��ư�� ������
			connect();
		}else if(obj==bt_select) { //select ��ư�� ������
			select();
		}else if(obj==bt_insert) { //insert ��ư�� ������
			insert();
		}
	}
	public static void main(String[] args) {
		new SelectApp();
	}
}
