package com.workforce.pages;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.SpringLayout;

import com.workforce.objects.Employee;

public class SpecificEmployee extends JFrame
{
	String host = "jdbc:mysql://localhost:3306/workforce?useSSL=false";
	
	Employee em;
	EmployeeList window;
	
	FileWriter fw;
	File err = new File("error.txt");
	
	public SpecificEmployee(Employee e, EmployeeList l)
	{
		em = e;
		window = l;
		prepare();
	}
	
	public void prepare()
	{
		try {fw = new FileWriter(err);} 
		catch (IOException e2) {e2.printStackTrace();}
		
		this.setTitle("Worforce Manager Single Employee Details");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(450, 250);
		this.setResizable(false);
		Container contentPane = this.getContentPane();
		
		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		
		labels.add(new JLabel("Name: " + em.getLastName() + ", " + em.getFirstName()));
		labels.add(new JLabel("Email: " + em.getEmail()));
		labels.add(new JLabel("Job Title: " + em.getTitle()));
		labels.add(new JLabel("Street Address: " + em.getAddress()));
		labels.add(new JLabel("City: " + em.getCity()));
		labels.add(new JLabel("State: " + em.getState()));
		labels.add(new JLabel("Date of Birth: " + em.getBirth()));
		labels.add(new JLabel("Zip Code: " + em.getZipCode()));
		labels.add(new JLabel("Home Phone #: " + em.getHomePhone()));
		labels.add(new JLabel("Work Phone #: " + em.getWorkPhone()));
		JButton backB = new JButton("Close");
		JButton deleteB = new JButton("Delete Employee");

		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		
		JPanel pan1 = new JPanel();
		JPanel pan2 = new JPanel();
		
		GridLayout pan1Layout = new GridLayout(5, 2);
		pan1Layout.setVgap(10);
		pan1Layout.setHgap(0);
		pan1.setLayout(pan1Layout);
		GridLayout pan2Layout = new GridLayout(1, 4);
		pan2.setLayout(pan2Layout);
		
		for(int i = 0; i < labels.size(); i++)
		{
			pan1.add(labels.get(i));
		}
		
		pan2.add(backB);
		pan2.add(deleteB);
		
		contentPane.add(pan1);
		contentPane.add(pan2);
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, pan1, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
		layout.putConstraint(SpringLayout.NORTH, pan1, 30, SpringLayout.NORTH, contentPane);

		layout.putConstraint(SpringLayout.WEST, pan2, 0, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, pan2, 185, SpringLayout.NORTH, contentPane);
		
		backB.addActionListener(new ActionListener() {
			 
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
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				try {fw.close();} 
				catch (IOException e1) {e1.printStackTrace();}
				System.exit(0);
			}
		});
		
	}
	
	private void actionListen()
	{
		this.setVisible(false);
	}
	
	private void deleteActionListen()
	{
		int result = JOptionPane.showConfirmDialog(this, "Warning: Are you sure you want\n to delete this employee?");
		if(result == JOptionPane.YES_OPTION)
		{
			try
			{
				
				Connection con = DriverManager.getConnection(host, "root", "password1");
				
				Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String SQL = "SELECT * FROM employees"; //workforce (database) --> employees (table)
				ResultSet rs = stmt.executeQuery(SQL);
				
				while(rs.next())
				{
					if(rs.getInt("id") == em.getID())
					{
						rs.deleteRow();
						
						stmt.close();
						rs.close();
						
						JOptionPane.showMessageDialog(this, "Success: Employee Deleted");
						actionListen();
						return;
					}
				}
				JOptionPane.showMessageDialog(this, "Failure: Employee Not Found");
				actionListen();
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
	
}
