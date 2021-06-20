import java.awt.AWTException;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class RegisterPanel extends JPanel{
	
	private JLabel imgLabel;
	private JLabel joinLabel;
	private JPanel welcomePanel;
	
	private JButton backToLoginButton;
	private JLabel signupLabel;
	private JTextField midField;
	private JLabel bottomLine1 = new JLabel("―――――――――――――――――――――――――");
	private JTextField nameField;
	private JLabel bottomLine2 = new JLabel("―――――――――――――――――――――――――");
	private JPasswordField passField;
	private JLabel bottomLine3 = new JLabel("―――――――――――――――――――――――――");
	private JPasswordField checkPassField;
	private JLabel bottomLine4 = new JLabel("―――――――――――――――――――――――――");
	private JButton registerButton;
	private JPanel infoPanel;
	
	public RegisterPanel() {
		createComp();
		setLayout(new GridLayout(1,2));
		setBackground(Color.white);
		add(this.welcomePanel);
		add(this.infoPanel);
	}
	
	public void createComp() {
		this.welcomePanel = new JPanel();
		this.welcomePanel.setLayout(new GridBagLayout());
		this.welcomePanel.setBackground(new Color(240,240,240));
		
		this.imgLabel = new JLabel(new ImageIcon(new ImageIcon("img/logo-cutout.png").getImage().getScaledInstance(185, 230, Image.SCALE_SMOOTH)));
		GridBagConstraints wp = new GridBagConstraints();
		wp.gridx = 0;
		wp.gridy = 0;
		wp.anchor = GridBagConstraints.CENTER;
		this.welcomePanel.add(imgLabel,wp);
		this.joinLabel = new JLabel("Join Now!");
		this.joinLabel.setFont(new Font("Avenir-Medium",Font.PLAIN,48));
		wp.gridy = 1;
		wp.insets = new Insets(50,0,0,0);
		this.welcomePanel.add(joinLabel,wp);
		
		this.infoPanel = new JPanel();
		this.infoPanel.setLayout(new GridBagLayout());
		this.infoPanel.setBackground(Color.white);
		
		GridBagConstraints ip = new GridBagConstraints();
		ip.gridx = 1;
		ip.gridy = 0;
		ip.insets = new Insets(0,10,60,0);
		this.backToLoginButton = new JButton("→ Login");
		this.backToLoginButton.setFont(new Font("Avenir",Font.PLAIN,26));
		this.backToLoginButton.setFocusable(false); 
		this.backToLoginButton.setBorderPainted(false); 
		this.backToLoginButton.setForeground(new Color(100,100,100));
		this.infoPanel.add(backToLoginButton,ip);
		
		this.signupLabel = new JLabel("Sign Up");
		this.signupLabel.setFont(new Font("Avenir-Book",Font.PLAIN,36));
		ip.gridx = 0;
		ip.gridy += 1;
		ip.insets = new Insets(0,100,0,0);
		ip.anchor = GridBagConstraints.WEST;
		this.infoPanel.add(signupLabel,ip);
		
		ip.insets = new Insets(40,100,0,0);
		ip.gridy += 1;
		Border whiteBorder = new LineBorder(Color.white, 1, true);
		this.midField = new JTextField("學號 Student ID");
		this.midField.setForeground(new Color(120,120,120));
		this.midField.setFont(new Font("Avenir",Font.PLAIN,20));
		this.midField.setPreferredSize(new Dimension(250,30));
		this.midField.setBorder(whiteBorder);
		addFocusListener1();
		this.infoPanel.add(midField,ip);
		ip.gridy += 1;
		ip.insets = new Insets(0,100,20,0);
		this.infoPanel.add(bottomLine1,ip);
		
		ip.insets = new Insets(0,100,0,0);
		ip.gridy += 1;
		this.nameField = new JTextField("姓名/暱稱 Name");
		this.nameField.setForeground(new Color(120,120,120));
		this.nameField.setFont(new Font("Avenir",Font.PLAIN,20));
		this.nameField.setPreferredSize(new Dimension(250,30));
		this.nameField.setBorder(whiteBorder);
		addFocusListener2();
		this.infoPanel.add(nameField,ip);
		ip.gridy += 1;
		ip.insets = new Insets(0,100,20,0);
		this.infoPanel.add(bottomLine2,ip);
		
		ip.insets = new Insets(0,100,0,0);
		ip.gridy += 1;
		this.passField = new JPasswordField("密碼 Password");
		this.passField.setEchoChar((char)0);
		this.passField.setForeground(new Color(120,120,120));
		this.passField.setFont(new Font("Avenir",Font.PLAIN,20));
		this.passField.setPreferredSize(new Dimension(250,30));
		this.passField.setBorder(whiteBorder);
		addFocusListener3();
		this.infoPanel.add(passField,ip);
		ip.gridy += 1;
		ip.insets = new Insets(0,100,20,0);
		this.infoPanel.add(bottomLine3,ip);
		
		ip.insets = new Insets(0,100,0,0);
		ip.gridy += 1;
		this.checkPassField = new JPasswordField("確認密碼 Confirm Password");
		this.checkPassField.setEchoChar((char)0);
		this.checkPassField.setForeground(new Color(120,120,120));
		this.checkPassField.setFont(new Font("Avenir",Font.PLAIN,20));
		this.checkPassField.setPreferredSize(new Dimension(250,30));
		this.checkPassField.setBorder(whiteBorder);
		addFocusListener4();
		this.infoPanel.add(checkPassField,ip);
		ip.gridy += 1;
		ip.insets = new Insets(0,100,20,0);
		this.infoPanel.add(bottomLine4,ip);
		
		ip.gridy += 1;
		ip.anchor = GridBagConstraints.CENTER;
		ip.ipadx = 190;
		ip.ipady = 10;
		ip.insets = new Insets(20,100,120,0);
		this.registerButton = new JButton("Register");
		this.registerButton.setFont(new Font("Avenir-Medium",Font.PLAIN,24));
		this.registerButton.setForeground(Color.white);
		this.registerButton.setBackground(new Color(86,122,154));
		this.registerButton.setBorderPainted(false);
		this.registerButton.setOpaque(true);
		this.registerButton.setFocusable(false); 
		addRegisterListener();
		this.infoPanel.add(registerButton,ip);

	}

	public void addFocusListener1() {
		class InputHintListener implements FocusListener{
		    public void focusGained(FocusEvent e) {
		    	if(midField.getText().equals("學號 Student ID")) {
		    		midField.setText("");
		    	}
		    	midField.setForeground(new Color(70,70,70));
		    }
		    public void focusLost(FocusEvent e) {
		    	if(midField.getText().equals("")) {
		    		midField.setForeground(new Color(120,120,120));
		    		midField.setText("學號 Student ID");
		    	}
		    }
		}
		FocusListener f = new InputHintListener();
		this.midField.addFocusListener(f);
	}
	
	public void addFocusListener2() {
		class InputHintListener implements FocusListener{
		    public void focusGained(FocusEvent e) {
		    	if(nameField.getText().equals("姓名/暱稱 Name")) {
		    		nameField.setText("");
		    	}
		    	nameField.setForeground(new Color(70,70,70));
		    }
		    public void focusLost(FocusEvent e) {
		    	if(nameField.getText().equals("")) {
		    		nameField.setForeground(new Color(120,120,120));
		    		nameField.setText("姓名/暱稱 Name");
		    	}
		    }
		}
		FocusListener f = new InputHintListener();
		this.nameField.addFocusListener(f);
	}
	
	public void addFocusListener3() {
		class InputHintListener implements FocusListener{
		    public void focusGained(FocusEvent e) {
		    	passField.setText("");
		    	passField.setEchoChar('\u2022');
		    	passField.setForeground(new Color(70,70,70));
		    }
		    public void focusLost(FocusEvent e) {
		    	String pass = new String(passField.getPassword());
		    	if(pass.equals("")) {
		    		passField.setForeground(new Color(120,120,120));
		    		passField.setEchoChar((char)0);
		    		passField.setText("密碼 Password");
		    	}
		    	else {
		    		passField.setEchoChar('\u2022');
		    	}
		    }
		}
		FocusListener f = new InputHintListener();
		this.passField.addFocusListener(f);
	}
	
	public void addFocusListener4() {
		class InputHintListener implements FocusListener{
		    public void focusGained(FocusEvent e) {
		    	checkPassField.setText("");
		    	checkPassField.setEchoChar('\u2022');
		    	checkPassField.setForeground(new Color(70,70,70));
		    }
		    public void focusLost(FocusEvent e) {
		    	String pass = new String(checkPassField.getPassword());
		    	if(pass.equals("")) {
		    		checkPassField.setForeground(new Color(120,120,120));
		    		checkPassField.setEchoChar((char)0);
		    		checkPassField.setText("確認密碼 Confirm Password");
		    	}
		    	else {
		    		checkPassField.setEchoChar('\u2022');
		    	}
		    }
		}
		FocusListener f = new InputHintListener();
		this.checkPassField.addFocusListener(f);
	}
	
	public void addLoginListener(JPanel main,LoginPanel login) {
		class LoginListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				midField.setForeground(new Color(120,120,120));
	    		midField.setText("學號 Student ID");
				nameField.setForeground(new Color(120,120,120));
	    		nameField.setText("姓名/暱稱 Name");
	    		passField.setForeground(new Color(120,120,120));
	    		passField.setEchoChar((char)0);
	    		passField.setText("密碼 Password");
	    		checkPassField.setText("");
				((CardLayout)main.getLayout()).show(main,"login");
			}
		}
		ActionListener l = new LoginListener();
		backToLoginButton.addActionListener(l);
	}
	
	public void addRegisterListener() {
		class RegisterListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String id = midField.getText();
				String name = nameField.getText();
				String pass = new String(passField.getPassword());
				String cPass = new String(checkPassField.getPassword());
				if(id.equals("學號 Student ID")||name.equals("姓名/暱稱 Name")||pass.equals("密碼 Password")||cPass.equals("確認密碼 Confirm Password")) {
					JOptionPane.showMessageDialog(null, "註冊資料有缺漏，請補入。");
				}
				else if(!pass.equals(cPass)) {
					JOptionPane.showMessageDialog(null, "密碼與確認密碼不相符，\n請重新輸入。");
				}
				else {
					try {
						int idInt = Integer.parseInt(id); 
						if(Query.registerVerify(idInt)) {
							Query.register(idInt, name, passField.getPassword());
							midField.setForeground(new Color(120,120,120));
				    		midField.setText("學號 Student ID");
							nameField.setForeground(new Color(120,120,120));
				    		nameField.setText("姓名/暱稱 Name");
				    		passField.setForeground(new Color(120,120,120));
				    		passField.setEchoChar((char)0);
				    		passField.setText("密碼 Password");
				    		checkPassField.setText("");
							JOptionPane.showMessageDialog(null, "註冊成功，\n請返回登入頁面登入。");
						}
						else {
							JOptionPane.showMessageDialog(null, "此帳號已註冊為用戶，\n請直接登入。");
						}
					} catch(NumberFormatException ne) {
						JOptionPane.showMessageDialog(null, "學號格式有誤，\n請重新輸入。");
						return;
					}
				}
			}
		}
		ActionListener button = new RegisterListener();
		this.registerButton.addActionListener(button);
	}
	
}
