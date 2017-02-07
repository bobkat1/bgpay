package bgpay.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.time.Month;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bgpay.database.Database;
import bgpay.voucher.Voucher;
import bgpay.voucher.VoucherModel;

import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import java.awt.event.ActionEvent;

public class MonthSearchedList extends JDialog {

	private static final long serialVersionUID = -4801160945552687338L;
	private final JPanel contentPanel = new JPanel();

	private VoucherModel voucherModel;
	private Database database = Database.getDatabase();

	/**
	 * Create the dialog.
	 * 
	 * @throws SQLException
	 */
	public MonthSearchedList(Month month) throws SQLException {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				voucherModel = new VoucherModel(database);
				voucherModel.listVouchersInMonth(month);
				JList<Voucher> theList = new JList<Voucher>(voucherModel);
				voucherModel.addListDataListener(new MyListDataListener(theList));
				
				ListSelectionModel lsm = theList.getSelectionModel();
				lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scrollPane.setViewportView(theList);
				
				SelectionController selectionController = new SelectionController(database, theList, voucherModel);
				theList.addListSelectionListener(selectionController);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener((ActionEvent e) -> dispose());
			}
		}
	}

}
