package forms;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.*;


import conn.dbCon;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import net.proteanit.sql.DbUtils;

@SuppressWarnings("serial")
public class patient extends JFrame implements ActionListener,MouseListener{
	public Connection conn = null;
	public Statement st = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	static JOptionPane j;
	String v = null, m = null, d = null;
	
	JTextArea ta;
	JButton astBtn, visBtn, medBtn, docBtn, hidBtn;
    JLabel patientname, patdp, lbla, lblb, lbltime, lblampm, lbldate;
	JScrollPane scrollPane;
	JTable table;
	
	Color bgColor = Color.decode("#34495e");
	Color mbColor = Color.decode("#16a085");
	Color yellow = Color.decode("#f1c40f");
	Color blue = Color.decode("#2980b9");
	Color green = Color.decode("#2ecc71");
	Color orange = Color.decode("#e67e22");
	Color red = Color.decode("#e74c3c");
	Color eblue = Color.decode("#0984e3");	
	Color pink = Color.decode("#e84393");
	Color gray = Color.decode("#7a7f80");
	
	Font patMenu = new Font("Sans Serif", Font.BOLD, 20);
	Font menufont = new Font("Sans Serif", Font.BOLD, 18);
	Font f9 = new Font("Sans Serif", Font.BOLD, 9);
	Font f12 = new Font("Sans Serif", Font.BOLD, 12);
	Font f15 = new Font("Sans Serif", Font.BOLD, 15);
	Font f20 = new Font("Sans Serif", Font.BOLD, 20);
	Font f25 = new Font("Sans Serif", Font.BOLD, 25);
	Font f30 = new Font("Sans Serif", Font.BOLD, 30);
	Font f35 = new Font("Sans Serif", Font.BOLD, 35);
	Font f40 = new Font("Sans Serif", Font.BOLD, 40);
	Font f45 = new Font("Sans Serif", Font.BOLD, 45);
	Font f60 = new Font("Sans Serif", Font.BOLD, 60);
	Font f80 = new Font("Sans Serif", Font.BOLD, 80);
	
