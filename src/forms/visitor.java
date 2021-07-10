package forms;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

import conn.dbCon;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

@SuppressWarnings({"serial","rawtypes"})
public class visitor extends JFrame implements ActionListener, FocusListener {
	static JOptionPane j;
	String visID;
	JButton save,edit,del;
	JPanel header;
	JLabel lblhead, lblpat, lblname, lbltov, lbldov, lblcont, lblrel;
	JTextField visname, rel;
	JComboBox patname, ampm;
	MaskFormatter dv, tv, co;
	JFormattedTextField dov, tov, cont;
	
	public Connection conn = null;
	public Statement st = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	
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
	
	
	@SuppressWarnings({ "unchecked",})
	public DefaultComboBoxModel patients() {
		DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
		try {     
			conn = dbCon.getConnection();
			String query = "SELECT fullname from patients";     
			ps = conn.prepareStatement(query);     
			rs = ps.executeQuery();     
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
	
	@SuppressWarnings({ "unused", "unchecked" })
	public visitor(){
		setTitle("");
		setSize(600, 280);
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		setContentPane(pane);
		pane.setVisible(true);
		pane.setBackground(bgColor);
		
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
		
		pane.add(header = new JPanel());
		header.setBounds(0,0,600,40);
		header.setBackground(mbColor);
		
		try {
			tv = new MaskFormatter("##:##");
			} catch (ParseException e) {    
				e.printStackTrace();   
		} 
		tv.setPlaceholderCharacter('_'); 
		try {
			dv = new MaskFormatter("##-##-####");
			} catch (ParseException e) {    
				e.printStackTrace();   
		} 
		dv.setPlaceholderCharacter('_'); 
		try {
			co = new MaskFormatter("####-###-####");
			} catch (ParseException e) {    
				e.printStackTrace();   
		} 
		co.setPlaceholderCharacter('_'); 
		
		
		pane.add(patname = new JComboBox(patients()));
		patname.setBounds(50,80,170,30);
		
		pane.add(visname = new JTextField());
		visname.setBounds(250,80,170,30);
		visname.setBackground(null);
		visname.setBorder(BorderFactory.createCompoundBorder(visname.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		visname.setForeground(Color.white);
		visname.setCaretColor(Color.white);
		
		pane.add(tov = new JFormattedTextField(tv));
		tov.setBounds(450,80,50,30);
		tov.setBackground(null);
		tov.setBorder(BorderFactory.createCompoundBorder(tov.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		tov.setForeground(Color.white);
		tov.setCaretColor(Color.white);
		tov.setHorizontalAlignment(SwingConstants.CENTER);
		tov.addFocusListener(this);
		
		pane.add(ampm = new JComboBox());
		ampm.setModel(new DefaultComboBoxModel(new String[] {"AM", "PM"}));   
		ampm.setBounds(500,80,50,30);
		ampm.setBorder(BorderFactory.createCompoundBorder(ampm.getBorder(), BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		
		
		pane.add(dov = new JFormattedTextField(dv));
		dov.setBounds(90,150,90,30);
		dov.setBackground(null);
		dov.setBorder(BorderFactory.createCompoundBorder(dov.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		dov.setForeground(Color.white);
		dov.setCaretColor(Color.white);
		dov.addFocusListener(this);
		dov.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		pane.add(cont = new JFormattedTextField(co));
		cont.setBounds(210,150,120,30);
		cont.setBackground(null);
		cont.setBorder(BorderFactory.createCompoundBorder(cont.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		cont.setForeground(Color.white);
		cont.setCaretColor(Color.white);
		cont.setHorizontalAlignment(SwingConstants.CENTER);
		
		pane.add(rel = new JTextField());
		rel.setBounds(360,150,150,30);
		rel.setBackground(null);
		rel.setBorder(BorderFactory.createCompoundBorder(rel.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		rel.setForeground(Color.white);
		rel.setCaretColor(Color.white);
		rel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//JLabel lblpat, lblname, lbltov, lbldov, lblcont, lblrel;
		
		pane.add(lblpat = new JLabel("Patient Name:"));
		lblpat.setBounds(50,50,120,30);
		lblpat.setForeground(Color.white);
		
		pane.add(lblname = new JLabel("Vistor Name:"));
		lblname.setBounds(250,50,120,30);
		lblname.setForeground(Color.white);
		
		pane.add(lbltov = new JLabel("Time of Visit:"));
		lbltov.setBounds(450,50,120,30);
		lbltov.setForeground(Color.white);
		
		pane.add(lbldov = new JLabel("Date of Visit:"));
		lbldov.setBounds(90,120,120,30);
		lbldov.setForeground(Color.white);
		
		
		pane.add(lblcont = new JLabel("Contact:"));
		lblcont.setBounds(210,120,120,30);
		lblcont.setForeground(Color.white);
		
		
		
		pane.add(lblrel = new JLabel("Relation to Patient:"));
		lblrel.setBounds(360,120,120,30);
		lblrel.setForeground(Color.white);
		
		
		pane.add(save = new JButton("Add Visitor"));
		save.setBounds(0, 210, 300, 40);
		save.setBackground(green);
		save.setFocusPainted(false);
		save.setForeground(Color.white);
		save.setIcon(plus);
		save.setBorder(null);
		save.addActionListener(this);
		
		pane.add(edit = new JButton("Edit"));
		edit.setBounds(300, 210, 300, 40);
		edit.setBackground(eblue);
		edit.setFocusPainted(false);
		edit.setForeground(Color.white);
		edit.setIcon(pencil);
		edit.setBorder(null);
		edit.addActionListener(this);
		
		
		
		lblhead = new JLabel("Visitor Details");
		lblhead.setBounds(0,0,600,40);
		lblhead.setForeground(Color.white);
		lblhead.setFont(f20);
		header.add(lblhead);
	}
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save) {
			try {
				conn = dbCon.getConnection();
				String query = "INSERT into visitorlog(Patient, VisitorName, `Time`, Relation, `Date`, Contact) value (?,?,?,?,?,?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, patname.getSelectedItem().toString());
				ps.setString(2, visname.getText());
				ps.setString(3, tov.getText()+ " " + ampm.getSelectedItem().toString());
				ps.setString(4, rel.getText());
				ps.setString(5, dov.getText());
				ps.setString(6, cont.getText());
				ps.execute();
				
				j.showMessageDialog(null, "Visitor Added");
				this.dispose();
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (e.getSource() == edit) {
			try {
				String query = "update visitorlog set Patient=?, VisitorName=?, `Time`=?, Relation=?, `Date`=?, Contact=? where id=?";
				ps = conn.prepareStatement(query);
				ps.setString(1, patname.getSelectedItem().toString());
				ps.setString(2, visname.getText());
				ps.setString(3, tov.getText()  + " "+ ampm.getSelectedItem().toString());
				ps.setString(4, rel.getText());
				ps.setString(5, dov.getText());
				ps.setString(6, cont.getText());
				ps.setString(7, visID);
				ps.execute();

				JOptionPane.showMessageDialog(null, "Data Updated");
				this.dispose();
				
				ps.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (e.getSource() == del) {
			
		}
		
	}
	
	public static void main(String[] args) {}

	@Override
	public void focusGained(FocusEvent fg) {
		if (fg.getSource() == dov) {
			String d = null,m = null;
			Calendar cal = new GregorianCalendar();
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int mon = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			
			if (day <= 9) {
				d = "0" + String.valueOf(day);
			}
			if (mon <= 9) {
				m = "0" + String.valueOf(mon);
			}
			else {
				m = String.valueOf(mon);
				d = String.valueOf(day);
			}
			dov.setText(m + "-" + d + "-" + String.valueOf(year));
		}
		if (fg.getSource() == tov) {
			String h = null, m = null, ap = null;
			Calendar cal = new GregorianCalendar();
			Calendar c = Calendar.getInstance();
			int hour = cal.get(Calendar.HOUR);
			int mint = cal.get(Calendar.MINUTE);
			
			if (hour <= 9) {
				h = "0" + String.valueOf(hour);
			}
			if (mint <= 9) {
				m = "0" + String.valueOf(mint);
			}
			if (hour >=10) {
				h = String.valueOf(hour);
			}
			if (mint >= 10) {
				m = String.valueOf(mint);
			}
			ap = c.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.ENGLISH).toString();
			tov.setText(h + ":" + m);
			ampm.setSelectedItem(ap);
			System.out.print(h + ":" + m);
		}
	}

	@Override
	public void focusLost(FocusEvent fl) {
	}

}
