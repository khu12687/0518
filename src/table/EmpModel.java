package table;

import javax.swing.table.AbstractTableModel;

public class EmpModel extends AbstractTableModel{

	@Override
	public int getRowCount() {
		return 5;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return null;
	}

}
