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
	
	public static String dbNewTable(String tableName) {
		String query = String.format("CREATE TABLE %s (CourseID text,Credit double(10,2),Course_CHName text,Instructor_CHName text,Session text,Classroom text,Locked boolean)", tableName);
		return query;
	}
	
	//註冊前檢查重複id
	public static boolean registerVerify(int id) {
		int count = -1;
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			query = String.format("SELECT COUNT(*) FROM Member WHERE MemberID = %d",id);
			ResultSet result = stat.executeQuery(query);
			if (result.next()) {
				count = result.getInt(1);
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
		if(count!=0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	//註冊
	public static void register(int id, String name, char[] pass) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			query = String.format("INSERT INTO Member VALUES (%d,'%s','%s')",id,name,String.valueOf(pass));
			stat.execute(query);
			stat.execute(dbNewTable(id+"Result"));
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
	
	//登入驗證
	public static boolean loginVerify(int id, char[] pass) {
		boolean verify = false;
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			query = String.format("SELECT Password FROM Member WHERE MemberID = %d",id);
			boolean hasResultSet = stat.execute(query);
			if(hasResultSet) {
				ResultSet result = stat.getResultSet();
				if(result.next()) {
					String realPass = result.getString(1);
					if(pass.length == realPass.length()) {
						for (int i=0;i<pass.length;i++) {
							verify = true;
							if(pass[i]!=realPass.charAt(i)) {
								verify = false;
								break;
							}
						}
					}
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
		return verify;
	}
	
	public static String getMemberName(int id) {
		String mName = "Visitor";
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			query = String.format("SELECT MemberName FROM Member WHERE MemberID = %d", id);
			stat.execute(query);
			ResultSet result = stat.getResultSet();
			if (result.next()) {
				mName = result.getString(1);
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
		return mName;
	}
	
	//登入頁面訪客搜尋
	public static void visitorInitSearch(String input) {
		if(input.equals("")||input.equals("  XXX306XXX-000111222-程式設計二")) {
			return;
		}
		ArrayList<String> inputCourseList = new ArrayList<String>();
		for (String course:input.split("-")) {
			inputCourseList.add(course);
		}
		dbSearch("Search",inputCourseList);
	}
	
	//根據課程代碼或名字去課程資料庫找
	public static void dbSearch(String user, ArrayList<String> input) {
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
				if(id) {
					query = String.format("SELECT CourseID,Credit,Course_CHName,Instructor_CHName,Session,Classroom FROM Semester_2021_courses WHERE CourseID = '%s'", inp);
				}
				else {
					query = String.format("SELECT CourseID,Credit,Course_CHName,Instructor_CHName,Session,Classroom FROM Semester_2021_courses WHERE Course_CHName = '%s'", inp);
				}
				boolean hasResultSet = stat.execute(query);
				if (hasResultSet) {
					ResultSet result = stat.getResultSet();
					dbAddResult(user,result);
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
	
	//將所搜課程根據時段分組
	public static HashMap<String,Integer> dbSessionGroup(String user) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		HashMap<String,Integer> sessionGroup = new HashMap<String,Integer>();
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			query = String.format("SELECT Session,COUNT(1) FROM %sResult GROUP BY Session", user);
			boolean hasResultSet = stat.execute(query);
			if (hasResultSet) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					sessionGroup.put(result.getString(1),result.getInt(2));
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
		return sessionGroup;
	}
	
	//取得同時段課程列表
	public static ArrayList<Course> getSessionCourse(String user, String session){
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		ArrayList<Course> sessionCourse = new ArrayList<Course>();
		Connection conn = null;
		query = String.format("SELECT CourseID,Credit,Course_CHName,Instructor_CHName,Session,Classroom FROM %sResult WHERE Session = '%s'", user, session);
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
	
	@SuppressWarnings("rawtypes")
	public static ArrayList[] dbGetResult(String user) {
		ArrayList<String> coursesName = new ArrayList<String>();
		ArrayList<Course> courses = new ArrayList<Course>();
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			query = String.format("SELECT * FROM %sResult", user);
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
	
	public static void addCourseList(ResultSet result,ArrayList<String> nameoutput,ArrayList<Course> courseoutput) throws SQLException {
		while (result.next()) {
			if (nameoutput.contains(result.getString(3))!=true) {
				nameoutput.add(result.getString(3));
			}
			courseoutput.add(new Course(result.getString(1),result.getDouble(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6)));
		}
	}
	
	public static void dbAddResult(String user, ResultSet result) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			while (result.next()) {
				query = String.format("INSERT INTO %sResult VALUES ('%s',%.2f,'%s','%s','%s','%s',0)",user,result.getString(1),result.getDouble(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6));
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
	
	//變更課程鎖定狀態
	public static void dbLockControl(String user, String input, boolean lock) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			if (lock == true) {
				query = String.format("UPDATE %sResult SET Locked = 1 WHERE CourseID = '%s'",user, input);
			}
			else {
				query = String.format("UPDATE %sResult SET Locked = 0 WHERE CourseID = '%s'",user, input);
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
	
	//取得鎖定狀態以生成鎖圖案
	public static boolean getLockStatus(String user, String input) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		boolean status = false;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			query = String.format("SELECT Locked FROM %sResult WHERE CourseID = '%s'", user, input);
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
	
	//輸入框課程刪除
	public static void dbDeleteResult(String user, ArrayList<String> input) {
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
					query = String.format("DELETE FROM %sResult WHERE CourseID = '%s' and Locked <> 1", user, inp);
				}
				else {
					query = String.format("DELETE FROM %sResult WHERE Course_CHName = '%s'and Locked <> 1", user, inp);
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
	
	//單堂課程按鍵刪除
	public static void dbDeleteResult(String user, String input) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			query = String.format("DELETE FROM %sResult WHERE CourseID = '%s' and Locked <> 1", user, input);
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
	public static void dbDeleteResult(String user) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			query = String.format("DELETE FROM %sResult WHERE Locked <> 1", user);
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
	
	//訪客登出紀錄刪除
	public static void dbDeleteResult() {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		query = "DELETE FROM SearchResult";
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
	
	public static void dbDeleteMember(int id) {
		String url = SERVER + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD + ENCODING;
		String query;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			query = String.format("DELETE FROM Member WHERE MemberID = %d", id);
			stat.execute(query);
			query = String.format("DROP TABLE %dResult", id);
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
