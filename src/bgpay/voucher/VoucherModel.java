/**
 * @author Robert Miki A00990619
 * @version
 */
package bgpay.voucher;

import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataListener;

import bgpay.database.Database;

public class VoucherModel extends AbstractListModel<Voucher> implements Observer {

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
	
	public void updateElement( Voucher voucher, int index) {
		list.set(index, voucher);
		fireContentsChanged(this, getSize(), getSize());
	}

	/**
	 * Uses the VoucherDao instance to populate the ArrayList in sorted order
	 */
	public void listAllVouchers() {
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
	
	/**
	 * Uses the VoucherDao instance to populate the ArrayList in sorted order
	 * @throws SQLException 
	 */
	public void listVouchersInMonth(Month month) throws SQLException {
		List<Voucher> tempList = voucherDao.getVouchersInDateRange(month);
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

	@Override
	public void update(Observable o, Object arg) {
		updateElement((Voucher) o, (int) arg);
		
	}


}
