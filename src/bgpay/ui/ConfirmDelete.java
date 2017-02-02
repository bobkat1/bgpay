package bgpay.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bgpay.voucher.Voucher;
import bgpay.voucher.VoucherDao;
import bgpay.voucher.VoucherModel;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class ConfirmDelete extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4301205313961402317L;
	private final JPanel contentPanel = new JPanel();


	/**
	 * Create the dialog.
	 */
	public ConfirmDelete(VoucherDao voucherDao, Voucher voucher, VoucherModel voucherModel, Window window) {
		super(window);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 300, 120);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblAreYouSure = new JLabel("Are you sure you want to delete this entry?");
			lblAreYouSure.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblAreYouSure, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							voucherDao.delete(voucher);
							voucherModel.removeElement(voucher);
							window.dispose();
							dispose();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
