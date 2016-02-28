package com.workforce.pages;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.workforce.objects.Employee;

public class AddEmployee extends JFrame
{
	FileWriter fw;
	File err = new File("error.txt");
	
	JTextField firstNameTF, lastNameTF, emailTF, titleTF, addressTF, cityTF;
	JFormattedTextField yearTF, dateTF, zipTF, homePhoneTF, workPhoneTF;
	JComboBox stateCB, monthCB;
	
	static final String[] states = {"AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA","GU","HI","IA","ID", "IL","IN","KS","KY","LA","MA","MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY", "OH","OK","OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA","WI","WV","WY"};
	static final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
	public AddEmployee()
	{
		prepare();
	}
	
	public void prepare()
	{
		try {fw = new FileWriter(err);} 
		catch (IOException e2) {e2.printStackTrace();}
		
		this.setTitle("Add Employee to Database");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(500, 300);
		this.setResizable(false);
		Container contentPane = this.getContentPane();
		
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		
		JLabel headerLabel = new JLabel("<HTML><U>Add Employee:</U></HTML>");
		headerLabel.setFont(headerLabel.getFont().deriveFont(14.0f));
		JLabel firstNameL = new JLabel("First Name: ");
		JLabel lastNameL = new JLabel("Last Name: ");
		JLabel emailL = new JLabel("Email: ");
		JLabel titleL = new JLabel("Title: ");
		JLabel addressL = new JLabel("Address: ");
		JLabel cityL = new JLabel("City: ");
		JLabel stateL = new JLabel("State: ");
		JLabel zipL = new JLabel("Zip Code: ");
		JLabel homePhoneL = new JLabel("Home Phone #: ");
		JLabel workPhoneL = new JLabel("Work Phone #: ");
		JLabel birthL = new JLabel("Date of Birth: ");
		JButton addB = new JButton("Add Employee");
		JButton cancelB = new JButton("Cancel Addition");
		
		firstNameTF = new JTextField();
		firstNameTF.setColumns(10);
		lastNameTF = new JTextField();
		lastNameTF.setColumns(10);
		emailTF = new JTextField();
		emailTF.setColumns(15);
		titleTF = new JTextField();
		titleTF.setColumns(14);
		addressTF = new JTextField();
		addressTF.setColumns(30);
		cityTF = new JTextField();
		cityTF.setColumns(10);
		
		stateCB = new JComboBox(states);
		final int width1 = 50, width2 = 100;
		final int height1 = 20;
		stateCB.setMaximumSize(new Dimension(width1, height1));
		stateCB.setMinimumSize(new Dimension(width1, height1));
		stateCB.setPreferredSize(new Dimension(width1, height1));
		stateCB.setSize(new Dimension(width1, height1));
		monthCB = new JComboBox(months);
		monthCB.setMaximumSize(new Dimension(width2, height1));
		monthCB.setMinimumSize(new Dimension(width2, height1));
		monthCB.setPreferredSize(new Dimension(width2, height1));
		monthCB.setSize(new Dimension(width2, height1));
		
		homePhoneTF = new JFormattedTextField();
		homePhoneTF.setColumns(10);
		
		workPhoneTF = new JFormattedTextField();
		workPhoneTF.setColumns(10);
		
		NumberFormat yearDecimalFormat = NumberFormat.getNumberInstance();
		yearDecimalFormat.setGroupingUsed(false);
		yearDecimalFormat.setMinimumIntegerDigits(4);
		yearDecimalFormat.setMaximumIntegerDigits(4);
		
		yearTF = new JFormattedTextField(yearDecimalFormat);
		yearTF.setColumns(4);
		
		NumberFormat dateDecimalFormat = NumberFormat.getNumberInstance();
		dateDecimalFormat.setGroupingUsed(false);
		dateDecimalFormat.setMinimumIntegerDigits(2);
		dateDecimalFormat.setMaximumIntegerDigits(2);
		
		dateTF = new JFormattedTextField(dateDecimalFormat);
		dateTF.setColumns(2); //maximum int = 31
		
		NumberFormat decimalFormat = NumberFormat.getNumberInstance();
		decimalFormat.setGroupingUsed(false);
		decimalFormat.setMinimumIntegerDigits(5);
		decimalFormat.setMaximumIntegerDigits(5);
		
		zipTF = new JFormattedTextField(decimalFormat);
		zipTF.setColumns(5);
		
		contentPane.add(headerLabel);
		contentPane.add(firstNameL);
		contentPane.add(lastNameL);
		contentPane.add(emailL);
		contentPane.add(titleL);
		contentPane.add(addressL);
		contentPane.add(cityL);
		contentPane.add(zipL);
		contentPane.add(homePhoneL);
		contentPane.add(workPhoneL);
		contentPane.add(stateL);
		contentPane.add(birthL);
//		
		contentPane.add(firstNameTF);
		contentPane.add(lastNameTF);
		contentPane.add(emailTF);
		contentPane.add(titleTF);
		contentPane.add(addressTF);
		contentPane.add(cityTF);
		contentPane.add(zipTF);
		contentPane.add(homePhoneTF);
		contentPane.add(workPhoneTF);
		contentPane.add(yearTF);
		contentPane.add(dateTF);
		contentPane.add(stateCB);
		contentPane.add(monthCB);
		
		contentPane.add(addB);
		contentPane.add(cancelB);
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headerLabel, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
		layout.putConstraint(SpringLayout.NORTH, headerLabel, 10, SpringLayout.NORTH, contentPane);
		
		layout.putConstraint(SpringLayout.WEST, firstNameL, 25, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, firstNameL, 25, SpringLayout.NORTH, headerLabel);
		
		layout.putConstraint(SpringLayout.WEST, firstNameTF, 5, SpringLayout.EAST, firstNameL);
		layout.putConstraint(SpringLayout.NORTH, firstNameTF, 0, SpringLayout.NORTH, firstNameL);
		
		layout.putConstraint(SpringLayout.WEST, lastNameL, 25, SpringLayout.EAST, firstNameTF);
		layout.putConstraint(SpringLayout.NORTH, lastNameL, 0, SpringLayout.NORTH, firstNameL);
		
		layout.putConstraint(SpringLayout.WEST, lastNameTF, 5, SpringLayout.EAST, lastNameL);
		layout.putConstraint(SpringLayout.NORTH, lastNameTF, 0, SpringLayout.NORTH, lastNameL);	
		
		layout.putConstraint(SpringLayout.WEST, emailL, 0, SpringLayout.WEST, firstNameL);
		layout.putConstraint(SpringLayout.NORTH, emailL, 25, SpringLayout.NORTH, firstNameL);
		
		layout.putConstraint(SpringLayout.WEST, emailTF, 5, SpringLayout.EAST, emailL);
		layout.putConstraint(SpringLayout.NORTH, emailTF, 0, SpringLayout.NORTH, emailL);
		
		layout.putConstraint(SpringLayout.WEST, titleL, 25, SpringLayout.EAST, emailTF);
		layout.putConstraint(SpringLayout.NORTH, titleL, 0, SpringLayout.NORTH, emailL);
		
		layout.putConstraint(SpringLayout.WEST, titleTF, 5, SpringLayout.EAST, titleL);
		layout.putConstraint(SpringLayout.NORTH, titleTF, 0, SpringLayout.NORTH, titleL);
		
		layout.putConstraint(SpringLayout.WEST, addressL, 0, SpringLayout.WEST, emailL);
		layout.putConstraint(SpringLayout.NORTH, addressL, 25, SpringLayout.NORTH, emailL);
		
		layout.putConstraint(SpringLayout.WEST, addressTF, 5, SpringLayout.EAST, addressL);
		layout.putConstraint(SpringLayout.NORTH, addressTF, 0, SpringLayout.NORTH, addressL);
		
		layout.putConstraint(SpringLayout.WEST, cityL, 0, SpringLayout.WEST, addressL);
		layout.putConstraint(SpringLayout.NORTH, cityL, 25, SpringLayout.NORTH, addressL);
		
		layout.putConstraint(SpringLayout.WEST, cityTF, 5, SpringLayout.EAST, cityL);
		layout.putConstraint(SpringLayout.NORTH, cityTF, 0, SpringLayout.NORTH, cityL);
		
		layout.putConstraint(SpringLayout.WEST, stateL, 25, SpringLayout.EAST, cityTF);
		layout.putConstraint(SpringLayout.NORTH, stateL, 0, SpringLayout.NORTH, cityTF);
		
		layout.putConstraint(SpringLayout.WEST, stateCB, 5, SpringLayout.EAST, stateL);
		layout.putConstraint(SpringLayout.NORTH, stateCB, 0, SpringLayout.NORTH, stateL);
		
		layout.putConstraint(SpringLayout.WEST, zipL, 25, SpringLayout.EAST, stateCB);
		layout.putConstraint(SpringLayout.NORTH, zipL, 0, SpringLayout.NORTH, stateCB);
		
		layout.putConstraint(SpringLayout.WEST, zipTF, 5, SpringLayout.EAST, zipL);
		layout.putConstraint(SpringLayout.NORTH, zipTF, 0, SpringLayout.NORTH, zipL);
		
		layout.putConstraint(SpringLayout.WEST, birthL, 0, SpringLayout.WEST, cityL);
		layout.putConstraint(SpringLayout.NORTH, birthL, 25, SpringLayout.NORTH, cityL);
		
		layout.putConstraint(SpringLayout.WEST, monthCB, 5, SpringLayout.EAST, birthL);
		layout.putConstraint(SpringLayout.NORTH, monthCB, 0, SpringLayout.NORTH, birthL);
		
		layout.putConstraint(SpringLayout.WEST, dateTF, 10, SpringLayout.EAST, monthCB);
		layout.putConstraint(SpringLayout.NORTH, dateTF, 0, SpringLayout.NORTH, monthCB);

		layout.putConstraint(SpringLayout.WEST, yearTF, 10, SpringLayout.EAST, dateTF);
		layout.putConstraint(SpringLayout.NORTH, yearTF, 0, SpringLayout.NORTH, dateTF);
		
		layout.putConstraint(SpringLayout.WEST, homePhoneL, 0, SpringLayout.WEST, birthL);
		layout.putConstraint(SpringLayout.NORTH, homePhoneL, 25, SpringLayout.NORTH, birthL);
		
		layout.putConstraint(SpringLayout.WEST, homePhoneTF, 5, SpringLayout.EAST, homePhoneL);
		layout.putConstraint(SpringLayout.NORTH, homePhoneTF, 0, SpringLayout.NORTH, homePhoneL);
		
		layout.putConstraint(SpringLayout.WEST, workPhoneL, 25, SpringLayout.EAST, homePhoneTF);
		layout.putConstraint(SpringLayout.NORTH, workPhoneL, 0, SpringLayout.NORTH, homePhoneL);
		
		layout.putConstraint(SpringLayout.WEST, workPhoneTF, 5, SpringLayout.EAST, workPhoneL);
		layout.putConstraint(SpringLayout.NORTH, workPhoneTF, 0, SpringLayout.NORTH, workPhoneL);
		
		layout.putConstraint(SpringLayout.WEST, addB, 0, SpringLayout.WEST, homePhoneL);
		layout.putConstraint(SpringLayout.NORTH, addB, 50, SpringLayout.NORTH, homePhoneL);
		
		layout.putConstraint(SpringLayout.WEST, cancelB, -30, SpringLayout.WEST, zipTF);
		layout.putConstraint(SpringLayout.NORTH, cancelB, 0, SpringLayout.NORTH, addB);
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				try {fw.close();} 
				catch (IOException e1) {e1.printStackTrace();}
				System.exit(0);
			}
		});
		
		addB.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	actionListen();
            }
        });
		
		cancelB.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	cancelListen();
            }
        });
	}
	
	private void actionListen()
	{
		if(Integer.parseInt(dateTF.getText()) > 31)
		{
			JOptionPane.showMessageDialog(rootPane, "Error: Date is larger than 31");
			return;
		}
		
		StringBuilder homeTF = new StringBuilder(homePhoneTF.getText());
		StringBuilder workTF = new StringBuilder(workPhoneTF.getText());
		if(!validatePhoneNumber(homeTF) || !validatePhoneNumber(workTF))
		{
			JOptionPane.showMessageDialog(rootPane, "Error: Phone Number Format not Accepted.\nPossible Formats:\n5555555555\n555-555-5555\n555.555.5555\n555 555 5555\n(555)-555-5555");
			return;
		}

		Date birthDate = new Date(Integer.parseInt(yearTF.getText())-1900, monthCB.getSelectedIndex(), Integer.parseInt(dateTF.getText()));
		Employee newEmployee = new Employee(firstNameTF.getText(), lastNameTF.getText(), emailTF.getText(), titleTF.getText(), addressTF.getText(), cityTF.getText(), stateCB.getSelectedItem().toString(), homeTF.toString(), workTF.toString(), birthDate, Integer.parseInt(zipTF.getText()));
		
		newEmployee.addToDatabase();
		
		cancelListen();
	}
	
	private void cancelListen()
	{
		EmployeeList el = new EmployeeList();
		this.setVisible(false);
	}
	private static boolean validatePhoneNumber(StringBuilder pNo) 
	{
		String phoneNo = pNo.toString();
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) 
    	{
    		return true;
    	}
        //validating phone number with -, . or spaces
        else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
    	{
        	pNo.delete(0,  pNo.toString().length()).append(phoneNo.substring(0, 3) + phoneNo.substring(4, 7) + phoneNo.substring(8));
        	return true;
    	}
//        //validating phone number with extension length from 3 to 5
//        else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) 
//        {
//        	return true;
//        }
        //validating phone number where area code is in braces ()
        else if(phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
    	{
        	pNo.delete(0,  pNo.toString().length()).append(phoneNo.substring(1, 4) + phoneNo.substring(6, 9) + phoneNo.substring(10));
        	return true;
    	}
        //return false if nothing matches the input
        else return false; 
    }
}
