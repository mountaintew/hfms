package forms;

import java.awt.*;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;

import conn.dbCon;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import net.proteanit.sql.DbUtils;


@SuppressWarnings({ "serial", "unused" })
public class loginForm extends JFrame  { 
	  static JOptionPane j;
	  int appCount;
	 
	  String empname, userid, type;
	  private JPanel contentPane, white;
	  private JTextField username;
	  private JPasswordField password;
	  private JLabel lpassword, lusername, gif, lblClock, title, subtitle;
	  //RUNS THE  LOGIN FRAME
	  
	  
	  
	  public static void main(String[] args) {
	    EventQueue.invokeLater(new Runnable() {
	      public void run() {
	        try {
	          loginForm frame = new loginForm();	          
	          frame.setUndecorated(true);
	          frame.setVisible(true);
	          frame.setLocationRelativeTo(null);
	          
	          
	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	      }
	    });
	  }
		
	  //LOGIN FRAME
	  public loginForm() {
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 720, 450);
	    
	    Font menufont = new Font("Sans Serif", Font.BOLD, 14);
	    Font lblFont = new Font("Sans Serif", Font.BOLD, 12);
	    Font tfFont = new Font("Sans Serif", Font.BOLD, 12);
	    Font titlef = new Font("Sans Serif", Font.BOLD, 70);
	    Font subtitlef = new Font("Sans Serif", Font.BOLD, 12);
	    
	    
		Color bgColor = Color.decode("#34495e");
		Color mbColor = Color.decode("#16a085");
	    
		IconFontSwing.register(FontAwesome.getIconFont());
		
		Icon lock = IconFontSwing.buildIcon(FontAwesome.LOCK, 20,Color.white);
		Icon unlock = IconFontSwing.buildIcon(FontAwesome.UNLOCK_ALT, 22,bgColor);
		Icon c = IconFontSwing.buildIcon(FontAwesome.BAN, 20,Color.white);
		Icon c_alt = IconFontSwing.buildIcon(FontAwesome.BAN, 22,bgColor);
		
		lblClock = new JLabel();
	    gif = new JLabel(new ImageIcon(this.getClass().getResource("bg.gif")));
	    gif.setBounds(0,0,450,450);

		
	    contentPane = new JPanel();
	    
	    setContentPane(contentPane);
	    contentPane.setBackground(bgColor);
	    
	    
	    contentPane.add(gif);
	    contentPane.setLayout(null);
	    
	    title = new JLabel("HFMS");
	    title.setBounds(488, 5, 200, 80);
	    title.setForeground(Color.white);
	    title.setFont(titlef);
	    contentPane.add(title);
	    
	    subtitle = new JLabel("Healthcare Facility Management System");
	    subtitle.setBounds(471, 40, 300, 80);
	    subtitle.setForeground(Color.white);
	    subtitle.setFont(subtitlef);
	    contentPane.add(subtitle);
	    
	    white = new JPanel();
	    white.setBounds(0,0,450,450);
	    white.setBackground(Color.white);
	    
	    username = new JTextField();
	    username.setBounds(470, 145, 230, 28);
	    username.setBackground(null);
	    username.setBorder(BorderFactory.createCompoundBorder(username.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	    username.setForeground(Color.white);
	    username.setFont(tfFont);
	    username.setCaretColor(Color.white);
	    contentPane.add(username);
	    
	   
	    password = new JPasswordField();
	    password.setBounds(470, 215, 230, 28);
	    password.setBackground(null);
	    password.setBorder(BorderFactory.createCompoundBorder(password.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	    password.setColumns(10);	
	    password.setForeground(Color.white);
	    password.setFont(tfFont);	

	    password.setCaretColor(Color.white);
	    contentPane.add(password);
	    
	    lusername = new JLabel("Username");
	    lusername.setBounds(470, 125, 100, 20);
	    contentPane.add(lusername);
	    lusername.setForeground(Color.white);
	    lusername.setFont(lblFont);
	    
	    lpassword = new JLabel("Password");
	    lpassword.setBounds(470, 195, 100, 20);
	    lpassword.setForeground(Color.white);
	    contentPane.add(lpassword);
	    lpassword.setFont(lblFont);
	    
	    JButton cancel = new JButton("Cancel");
	    cancel.setFocusPainted(false);
		cancel.setMnemonic(KeyEvent.VK_C);
	    cancel.addActionListener(new ActionListener() {
			
		@SuppressWarnings("static-access")
		@Override
			public void actionPerformed(ActionEvent e) {
				j.showMessageDialog(null, "Cancel Login?");
				loginForm.this.dispose();
				
			}
		});
	    cancel.setBounds(585, 410, 135, 40);
	    cancel.setFont(menufont);

	    cancel.setContentAreaFilled(false);
		cancel.setFocusPainted(true);
		cancel.setOpaque(true);
		cancel.setBorder(null);
		cancel.setBackground(Color.decode("#e74c3c"));
		cancel.setForeground(Color.white);
		cancel.setIcon(c);
		
	    contentPane.add(cancel);
	    
	    JButton login = new JButton("Login");
		login.setMnemonic(KeyEvent.VK_L);
		login.setFocusPainted(false);
		login.addMouseListener(new MouseAdapter() {
	         Color oldcolor = login.getForeground();
	         public void mouseEntered(MouseEvent me) {
	            oldcolor = login.getForeground();
	            login.setForeground(bgColor);
	            login.setIcon(unlock);
	         }
	         public void mouseExited(MouseEvent me) {
	        	login.setForeground(oldcolor);
	        	login.setBackground(Color.decode("#27ae60"));
	        	login.setIcon(lock);
	        }
	    });
		
		cancel.addMouseListener(new MouseAdapter() {
	         Color oldcolor = cancel.getForeground();
	         public void mouseEntered(MouseEvent me) {
	        	oldcolor = cancel.getForeground();
	        	cancel.setForeground(bgColor);
	        	cancel.setIcon(c_alt);
	         }
	         public void mouseExited(MouseEvent me) {
	        	cancel.setForeground(oldcolor);
	        	cancel.setBackground(Color.decode("#e74c3c"));
	        	cancel.setIcon(c);
	        }
	    });
		
	    login.addActionListener(new ActionListener() {
		@SuppressWarnings("static-access")
		public void actionPerformed(ActionEvent arg0) {
	    	try {
	    		Connection conn = dbCon.getConnection();
	    		String q  = "SELECT * FROM employee WHERE eUn = '" + username.getText()  + "'";
	    		PreparedStatement p = conn.prepareStatement(q);
	    		ResultSet r = p.executeQuery();
	    		while (r.next()) {
	    			if (r.getString("eJp").contentEquals("Doctor")) {
		    			empname = "Dr. " + r.getString("eLname");	
		    			userid = r.getString("eId");
		    			type = r.getString("eJp").toString();
	    			}
	    			if (r.getString("eJp").contentEquals("Nurse")) {
	    				empname = "Nurse " + r.getString("eLname");
	    				userid = r.getString("eId");
		    			type = r.getString("eJp").toString();
	    			}
	    			if (r.getString("eJp").contentEquals("Admin")) {
	    				empname = "Admin";
	    			}
	    			
	    			userid = r.getString("eId");
	    		}
	    	}catch (Exception e) {
	    		e.printStackTrace();
	    	}
			
	    	try {
	    		Connection conn = dbCon.getConnection();
	    		String q  = "select COUNT(id) from appointments where userId='" + userid  +"'";
	    		PreparedStatement p = conn.prepareStatement(q);
	    		ResultSet r = p.executeQuery();
	    		
	    		
	    		while (r.next()) {
	    			appCount = Integer.parseInt(r.getString("COUNT(id)"));
	    		}
	    	}catch (Exception e) {
	    		e.printStackTrace();
	    	}
			
			
	        try {
	          Connection connection=dbCon.getConnection();
	          
	          //conversion of getPassword() to String()
	          char[] pass  = password.getPassword();
	          String pw = new String(pass);
	          
	          
	          String query="SELECT * FROM userdata WHERE username=? and password=?";
	          PreparedStatement ps = connection.prepareStatement(query);
	          ps.setString(1, username.getText());
	          ps.setString(2, pw);
	          ResultSet rs = ps.executeQuery();
	          
	          if(rs.next())
	          {
	        	  
	        	j.showMessageDialog(null, "\tLogin Sucessful\n" + "\tWelcome " + empname);
		        j.showMessageDialog(null, "This System will be using Local Database");
        		loginForm.this.dispose();
	            
	            if (rs.getString("usertype").contentEquals("admin") || rs.getString("usertype").contentEquals("ADMIN") || rs.getString("usertype").contentEquals("Admin")) { 
	                adminDashboard ad = new adminDashboard();
		            ad.setResizable(false);
		            ad.setLayout(null);
		            ad.setLocationRelativeTo(null);
		            ad.setVisible(true);
		            ad.loadAll();
	            }
	            
	            if (rs.getString("usertype").contentEquals("doctor") || rs.getString("usertype").contentEquals("DOCTOR") || rs.getString("usertype").contentEquals("Doctor")) { 
	            	empDashboard db = new empDashboard();
	        		db.setLocationRelativeTo(null);
	        		db.setLayout(null);
	        		db.setResizable(false);
	        		db.setVisible(true);
	        		db.lblwe.setText("Welcome " + empname);
	        		db.userid.setText(userid.toString());
	        		db.type.setText(type.toString());
	        		db.tp.remove(db.panel1);
	        		db.tp.remove(db.panel3);
	        		db.loadvis.setVisible(false);
	        		db.PatBtn.setVisible(false);
	        		db.SetBtn.setBounds(db.PatBtn.getBounds());
	        		db.load();
	        		db.sp.setViewportView(db.table1);
	        		db.btn1.setText(String.valueOf(appCount) + " Patient Appointments");
	            }
	            
	            if (rs.getString("usertype").contentEquals("nurse") || rs.getString("usertype").contentEquals("NURSE") ||rs.getString("usertype").contentEquals("Nurse")) { 
	        		empDashboard db = new empDashboard();
	        		db.setLocationRelativeTo(null);
	        		db.setLayout(null);
	        		db.setResizable(false);
	        		db.setVisible(true);
	        		db.tp.remove(db.panel2);
	        		db.userid.setText(userid.toString());
	        		db.type.setText(type.toString());
	        		db.loadtable();
	        		db.loadtable1();
	        		db.table.setComponentPopupMenu(db.jpm2);
	        		db.table1.setComponentPopupMenu(null);
	        		db.loadPres.setVisible(false);
	        		db.type.setText("nurse");
	        		
	            }
	          }
	          else
	          {
	            j.showMessageDialog(null, "Invalid Username or Password");
	            username.setText("");
	            password.setText("");
	            username.requestFocus();
	            return;
	          }
	          
	        } catch (Exception e) {
	          // TODO: handle exception
	        }
	        
	      }
	    });
	    login.setBounds(450, 410, 135, 40);
	    login.setFont(menufont);
	    login.setContentAreaFilled(false);
	    login.setFocusPainted(true);
	    login.setOpaque(true);
	    login.setBorder(null);
	    login.setBackground(Color.decode("#27ae60"));
		login.setForeground(Color.white);
		login.setIcon(lock);
		
		//contentPane.add(white);
		contentPane.add(login);
		
		
	  }
	}
