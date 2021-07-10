package forms;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import conn.dbCon;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import net.proteanit.sql.DbUtils;

@SuppressWarnings("serial")
public class adminDashboard extends JFrame implements ActionListener, MouseListener {
	
	static JOptionPane j;
	String s;
	
	public JLabel welcome, wait, date, time;
	public JLabel noPat, noEmp, noPend, noRel, noAdm;
	public JLabel lblPat, lblEmp, lblPending, lblRel, lblAdm, lblAge;
	public JLabel noMale, noFemale;
	public JLabel lblMale, lblFemale;
	public JPanel side, headerbg, bg1, bg2, bg3, bg4, bg5;
	public JButton load, manageBtn;
	public JScrollPane scroll;
	public JTable table;
	public JProgressBar p1, p2, p3, p4, p5, p6;
	public Connection conn = null;
	public Statement st = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	public DefaultTableModel dm = null;
	//Patient
	//Patient Section
	JButton astBtn, visBtn, medBtn, docBtn;
	JLabel patientname;
	JScrollPane scrollPane;
	JTable tablePat;
	
	
	JButton AdBtn, PatBtn, EmpBtn, SetBtn, SoBtn;
	
	//ages 0-12 ,age 13-19, 20-30, 31-50, 51-70, 71-100or above
	public JLabel noAge1,noAge2,noAge3,noAge4,noAge5,noAge6;
	public JLabel lblAge1,lblAge2,lblAge3,lblAge4,lblAge5,lblAge6;
	
	
	//patient //doctors //nurse 
	//admitted, pending, released
	Font patMenu = new Font("MADE Evolve Sans", Font.BOLD, 20);

	Font menufont = new Font("MADE Evolve Sans", Font.BOLD, 18);

