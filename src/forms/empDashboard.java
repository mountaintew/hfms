package forms;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.time.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import conn.dbCon;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import net.proteanit.sql.DbUtils;


@SuppressWarnings({ "serial", "unused" , "rawtypes"})
public class empDashboard extends JFrame implements ActionListener, MouseListener, FocusListener, KeyListener, ChangeListener{
	int x,m;
	static JOptionPane j;
	public String s, n, pname, selID;
	public JButton PatBtn, SetBtn, SoBtn, upload, save, edit, del, load, searchBtn, loadPres,
				   btn1, btn2, btn3, btn4, savePat, editPat, delPat, loadvis;
	
	public JPanel side, panel1, panel2, panel3;
	public JLabel other, dt, dp, lblpat, lblfname, lblmname, lbllname, lblgend, lblBday, lblage, 
				  lblill, lblall, lbldin, lbldout, lblphys, lbladdr, lblcnum, lblstat,
				  calbg, lblmo, lblda, lblye, lblwee, lblwe, time, lblpatName, lblpatTime, 
				  lblpatDate, lblpatDesc, lblpatStatus, pn, prem, pda, pta, pdesc, 
				  stamp, lblnoApp, lblspe, frmt, type, userid, patid;
	
	public JComboBox gender, aller, stat, doc, spe, ampm, patname, patStatus;
	public JTextField fname, mname, lname, ill, ot, age, id, search, ptn, ptrem, ptda, ptta;
	public JTextArea addr, patDesc, ptdesc;
	public JCheckBox jcb1, jcb2, jcb3;
	public MaskFormatter ag, co, in, out, mo, da ,ye, patda, ptime;
	public JFormattedTextField  contact, din, dout, month, day, year, patdate, pattime;
	public JFileChooser fileChooser;
	public FileNameExtensionFilter filter;
	public JScrollPane sp;
	public JTable table, table1, table2, table3;
	public JTabbedPane tp;
	public JViewport jvp, jvp1, jvp2, jvp3, jvp4, jvp5;
	public JMenuItem presc, patDel, viewDt, patView, patDel2, vis , edvis, delvis, seepres;
	public JPopupMenu jpm, jpm1, jpm2, jpm3;
	
	
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
	Font f80 = new Font("Sans Serif", Font.BOLD, 80);
	public Connection conn = null;
	public Statement st = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	Color bgColor = Color.decode("#34495e");
	Color mbColor = Color.decode("#16a085");
	Color mbColor_alt = Color.decode("#f0e8b72");
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
						Calendar c = Calendar.getInstance();
						
						
						int day = cal.get(Calendar.DAY_OF_MONTH);
						int year = cal.get(Calendar.YEAR);
						int second = cal.get(Calendar.SECOND);
						int minute = cal.get(Calendar.MINUTE);
						int hour = cal.get(Calendar.HOUR);
						int week =cal.get(Calendar.DAY_OF_WEEK);
						
