import java.awt.AWTException;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
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
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SchedulePanel extends JPanel {

	private String memberID;
	private String memberName;
	private JLabel imgLabel;
	private JLabel titleLabel;
	private JLabel subtitleLabel;
	
	private JPanel adjustCoursePanel;
	private JTextField adjustCourseInput;
	private JToggleButton changeModeButton;
	private JButton adjustCourse;
		
	private JPanel linkPanel;
	private JLabel memberNameLabel;
	private JButton linkLabel;
	private JButton logoutLabel;
	private JPanel currentCoursePanel;
	private JLabel currentCourseTitle;
	private JLabel blank;
	private JLabel creditLabel;
	private JButton saveSchedule;
	
	//課表不考慮週六以及6:00-8:00,21:00以後時段
	private JPanel timeTablePanel;
	private Random random = new Random();
	private ArrayList<JLabel> dayL = new ArrayList<JLabel>();
	private JLabel monLabel = new JLabel("Mon");
	private JLabel tueLabel = new JLabel("Tue");
	private JLabel wedLabel = new JLabel("Wed");
	private JLabel thuLabel = new JLabel("Thu");
	private JLabel friLabel = new JLabel("Fri");
	private ArrayList<JLabel> timeL = new ArrayList<JLabel>();
	private JLabel timeLabel1 = new JLabel("08-09");
	private JLabel timeLabel2 = new JLabel("09-10");
	private JLabel timeLabel3 = new JLabel("10-11");
	private JLabel timeLabel4 = new JLabel("11-12");
	private JLabel timeLabelC = new JLabel("12-13");
	private JLabel timeLabelD = new JLabel("13-14");
	private JLabel timeLabel5 = new JLabel("14-15");
	private JLabel timeLabel6 = new JLabel("15-16");
	private JLabel timeLabel7 = new JLabel("16-17");
	private JLabel timeLabel8 = new JLabel("17-18");
	private JLabel timeLabelE = new JLabel("18-19");
	private JLabel timeLabelF = new JLabel("19-20");
	private JLabel timeLabelG = new JLabel("20-21");

	public SchedulePanel() {
		createComp();
		setLayout(new GridBagLayout());
		setBackground(Color.white);
		GridBagConstraints fin = new GridBagConstraints();
		fin.gridx = 0;
		fin.gridy = 0;
		fin.gridwidth = 1;
		fin.gridheight = 2;
		fin.anchor = GridBagConstraints.WEST;
		fin.insets = new Insets(20,0,0,0);
		add(imgLabel,fin);
		fin.anchor = GridBagConstraints.SOUTHWEST;
		fin.insets = new Insets(20,30,0,0);
		fin.gridx = 1;
		fin.gridheight = 1;
		add(titleLabel,fin);
		fin.gridx = 2;
		fin.insets = new Insets(20,12,7,0);
		add(subtitleLabel,fin);
		fin.anchor = GridBagConstraints.WEST;
		fin.gridx = 4;
		fin.insets = new Insets(20,25,0,0);
		add(linkPanel,fin);
		fin.gridx = 1;
		fin.gridy = 1;
		fin.gridwidth = 2;
		fin.insets = new Insets(0,0,0,0);
		fin.ipady = 15;
		fin.fill = GridBagConstraints.HORIZONTAL;
		add(adjustCoursePanel,fin);
		fin.gridx = 3;
		fin.gridwidth = 1;
		fin.ipadx = -30;
		fin.ipady = -7;
		add(adjustCourse,fin);
		fin.gridx = 4;
		fin.gridy = 3;
		fin.ipady = 10;
		fin.insets = new Insets(10,30,0,0);
		fin.anchor = GridBagConstraints.SOUTH;
		add(saveSchedule,fin);
		fin.gridx = 0;
		fin.gridy = 4;
		fin.gridwidth = 5;
		fin.gridheight = 1;
		fin.insets = new Insets(5,0,0,0);
		fin.fill = GridBagConstraints.HORIZONTAL;
		add(creditLabel,fin);
	}
	
	public void createComp() {
		this.imgLabel = new JLabel(new ImageIcon(new ImageIcon("logo.png").getImage().getScaledInstance(81, 103, Image.SCALE_SMOOTH)));
		this.titleLabel = new JLabel("NCCU Course Schedule Planner");
		this.titleLabel.setFont(new Font("Avenir-BlackOblique",Font.PLAIN,34));
		this.subtitleLabel = new JLabel("\"see see ur schedule\"");
		this.subtitleLabel.setFont(new Font("Avenir-LightOblique",Font.PLAIN,20));
		
		this.adjustCoursePanel = new JPanel();
		this.adjustCoursePanel.setLayout(new GridBagLayout());
		this.adjustCoursePanel.setBackground(Color.white);
		this.changeModeButton = new JToggleButton();
		this.changeModeButton.setBorderPainted(false);
		this.changeModeButton.setSelectedIcon(new ImageIcon(new ImageIcon("toggle_minus.png").getImage().getScaledInstance(70, 40, Image.SCALE_SMOOTH)));
		this.changeModeButton.setIcon(new ImageIcon(new ImageIcon("toggle_plus.png").getImage().getScaledInstance(70, 40, Image.SCALE_SMOOTH)));
		addChangeModeListener();
		this.adjustCourseInput = new JTextField();
		this.adjustCourseInput.setFont(new Font("Avenir",Font.PLAIN,20));
		Border redBorder = new LineBorder(new Color(240,85,67), 2, true);
		this.adjustCourseInput.setBorder(redBorder);
		GridBagConstraints acp = new GridBagConstraints();
		acp.gridx = 0;
		acp.gridy = 0;
		acp.anchor = GridBagConstraints.WEST;
		acp.insets = new Insets(0,5,0,0);
		this.adjustCoursePanel.add(changeModeButton,acp);
		acp.anchor = GridBagConstraints.EAST;
		acp.ipady = 12;
		acp.gridx = 1;
		acp.ipadx = 610;
		acp.insets = new Insets(0,10,0,0);
		this.adjustCoursePanel.add(adjustCourseInput,acp);
		
		this.adjustCourse = new JButton("+");
		this.adjustCourse.setFocusable(false); 
		this.adjustCourse.setForeground(new Color(240,85,67));
		this.adjustCourse.setFont(new Font("Avenir-Black",Font.PLAIN,38));
		addButtonListenerPlus();
		
		this.memberNameLabel = new JLabel("Hi");
		this.memberNameLabel.setFont(new Font("Avenir",Font.PLAIN,18));
		this.memberNameLabel.setForeground(new Color(86,122,154));
		
		Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
		fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		this.logoutLabel = new JButton("登出");
		this.logoutLabel.setFont(new Font("Avenir",Font.PLAIN,16).deriveFont(fontAttributes));
		this.logoutLabel.setForeground(new Color(86,122,154));
		this.logoutLabel.setBorderPainted(false);
		this.logoutLabel.setOpaque(false);
		this.logoutLabel.setBackground(Color.WHITE);
		
		this.linkLabel = new JButton("政大全校課程查詢系統");
		this.linkLabel.setFont(new Font("Avenir",Font.PLAIN,16).deriveFont(fontAttributes));
		this.linkLabel.setForeground(new Color(86,122,154));
		this.linkLabel.setBorderPainted(false);
		this.linkLabel.setOpaque(false);
		this.linkLabel.setBackground(Color.WHITE);
		try {
			addLinkListener();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		this.linkPanel = new JPanel();
		this.linkPanel.setBackground(Color.WHITE);
		this.linkPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints lp = new GridBagConstraints();
		lp.gridx = 0;
		lp.gridy = 0;
		lp.insets = new Insets(0,70,0,0);
		lp.anchor = GridBagConstraints.NORTHEAST;
		this.linkPanel.add(this.memberNameLabel,lp);
		lp.insets = new Insets(0,0,0,0);
		lp.gridx = 1;
		this.linkPanel.add(this.logoutLabel,lp);
		lp.gridx = 0;
		lp.gridy = 1;
		lp.gridwidth = 2;
		this.linkPanel.add(this.linkLabel,lp);
		
		this.saveSchedule = new JButton("下載課表");
		this.saveSchedule.setFocusable(false); 
		this.saveSchedule.setForeground(new Color(80,80,80));
		this.saveSchedule.setFont(new Font("Avenir",Font.PLAIN,20));
		addDownloadListener();
		
		this.creditLabel = new JLabel("2021 NCCU OOP Project Group 4");
		this.creditLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.creditLabel.setFont(new Font("Avenir-BookOblique",Font.PLAIN,14));
		this.creditLabel.setForeground(new Color(122,122,122));
	}
	
	public void setMemberNameLabel() {
		this.memberNameLabel.setText(String.format("Hi, %s", memberName));
	}
	
	public JPanel getTimeTablePanel() {
		return this.timeTablePanel;
	}
	
	public JPanel getCurrentCoursePanel() {
		return this.currentCoursePanel;
	}
	
	public void recordMemberID(int mid) {
		this.memberID = Integer.toString(mid);
	}
	
	public void recordVisitor(String visitor) {
		this.memberID = visitor;
	}
	
	public void recordMemberName(int mid) {
		if(mid == 0) {
			this.memberName = "visitor";
		}
		else{
			this.memberName = Query.getMemberName(mid);
		}
	}
	
	public void createTimeTable(ArrayList<Course> courseList) {
		this.timeTablePanel = new JPanel();
		this.timeTablePanel.setLayout(new GridBagLayout());
		this.timeTablePanel.setBackground(Color.white);
		Border border = new LineBorder(new Color(130,130,130), 2, true);
		this.timeTablePanel.setBorder(border);
		this.dayL.add(monLabel);
		this.dayL.add(tueLabel);
		this.dayL.add(wedLabel);
		this.dayL.add(thuLabel);
		this.dayL.add(friLabel);
		this.timeL.add(timeLabel1);
		this.timeL.add(timeLabel2);
		this.timeL.add(timeLabel3);
		this.timeL.add(timeLabel4);
		this.timeL.add(timeLabelC);
		this.timeL.add(timeLabelD);
		this.timeL.add(timeLabel5);
		this.timeL.add(timeLabel6);
		this.timeL.add(timeLabel7);
		this.timeL.add(timeLabel8);
		this.timeL.add(timeLabelE);
		this.timeL.add(timeLabelF);
		this.timeL.add(timeLabelG);
		
		GridBagConstraints init = new GridBagConstraints();
		init.gridx = 0;
		init.gridy = 0;
		init.ipadx = 12;
		init.ipady = 10;
		init.fill = GridBagConstraints.BOTH;
		init.insets = new Insets(0,0,0,0);
		JLabel blank = new JLabel();
		blank.setOpaque(true);
		blank.setBackground(new Color(166,213,219));
		this.timeTablePanel.add(blank,init);
		init.ipadx = 110;
		init.insets = new Insets(0,2,0,0);
		init.gridx += 1;
		Color dtWordColor = new Color(100,100,100);
		Color dayBGColor = new Color(166,213,219);
		Font dayFont = new Font("Avenir-Light",Font.PLAIN,24);
		for(JLabel day:dayL) {
			day.setHorizontalAlignment(SwingConstants.CENTER);
			day.setOpaque(true);
			day.setForeground(dtWordColor);
			day.setBackground(dayBGColor);
			day.setFont(dayFont);
			init.ipadx += init.gridx*2;
			this.timeTablePanel.add(day,init);
			init.gridx += 1;
		}	
		init.gridy = 1;
		Font timeFont = new Font("Avenir-Light",Font.PLAIN,18);
		init.gridx = 0;
		init.ipadx = 12;
		init.ipady = 7;
		init.fill = GridBagConstraints.HORIZONTAL;
		init.insets = new Insets(2,0,0,0);
		for(JLabel time:timeL) {
			time.setHorizontalAlignment(SwingConstants.CENTER);
			time.setOpaque(true);
			time.setForeground(dtWordColor);
			time.setBackground(Color.white);
			time.setFont(timeFont);
			this.timeTablePanel.add(time,init);
			init.gridy += 1;
		}
		GridBagConstraints co = new GridBagConstraints();
		co.gridx = 0;
		co.gridy = 0;
		co.anchor = GridBagConstraints.WEST;
		Font cNameFont = new Font("Avenir-Medium",Font.PLAIN,14);
		Color cWordColor = new Color(70,70,70);
		Font cInfoFont = new Font("Avenir",Font.PLAIN,13);
		HashMap<String,Integer> sg = Query.dbSessionGroup(memberID);
		for(String session:sg.keySet()) {
			JPanel courseBlock = new JPanel();
			int numClash = sg.get(session);
			courseBlock.setLayout(new GridLayout(1,numClash));
			ArrayList<Course> sc = Query.getSessionCourse(memberID,session);
			GridBagConstraints ses = sc.get(0).sessionVisualization(session);
			for(Course cour:sc) {
				co.insets = new Insets(0,(numClash-1)*(30-numClash),0,0);
				JButton c = new JButton();
				c.setLayout(new GridBagLayout());
				JLabel name = new JLabel(cour.getCourse_CHName());
				name.setFont(cNameFont);
				name.setForeground(cWordColor);
				c.add(name,co);
				co.gridy += 1;
				JLabel id = new JLabel(cour.getCourseID()+" "+cour.getInstructor_CHName().split("、")[0]); //如果有多位老師，只顯示第一位
				id.setFont(cInfoFont);
				id.setForeground(cWordColor);
				c.add(id,co);
				co.gridy += 1;
				if(ses.gridheight > 1) {
					JLabel classroom = new JLabel("@ "+cour.getClassroom());
					classroom.setFont(cInfoFont);
					classroom.setForeground(cWordColor);
					c.add(classroom,co);
				}
				c.setOpaque(true);
				c.setBorderPainted(false);
				c.setPreferredSize(new Dimension(110/numClash,20));
				c.setBackground(new Color(random.nextInt(76)+180,random.nextInt(76)+180,random.nextInt(121)+135));
				addCourseInfoButtonListener(c,cour);
				courseBlock.add(c);
			}
			this.timeTablePanel.add(courseBlock,ses);
		}
		GridBagConstraints tt = new GridBagConstraints();
		tt.gridx = 0;
		tt.gridy = 2;
		tt.gridwidth = 4;
		tt.gridheight = 2;
		tt.anchor = GridBagConstraints.WEST;
		tt.fill = GridBagConstraints.HORIZONTAL;
		tt.insets = new Insets(5,0,0,0);
		add(this.timeTablePanel,tt);
	}
	
	public void addCourseInfoButtonListener(JButton courseInfo,Course cour) {
		class ClickListener implements ActionListener {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				JToggleButton lockButton = createLockButton(cour.getCourseID());
				addLockListener(lockButton,cour.getCourseID());
				ImageIcon icon = new ImageIcon("icon.png");
				String[] options = {"刪除本課程","OK"};
				JPanel info = new JPanel();
				info.setLayout(new GridBagLayout());
				GridBagConstraints iLay = new GridBagConstraints();
				iLay.gridy = 0;
				iLay.insets = new Insets(3,0,0,0);
				iLay.anchor = GridBagConstraints.WEST;
				JLabel cname = new JLabel(String.format("課程名稱：%s",cour.getCourse_CHName()));
				info.add(cname,iLay);
				iLay.gridy += 1;
				JLabel cid = new JLabel(String.format("課程代碼：%s",cour.getCourseID()));
				info.add(cid,iLay);
				iLay.gridy += 1;
				JLabel cses = new JLabel(String.format("課程時段：%s",cour.getSessionWord()));
				info.add(cses,iLay);
				iLay.gridy += 1;
				JLabel iname = new JLabel(String.format("教師姓名：%s",cour.getInstructor_CHName()));
				info.add(iname,iLay);
				JPanel show = new JPanel(new GridBagLayout());
				iLay.gridx = 0;
				iLay.gridy = 0;
				iLay.insets = new Insets(0,0,0,0);
				show.add(info,iLay);
				iLay.gridx = 1;
				iLay.insets = new Insets(0,10,0,0);
				show.add(lockButton,iLay);
				int result = JOptionPane.showOptionDialog(null,show,"課程詳細資訊",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,icon,options,options[1]);
				if(result == 0) {
					removeTimeTable();
					removeCurrentCoursePanel();
					Query.dbDeleteResult(memberID,cour.getCourseID());
					updateTimeTable(Query.dbGetResult(memberID)[1]);
					updateCurrentCoursePanel();
				}
			}
		}
		ActionListener button = new ClickListener();
		courseInfo.addActionListener(button);
	}
	
	public JToggleButton createLockButton(String CourseID) {
		JToggleButton lockButton = new JToggleButton();
		lockButton.setBorderPainted(false);
		lockButton.setSelectedIcon(new ImageIcon(new ImageIcon("locked.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		lockButton.setIcon(new ImageIcon(new ImageIcon("unlocked.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		lockButton.setSelected(Query.getLockStatus(memberID,CourseID));
		return lockButton;
	}
	
	public void updateTimeTable(ArrayList<Course> courseList) {
		GridBagConstraints co = new GridBagConstraints();
		co.gridx = 0;
		co.gridy = 0;
		co.anchor = GridBagConstraints.WEST;
		Font cNameFont = new Font("Avenir-Medium",Font.PLAIN,14);
		Color cWordColor = new Color(70,70,70);
		Font cInfoFont = new Font("Avenir",Font.PLAIN,13);
		HashMap<String,Integer> sg = Query.dbSessionGroup(memberID);
		for(String session:sg.keySet()) {
			JPanel courseBlock = new JPanel();
			int numClash = sg.get(session);
			courseBlock.setLayout(new GridLayout(1,numClash));
			ArrayList<Course> sc = Query.getSessionCourse(memberID,session);
			GridBagConstraints ses = sc.get(0).sessionVisualization(session);
			for(Course cour:sc) {
				co.insets = new Insets(0,(numClash-1)*(30-numClash),0,0);
				JButton c = new JButton();
				c.setLayout(new GridBagLayout());
				JLabel name = new JLabel(cour.getCourse_CHName());
				name.setFont(cNameFont);
				name.setForeground(cWordColor);
				c.add(name,co);
				co.gridy += 1;
				JLabel id = new JLabel(cour.getCourseID()+" "+cour.getInstructor_CHName().split("、")[0]); //如果有多位老師，只顯示第一位
				id.setFont(cInfoFont);
				id.setForeground(cWordColor);
				c.add(id,co);
				co.gridy += 1;
				if(ses.gridheight > 1) {
					JLabel classroom = new JLabel("@ "+cour.getClassroom());
					classroom.setFont(cInfoFont);
					classroom.setForeground(cWordColor);
					c.add(classroom,co);
				}
				c.setOpaque(true);
				c.setBorderPainted(false);
				c.setPreferredSize(new Dimension(110/numClash,20));
				c.setBackground(new Color(random.nextInt(76)+180,random.nextInt(76)+180,random.nextInt(121)+135));
				addCourseInfoButtonListener(c,cour);
				courseBlock.add(c);
			}
			this.timeTablePanel.add(courseBlock,ses);
		}
		this.timeTablePanel.validate();
	}

	public void removeTimeTable() {
		this.timeTablePanel.removeAll();
		GridBagConstraints init = new GridBagConstraints();
		init.gridx = 0;
		init.gridy = 0;
		init.ipadx = 12;
		init.ipady = 10;
		init.fill = GridBagConstraints.BOTH;
		init.insets = new Insets(0,0,0,0);
		JLabel blank = new JLabel();
		blank.setOpaque(true);
		blank.setBackground(new Color(166,213,219));
		this.timeTablePanel.add(blank,init);
		init.ipadx = 110;
		init.insets = new Insets(0,2,0,0);
		init.gridx += 1;
		Color dtWordColor = new Color(100,100,100);
		Color dayBGColor = new Color(166,213,219);
		Font dayFont = new Font("Avenir-Light",Font.PLAIN,24);
		for(JLabel day:dayL) {
			day.setHorizontalAlignment(SwingConstants.CENTER);
			day.setOpaque(true);
			day.setForeground(dtWordColor);
			day.setBackground(dayBGColor);
			day.setFont(dayFont);
			init.ipadx += init.gridx*2;
			this.timeTablePanel.add(day,init);
			init.gridx += 1;
		}	
		init.gridy = 1;
		Font timeFont = new Font("Avenir-Light",Font.PLAIN,18);
		init.gridx = 0;
		init.ipadx = 12;
		init.ipady = 7;
		init.fill = GridBagConstraints.HORIZONTAL;
		init.insets = new Insets(2,0,0,0);
		for(JLabel time:timeL) {
			time.setHorizontalAlignment(SwingConstants.CENTER);
			time.setOpaque(true);
			time.setForeground(dtWordColor);
			time.setBackground(Color.white);
			time.setFont(timeFont);
			this.timeTablePanel.add(time,init);
			init.gridy += 1;
		}
		this.timeTablePanel.revalidate();
		this.timeTablePanel.repaint();
	}
	
	public void createCurrentCoursePanel(ArrayList<String> inputCourseList) {
		Font curCTFont = new Font("Avenir",Font.PLAIN,18);
		Font curCFont = new Font("Avenir",Font.PLAIN,17);
		this.currentCoursePanel = new JPanel();
		this.currentCoursePanel.setPreferredSize(new Dimension(50,500));
		this.currentCoursePanel.setBackground(new Color(240,240,240));
		this.currentCoursePanel.setLayout(new GridBagLayout());
		this.currentCourseTitle = new JLabel("目前顯示課程列表");
		this.currentCourseTitle.setFont(curCTFont);
		GridBagConstraints cT = new GridBagConstraints();
		cT.gridx = 0;
		cT.gridy = 0;
		cT.insets = new Insets(50,0,10,0);
		this.currentCoursePanel.add(currentCourseTitle,cT);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(3,5,0,0);
		for(String input:inputCourseList) {
			JLabel cI = new JLabel(input);
			cI.setFont(curCFont);
			this.currentCoursePanel.add(cI,c);
			c.gridy += 1;
		}
		c.insets = new Insets(3,5,485-inputCourseList.size()*27,0);
		this.blank = new JLabel();
		this.currentCoursePanel.add(blank,c);
		GridBagConstraints cc = new GridBagConstraints();
		cc.gridx = 4;
		cc.gridy = 1;
		cc.gridwidth = 1;
		cc.gridheight = 3;
		cc.ipadx = -20;
		cc.anchor = GridBagConstraints.NORTH;
		cc.fill = GridBagConstraints.HORIZONTAL;
		cc.insets = new Insets(10,15,0,0);
		add(this.currentCoursePanel,cc);
	}

	@SuppressWarnings("unchecked")
	public void updateCurrentCoursePanel() {
		Font curCFont = new Font("Avenir",Font.PLAIN,17);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(3,5,0,0);
		for(String input:(ArrayList<String>)Query.dbGetResult(memberID)[0]) {
			JLabel cI = new JLabel(input);
			cI.setFont(curCFont);
			this.currentCoursePanel.add(cI,c);
			c.gridy += 1;
		}
		c.anchor = GridBagConstraints.SOUTH;
		c.insets = new Insets(3,5,485-Query.dbGetResult(memberID)[0].size()*27,0);
		this.currentCoursePanel.add(this.blank,c);
		this.currentCoursePanel.validate();
	}
	
	public void removeCurrentCoursePanel() {
		this.currentCoursePanel.removeAll();
		GridBagConstraints cT = new GridBagConstraints();
		cT.gridx = 0;
		cT.gridy = 0;
		cT.insets = new Insets(50,0,10,0);
		this.currentCoursePanel.add(currentCourseTitle,cT);
		this.currentCoursePanel.revalidate();
		this.currentCoursePanel.repaint();
	}
	
	public void addLinkListener() throws URISyntaxException {
		final URI uri = new URI("https://qrysub.nccu.edu.tw/");
		class OpenUrlAction implements ActionListener {
		      public void actionPerformed(ActionEvent e) {
		    	  open(uri);
		      }
		}
		ActionListener link = new OpenUrlAction();
		this.linkLabel.addActionListener(link);
	}
	
	private static void open(URI uri) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) { 
				e.printStackTrace();
			}
		}
		else {
			System.out.println("not supported");
		}
	}
	
	public void addLogOutListener(JPanel main,LoginPanel login) {
		class LogOutListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				Query.dbDeleteResult();
				removeTimeTable();
				removeCurrentCoursePanel();
				changeModeButton.setSelected(false);
				login.getCourseInfoField().setText("  XXX306XXX-000111222-程式設計二");
				login.getCourseInfoField().setForeground(new Color(120,120,120));
				((CardLayout)main.getLayout()).show(main,"login");
			}
		}
		ActionListener l = new LogOutListener();
		logoutLabel.addActionListener(l);
	}
	
	public void addChangeModeListener() {
		class addDropListener implements ChangeListener {
			public void stateChanged(ChangeEvent e) {
				for(ActionListener al:adjustCourse.getActionListeners()) {
					adjustCourse.removeActionListener(al);
				}
				Color minusBlue = new Color(110,157,206);
				Color plusRed = new Color(240,85,67);
				Border redBorder = new LineBorder(plusRed, 2, true);
				Border blueBorder =  new LineBorder(minusBlue, 2, true);
				if(changeModeButton.isSelected() == true) {
					adjustCourse.setText("−");
					adjustCourse.setForeground(minusBlue);
					addButtonListenerMinus();
					adjustCourseInput.setBorder(blueBorder);
				}
				else {
					adjustCourse.setText("+");
					adjustCourse.setForeground(plusRed);
					addButtonListenerPlus();
					adjustCourseInput.setBorder(redBorder);
				}
			}
		}
		ChangeListener button = new addDropListener();
		this.changeModeButton.addChangeListener(button);
	}
	
	public void addLockListener(JToggleButton lockButton,String courseID) {
		class lockListener implements ChangeListener {
			public void stateChanged(ChangeEvent e) {
				if(lockButton.isSelected() == true) {
					Query.dbLockControl(memberID,courseID,true);
				}
				else {
					Query.dbLockControl(memberID,courseID,false);
				}
			}
		}
		ChangeListener button = new lockListener();
		lockButton.addChangeListener(button);
	}
	
	public void addButtonListenerPlus() {
		class ClickListener implements ActionListener {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				String aI = adjustCourseInput.getText();
				if(aI.equals("")) {
					//nothing happens
				}
				//不知道為什麼要擺在這裡才能在drop mode呼叫
				else if(aI.equals("all")) {
					Query.dbDeleteResult(memberID);
				}
				else {
					ArrayList<String> extraList = new ArrayList<String>();
					for (String course:aI.split("-")) {
						extraList.add(course);
					}
					removeTimeTable();
					removeCurrentCoursePanel();
					Query.dbSearch(memberID,extraList);
					updateTimeTable(Query.dbGetResult(memberID)[1]);
					updateCurrentCoursePanel();
					adjustCourseInput.setText("");
				}
			}
		}
		ActionListener button = new ClickListener();
		this.adjustCourse.addActionListener(button);
	}
	
	public void addButtonListenerMinus() {
		class ClickListener implements ActionListener {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				String aI = adjustCourseInput.getText();
				if(aI.equals("")) {
					//nothing happens
				}
				else {
					ArrayList<String> removeList = new ArrayList<String>();
					for (String course:aI.split("-")) {
						removeList.add(course);
					}
					removeTimeTable();
					removeCurrentCoursePanel();
					Query.dbDeleteResult(memberID,removeList);
					updateTimeTable(Query.dbGetResult(memberID)[1]);
					updateCurrentCoursePanel();
					adjustCourseInput.setText("");
				}
			}
		}
		ActionListener button = new ClickListener();
		this.adjustCourse.addActionListener(button);
	}
	
	public void addDownloadListener() {
		class CaptureListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				ImageIcon icon = new ImageIcon("icon.png");
				Point p = timeTablePanel.getLocationOnScreen();
			    Dimension dim = timeTablePanel.getSize();
			    Rectangle scheduleField = new Rectangle(p, dim);
			    Robot robot = null;
				try {
					robot = new Robot();
				} catch (AWTException e2) {
					e2.printStackTrace();
				}  
			    BufferedImage scheduleImg = robot.createScreenCapture(scheduleField);
			    String home = System.getProperty("user.home");
				File scheduleFile = new File(home+"/Downloads/schedule.png"); //需考慮檔案重名
				try {
					ImageIO.write(scheduleImg,"png",scheduleFile);
					JOptionPane.showMessageDialog(null,"成功下載課表","Success",JOptionPane.PLAIN_MESSAGE,icon);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		ActionListener button = new CaptureListener();
		this.saveSchedule.addActionListener(button);
	}
	
}
