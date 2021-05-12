import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		JFrame sp = new JFrame("NCCU Course Schedule Planner");
		sp.setSize(1214, 712);
		sp.setResizable(false);
		
		JPanel all = new JPanel();
		CardLayout c = new CardLayout();
		all.setLayout(c);
	
		LoginPanel login = new LoginPanel();
		SchedulePanel schedule = new SchedulePanel();
		
		all.add("login",login);
		all.add("schedule",schedule);
		c.show(all,"login");
		login.addButtonListener2(all,schedule);
		schedule.addLogOutListener(all,login);
		
		sp.add(all);
		sp.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	        	Query.dbDeleteResult();
	            e.getWindow().dispose();
	        }
	    });
		sp.setVisible(true);
		sp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