	Font f12 = new Font("Sans Serif", Font.BOLD, 12);
	Font f15 = new Font("Sans Serif", Font.BOLD, 15);
	Font f20 = new Font("Sans Serif", Font.BOLD, 20);
	Font f25 = new Font("Sans Serif", Font.BOLD, 25);
	Font f30 = new Font("Sans Serif", Font.BOLD, 30);
	Font f35 = new Font("Sans Serif", Font.BOLD, 35);
	Font f40 = new Font("Sans Serif", Font.BOLD, 40);
	Font f45 = new Font("Sans Serif", Font.BOLD, 45);
	
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
	
	
	public void Clock() {
		Thread clock = new Thread() {
			public void run() {
				try {
					while (true) {
						Calendar cal = new GregorianCalendar();
						int day = cal.get(Calendar.DAY_OF_MONTH);
						int month = cal.get(Calendar.MONTH);
						int year = cal.get(Calendar.YEAR);
						int second = cal.get(Calendar.SECOND);
						int minute = cal.get(Calendar.MINUTE);
						int hour = cal.get(Calendar.HOUR_OF_DAY);
						
						date.setText((month+1) + " / " + day + " / " + year);
						time.setText("" + hour + ":" + minute + ":" + second );
						sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		clock.start();
	}
	
	public void patientcount() {

		try {     
			//connection
			Connection conn = dbCon.getConnection();
			//query
			String query = "select COUNT(id) from patients where id != 0;";   
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();  
			while(rs.next()) {
				//get the total value of patients
				noPat.setText(rs.getString("COUNT(id)"));
			}
			ps.close();
			rs.close();
			} catch (Exception ex) {     
				ex.printStackTrace();    
			}  
	}
	
	
	public void pendcount() {
		try {     
			//connection
			Connection conn = dbCon.getConnection();
			String query = "select COUNT(id) from patients where status ='pending';";     
			PreparedStatement ps = conn.prepareStatement(query);     
			ResultSet rs = ps.executeQuery();     
			if(rs.next()) {
				//total count of patient that has a status of pending
				noPend.setText(rs.getString("COUNT(id)"));
			}
			ps.close();     
			rs.close();   
			} catch (Exception ex) {     
				ex.printStackTrace();    
			}  
	}
	public void admitcount() {
		try {     

			Connection conn = dbCon.getConnection();
			String query = "select COUNT(id) from patients where status ='admitted';";     
			PreparedStatement ps = conn.prepareStatement(query);     
			rs = ps.executeQuery();     
			if(rs.next()) {
				noAdm.setText(rs.getString("COUNT(id)"));
			}
			ps.close();     
			rs.close();         
			} catch (Exception ex) {     
				ex.printStackTrace();    
			}  
	}
	public void releasecount() {
		try {    
			Connection conn = dbCon.getConnection();
			String query = "select COUNT(id) from patients where status ='released';";     
			PreparedStatement ps = conn.prepareStatement(query);     
			ResultSet rs = ps.executeQuery();     
			if(rs.next()) {
				noRel.setText(rs.getString("COUNT(id)"));
			}
			ps.close();     
			rs.close();         
			} catch (Exception ex) {     
				ex.printStackTrace();    
			}  
	}
	public void femalecount() {
		try {     
			Connection conn = dbCon.getConnection();
			String query = "select COUNT(id) from patients where gender ='female';";     
			PreparedStatement ps = conn.prepareStatement(query);     
			ResultSet rs = ps.executeQuery();     
			if(rs.next()) {
				noFemale.setText(rs.getString("COUNT(id)"));
			}
			ps.close();     
			rs.close();         
			} catch (Exception ex) {     
				ex.printStackTrace();    
			}  
	}
	public void malecount() {
		try {     
			Connection conn = dbCon.getConnection();
			String query = "select COUNT(id) from patients where gender ='male';";     
			PreparedStatement ps = conn.prepareStatement(query);     
			ResultSet rs = ps.executeQuery();     
			if(rs.next()) {
				noMale.setText(rs.getString("COUNT(id)"));
			}
			ps.close();     
			rs.close();         
			} catch (Exception ex) {     
				ex.printStackTrace();    
			}  
	}
	
	public void ageRange() {
		//Range1
		try {     
			Connection conn = dbCon.getConnection();
			String query = "select COUNT(id) from patients where age <= 12;";     
			PreparedStatement ps = conn.prepareStatement(query);     
			ResultSet rs = ps.executeQuery();     
			if(rs.next()) {
				//Percentage of Patient(range1) from the total No. of Patients
				Double x = Double.parseDouble(rs.getString("COUNT(id)"))/Double.parseDouble(noPat.getText())*100;
				int y = (int) Math.round(x);
				String z = (String) String.format("%.2f", x);
				p1.setValue(y);
				p1.setString(z + "%");
				
			}
			ps.close();     
			rs.close();         
			} catch (Exception ex) {     
				ex.printStackTrace();    
			}
		
		
		//Range 2
		try {     
			Connection conn = dbCon.getConnection();
			String query = "select COUNT(id) from patients where age >= 13 && age <=19;";     
			PreparedStatement ps = conn.prepareStatement(query);     
			ResultSet rs = ps.executeQuery();     
			if(rs.next()) {
				//Percentage of Patient(range2) from the total No. of Patients
				Double x = Double.parseDouble(rs.getString("COUNT(id)"))/Double.parseDouble(noPat.getText())*100;
				int y = (int) Math.round(x);
				String z = (String) String.format("%.2f", x);
				p2.setValue(y);
				p2.setString(z + "%");
			}
			ps.close();     
			rs.close();         
			} catch (Exception ex) {     
				ex.printStackTrace();    
			}  
		//Range 3
		try {     
			Connection conn = dbCon.getConnection();
			String query = "select COUNT(id) from patients where age >= 20 && age <=30";
			PreparedStatement ps = conn.prepareStatement(query);     
			ResultSet rs = ps.executeQuery();     
			if(rs.next()) {
				//Percentage of Patient(range3) from the total No. of Patients
				Double x = Double.parseDouble(rs.getString("COUNT(id)"))/Double.parseDouble(noPat.getText())*100;
				int y = (int) Math.round(x);
				String z = (String) String.format("%.2f", x);
				p3.setValue(y);
				p3.setString(z + "%");
			}
			ps.close();     
			rs.close();         
			} catch (Exception ex) {     
				ex.printStackTrace();    
			}  
		//Range 4
		try {     
			Connection conn = dbCon.getConnection();
			String query = "select COUNT(id) from patients where age >= 31 && age <=50";
			PreparedStatement ps = conn.prepareStatement(query);     
			ResultSet rs = ps.executeQuery();     
			if(rs.next()) {
				//Percentage of Patient(range4) from the total No. of Patients
				Double x = Double.parseDouble(rs.getString("COUNT(id)"))/Double.parseDouble(noPat.getText())*100;
				int y = (int) Math.round(x);
				String z = (String) String.format("%.2f", x);
				p4.setValue(y);
				p4.setString(z + "%");
			}
			ps.close();     
			rs.close();         
			} catch (Exception ex) {     
				ex.printStackTrace();    
			} 
		//Range 5
		try {   
			Connection conn = dbCon.getConnection();
			String query = "select COUNT(id) from patients where age >= 51 && age <=70";
			PreparedStatement ps = conn.prepareStatement(query);     
			ResultSet rs = ps.executeQuery();     
			if(rs.next()) {
				//Percentage of Patient(range5) from the total No. of Patients
				Double x = Double.parseDouble(rs.getString("COUNT(id)"))/Double.parseDouble(noPat.getText())*100;
				int y = (int) Math.round(x);
				String z = (String) String.format("%.2f", x);
				p5.setValue(y);
				p5.setString(z + "%");
			}
			ps.close();     
			rs.close();         
			} catch (Exception ex) {     
				ex.printStackTrace();    
			}  
		//Range 6
		try {     
			
			Connection conn = dbCon.getConnection();
			String query = "select COUNT(id) from patients where age >= 71";
			PreparedStatement ps = conn.prepareStatement(query);     
			ResultSet rs = ps.executeQuery();     
			if(rs.next()) {
				//Percentage of Patient(range6) from the total No. of Patients
				Double x = Double.parseDouble(rs.getString("COUNT(id)"))/Double.parseDouble(noPat.getText())*100;
				int y = (int) Math.round(x);
				String z = (String) String.format("%.2f", x);
				p6.setValue(y);
				p6.setString(z + "%");
			}
			ps.close();     
			rs.close();         
			} catch (Exception ex) {     
				ex.printStackTrace();    
			}  
	} 
	public void loadtable() {
			try {
				Connection conn = dbCon.getConnection();
				String query = "select Fullname,Gender,Birthdate,Age,Illness,Allergies,`Date-in`,`Date-out`,Specialist,Contact,Address,`Status`,Displayphoto from patients";
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				ps.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	public void loadAll() {
		patientcount();
		pendcount();
		admitcount();
		releasecount();
		malecount();
		femalecount();
		loadtable();
		ageRange();
	}
	
	
	@SuppressWarnings("unused")
	public adminDashboard() {
		setTitle("Admin");
		setSize(1000, 650);
		Container pane = getContentPane(); // get the container
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pane.setBackground(bgColor);
		
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon hospital = IconFontSwing.buildIcon(FontAwesome.HOSPITAL_O, 80,Color.DARK_GRAY);
		Icon female = IconFontSwing.buildIcon(FontAwesome.FEMALE, 70,pink);
		Icon male = IconFontSwing.buildIcon(FontAwesome.MALE, 72,eblue);
		Icon patients = IconFontSwing.buildIcon(FontAwesome.USERS, 20,blue);
		
		Icon admin = IconFontSwing.buildIcon(FontAwesome.USER_CIRCLE, 20,Color.white);
		Icon patient = IconFontSwing.buildIcon(FontAwesome.BED, 20,Color.white);
		Icon employee = IconFontSwing.buildIcon(FontAwesome.USER_MD, 20,Color.white);
		Icon signout = IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, 20,Color.white);
		Icon settings = IconFontSwing.buildIcon(FontAwesome.COG, 20,Color.white);
		
		Icon assist = IconFontSwing.buildIcon(FontAwesome.WHEELCHAIR, 80,Color.white);
		Icon medic = IconFontSwing.buildIcon(FontAwesome.CLIPBOARD, 80,Color.white);
		Icon doctor = IconFontSwing.buildIcon(FontAwesome.USER_MD, 80,Color.white);
		Icon visitor = IconFontSwing.buildIcon(FontAwesome.USERS, 80,Color.white);
		Icon patientpic = IconFontSwing.buildIcon(FontAwesome.USER_CIRCLE, 100,Color.white);
		
		/*
 		public JLabel noPat, noEmp, noPend, noRel, noAdm;
		public JLabel lblPat, lblEmp, lblPending, lblRel, lblAdm;
		*/
		
		//ages 0-12 ,age 13-19, 20-30, 31-50, 51-70, 71-100or above

		pane.add(AdBtn = new JButton("Admin"));
		AdBtn.setBounds(0,80,200,50);
		AdBtn.setBorder(null);
		AdBtn.setMargin(new Insets(2, 8, 2, 8));
		AdBtn.setMnemonic(KeyEvent.VK_A);
		AdBtn.setFont(menufont);
		AdBtn.setIcon(admin);
		AdBtn.setContentAreaFilled(false);
		AdBtn.setFocusPainted(false);
		AdBtn.setOpaque(false);
		AdBtn.setForeground(Color.WHITE);
		AdBtn.addActionListener(this);
		AdBtn.addMouseListener(this);

		
		pane.add(PatBtn = new JButton("Patient"));
		PatBtn.setBounds(0,150,200,50);
		PatBtn.setBorder(null);
		PatBtn.setMargin(new Insets(2, 8, 2, 8));
		PatBtn.setMnemonic(KeyEvent.VK_P);
		PatBtn.setFont(menufont);
		PatBtn.setIcon(patient);
		PatBtn.setContentAreaFilled(false);
		PatBtn.setFocusPainted(false);
		PatBtn.setOpaque(false);
		PatBtn.setForeground(Color.WHITE);
		PatBtn.addActionListener(this);
		PatBtn.addMouseListener(this);
	
		
		pane.add(EmpBtn = new JButton("Employee"));
		EmpBtn.setBounds(0,220,200,50);
		EmpBtn.setBorder(null);
		EmpBtn.setMargin(new Insets(2, 8, 2, 8));
		EmpBtn.setMnemonic(KeyEvent.VK_E);
		EmpBtn.setFont(menufont);
		EmpBtn.setIcon(employee);
		EmpBtn.setContentAreaFilled(false);
		EmpBtn.setFocusPainted(false);
		EmpBtn.setOpaque(false);
		EmpBtn.addActionListener(this);
		EmpBtn.setForeground(Color.WHITE);
		EmpBtn.addMouseListener(this);	
		
		pane.add(SetBtn = new JButton("Settings"));
		SetBtn.setBounds(0,290,200,50);
		SetBtn.setBorder(null);
		SetBtn.setMargin(new Insets(2, 8, 2, 8));
		SetBtn.setMnemonic(KeyEvent.VK_S);
		SetBtn.setFont(menufont);
		SetBtn.setIcon(settings);
		SetBtn.setContentAreaFilled(false);
		SetBtn.setFocusPainted(false);
		SetBtn.setOpaque(false);
		SetBtn.setForeground(Color.WHITE);
		SetBtn.addActionListener(this);
		SetBtn.addMouseListener(this);
		
		pane.add(SoBtn = new JButton("Sign Out"));
		SoBtn.setBounds(0,550,200,50);
		SoBtn.setBorder(null);
		SoBtn.setMargin(new Insets(2, 8, 2, 8));
		SoBtn.setMnemonic(KeyEvent.VK_O);
		SoBtn.setFont(menufont);
		SoBtn.setIcon(signout);
		SoBtn.setContentAreaFilled(false);
		SoBtn.setFocusPainted(false);
		SoBtn.setOpaque(false);
		SoBtn.setForeground(Color.WHITE);
		SoBtn.addActionListener(this);
		SoBtn.addMouseListener(this);

		pane.add(manageBtn = new JButton("Manage Accounts"));
		manageBtn.setBounds(210,15,150,30);
		manageBtn.setForeground(Color.white);
		manageBtn.setFocusPainted(false);
		manageBtn.setContentAreaFilled(false);
		manageBtn.addActionListener(this);
		
		pane.add(date = new JLabel());
		pane.add(time = new JLabel());
		
		pane.add(lblAge = new JLabel("Patients Percentage By Age"));
		
		pane.add(p1 = new JProgressBar());
		pane.add(p2 = new JProgressBar());
		pane.add(p3 = new JProgressBar());
		pane.add(p4 = new JProgressBar());
		pane.add(p5 = new JProgressBar());
		pane.add(p6 = new JProgressBar());
		
		
		pane.add(lblAge1 = new JLabel("0 - 12"));
		pane.add(lblAge2 = new JLabel("13 - 19"));
		pane.add(lblAge3 = new JLabel("20 - 30"));
		pane.add(lblAge4 = new JLabel("31 - 50"));
		pane.add(lblAge5 = new JLabel("51 - 70"));
		pane.add(lblAge6 = new JLabel("70 and above"));
		
		
		pane.add(lblFemale = new JLabel("No. of Female Patients"));
		pane.add(lblMale = new JLabel("No. of Male Patients"));
		
		
		pane.add(noFemale = new JLabel("0"));
		pane.add(noMale = new JLabel("0"));
		
		
		pane.add(scroll = new JScrollPane());
		pane.add(table = new JTable());
		
		pane.add(load = new JButton("Refresh"));
		pane.add(welcome = new JLabel("Welcome Admin!"));
		
		pane.add(noPend = new JLabel("0"));
		pane.add(lblPending = new JLabel("No. of Pending Patients"));
		pane.add(noPat = new JLabel("0"));
		pane.add(lblPat = new JLabel("Total No. of Patients"));
		pane.add(noAdm = new JLabel("0"));
		pane.add(lblAdm = new JLabel("No. of Patients Admitted"));
		pane.add(noRel = new JLabel("0"));
		pane.add(lblRel = new JLabel("No. of Patients Released"));
		
		scroll.setViewportView(table);
		scroll.setBounds(210,120,765,150);
		
		table.setDefaultEditor(Object.class, null);
		table.addMouseListener(this);
		
		pane.add(bg1 = new JPanel());
		bg1.setBackground(Color.white);
		bg1.setBounds(200,60,800,50);
		
		pane.add(bg2 = new JPanel());
		bg2.setBackground(Color.white);
		bg2.setBounds(210,280,150,200);
		
		pane.add(bg3 = new JPanel());
		bg3.setBackground(Color.white);
		bg3.setBounds(370,280,150,200);
		
		pane.add(bg4 = new JPanel());
		bg4.setBackground(Color.white);
		bg4.setBounds(530,280,445,325);
		
		pane.add(bg5 = new JPanel());
		bg5.setBackground(Color.white);
		bg5.setBounds(370,5,305,50);
		
		pane.add(headerbg = new JPanel());
		headerbg.setBackground(mbColor);
		headerbg.setBounds(785,5,200,50);
		
		load.setBounds(685,5,100,50);
		load.setBackground(Color.DARK_GRAY);
		load.setForeground(Color.white);
		load.addActionListener(this);
		
		pane.add(side = new JPanel());
		side.setBackground(mbColor);
		side.setBounds(0,0,200,1000);
		
		welcome.setForeground(Color.white);
		welcome.setBounds(785,5,200,50);
		welcome.setHorizontalAlignment(SwingConstants.CENTER);
		welcome.setFont(f20);
		
		noPat.setFont(f45);
		noPat.setForeground(blue);
		noPat.setBounds(400,5,80,50);
		noPat.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lblPat.setFont(f12);
		lblPat.setForeground(Color.DARK_GRAY);
		lblPat.setBounds(500,5,150,50);
		
		noPend.setFont(f45);
		noPend.setForeground(gray);
		noPend.setBounds(230,60,80,50);
		noPend.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lblPending.setFont(f12);
		lblPending.setForeground(Color.DARK_GRAY);
		lblPending.setBounds(320,60,170,50);
		
		noAdm.setFont(f40);
		noAdm.setForeground(green);
		noAdm.setBounds(470,60,80,50);
		noAdm.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lblAdm.setFont(f12);
		lblAdm.setForeground(Color.DARK_GRAY);
		lblAdm.setBounds(560,60,150,50);
	
		noRel.setFont(f40);
		noRel.setForeground(yellow);
		noRel.setBounds(710,60,80,50);
		noRel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lblRel.setFont(f12);
		lblRel.setForeground(Color.DARK_GRAY);
		lblRel.setBounds(800,60,150,50);
		
		lblFemale.setBounds(210,280,150,150);
		lblFemale.setFont(f12);
		lblFemale.setHorizontalAlignment(SwingConstants.CENTER);
		lblFemale.setIcon(female);
		lblFemale.setForeground(pink);
		lblFemale.setVerticalTextPosition(SwingConstants.BOTTOM);
		lblFemale.setHorizontalTextPosition(SwingConstants.CENTER);
		
		noFemale.setBounds(210,380,150,100);
		noFemale.setHorizontalAlignment(SwingConstants.CENTER);
		noFemale.setFont(f40);
		
		lblMale.setBounds(370,280,150,150);
		lblMale.setFont(f12);
		lblMale.setHorizontalAlignment(SwingConstants.CENTER);
		lblMale.setIcon(male);
		lblMale.setForeground(eblue);
		lblMale.setVerticalTextPosition(SwingConstants.BOTTOM);
		lblMale.setHorizontalTextPosition(SwingConstants.CENTER);
		
		noMale.setBounds(370,380,150,100);
		noMale.setHorizontalAlignment(SwingConstants.CENTER);
		noMale.setFont(f40);
		
		lblAge1.setBounds(540,330,100,30);
		lblAge1.setHorizontalAlignment(SwingConstants.CENTER);
		lblAge1.setFont(f15);
		
		p1.setBounds(650,330,300,30);
		p1.setStringPainted(true);
		p1.setForeground(mbColor);
		
		lblAge2.setBounds(540,370,100,30);
		lblAge2.setHorizontalAlignment(SwingConstants.CENTER);
		lblAge2.setFont(f15);
		
		p2.setBounds(650,370,300,30);
		p2.setStringPainted(true);
		p2.setForeground(mbColor);
		
		lblAge3.setBounds(540,410,100,30);
		lblAge3.setHorizontalAlignment(SwingConstants.CENTER);
		lblAge3.setFont(f15);
		
		p3.setBounds(650,410,300,30);
		p3.setStringPainted(true);
		p3.setForeground(mbColor);
		
		lblAge4.setBounds(540,450,100,30);
		lblAge4.setHorizontalAlignment(SwingConstants.CENTER);
		lblAge4.setFont(f15);
		
		p4.setBounds(650,450,300,30);
		p4.setStringPainted(true);
		p4.setForeground(mbColor);
	
		lblAge5.setBounds(540,490,100,30);
		lblAge5.setHorizontalAlignment(SwingConstants.CENTER);
		lblAge5.setFont(f15);
		
		p5.setBounds(650,490,300,30);
		p5.setStringPainted(true);
		p5.setForeground(mbColor);
		
		lblAge6.setBounds(540,530,100,30);
		lblAge6.setHorizontalAlignment(SwingConstants.CENTER);
		lblAge6.setFont(f15);
		
		p6.setBounds(650,530,300,30);
		p6.setStringPainted(true);
		p6.setForeground(mbColor);
		
		lblAge.setBounds(530,280,445,40);
		lblAge.setFont(f20);
		lblAge.setHorizontalAlignment(SwingConstants.CENTER);
		lblAge.setIcon(patients);

		time.setBounds(210,470,310,100);
		time.setFont(f35);
		time.setHorizontalAlignment(SwingConstants.CENTER);
		time.setForeground(Color.white);
		
		date.setBounds(210,550,310,30);
		date.setFont(f15);
		date.setHorizontalAlignment(SwingConstants.CENTER);
		date.setForeground(Color.white);
		
		Clock();

		
	
		
	}
	
	
	
	
	@SuppressWarnings({"static-access" })
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == EmpBtn) {
			int x = j.showConfirmDialog(null, "Do you want to login as an Employee", "Login",j.YES_NO_OPTION);
    		
    		if (x == 0) {
        		loginForm frame = new loginForm();	          
    	        frame.setUndecorated(true);
    	        frame.setVisible(true);
    	        frame.setLocationRelativeTo(null);
    	        this.dispose();
    		}
		}
		
		
		
		if(e.getSource() == manageBtn) {
			this.dispose();
			adminForm af = new adminForm();
			Random rand = new Random();
			int c1 = rand.nextInt(10), c2 = rand.nextInt(10),c3 = rand.nextInt(10),c4 = rand.nextInt(10),c5 = rand.nextInt(10);
			Integer.toString(c1);
			String genCode = Integer.toString(c1) + Integer.toString(c2)+ Integer.toString(c3) + Integer.toString(c4) +  Integer.toString(c5);
			af.tfeID.setText(genCode);
			af.tfeID.setVisible(true);
			af.contact.setVisible(true);
			af.month.setVisible(true);
			af.day.setVisible(true);
			af.year.setVisible(true);
			af.clearBtn.doClick();
			af.setLocationRelativeTo(null);
			af.setLayout(null);
			af.setResizable(false);
			af.setVisible(true);
		}
		
		if (e.getSource() == load) {
			loadAll();
		}
		if (e.getSource() == visBtn) {
			try {     
				Connection conn = dbCon.getConnection();
				String query = "SELECT VisitorName,Relationship,Time FROM visitorlog where patients = '" + patientname.getText() + "'";     
				PreparedStatement ps = conn.prepareStatement(query);     
				rs = ps.executeQuery();     
				
				while (rs.next()) {
					table.setModel(DbUtils.resultSetToTableModel(rs));     
				}
				
				
				ps.close();     
				rs.close();         
				scrollPane.getViewport().add(table);
				
				} catch (Exception ex) {     
					ex.printStackTrace();    
				}   
		}
		if (e.getSource() == docBtn) {
			try {     
				Connection conn = dbCon.getConnection();
				String query = "select * from doctor";     
				PreparedStatement ps = conn.prepareStatement(query);     
				rs = ps.executeQuery();     
				table.setModel(DbUtils.resultSetToTableModel(rs));     
				ps.close();     
				rs.close();         
				

				scrollPane.getViewport().add(table);
				} catch (Exception ex) {     
					ex.printStackTrace();    
				}   
		}
		
		if (e.getSource() == SoBtn) {
			this.dispose();
		    loginForm frame = new loginForm();	          
            frame.setUndecorated(true);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
		}
		
		if(e.getSource() == PatBtn) {
			
			String n = j.showInputDialog(null, "Patient Name : (Firstname,Middlename,Lastname)", "Patient Information", j.PLAIN_MESSAGE);
			
			try{
				   Connection connection=dbCon.getConnection();
			          String query="SELECT * FROM patients WHERE Fullname=?";
			          PreparedStatement ps=connection.prepareStatement(query);
			          ps.setString(1,n);
			          
			          ResultSet rs = ps.executeQuery();
			          
			          while(rs.next())
			          {
			        	if(rs.getString("Fullname").equals(n)) {
			        		  JOptionPane.showMessageDialog(null, "Success");
					            this.dispose();		
					            patient pf = new patient();
					    		pf.dispose();  byte[] pic = rs.getBytes("displayphoto");  
					        	  ImageIcon image = new ImageIcon(pic);
									Image im = image.getImage();
									Image myImg = im.getScaledInstance(pf.patdp.getWidth(), pf.patdp.getHeight(), Image.SCALE_SMOOTH);
									ImageIcon newImage = new ImageIcon(myImg);
									pf.patdp.setIcon(newImage);
								  
					    		pf.patientname.setText(rs.getString("fname") + " " + rs.getString("mname") + " " + rs.getString("lname"));
					    	    pf.setExtendedState(JFrame.MAXIMIZED_BOTH);
					    		pf.setUndecorated(true);
					    		pf.setLayout(null);
					    		pf.setVisible(true);
					    		pf.setLocationRelativeTo(null);
					           
			        	}
			        	else{
			        		j.showMessageDialog(null, "Patient Doesn't have a record", "Error", j.ERROR_MESSAGE);
			        		System.out.println(n + rs.getString("Fullname"));
			        	}
			          }
			          ps.close();
			}catch (Exception ee) {
				ee.printStackTrace();
			}

		}
		
		if(e.getSource() == SetBtn) {
			j.showMessageDialog(null, "The Settings is dedicated for the Database Connection using TCP/IP.\n Due to the Community Quarantine the Developers cannot test the said feature.\nFurthermore the Developers intended to upgrade this system for Future Use.\nHoping for your Consideration.");
		}
		
	}
	
	public static void main(String[] args) {}



	@SuppressWarnings("static-access")
	@Override
	public void mouseClicked(MouseEvent mc) {
		
		if(mc.getSource() == table) {
				try {
					Connection conn = dbCon.getConnection();
					
					int row = table.getSelectedRow();
					String id = (table.getModel().getValueAt(row, 0).toString());
					String query = "SELECT * FROM patients where Fullname = '" + id + "' ";
					ps = conn.prepareStatement(query);
					rs = ps.executeQuery();
					while (rs.next()) {
						info in = new info();
						in.dispose();
						in.setLocationRelativeTo(null);
						in.setResizable(false);
						in.setVisible(true);
						
						in.lblFull.setText("Name: " + rs.getString("Fullname"));
						in.lblGender.setText("Gender: " + rs.getString("Gender"));
						in.lblAge.setText("Age: " + rs.getString("Age"));
						in.lblIll.setText("Illness: " + rs.getString("Illness"));
						in.lblAll.setText("Allergies: " + rs.getString("Allergies"));
						in.lblDin.setText("Date In: " + rs.getString("Date-In"));
						in.lblDout.setText("Date Out: " + rs.getString("Date-Out"));
						in.lblPhys.setText("Doctor: " + rs.getString("Specialist"));
						in.lblAddr.setText("Address: " + rs.getString("Address"));
						in.lblCont.setText("Contact: " + rs.getString("Contact"));
						in.lblStat.setText("Status: " + rs.getString("Status"));
						
						byte[] img = rs.getBytes("DisplayPhoto");
						
						ImageIcon image = new ImageIcon(img);
						Image im = image.getImage();
						Image myImg = im.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
						ImageIcon newImage = new ImageIcon(myImg);
						in.lblDp.setIcon(newImage);
						
					}
					
					ps.close();
					
				}
				catch (Exception e) {
					e.printStackTrace();
					j.showMessageDialog(null, e);
				}
			
		}
		
	}



	@Override
	public void mouseEntered(MouseEvent me) {
		
		if(me.getSource() == AdBtn) {
	        AdBtn.setForeground(Color.decode("#34495e"));
	        AdBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.USER_CIRCLE, 20,Color.decode("#34495e")));
	        AdBtn.setOpaque(true);
	    	AdBtn.setBackground(Color.white);
		}
		
		if(me.getSource() == PatBtn) {
            PatBtn.setForeground(Color.decode("#34495e"));
            PatBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.BED, 20,Color.decode("#34495e")));
            PatBtn.setOpaque(true);
    		PatBtn.setBackground(Color.white);
		}
		
