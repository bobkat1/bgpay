package bgpay.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import org.jdesktop.swingx.JXDatePicker;

import bgpay.util.DateAndTimeFormats;
import bgpay.util.DateConverters;
import bgpay.voucher.Voucher;
import bgpay.voucher.VoucherDao;
import bgpay.voucher.VoucherModel;
import bgpay.voucher.enumerations.Paid;
import bgpay.voucher.enumerations.PayRates;

public class VoucherDialog extends JDialog {

	private static final long serialVersionUID = -1093318977235491292L;

	private final JPanel contentPanel = new JPanel();
	JDialog vd = this;

	private JTextField stTxtField;
	private JTextField etTxtField;
	private JXDatePicker stDatePicker;
	private JXDatePicker edDatePicker;
	private JComboBox<PayRates> rateComboBox;
	private JComboBox<Paid> paidComboBox;

	private String prodName;
	private String prodCom;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;

	private Voucher voucher;

	/**
	 * Create the dialog.
	 */
	public VoucherDialog(Voucher voucher, VoucherDao voucherDao, VoucherModel voucherModel, int index) {

		this.voucher = voucher;
		voucher.addObserver(voucherModel);
		voucher.addObserver(voucherDao);

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
			stTxtField.setText(voucher.getStartTime().format(DateAndTimeFormats.TIMEFORMATTER));
			contentPanel.add(stTxtField, "cell 0 3");
			stTxtField.setColumns(10);
		}
		{
			etTxtField = new JTextField();
			etTxtField.setText(voucher.getEndTime().format(DateAndTimeFormats.TIMEFORMATTER));
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
			stDatePicker.setDate(DateConverters.convertToDate(voucher.getStartDate()));
			stDatePicker.getEditor().setText(voucher.getStartDate().format(DateAndTimeFormats.DATEFORMATTER));
			contentPanel.add(stDatePicker, "cell 0 2");
		}
		{
			edDatePicker = new JXDatePicker();
			edDatePicker.setDate(DateConverters.convertToDate(voucher.getEndDate()));
			edDatePicker.getEditor().setText(voucher.getEndDate().format(DateAndTimeFormats.DATEFORMATTER));
			contentPanel.add(edDatePicker, "cell 1 2");
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
				okButton.addActionListener((ActionEvent e) -> {
					startDateTime = dateSetter(stDatePicker, stTxtField);
					endDateTime = dateSetter(edDatePicker, etTxtField);
					setVoucher();
					voucher.notifyObservers(index);
					dispose();
				});

				{
					JButton btnDelete = new JButton("Delete");
					buttonPane.add(btnDelete);
					btnDelete.addActionListener((ActionEvent e) -> new ConfirmDelete(voucherDao, voucher, voucherModel, vd));
				}
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener((ActionEvent e) -> dispose());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	/**
	 * Sets the fields in the Voucher if they are unchanged
	 */
	public void setVoucher() {

		if (!voucher.getProductionName().equals(prodName))
			voucher.setProductionName(prodName);

		if (!voucher.getProductionCompany().equals(prodCom))
			voucher.setProductionCompany(prodCom);

		if (!((voucher.getRate()) == ((PayRates) rateComboBox.getSelectedItem()).getRate()))
			voucher.setRate(((PayRates) rateComboBox.getSelectedItem()).getRate());

		if (!voucher.getStartDateTime().equals(startDateTime))
			voucher.setStartDateTime(startDateTime);

		if (!voucher.getEndDateTime().equals(endDateTime))
			voucher.setEndDateTime(endDateTime);

		if (!voucher.getIsPaid() == ((Paid) paidComboBox.getSelectedItem()).getIsPaid())
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
	public static LocalDateTime dateSetter(JXDatePicker datePicker, JTextField textField) {
		Date date = datePicker.getDate();
		String time = textField.getText();
		if (date == null && time == null)
			return LocalDateTime.now();
		else {
			LocalDate convertedDate = DateConverters.convertToLocalDate(date);
			LocalTime convertedTime = LocalTime.parse(time);
			return LocalDateTime.of(convertedDate, convertedTime);
		}

	}

}
