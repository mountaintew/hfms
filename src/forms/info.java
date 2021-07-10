package forms;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class info extends JFrame implements ActionListener, FocusListener{
	
	JLabel bg, lblFull, lblGender, lblAge, lblIll, lblAll, lblDin, lblDout, lblPhys, lblAddr, lblCont, lblStat, lblDp;
	
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
	
	public info() {
		setTitle("Info");
		setSize(500, 270);
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		setContentPane(pane);
		pane.setVisible(true);
		pane.addFocusListener(this);
		this.addFocusListener(this);
		
		
		pane.add(lblFull = new JLabel("Fullname"));
		lblFull.setBounds(40,10,200,30);
		lblFull.setForeground(Color.white);
		
		pane.add(lblGender = new JLabel("Gender"));
		lblGender.setBounds(40,30,200,30);
		lblGender.setForeground(Color.white);
		
		pane.add(lblAge = new JLabel("Age"));
		lblAge.setBounds(40,50,200,30);
		lblAge.setForeground(Color.white);
		
		pane.add(lblIll = new JLabel("Illness"));
		lblIll.setBounds(40,70,200,30);
		lblIll.setForeground(Color.white);
		
		pane.add(lblAll = new JLabel("Allergies"));
		lblAll.setBounds(40,90,200,30);
		lblAll.setForeground(Color.white);
		
		pane.add(lblDin = new JLabel("Date In"));
		lblDin.setBounds(40,110,200,30);
		lblDin.setForeground(Color.white);
		
		pane.add(lblDout = new JLabel("Date Out"));
		lblDout.setBounds(40,130,200,30);
		lblDout.setForeground(Color.white);
		
		pane.add(lblAddr = new JLabel("Address"));
		lblAddr.setBounds(40,150,200,30);
		lblAddr.setForeground(Color.white);
		
		pane.add(lblCont = new JLabel("Contact"));
		lblCont.setBounds(40,170,200,30);
		lblCont.setForeground(Color.white);
		
		pane.add(lblPhys = new JLabel("Physician"));
		lblPhys.setBounds(40,190,200,30);
		lblPhys.setForeground(Color.white);
		
		pane.add(lblStat = new JLabel("Status"));
		lblStat.setBounds(310,165,150,30);
		lblStat.setHorizontalAlignment(SwingConstants.CENTER);
		lblStat.setForeground(Color.white);
		
	
		
		pane.add(lblDp = new JLabel());
		lblDp.setBorder(BorderFactory.createLineBorder(Color.black));
		lblDp.setBounds(310,15,150,150);
		lblDp.setHorizontalAlignment(SwingConstants.CENTER);
		lblDp.setForeground(Color.white);

		pane.add(bg = new JLabel(new ImageIcon(this.getClass().getResource("background.jpg"))));
		bg.setBounds(0,0,500,270);
		
	}

	// resize image
	public ImageIcon ResizeImage(String imgPath) {
		ImageIcon MyImage = new ImageIcon(imgPath);
		Image img = MyImage.getImage();
		Image newImage = img.getScaledInstance(lblDp.getWidth(), lblDp.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImage);
		return image;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {}
	public static void main(String[] args) {}
	@Override
	public void focusGained(FocusEvent arg0) {}

	@Override
	public void focusLost(FocusEvent fl) {
		if (fl.getSource() == this) {
			this.dispose();
		}
		
	}

}
