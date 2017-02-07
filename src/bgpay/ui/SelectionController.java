package bgpay.ui;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bgpay.database.Database;
import bgpay.voucher.Voucher;
import bgpay.voucher.VoucherDao;
import bgpay.voucher.VoucherModel;

public class SelectionController implements ListSelectionListener {

	VoucherDao voucherDao;
	JList<Voucher> theList;
	VoucherModel voucherModel;

	public SelectionController(Database database, JList<Voucher> theList, VoucherModel voucherModel) {
		voucherDao = new VoucherDao(database);
		this.theList = theList;
		this.voucherModel = voucherModel;
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		if (event.getValueIsAdjusting()) {
			return;
		}

		Object o = theList.getSelectedValue();
		if (o == null) {
			return;
		}

		Voucher item = (Voucher) o;
		int index = theList.getSelectedIndex();
		try {
			new VoucherDialog(item, voucherDao, voucherModel, index);
			theList.clearSelection();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
