package bgpay.ui;

import javax.swing.JFrame;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import bgpay.database.Database;
import bgpay.voucher.Voucher;
import bgpay.voucher.VoucherDao;
import bgpay.voucher.VoucherModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;
import javax.swing.JList;

public class ApplicationUi {

	public JFrame frame;
	private JList<Voucher> theList;
	private VoucherModel listModel;

	/**
	 * Create the application.
	 */
	public ApplicationUi(Database database) {
		initialize(database);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Database database) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNewVoucher = new JMenuItem("New Voucher");
		mntmNewVoucher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VoucherEntry vE = new VoucherEntry(database, listModel, theList);
				listModel.addElement(vE.getVoucher());
			}
		});
		mnFile.add(mntmNewVoucher);

		JMenuItem mntmShowVouchers = new JMenuItem("Show Vouchers");
		mnFile.add(mntmShowVouchers);

		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		listModel = new VoucherModel(database);
		theList = new JList<Voucher>(listModel);

		ListSelectionModel lsm = theList.getSelectionModel();
		lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(theList);

		SelectionController selectionController = new SelectionController(database);
		theList.addListSelectionListener(selectionController);
	}

	class SelectionController implements ListSelectionListener {

		VoucherDao voucherDao;

		public SelectionController(Database database) {
			voucherDao = new VoucherDao(database);
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
			try {
				VoucherDialog itemDialog = new VoucherDialog(item, voucherDao, listModel, theList);
				itemDialog.setVisible(true);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
