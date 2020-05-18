package table;

import javax.swing.table.DefaultTableColumnModel;

public class DeptColumn extends DefaultTableColumnModel{
	
	String[] column= {"deptno","dname","loc"};
	
	//�÷��� ����
	@Override
	public int getColumnCount() {
		return column.length;
	}
	
	//�� �÷��� ����
	@Override
	public String getColumnName(int col) {
		return column[col];
	}
}
