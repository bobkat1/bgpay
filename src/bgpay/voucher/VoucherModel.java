/**
 * @author Robert Miki A00990619
 * @version
 */
package bgpay.voucher;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

import bgpay.database.Database;

public class VoucherModel extends AbstractListModel<Voucher> {

	private static final long serialVersionUID = -2518266574306794765L;

	final DefaultListModel<Voucher> listModel;
	private VoucherDao voucherDao;

	/**
	 * 
	 */
	public VoucherModel(Database database) {
		listModel = new DefaultListModel<Voucher>();
		voucherDao = new VoucherDao(database);
		listVouchers();

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

	public boolean listVouchers() {
		boolean completed = false;
		List<Voucher> tempList = voucherDao.getAllVouchers();
		if (tempList.isEmpty())
			completed = false;
		else {
			for (Voucher vouchers : tempList) {
				listModel.addElement(vouchers);
				completed = true;
			}
		}
		return completed;
	}
	
	public DefaultListModel<Voucher> getListModel() {
		return listModel;
	}

}
