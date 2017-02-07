package bgpay.ui;

import javax.swing.JFrame;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import bgpay.database.Database;
import bgpay.voucher.Voucher;
import bgpay.voucher.VoucherModel;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
import javax.swing.JList;
/**
 * This class is the main applications GUI window which displays a JList of all of the entered vouchers in the database.
 * The top main contains items for exiting the program, entering a new voucher and searching months for vouchers.
 * @author RMiki
 * @version 1.0
 */
public class ApplicationUi {

	public JFrame frame;
	private JList<Voucher> theList;
	private VoucherModel voucherModel;

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
		frame.setBounds(100, 100, 550, 325);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener((ActionEvent e) -> System.exit(1));
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.ALT_MASK));
		mnFile.add(mntmExit);

		JMenu mnVouchers = new JMenu("Vouchers");
		menuBar.add(mnVouchers);

		JMenuItem mntmNewVoucher = new JMenuItem("New Voucher");
		mnVouchers.add(mntmNewVoucher);
		mntmNewVoucher.addActionListener((ActionEvent e) -> new VoucherEntry(database, voucherModel, theList));

		JMenu mnSearch = new JMenu("Search");
		menuBar.add(mnSearch);

		JMenuItem mntmMonth = new JMenuItem("Month");
		mntmMonth.addActionListener((ActionEvent e) -> new MonthSearch());
		mnSearch.add(mntmMonth);

		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		voucherModel = new VoucherModel(database);
		voucherModel.listAllVouchers();
		theList = new JList<Voucher>(voucherModel);
		voucherModel.addListDataListener(new MyListDataListener(theList));

		ListSelectionModel lsm = theList.getSelectionModel();
		lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(theList);

		SelectionController selectionController = new SelectionController(database, theList, voucherModel);
		theList.addListSelectionListener(selectionController);
	}

}
