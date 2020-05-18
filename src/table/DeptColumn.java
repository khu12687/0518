package table;

import javax.swing.table.DefaultTableColumnModel;

public class DeptColumn extends DefaultTableColumnModel{
	
	String[] column= {"deptno","dname","loc"};
	
	//컬럼의 갯수
	@Override
	public int getColumnCount() {
		return column.length;
	}
	
	//각 컬럼의 제목
	@Override
	public String getColumnName(int col) {
		return column[col];
	}
}
