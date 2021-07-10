package forms;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import conn.dbCon;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import net.proteanit.sql.DbUtils;

@SuppressWarnings("serial")	
public class adminForm extends JFrame implements ActionListener, MouseListener, KeyListener, FocusListener {
	String n, id;
	static JOptionPane j;
	public Connection conn = null;
	public Statement st = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	public DefaultTableModel dm = null;
	
	String s;
	public JPanel side, whitebg;
	
	public JButton AdBtn, PatBtn, EmpBtn, SetBtn, SoBtn, clearBtn;
	public JLabel lblFname, lblMname, lblLname, lblAddr, lblPos, lblCont, lblBday, lblUname, lblPw, lblEid, dp, lblSearch, lblClock;
	public JTextField fname, mname, lname, addr, pos, cont, uname, eid, search, txtID;
	public MaskFormatter mf, mmf, ddf, yyf, contf;
	public JPasswordField pw;
	public JFormattedTextField tfeID, month, day, year, contact;
	public JButton saveBtn, editBtn, delBtn, searchBtn, upload, load;
	public JTable usertable;
	public JScrollPane scrollPane;

	@SuppressWarnings("rawtypes")
	public JComboBox cBox;
	public JFileChooser fileChooser;
	public FileNameExtensionFilter filter;
	
	
	Font menufont = new Font("MADE Evolve Sans", Font.BOLD, 18);
	Font f1 = new Font("MADE Evolve Sans", Font.BOLD, 12);
	Font f2 = new Font("MADE Evolve Sans", Font.BOLD, 15);
	Font f3 = new Font("MADE Evolve Sans", Font.BOLD, 10);
	
