package forms;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import conn.*;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

@SuppressWarnings({ "serial" , "rawtypes"})
public class medications extends JFrame implements ActionListener, KeyListener, FocusListener, ItemListener{
	static JOptionPane j;
	String eid;
	JLabel label, sub_label, userid, lblname, lbldate, lblage, lblpres, lbladdr, rx, lbluser, lbllic,  lblptr;
	JTextField age, addr, uid, lic, ptr;
	JTextArea med;
	JButton save;
	JComboBox patname;
	JPanel bg1;
	MaskFormatter da, ag;
	JFormattedTextField date;
	
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
	
	@SuppressWarnings({ "unchecked" })
	public DefaultComboBoxModel patients() {
		DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
		try {     
			conn = dbCon.getConnection();
			String query = "SELECT patient from appointments where userid='" + eid +  "'";     
			PreparedStatement ps = conn.prepareStatement(query);     
			
			ResultSet rs = ps.executeQuery();     
			while(rs.next()) {
				dcbm.addElement(rs.getString("patient"));
			}
			ps.close();     
			rs.close();         
			} catch (Exception ex) {     
				ex.printStackTrace();    
			}
		return dcbm;
	}
	
	@SuppressWarnings("unchecked")
	public DefaultComboBoxModel prescriptions() {
		DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
		try {     
			conn = dbCon.getConnection();
			String query = "SELECT patientname from medications where userid='" + eid +  "'";     
			PreparedStatement ps = conn.prepareStatement(query);     
			
			ResultSet rs = ps.executeQuery();     
			while(rs.next()) {
				dcbm.addElement(rs.getString("patientname"));
			}
			ps.close();     
			rs.close();         
			} catch (Exception ex) {     
				ex.printStackTrace();    
			}
		return dcbm;
	}
	
	
	public medications() {
		setTitle("Medications");
		setSize(450, 600);
		Container pane = getContentPane(); // get the container
		pane.setLayout(new BorderLayout());
		setContentPane(pane);
		pane.setVisible(true);
		pane.setBackground(bgColor);
		
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon plus = IconFontSwing.buildIcon(FontAwesome.PLUS, 20,Color.white);
		
		
		label = new JLabel("HFMS");
		label.setBounds(180,5,80,30);
		label.setForeground(mbColor);
		label.setFont(f25);
		
		sub_label = new JLabel("Health Care Facility Management System");
		sub_label.setBounds(120,23,300,30);
		sub_label.setForeground(bgColor);
		sub_label.setFont(f9);
		
		ImageIcon MyImage = new ImageIcon(this.getClass().getResource("rx.jpg"));
		Image img = MyImage.getImage();
		Image newImage = img.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImage);
		
		rx = new JLabel();
		rx.setBounds(20,250,70,70);
		rx.setIcon(image);
		
		pane.add(lblname = new JLabel("Patient Name: "));
		lblname.setBounds(10,90,100,30);
	
		patname = new JComboBox();
		patname.setBounds(100,90,180,30);
		patname.addItemListener(this);
		
		lbldate = new JLabel("Date:");
		lbldate.setBounds(300,90,80,30);
		
		try {
			da = new MaskFormatter("##-##-####");
			} catch (ParseException e) {    
				e.printStackTrace();   
		} 
		da.setPlaceholderCharacter('_'); 
		
		date = new JFormattedTextField(da);
		date.setBounds(330,90,80,30);
		date.setHorizontalAlignment(SwingConstants.CENTER);
		date.setBorder(null);
		date.addFocusListener(this);
		
		
		lblage = new JLabel("Age:");
		lblage.setBounds(300,130,40,30);
		
		age = new JTextField();
		age.setBounds(330,130,55,30);
		age.setHorizontalAlignment(SwingConstants.CENTER);
		age.addKeyListener(this);
		age.addFocusListener(this);
		
		lbladdr = new JLabel("Address:");
		lbladdr.setBounds(10,130,80,30);
		
		addr = new JTextField();
		addr.setBounds(65,130,216,30);
		
		med = new JTextArea();
		med.setBounds(110,200,300,200);
		med.setBorder(BorderFactory.createLineBorder(Color.black));
		
		lbluser = new JLabel("User ID:");
		lbluser.setBounds(280,420,60,30);
		
