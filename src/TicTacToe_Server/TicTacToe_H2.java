package TicTacToe_Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.h2.tools.DeleteDbFiles;

public class TicTacToe_H2 {

	private static TicTacToe_H2 h2;

	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:~/TicTacToe";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	// only for test the DB and show the tables
	public static void main(String[] args) throws Exception {
		// deleteDB();
		// createTableWithPreparedStatement();
		// insertWithPreparedStatement(741,"default",00);
		// insertWithPreparedStatement(879,"computer",00);
		// selectPreparedStatement("select * from PERSON");
		// updateWithPreparedStatement("update PERSON set points = 40 where id
		// =1");
		// if(isTheEntryThere("select * from PERSON where id = 1")){
		// System.out.println("Yes");
		// } else {
		// System.out.println("no");
		// }
	}

	public TicTacToe_H2() {
	}

	/**
	 * Factory method for returning the singleton board
	 * 
	 * @param mainClass
	 *            The main class of this program
	 * @return The singleton resource locator
	 */
	public static TicTacToe_H2 getDB() {
		if (h2 == null)
			h2 = new TicTacToe_H2();
		return h2;
	}

	/**
	 * delete the DB and sets up a new table
	 */
	public static void deleteDB() {
		// delete the H2 database named 'test' in the user home directory
		DeleteDbFiles.execute("~", "TicTacToe", true);
		try {
			createTableWithPreparedStatement();
			insertWithPreparedStatement(741, "default", 00);
			insertWithPreparedStatement(879, "computer", 00);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * generate a new Table
	 * 
	 * @throws SQLException
	 */
	public static void createTableWithPreparedStatement() throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement createPreparedStatement = null;
		String CreateQuery = "CREATE TABLE PERSON(id int, name varchar(255) primary key, points int)";

		try {
			connection.setAutoCommit(false);

			createPreparedStatement = connection.prepareStatement(CreateQuery);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}

	}

	/**
	 * becomes a whole statement
	 * 
	 * @param statement
	 * @return gives all back
	 * @throws SQLException
	 */
	public static String selectPreparedStatement(String statement) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = statement;
		String answer = null;

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();
			System.out.println("H2 Database inserted through PreparedStatement");
			while (rs.next()) {
				System.out.println(
						"Id " + rs.getInt("id") + " Name " + rs.getString("name") + "Points " + rs.getInt("points"));
				answer = rs.getString("name");
				answer += " hat ";
				answer += rs.getString("points".toString());
				answer += " Punkte";
			}
			selectPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return answer;
	}

