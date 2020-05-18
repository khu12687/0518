package table;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/*
 * JTable���� ���� ���� ���� �ִ� ���̺��� ��ü�Ҷ��� ���� ȿ�������� �ڵ带 �����Ϸ���
 * ��� �ϴ°�??
 * �� ������ �����Ͱ� �ٸ���, ���� ���� �ҽ��� ������ �ϴ°�??
 * ���) �����Ͱ� �ٲ�ٰ� �Ͽ� ���� �ڵ带 �����ٸ� ��û�� ����� �������� �ð��� �սǵ� ũ��!!
 * �ذ�å) �����Ϳ� �������� ö���� �и����Ѽ� �����϶�!!
 * 		  �� ��Ģ�� ������ ���߹���� ������ MVC�����̶� �Ѵ�!!
 * ����) JTable�� MVC������ ������� �ݿ��� ������Ʈ!!
 * �ǽ�) ������ �ڵ忡�� emp, dept, student �� �����͸� ��ü�� ������ Ŭ������ ū ������ ���� �ʴ� �ڵ� �����
 * 		����������Ѵ�!!!
 * 
 * */
public class MVCApp extends JFrame{
	JTable table;
	DeptModel model;
	JScrollPane scroll;
	public MVCApp() {
		//TableModel �̶� ��ü�� JTable �� ������� �� �����Ϳ�,
		//Table �������� �и������ִ� ������ ����!!
		//����)��Ī�� ���� ������, ���ҷ� ���ٸ� Controller�̴�..by zino
		
		table = new JTable(model = new DeptModel(), new DeptColumn());
		setLayout(new FlowLayout());
		scroll = new JScrollPane(table);
		add(table);
		setSize(600,400);
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeDB();
				System.exit(0);
			}
		});
	}
	public void closeDB() {
		
	}
	public static void main(String[] args) {
		new MVCApp();
	}
}