		uid = new JTextField();
		uid.setBounds(330,422,80,20);
		uid.setHorizontalAlignment(SwingConstants.CENTER);
		uid.setEditable(false);
		
		lbllic = new JLabel("Lic. No.:");
		lbllic.setBounds(280,440,60,30);
		
		lic = new JTextField();
		lic.setBounds(330,442,80,20);
		lic.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblptr = new JLabel("PTR No.:");
		lblptr.setBounds(280,460,60,30);
		
		ptr = new JTextField();
		ptr.setBounds(330,462,80,20);
		ptr.setHorizontalAlignment(SwingConstants.CENTER);
		
		save = new JButton("Save");
		save.setBounds(0,515,425,35);
		save.setBorder(null);
		save.setFocusPainted(false);
		save.setForeground(Color.white);
		save.setBackground(green);
		save.setHorizontalAlignment(SwingConstants.CENTER);
		save.setIcon(plus);
		save.addActionListener(this);
		
		pane.add(bg1 = new JPanel());
		bg1.add(label);
		bg1.add(sub_label);
		bg1.add(lblname);
		bg1.add(patname);
		bg1.add(lbldate);
		bg1.add(date);
		bg1.add(lblage);
		bg1.add(age);
		bg1.add(rx);
		bg1.add(lbladdr);
		bg1.add(addr);
		bg1.add(med);
		bg1.add(lbluser);
		bg1.add(uid);
		bg1.add(lbllic);
		bg1.add(lic);
		bg1.add(lblptr);
		bg1.add(ptr);
		bg1.add(save);
		bg1.setBackground(Color.white);
		bg1.setBounds(10,10,425,550);
		bg1.setLayout(null);

		pane.add(userid = new JLabel());
	}
	
	
	
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == save) {
			if (date.getText().contentEquals("__-__-____") || addr.getText().contentEquals("") || med.getText().contentEquals("") || ptr.getText().contentEquals("") || lic.getText().contentEquals("")) {
				j.showMessageDialog(null, "Please Complete All Fields", "Error", j.ERROR_MESSAGE);
				med.requestFocus();
			}
			else {
				try {
					conn = dbCon.getConnection();
					String query = "INSERT INTO medications (PatientName, Prescription, `Date`, Age, Address, `PTRNo.`, `LICNo.`, UserID) values (?,?,?,?,?,?,?,?)";
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setString(1, patname.getSelectedItem().toString());
					ps.setString(2, med.getText());
					ps.setString(3, date.getText());
					ps.setString(4, age.getText());
					ps.setString(5, addr.getText());
					ps.setString(6, ptr.getText());
					ps.setString(7, lic.getText());
					ps.setString(8, uid.getText());
					ps.execute();
					j.showMessageDialog(null, "Data Saved");
					this.dispose();
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}	
		}
	}
	
	public static void main(String[] args) {}

	@SuppressWarnings("static-access")
	@Override
	public void keyPressed(KeyEvent kp) {
		if (kp.getSource() == age) {
			String value = age.getText();
			int l = value.length();
			
			if (l >= 3) {
				j.showMessageDialog(null, "Invalid Age", "Error", j.ERROR_MESSAGE);
				age.setText("");
				age.requestFocus();
			}
			
			
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

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void focusGained(FocusEvent fg) {
		if (fg.getSource() == date) {
			LocalDate today;
			today = LocalDate.now();//ymd
			String[] str = String.valueOf(today).split("-");
			date.setText(str[1] + "-" + str[2] + "-" + str[0]);
			
		}
		
	}

	@SuppressWarnings("static-access")
	@Override
	public void focusLost(FocusEvent fl) {
		if (fl.getSource() == age) {
			if (age.getText() == "") {
				
			}
			if (age.getText().contentEquals("") == false) {
				int x = Integer.parseInt(age.getText());
				if (x >=150) {
					j.showMessageDialog(null, "Invalid Age", "Error", j.ERROR_MESSAGE);
					age.setText("");
					age.requestFocus();
				}
			}
			
		}
	}
	@Override
	public void itemStateChanged(ItemEvent isc) {
		if(isc.getSource() == patname.getSelectedItem()) {
		}
		
	}

}
