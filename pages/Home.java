package com.workforce.pages;

import java.awt.Color;
import java.awt.Container;
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
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class Home extends JFrame{
	
	String host = "jdbc:mysql://localhost:3306/workforce?useSSL=false", uName, uPass;
	JLabel uNameL, uPassL, authorizedL;
	JTextField uNameTB;
	JPasswordField uPassTB;
	JButton enterB;
	FileWriter fw;
	File err = new File("error.txt");
	
	public Home()
	{
		prepare();
	}
	
	public static void main(String[] args) 
	{
		Home homepage = new Home();
	}
	
	private void prepare()
	{
		//frame setup
		this.setTitle("Worforce Manager Login");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(400, 400);
		this.setResizable(false);
		Container contentPane = this.getContentPane();
		
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		
		uNameL = new JLabel("Username: ");
		uNameL.setVisible(false);
		contentPane.add(uNameL);

		uNameTB = new JTextField("", 15);
		uNameTB.setVisible(false);
		contentPane.add(uNameTB);
		
		uPassL = new JLabel("Password: ");
		uPassL.setVisible(false);
		contentPane.add(uPassL);
		
		uPassTB = new JPasswordField("", 15);
		uPassTB.setVisible(false);
		contentPane.add(uPassTB);
		
		enterB = new JButton("Sign In");
		enterB.setVisible(false);
		contentPane.add(enterB);
		
		authorizedL = new JLabel("Testing Purposes");
		authorizedL.setForeground(Color.red);
		authorizedL.setVisible(false);
		contentPane.add(authorizedL);
		
		//Username Label at (70, 120)
		layout.putConstraint(SpringLayout.WEST, uNameL, 70, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, uNameL, 120, SpringLayout.WEST, contentPane);
		
		//Username TextField at (uNameL.x + 15, uNameL.y)
		layout.putConstraint(SpringLayout.WEST, uNameTB, 15, SpringLayout.EAST, uNameL);
		layout.putConstraint(SpringLayout.NORTH, uNameTB, 0, SpringLayout.NORTH, uNameL);
		
		//Password Label at (uNameL.x, uNameL.y + 25)
		layout.putConstraint(SpringLayout.WEST, uPassL, 0, SpringLayout.WEST, uNameL);
		layout.putConstraint(SpringLayout.NORTH, uPassL, 25, SpringLayout.NORTH, uNameL);
		
		//Password Textbook at (uNameTB.x, uPassL.y)
		layout.putConstraint(SpringLayout.WEST, uPassTB, 0, SpringLayout.WEST, uNameTB);
		layout.putConstraint(SpringLayout.NORTH, uPassTB, 0, SpringLayout.NORTH, uPassL);
		
		//Sign In Button at (uPassL.x, uPassL.y + 20)
		layout.putConstraint(SpringLayout.WEST, enterB, 0, SpringLayout.WEST, uPassL);
		layout.putConstraint(SpringLayout.NORTH, enterB, 20, SpringLayout.SOUTH, uPassL);
		
		//Authorized Label at (uPassTB.x + 35, enterB.y + 5)
		layout.putConstraint(SpringLayout.WEST, authorizedL, 35, SpringLayout.WEST, uPassTB);
		layout.putConstraint(SpringLayout.NORTH, authorizedL, 5, SpringLayout.NORTH, enterB);
		
		uNameL.setVisible(true);
		uNameTB.setVisible(true);
		uPassL.setVisible(true);
		uPassTB.setVisible(true);
		enterB.setVisible(true);
		
		try 
		{
			fw = new FileWriter(err);
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		
		enterB.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	actionListen();
            }
        });
		
		uPassTB.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				actionListen();
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
	
	public void doConnect()
	{
		try
		{		
			Connection con = DriverManager.getConnection(host, "root", "password1");
			
			Statement stmt = con.createStatement();
			String SQL = "SELECT * FROM login"; //workforce (database) --> login (table)
			ResultSet rs = stmt.executeQuery(SQL);
			
			String u, p;
			
			while(rs.next())
			{
				u = rs.getString("username");
				p = rs.getString("password");
				if(uName.equals(u) && uPass.equals(p))
				{
					EmployeeList page = new EmployeeList();
					this.setVisible(false);
					return;
				}
			}
			
			JOptionPane.showMessageDialog(rootPane, "Access Denied - Invalid Username and Password");
			
			this.authorizedL.setText("Access Denied");
			this.authorizedL.setVisible(true);
			
		}
		catch(SQLException err)
		{
			JOptionPane.showMessageDialog(rootPane, "Access Denied - Problem with the Server");
			System.out.println(err.getMessage());
			
			Date date = new Date();
			try {fw.write(err.getMessage() + ": " + date); fw.write(System.getProperty( "line.separator" ));} 
			catch (IOException e) {e.printStackTrace();}
			
			this.authorizedL.setText("Access Denied");
			this.authorizedL.setVisible(true);
		}
	}
	
	private void actionListen()
	{
		uName = uNameTB.getText();
    	uPass = uPassTB.getText();
        doConnect();
	}

}
