/**
 * @author Robert Miki A00990619
 * @version
 */
package bgpay.voucher;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

import bgpay.database.Database;

public class VoucherModel extends AbstractListModel<Voucher> {

	private static final long serialVersionUID = -2518266574306794765L;

	final DefaultListModel<LocalDate> listModel;
	private VoucherDao voucherDao;

	/**
	 * 
	 */
	public VoucherModel(Database database) {
		listModel = new DefaultListModel<LocalDate>();
		voucherDao = new VoucherDao(database);
		listDates();

	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Voucher getElementAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean listDates() {
		boolean completed = false;
		try {
			List<LocalDate> tempList = voucherDao.getDates();
			if (tempList.isEmpty())
				completed = false;
			else {
				for (LocalDate dates : tempList) {
					listModel.addElement(dates);
					completed = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return completed;
	}

}
