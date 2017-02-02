/**
 * @author Robert Miki A00990619
 * @version
 */
package bgpay.voucher;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bgpay.dao.Dao;
import bgpay.database.Database;

public class VoucherDao extends Dao {

	public static final String TABLE_NAME = "vouchers";
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * 
	 */
	public VoucherDao(Database database) {
		super(database, TABLE_NAME);
	}

	/**
	 * 
	 * @param voucher
	 * @throws SQLException
	 */
	public void add(Voucher voucher) throws SQLException {
		String sqlString = String.format("INSERT INTO %s values (?, ?, ?, ?, ?, ?, ?, ?)", TABLE_NAME);
		@SuppressWarnings("unused")
		boolean result = execute(sqlString, voucher.getProductionName(), voucher.getProductionCompany(), voucher.getRate(), voucher.getStartDate(),
				voucher.getEndDate(), Time.valueOf(voucher.getStartTime()), Time.valueOf(voucher.getEndTime()), voucher.getIsPaid());
	}

	/**
	 * 
	 * @param voucher
	 * @throws SQLException
	 */
	public void update(Voucher voucher) throws SQLException {
		String sqlString = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=? AND %s=?", TABLE_NAME, //
				VoucherFields.PRODUCTION_NAME.getFieldTitle(), //
				VoucherFields.PRODUCTION_COMPANY.getFieldTitle(), //
				VoucherFields.RATE.getFieldTitle(), //
				VoucherFields.START_DATE.getFieldTitle(), //
				VoucherFields.END_DATE.getFieldTitle(), //
				VoucherFields.START_TIME.getFieldTitle(), //
				VoucherFields.END_TIME.getFieldTitle(), //
				VoucherFields.PAID.getFieldTitle(),
				VoucherFields.START_DATE.getFieldTitle(),
				VoucherFields.END_DATE.getFieldTitle()); //

		@SuppressWarnings("unused")
		boolean result = execute(sqlString, voucher.getProductionName(), voucher.getProductionCompany(), voucher.getRate(), voucher.getStartDate(),
				voucher.getEndDate(), voucher.getStartTime(), voucher.getEndTime(), voucher.getIsPaid(), voucher.getStartDate(), voucher.getEndDate());
	}

	/**
	 * 
	 * @param voucher
	 * @throws SQLException
	 */
	public void delete(Voucher voucher) throws SQLException {
		Connection connection;
		Statement statement = null;
		try {
			connection = Database.getConnection();
			statement = connection.createStatement();

			String sqlString = String.format("DELETE FROM %s WHERE %s=%s AND %s=%s", TABLE_NAME, VoucherFields.START_DATE.getFieldTitle(),
					voucher.getStartDate(), VoucherFields.END_DATE.getFieldTitle(), voucher.getEndDate());
			@SuppressWarnings("unused")
			int rowcount = statement.executeUpdate(sqlString);
		} finally {
			close(statement);
		}
	}

	public Voucher getVoucher(LocalDate date) throws Exception {
		String sqlString = String.format("SELECT * FROM %s WHERE %s = %s", TABLE_NAME, VoucherFields.START_DATE.getFieldTitle(),
				java.sql.Date.valueOf(date));

		Statement statement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlString);

			int count = 0;
			while (resultSet.next()) {
				if (count > 0)
					throw new Exception(String.format("Expected one result, got %d", count));
			}

			Date stDateSql = resultSet.getDate(VoucherFields.START_DATE.getFieldTitle());
			Date eDateSql = resultSet.getDate(VoucherFields.END_DATE.getFieldTitle());
			Time startTimeSql = resultSet.getTime(VoucherFields.START_TIME.getFieldTitle());
			Time endTimeSql = resultSet.getTime(VoucherFields.END_TIME.getFieldTitle());

			LocalDate startDate = stDateSql.toLocalDate();
			LocalDate endDate = eDateSql.toLocalDate();
			LocalTime startTime = startTimeSql.toLocalTime();
			LocalTime endTime = endTimeSql.toLocalTime();

			return new Voucher(resultSet.getString(VoucherFields.PRODUCTION_NAME.getFieldTitle()),
					resultSet.getString(VoucherFields.PRODUCTION_COMPANY.getFieldTitle()), resultSet.getDouble(VoucherFields.RATE.getFieldTitle()),
					startDate, endDate, startTime, endTime, resultSet.getBoolean(VoucherFields.PAID.getFieldTitle()));

		} finally {
			close(statement);
		}

	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<LocalDate> getDates() throws SQLException {
		List<LocalDate> list = new ArrayList<LocalDate>();

		String selectString = String.format("SELECT %s FROM %s", VoucherFields.START_DATE.getFieldTitle(), TABLE_NAME);
		System.out.println(selectString);

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();

			resultSet = statement.executeQuery(selectString);

			while (resultSet.next()) {
				Date date = resultSet.getDate(VoucherFields.START_DATE.getFieldTitle());
				list.add(LocalDate.parse(date.toString()));
				LOG.debug("Getting date: " + date);
			}
		} finally {
			close(statement);
		}
		return list;
	}

	/**
	 * Creates a voucher object from a database row and returns an ArrayList of the objects
	 * 
	 * @return
	 */
	public List<Voucher> getAllVouchers() {

		List<Voucher> list = new ArrayList<Voucher>();

		String selectString = "SELECT * FROM " + TABLE_NAME;

		Statement statement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectString);

			while (resultSet.next()) {
				Date stDateSql = resultSet.getDate(VoucherFields.START_DATE.getFieldTitle());
				Date eDateSql = resultSet.getDate(VoucherFields.END_DATE.getFieldTitle());
				Time startTimeSql = resultSet.getTime(VoucherFields.START_TIME.getFieldTitle());
				Time endTimeSql = resultSet.getTime(VoucherFields.END_TIME.getFieldTitle());

				LocalDate startDate = stDateSql.toLocalDate();
				LocalDate endDate = eDateSql.toLocalDate();
				LocalTime startTime = startTimeSql.toLocalTime();
				LocalTime endTime = endTimeSql.toLocalTime();

				list.add(new Voucher(resultSet.getString(VoucherFields.PRODUCTION_NAME.getFieldTitle()),
						resultSet.getString(VoucherFields.PRODUCTION_COMPANY.getFieldTitle()),
						resultSet.getDouble(VoucherFields.RATE.getFieldTitle()), startDate, endDate, startTime, endTime,
						resultSet.getBoolean(VoucherFields.PAID.getFieldTitle())));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(statement);
		}
		return list;

	}

	@Override
	public void create() throws SQLException {
		// TODO Auto-generated method stub

	}

}
