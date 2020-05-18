package table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * �����ͺ��̽� ������ Ŭ�������� ���� �־�� ���, Ȥ���� db������ ����ȴٸ�???
 * ��� ������ Ŭ�������� �� ��� �����ؾ� �Ѵ�.. �� �ϵ��ڵ��� ���ؾ� �Ѵ�!!
 * HardCoding : �ܺ��� ��ȭ�� ������ �� ���� ������ �ڵ��ۼ� ���
 * */
public class ConnectionManager {
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="c##java";
	String password="1234";
	
	//������ �����ϴ� �޼���!!
	public Connection getConnection() {
		Connection con = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("����̹� �ε� ����");

			con = DriverManager.getConnection(url,user,password);
			
			if(con==null) {
				System.out.println("���ӽ���");
			}else {
				System.out.println("���Ӽ���");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("����̹� �ε� ����");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con; //���������� ��ȯ�Ѵ�!!
	}
	
	//������ �����ϴ� �޼���!!
	public void closeDB(Connection con) {
		
	}
	
	//DML�� ������ ���
	public void closeDB(PreparedStatement pstmt) {
			
	}
	
	//select�� ������ ���
	public void closeDB(Connection con, PreparedStatement pstmt,ResultSet rs) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
