import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 * NCCU Course Schedule Planner 
 * @author TSAO CHING CHIH
 * @version 1.2
 */

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
		RegisterPanel register = new RegisterPanel();
		
		all.add("login",login);
		all.add("schedule",schedule);
		all.add("register",register);
		c.show(all,"login");
		login.addButtonListener1(all,schedule);
		login.addButtonListener2(all,schedule);
		login.addButtonListener3(all,register);
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
