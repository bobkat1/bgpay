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

import bgpay.database.Database;
import bgpay.voucher.Voucher;
import bgpay.voucher.VoucherDao;

public class VoucherEntry extends JDialog {

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

	/**
	 * Create the dialog.
	 */
	public VoucherEntry(Database database) {
		
		VoucherDao voucherDao = new VoucherDao(database);

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
			contentPanel.add(rateComboBox, "cell 0 4");
		}
		{
			stTxtField = new JTextField();
			stTxtField.setText("2400");
			contentPanel.add(stTxtField, "cell 0 3");
			stTxtField.setColumns(10);
		}
		{
			etTxtField = new JTextField();
			etTxtField.setText("2400");
			contentPanel.add(etTxtField, "cell 1 3");
			etTxtField.setColumns(10);
			{
				paidComboBox = new JComboBox<Paid>();
				paidComboBox.setModel(new DefaultComboBoxModel<Paid>(Paid.values()));
				contentPanel.add(paidComboBox, "cell 0 5");
			}
		}
		{
			stDatePicker = new JXDatePicker();
			stDatePicker.getEditor().setText("dd/mm/yy");
			contentPanel.add(stDatePicker, "cell 0 2");
		}
		{
			edDatePicker = new JXDatePicker();
			edDatePicker.getEditor().setText("dd/mm/yy");
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
						startDate = convertDate(stDatePicker.getDate());
						endDate = convertDate(edDatePicker.getDate());
						startTime = LocalTime.parse(stTxtField.getText(), formatter);
						endTime = LocalTime.parse(etTxtField.getText(), formatter);
						Voucher voucher = new Voucher(prodName, prodCom, ((PayRates) rateComboBox.getSelectedItem()).getWage(), startDate, endDate,
								startTime, endTime, ((Paid) paidComboBox.getSelectedItem()).isPaid);
						try {
						voucherDao.add(voucher);
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
						dispose();
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
	
	/**
	 * Converts a java.util.Date instance to a a 
	 * @param date
	 * @return
	 */
	private LocalDate convertDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

}