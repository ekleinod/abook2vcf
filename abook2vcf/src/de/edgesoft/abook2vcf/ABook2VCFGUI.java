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

/**
 * Simple GUI for {@link ABook2VCF}.
 * 
 * @author Ekkart Kleinod
 * @version 0.2
 * @since 0.2
 */
public class ABook2VCFGUI {

	private JFrame frmAbookVcf;
	private JTextField txtABook;
	private JTextField txtVCF;

	private String sInFile = null;
	private String sOutFile = null;
	private int iVCFCount = 0;
	private String sVersion = null;
	private boolean bWriteDoubles = false;
	private boolean bWriteTextDump = false;
	private boolean bWriteCsvDump = false;
	
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
		frmAbookVcf.setBounds(100, 100, 432, 300);
		frmAbookVcf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAbookVcf.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
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
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblABook = new JLabel("ABook file (input)");
		frmAbookVcf.getContentPane().add(lblABook, "2, 2");
		
		txtABook = new JTextField();
		lblABook.setLabelFor(txtABook);
		frmAbookVcf.getContentPane().add(txtABook, "4, 2, fill, default");
		txtABook.setColumns(10);
		
		JButton btnABook = new JButton("");
		btnABook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectABookFile();
			}
		});
		btnABook.setIcon(new ImageIcon(ABook2VCFGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")));
		frmAbookVcf.getContentPane().add(btnABook, "6, 2");
		
		JLabel lblDefaultAbookmab = new JLabel("default: abook.mab");
		lblDefaultAbookmab.setFont(lblDefaultAbookmab.getFont().deriveFont(lblDefaultAbookmab.getFont().getStyle() & ~Font.BOLD | Font.ITALIC, lblDefaultAbookmab.getFont().getSize() - 2f));
		frmAbookVcf.getContentPane().add(lblDefaultAbookmab, "8, 2");
		
		JLabel lblVCF = new JLabel("VCF file (output)");
		frmAbookVcf.getContentPane().add(lblVCF, "2, 4");
		
		txtVCF = new JTextField();
		lblVCF.setLabelFor(txtVCF);
		frmAbookVcf.getContentPane().add(txtVCF, "4, 4, fill, default");
		txtVCF.setColumns(10);
		
		JButton btnVCF = new JButton("");
		btnVCF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectVCFFile();
			}
		});
		btnVCF.setIcon(new ImageIcon(ABook2VCFGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		frmAbookVcf.getContentPane().add(btnVCF, "6, 4");
		
		JLabel lblDefaultAbookvcf = new JLabel("default: abook.vcf");
		lblDefaultAbookvcf.setFont(lblDefaultAbookvcf.getFont().deriveFont(lblDefaultAbookvcf.getFont().getStyle() & ~Font.BOLD | Font.ITALIC, lblDefaultAbookvcf.getFont().getSize() - 2f));
		frmAbookVcf.getContentPane().add(lblDefaultAbookvcf, "8, 4");
		
		JLabel lblVCFCount = new JLabel("Cards per file");
		frmAbookVcf.getContentPane().add(lblVCFCount, "2, 6");
		
		JSpinner spnVCFCount = new JSpinner();
		lblVCFCount.setLabelFor(spnVCFCount);
		spnVCFCount.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		frmAbookVcf.getContentPane().add(spnVCFCount, "4, 6");
		
		JLabel lblUnlimited = new JLabel("0 = unlimited");
		lblUnlimited.setFont(lblUnlimited.getFont().deriveFont(lblUnlimited.getFont().getStyle() & ~Font.BOLD | Font.ITALIC, lblUnlimited.getFont().getSize() - 2f));
		frmAbookVcf.getContentPane().add(lblUnlimited, "8, 6");
		
		JLabel lblVersion = new JLabel("VCard version");
		frmAbookVcf.getContentPane().add(lblVersion, "2, 8");
		
		JComboBox cboVersion = new JComboBox();
		lblVersion.setLabelFor(cboVersion);
		cboVersion.setModel(new DefaultComboBoxModel(new String[] {"3.0", "4.0"}));
		cboVersion.setSelectedIndex(0);
		cboVersion.setMaximumRowCount(2);
		frmAbookVcf.getContentPane().add(cboVersion, "4, 8, fill, default");
		
		JLabel lblWriteDoublesFile = new JLabel("Write doubles file");
		frmAbookVcf.getContentPane().add(lblWriteDoublesFile, "2, 10");
		
		JCheckBox chkDoubles = new JCheckBox("");
		lblWriteDoublesFile.setLabelFor(chkDoubles);
		frmAbookVcf.getContentPane().add(chkDoubles, "4, 10");
		
		JLabel lblWriteTextDump = new JLabel("Write text dump");
		frmAbookVcf.getContentPane().add(lblWriteTextDump, "2, 12");
		
		JCheckBox chkTextDump = new JCheckBox("");
		lblWriteTextDump.setLabelFor(chkTextDump);
		frmAbookVcf.getContentPane().add(chkTextDump, "4, 12");
		
		JLabel lblWriteCsvDump = new JLabel("Write csv dump");
		frmAbookVcf.getContentPane().add(lblWriteCsvDump, "2, 14");
		
		JCheckBox chkCSVDump = new JCheckBox("");
		lblWriteCsvDump.setLabelFor(chkCSVDump);
		frmAbookVcf.getContentPane().add(chkCSVDump, "4, 14");
		
		JLabel lblCmdCall = new JLabel("cmd call");
		frmAbookVcf.getContentPane().add(lblCmdCall, "2, 16");
		
		JLabel lblCMDLine = new JLabel("");
		lblCmdCall.setLabelFor(lblCMDLine);
		lblCMDLine.setFont(lblCMDLine.getFont().deriveFont(lblCMDLine.getFont().getStyle() & ~Font.BOLD, lblCMDLine.getFont().getSize() - 2f));
		frmAbookVcf.getContentPane().add(lblCMDLine, "4, 16");
	}

	/**
	 * Selects abook file.
	 */
	private void selectABookFile() {

		JFileChooser chsDialog = new JFileChooser();
		chsDialog.setDialogTitle("ABook file");
		chsDialog.setDialogType(JFileChooser.OPEN_DIALOG);
		chsDialog.setAcceptAllFileFilterUsed(false);
		chsDialog.addChoosableFileFilter(new FileNameExtensionFilter("ABook files", "mab"));
		chsDialog.setAcceptAllFileFilterUsed(true);
		
		if (chsDialog.showOpenDialog(frmAbookVcf) == JFileChooser.APPROVE_OPTION) {
			txtABook.setText(chsDialog.getSelectedFile().getAbsolutePath());
		}
	
	}

	/**
	 * Selects VCF file.
	 */
	private void selectVCFFile() {

		JFileChooser chsDialog = new JFileChooser();
		chsDialog.setDialogTitle("VCF file");
		chsDialog.setDialogType(JFileChooser.SAVE_DIALOG);
		chsDialog.setAcceptAllFileFilterUsed(false);
		chsDialog.addChoosableFileFilter(new FileNameExtensionFilter("VCF files", "vcf"));
		chsDialog.setAcceptAllFileFilterUsed(true);
		
		if (chsDialog.showSaveDialog(frmAbookVcf) == JFileChooser.APPROVE_OPTION) {
			txtVCF.setText(chsDialog.getSelectedFile().getAbsolutePath());
		}
	
	}
	
	/**
	 * Updates command line call display
	 */
	private void updateCMDLine() {
		
	}

}