	/**
	 * is for fill an ArrayList<String> for the XML-Writing
	 * 
	 * @param statement
	 * @return gives all back
	 * @throws SQLException
	 */
	public static ArrayList<Game> selectPreparedStatementForXML(String statement) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = statement;
		ArrayList<Game> answer = new ArrayList<Game>();
		int counter = 0;

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();
				while (rs.next()) {
					System.out.println("Id " + rs.getInt("id") + " Name " + rs.getString("name") + "Points "
							+ rs.getInt("points"));
					System.out.println("H2 Database inserted through PreparedStatementXML");
					String id = rs.getString("id".toString());
					String name = rs.getString("name");
					String points = rs.getString("points".toString());
					Game g = new Game(Integer.toString(counter),id,name,points);
					answer.add(g);
					counter++;
				}
			selectPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return answer;
	}

	/**
	 * for the display in the view
	 * 
	 * @param id
	 *            - the generatet id from the model
	 * @return String with name and points
	 * @throws SQLException
	 */
	public int selectPreparedStatementForPoints(int id) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = ("SELECT * FROM PERSON WHERE ID = ?");
		int answer = 0;

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			selectPreparedStatement.setInt(1, id);
			ResultSet rs = selectPreparedStatement.executeQuery();
			System.out.println("H2 Database inserted through PreparedStatement");
			while (rs.next()) {
				System.out.println(
						"Id " + rs.getInt("id") + " Name " + rs.getString("name") + "Points " + rs.getInt("points"));
				answer = rs.getInt("points");
			}
			selectPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return answer;
	}

	/**
	 * check is there an line in the DB
	 * 
	 * @param id
	 *            - the generate id from the model
	 * @return - yes or no
	 * @throws SQLException
	 */
	public boolean isTheEntryThere(int id) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = ("SELECT * FROM PERSON WHERE ID = ?");

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			selectPreparedStatement.setInt(1, id);
			ResultSet rs = selectPreparedStatement.executeQuery();
			String entry = null;
			while (rs.next()) {
				entry = rs.getString("name");
			}
			if (entry != null) {
				System.out.println("EntryThere");
				return true;
			}

			selectPreparedStatement.close();
			connection.commit();

		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		System.out.println("EntryNotThere");
		return false;
	}

	/**
	 * insert a new line in the DB
	 * 
	 * @param id
	 *            - generate id from the model
	 * @param name
	 *            - name that given
	 * @param points
	 *            - points that given
	 * @throws SQLException
	 */
	public static void insertWithPreparedStatement(int id, String name, int points) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement insertPreparedStatement = null;
		String InsertQuery = "INSERT INTO PERSON" + "(id, name, points) values" + "(?,?,?)";

		try {
			connection.setAutoCommit(false);

			insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setInt(1, id);
			insertPreparedStatement.setString(2, name);
			insertPreparedStatement.setInt(3, points);

			System.out.println("Insert: " + id + " " + name + " " + points);
			insertPreparedStatement.executeUpdate();
			insertPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	/**
	 * update the entry in the DB
	 * 
	 * @param update
	 *            - input the whole SQL-Query
	 * @throws SQLException
	 */
	public void updateWithPreparedStatement(String update) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement updatePreparedStatement = null;

		try {
			connection.setAutoCommit(false);

			updatePreparedStatement = connection.prepareStatement(update);
			updatePreparedStatement.executeUpdate();
			connection.commit();

		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

	/**
	 * H2 SQL Prepared Statement Select for Name and Points
	 * 
	 * @param id
	 * @return send a String with the name and the points
	 * @throws SQLException
	 */
	public static String selectPreparedStatementForDisplayTheNameAndPoints(int id) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = ("SELECT * FROM PERSON WHERE ID = ?");
		String answer = null;

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			selectPreparedStatement.setInt(1, id);
			ResultSet rs = selectPreparedStatement.executeQuery();
			System.out.println("H2 Database inserted through PreparedStatement");
			while (rs.next()) {
				System.out.println(
						"Id " + rs.getInt("id") + " Name " + rs.getString("name") + "Points " + rs.getInt("points"));
				answer = rs.getString("name");
				answer += " hat ";
				answer += rs.getString("points".toString());
				answer += " Punkte";
			}
			selectPreparedStatement.close();

			connection.commit();

		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return answer;
	}

	/**
	 * H2 SQL Prepared Statement Select for Points
	 * 
	 * @param id
	 * @return send only the points
	 * @throws SQLException
	 */
	public static int selectPreparedStatementPoints(int id) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = ("SELECT * FROM PERSON WHERE ID = ?");
		int answer = 0;

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			selectPreparedStatement.setInt(1, id);
			ResultSet rs = selectPreparedStatement.executeQuery();
			System.out.println("H2 Database inserted through PreparedStatement");
			while (rs.next()) {
				System.out.println(
						"Id " + rs.getInt("id") + " Name " + rs.getString("name") + "Points " + rs.getInt("points"));
				answer = rs.getInt("points");
			}
			selectPreparedStatement.close();

			connection.commit();

		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return answer;
	}

	/**
	 * H2 SQL Prepared Statement Select for Name
	 * 
	 * @param id
	 * @return send only the points
	 * @throws SQLException
	 */
	public static String selectPreparedStatementName(int id) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = ("SELECT * FROM PERSON WHERE ID = ?");
		String answer = null;

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			selectPreparedStatement.setInt(1, id);
			ResultSet rs = selectPreparedStatement.executeQuery();
			System.out.println("H2 Database select for name through PreparedStatement");
			while (rs.next()) {
				System.out.println(
						"Id " + rs.getInt("id") + " Name " + rs.getString("name") + "Points " + rs.getInt("points"));
				answer = rs.getString("name");
			}
			selectPreparedStatement.close();

			connection.commit();

		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return answer;
	}
}