						lblmo.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
						lblye.setText(year + "");
						lblda.setText(day + "");
						lblwee.setText(c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH));
						
						if (minute <= 9) {
							time.setText(hour + ":0"+ minute + " " +c.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.ENGLISH));
						}
						else {
							time.setText(hour + ":"+ minute + " " +c.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.ENGLISH));
						}
						
						sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		clock.start();
	}
	
	@SuppressWarnings("unchecked")
	public DefaultComboBoxModel doctors() {
		DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
		try {     
			conn = dbCon.getConnection();
			String query = "SELECT eLname,eJp,eFname from employee;";     
			PreparedStatement ps = conn.prepareStatement(query);     
			ResultSet rs = ps.executeQuery();     
			while(rs.next()) {
				if(rs.getString("eJp").contentEquals("Doctor")) {
					dcbm.addElement("Dr. " + rs.getString("eFname") + " " + rs.getString("eLname"));
				}
			}
			ps.close();     
			rs.close();         
			} catch (Exception ex) {     
				ex.printStackTrace();    
			}
		return dcbm; 
	}
	
	@SuppressWarnings("unchecked")
	public DefaultComboBoxModel patients() {
		DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
		try {     
			conn = dbCon.getConnection();
			String query = "SELECT Fullname from patients ORDER BY Fullname";     
			PreparedStatement ps = conn.prepareStatement(query);     
			ResultSet rs = ps.executeQuery();     
			while(rs.next()) {
				dcbm.addElement(rs.getString("Fullname"));
			}
			ps.close();     
			rs.close();         
			} catch (Exception ex) {     
				ex.printStackTrace();    
			}
		return dcbm; 
	}

	public void load(){
		try {
			conn = dbCon.getConnection();
			String query = "select Specialist,Patient,`Time`,`Date`,`Description`,Status from appointments where userID = '" + userid.getText() + "'";
			PreparedStatement ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			table1.setModel(DbUtils.resultSetToTableModel(rs));
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void loadtable1() {
		try {
			conn = dbCon.getConnection();
			String query = "select Specialist,Patient,`Time`,`Date`,`Description`,Status from appointments";
			PreparedStatement ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			table1.setModel(DbUtils.resultSetToTableModel(rs));
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadtable() {
		try {
			conn = dbCon.getConnection();
			String query = "select Fullname,Gender,Age,Illness,Allergies,`Date-in`,`Date-out`,Specialist,Address,Contact,`Status`,DisplayPhoto from patients";
			PreparedStatement ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public empDashboard() {
		setTitle("Dashboard");
		setSize(1000, 650);
		Container pane = getContentPane(); // get the container
		pane.setLayout(new BorderLayout());
		setContentPane(pane);
		pane.setVisible(true);
		pane.setBackground(bgColor);
		
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));   
		filter = new FileNameExtensionFilter("PNG JPG AND JPEG", "png", "jpeg", "jpg");      
		fileChooser.addChoosableFileFilter(filter); 

		IconFontSwing.register(FontAwesome.getIconFont());
		Icon patient = IconFontSwing.buildIcon(FontAwesome.BED, 20,Color.white);
		Icon employee = IconFontSwing.buildIcon(FontAwesome.USER_MD, 20,Color.white);
		Icon signout = IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, 20,Color.white);
		Icon settings = IconFontSwing.buildIcon(FontAwesome.COG, 20,Color.white);
		Icon up = IconFontSwing.buildIcon(FontAwesome.UPLOAD, 18,Color.white);
		Icon plus = IconFontSwing.buildIcon(FontAwesome.PLUS, 20,Color.white);
		Icon trash = IconFontSwing.buildIcon(FontAwesome.TRASH, 20,Color.white);
		Icon pencil = IconFontSwing.buildIcon(FontAwesome.PENCIL_SQUARE_O, 20,Color.white);
		Icon refresh = IconFontSwing.buildIcon(FontAwesome.LIST, 13,Color.white);
		Icon se = IconFontSwing.buildIcon(FontAwesome.SEARCH, 13,Color.white);
		Icon eye = IconFontSwing.buildIcon(FontAwesome.EYE, 13,Color.white);
		Icon users = IconFontSwing.buildIcon(FontAwesome.USERS, 13,Color.white);
		
		pane.add(type = new JLabel());
		pane.add(userid = new JLabel());
		pane.add(stamp = new JLabel());
		pane.add(patid = new JLabel());
		
		lblfname = new JLabel("Firstname:");
		lblfname.setBounds(20,10,70,20);
		
		fname = new JTextField();
		fname.setBounds(20,30,100,30);
		fname.setBorder(BorderFactory.createLineBorder(bgColor));
		fname.addFocusListener(this);
		
		lblmname = new JLabel("Middlename:");
		lblmname.setBounds(140,10,80,20);
		
		
		mname = new JTextField();
		mname.setBounds(140,30,100,30);
		mname.setBorder(BorderFactory.createLineBorder(bgColor));
		mname.addFocusListener(this);
		
		lbllname = new JLabel("Lastname:");
		lbllname.setBounds(260,10,80,20);
		
		lname = new JTextField();
		lname.setBounds(260,30,100,30);
		lname.setBorder(BorderFactory.createLineBorder(bgColor));
		lname.addFocusListener(this);
		
		lblgend = new JLabel("Gender:");
		lblgend.setBounds(20, 70, 50, 30);
		
		gender = new JComboBox();
		gender.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female"}));   
		gender.setBounds(20,95,100,30);
		
		gender.setBorder(BorderFactory.createLineBorder(bgColor));
		
		
		lblBday = new JLabel("Birthdate:");
		lblBday.setBounds(370, 10, 80, 20 );
		
		
		try {
			co = new MaskFormatter("####-###-####");
			} catch (ParseException e) {    
				e.printStackTrace();   
		} 
		co.setPlaceholderCharacter('_'); 
		try {
			in = new MaskFormatter("##-##-####");
			} catch (ParseException e) {    
				e.printStackTrace();   
		} 
		in.setPlaceholderCharacter('_'); 
		try {
			out = new MaskFormatter("##-##-####");
			} catch (ParseException e) {    
				e.printStackTrace();   
		} 
		out.setPlaceholderCharacter('_'); 
		try {
			mo = new MaskFormatter("##");
			} catch (ParseException e) {    
				e.printStackTrace();   
		} 
		mo.setPlaceholderCharacter('M'); 
		try {
			da = new MaskFormatter("##");
			} catch (ParseException e) {    
				e.printStackTrace();   
		} 
		da.setPlaceholderCharacter('D'); 
		try {
			ye = new MaskFormatter("####");
			} catch (ParseException e) {    
				e.printStackTrace();   
		} 
		ye.setPlaceholderCharacter('Y'); 
		try {
			patda = new MaskFormatter("##-##-####");
			} catch (ParseException e) {    
				e.printStackTrace();   
		} 
		patda.setPlaceholderCharacter('_'); 
		
		try {
			ptime = new MaskFormatter("##:##");
			} catch (ParseException e) {    
				e.printStackTrace();   
		} 
		ptime.setPlaceholderCharacter('_'); 
		
		month = new JFormattedTextField(mo);
		month.setBounds(370,30,30,30);
		month.setHorizontalAlignment(SwingConstants.CENTER);
		month.setBorder(BorderFactory.createLineBorder(bgColor));
		month.addFocusListener(this);
		
		day = new JFormattedTextField(da);
		day.setBounds(405,30,30,30);
		day.setHorizontalAlignment(SwingConstants.CENTER);
		day.setBorder(BorderFactory.createLineBorder(bgColor));
		day.addFocusListener(this);
		
		year = new JFormattedTextField(ye);
		year.setBounds(440,30,45,30);
		year.setHorizontalAlignment(SwingConstants.CENTER);
		year.setBorder(BorderFactory.createLineBorder(bgColor));
		year.addFocusListener(this);
		
		lblage = new JLabel("Age:");
		lblage.setBounds(495, 10, 70, 20);
		
		
		age = new JTextField();
		age.setBounds(495, 30, 50, 30);
		age.setBorder(BorderFactory.createLineBorder(bgColor));
		age.setHorizontalAlignment(SwingConstants.CENTER);
		age.addKeyListener(this);
	
		lblill = new JLabel("Illness: ");
		lblill.setBounds(140, 70, 50, 30);
	
		jcb1 = new JCheckBox("Fever");
		jcb1.setBounds(140,95,60,30);
		jcb1.setFocusPainted(false);
		jcb1.setBackground(Color.white);
		
		jcb2 = new JCheckBox("Headache");
		jcb2.setBounds(210,95,90,30);
		jcb2.setFocusPainted(false);
		jcb2.setBackground(Color.white);
		
		jcb3 = new JCheckBox("Cough");
		jcb3.setBounds(305,95,70,30);
		jcb3.setFocusPainted(false);
		jcb3.setBackground(Color.white);
		
		other = new JLabel("Other Illness: ");
		other.setBounds(380,70,180,30);

		ot = new JTextField();
		ot.setBounds(380,95,165	,30);
		ot.setBorder(BorderFactory.createLineBorder(bgColor));
		ot.addFocusListener(this);
		
		lblall = new JLabel("Allergies:");
		lblall.setBounds(20,130,100,30);
		
		aller = new JComboBox();
		aller.setModel(new DefaultComboBoxModel(new String[] {"None", "Present"}));   
		aller.setBounds(20,160,100,30);
		
		lbldin = new JLabel("Date-In:");
		lbldin.setBounds(140,130,100,30);
		
		din = new JFormattedTextField(in);
		din.setBounds(140,160,100,30);
		din.setBorder(BorderFactory.createLineBorder(bgColor));
		din.addFocusListener(this);
		
		lbldout = new JLabel("Date-Out:");
		lbldout.setBounds(260,130,100,30);
		
		dout = new JFormattedTextField(out);
		dout.setBounds(260,160,100,30);
		dout.setBorder(BorderFactory.createLineBorder(bgColor));
			
		lblphys = new JLabel("Doctor:");
		lblphys.setBounds(378,130,100,30);
		
		doc = new JComboBox();
		doc.setBounds(378,160,165,30);
		doc.setModel(doctors()); 
		
		lbladdr = new JLabel("Address:");
		lbladdr.setBounds(20,195,100,30);
		
		addr = new JTextArea();
		addr.setBounds(20,220,220,50);
		addr.setBorder(BorderFactory.createLineBorder(bgColor));
		addr.addFocusListener(this);
		
		lblcnum = new JLabel("Contact No.:");
		lblcnum.setBounds(260,195,100,30);
		
		contact = new JFormattedTextField(co);
		contact.setBounds(260,220,100,30);
		
		
		lblstat = new JLabel("Status:");
		lblstat.setBounds(378,195,100,30);
		stat = new JComboBox();
		stat.setModel(new DefaultComboBoxModel(new String[] {"Pending", "Admitted", "Released"}));   
		stat.setBounds(378,220,165,30);

		upload = new JButton();
		upload.setBounds(715,5,30,30);
		upload.setBackground(blue);
		upload.setForeground(Color.white);
		upload.addActionListener(this);
		upload.setFocusPainted(false);
		upload.setIcon(up);
		
		dp = new JLabel();
		dp.setBounds(565,5,150,150);
		dp.setBorder(BorderFactory.createLineBorder(Color.black));
		
		save = new JButton("Save");
		save.setIcon(plus);
		save.setBounds(565, 162, 200, 30);
		save.setFocusPainted(false);
		save.setBackground(green);
		save.setForeground(Color.white);
		save.addActionListener(this);
		
		edit = new JButton("Edit");
		edit.setIcon(pencil);		
		edit.setBounds(565, 200, 200, 30);
		edit.setFocusPainted(false);
		edit.setBackground(blue);
		edit.setForeground(Color.white);
		edit.addActionListener(this);
		
		del = new JButton("Delete");
		del.setIcon(trash);
		del.setBounds(565, 237, 200, 30);
		del.setFocusPainted(false);
		del.setBackground(red);
		del.setForeground(Color.white);
		del.addActionListener(this);
		
		pane.add(load = new JButton("Load Table"));
		load.setBounds(885, 320, 100, 30);
		load.setFocusPainted(false);
		load.setForeground(Color.white);
		load.setBackground(mbColor);
		load.setBorder(null);
		load.setIcon(refresh);
		load.addActionListener(this);
		
		pane.add(panel1 = new JPanel());
		panel1.setBounds(210,10,775,300);
		panel1.setBackground(Color.white);
		panel1.add(del);
		panel1.add(save);
		panel1.add(edit);
		panel1.add(dp);
		panel1.add(upload);
		panel1.add(lblfname);
		panel1.add(lblmname);
		panel1.add(lbllname);
		panel1.add(fname);
		panel1.add(mname);
		panel1.add(lname);
		panel1.add(lblgend);
		panel1.add(gender);
		panel1.add(lblBday);
		panel1.add(month);
		panel1.add(day);
		panel1.add(year);
		panel1.add(lblage);
		panel1.add(age);
		panel1.add(lblill);
		panel1.add(jcb1);
		panel1.add(jcb2);
		panel1.add(jcb3);
		panel1.add(other);
		panel1.add(ot);
		panel1.add(lblall);
		panel1.add(aller);
		panel1.add(lbldin);
		panel1.add(din);
		panel1.add(lbldout);
		panel1.add(dout);
		panel1.add(lblphys);
		panel1.add(doc);
		panel1.add(lbladdr);
		panel1.add(addr);
		panel1.add(lblcnum);
		panel1.add(contact);
		panel1.add(lblstat);
		panel1.add(stat);
		panel1.setLayout(null);
		
		//new panel
		lblye = new JLabel();
		lblye.setBounds(0,0,150,30);
		lblye.setForeground(Color.white);
		lblye.setFont(f20);
		lblye.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblmo = new JLabel();
		lblmo.setBounds(0,0,150,30);
		lblmo.setForeground(Color.white);
		lblmo.setFont(f20);
		lblmo.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblda = new JLabel();
		lblda.setBounds(0,25,150,100);
		lblda.setForeground(mbColor);
		lblda.setFont(f80);
		lblda.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Label Welcome
		lblwe = new JLabel();
		lblwe.setBounds(0,0,300,35);
		lblwe.setForeground(Color.white);
		lblwe.setFont(f20);
		lblwe.setBorder(BorderFactory.createLineBorder(mbColor, 5));
		lblwe.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Label Day of the Week
		lblwee = new JLabel();
		lblwee.setBounds(0,5,150,30);
		lblwee.setForeground(mbColor);
		lblwee.setFont(f15);
		lblwee.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Label for Time
		time = new JLabel();
		time.setBounds(0,0,150,35);
		time.setForeground(Color.decode("#f7f1e3"));
		time.setFont(f15);
		time.setHorizontalAlignment(SwingConstants.CENTER);
		
		//month
		jvp = new JViewport();
		jvp.setBackground(mbColor);
		jvp.setBounds(610,40,150,30);
		jvp.add(lblmo);
		jvp.setLayout(null);
		jvp.addFocusListener(this);
		
		//year
		jvp1 = new JViewport();
		jvp1.setBackground(mbColor);
		jvp1.setBounds(610,235,150,30);
		jvp1.add(lblye);
		jvp1.setLayout(null);
		
		//day
		jvp2 = new JViewport();
		jvp2.setBackground(Color.decode("#f7f1e3"));
		jvp2.setBounds(610,70,150,110);
		jvp2.add(lblda);
		jvp2.setLayout(null);
		
		//welcome
		jvp3 = new JViewport();
		jvp3.setBackground(mbColor);
		jvp3.setBounds(10,0,600,35);
		jvp3.add(lblwe);
		jvp3.setLayout(null);
		
		jvp4 = new JViewport();
		jvp4.setBackground(Color.decode("#f7f1e3"));
		jvp4.setBounds(610,170,150,65);
		jvp4.add(lblwee);
		jvp4.setLayout(null);
		
		jvp5 = new JViewport();
		jvp5.setBounds(610,0,150,35);
		jvp5.setBackground(mbColor_alt);
		jvp5.add(time);
		jvp5.setLayout(null);
		
		//No of Appointments Today
		btn1 = new JButton();
		btn1.setBounds(10,60,180,40);
		btn1.setBackground(eblue);
		btn1.setBorder(null);
		btn1.setForeground(Color.white);
		btn1.setFocusPainted(false);
		btn1.addMouseListener(this);
		btn1.addActionListener(this);
		
		btn2 = new JButton("See Schedule for this Day");
		btn2.setBounds(10,110,180,40);
		btn2.setBackground(orange);
		btn2.setBorder(null);
		btn2.setFocusPainted(false);
		btn2.setForeground(Color.white);
		btn2.addMouseListener(this);
		btn2.addActionListener(this);
		
		btn3 = new JButton("See Schedule for this Month");
		btn3.setBounds(10,160,180,40);
		btn3.setBackground(green);
		btn3.setBorder(null);
		btn3.setFocusPainted(false);
		btn3.setForeground(Color.white);
		btn3.addMouseListener(this);
		btn3.addActionListener(this);
		
		btn4 = new JButton("Add Prescription");
		btn4.setBounds(10,210,180,40);
		btn4.setBackground(gray);
		btn4.setBorder(null);
		btn4.setFocusPainted(false);
		btn4.setForeground(Color.white);
		btn4.addMouseListener(this);
		btn4.addActionListener(this);
		
		//JLabel pn, prem, pda, pta, pdesc;
		//JTextField ptn, ptrem, ptda, ptta, ptdes;

		pn = new JLabel("Patient Name:");
		pn.setBounds(230,60,180,30);
		
		ptn = new JTextField();
		ptn.setEditable(false);
		ptn.setBounds(230,85,180,25);
		
		pda = new JLabel("Date:");
		pda.setBounds(430,60,50,30);
		
		ptda = new JTextField();
		ptda.setBounds(430,85,80,25);
		ptda.setEditable(false);
		ptda.setHorizontalAlignment(SwingConstants.CENTER);

		pta = new JLabel("Time:");
		pta.setBounds(520,60,70,30);
		
		ptta = new JTextField();
		ptta.setBounds(520,85,60,25);
		ptta.setEditable(false);
		ptta.setHorizontalAlignment(SwingConstants.CENTER);
		
		prem = new JLabel("Status:");
		prem.setBounds(230,120,180,30);
		
		ptrem = new JTextField();
		ptrem.setBounds(230,145,80,25);
		ptrem.setHorizontalAlignment(SwingConstants.CENTER);
		ptrem.setEditable(false);
		
		pdesc = new JLabel("Description:");
		pdesc.setBounds(320,120,100,30);
		
		ptdesc = new JTextArea();
		ptdesc.setBounds(320,145,250,90);
		ptdesc.setBorder(BorderFactory.createLineBorder(Color.black));  
		ptdesc.setEditable(false);
		
		panel2 = new JPanel();
		panel2.setLayout(null);
		panel2.setBackground(Color.white);
		panel2.add(jvp);
		panel2.add(jvp1);
		panel2.add(jvp2);
		panel2.add(jvp3);
		panel2.add(jvp4);
		panel2.add(jvp5);
		panel2.add(btn1);
		panel2.add(btn2);
		panel2.add(btn3);
		panel2.add(btn4);
		
		panel2.add(pn);
		panel2.add(ptn);
		panel2.add(pda);
		panel2.add(ptda);
		panel2.add(pta);
		panel2.add(ptta);
		panel2.add(prem);
		panel2.add(ptrem);
		panel2.add(pdesc);
		panel2.add(ptdesc);
		
		savePat = new JButton("Save Appointment");
		savePat.setIcon(plus);
		savePat.setBounds(160,240,150,30);
		savePat.setBackground(green);
		savePat.setForeground(Color.white);
		savePat.setFocusPainted(false);
		savePat.setBorder(null);
		savePat.addActionListener(this);
		
		editPat = new JButton("Edit Appointment");
		editPat.setIcon(pencil);
		editPat.setBounds(310,240,150,30);
		editPat.setBackground(eblue);
		editPat.setForeground(Color.white);
		editPat.setFocusPainted(false);
		editPat.setBorder(null);
		editPat.addActionListener(this);
		
		delPat  = new JButton("Delete Appointment");
		delPat.setIcon(trash);
		delPat.setBounds(460,240,150,30);
		delPat.setBackground(red);
		delPat.setForeground(Color.white);
		delPat.setFocusPainted(false);
		delPat.setBorder(null);
		delPat.addActionListener(this);
		
		
		
		lblpatName = new JLabel("Patient Name:");
		lblpatName.setBounds(175,10,90,20);

		patname = new JComboBox();
		patname.setBounds(175,30,170,30);
		patname.setModel(patients());
		
		lblspe = new JLabel("Specialist:");
		lblspe.setBounds(355,10,80,20);
		
		spe = new JComboBox(doctors());
		spe.setBounds(355,30,120,30);
		
		lblpatStatus = new JLabel("Status:");
		lblpatStatus.setBounds(485,10,80,20);
		
		patStatus = new JComboBox();
		patStatus.setModel(new DefaultComboBoxModel(new String[] {"Pending", "Completed"}));   
		patStatus.setBounds(485,30,100,30);
		
		
		frmt = new JLabel("MM-DD-YYYY");
		frmt.setFont(f9);
		frmt.setBounds(270,130,140,20);
		
		lblpatDate = new JLabel("Date of Appointment");
		lblpatDate.setBounds(240,80,140,20);
		
		patdate = new JFormattedTextField(patda);
		patdate.setBounds(240,105,120,30);
		patdate.setHorizontalAlignment(SwingConstants.CENTER);
		patdate.addFocusListener(this);
		
		
		
		lblpatTime = new JLabel("Time of Appointment");
		lblpatTime.setBounds(400,80,140,20);
		
		
		ampm = new JComboBox();
		ampm.setBounds(470,105,50,30);
		ampm.setModel(new DefaultComboBoxModel(new String[] {"AM", "PM"}));   
		ampm.setBorder(null);
		
		pattime = new JFormattedTextField(ptime);
		pattime.setBounds(400,105,70,30);
		pattime.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblpatDesc = new JLabel("Description");
		lblpatDesc.setBounds(350,140,90,30);
		
		patDesc = new JTextArea();
		patDesc.setBounds(260,170,250,50);
		patDesc.setBorder(BorderFactory.createLineBorder(bgColor));
		
		panel3 = new JPanel();
		panel3.add(lblpatName);
		panel3.add(patname);
		panel3.add(lblspe);
		panel3.add(spe);
		panel3.add(savePat);
		panel3.add(editPat);
		panel3.add(delPat);
		panel3.add(lblpatStatus);
		panel3.add(patStatus);
		panel3.add(lblpatDate);
		panel3.add(frmt);
		panel3.add(patdate);
		panel3.add(lblpatTime);
		panel3.add(pattime);
		panel3.add(ampm);
		panel3.add(lblpatDesc);
		panel3.add(patDesc);
		panel3.setLayout(null);
		panel3.setBackground(Color.white);
		
		
		tp = new JTabbedPane();
		tp.addTab("Schedule", panel2);
		tp.addTab("Patient Information",panel1);
		tp.addTab("Appointments", panel3);
		tp.setMnemonicAt(0,KeyEvent.VK_C);
		tp.setMnemonicAt(1,KeyEvent.VK_A);
		tp.setMnemonicAt(2,KeyEvent.VK_P);
		tp.setBounds(210,10,775,300);
		tp.addChangeListener(this);
		pane.add(tp);
		
		
		pane.add(searchBtn = new JButton("Search Patient"));
		searchBtn.setBounds(210,320,120,30);
		searchBtn.setIcon(se);
		searchBtn.setFocusPainted(false);
		searchBtn.setBackground(mbColor);
		searchBtn.setForeground(Color.white);
		searchBtn.setBorder(null);
		searchBtn.setMnemonic(KeyEvent.VK_ENTER);
		searchBtn.addActionListener(this);
		
		pane.add(search = new JTextField());
		search.setBounds(330,320,300,30);
		search.setBorder(BorderFactory.createLineBorder(mbColor, 5));
		search.addFocusListener(this);
		
		pane.add(table = new JTable());
		table.setDefaultEditor(Object.class, null);
		table.addMouseListener(this);
		
		pane.add(table1 = new JTable());
		table1.setDefaultEditor(Object.class, null);
		table1.addMouseListener(this);
		
		pane.add(table2 = new JTable());
		table2.setDefaultEditor(Object.class, null);
		table2.addMouseListener(this);
		
		pane.add(table3 = new JTable());
		table3.setDefaultEditor(Object.class, null);
		table3.addMouseListener(this);
		
		pane.add(sp = new JScrollPane());
		sp.setViewportView(table);
		sp.setBounds(210,350,775,230);
		
		pane.add(loadPres = new JButton("See Prescriptions"));
		loadPres.setBounds(715, 320, 170, 30);
		loadPres.setFocusPainted(false);
		loadPres.setBackground(eblue);
		loadPres.setForeground(Color.white);
		loadPres.setBorder(null);
		loadPres.setIcon(eye);
		loadPres.addActionListener(this);
		
		
		pane.add(PatBtn = new JButton("Patient"));
		PatBtn.setBounds(0,100,200,50);
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
		
		pane.add(SetBtn = new JButton("Settings"));
		SetBtn.setBounds(0,150,200,50);
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
		SoBtn.setFont(menufont);
		SoBtn.setIcon(signout);
		SoBtn.setContentAreaFilled(false);
		SoBtn.setFocusPainted(false);
		SoBtn.setMnemonic(KeyEvent.VK_O);
		SoBtn.setOpaque(false);
		SoBtn.setForeground(Color.WHITE);
		SoBtn.addActionListener(this);
		SoBtn.addMouseListener(this);
		
		pane.add(loadvis = new JButton("Visitor Log"));
		loadvis.setBounds(785, 320, 100, 30);
		loadvis.setIcon(users);
		loadvis.setBackground(orange);
		loadvis.setFocusPainted(false);
		loadvis.setBorder(null);
		loadvis.setForeground(Color.white);
		loadvis.addActionListener(this);
		
		jpm = new JPopupMenu();
		jpm1 = new JPopupMenu();
		jpm2 = new JPopupMenu();
		jpm3 = new JPopupMenu();
		
		seepres = new JMenuItem("See Prescriptions");
		seepres.addActionListener(this);
		
		viewDt = new JMenuItem("View Details");
		viewDt.addActionListener(this);
		
		presc = new JMenuItem("Add Prescription");
		presc.addActionListener(this);
		
		patDel = new JMenuItem("Delete Appointment");
		patDel.addActionListener(this);
		
		patDel2 = new JMenuItem("Delete Prescription");
		patDel2.addActionListener(this);
		
		patView = new JMenuItem("View Prescription");
		patView.addActionListener(this);
		
		vis = new JMenuItem("Add Visitor");
		vis.addActionListener(this);

		// jpm3 edvis, delvis, table3
		edvis = new JMenuItem("Edit Visitor Details");
		edvis.addActionListener(this);
		
		delvis = new JMenuItem("Delete Visitor Details");
		delvis.addActionListener(this);
		
		table1.setComponentPopupMenu(jpm);
		table2.setComponentPopupMenu(jpm1);
		table3.setComponentPopupMenu(jpm3);
		
		jpm.add(presc);
		jpm.add(patDel);
		jpm.add(viewDt);
		
		jpm1.add(patView);
		jpm1.add(patDel2);
		
		jpm2.add(vis);
		jpm2.add(seepres);
		
		jpm3.add(edvis);
		jpm3.add(delvis);
		
		pane.add(side = new JPanel());
		side.setBackground(mbColor);
		side.setBounds(0,0,200,1000);
		pane.add(id = new JTextField());
		
	
		Clock();
	}
	
	public ImageIcon ResizeImage(String imgPath) {
		ImageIcon MyImage = new ImageIcon(imgPath);
		Image img = MyImage.getImage();
		Image newImage = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImage);
		return image;
	}
	
	public void refreshTable() {
		try {
			conn = dbCon.getConnection();
			String query = "select Fullname,Gender,Age,Illness,Allergies,`Date-in`,`Date-out`,Specialist,Address,Contact,`Status`,DisplayPhoto from patients";
			PreparedStatement ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshTable1() {
		try {
			conn = dbCon.getConnection();
			String query = "select Specialist,Patient,`Time`,`Date`,`Description`,Status from appointments";
			PreparedStatement ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			table1.setModel(DbUtils.resultSetToTableModel(rs));
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshTable2() {
		try {
			conn = dbCon.getConnection();
			String query = "select Specialist,Patient,`Time`,`Date`,`Description`,Status from appointments where userID = '" + userid.getText() + "'";
			PreparedStatement ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			table1.setModel(DbUtils.resultSetToTableModel(rs));
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshTable3() {
		try {
			conn = dbCon.getConnection();
			String query = "select PatientName, Prescription, `Date`, Age, Address, `PTRNo.`, `LICNo.`, UserID from medications where userID = '" + userid.getText() + "'";
			PreparedStatement ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			table2.setModel(DbUtils.resultSetToTableModel(rs));
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void refreshTable4() {
		try {
			conn = dbCon.getConnection();
			String query = "select id,Patient, VisitorName, `Time`, Relation, `Date`, Contact from visitorlog";
			PreparedStatement ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			table3.setModel(DbUtils.resultSetToTableModel(rs));
			sp.setViewportView(table3);
			ps.close();
			rs.close();
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}
	public void clearF() {
		fname.setText("");
		mname.setText("");
		lname.setText("");
		age.setText("");
		ot.setText("");
		din.setText("");
		dout.setText("");
		addr.setText("");
		contact.setText("");
		month.setText("");
		day.setText("");
		year.setText("");
		age.setText("");
		dp.setIcon(null);
		jcb1.setSelected(false);
		jcb2.setSelected(false);
		jcb3.setSelected(false);
	}

	public void search() {
		if (type.getText().contentEquals("Doctor")) {
			try {
				conn = dbCon.getConnection();
				String se = search.getText();
				String query = "select Specialist,Patient,`Time`,`Date`,`Description`,Status from appointments where Patient = '" + se + "'";
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
					 table1.setModel(DbUtils.resultSetToTableModel(rs));	
				ps.close();
				} catch (Exception e) {
					e.printStackTrace();
			}
		}
		if (type.getText().contentEquals("nurse")) {
			int x = tp.getSelectedIndex();
			if (x == 0) {
			try {
				conn = dbCon.getConnection();
				String se = search.getText();
				String query = "select Fullname,Gender,Age,Illness,Allergies,`Date-in`,`Date-out`,Specialist,Address,Contact,`Status`,DisplayPhoto from patients where Fullname = '" + se + "'";
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
					 table.setModel(DbUtils.resultSetToTableModel(rs));	
				ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (x == 1) {
			try {
				conn = dbCon.getConnection();
				String se = search.getText();
				String query = "select Specialist,Patient,`Time`,`Date`,`Description`,Status from appointments where Patient = '" + se + "'";
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
					 table1.setModel(DbUtils.resultSetToTableModel(rs));	
				ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == seepres) {
			int row = table.getSelectedRow();
			String name = table.getModel().getValueAt(row, 0).toString();
			
			try {
				conn = dbCon.getConnection();
				String se = search.getText();
				String query = "select * from medications where PatientName = '" + name + "'";
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					medications md = new medications();
					md.eid = rs.getString("userid");
					md.patname.setModel(md.prescriptions());
					md.patname.setSelectedItem(rs.getString("PatientName"));
					md.patname.setEditable(false);
					md.addr.setText(rs.getString("Address"));
					md.addr.setEditable(false);
					md.med.setText(rs.getString("Prescription"));
					md.med.setEditable(false);
					md.date.setText(rs.getString("Date"));
					md.date.setEditable(false);
					md.age.setText(rs.getString("Age"));
					md.age.setEditable(false);
					md.ptr.setText(rs.getString("PTRNo."));
					md.ptr.setEditable(false);
					md.lic.setText(rs.getString("LICNo."));
					md.lic.setEditable(false);
					md.uid.setText(rs.getString("UserID"));
					md.uid.setEditable(false);
					md.save.setVisible(false);
					md.setLocationRelativeTo(null);
					md.setLayout(null);
					md.setResizable(false);
					md.setVisible(true);
				}
				else {
					j.showMessageDialog(null, "No Prescriptions on this Patient", null, j.INFORMATION_MESSAGE);
				}
				ps.close();
				rs.close();
				} catch (Exception ee) {
					ee.printStackTrace();
				}
		}
		if (e.getSource() == edvis) {
			String[] stime;
			int row = table3.getSelectedRow();
			String id = table3.getModel().getValueAt(row, 0).toString();
			try {
				conn = dbCon.getConnection();
				String se = search.getText();
				String query = "select * from visitorlog where id = '" + id + "'";
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					stime = rs.getString("Time").split(" ");
					visitor vis = new visitor();
					vis.visID = rs.getString("id");
					vis.patname.setSelectedItem(rs.getString("Patient"));
					vis.visname.setText(rs.getString("visitorname"));
					vis.tov.setText(rs.getString("Time"));
					vis.ampm.setSelectedItem(stime[1]);
					vis.rel.setText(rs.getString("Relation"));
					vis.dov.setText(rs.getString("Date"));
					vis.cont.setText(rs.getString("Contact"));
					vis.setLocationRelativeTo(null);
					vis.setLayout(null);
					vis.setResizable(false);
					vis.setVisible(true);
				}
				
				ps.close();
				} catch (Exception ee) {
					ee.printStackTrace();
				}
		}
		if (e.getSource() == delvis) {
			int row = table3.getSelectedRow();
			String id = table3.getModel().getValueAt(row, 0).toString();
			String vis = table3.getModel().getValueAt(row,2).toString();
			
			int action = JOptionPane.showConfirmDialog(null, "Do you want to delete visitor " + vis , "Delete",
					JOptionPane.YES_NO_OPTION);
			if (action == 0) {
				try {
					String query = "delete from visitorlog where id = '" + id + "' ";
					ps = conn.prepareStatement(query);
					ps.execute();
					JOptionPane.showMessageDialog(null, "Data Deleted");
					ps.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				refreshTable4();
			}
			
		}
		if (e.getSource() == loadvis) {
			try {
				conn = dbCon.getConnection();
				String query = "select id,Patient, VisitorName, `Time`, Relation, `Date`, Contact from visitorlog";
				PreparedStatement ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				table3.setModel(DbUtils.resultSetToTableModel(rs));
				sp.setViewportView(table3);
				ps.close();
				rs.close();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
		
		
		if (e.getSource() == vis) {
			int row = table.getSelectedRow();
			String name = table.getModel().getValueAt(row, 0).toString();//name
			
			visitor vis = new visitor();
			vis.setLocationRelativeTo(null);
			vis.patname.setSelectedItem(name);
			vis.setLayout(null);
			vis.setResizable(false);
			vis.setVisible(true);
		}
		if (e.getSource() == viewDt) {
			int row = table1.getSelectedRow();
			String name = table1.getModel().getValueAt(row, 1).toString();//name
			
			try {
				conn = dbCon.getConnection();
				String q = "SELECT * FROM patients where Fullname = '" + name + "'";
				PreparedStatement ps = conn.prepareStatement(q);
				ResultSet rs = ps.executeQuery();
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
				rs.close();
				
				System.out.print(q);
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			
			
		}
		if (e.getSource() == patView) {
			int row = 0;
			
			row = table2.getSelectedRow();
			String name = table2.getModel().getValueAt(row, 0).toString();;
			try {
				conn = dbCon.getConnection();
				String q = "SELECT * FROM medications where PatientName = '" + name + "' and userID='" + userid.getText() + "'";
				PreparedStatement ps = conn.prepareStatement(q);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					medications md = new medications();
					md.eid = userid.getText();
					md.userid.setText(userid.getText());
					md.patname.setModel(md.prescriptions()); // declaration
					md.addr.setText(rs.getString("Address"));
					md.patname.setSelectedItem(rs.getString("PatientName"));
					md.med.setText(rs.getString("Prescription"));
					md.date.setText(rs.getString("Date"));
					md.age.setText(rs.getString("Age"));
					md.uid.setText(rs.getString("userid"));
					md.ptr.setText(rs.getString("PTRNo."));
					md.lic.setText(rs.getString("LICNo."));
					md.setLocationRelativeTo(null);
					md.setLayout(null);
					md.setResizable(false);
					md.setVisible(true);
				}
				ps.close();
				rs.close();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
		if (e.getSource() == patDel2) {
			
			int row = table2.getSelectedRow();
			if (row == -1) {
				j.showMessageDialog(null, "Please Select Patient", "Error", j.ERROR_MESSAGE);
			}
			else {
				String name = table2.getModel().getValueAt(row, 0).toString();
				String ids = null;
				try {
					conn = dbCon.getConnection();
					String q = "SELECT * FROM medications where patientname = '" + name + "'";
					PreparedStatement ps = conn.prepareStatement(q);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						ids = rs.getString("id");
					}
					ps.close();
					rs.close();
				}
				catch (Exception ee) {
					ee.printStackTrace();
				}
				int action = JOptionPane.showConfirmDialog(null, "Do you want to delete Prescription for " + name, "Delete",JOptionPane.YES_NO_OPTION);
				if (action == 0) {
					try {
						conn = dbCon.getConnection();
						String query = "delete from medications where id = '" + Integer.parseInt(ids) + "' ";
						ps = conn.prepareStatement(query);
						ps.execute();
						JOptionPane.showMessageDialog(null, "Data Deleted");
						ps.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					refreshTable3();
				}
			}
		}
		if (e.getSource() == loadPres) {
			refreshTable3();
			sp.setViewportView(table2);
		}
		if (e.getSource() == presc) {
			int row = table1.getSelectedRow();
			if (row == -1) {
				j.showMessageDialog(null, "Please Select Patient", "Error", j.ERROR_MESSAGE);
			}
			else {
				String name = table1.getModel().getValueAt(row, 1).toString();
				try {
					conn = dbCon.getConnection();
					String q = "SELECT * FROM patients where Fullname='" + name + "'";
					PreparedStatement ps = conn.prepareStatement(q);
					ResultSet rs = ps.executeQuery();
					
					while(rs.next()) {
						medications md = new medications();
						md.eid = userid.getText();
						md.userid.setText(userid.getText());
						md.uid.setText(userid.getText());
						md.patname.setModel(md.patients()); // declaration
						md.patname.setSelectedItem(name.toString());
						md.addr.setText(rs.getString("Address"));
						md.age.setText(rs.getString("Age"));
						md.setLocationRelativeTo(null);
						md.setLayout(null);
						md.setResizable(false);
						md.setVisible(true);
					}
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		}
		if (e.getSource() == patDel) {
			int row = table1.getSelectedRow();
			if (row == -1) {
				j.showMessageDialog(null, "Please Select Patient", "Error", j.ERROR_MESSAGE);
			}
			else {
				String name = table1.getModel().getValueAt(row, 1).toString();
				String ids = null;
				try {
					conn = dbCon.getConnection();
					String q = "SELECT * FROM appointments where patient = '" + name + "'";
					PreparedStatement ps = conn.prepareStatement(q);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						ids = rs.getString("id");
					}
					ps.close();
					rs.close();
				}
				catch (Exception ee) {
					ee.printStackTrace();
				}
				int action = JOptionPane.showConfirmDialog(null, "Do you want to delete Appointment for " + name, "Delete",JOptionPane.YES_NO_OPTION);
				if (action == 0) {
					try {
						conn = dbCon.getConnection();
						String query = "delete from appointments where id = '" + Integer.parseInt(ids) + "' ";
						ps = conn.prepareStatement(query);
						ps.execute();
						JOptionPane.showMessageDialog(null, "Data Deleted");
						ps.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					refreshTable2();
				}
			}
		}
		if (e.getSource() == savePat) {
			
			String da = patdate.getText();
			String[]strng = da.split("-");
			if (strng[0].contentEquals("01")) {
				stamp.setText("1");
			}
			if (strng[0].contentEquals("02")) {
				stamp.setText("2");
			}
			if (strng[0].contentEquals("03")) {
				stamp.setText("3");
			}
			if (strng[0].contentEquals("04")) {
				stamp.setText("4");
			}
			if (strng[0].contentEquals("05")) {
				stamp.setText("5");
			}
			if (strng[0].contentEquals("06")) {
				stamp.setText("6");
			}
			if (strng[0].contentEquals("07")) {
				stamp.setText("7");
			}
			if (strng[0].contentEquals("08")) {
				stamp.setText("8");
			}
			if (strng[0].contentEquals("09")) {
				stamp.setText("9");
			}
			if (strng[0].contentEquals("10")) {
				stamp.setText("10");
			}
			if (strng[0].contentEquals("11")) {
				stamp.setText("11");
			}
			if (strng[0].contentEquals("12")) {
				stamp.setText("12");
			}
			try {
				String str = spe.getSelectedItem().toString();
				String[] parts = str.split(" ");
				conn = dbCon.getConnection();
				String query = "SELECT * FROM employee where  eFname= '" + parts[1] + "' and eLname = '" + parts[2] + "'";
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {	
					selID = rs.getString("eID");
				}
				ps.close();
				rs.close();
			}catch (Exception ee) {
				ee.printStackTrace();
			}
			try {
				conn = dbCon.getConnection();
				String query = "INSERT INTO appointments (Specialist, Patient, `Time`, `Date`, `Description`, Status, userID, stamp) VALUES (?,?,?,?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(query);
				
				ps.setString(1, spe.getSelectedItem().toString());
				ps.setString(2, patname.getSelectedItem().toString());
				ps.setString(3, pattime.getText() + ampm.getSelectedItem().toString());
				ps.setString(4, patdate.getText());
				ps.setString(5, patDesc.getText());
				ps.setString(6, patStatus.getSelectedItem().toString());
				ps.setString(7, selID);
				ps.setString(8, stamp.getText());
				
				ps.execute();
				j.showMessageDialog(null, "Appointment Saved");
				
			}catch (Exception ee) {
				ee.printStackTrace();
			}
			refreshTable1();
		}
		if (e.getSource() == editPat) {
			String da = patdate.getText();
			String[]strng = da.split("-");
			if (strng[0].contentEquals("01")) {
				stamp.setText("1");
			}
			if (strng[0].contentEquals("02")) {
				stamp.setText("2");
			}
		
			if (strng[0].contentEquals("03")) {
				stamp.setText("3");
			}
			if (strng[0].contentEquals("04")) {
				stamp.setText("4");
			}
			if (strng[0].contentEquals("05")) {
				stamp.setText("5");
			}
			if (strng[0].contentEquals("06")) {
				stamp.setText("6");
			}
			if (strng[0].contentEquals("07")) {
				stamp.setText("7");
			}
			if (strng[0].contentEquals("08")) {
				stamp.setText("8");
			}
			if (strng[0].contentEquals("09")) {
				stamp.setText("9");
			}
			if (strng[0].contentEquals("10")) {
				stamp.setText("10");
			}
			if (strng[0].contentEquals("11")) {
				stamp.setText("11");
			}
			if (strng[0].contentEquals("12")) {
				stamp.setText("12");
			}

			try {
				String str = spe.getSelectedItem().toString();
				String[] parts = str.split(" ");
				conn = dbCon.getConnection();
				String query = "SELECT * FROM employee where  eFname= '" + parts[1] + "' and eLname = '" + parts[2] + "'";
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {	
					selID = rs.getString("eID");
				}
				ps.close();
				rs.close();
			}catch (Exception ee) {
				ee.printStackTrace();
			}
			try {
				conn = dbCon.getConnection();
				String query = "UPDATE appointments set Specialist=?, Patient=?, `Time`=?, `Date`=?, `Description`=?, Status=?, userID=?,stamp=? where id=?";
				ps = conn.prepareStatement(query);
				
				ps.setString(1, spe.getSelectedItem().toString());
				ps.setString(2, patname.getSelectedItem().toString());
				ps.setString(3, pattime.getText() + " " + ampm.getSelectedItem().toString());
				ps.setString(4, patdate.getText());
				ps.setString(5, patDesc.getText());
				ps.setString(6, patStatus.getSelectedItem().toString());
				ps.setString(7, selID);
				ps.setString(8, stamp.getText());
				ps.setString(9, patid.getText());
				ps.execute();
				JOptionPane.showMessageDialog(null, "Data Updated");
				
				ps.close();
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			refreshTable1();
			clearF(); 	
		}		
		if (e.getSource() == delPat) {
			
			int action = JOptionPane.showConfirmDialog(null, "Do you want to delete!", "Delete",JOptionPane.YES_NO_OPTION);
			if (action == 0) {
				try {
					conn = dbCon.getConnection();
					String query = "delete from appointments where id = '" + patid.getText() + "' ";
					ps = conn.prepareStatement(query);
					ps.execute();
					JOptionPane.showMessageDialog(null, "Data Deleted");
					ps.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				refreshTable1();
				clearF();
			}
			
		}
		if(e.getSource() == btn1) {
			try {
				conn = dbCon.getConnection();
				String query = "select Specialist,Patient,`Time`,`Date`,`Description`,Status from appointments where userId='" + userid.getText() + "'";
				PreparedStatement ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				ps.close();
				rs.close();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
		if(e.getSource() == btn2) {
			LocalDate today;
			today = LocalDate.now();
			String[] str = today.toString().split("-");
			try {
				conn = dbCon.getConnection();
				String se = search.getText();																						//MM			DD				YYYY
				String query = "select Specialist,Patient,`Time`,`Date`,`Description`,Status from appointments where `Date` = '" + str[1] + "-" + str[2] + "-" + str[0] + "' and userID='" + userid.getText() + "'";
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
					 table1.setModel(DbUtils.resultSetToTableModel(rs));	
				ps.close();
				} catch (Exception ee) {
					ee.printStackTrace();
					
				}
			
		}
		if(e.getSource() == btn3) {
	
			try {
				Calendar cal = new GregorianCalendar();
				String presentmonth = String.valueOf(cal.get(Calendar.MONTH)+1);//get the present month number
				conn = dbCon.getConnection();
				String query = "select Specialist,Patient,`Time`,`Date`,`Description`,Status from appointments where userId='" + userid.getText() + "' and stamp = '"+ presentmonth + "'";
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				table1.setModel(DbUtils.resultSetToTableModel(rs));
				ps.close();
				rs.close();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
		if(e.getSource() == btn4) {
			medications md = new medications();
			md.eid = userid.getText();
			md.userid.setText(userid.getText());
			md.uid.setText(userid.getText());
			md.patname.setModel(md.patients()); // declaration
			md.setLocationRelativeTo(null);
			md.setLayout(null);
			md.setResizable(false);
			md.setVisible(true);
			
		}	
		if (e.getSource() == searchBtn) {
			search();
		}
		if (e.getSource() == SetBtn) {
			j.showMessageDialog(null, "The Settings panel is dedicated for the Database Connection using TCP/IP.\nDue to the Community Quarantine, the Developers cannot test the said feature.\n\nFurthermore, the Developers will be upgrading this system for Future Use.\nHoping for your Consideration.");
	    }
		if (e.getSource() == load) {
			if (type.getText().contentEquals("Doctor")) {
				refreshTable2();
				sp.setViewportView(table1);
			}
			if (type.getText().contentEquals("nurse")) {
				int x = tp.getSelectedIndex();
				
				if (x == 0) {
					sp.setViewportView(table);
					refreshTable();
				}
				if (x == 1) {
					sp.setViewportView(table1);
					refreshTable1();
				}
			}
		}
		if (e.getSource() == PatBtn) {
			String n = j.showInputDialog(null, "Patient Name : (Firstname,Middlename,Lastname)", "Patient Information", j.PLAIN_MESSAGE);
			String name = StringFormatter.capitalizeWord(n);
			
			try{
			  conn = dbCon.getConnection();
	          String query="SELECT * FROM patients WHERE Fullname=?";
	          PreparedStatement ps=conn.prepareStatement(query);
	          ps.setString(1,n);
	          
	          ResultSet rs = ps.executeQuery();
	          
	          if(rs.next()){
	        	if(rs.getString("Fullname").equals(name)) {
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
	            }
	        	else{
	        		j.showMessageDialog(null, "Patient Doesn't have a record", "Error", j.ERROR_MESSAGE);
	        	}
	          ps.close();
			}catch (Exception ee) {
				ee.printStackTrace();
			}
		}
		if(e.getSource() == SoBtn) {
		  this.dispose();
		  loginForm frame = new loginForm();	          
          frame.setUndecorated(true);
          frame.setVisible(true);
          frame.setLocationRelativeTo(null);
		}
		if(e.getSource() == upload) {
			int result = fileChooser.showSaveDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				String path = selectedFile.getAbsolutePath();
				dp.setIcon(ResizeImage(path));
				s = path;
				n = "new";
			} else if (result == JFileChooser.CANCEL_OPTION) {
			}
		}
		if (e.getSource() == del) {
			int action = JOptionPane.showConfirmDialog(null, "Do you want to delete!", "Delete",JOptionPane.YES_NO_OPTION);
			if (action == 0) {
				try {
					conn = dbCon.getConnection();
					String query = "delete from patients where id = '" + patid.getText() + "' ";
					ps = conn.prepareStatement(query);
					ps.execute();
					JOptionPane.showMessageDialog(null, "Data Deleted");
					ps.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				refreshTable();
				clearF();
			}
		}
		if(e.getSource() == save) {
			String lns = null;
			//true, false, false
			if (jcb1.isSelected() && jcb2.isSelected() == false && jcb3.isSelected() == false) {
				if (ot.getText().contentEquals("")) {
					lns = "Fever";
				}
				else {
					lns = "Fever, ";
				}
			}
			//false, true, false
			if (jcb1.isSelected() == false && jcb2.isSelected() && jcb3.isSelected() == false) {
				if (ot.getText().contentEquals("")) {
					lns = "Headache";
				}
				else {
					lns = "Headache, ";
				}
			}
			//false, false, true
			if (jcb1.isSelected() == false && jcb2.isSelected() == false && jcb3.isSelected()) {
				if (ot.getText().contentEquals("")) {
					lns = "Cough";
				}
				else {
					lns = "Cough, ";
				}
			}
			//true, true, false
			if (jcb1.isSelected() && jcb2.isSelected() && jcb2.isSelected() == false) {
				if (ot.getText().contentEquals("")) {
					lns = "Fever, Headache";
				}
				else {
					lns = "Fever, Headache, ";
				}
			}
			//false, true, true
			if (jcb1.isSelected() == false && jcb2.isSelected() && jcb3.isSelected()) {
				if (ot.getText().contentEquals("")) {
					lns = "Headache, Cough";
				}
				else {
					lns = "Headache, Cough, ";
				}
			}
			//true, false, true
			if (jcb1.isSelected() && jcb2.isSelected() == false && jcb3.isSelected()) {
				if (ot.getText().contentEquals("")) {
					lns = "Fever, Cough";
				}
				else {
					lns = "Fever, Cough, ";
				}
			}
			//true,true,true
			if (jcb1.isSelected() && jcb2.isSelected() && jcb3.isSelected()) {
				if (ot.getText().contentEquals("")) {
					lns = "Fever, Headache, Cough";
				}
				else {
					lns = "Fever, Headache, Cough, ";
				}
			}
			//false, false, false
			if (jcb1.isSelected() == false && jcb2.isSelected() == false && jcb3.isSelected() == false) {
				lns = "";
			}
			
			if (fname.getText().contentEquals("") || mname.getText().contentEquals("") || lname.getText().contentEquals("") || month.getText().contentEquals("MM") || day.getText().contentEquals("DD") || year.getText().contentEquals("YYYY") || age.getText().contentEquals("") || din.getText().contentEquals("__-__-____") || addr.getText().contentEquals("") || contact.getText().contentEquals("____-___-____")) {
			  j.showMessageDialog(null, "Please complete all the fields needed");
			  fname.requestFocus();
			}
			if(dp.getIcon() == null) {
				j.showMessageDialog(null, "Please add Photo");
				
			}
			else {
				try {
					  conn = dbCon.getConnection();
					  String bday = month.getText() + "-" + day.getText() + "-" + year.getText();
			          String query="INSERT INTO patients (fname,lname,mname,gender,birthdate,age,illness,allergies,`date-in`,`date-out`, specialist,address,contact,status,displayphoto) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			          PreparedStatement ps = conn.prepareStatement(query);
			          
			          InputStream img = new FileInputStream(new File(s));
			          ps.setString(1, fname.getText());
			          ps.setString(2, lname.getText());
			          ps.setString(3, mname.getText());
			          ps.setString(4, gender.getSelectedItem().toString());
			          ps.setString(5, bday);
			          ps.setString(6, age.getText());
			          ps.setString(7, lns + ot.getText());
			          ps.setString(8, aller.getSelectedItem().toString());
			          ps.setString(9, din.getText().toString());
			          ps.setString(10, dout.getText());
			          ps.setString(11, doc.getSelectedItem().toString());
			          ps.setString(12, addr.getText());
			          ps.setString(13, contact.getText());
			          ps.setString(14, stat.getSelectedItem().toString());
			          ps.setBlob(15, img);
			          ps.execute();
					JOptionPane.showMessageDialog(null, "Data Saved");
					ps.close();
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex);
				}
				dp.setIcon(null);
				refreshTable();
				clearF();
			}
		}	
		
		if(e.getSource() == edit) {
			// declare a blob that serves as local storage
			Blob image = null;
			String lns = null;
			//true, false, false
			if (jcb1.isSelected() && jcb2.isSelected() == false && jcb3.isSelected() == false) {
				if (ot.getText().contentEquals("")) {
					lns = "Fever";
				}
				else {
					lns = "Fever, ";
				}
			}
			//false, true, false
			if (jcb1.isSelected() == false && jcb2.isSelected() && jcb3.isSelected() == false) {
				if (ot.getText().contentEquals("")) {
					lns = "Headache";
				}
				else {
					lns = "Headache, ";
				}
			}
			//false, false, true
			if (jcb1.isSelected() == false && jcb2.isSelected() == false && jcb3.isSelected()) {
				if (ot.getText().contentEquals("")) {
					lns = "Cough";
				}
				else {
					lns = "Cough, ";
				}
			}
			//true, true, false
			if (jcb1.isSelected() && jcb2.isSelected() && jcb2.isSelected() == false) {
				if (ot.getText().contentEquals("")) {
					lns = "Fever, Headache";
				}
				else {
					lns = "Fever, Headache, ";
				}
			}
			//false, true, true
			if (jcb1.isSelected() == false && jcb2.isSelected() && jcb3.isSelected()) {
				if (ot.getText().contentEquals("")) {
					lns = "Headache, Cough";
				}
				else {
					lns = "Headache, Cough, ";
				}
			}
			//true, false, true
			if (jcb1.isSelected() && jcb2.isSelected() == false && jcb3.isSelected()) {
				if (ot.getText().contentEquals("")) {
					lns = "Fever, Cough";
				}
				else {
					lns = "Fever, Cough, ";
				}
			}//true, true, true
			if (jcb1.isSelected() && jcb2.isSelected() && jcb3.isSelected()) {
				if (ot.getText().contentEquals("")) {
					lns = "Fever, Headache, Cough";
				}
				else {
					lns = "Fever, Headache, Cough, ";
				}
			}
			//false, false, false
			if (jcb1.isSelected() == false && jcb2.isSelected() == false && jcb3.isSelected() == false) {
				lns = "";
			}
			try {
				conn = dbCon.getConnection();
				String query = "SELECT * From patients where id = 1";
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				while(rs.next()) {
				 image = rs.getBlob("DisplayPhoto");
				} 
		         ps.execute();
		         ps.close();
			}
			catch (Exception ex) {
				ex.printStackTrace();
				j.showMessageDialog(null, ex);
			}
			if (n.contentEquals("new")) {

				try {
					  conn = dbCon.getConnection();
					  int row = table.getSelectedRow();
					  String id = (table.getModel().getValueAt(row, 0).toString());
					  String bday = month.getText() + "-" + day.getText() + "-" + year.getText();
			          String query="UPDATE patients set fname = ?,lname = ?,mname = ?,gender = ?, birthdate = ?, age = ?,illness = ?,allergies = ?,`date-in` = ?,`date-out` = ?, specialist = ?,address = ?,contact = ?,status = ?,displayphoto = ? where Fullname = '"+ fname.getText() + " " + mname.getText() + " " + lname.getText() +"';";
			          ps = conn.prepareStatement(query);
				  	  InputStream img = new FileInputStream(new File(s));
			          ps.setString(1, fname.getText());
			          ps.setString(2, lname.getText());
			          ps.setString(3, mname.getText());
			          ps.setString(4, gender.getSelectedItem().toString());
			          ps.setString(5, bday);
			          ps.setString(6, age.getText());
			          ps.setString(7, lns + ot.getText());
			          ps.setString(8, aller.getSelectedItem().toString());
			          ps.setString(9, din.getText());
			          ps.setString(10, dout.getText());
			          ps.setString(11, doc.getSelectedItem().toString());
			          ps.setString(12, addr.getText());
			          ps.setString(13, contact.getText());
			          ps.setString(14, stat.getSelectedItem().toString());
			          ps.setBlob(15, img);
				      
			          ps.execute();
			          ps.close();
			          j.showMessageDialog(null, "Data Updated", null, j.INFORMATION_MESSAGE);
			          j.showMessageDialog(null, "New Image has been Uploaded", null, j.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					ex.printStackTrace();
					j.showMessageDialog(null, ex);
				}
				refreshTable();
				clearF();
			}	
			if (n.contentEquals("old")) {
				try {
					  conn = dbCon.getConnection();
					  int row = table.getSelectedRow();
					  String id = (table.getModel().getValueAt(row, 0).toString());
					  String bday = month.getText() + "-" + day.getText() + "-" + year.getText();
			          String query="UPDATE patients set fname = ?,lname = ?,mname = ?,gender = ?, birthdate = ?, age = ?,illness = ?,allergies = ?,`date-in` = ?,`date-out` = ?, specialist = ?,address = ?,contact = ?,status = ?,displayphoto = ? where Fullname = '"+ fname.getText() + " " + mname.getText() + " " + lname.getText() +"';";
			          ps = conn.prepareStatement(query);
			          Blob img = image;
			          ps.setString(1, fname.getText());
			          ps.setString(2, lname.getText());
			          ps.setString(3, mname.getText());
			          ps.setString(4, gender.getSelectedItem().toString());
			          ps.setString(5, bday);
			          ps.setString(6, age.getText());
			          ps.setString(7, lns + ot.getText());
			          ps.setString(8, aller.getSelectedItem().toString());
			          ps.setString(9, din.getText());
			          ps.setString(10, dout.getText());
			          ps.setString(11, doc.getSelectedItem().toString());
			          ps.setString(12, addr.getText());
			          ps.setString(13, contact.getText());
			          ps.setString(14, stat.getSelectedItem().toString());
			          ps.setBlob(15, img);
				      
			          ps.execute();
			          ps.close();
			          j.showMessageDialog(null, "Data Updated", null, j.INFORMATION_MESSAGE);
			          
				} catch (Exception ex) {
					ex.printStackTrace();
					j.showMessageDialog(null, ex);
				}
				refreshTable();
				clearF();
			}
		}
	}
	public static void main (String[] args) {}
	@SuppressWarnings("static-access")
	@Override
	public void mouseClicked(MouseEvent mc) {
		if (mc.getSource() == table1) {

			//DOCTOR
			if (type.getText().contentEquals("Doctor")) {
				try {
					conn = dbCon.getConnection();
					int row = table1.getSelectedRow();
					String patname = (table1.getModel().getValueAt(row, 1).toString());
					String query = "SELECT * FROM appointments where Patient = '" + patname + "' ";
					ps = conn.prepareStatement(query);
					rs = ps.executeQuery();
					while (rs.next()) {
						ptn.setText(rs.getString("Patient"));
						ptrem.setText(rs.getString("Status"));
						ptda.setText(rs.getString("Date"));
						ptta.setText(rs.getString("Time"));
						ptdesc.setText(rs.getString("Description"));
					}
					ps.close();
					rs.close();
				}
				catch (Exception e) {
					e.printStackTrace();
					j.showMessageDialog(null, e);
				}
			}
			//NURSE
			if (type.getText().contentEquals("nurse")) {
				try {
					conn = dbCon.getConnection();
					int row = table1.getSelectedRow();
					String patName = (table1.getModel().getValueAt(row, 1).toString());
					String tme = (table1.getModel().getValueAt(row, 2).toString());
					String spec = (table1.getModel().getValueAt(row, 0).toString());
					String date = (table1.getModel().getValueAt(row, 3).toString());
					String desc = (table1.getModel().getValueAt(row, 4).toString());
					
					String query = "SELECT * From appointments where Patient = '" + patName + "' and `Time` = '" + tme + "' and specialist = '" + spec + "' and `Date` = '" + date + "' and Description ='" + desc + "'" ;
					PreparedStatement ps = conn.prepareStatement(query);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						String time = rs.getString("Time");
						String[] str = time.split(" ");
						
						patid.setText(rs.getString("id"));
						tp.setSelectedIndex(1);
						patname.setSelectedItem(patName);
						spe.setSelectedItem(rs.getString("Specialist"));
						ampm.setSelectedItem(str[1]);
						patStatus.setSelectedItem(rs.getString("Status"));
						pattime.setText(rs.getString("Time"));
						patdate.setText(rs.getString("Date"));
						patDesc.setText(rs.getString("Description"));
					}
					ps.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(mc.getSource() == table) {
			try {
				jcb1.setSelected(false);
				jcb2.setSelected(false);
				jcb3.setSelected(false);
				ot.setText("");
				dout.setText("");
				
				conn = dbCon.getConnection();
				int row = table.getSelectedRow();
				String rowid = (table.getModel().getValueAt(row, 0).toString());
				n = "old";
				id.setText(rowid);
				String query = "SELECT * FROM patients where Fullname = '" + rowid + "' ";
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				while (rs.next()) {
					//patid.setText(rs.getString("id"));
					fname.setText(rs.getString("fname"));
					mname.setText(rs.getString("mname"));
					lname.setText(rs.getString("lname"));
					gender.setSelectedItem(rs.getString("gender"));
					age.setText(rs.getString("age"));
					addr.setText(rs.getString("address"));
					aller.setSelectedItem(rs.getString("allergies"));
					din.setText(rs.getString("date-in"));
					dout.setText(rs.getString("date-out"));
					doc.setSelectedItem(rs.getString("specialist"));
					addr.setText(rs.getString("address"));
					contact.setText(rs.getString("contact"));
					stat.setSelectedItem(rs.getString("status"));
					byte[] img = rs.getBytes("displayphoto");
					ImageIcon image = new ImageIcon(img);
					Image im = image.getImage();
					Image myImg = im.getScaledInstance(dp.getWidth(), dp.getHeight(), Image.SCALE_SMOOTH);
					ImageIcon newImage = new ImageIcon(myImg);
					dp.setIcon(newImage);

					//get the value of bday format [mm-dd-yyyy]
					String temp = rs.getString("birthdate").toString();
					
					//split them into an array separated by "-"  [0,1,2] == [mm,dd,yy]
					String[] parts = temp.split("-");
					//declaration
					String mm = parts[0];
					String dd = parts[1];
					String yyyy = parts[2];
					//set
					month.setText(mm);
					day.setText(dd);
					year.setText(yyyy);
					
					try {
						String a = rs.getString("Illness").toString();
						String[] ill = a.split(", ");
						int listener = 0;
	
						int ha = a.indexOf("Headache");
						int fev = a.indexOf("Fever");
						int co = a.indexOf("Cough");
				
						//not false = true
						if (fev != -1) {
							jcb1.setSelected(true);
							listener = listener + 1;
						}
						else {
							jcb1.setSelected(false);
						}
						if (ha != -1) {
							jcb2.setSelected(true);
							listener = listener + 1;
						}
						else {
							jcb2.setSelected(false);
						}
						if (co != -1) {
							jcb3.setSelected(true);
							listener = listener + 1;
						}
						else {
							jcb3.setSelected(false);
						}
						if (listener == 0) {
							ot.setText(rs.getString("Illness"));
						}
						
					}catch (Exception e) {
						j.showMessageDialog(null, e);
					}
				}
		    } catch (Exception e) {
			     e.printStackTrace();
		    }
		}
	}
	@Override
	public void mouseEntered(MouseEvent me) {
		if (me.getSource() == btn1) {
			btn1.setBackground(Color.decode("#075591"));
		}
		if (me.getSource() == btn2) {
			btn2.setBackground(Color.decode("#8a4c15"));
		}
		if (me.getSource() == btn3) {
			btn3.setBackground(Color.decode("#a8890a"));
		}
		if (me.getSource() == btn4) {
			btn4.setBackground(Color.decode("#464a4a"));
		}
		if(me.getSource() == PatBtn) {
            PatBtn.setForeground(Color.decode("#34495e"));
            PatBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.BED, 20,Color.decode("#34495e")));
            PatBtn.setOpaque(true);
    		PatBtn.setBackground(Color.white);
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
		if (mex.getSource() == btn1) {
			btn1.setBackground(eblue);
		}
		if (mex.getSource() == btn2) {
			btn2.setBackground(orange);
		}
		if (mex.getSource() == btn3) {
			btn3.setBackground(yellow);
		}
		if (mex.getSource() == btn4) {
			btn4.setBackground(gray);
		}
		if(mex.getSource() == PatBtn) {
			PatBtn.setForeground(Color.white);
        	PatBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.BED, 20,Color.white));
 	        PatBtn.setOpaque(false);
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

	@SuppressWarnings("static-access")
	@Override
	public void focusGained(FocusEvent fg) {
		if (fg.getSource() == patdate) {
			LocalDate today;
			today = LocalDate.now();//ymd
			String[] str = String.valueOf(today).split("-");
			patdate.setText(str[1] + "-" + str[2] + "-" + str[0]);		
		}
		if (fg.getSource() == jvp) {
			table.setModel(null);
		}
		//focusgained on formatted textfield date-in (Var name:din)
		if(fg.getSource() == din) {
			 //get current date
			 LocalDate t = LocalDate.now();
			 //conversion to string
			 String today = String.valueOf(t.getMonthValue()) + "-" + String.valueOf(t.getDayOfMonth()) + "-" + String.valueOf(t.getYear());
			 // if month number is greater than 10 and day number is greater than 10 [normal print]
			 
			 if (t.getMonthValue() >= 10 && t.getDayOfMonth() >= 10) {
				 din.setText(today);
			 }
			 // if month number is less than 10 and day number is greater than 10 [add string 0 in the month value]
			 if(t.getMonthValue() <= 10 && t.getDayOfMonth() >= 10) {
				 din.setText("0"+today.toString()); 
			 }
			 // if month number is less than 10 and day number is less than 10 [add string 0 in the month and day value]
			 if(t.getMonthValue() <= 10 && t.getDayOfMonth() <= 10) {
				 din.setText("0"+ t.getMonthValue() + "-0" + t.getDayOfMonth() + "-" + t.getYear()); 
			 }
			 //for formatted textfield date in Format: [MM-DD-YYYY]
		}
		if (fg.getSource() == day) {
			if(month.getText().contentEquals("MM")) {
				j.showMessageDialog(null, "Enter valid Month Number [1 - 12]");
			}
		}
		if (fg.getSource() == year) {
			if(day.getText().contentEquals("DD") && month.getText().contentEquals("MM")) {
				j.showMessageDialog(null, "Enter valid Day Number");
			}
		}
	}
	@SuppressWarnings("static-access")
	@Override
	public void focusLost(FocusEvent fl) {
		if (fl.getSource() == patdate) {
			String str = patdate.getText().toString();
			String[] a = str.split("-");
			int mo = Integer.parseInt(a[0]);
			int da = Integer.parseInt(a[1]);
			if (mo >12) {
				j.showMessageDialog(null, "Invalid Month", "Invalid Data", j.ERROR_MESSAGE);
				patdate.setText("");
				patdate.requestFocus();
			}
			if (da > 31) {
				j.showMessageDialog(null, "Invalid Day", "Invalid Data", j.ERROR_MESSAGE);	
				patdate.setText("");
				patdate.requestFocus();
			}
		}
		if(fl.getSource() == fname) {
			fname.setText(StringFormatter.capitalizeWord(fname.getText()));
		}
		if(fl.getSource() == mname) {
			mname.setText(StringFormatter.capitalizeWord(mname.getText()));
		}
		if (fl.getSource() == lname) {
			lname.setText(StringFormatter.capitalizeWord(lname.getText()));
		}
		if (fl.getSource() == ot) {
			ot.setText(StringFormatter.capitalizeWord(ot.getText()));
		}
		if (fl.getSource() == addr) {
			addr.setText(StringFormatter.capitalizeWord(addr.getText()));
		}
		if (fl.getSource() == search) {
			search.setText(StringFormatter.capitalizeWord(search.getText()));
		}
		if (fl.getSource() == month) {
			//Conversion string to int
			int x = Integer.parseInt(month.getText());
			if (x > 12 || x == 00) {
				j.showMessageDialog(null, "Invalid Month (01 - 12)");
				month.setText("");
			}
		}
		if(fl.getSource() == day) {
			if (day.getText().contentEquals("") && month.getText().contentEquals("") && year.getText().contentEquals("")) {
			}
			else {
				x = Integer.parseInt(day.getText());
				m = Integer.parseInt(month.getText());
				//Filters the number of days based on month (01 = January...)
				if(m == 01 || m == 03 || m == 05 || m == 07 || m == 8 || m == 10 || m == 12 ) {
					if (x >= 32) {
						j.showMessageDialog(null, "Invalid Day Number of the Month:" + m  + " [01 - 31]");
						day.setText("");
					}
				}
				if(m == 04 || m == 06 || m == 9 || m == 11) {
					if (x >= 31) {
						j.showMessageDialog(null, "Invalid Day For the Month(01 - 30)");
						day.setText("");
					}
				}
				if(m == 02 && x >= 30) {
					j.showMessageDialog(null, "Invalid Day For the month of February (01 - 29)");
					day.setText("");
				}
			}					
		}
		if (fl.getSource() == year) {
			int x,y,z;
			LocalDate today, bday;
			Period p;
			
			x = Integer.parseInt(year.getText());
			y = Integer.parseInt(month.getText());
			z = Integer.parseInt(day.getText());
			
			today = LocalDate.now();
			bday = LocalDate.of(x, y, z);
			p = Period.between(bday, today);
			int a = p.getYears();
			String finalage = String.valueOf(a);
			age.setText(finalage);
		}
	}
	@SuppressWarnings("static-access")
	@Override
	public void keyPressed(KeyEvent kp) {
		if (kp.getSource() == age) {
			String value = age.getText();
			int l = value.length();
			
			if (kp.getKeyChar() >= '0' && kp.getKeyChar() <= '9') {
			
			}else {
				j.showMessageDialog(null, "Enter Numeric Digits Only (0-9)");
				age.setText("");
				age.requestFocus();
			}
		}
		
		
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@SuppressWarnings("static-access")
	@Override
	public void keyTyped(KeyEvent kt) {
		if(kt.getSource() == age) {
			if(age.getText().length() >= 3 ) {
				j.showMessageDialog(null, "Invalid Input of Age" , "Error", j.ERROR_MESSAGE);
				age.setText("");
			}
			if (kt.getSource() == search) {}
		}
	}
	@Override
	public void stateChanged(ChangeEvent sc) {
		if (sc.getSource() == tp) {
			if (type.getText().contentEquals("Doctor")) {
					sp.setViewportView(table1);
			}
			if (type.getText().contentEquals("nurse")) {
				int x = Integer.parseInt("" + tp.getSelectedIndex());
				
				if (x == 0) {
					sp.setViewportView(table);
				}
				if (x == 1) {
					sp.setViewportView(table1);
				}
			}
		}
	}
}