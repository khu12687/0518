package table;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/*
 * �����ͺ��̽��� ������ ����ϱ⿡ ����ȭ�� ������Ʈ�� JTable�� ��������
 * JTable MVC������ ������ ������Ʈ �� �ϳ��̴�..
 * */
public class JTableTest extends JFrame{
	JTable table;
	JScrollPane scroll;
	
	String[] column = {
			"student_id","name","phone"
	}; //���̺��� �÷� ������ ������ �迭�� ä������
//	String[][] data = new String[][] {
//		{"1","scott","010-1234-5678"},
//		{"2","adams","010-1234-5671"},
//		{"3","king","010-1234-5672"}
//	};
	
	String[][] data = new String[0][0];
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="c##java";
	String password="1234";
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	public JTableTest() {
		connect(); //���̺� ������ �ֱ� ���ؼ��� table �������� �̹� db������ �Ǿ� �־�� �ϹǷ�..
		
		table = new JTable(data,column); //��� ǥ���Ѵٸ�, ��� ���� �����ؾ� ��
		scroll = new JScrollPane(table);
		setLayout(new FlowLayout());
		
		add(scroll);
		
		setSize(500,300);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(con!=null) {
					try {
						con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		//JTable�� ������ ����
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("�� �����?");
				
				//Table���� ������ �࿡ ���� ������ ����غ���!!
				int row = table.getSelectedRow();
				int col = table.getSelectedColumn();
				//JOptionPane.showMessageDialog(JTableTest.this, "������ row : "+row+",������ column : "+col);
				JOptionPane.showMessageDialog(JTableTest.this, data[row][col]);
			}
		});
		
	}
	
	//����Ŭ�� ����
	public void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			JOptionPane.showMessageDialog(this, "����̹� �ε强��!");
			
			con = DriverManager.getConnection(url,user,password);
			if(con!=null) {
				JOptionPane.showMessageDialog(this, "���Ӽ���");
				getData();
			}else {
				JOptionPane.showMessageDialog(this, "���ӽ���");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "����̹� �ε� ����");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void getData() {
		String sql = "select * from student";
		
		//������ ���� ��ä����
		try { //TYPE_SCROLL_INSENSITIVE : Ŀ���� ��ĭ���� �ƴ� , ��������� ������ �� �ִ� �Ӽ�
			pstmt = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//���� ��������(DML, select �Ǵ�)
			rs = pstmt.executeQuery();
			//JTable�� �����ϰ� �ִ� �������迭�� ���⼭ �����Ͽ� ä������
			
			//���ڵ��� �� ������ �˾Ƹ��ߴ� �޼���� ���� ��������!!
			//���� Ŀ���� ���ڵ��� ������ ���� ��, �� ��ġ�� ����
			rs.last(); //Ŀ���� ���� ������ row ������!!
			
			//Ŀ���� ��ġ �˾ƺ���!!
			int row = rs.getRow(); //���� Ŀ���� ��ġ������ ���ȣ!!
			JOptionPane.showMessageDialog(this,"��"+row+"�� �� �ֽ��ϴ�");
			
			//������ �迭����
			data = new String[row][3];
			
			//������ �迭�� ������ ä���ֱ�!!
			//��� ����� ������ �ֿܼ� ����ϱ�!!
			rs.beforeFirst(); //Ŀ���� ź�� ������ ���ڸ��� ��������!!
			
			int index=0; //������ �������� ����
			
			while(rs.next()) {
				String student_id = Integer.toString(rs.getInt("student_id"));
				String name =rs.getString("name");
				String phone=rs.getString("phone");
				System.out.println(student_id+","+name+","+phone);
				
				//�ѻ�� �ѻ���� ������ ������ �迭�� �ű��
				data[index][0]=student_id; //student_id
				data[index][1]=name; //name
				data[index][2]=phone; //phone
				
				index++;
			}
			System.out.println("������ �������迭�� ���̴� "+data.length);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//rs�� �ִ� ��� ���ڵ尡 �������迭�� �Ű������Ƿ�, ���̻� ����� ���� ����..
			//���� �ݾƹ����� �ȴ�!!
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
	public static void main(String[] args) {
		new JTableTest();
	}
}
