/**
 * @author Robert Miki A00990619
 * @version
 */
package bgpay.voucher;

import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

import bgpay.database.Database;

public class VoucherModel extends AbstractListModel<Voucher> {

	private static final long serialVersionUID = -2518266574306794765L;

	private DefaultListModel<Voucher> listModel;

	private VoucherDao voucherDao;

	/**
	 * Default Constructor
	 */
	public VoucherModel() {
	}

	/**
	 * Constructor initializes a DefaultListModel and a VoucherDao.
	 * 
	 * @param database
	 */
	public VoucherModel(Database database) {
		listModel = new DefaultListModel<Voucher>();
		voucherDao = new VoucherDao(database);
		listVouchers();

	}

	@Override
	public int getSize() {
		return listModel.size();
	}

	@Override
	public Voucher getElementAt(int index) {
		return listModel.getElementAt(index);
	}

	/**
	 * Adds an element to the DefaultListModel
	 * 
	 * @param voucher
	 */
	public void addElement(Voucher voucher) {
		listModel.addElement(voucher);
	}

	/**
	 * Uses the VoucherDao instance to populate the DefaultListModel in sorted order
	 */
	public void listVouchers() {
		List<Voucher> tempList = voucherDao.getAllVouchers();
		if (tempList.isEmpty())
			return;
		else {
			for (Voucher vouchers : tempList) {
				listModel.addElement(vouchers);
			}
		}
		Collections.sort(tempList);
	}

}
