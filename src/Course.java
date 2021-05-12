import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Course {
	
	private String courseID;
	private double credit;
	private String course_CHName;
	private String instructor_CHName;
	private String sessionWord;
	private GridBagConstraints session;
	private String classroom;
	
	public Course(String courseID,double credit,String course_CHName,String instructor_CHName,String session,String classroom) {
		this.courseID = courseID;
		this.credit = credit;
		this.course_CHName = course_CHName;
		this.instructor_CHName = instructor_CHName;
		this.classroom = classroom;
		this.sessionWord = session;
		this.session = sessionVisualization(session);
	}
	
	public String getCourseID() {
		return courseID;
	}
	public double getCredit() {
		return credit;
	}
	public String getCourse_CHName() {
		return course_CHName;
	}
	public String getInstructor_CHName() {
		return instructor_CHName;
	}
	
	public String getSessionWord() {
		return sessionWord;
	}
	
	public GridBagConstraints getSession() {
		return session;
	}
	public String getClassroom() {
		return classroom;
	}
	
	public GridBagConstraints sessionVisualization(String session) {
		GridBagConstraints s = new GridBagConstraints();
		String weekday = session.substring(0,3);
		int startTime = Integer.parseInt(session.substring(3,5));
		int endTime = Integer.parseInt(session.substring(6,8));
		
		if(weekday.equals("mon")) {
			s.gridx = 1;
		}
		else if (weekday.equals("tue")) {
			s.gridx = 2;
		}
		else if (weekday.equals("wed")) {
			s.gridx = 3;
		}
		else if (weekday.equals("thu")) {
			s.gridx = 4;
		}
		else if (weekday.equals("fri")) {
			s.gridx = 5;
		}
		else {
			System.out.println("N/A");
		}		
		s.gridy = startTime-7;
		s.gridheight = endTime-startTime;
		s.fill = GridBagConstraints.BOTH;
		s.insets = new Insets(2,2,0,0);
		return s;
	}

}