	Color bgColor = Color.decode("#34495e");
	Color mbColor = Color.decode("#16a085");
	Color yellow = Color.decode("#f1c40f");
	Color blue = Color.decode("#2980b9");
	Color green = Color.decode("#2ecc71");
	Color orange = Color.decode("#e67e22");
	Color red = Color.decode("#e74c3c");

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
						int hour = cal.get(Calendar.HOUR);
						lblClock.setText("Date: " + (month+1) + " / " + day + " / " + year + " Time: " + hour + " : "
								+ minute + " : " + second);
						sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		clock.start();
	}
	
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	public adminForm() {
		//Frame
		setTitle("Accounts");
		setSize(1000, 650);
		Container pane = getContentPane(); // get the container
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pane.setBackground(bgColor);
		
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon pic = IconFontSwing.buildIcon(FontAwesome.USER_CIRCLE, 80,Color.DARK_GRAY);
		Icon save = IconFontSwing.buildIcon(FontAwesome.PLUS, 20,Color.white);
		Icon del = IconFontSwing.buildIcon(FontAwesome.TRASH, 20,Color.white);
		Icon edit = IconFontSwing.buildIcon(FontAwesome.PENCIL_SQUARE_O, 20,Color.white);
		Icon se = IconFontSwing.buildIcon(FontAwesome.SEARCH, 15,mbColor);
		Icon up = IconFontSwing.buildIcon(FontAwesome.UPLOAD, 20,Color.white);
		Icon refresh = IconFontSwing.buildIcon(FontAwesome.LIST, 20,Color.white);
		
		Icon admin = IconFontSwing.buildIcon(FontAwesome.USER_CIRCLE, 20,Color.white);
		Icon patient = IconFontSwing.buildIcon(FontAwesome.BED, 20,Color.white);
		Icon employee = IconFontSwing.buildIcon(FontAwesome.USER_MD, 20,Color.white);
		Icon signout = IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, 20,Color.white);
		Icon settings = IconFontSwing.buildIcon(FontAwesome.COG, 20,Color.white);
			
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));   
		filter = new FileNameExtensionFilter("PNG JPG AND JPEG", "png", "jpeg", "jpg");      
		fileChooser.addChoosableFileFilter(filter); 
	
		pane.add(clearBtn = new JButton("Clear Fields"));
		
		//scrollpane & table
		pane.add(scrollPane  = new JScrollPane());
		
		//ComboBox
		pane.add(cBox = new JComboBox());
		
		//saveBtn, editBtn, delBtn, searchBtn, upload, load;
		pane.add(saveBtn = new JButton("Save"));
		pane.add(editBtn = new JButton("Update"));
		pane.add(delBtn = new JButton("Delete"));
		pane.add(searchBtn = new JButton("Search"));
		pane.add(upload = new JButton());
		pane.add(load = new JButton("Load Table"));
		
		//JTextField fname, mname, lname, addr, pos, cont, uname, pw, eid, search, mm, yyyy, dd, txtID;
		pane.add(fname = new JTextField());
		pane.add(mname = new JTextField());
		pane.add(lname = new JTextField());
		pane.add(addr = new JTextField());
		pane.add(pos = new JTextField());
		pane.add(cont = new JTextField());
		pane.add(uname = new JTextField());
		pane.add(pw = new JPasswordField());
		pane.add(eid = new JTextField());
		pane.add(search = new JTextField());
		pane.add(txtID = new JTextField());
		
		//lblFname, lblMname, lblLname, lblAddr, lblPos, lblCont, lblBday, lblUname, lblPw, lblEid, dp, lblClock;
		pane.add(lblFname = new JLabel("Firstname: "));
		pane.add(lblMname = new JLabel("Middlename: "));
		pane.add(lblLname = new JLabel("Lastname: "));
		pane.add(lblAddr = new JLabel("Address: "));
		pane.add(lblPos = new JLabel("Job Position: "));
		pane.add(lblCont = new JLabel("Contact No.: "));
		pane.add(lblBday = new JLabel("Birthdate: "));
		pane.add(lblUname = new JLabel("Username: "));
		pane.add(lblPw = new JLabel("Password: "));
		pane.add(lblEid = new JLabel("Employee ID: "));
		pane.add(dp = new JLabel("IMAGE"));
		
		pane.add(lblClock = new JLabel());
		//AdBtn, PatBtn, EmpBtn, SetBtn, SoBtn, hidBtn;
		pane.add(AdBtn = new JButton("Admin"));
		pane.add(PatBtn = new JButton("Patient"));
		pane.add(EmpBtn = new JButton("Employee"));
		pane.add(SetBtn = new JButton("Settings"));
		pane.add(SoBtn = new JButton("Sign Out"));
		
		pane.add(side = new JPanel());
		pane.add(whitebg = new JPanel());
		
		clearBtn.setBounds(540,65,100,30);
		clearBtn.setFocusPainted(false);
		clearBtn.setContentAreaFilled(false);
		clearBtn.setForeground(Color.white);
		clearBtn.addActionListener(this);
		
		
		cBox.setModel(new DefaultComboBoxModel(new String[] {"id", "eId", "eFname", "eLname", "eJp"}));   
		cBox.setBackground(Color.white);
		cBox.setForeground(Color.DARK_GRAY);
		cBox.setBounds(540,20,100,30); 
		
		
		load.setBounds(870,65,95,30);
		load.setIcon(refresh);
		load.setContentAreaFilled(false);
		load.setFocusPainted(false);
		load.setBorder(null);
		load.setForeground(Color.white);
		load.addActionListener(this);
		load.addMouseListener(this);
		
		upload.setBounds(490,20,30,30);
		upload.setIcon(up);
		upload.setBorder(null);
		upload.setFont(f3);
		upload.setForeground(Color.white);
		upload.setBackground(mbColor);
		upload.setFocusPainted(false);
		upload.addActionListener(this);
		
		saveBtn.setBounds(220,555,100,40);
		saveBtn.setBorder(null);
		saveBtn.setFocusPainted(false);
		saveBtn.setBorder(null);
		saveBtn.setBackground(green);
		saveBtn.setForeground(Color.white);
		saveBtn.setIcon(save);
		saveBtn.addActionListener(this);
		
		editBtn.setBounds(320,555,100,40);
		editBtn.setBorder(null);
		editBtn.setFocusPainted(false);
		editBtn.setBorder(null);
		editBtn.setBackground(blue);
		editBtn.setForeground(Color.white);
		editBtn.setIcon(edit);
		editBtn.addActionListener(this);
		
		delBtn.setBounds(420,555,100,40);
		delBtn.setBorder(null);
		delBtn.setFocusPainted(false);
		delBtn.setBorder(null);
		delBtn.setBackground(red);
		delBtn.setForeground(Color.white);
		delBtn.setIcon(del);
		delBtn.addActionListener(this);
		
		searchBtn.setBounds(860,20,105,30);
		searchBtn.setBorder(null);
		searchBtn.setBackground(Color.white);
		searchBtn.setForeground(mbColor);
		searchBtn.setFocusPainted(false);
		searchBtn.setFont(f2);
		searchBtn.setMnemonic(KeyEvent.VK_S);
		searchBtn.addActionListener(this);
		
		try {
			mf = new MaskFormatter("##-###");
			} catch (ParseException e) {    
				e.printStackTrace();   
		}   
		mf.setPlaceholderCharacter('_'); 
		
		try {
			mmf = new MaskFormatter("##");
			} catch (ParseException e) {    
				e.printStackTrace();   
		}   
		mmf.setPlaceholderCharacter('M'); 
		
		try {
			ddf = new MaskFormatter("##");
			} catch (ParseException e) {    
				e.printStackTrace();   
		}   
		ddf.setPlaceholderCharacter('D');
		
		try {
			yyf = new MaskFormatter("####");
			} catch (ParseException e) {    
				e.printStackTrace();   
		}   
		yyf.setPlaceholderCharacter('Y'); 
		
		try {
			contf = new MaskFormatter("####-###-####");
			} catch (ParseException e) {    
				e.printStackTrace();   
		}   
		contf.setPlaceholderCharacter('_'); 
		
		
		//MaskFormatters
		pane.add(tfeID = new JFormattedTextField(mf));
		pane.add(month = new JFormattedTextField(mmf));
		pane.add(day = new JFormattedTextField(ddf));
		pane.add(year = new JFormattedTextField(yyf));
		pane.add(contact = new JFormattedTextField(contf));
		
		month.setBounds(230,365,90,30);
		month.addFocusListener(this);
		
		day.setBounds(328,365,90,30);
		day.addFocusListener(this);
		
		year.setBounds(425,365,85,30);
		year.addFocusListener(this);
		
		uname.setBounds(295,35,100,20);

		pw.setBounds(295,55,100,20);
		
		tfeID.setBounds(295,75,100,20);
		
		fname.setBounds(230,125,280,30);
		
		mname.setBounds(230,185,280,30);
		
		lname.setBounds(230,245,280,30);
		
		addr.setBounds(230,305,280,30);
		
		pos.setBounds(230,425,280,30);
	
		contact.setBounds(230,485,280,30);
		
		search.setBounds(640,20,220,30);
		search.setBorder(BorderFactory.createCompoundBorder(search.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		search.setBackground(null);
		search.setForeground(Color.white);
		search.setFont(f1);
		search.setCaretColor(Color.white);
		search.addActionListener(this);
		
		lblFname.setBounds(230,90,80,50);
		lblFname.setFont(f1);
		
		lblMname.setBounds(230,150,80,50);
		lblMname.setFont(f1);
		
		lblLname.setBounds(230,210,80,50);
		lblLname.setFont(f1);
		
		lblAddr.setBounds(230,270,80,50);
		lblAddr.setFont(f1);
		
		lblPos.setBounds(230,390,80,50);
		lblPos.setFont(f1);
		
		lblCont.setBounds(230,450,100,50);
		lblCont.setFont(f1);
		
		lblBday.setBounds(230,330,80,50);
		lblBday.setFont(f1);
		
		lblUname.setBounds(230,20,70,50);
		
		lblPw.setBounds(230,40,70,50);
		
		lblEid.setBounds(230,60,70,50);
		lblEid.setFont(f3);
		
		dp.setFont(f1);//
		dp.setBounds(403,30,80,80);
		dp.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		lblClock.setFont(f2);
		lblClock.setForeground(Color.white);
		lblClock.setBounds(540,550,300,30);
		
		
		AdBtn.setBounds(0,80,200,50);
		AdBtn.setBorder(null);
		AdBtn.setMnemonic(KeyEvent.VK_A);
		AdBtn.setFont(menufont);
		AdBtn.setIcon(admin);
		AdBtn.setContentAreaFilled(false);
		AdBtn.setFocusPainted(false);
		AdBtn.setOpaque(false);
		AdBtn.setForeground(Color.WHITE);
		AdBtn.addActionListener(this);
		AdBtn.addMouseListener(new MouseAdapter() {
	         Color oldcolor = AdBtn.getForeground();
	         public void mouseEntered(MouseEvent me) {
	            oldcolor = AdBtn.getForeground();
	            AdBtn.setForeground(Color.decode("#34495e"));
	            AdBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.USER_CIRCLE, 20,Color.decode("#34495e")));
	            AdBtn.setOpaque(true);
	    		AdBtn.setBackground(Color.white);
	         }
	         public void mouseExited(MouseEvent me) {
	        	AdBtn.setForeground(oldcolor);
	        	AdBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.USER_CIRCLE, 20,Color.white));
	 	        AdBtn.setOpaque(false);
	         }
	    });
		
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
		PatBtn.addMouseListener(new MouseAdapter() {
	         Color oldcolor = PatBtn.getForeground();
	         public void mouseEntered(MouseEvent me) {
	            oldcolor = PatBtn.getForeground();
	            PatBtn.setForeground(Color.decode("#34495e"));
	            PatBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.BED, 20,Color.decode("#34495e")));
	            PatBtn.setOpaque(true);
	    		PatBtn.setBackground(Color.white);
	         }
	         public void mouseExited(MouseEvent me) {
	        	PatBtn.setForeground(oldcolor);
	        	PatBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.BED, 20,Color.white));
	 	        PatBtn.setOpaque(false);
	         }
	    });
		
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
		EmpBtn.addMouseListener(new MouseAdapter() {
	         Color oldcolor = EmpBtn.getForeground();
	         public void mouseEntered(MouseEvent me) {
	            oldcolor = EmpBtn.getForeground();
	            EmpBtn.setForeground(Color.decode("#34495e"));
	            EmpBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.USER_MD, 20,Color.decode("#34495e")));
	            EmpBtn.setOpaque(true);
	    		EmpBtn.setBackground(Color.white);
	         }
	         public void mouseExited(MouseEvent me) {
	        	 EmpBtn.setForeground(oldcolor);
	        	 EmpBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.USER_MD, 20,Color.white));
	        	 EmpBtn.setOpaque(false);
	         }
	    });	
		
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
		SetBtn.addMouseListener(new MouseAdapter() {
	         Color oldcolor = SetBtn.getForeground();
	         public void mouseEntered(MouseEvent me) {
	            oldcolor = SetBtn.getForeground();
	            SetBtn.setForeground(Color.decode("#34495e"));
	            SetBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.COG, 20,Color.decode("#34495e")));
	            SetBtn.setOpaque(true);
	            SetBtn.setBackground(Color.white);
	         }
	         public void mouseExited(MouseEvent me) {
	        	 SetBtn.setForeground(oldcolor);
	        	 SetBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.COG, 20,Color.white));
	        	 SetBtn.setOpaque(false);
	         }
	    });
		
		SoBtn.setBounds(0,550,200,50);
		SoBtn.setBorder(null);
		SoBtn.setMargin(new Insets(2, 8, 2, 8));
		SoBtn.setFont(menufont);
		SoBtn.setIcon(signout);
		SoBtn.setContentAreaFilled(false);
		SoBtn.setFocusPainted(false);
		SoBtn.setOpaque(false);
		SoBtn.setForeground(Color.WHITE);
		SoBtn.addActionListener(this);
		SoBtn.addMouseListener(new MouseAdapter() {
	         Color oldcolor = SoBtn.getForeground();
	         public void mouseEntered(MouseEvent me) {
	            oldcolor = SoBtn.getForeground();
	            SoBtn.setForeground(Color.decode("#34495e"));
	            SoBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, 20,Color.decode("#34495e")));
	            SoBtn.setOpaque(true);
	            SoBtn.setBackground(Color.white);
	         }
	         public void mouseExited(MouseEvent me) {
	        	 SoBtn.setForeground(oldcolor);
	        	 SoBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, 20,Color.white));
	        	 SoBtn.setOpaque(false);
	         }
	    });
		
		side.setBackground(mbColor);
		side.setBounds(0,0,200,1000);
		
		whitebg.setBackground(Color.white);
		whitebg.setBounds(220,20,300,575);
	
		
		scrollPane.setBounds(540,110,425,440);
		
		usertable = new JTable();  

		usertable.setDefaultEditor(Object.class, null);
		scrollPane.setViewportView(usertable);  
		usertable.addMouseListener(this);  
		
		Clock();
		
	}
	public void Search() {
	     try {
	          Connection connection=dbCon.getConnection();
	          
	          String selection = (String) cBox.getSelectedItem();
	          String query="SELECT * FROM employee WHERE "+ selection + " = '" + search.getText() + "'";
	          PreparedStatement ps = connection.prepareStatement(query);
	          rs = ps.executeQuery();

	          usertable.setModel(DbUtils.resultSetToTableModel(rs));
			  ps.close();
	        
	        } catch (Exception ee) {
	          ee.printStackTrace();
	        }
	}

	public void refreshTable() {
		try {
			Connection connection=dbCon.getConnection();
			String query = "select * from employee";
			PreparedStatement ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			usertable.setModel(DbUtils.resultSetToTableModel(rs));
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ImageIcon ResizeImage(String imgPath) {
		ImageIcon MyImage = new ImageIcon(imgPath);
		Image img = MyImage.getImage();
		Image newImage = img.getScaledInstance(dp.getWidth(), dp.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImage);
		return image;
	}
	
	
	public void clearF() {
		uname.setText("");
		pw.setText("");
		tfeID.setText("");
		dp.setIcon(null);
		fname.setText("");
		mname.setText("");
		lname.setText("");
		addr.setText("");
		month.setText("");
		day.setText("");
		year.setText("");
		pos.setText("");
		contact.setText("");
	
		Random rand = new Random();
		int c1 = rand.nextInt(10), c2 = rand.nextInt(10),c3 = rand.nextInt(10),c4 = rand.nextInt(10),c5 = rand.nextInt(10);
		Integer.toString(c1);
		String genCode = Integer.toString(c1) + Integer.toString(c2)+ Integer.toString(c3) + Integer.toString(c4) +  Integer.toString(c5);
		tfeID.setText(genCode);
	
		tfeID.setEditable(true);
	}
	
	public void LoadList() {
		try {
			Connection connection = dbCon.getConnection();
			String query = "select * from employee ";
			PreparedStatement ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			DefaultListModel<String> DLM = new DefaultListModel<String>();
			while (rs.next()) {
				DLM.addElement(rs.getString("eFname"));
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings({ "static-access", "unused" })
	@Override
	public void actionPerformed(ActionEvent e) {
	
		
	String bday = month.getText()+"-"+day.getText()+"-"+year.getText();
	char[] pass  = pw.getPassword();
    String upw = new String(pass);

    	if(e.getSource() == AdBtn) {
    		this.dispose();
    		adminDashboard db = new adminDashboard();
    		db.setLayout(null);
			db.setResizable(false);
			db.setLocationRelativeTo(null);
			db.setVisible(true);
			db.loadAll();
    	}
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
    	if (e.getSource() == SetBtn) {
    		j.showMessageDialog(null, "The Settings panel is dedicated for the Database Connection using TCP/IP.\nDue to the Community Quarantine, the Developers cannot test the said feature.\n\nFurthermore, the Developers will be upgrading this system for Future Use.\nHoping for your Consideration.");
    	}
	    if(e.getSource() == clearBtn) {
	    	clearF();
	    }
	        
		if (e.getSource() == searchBtn) {
			Search();
			  if(usertable.getRowCount() == 0){
				  j.showMessageDialog(null, "No Data");
			  }
		}
		if (e.getSource() == load) {
			refreshTable();
		}
		if (e.getSource() == PatBtn) {
			
			String n = j.showInputDialog(null, "Patient Name : (Firstname,Middlename,Lastname)", "Patient Information", j.PLAIN_MESSAGE);
		
			if(n == null) {}
			else {
			     try {
		          Connection connection=dbCon.getConnection();
		          String query="SELECT * FROM patient WHERE Fullname=?";
		          PreparedStatement ps=connection.prepareStatement(query);
		          ps.setString(1,n);
		          
		          ResultSet set = ps.executeQuery();
		          
		          
		          if(set.next())
		          {
		            JOptionPane.showMessageDialog(null, "Success");
		            this.dispose();
		            patient pf = new patient();
		    		pf.dispose();
		    		pf.patientname.setText(set.getString("fname") + " " + set.getString("mname") + " " + set.getString("lname"));
		    	    pf.setExtendedState(JFrame.MAXIMIZED_BOTH);
		    		pf.setUndecorated(true);
		    		pf.setLayout(null);
		    		pf.setVisible(true);
		    		pf.setLocationRelativeTo(null);
		            
		          }else
		          {
		            JOptionPane.showMessageDialog(null, "Patient doesn't have record");
		            side.setVisible(true);
					AdBtn.setVisible(true);
					EmpBtn.setVisible(true);
					SetBtn.setVisible(true);
					PatBtn.setVisible(true);
					SoBtn.setVisible(true);
		            return;
		          }
		        
		        } catch (Exception ee) {
		          ee.printStackTrace();
		          j.showMessageDialog(null, ee);
		        }
			}
		}
		if (e.getSource() == saveBtn) {
				String yrsold;
				int x = Integer.parseInt(year.getText());
				int y = Integer.parseInt(month.getText());
				int z = Integer.parseInt(day.getText());
				
				LocalDate today = LocalDate.now();
				LocalDate birth = LocalDate.of(x, y, z);
				Period p = Period.between(birth, today);
				int a = p.getYears();
				String finalage = String.valueOf(a);
				yrsold = finalage;
		
				try {
				  Connection connection=dbCon.getConnection();
		          String query="INSERT INTO employee (eFname,eMname,eLname,eJp,eId,eBday,eAge,eCont,eAddr,eUn,ePw,img) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
		          PreparedStatement ps = connection.prepareStatement(query);
			  	    InputStream img = new FileInputStream(new File(s));
		            ps.setString(1, fname.getText());
		            ps.setString(2, mname.getText());
		            ps.setString(3, lname.getText());
			        ps.setString(4, pos.getText());
			        ps.setString(5, tfeID.getText());
			        ps.setString(6, bday);
			        ps.setString(7, finalage);
			        ps.setString(8, contact.getText());
			        ps.setString(9, addr.getText());
			        ps.setString(10, uname.getText());
			        ps.setString(11, upw);
			        ps.setBlob(12, img);
		            ps.execute();
				    JOptionPane.showMessageDialog(null, "Data Saved");
				    ps.close();
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex);
				}
				try {	
					  Connection connection=dbCon.getConnection();
			          String query="INSERT INTO userdata (username,password,usertype,empId) VALUES (?,?,?,?);";
			          PreparedStatement ps = connection.prepareStatement(query);
			          ps.setString(1, uname.getText());
			          ps.setString(2, upw);
			          ps.setString(3, pos.getText());
			          ps.setString(4, tfeID.getText());
			          ps.execute();
					ps.close();
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex);
				}
				refreshTable();
				clearF();
		}
		if (e.getSource() == editBtn) {
				Blob image = null;
				try {
					Connection conn = dbCon.getConnection();
					String query = "SELECT * From employee where id = " + id;
					PreparedStatement ps = conn.prepareStatement(query);
					rs = ps.executeQuery();
					while(rs.next()) {
					 image = rs.getBlob("img");
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
						  Connection connection=dbCon.getConnection();
				          String query="UPDATE employee set eFname=?, eMname=?,eLname=?,eJp=?,eId=?,eBday=?,eCont=?,eAddr=?,eUn=?,ePw=?,img=? where id = '"+ txtID.getText() +"';";
				          PreparedStatement ps = connection.prepareStatement(query);
				     
				          InputStream img = new FileInputStream(new File(s));
				      	
				          ps.setString(1, fname.getText());
				          ps.setString(2, mname.getText());
				          ps.setString(3, lname.getText());
				          ps.setString(4, pos.getText());
				          ps.setString(5, tfeID.getText());
				          ps.setString(6, bday);
				          ps.setString(7, contact.getText());
				          ps.setString(8, addr.getText());
				          ps.setString(9, uname.getText());
				          ps.setString(10, upw);
				          ps.setBlob(11, img);
				          
				          ps.execute();
				          ps.close();
				          j.showMessageDialog(null, "Data Updated", null, j.INFORMATION_MESSAGE);
				          j.showMessageDialog(null, "New Image has been Uploaded");
							
					} catch (Exception ex) {
						ex.printStackTrace();
						j.showMessageDialog(null, ex);
					}
					refreshTable();
					clearF();
				}
				if (n.contentEquals("old")) {

					try {
						  Connection connection=dbCon.getConnection();
				          String query="UPDATE employee set eFname=?, eMname=?,eLname=?,eJp=?,eId=?,eBday=?,eCont=?,eAddr=?,eUn=?,ePw=?,img=? where id = '"+ txtID.getText() +"';";
				          PreparedStatement ps = connection.prepareStatement(query);
				          ps.setString(1, fname.getText());
				          ps.setString(2, mname.getText());
				          ps.setString(3, lname.getText());
				          ps.setString(4, pos.getText());
				          ps.setString(5, tfeID.getText());
				          ps.setString(6, bday);
				          ps.setString(7, contact.getText());
				          ps.setString(8, addr.getText());
				          ps.setString(9, uname.getText());
				          ps.setString(10, upw);
				          ps.setBlob(11, image);
				          
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
		if (e.getSource() == delBtn) {
			int action = j.showConfirmDialog(null, "Do you want to delete!", "Delete",j.YES_NO_OPTION);
			if (action == 0) {
				try {
					Connection connection=dbCon.getConnection();
				      
					String query = "delete from employee where id = '" + txtID.getText() + "' ";
					PreparedStatement ps = connection.prepareStatement(query);
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
		if(e.getSource() == SoBtn) {
			this.dispose();
			loginForm lf = new loginForm();
            lf.setUndecorated(true);
			lf.setLayout(null);
            lf.setLocationRelativeTo(null);
            lf.setVisible(true);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	@SuppressWarnings("static-access")
	@Override
	public void mouseClicked(MouseEvent mc) {
		if (mc.getSource() == usertable) {	
			try {
				Connection conn = dbCon.getConnection();
				
				int row = usertable.getSelectedRow();
				String idemp = (usertable.getModel().getValueAt(row, 0).toString());
				String query = "SELECT * FROM employee where id = '" + idemp + "' ";
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				while (rs.next()) {
					id = idemp;
					uname.setText(rs.getString("eUn").toString());
					pw.setText(rs.getString("ePw").toString());
					fname.setText(rs.getString("eFname").toString());
					mname.setText(rs.getString("eMname").toString());
					lname.setText(rs.getString("eLname").toString());
					addr.setText(rs.getString("eAddr").toString());
					pos.setText(rs.getString("eJp").toString());
					contact.setText(rs.getString("eCont"));
					tfeID.setText(rs.getString("eId"));
					txtID.setText(rs.getString("id"));
					tfeID.setEditable(false);
					byte[] img = rs.getBytes("img");
					n = "old";
					if (img == null) {
						j.showMessageDialog(null, "No Image has been saved on this Account", "Warning", j.WARNING_MESSAGE);
						dp.setIcon(null);
					
					}
					else {
						ImageIcon image = new ImageIcon(img);
						Image im = image.getImage();
						Image myImg = im.getScaledInstance(dp.getWidth(), dp.getHeight(), Image.SCALE_SMOOTH);
						ImageIcon newImage = new ImageIcon(myImg);
						dp.setIcon(newImage);
					}
					
					
					
					String temp = rs.getString("eBday").toString();
					String[] parts = temp.split("-");
					String mm = parts[0];
					String dd = parts[1];
					String yyyy = parts[2];
					
					month.setText(mm);
					day.setText(dd);
					year.setText(yyyy);
				}
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent me) {
		if(me.getSource() == load) {
			load.setForeground(green);
		}
	}
	@Override
	public void mouseExited(MouseEvent mex) {
		if(mex.getSource() == load) {
			load.setForeground(Color.white);
		}
	}
	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	public static void main(String[] args) {}
	@Override
	public void focusGained(FocusEvent arg0) {}
	@SuppressWarnings("static-access")
	@Override
	public void focusLost(FocusEvent fl) {
		
		
		if (fl.getSource() == month) {
			if (month.getText().contentEquals("MM")) {}
			else {
				if (Integer.parseInt(month.getText()) >= 13) {
					j.showMessageDialog(null, "Invalid Month [0-12]", "Invalid", j.ERROR_MESSAGE);
					month.setText("");
					month.requestFocus();
				}	
			}
		}
		if (fl.getSource() == day) {
			if (month.getText().contentEquals("MM")) {}
			else {
				if (Integer.parseInt(day.getText()) >= 32) {
					j.showMessageDialog(null, "Invalid Day [0-31]", "Invalid", j.ERROR_MESSAGE);
					day.setText("");
					day.requestFocus();
				}
			}
		}
		if (fl.getSource() == year) {
			if (year.getText().contentEquals("YYYY")) {}
			else {
				if (Integer.parseInt(year.getText()) == 0000) {
					j.showMessageDialog(null, "Invalid Year", "Invalid", j.ERROR_MESSAGE);
					year.setText("");
					year.requestFocus();
				}
				if (Integer.parseInt(year.getText()) < 1900) {
					j.showMessageDialog(null, "Invalid Year", "Invalid", j.ERROR_MESSAGE);
					year.setText("");
					year.requestFocus();
				}
			}
		}
		
		
		
		
	}
}