		if(me.getSource() == EmpBtn) {
          	EmpBtn.setForeground(Color.decode("#34495e"));
          	EmpBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.USER_MD, 20,Color.decode("#34495e")));
          	EmpBtn.setOpaque(true);
  			EmpBtn.setBackground(Color.white);
		}
		
		if(me.getSource() == SetBtn) {
            SetBtn.setForeground(Color.decode("#34495e"));
            SetBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.COG, 20,Color.decode("#34495e")));
            SetBtn.setOpaque(true);
            SetBtn.setBackground(Color.white);
		}
		
		if(me.getSource() == SoBtn) {
	        SoBtn.setForeground(Color.decode("#34495e"));
	        SoBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, 20,Color.decode("#34495e")));
	        SoBtn.setOpaque(true);
	        SoBtn.setBackground(Color.white);
		}
		
		
		
		
		
	
	}
	@Override
	public void mouseExited(MouseEvent mex) {
		
		if(mex.getSource() == AdBtn) {
			AdBtn.setForeground(Color.white);
        	AdBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.USER_CIRCLE, 20,Color.white));
 	        AdBtn.setOpaque(false);
		}
		if(mex.getSource() == PatBtn) {
			PatBtn.setForeground(Color.white);
        	PatBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.BED, 20,Color.white));
 	        PatBtn.setOpaque(false);
		}
		if(mex.getSource() == EmpBtn) {
		    EmpBtn.setForeground(Color.white);
    	    EmpBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.USER_MD, 20,Color.white));
    	    EmpBtn.setOpaque(false);
		}
		if(mex.getSource() == SetBtn) {
			SetBtn.setForeground(Color.white);
        	SetBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.COG, 20,Color.white));
        	SetBtn.setOpaque(false);
		}
		
		if(mex.getSource() == SoBtn) {
			SoBtn.setForeground(Color.white);
        	SoBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, 20,Color.white));
        	SoBtn.setOpaque(false);
		}
		
	}



	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