	public void Clock() {
		Thread clock = new Thread() {
			public void run() {
				try {
					while (true) {
						Calendar cal = new GregorianCalendar();
						Calendar c = Calendar.getInstance();
						
						int day = cal.get(Calendar.DAY_OF_MONTH);
						int year = cal.get(Calendar.YEAR);
						int minute = cal.get(Calendar.MINUTE);
						int hour = cal.get(Calendar.HOUR);
						
						if (minute <= 9) {
							lbltime.setText(hour + ":0"+ minute);
						}
						else {
							lbltime.setText(hour + ":"+ minute + " ");
						}
						lblampm.setText(c.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.ENGLISH).toString());
						lbldate.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH).toString() + " / " + day + " / " + year );
						
						sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		clock.start();
	}
	public patient() {
		setTitle("Patient");
		setSize(1650, 1080);
		Container pane = getContentPane(); // get the container
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pane.setBackground(bgColor);
		
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon assist = IconFontSwing.buildIcon(FontAwesome.WHEELCHAIR, 50,Color.white);
		Icon medic = IconFontSwing.buildIcon(FontAwesome.CLIPBOARD, 50,Color.white);
		Icon doctor = IconFontSwing.buildIcon(FontAwesome.USER_MD, 50,Color.white);
		Icon visitor = IconFontSwing.buildIcon(FontAwesome.USERS, 50,Color.white);
		
		pane.add(ta = new JTextArea());
		ta.setBounds(10, 550, 880, 190);
		ta.setBorder(BorderFactory.createLineBorder(mbColor,5));
		ta.setFont(f20);
		
		pane.add(lbltime = new JLabel());
		lbltime.setBounds(1180,625,150,150);
		lbltime.setHorizontalTextPosition(SwingConstants.CENTER);
		lbltime.setForeground(Color.white);
		lbltime.setFont(f60);
		
		pane.add(lblampm = new JLabel());
		lblampm.setBounds(1300,672,80,80);
		lblampm.setHorizontalTextPosition(SwingConstants.CENTER);
		lblampm.setForeground(Color.white);
		lblampm.setFont(f25);
		
		pane.add(lbldate = new JLabel());
		lbldate.setBounds(1242,700,150,80);
		lbldate.setHorizontalTextPosition(SwingConstants.CENTER);
		lbldate.setForeground(Color.white);
		lbldate.setFont(f15);
		
		//patient
		pane.add(astBtn = new JButton("Ask for Assistance"));
		astBtn.setBounds(10, 10, 880,120);
		astBtn.setBackground(mbColor);
		astBtn.setFont(patMenu);
		astBtn.setIcon(assist);
		astBtn.setBackground(yellow);
		astBtn.setForeground(Color.white);
		astBtn.setFocusPainted(false);
		astBtn.addActionListener(this);
		astBtn.addMouseListener(this);
		astBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		astBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		
		pane.add(visBtn = new JButton("Visitor History"));
		visBtn.setBounds(10, 140, 880,120);
		visBtn.setBackground(mbColor);
		visBtn.setFont(patMenu);
		visBtn.setIcon(visitor);
		visBtn.setBackground(blue);
		visBtn.setForeground(Color.white);
		visBtn.setFocusPainted(false);
		visBtn.addActionListener(this);
		visBtn.addMouseListener(this);
		visBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		visBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		
		pane.add(medBtn = new JButton("Medications"));
		medBtn.setBounds(10, 270, 880,120);
		medBtn.setBackground(mbColor);
		medBtn.setFont(patMenu);
		medBtn.setIcon(medic);
		medBtn.setBackground(green);
		medBtn.setForeground(Color.white);
		medBtn.setFocusPainted(false);
		medBtn.addActionListener(this);
		medBtn.addMouseListener(this);
		medBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		medBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		
		pane.add(docBtn = new JButton("Doctor Assigned"));
		docBtn.setBounds(10, 400, 880,120);
		docBtn.setBackground(mbColor);
		docBtn.setFont(patMenu);
		docBtn.setIcon(doctor);
		docBtn.setBackground(orange);
		docBtn.setForeground(Color.white);
		docBtn.setFocusPainted(false);
		docBtn.addActionListener(this);
		docBtn.addMouseListener(this);
		docBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		docBtn.setHorizontalTextPosition(SwingConstants.CENTER);

		pane.add(patientname = new JLabel("Patient Name"));
		patientname.setBounds(900,100,450,40);
		patientname.setHorizontalAlignment(SwingConstants.CENTER);
		patientname.setForeground(Color.white);
		patientname.setFont(menufont);	
		
		pane.add(patdp = new JLabel("Image"));
		patdp.setBorder(BorderFactory.createLineBorder(Color.black));
		patdp.setBounds(1080,10,85,85);
		patdp.setForeground(Color.white);
		patdp.setFont(menufont);	
		
		pane.add(hidBtn = new JButton());
		hidBtn.setMnemonic(KeyEvent.VK_Z);
		hidBtn.addActionListener(this);
		hidBtn.setBounds(0,0,0,0);
		hidBtn.setBorder(null);
		
		pane.add(table = new JTable());
		table.setDefaultEditor(Object.class, null);
		table.addMouseListener(this);
		table.setAlignmentX(CENTER_ALIGNMENT);
		
		pane.add(scrollPane = new JScrollPane());
		scrollPane.setViewportView(table);
		scrollPane.setBounds(900,140,450,380);
		Clock();
	}
	public ImageIcon ResizeImage(String imgPath) {
		ImageIcon MyImage = new ImageIcon(imgPath);
		Image img = MyImage.getImage();
		Image newImage = img.getScaledInstance(patdp.getWidth(), patdp.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImage);
		return image;
	}
	public void visitor() {
		try {
			conn = dbCon.getConnection();
			String query = "SELECT VisitorName,`Time`,Relation,`Date`,Contact from visitorlog where patient = '" + patientname.getText() + "'";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void doctor()  {
		try {
			conn = dbCon.getConnection();
			String query = "SELECT Specialist,`Time`,`Date`,`Description`,Status from appointments where Patient = '" + patientname.getText() + "' and status = 'Pending'";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void medication()  {
		try {
			conn = dbCon.getConnection();
			String query = "SELECT Prescription,`Date`,Age from medications where PatientName = '" + patientname.getText() + "'";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == hidBtn) {
			this.dispose();
		}
		if(e.getSource() == visBtn) {
			v = "1";
			m = "0";
			d = "0";
			visitor();
		}
		if(e.getSource() == astBtn) {
			j.showMessageDialog(null, "This feature is dedicated to ALERT the Nurse if the patient needs assistance \n Due to the Community Quarantine, the Developers cannot test it using Two PC's. \n Hoping for your Consideration.", "Developer's Message", j.INFORMATION_MESSAGE);
		}
		if(e.getSource() == medBtn) {
			v = "0";
			m = "1";
			d = "0";
			medication();
		}
		if(e.getSource() == docBtn) {
			v = "0";
			m = "0";
			d = "1";
			doctor();
		}
	}
	public static void main(String[] args) {}
	@Override
	public void mouseEntered(MouseEvent me) {
		if(me.getSource() == astBtn) {
			astBtn.setBackground(Color.decode("#e2b500"));
		}
		if(me.getSource() == visBtn) {
			visBtn.setBackground(Color.decode("#246691"));
		}
		if(me.getSource() == medBtn) {
			medBtn.setBackground(Color.decode("#239754"));
		}
		if(me.getSource() == docBtn) {
			docBtn.setBackground(Color.decode("#bf681c"));
		}
	}
	@Override
	public void mouseExited(MouseEvent mex) {
		if(mex.getSource() == astBtn) {
			astBtn.setBackground(yellow);
		}
		if(mex.getSource() == visBtn) {
			visBtn.setBackground(blue);
		}
		if(mex.getSource() == medBtn) {
			medBtn.setBackground(green);
		}
		if(mex.getSource() == docBtn) {
			docBtn.setBackground(orange);
		}
		
	}

	//unused
	@Override
	public void mouseClicked(MouseEvent mc) {
		if (mc.getSource() == table) {
			//Visitor is Clicked
			if (v == "1" && m == "0" && d == "0") {
				int row = table.getSelectedRow();
				String visname = table.getModel().getValueAt(row, 0).toString();
				String time = table.getModel().getValueAt(row, 1).toString();
				String relation = table.getModel().getValueAt(row, 2).toString();
				String date = table.getModel().getValueAt(row, 3).toString();
				
				ta.setText("Your " + relation + " " + visname + ", visits you on " + date + " at " + time );
				ta.setEditable(false);
			}
			//Medication is Clicked
			if (v == "0" && m == "1" && d == "0") {
				int row = table.getSelectedRow();
				String prescription = table.getModel().getValueAt(row, 0).toString();
				String date = table.getModel().getValueAt(row, 1).toString();
				ta.setText("Prescription: " + prescription + "\nDate: " + date);
				ta.setEditable(false);
			}
			//Doctor is Clicked
			if (v == "0" && m == "0" && d == "1") {
				int row = table.getSelectedRow();
				String doctor = table.getModel().getValueAt(row, 0).toString();
				String time = table.getModel().getValueAt(row, 1).toString();
				String date = table.getModel().getValueAt(row, 2).toString();
				String desc = table.getModel().getValueAt(row, 3).toString();
				
				ta.setText(doctor + " will be here on " + date + " at " + time + " for " + desc);
				ta.setEditable(false);
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
