/**
 * @author Robert Miki A00990619
 * @version 1.0
 */
package bgpay.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;



public class Database {

	public static final String DB_DRIVER_KEY = "db.driver";
	public static final String DB_URL_KEY = "db.url";
	public static final String DB_USER_KEY = "db.user";
	public static final String DB_PASSWORD_KEY = "db.password";
	
	public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/bgpay";
	public static final String DB_USER = "robert";
	public static final String DB_PASSWORD = "robertmiki";
	
	public static Database database = new Database();
	
	private static Connection connection;
	private static Properties properties;
	private static boolean dbTableDropRequested;
	
	/**
	 * 
	 */
	private Database() {
	}
	
	/**
	 * @param properties
	 */
	public static void init(Properties properties) {
		Database.properties = properties;
	}
	
	/**
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		if (connection != null) {
			return connection;
		}

		try {
			connect();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		return connection;
	}
	
	/**
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static void connect() throws ClassNotFoundException, SQLException {
		String dbDriver = properties.getProperty(DB_DRIVER_KEY);
		Class.forName(dbDriver);
		System.out.println("Driver loaded");
		connection = DriverManager.getConnection(properties.getProperty(DB_URL_KEY), properties.getProperty(DB_USER_KEY),
				properties.getProperty(DB_PASSWORD_KEY));

		if (dbTableDropRequested) {

		}
	}
	
	/**
	 * Close the connections to the database
	 */
	public static void shutdown() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
			}
		}
	}
	
	/**
	 * Determine if the database table exists.
	 * 
	 * @param tableName
	 * @return true is the table exists, false otherwise
	 * @throws SQLException
	 */
	public static boolean tableExists(String targetTableName) throws SQLException {
		DatabaseMetaData databaseMetaData = getConnection().getMetaData();
		ResultSet resultSet = null;
		String tableName = null;

		try {
			resultSet = databaseMetaData.getTables(connection.getCatalog(), "%", "%", null);
			while (resultSet.next()) {
				tableName = resultSet.getString("TABLE_NAME");
				if (tableName.equalsIgnoreCase(targetTableName)) {
					return true;
				}
			}
		} finally {
			resultSet.close();
		}

		return false;
	}

	/**
	 * 
	 */
	public static void requestDbTableDrop() {
		dbTableDropRequested = true;
	}

	/**
	 * 
	 * @return
	 */
	public static boolean dbTableDropRequested() {
		return dbTableDropRequested;
	}
	
	/**
	 * 
	 * @return
	 */
	public static Database getDatabase() {
		return database;
	}
	

}
