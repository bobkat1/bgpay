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
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
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
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.ALT_MASK));
		mnFile.add(mntmExit);
		
		JMenu mnVouchers = new JMenu("Vouchers");
		menuBar.add(mnVouchers);
		
				JMenuItem mntmNewVoucher = new JMenuItem("New Voucher");
				mnVouchers.add(mntmNewVoucher);
				mntmNewVoucher.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new VoucherEntry(database, listModel, theList);
					}
				});

		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		listModel = new VoucherModel(database);
		listModel.addListDataListener(new MyListDataListener());
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
			int index = theList.getSelectedIndex();
			try {
				VoucherDialog itemDialog = new VoucherDialog(item, voucherDao, listModel, index);
				itemDialog.setVisible(true);
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

	/**
	 * Class listens for changes to the VoucherModel
	 */
	class MyListDataListener implements ListDataListener {

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

}
