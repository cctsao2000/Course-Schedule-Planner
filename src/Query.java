import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Query {
	
	private static final String SERVER = "jdbc:mysql://";
	private static final String DATABASE = "";
	private static final String USERNAME = "";
	private static final String PASSWORD = "";
	private static final String ENCODING = "&useUnicode=true&characterEncoding=UTF-8";
	
	public Query(String input,SchedulePanel display) {
		if(input.equals("  XXX306XXX-000111222-程式設計二")) {
			input = "";
		}
		ArrayList<String> inputCourseList = new ArrayList<String>();
		for (String course:input.split("-")) {
			inputCourseList.add(course);
		}
		dbSearch(inputCourseList);
	}
	
	public void dbSearch(ArrayList<String> input) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			boolean id;
			for(int i=0; i<input.size(); i++) {
				String inp = input.get(i);
				try {
					Integer.parseInt(inp.substring(6));
					id = true;
				} catch (NumberFormatException|IndexOutOfBoundsException e) {
					id = false;
				}
				if(id == true) {
					query = String.format("SELECT CourseID,Credit,Course_CHName,Instructor_CHName,Session,Classroom FROM Semester_2021_courses WHERE CourseID = '%s'", inp);
				}
				else {
					query = String.format("SELECT CourseID,Credit,Course_CHName,Instructor_CHName,Session,Classroom FROM Semester_2021_courses WHERE Course_CHName = '%s'", inp);
				}
				boolean hasResultSet = stat.execute(query);
				if (hasResultSet) {
					ResultSet result = stat.getResultSet();
					dbAddResult(result);
					result.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Course> dbSearch(ArrayList<String> input, boolean temporary) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		ArrayList<Course> output = new ArrayList<Course>();
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			boolean id;
			for(int i=0; i<input.size(); i++) {
				String inp = input.get(i);
				try {
					Integer.parseInt(inp.substring(6));
					id = true;
				} catch (NumberFormatException|IndexOutOfBoundsException e) {
					id = false;
				}
				if(id == true) {
					query = String.format("SELECT CourseID,Credit,Course_CHName,Instructor_CHName,Session,Classroom FROM SearchResult WHERE CourseID = '%s'", inp);
				}
				else {
					query = String.format("SELECT CourseID,Credit,Course_CHName,Instructor_CHName,Session,Classroom FROM SearchResult WHERE Course_CHName = '%s'", inp);
				}
				boolean hasResultSet = stat.execute(query);
				if (hasResultSet) {
					ResultSet result = stat.getResultSet();
					while(result.next()){
						output.add(new Course(result.getString(1),result.getDouble(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6)));
					}
					result.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

	public HashMap<String,Integer> dbSessionGroup() {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		HashMap<String,Integer> sessionGroup = new HashMap<String,Integer>();
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			query = "SELECT Session,COUNT(1) FROM SearchResult GROUP BY Session;";
			boolean hasResultSet = stat.execute(query);
			if (hasResultSet) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					sessionGroup.put(result.getString(1),result.getInt(2));
				}
//				System.out.println(sessionGroup);
				result.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sessionGroup;
	}

	public ArrayList<Course> getSessionCourse(String session){
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		ArrayList<Course> sessionCourse = new ArrayList<Course>();
		Connection conn = null;
		query = String.format("SELECT CourseID,Credit,Course_CHName,Instructor_CHName,Session,Classroom FROM SearchResult WHERE Session = '%s'", session);
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			boolean hasResultSet = stat.execute(query);
			if (hasResultSet) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					sessionCourse.add(new Course(result.getString(1),result.getDouble(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6)));
				}
				result.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sessionCourse;
	}
	
	public static void dbNewTable(String tableName) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		query = String.format("CREATE TABLE %s (CourseID text,Credit double(10,2),Course_CHName text,Instructor_CHName text,Session text,Classroom text);", tableName);
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			stat.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList[] dbGetResult() {
		ArrayList<String> coursesName = new ArrayList<String>();
		ArrayList<Course> courses = new ArrayList<Course>();
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			query = "SELECT * FROM SearchResult";
			boolean hasResultSet = stat.execute(query);
			if (hasResultSet) {
				ResultSet result = stat.getResultSet();
				addCourseList(result,coursesName,courses);
				result.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		ArrayList[] result = {coursesName,courses};
		return result;
	}
	
	public void addCourseList(ResultSet result,ArrayList<String> nameoutput,ArrayList<Course> courseoutput) throws SQLException {
		while (result.next()) {
			if (nameoutput.contains(result.getString(3))!=true) {
				nameoutput.add(result.getString(3));
			}
			courseoutput.add(new Course(result.getString(1),result.getDouble(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6)));
		}
	}
	
	public void dbAddResult(ResultSet result) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			while (result.next()) {
				query = String.format("INSERT INTO SearchResult VALUES ('%s',%.2f,'%s','%s','%s','%s',0);",result.getString(1),result.getDouble(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6));
				stat.execute(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void dbLockControl(String input, boolean lock) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			if (lock == true) {
				query = String.format("UPDATE SearchResult SET Locked = 1 WHERE CourseID = '%s'", input);
			}
			else {
				query = String.format("UPDATE SearchResult SET Locked = 0 WHERE CourseID = '%s'", input);
			}
			stat.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean getLockStatus(String input) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		boolean status = false;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			query = String.format("SELECT Locked FROM SearchResult WHERE CourseID = '%s'", input);
			stat.execute(query);
			ResultSet result = stat.getResultSet();
			if (result.next()) {
				status = result.getBoolean(1);
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return status;
	}
	
	public void dbDeleteResult(ArrayList<String> input) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			boolean id;
			for(int i=0; i<input.size(); i++) {
				String inp = input.get(i);
				try {
					Integer.parseInt(inp.substring(6));
					id = true;
				} catch (NumberFormatException|IndexOutOfBoundsException e) {
					id = false;
				}
				if(id == true) {
					query = String.format("DELETE FROM SearchResult WHERE CourseID = '%s' and Locked <> 1", inp);
				}
				else {
					query = String.format("DELETE FROM SearchResult WHERE Course_CHName = '%s'and Locked <> 1", inp);
				}
				stat.execute(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void dbDeleteResult(String input) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			query = String.format("DELETE FROM SearchResult WHERE CourseID = '%s' and Locked <> 1", input);
			stat.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//開發者隱藏功能：輸入all能把沒有鎖定的課都drop掉
	public void dbDeleteResult(boolean input) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			query = "DELETE FROM SearchResult WHERE Locked <> 1";
			stat.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void dbDeleteResult() {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		query = "DELETE FROM SearchResult;";
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			stat.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void showResultSet(ResultSet result) throws SQLException {
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			if (i >= 1){
				if(i!=1) {
					System.out.print(", ");
				}
				System.out.print(metaData.getColumnLabel(i));
			}
		}
		System.out.println();
		while (result.next()) {
			for (int i = 1; i <= columnCount; i++) {
				if (i >= 1){
					if(i!=1) {
						System.out.print(", ");
					}
					System.out.print(result.getString(i));
				}
			}
			System.out.println();
		}
	}
	
}
