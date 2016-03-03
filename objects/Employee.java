package com.workforce.objects;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Employee
{
	String host = "jdbc:mysql://localhost:3306/workforce?useSSL=false";
	
	String firstName, lastName, email, title, address, city, state, homePhone, workPhone;

	Date birth;
	int zip, id;
	
	FileWriter fw;
	File err = new File("error.txt");
	
	public Employee(int x, FileWriter f)
	{
		fw = f;
		try 
		{
			Connection con = DriverManager.getConnection(host, "root", "password1");
			Statement stmt = con.createStatement();
			String SQL = "SELECT * FROM employees"; //workforce (database) --> employees (table)
			ResultSet rs = stmt.executeQuery(SQL);
			
			int count;
			rs.next();
			for(count = 0; count < x; count++)
			{
				rs.next();
			}
			
			firstName = rs.getString("FirstName");
			lastName = rs.getString("LastName");
			email = rs.getString("Email");
			title = rs.getString("Title");
			address = rs.getString("Address");
			city = rs.getString("City");
			state = rs.getString("State");
			zip = rs.getInt("ZipCode");
			homePhone = rs.getString("H-Phone");
			workPhone = rs.getString("W-Phone");
			birth = rs.getDate("DateOfBirth");
			id = rs.getInt("id");
//			System.out.println(firstName + " " + lastName + " " + email + " " + title + " " + address + " " + city + " " + state + " " + zip);
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(new JPanel(), "Error - Problem with the Server");
			System.out.println(e.getMessage());
			
			java.util.Date date = new java.util.Date();
			try
			{
				fw.write(e.getMessage() + ": " + date); 
				fw.write(System.getProperty( "line.separator" ));
			} 
			catch (IOException er) {er.printStackTrace();}
		}				
	}
	
	public Employee(String f, String l, String e, String t, String a, String c, String s, String hp, String wp, Date b, int z)
	{
		firstName = f;
		lastName = l;
		email = e;
		title = t;
		address = a;
		city = c;
		state = s;
		zip = z;
		homePhone = hp;
		workPhone = wp;
		birth = b;
	}
	
	public Employee(String f, String l, String e, String t, String a, String c, String s, String hp, String wp, Date b, int z, int i)
	{
		firstName = f;
		lastName = l;
		email = e;
		title = t;
		address = a;
		city = c;
		state = s;
		zip = z;
		homePhone = hp;
		workPhone = wp;
		birth = b;
		id = i;
	}
	
	public String getFirstName()
	{
		return this.firstName;
	}
	
	public String getLastName()
	{
		return this.lastName;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public String getAddress()
	{
		return this.address;
	}
	
	public String getCity()
	{
		return this.city;
	}
	
	public String getState()
	{
		return this.state;
	}
	
	public String getHomePhone()
	{
		return this.homePhone;
	}
	
	public String getWorkPhone()
	{
		return this.workPhone;
	}
	
	public int getZipCode()
	{
		return this.zip;
	}
	
	public Date getBirth()
	{
		return this.birth;
	}
	
	public int getID()
	{
		return this.id;
	}

	public void addToDatabase() 
	{
		try
		{
			Connection con = DriverManager.getConnection(host, "root", "password1");
			
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String SQL = "SELECT * FROM employees"; //workforce (database) --> employees (table)
			ResultSet rs = stmt.executeQuery(SQL);
			
			rs.moveToInsertRow();
			
			rs.updateString("FirstName", this.getFirstName());
			rs.updateString("LastName", this.getLastName());
			rs.updateString("Email", this.getEmail());
			rs.updateString("Title", this.getTitle());
			rs.updateString("Address", this.getAddress());
			rs.updateString("City", this.getCity());
			rs.updateString("State", this.getState());
			rs.updateInt("ZipCode", this.getZipCode());
			rs.updateDate("DateOfBirth", this.getBirth());
			rs.updateString("H-Phone", this.getHomePhone());
			rs.updateString("W-Phone", this.getWorkPhone());

			rs.insertRow();
			id = rs.getInt("id");
			JOptionPane.showMessageDialog(new JPanel(), "Success: Added Employee to Database");
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(new JPanel(), "Error - Problem with the Server");
			System.out.println(e.getMessage());
		}
	}
	
	public void changeDetails()
	{
		try
		{
			Connection con = DriverManager.getConnection(host, "root", "password1");
			
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String SQL = "SELECT * FROM employees"; //workforce (database) --> employees (table)
			ResultSet rs = stmt.executeQuery(SQL);
			
			while(rs.next())
			{
				if(rs.getInt("id") == this.getID())
				{
					rs.updateString("FirstName", this.getFirstName());
					rs.updateString("LastName", this.getLastName());
					rs.updateString("Email", this.getEmail());
					rs.updateString("Title", this.getTitle());
					rs.updateString("Address", this.getAddress());
					rs.updateString("City", this.getCity());
					rs.updateString("State", this.getState());
					rs.updateInt("ZipCode", this.getZipCode());
					rs.updateDate("DateOfBirth", this.getBirth());
					rs.updateString("H-Phone", this.getHomePhone());
					rs.updateString("W-Phone", this.getWorkPhone());
					rs.updateRow();
					
					JOptionPane.showMessageDialog(new JPanel(), "Success - Updated Employee");
					return;
				}
			}
			
			JOptionPane.showMessageDialog(new JPanel(), "Failure - Employee Not Found");
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(new JPanel(), "Error - Problem with the Server");
			System.out.println(e.getMessage());
		}
	}
}
