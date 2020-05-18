package table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

//View �� Jtable�� �����Ϳ� �и������ִ� ��ü�� TableModel�������̽��� ������ 
//AbstractTableModel�� ������ �غ���!!
public class DeptModel extends AbstractTableModel{
	/*
	 *  �Ʒ��� ������ �޼������ �ʿ�� �ϰ�, ȣ���ϴ� ��ü�� �ٷ� JTable�̴�
	 * */
	//�츮��� ��� ������ ������ Ŭ������ �����Ѵ�!!
	ConnectionManager connectionManager;
	String[][] data = new String[][] {
		{"���","����","����"},
		{"�ڹ�","����Ŭ","Mysql"}
	};
	
	
	public DeptModel() {
		connectionManager = new ConnectionManager();
		
		//���� �� Ŀ�ؼ� ��ü�� ��ȯ����!! why? �Ʒ��� pstmt�� �ν��Ͻ��� ��� ����..
		Connection con = connectionManager.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		//������ �� ���ĺ��� ������ ������ �����ϴ�
		//���Ӱ�ü�κ��� �ν��Ͻ��� ��´�
		String sql ="select * from dept";
		try {
			pstmt = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			
			//rs�� last �� ������!!
			rs.last();
			int total = rs.getRow(); //������ ���� ��ȣ�� ��ȯ!!
			//rs�� ���� ǥ������ ���� data�������迭�� �Űܴ��, 
			//rs,psmt�� ���̻� �ʿ�����Ƿ� �ݾƹ�����!!
			data = new String[total][3];
			
			//rs�� Ŀ����ġ ���󺹱�!!
			rs.beforeFirst();
			
			int index=0; //������ �����ϴ� ����!!
			while(rs.next()) {
				String deptno = Integer.toString(rs.getInt("deptno"));
				String dname = rs.getString("dname");
				String loc = rs.getString("loc");
				
				data[index][0]=deptno;
				data[index][1]=dname;
				data[index][2]=loc;
				
				index++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//���� ��ü �̿ܿ� ��ü���� �ݳ�!!
			connectionManager.closeDB(con,pstmt,rs);
		}
	}
	
	//���� ������ ��ȯ���ִ� �޼���
	@Override
	public int getRowCount() {
		//System.out.println("JTable�� ���� getRowCount() ȣ���");
		return data.length; //���� ������ �迭�� ����
	}

	//���� ������ ��ȯ���ִ� �޼���
	@Override
	public int getColumnCount() {
		//System.out.println("JTable�� ���� getColumnCount() ȣ���");
		return data[0].length; 
	}
	

	//�ش� ��� ���� ����ִ� �����͸� ��ȯ�ϴ� �޼���
	@Override
	public Object getValueAt(int row, int col) { //ĭ����ŭ ȣ��
		System.out.println("JTable�� ���� getValueAt("+row+","+col+") ȣ���");
		return data[row][col];
	}
	
}
