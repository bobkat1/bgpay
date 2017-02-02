package bgpay.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;

import org.jdesktop.swingx.JXDatePicker;

import bgpay.voucher.Voucher;
import bgpay.voucher.VoucherDao;
import bgpay.voucher.VoucherModel;

public class VoucherDialog extends JDialog {

	private static final long serialVersionUID = -1093318977235491292L;
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");

	private final JPanel contentPanel = new JPanel();

	private JTextField stTxtField;
	private JTextField etTxtField;
	private JXDatePicker stDatePicker;
	private JXDatePicker edDatePicker;
	private JComboBox<PayRates> rateComboBox;
	private JComboBox<Paid> paidComboBox;

	private String prodName;
	private String prodCom;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;

	private Voucher voucher;

	/**
	 * Create the dialog.
	 */
	public VoucherDialog(Voucher voucher, VoucherDao voucherDao, VoucherModel voucherModel, JList<Voucher> jList) {

		this.voucher = voucher;

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow][][][][grow]", "[][][][][][]"));
		{
			JLabel lblProductionName = new JLabel("Production Name");
			lblProductionName.setHorizontalAlignment(SwingConstants.LEFT);
			contentPanel.add(lblProductionName, "cell 0 0,alignx left");
		}
		{
			JTextField prodNameTxtBox = new JTextField();
			prodNameTxtBox.setText(voucher.getProductionName());
			prodNameTxtBox.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					prodName = prodNameTxtBox.getText();
				}
			});
			contentPanel.add(prodNameTxtBox, "cell 1 0 4 1,growx");
			prodNameTxtBox.setColumns(10);
		}
		{
			JLabel lblProductionCompany = new JLabel("Production Company");
			contentPanel.add(lblProductionCompany, "cell 0 1,alignx left");
		}
		{
			JTextField prodComTxtBox = new JTextField();
			prodComTxtBox.setText(voucher.getProductionCompany());
			prodComTxtBox.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					prodCom = prodComTxtBox.getText();
				}
			});
			contentPanel.add(prodComTxtBox, "cell 1 1 4 1,growx");
			prodComTxtBox.setColumns(10);
			prodCom = prodComTxtBox.getText();
		}
		{
			JLabel lblStartDate = new JLabel("Start Date");
			contentPanel.add(lblStartDate, "flowx,cell 0 2");
		}
		{
			JLabel lblEndDate = new JLabel("End Date");
			contentPanel.add(lblEndDate, "flowx,cell 1 2");
		}
		{
			JLabel lblStartTime = new JLabel("Start Time");
			contentPanel.add(lblStartTime, "flowx,cell 0 3");
		}
		{
			JLabel lblEndTime = new JLabel("End Time");
			contentPanel.add(lblEndTime, "flowx,cell 1 3");
		}
		{
			JLabel lblRate = new JLabel("Rate");
			contentPanel.add(lblRate, "flowx,cell 0 4");
		}
		{
			JLabel lblPaid = new JLabel("Paid");
			contentPanel.add(lblPaid, "flowx,cell 0 5");
		}
		{
			rateComboBox = new JComboBox<PayRates>();
			rateComboBox.setModel(new DefaultComboBoxModel<PayRates>(PayRates.values()));
			rateComboBox.setSelectedIndex(voucher.getPayRateEnum().getIndex());
			;
			contentPanel.add(rateComboBox, "cell 0 4");
		}
		{
			stTxtField = new JTextField();
			stTxtField.setText(voucher.getStartTime().format(formatter));
			contentPanel.add(stTxtField, "cell 0 3");
			stTxtField.setColumns(10);
		}
		{
			etTxtField = new JTextField();
			etTxtField.setText(voucher.getEndTime().format(formatter));
			contentPanel.add(etTxtField, "cell 1 3");
			etTxtField.setColumns(10);
			{
				paidComboBox = new JComboBox<Paid>();
				paidComboBox.setModel(new DefaultComboBoxModel<Paid>(Paid.values()));
				paidComboBox.setSelectedItem(voucher.getPayRateEnum());
				contentPanel.add(paidComboBox, "cell 0 5");
			}
		}
		{
			stDatePicker = new JXDatePicker();
			stDatePicker.getEditor().setText(voucher.getStartDate().toString());
			contentPanel.add(stDatePicker, "cell 0 2");
		}
		{
			edDatePicker = new JXDatePicker();
			edDatePicker.getEditor().setText(voucher.getEndDate().toString());
			contentPanel.add(edDatePicker, "cell 1 2");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if (stDatePicker.getDate() == null)
							startDate = LocalDate.parse(stDatePicker.getEditor().getText());

						startDate = dateSetter(stDatePicker);
						endDate = dateSetter(edDatePicker);
						startTime = LocalTime.parse(stTxtField.getText(), formatter);
						endTime = LocalTime.parse(etTxtField.getText(), formatter);

						setVoucher();

						/*
						 * startDate = convertDate(stDatePicker.getDate());
						 * endDate = convertDate(edDatePicker.getDate());
						 * startTime = LocalTime.parse(stTxtField.getText(), formatter);
						 * endTime = LocalTime.parse(etTxtField.getText(), formatter);
						 * Voucher tempVoucher = new Voucher(prodName, prodCom, ((PayRates) rateComboBox.getSelectedItem()).getRate(), startDate,
						 * endDate,
						 * startTime, endTime, ((Paid) paidComboBox.getSelectedItem()).isPaid);
						 */
						try {
							voucherDao.update(voucher);
							voucherModel.addElement(voucher);
						} catch (SQLException ex) {
							ex.printStackTrace();
						} finally {
							jList.updateUI();
							dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				{
					JButton btnDelete = new JButton("Delete");
					buttonPane.add(btnDelete);
				}
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

	/**
	 * Sets the fields in the voucher
	 */
	public void setVoucher() {
		voucher.setProductionName(prodName);
		voucher.setProductionCompany(prodCom);
		voucher.setRate(((PayRates) rateComboBox.getSelectedItem()).getRate());
		voucher.setStartDate(startDate);
		voucher.setEndDate(endDate);
		voucher.setStartTime(startTime);
		voucher.setEndTime(endTime);
		voucher.setIsPaid(((Paid) paidComboBox.getSelectedItem()).getIsPaid());

	}

	/**
	 * 
	 * @return the voucher
	 */
	public Voucher getVoucher() {
		return voucher;
	}

	/**
	 * 
	 * @param date
	 * @param datePicker
	 */
	private LocalDate dateSetter(JXDatePicker datePicker) {
		if (datePicker.getDate() == null)
			return LocalDate.parse(datePicker.getEditor().getText());
		else
			return convertDate(datePicker.getDate());

	}

	/**
	 * Converts a java.util.Date instance to a a
	 * 
	 * @param date
	 * @return
	 */
	private LocalDate convertDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

}
