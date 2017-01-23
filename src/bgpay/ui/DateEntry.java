package bgpay.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import org.jdesktop.swingx.JXDatePicker;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.awt.event.ActionEvent;

public class DateEntry extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4492376687961840882L;
	private final JPanel contentPanel = new JPanel();
	private JXDatePicker datePicker;
	private LocalDate dateEntered;

	/**
	 * Create the dialog.
	 */
	public DateEntry() {
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 170);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		{
			JLabel label = new JLabel("");
			contentPanel.add(label);
		}
		{
			JLabel lblPleaseEnterA = new JLabel("Please Enter A Date");
			contentPanel.add(lblPleaseEnterA);
		}
		{
			datePicker = new JXDatePicker();
			datePicker.getEditor().setText("dd/mm/yy");
			contentPanel.add(datePicker);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dateEntered = convertDate(datePicker.getDate());
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	/**
	 * 
	 * @return dateEntered
	 */
	public LocalDate getDateEntered() {
		return dateEntered;
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
