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
import java.awt.BorderLayout;
import javax.swing.JList;

public class ApplicationUi {

	public JFrame frame;

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
				new VoucherEntry(database);
			}
		});
		mnFile.add(mntmNewVoucher);

		JMenuItem mntmShowVouchers = new JMenuItem("Show Vouchers");
		mnFile.add(mntmShowVouchers);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		VoucherModel voucherModel = new VoucherModel(database);
		JList<Voucher> list = new JList<Voucher>(voucherModel);
		scrollPane.setViewportView(list);
	}

}
