package bgpay.ui;

import javax.swing.JList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import bgpay.voucher.Voucher;

/**
 * Class listens for changes to the VoucherModel
 */
public class MyListDataListener implements ListDataListener {

	private JList<Voucher> theList;
	
	public MyListDataListener(JList<Voucher> theList) {
		this.theList = theList;
		
	}
	@Override
	public void intervalAdded(ListDataEvent e) {
		theList.updateUI();

	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		theList.updateUI();

	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		theList.repaint();
		theList.clearSelection();

	}

}
