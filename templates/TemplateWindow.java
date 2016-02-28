package com.workforce.templates;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

public class TemplateWindow extends JFrame
{
	FileWriter fw;
	File err = new File("error.txt");
	
	public TemplateWindow()
	{
		prepare();
	}
	
	public void prepare()
	{
		try {fw = new FileWriter(err);} 
		catch (IOException e2) {e2.printStackTrace();}
		
		this.setTitle("Template Window");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(400, 400);
		this.setResizable(false);
		Container contentPane = this.getContentPane();
		
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		
//		//Username Label at (70, 120)
//		layout.putConstraint(SpringLayout.WEST, uNameL, 70, SpringLayout.WEST, contentPane);
//		layout.putConstraint(SpringLayout.NORTH, uNameL, 120, SpringLayout.WEST, contentPane);
//		
//		//Username TextField at (uNameL.x + 15, uNameL.y)
//		layout.putConstraint(SpringLayout.WEST, uNameTB, 15, SpringLayout.EAST, uNameL);
//		layout.putConstraint(SpringLayout.NORTH, uNameTB, 0, SpringLayout.NORTH, uNameL);
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				try {fw.close();} 
				catch (IOException e1) {e1.printStackTrace();}
				System.exit(0);
			}
		});
	}
}

