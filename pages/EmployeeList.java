package com.workforce.pages;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.workforce.objects.Employee;

public class EmployeeList extends JFrame 
{
	String host = "jdbc:mysql://localhost:3306/workforce?useSSL=false";
	String[][] data;
	String[] columns;
	FileWriter fw;
	File err = new File("error.txt");
	int width = 600, height = 600;
	
	JTable table;
	
	public EmployeeList()
	{
		prepare();
	}
	
	public void prepare()
	{
		try {fw = new FileWriter(err);} 
		catch (IOException e2) {e2.printStackTrace();}
		
		this.setTitle("Worforce Manager Employee List");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(width, height);
		this.setResizable(false);
		Container contentPane = this.getContentPane();
		contentPane.setBackground(Color.white);
		
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		
		doConnect();
		
		JPanel headerPan = new JPanel();
		headerPan.setBackground(Color.white);
		JLabel header = new JLabel("Employee List");
		header.setFont(header.getFont().deriveFont(18.0f));
		header.setVisible(false);
		headerPan.add(header);
		contentPane.add(headerPan);
		
		JPanel tablePan = new JPanel();
		tablePan.setBackground(Color.white);
		table = new JTable(data, columns);
		table.setModel(new MyModel(data, columns));
		table.setPreferredScrollableViewportSize(new Dimension(width-76, this.height-180));
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setVisible(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TableColumnModel tcm = table.getColumnModel();
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);
		setColumnWidth(tcm);
		tablePan.add(table);
		JScrollPane inputPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		inputPane.setAlignmentX(LEFT_ALIGNMENT);
		tablePan.add(inputPane);
		contentPane.add(tablePan);
		
		JPanel buttonPan = new JPanel();
		buttonPan.setBackground(Color.white);
		JButton addB = new JButton("Add Employee");
		JButton deleteB = new JButton("Delete Selection");
		JButton editB = new JButton("Edit Selection");
		JButton exitB = new JButton("Exit Program");
		
		buttonPan.add(addB);
		buttonPan.add(deleteB);
		buttonPan.add(editB);
		buttonPan.add(exitB);
		contentPane.add(buttonPan);
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headerPan, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
		layout.putConstraint(SpringLayout.NORTH, headerPan, 0, SpringLayout.NORTH, contentPane);

		layout.putConstraint(SpringLayout.WEST, tablePan, 30, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, tablePan, 30, SpringLayout.NORTH, contentPane);
		
		layout.putConstraint(SpringLayout.WEST, buttonPan, 30, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, buttonPan, 40, SpringLayout.SOUTH, tablePan);
		
		header.setVisible(true);
		table.setVisible(true);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					int col = target.getSelectedColumn();
					createEmployee(row);
				}
			}
		});
		
		addB.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	actionListen();
            }
        });
		
		deleteB.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	deleteActionListen();
            }
        });
		
		editB.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	editActionListen();
            }
        });
		
//		refreshB.addActionListener(new ActionListener() {
//			 
//            public void actionPerformed(ActionEvent e)
//            {
//            	refreshActionListen();
//            }
//        });
		
		exitB.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	exitActionListen();
            }
        });
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				try {fw.close();} 
				catch (IOException e1) {e1.printStackTrace();}
				System.exit(0);
			}
		});
	}
	
	public void createEmployee(int row)
	{
		Employee em = new Employee(row, fw);
		SpecificEmployee window = new SpecificEmployee(em, this);
		this.setVisible(false);
	}
	
	public void doConnect()
	{
		try
		{		
			Connection con = DriverManager.getConnection(host, "root", "password1");
			
			Statement stmt = con.createStatement();
			String SQL = "SELECT * FROM employees"; //workforce (database) --> employees (table)
			ResultSet rs = stmt.executeQuery(SQL);

			ArrayList<ArrayList<String>> dataAL = new ArrayList<ArrayList<String>>();
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> username = new ArrayList<String>();
			ArrayList<String> email = new ArrayList<String>();
			ArrayList<String> title = new ArrayList<String>();
			
			String f, l, u;
			
			while(rs.next())
			{
				f = rs.getString("FirstName");
				l = rs.getString("LastName");
				if(l.length() > 7)
					u = f.toCharArray()[0] + l.substring(0, 7);
				else
					u = f.toCharArray()[0] + l;
				
				names.add(f + " " + l);
				email.add(rs.getString("Email"));
				username.add(u.toLowerCase());
				title.add(rs.getString("Title"));
			}
			
			dataAL.add(names);
			dataAL.add(username);
			dataAL.add(email);
			dataAL.add(title);
			
			data = new String[names.size()][dataAL.size()];
			int i = 0, j = 0;
			for(ArrayList<String> col : dataAL)
			{
				i = 0;
				for(String row : col)
				{
					data[i][j] = row;
					i++;
				}
				j++;
			}
			
			columns = new String[4];
			columns[0] = "Name";
			columns[1] = "Username";
			columns[2] = "Email";
			columns[3] = "Job Title";
			
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(rootPane, "Error - Problem with the Server");
			System.out.println(e.getMessage());
			
			Date date = new Date();
			
			try
			{
				fw.write(e.getMessage() + ": " + date); 
				fw.write(System.getProperty( "line.separator" ));
			} 
			catch (IOException er) {er.printStackTrace();}
		}
	}
	
	private void actionListen()
	{
		AddEmployee ae = new AddEmployee();
		this.setVisible(false);
	}
	
	private void refreshActionListen()
	{
		this.setVisible(false);
		EmployeeList em = new EmployeeList();
	}
	
	private void exitActionListen()
	{
		int result = JOptionPane.showConfirmDialog(this, "Warning: Are you sure you want to close the program?");
		if(result == JOptionPane.YES_OPTION)
		{
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
	}
	
	private void editActionListen()
	{
		this.setVisible(false);
		EditEmployee ee = new EditEmployee(new Employee(table.getSelectedRow(), fw));
	}
		
	private void deleteActionListen()
	{
		int result = JOptionPane.showConfirmDialog(this, "Warning: Are you sure you want to delete this employee?");
		if(result == JOptionPane.YES_OPTION)
		{
			int row = table.getSelectedRow();
			try
			{
				Connection con = DriverManager.getConnection(host, "root", "password1");
				
				Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String SQL = "SELECT * FROM employees"; //workforce (database) --> employees (table)
				ResultSet rs = stmt.executeQuery(SQL);
				
				rs.next();
				for(int i = 0; i < row; i++)
				{
					rs.next(); 
				}
				
				rs.deleteRow();
					
				stmt.close();
				rs.close();
				
				JOptionPane.showMessageDialog(this, "Success: Employee Deleted");
				refreshActionListen();
			}
			catch(SQLException e)
			{
				JOptionPane.showMessageDialog(rootPane, "Error - Problem with the Server");
				System.out.println(e.getMessage());
				
				Date date = new Date();
				
				try
				{
					fw.write(e.getMessage() + ": " + date); 
					fw.write(System.getProperty( "line.separator" ));
				} 
				catch (IOException er) {er.printStackTrace();}
			}
		}
	}
	
	private void setColumnWidth(TableColumnModel tcm)
	{
		//entire width = 600-60 = 540
		tcm.getColumn(0).setPreferredWidth(135); //135
		tcm.getColumn(1).setPreferredWidth(85); //220
		tcm.getColumn(2).setPreferredWidth(185); //405
		tcm.getColumn(3).setPreferredWidth(120); //540 +- 15
	}
}

class MyModel extends DefaultTableModel {

    MyModel(Object[][] data, String[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

