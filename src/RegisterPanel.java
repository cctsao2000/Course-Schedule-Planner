import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegisterPanel extends JPanel{
	
	private JLabel imgLabel;
	private JLabel joinLabel;
	private JPanel welcomePanel;
	
	private JLabel signupLabel;
	private JTextField midField;
	private JTextField nameField;
	private JTextField passField;
	private JTextField checkPassField;
	private JPanel infoPanel;
	private JLabel creditLabel;
	
	public RegisterPanel() {
		createComp();
		setLayout(new GridLayout(1,2));
		setBackground(Color.white);
		add(this.welcomePanel);
		add(this.infoPanel);
//		add(this.creditPanel);
	}
	
	public void createComp() {
		this.welcomePanel = new JPanel();
		this.welcomePanel.setLayout(new GridBagLayout());
		this.welcomePanel.setBackground(new Color(240,240,240));
		
		this.imgLabel = new JLabel(new ImageIcon(new ImageIcon("logo-cutout.png").getImage().getScaledInstance(185, 230, Image.SCALE_SMOOTH)));
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
		this.infoPanel.setBackground(Color.white);

		this.creditLabel = new JLabel("2021 NCCU OOP Project Group 4");
		this.creditLabel.setFont(new Font("Avenir-BookOblique",Font.PLAIN,22));
		this.creditLabel.setForeground(new Color(122,122,122));
	}

}
