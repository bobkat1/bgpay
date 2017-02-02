/**
 * @author Robert Miki A00990619
 * @version
 */
package bgpay.voucher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataListener;

import bgpay.database.Database;

public class VoucherModel extends AbstractListModel<Voucher> {

	private static final long serialVersionUID = -2518266574306794765L;

	private ArrayList<Voucher> list;
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
		voucherDao = new VoucherDao(database);
		list = new ArrayList<Voucher>();
		listVouchers();

	}

	/**
	 * Adds an element to the ArrayList
	 * 
	 * @param voucher
	 */
	public void addElement(Voucher voucher) {
		list.add(voucher);
		Collections.sort(list);
		fireContentsChanged(this, getSize(), getSize());
	}
	
	/**
	 * Removes the element
	 * @param voucher
	 */
	public void removeElement(Voucher voucher) {
		list.remove(voucher);
		fireContentsChanged(this, getSize(), getSize());
	}
	
	public void updateElement(int index, Voucher voucher) {
		list.set(index, voucher);
		fireContentsChanged(this, getSize(), getSize());
	}

	/**
	 * Uses the VoucherDao instance to populate the ArrayList in sorted order
	 */
	public void listVouchers() {
		List<Voucher> tempList = voucherDao.getAllVouchers();
		if (tempList.isEmpty())
			return;
		else {
			for (Voucher vouchers : tempList) {
				list.add(vouchers);
			}
		}
		Collections.sort(list);
	}

	@Override
	public void addListDataListener(ListDataListener e) {
		super.addListDataListener(e);
	}

	@Override
	public int getSize() {
		return list.size();
	}

	@Override
	public Voucher getElementAt(int index) {
		return list.get(index);
	}

}
