package de.edgesoft.abook2vcf;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Simple GUI for {@link ABook2VCF}.
 * 
 * @author Ekkart Kleinod
 * @version 0.2
 * @since 0.2
 */
public class ABook2VCFGUI {

	private JFrame frmAbookVcf;

	private String sInFile = null;
	private String sOutFile = null;
	private int iVCFCount = 0;
	private String sVersion = null;
	private boolean bWriteDoubles = false;
	private boolean bWriteTextDump = false;
	private boolean bWriteCsvDump = false;
	private JTextField textField;
	private JTextField textField_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ABook2VCFGUI window = new ABook2VCFGUI();
					window.frmAbookVcf.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ABook2VCFGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAbookVcf = new JFrame();
		frmAbookVcf.setTitle("ABook 2 VCF");
		frmAbookVcf.setBounds(100, 100, 683, 568);
		frmAbookVcf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAbookVcf.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JPanel panel = new JPanel();
		frmAbookVcf.getContentPane().add(panel, "2, 18, fill, fill");
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel label = new JLabel("ABook file (input)");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel_1.add(label, gbc_label);
		
		textField = new JTextField();
		textField.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel_1.add(textField, gbc_textField);
		
		JButton button = new JButton("");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 2;
		gbc_button.gridy = 0;
		panel_1.add(button, gbc_button);
		
		JLabel label_1 = new JLabel("default: abook.mab");
		label_1.setFont(label_1.getFont().deriveFont(label_1.getFont().getStyle() & ~Font.BOLD | Font.ITALIC, label_1.getFont().getSize() - 2f));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 0);
		gbc_label_1.gridx = 3;
		gbc_label_1.gridy = 0;
		panel_1.add(label_1, gbc_label_1);
		
		JLabel label_2 = new JLabel("VCF file (output)");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.EAST;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 1;
		panel_1.add(label_2, gbc_label_2);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 1;
		panel_1.add(textField_1, gbc_textField_1);
		
		JButton button_1 = new JButton("");
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 2;
		gbc_button_1.gridy = 1;
		panel_1.add(button_1, gbc_button_1);
		
		JLabel label_10 = new JLabel("default: abook.vcf");
		label_10.setFont(label_10.getFont().deriveFont(label_10.getFont().getStyle() & ~Font.BOLD | Font.ITALIC, label_10.getFont().getSize() - 2f));
		GridBagConstraints gbc_label_10 = new GridBagConstraints();
		gbc_label_10.insets = new Insets(0, 0, 5, 0);
		gbc_label_10.gridx = 3;
		gbc_label_10.gridy = 1;
		panel_1.add(label_10, gbc_label_10);
		
		JLabel label_3 = new JLabel("Cards per file");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 0;
		gbc_label_3.gridy = 2;
		panel_1.add(label_3, gbc_label_3);
		
		JSpinner spinner = new JSpinner();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 2;
		panel_1.add(spinner, gbc_spinner);
		
		JLabel label_11 = new JLabel("0 = unlimited");
		label_11.setFont(label_11.getFont().deriveFont(label_11.getFont().getStyle() & ~Font.BOLD | Font.ITALIC, label_11.getFont().getSize() - 2f));
		GridBagConstraints gbc_label_11 = new GridBagConstraints();
		gbc_label_11.insets = new Insets(0, 0, 5, 0);
		gbc_label_11.gridx = 3;
		gbc_label_11.gridy = 2;
		panel_1.add(label_11, gbc_label_11);
		
		JLabel label_4 = new JLabel("VCard version");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.anchor = GridBagConstraints.EAST;
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 0;
		gbc_label_4.gridy = 3;
		panel_1.add(label_4, gbc_label_4);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setSelectedIndex(0);
		comboBox.setMaximumRowCount(2);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 3;
		panel_1.add(comboBox, gbc_comboBox);
		
		JLabel label_5 = new JLabel("Write doubles file");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 0;
		gbc_label_5.gridy = 4;
		panel_1.add(label_5, gbc_label_5);
		
		JCheckBox checkBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox = new GridBagConstraints();
		gbc_checkBox.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox.gridx = 1;
		gbc_checkBox.gridy = 4;
		panel_1.add(checkBox, gbc_checkBox);
		
		JLabel label_6 = new JLabel("Write text dump");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 0;
		gbc_label_6.gridy = 5;
		panel_1.add(label_6, gbc_label_6);
		
		JCheckBox checkBox_1 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_1 = new GridBagConstraints();
		gbc_checkBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox_1.gridx = 1;
		gbc_checkBox_1.gridy = 5;
		panel_1.add(checkBox_1, gbc_checkBox_1);
		
		JLabel label_7 = new JLabel("Write csv dump");
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.insets = new Insets(0, 0, 5, 5);
		gbc_label_7.gridx = 0;
		gbc_label_7.gridy = 6;
		panel_1.add(label_7, gbc_label_7);
		
		JCheckBox checkBox_2 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_2 = new GridBagConstraints();
		gbc_checkBox_2.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox_2.gridx = 1;
		gbc_checkBox_2.gridy = 6;
		panel_1.add(checkBox_2, gbc_checkBox_2);
		
		JLabel label_8 = new JLabel("cmd call");
		GridBagConstraints gbc_label_8 = new GridBagConstraints();
		gbc_label_8.insets = new Insets(0, 0, 0, 5);
		gbc_label_8.gridx = 0;
		gbc_label_8.gridy = 7;
		panel_1.add(label_8, gbc_label_8);
		
		JLabel label_9 = new JLabel("");
		label_9.setFont(label_9.getFont().deriveFont(label_9.getFont().getStyle() & ~Font.BOLD, label_9.getFont().getSize() - 2f));
		GridBagConstraints gbc_label_9 = new GridBagConstraints();
		gbc_label_9.insets = new Insets(0, 0, 0, 5);
		gbc_label_9.gridx = 1;
		gbc_label_9.gridy = 7;
		panel_1.add(label_9, gbc_label_9);
	}
	
	/**
	 * Updates command line call display
	 * 
	 * @version 0.2
	 * @since 0.2
	 */
	private void updateCMDLine() {
		
	}

}
