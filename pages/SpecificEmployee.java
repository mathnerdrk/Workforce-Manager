package com.workforce.pages;

import java.awt.Container;
import java.awt.Font;
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
		
		this.setTitle("Worforce Manager Employee Details");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(500, 300);
		this.setResizable(false);
		Container contentPane = this.getContentPane();
		
		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		
		labels.add(new JLabel("<HTML><B>Name:</B> " + em.getLastName() + ", " + em.getFirstName() + "</HTML>"));
		labels.add(new JLabel("<HTML><B>Email:</B> " + em.getEmail() + "</HTML>"));
		labels.add(new JLabel("<HTML><B>Job Title:</B> " + em.getTitle() + "</HTML>"));
		labels.add(new JLabel("<HTML><B>Street Address:</B> " + em.getAddress() + "</HTML>"));
		labels.add(new JLabel("<HTML><B>City:</B> " + em.getCity() + "</HTML>"));
		labels.add(new JLabel("<HTML><B>State:</B> " + em.getState()));
		labels.add(new JLabel("<HTML><B>Date of Birth:</B> " + em.getBirth() + "</HTML>"));
		labels.add(new JLabel("<HTML><B>Zip Code:</B> " + em.getZipCode() + "</HTML>"));
		labels.add(new JLabel("<HTML><B>Home Phone #:</B> " + em.getHomePhone() + "</HTML>"));
		labels.add(new JLabel("<HTML><B>Work Phone #:</B> " + em.getWorkPhone() + "</HTML>"));
		JButton backB = new JButton("Back to List");
		JButton deleteB = new JButton("Delete Employee");
		JButton editB = new JButton("Edit Employee");
		
		for(JLabel a: labels)
		{
			a.setFont(a.getFont().deriveFont(Font.PLAIN));
		}

		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		
		JPanel pan1 = new JPanel();
		JPanel pan2 = new JPanel();
		
		GridLayout pan1Layout = new GridLayout(5, 2);
		pan1Layout.setVgap(10);
		pan1Layout.setHgap(50);
		pan1.setLayout(pan1Layout);
		GridLayout pan2Layout = new GridLayout(1, 3);
		pan2Layout.setHgap(30);
		pan2.setLayout(pan2Layout);
		
		for(int i = 0; i < labels.size(); i++)
		{
			pan1.add(labels.get(i));
		}
		pan2.add(deleteB);
		pan2.add(editB);
		pan2.add(backB);
		
		contentPane.add(pan1);
		contentPane.add(pan2);
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, pan1, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
		layout.putConstraint(SpringLayout.NORTH, pan1, 30, SpringLayout.NORTH, contentPane);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, pan2, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
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
		
		editB.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	editActionListen();
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
		EmployeeList el = new EmployeeList();
		this.setVisible(false);
	}
	
	private void editActionListen()
	{
		this.setVisible(false);
		EditEmployee ee = new EditEmployee(em);
	}
	
	private void deleteActionListen()
	{
		int result = JOptionPane.showConfirmDialog(this, "Warning: Are you sure you want to delete this employee?");
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
