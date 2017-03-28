package TicTacToe_Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.h2.tools.DeleteDbFiles;

public class TicTacToe_H2 {

	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:~/TicTacToe";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	public static void main(String[] args) throws Exception {
		// deleteDB();
		// createTableWithPreparedStatement();
		// insertWithPreparedStatement(1,"Lukas",30);
		// selectPreparedStatement("select * from PERSON");
		// updateWithPreparedStatement("update PERSON set points = 40 where id =
		// 1");
		// if(isTheEntryThere("select * from PERSON where id = 1")){
		// System.out.println("Yes");
		// } else {
		// System.out.println("no");
		// }
	}

	public TicTacToe_H2() {
	}

	public void deleteDB() {
		// delete the H2 database named 'test' in the user home directory
		DeleteDbFiles.execute("~", "TicTacToe", true);
	}

	// H2 SQL Prepared Statement Example Create
	public void createTableWithPreparedStatement() throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement createPreparedStatement = null;
		String CreateQuery = "CREATE TABLE PERSON(id int primary key, name varchar(255), points int)";

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

	// H2 SQL Prepared Statement Example Select
	public String selectPreparedStatement(String statement) throws SQLException {
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
				System.out.println("Id " + rs.getInt("id") + " Name " + rs.getString("name") + "Points " + rs.getInt("points"));
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

	// H2 SQL Prepared Statement Example Select
	public boolean isTheEntryThere(String statement) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = statement;

		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();
			selectPreparedStatement.close();
			connection.commit();
			return true;

		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return false;
	}

	// H2 SQL Prepared Statement Example Insert
	public void insertWithPreparedStatement(int id, String name, int points) throws SQLException {
		Connection connection = getDBConnection();

		PreparedStatement insertPreparedStatement = null;

		String InsertQuery = "INSERT INTO PERSON" + "(id, name, points) values" + "(?,?,?)";

		try {
			connection.setAutoCommit(false);

			insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setInt(1, id);
			insertPreparedStatement.setString(2, name);
			insertPreparedStatement.setInt(3, points);
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

	// H2 SQL Prepared Statement Example Update
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
}
