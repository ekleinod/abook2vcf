package de.edgesoft.abook2vcf;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Simple GUI for {@link ABook2VCF}.
 * 
 * @author Ekkart Kleinod
 * @version 0.2
 * @since 0.2
 */
public class ABook2VCFGUI {

	private JFrame frmAbookVcf;

	private String sABookFile = null;
	private String sVCFFile = null;
	private int iVCFCount = 0;
	private String sVersion = null;
	private boolean bDoubles = false;
	private boolean bTextDump = false;
	private boolean bCSVDump = false;
	private JTextField txtABook;
	private JTextField txtVCF;
	private JLabel lblCMDLine;
	private JCheckBox chkCSVDump;
	private JCheckBox chkTextDump;
	private JCheckBox chkDoubles;
	private JComboBox cboVersion;
	private JSpinner spnVCFCount;
	private JButton btnConvert;
	
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
		frmAbookVcf.setBounds(100, 100, 563, 298);
		frmAbookVcf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAbookVcf.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel pnlMain = new JPanel();
		frmAbookVcf.getContentPane().add(pnlMain, BorderLayout.CENTER);
		GridBagLayout gbl_pnlMain = new GridBagLayout();
		gbl_pnlMain.columnWidths = new int[]{0, 0, 0};
		gbl_pnlMain.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pnlMain.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pnlMain.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlMain.setLayout(gbl_pnlMain);
		
		JLabel lblABook = new JLabel("ABook file (input)");
		lblABook.setDisplayedMnemonic('a');
		GridBagConstraints gbc_lblABook = new GridBagConstraints();
		gbc_lblABook.insets = new Insets(0, 0, 5, 5);
		gbc_lblABook.anchor = GridBagConstraints.WEST;
		gbc_lblABook.gridx = 0;
		gbc_lblABook.gridy = 0;
		pnlMain.add(lblABook, gbc_lblABook);
		
		txtABook = new JTextField();
		txtABook.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateCMDLine();
			}
		});
		txtABook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateCMDLine();
			}
		});
		lblABook.setLabelFor(txtABook);
		txtABook.setColumns(10);
		GridBagConstraints gbc_txtABook = new GridBagConstraints();
		gbc_txtABook.anchor = GridBagConstraints.WEST;
		gbc_txtABook.insets = new Insets(0, 0, 5, 5);
		gbc_txtABook.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtABook.gridx = 1;
		gbc_txtABook.gridy = 0;
		pnlMain.add(txtABook, gbc_txtABook);
		
		JButton btnABook = new JButton("");
		btnABook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectABookFile();
				updateCMDLine();
			}
		});
		btnABook.setIcon(new ImageIcon(ABook2VCFGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")));
		GridBagConstraints gbc_btnABook = new GridBagConstraints();
		gbc_btnABook.insets = new Insets(0, 0, 5, 5);
		gbc_btnABook.gridx = 2;
		gbc_btnABook.gridy = 0;
		pnlMain.add(btnABook, gbc_btnABook);
		
		JLabel lblABookDefault = new JLabel("default: abook.mab");
		lblABookDefault.setFont(lblABookDefault.getFont().deriveFont(lblABookDefault.getFont().getStyle() & ~Font.BOLD | Font.ITALIC, lblABookDefault.getFont().getSize() - 2f));
		GridBagConstraints gbc_lblABookDefault = new GridBagConstraints();
		gbc_lblABookDefault.anchor = GridBagConstraints.WEST;
		gbc_lblABookDefault.insets = new Insets(0, 0, 5, 0);
		gbc_lblABookDefault.gridx = 3;
		gbc_lblABookDefault.gridy = 0;
		pnlMain.add(lblABookDefault, gbc_lblABookDefault);
		
		JLabel lblVCF = new JLabel("VCF file (output)");
		lblVCF.setDisplayedMnemonic('v');
		GridBagConstraints gbc_lblVCF = new GridBagConstraints();
		gbc_lblVCF.anchor = GridBagConstraints.WEST;
		gbc_lblVCF.insets = new Insets(0, 0, 5, 5);
		gbc_lblVCF.gridx = 0;
		gbc_lblVCF.gridy = 1;
		pnlMain.add(lblVCF, gbc_lblVCF);
		
		txtVCF = new JTextField();
		txtVCF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateCMDLine();
			}
		});
		txtVCF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateCMDLine();
			}
		});
		lblVCF.setLabelFor(txtVCF);
		txtVCF.setColumns(10);
		GridBagConstraints gbc_txtVCF = new GridBagConstraints();
		gbc_txtVCF.anchor = GridBagConstraints.WEST;
		gbc_txtVCF.insets = new Insets(0, 0, 5, 5);
		gbc_txtVCF.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtVCF.gridx = 1;
		gbc_txtVCF.gridy = 1;
		pnlMain.add(txtVCF, gbc_txtVCF);
		
		JButton btnVCF = new JButton("");
		btnVCF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectVCFFile();
				updateCMDLine();
			}
		});
		btnVCF.setIcon(new ImageIcon(ABook2VCFGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		GridBagConstraints gbc_btnVCF = new GridBagConstraints();
		gbc_btnVCF.insets = new Insets(0, 0, 5, 5);
		gbc_btnVCF.gridx = 2;
		gbc_btnVCF.gridy = 1;
		pnlMain.add(btnVCF, gbc_btnVCF);
		
		JLabel lblVCFDefault = new JLabel("default: abook.vcf");
		lblVCFDefault.setFont(lblVCFDefault.getFont().deriveFont(lblVCFDefault.getFont().getStyle() & ~Font.BOLD | Font.ITALIC, lblVCFDefault.getFont().getSize() - 2f));
		GridBagConstraints gbc_lblVCFDefault = new GridBagConstraints();
		gbc_lblVCFDefault.anchor = GridBagConstraints.WEST;
		gbc_lblVCFDefault.insets = new Insets(0, 0, 5, 0);
		gbc_lblVCFDefault.gridx = 3;
		gbc_lblVCFDefault.gridy = 1;
		pnlMain.add(lblVCFDefault, gbc_lblVCFDefault);
		
		JLabel lblVCFCount = new JLabel("Cards per file");
		lblVCFCount.setDisplayedMnemonic('r');
		GridBagConstraints gbc_lblVCFCount = new GridBagConstraints();
		gbc_lblVCFCount.anchor = GridBagConstraints.WEST;
		gbc_lblVCFCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblVCFCount.gridx = 0;
		gbc_lblVCFCount.gridy = 2;
		pnlMain.add(lblVCFCount, gbc_lblVCFCount);
		
		spnVCFCount = new JSpinner();
		spnVCFCount.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateCMDLine();
			}
		});
		spnVCFCount.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		lblVCFCount.setLabelFor(spnVCFCount);
		GridBagConstraints gbc_spnVCFCount = new GridBagConstraints();
		gbc_spnVCFCount.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnVCFCount.anchor = GridBagConstraints.WEST;
		gbc_spnVCFCount.insets = new Insets(0, 0, 5, 5);
		gbc_spnVCFCount.gridx = 1;
		gbc_spnVCFCount.gridy = 2;
		pnlMain.add(spnVCFCount, gbc_spnVCFCount);
		
		JLabel lblVCFCountDefault = new JLabel("0 = unlimited");
		lblVCFCountDefault.setFont(lblVCFCountDefault.getFont().deriveFont(lblVCFCountDefault.getFont().getStyle() & ~Font.BOLD | Font.ITALIC, lblVCFCountDefault.getFont().getSize() - 2f));
		GridBagConstraints gbc_lblVCFCountDefault = new GridBagConstraints();
		gbc_lblVCFCountDefault.anchor = GridBagConstraints.WEST;
		gbc_lblVCFCountDefault.insets = new Insets(0, 0, 5, 0);
		gbc_lblVCFCountDefault.gridx = 3;
		gbc_lblVCFCountDefault.gridy = 2;
		pnlMain.add(lblVCFCountDefault, gbc_lblVCFCountDefault);
		
		JLabel lblVersion = new JLabel("VCard version");
		lblVersion.setDisplayedMnemonic('i');
		lblVersion.setLabelFor(spnVCFCount);
		GridBagConstraints gbc_lblVersion = new GridBagConstraints();
		gbc_lblVersion.anchor = GridBagConstraints.WEST;
		gbc_lblVersion.insets = new Insets(0, 0, 5, 5);
		gbc_lblVersion.gridx = 0;
		gbc_lblVersion.gridy = 3;
		pnlMain.add(lblVersion, gbc_lblVersion);
		
		cboVersion = new JComboBox();
		cboVersion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateCMDLine();
			}
		});
		cboVersion.setModel(new DefaultComboBoxModel(new String[] {"3.0", "4.0"}));
		cboVersion.setSelectedIndex(0);
		cboVersion.setMaximumRowCount(2);
		GridBagConstraints gbc_cboVersion = new GridBagConstraints();
		gbc_cboVersion.anchor = GridBagConstraints.WEST;
		gbc_cboVersion.insets = new Insets(0, 0, 5, 5);
		gbc_cboVersion.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboVersion.gridx = 1;
		gbc_cboVersion.gridy = 3;
		pnlMain.add(cboVersion, gbc_cboVersion);
		
		JLabel lblDoubles = new JLabel("Write doubles file");
		lblDoubles.setDisplayedMnemonic('d');
		GridBagConstraints gbc_lblDoubles = new GridBagConstraints();
		gbc_lblDoubles.anchor = GridBagConstraints.WEST;
		gbc_lblDoubles.insets = new Insets(0, 0, 5, 5);
		gbc_lblDoubles.gridx = 0;
		gbc_lblDoubles.gridy = 4;
		pnlMain.add(lblDoubles, gbc_lblDoubles);
		
		chkDoubles = new JCheckBox("");
		chkDoubles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateCMDLine();
			}
		});
		lblDoubles.setLabelFor(chkDoubles);
		GridBagConstraints gbc_chkDoubles = new GridBagConstraints();
		gbc_chkDoubles.anchor = GridBagConstraints.WEST;
		gbc_chkDoubles.insets = new Insets(0, 0, 5, 5);
		gbc_chkDoubles.gridx = 1;
		gbc_chkDoubles.gridy = 4;
		pnlMain.add(chkDoubles, gbc_chkDoubles);
		
		JLabel lblTextDump = new JLabel("Write text dump");
		lblTextDump.setDisplayedMnemonic('t');
		GridBagConstraints gbc_lblTextDump = new GridBagConstraints();
		gbc_lblTextDump.anchor = GridBagConstraints.WEST;
		gbc_lblTextDump.insets = new Insets(0, 0, 5, 5);
		gbc_lblTextDump.gridx = 0;
		gbc_lblTextDump.gridy = 5;
		pnlMain.add(lblTextDump, gbc_lblTextDump);
		
		chkTextDump = new JCheckBox("");
		chkTextDump.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateCMDLine();
			}
		});
		lblTextDump.setLabelFor(chkTextDump);
		GridBagConstraints gbc_chkTextDump = new GridBagConstraints();
		gbc_chkTextDump.anchor = GridBagConstraints.WEST;
		gbc_chkTextDump.insets = new Insets(0, 0, 5, 5);
		gbc_chkTextDump.gridx = 1;
		gbc_chkTextDump.gridy = 5;
		pnlMain.add(chkTextDump, gbc_chkTextDump);
		
		JLabel lblCSVDump = new JLabel("Write csv dump");
		lblCSVDump.setDisplayedMnemonic('s');
		GridBagConstraints gbc_lblCSVDump = new GridBagConstraints();
		gbc_lblCSVDump.anchor = GridBagConstraints.WEST;
		gbc_lblCSVDump.insets = new Insets(0, 0, 5, 5);
		gbc_lblCSVDump.gridx = 0;
		gbc_lblCSVDump.gridy = 6;
		pnlMain.add(lblCSVDump, gbc_lblCSVDump);
		
		chkCSVDump = new JCheckBox("");
		chkCSVDump.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateCMDLine();
			}
		});
		lblCSVDump.setLabelFor(chkCSVDump);
		GridBagConstraints gbc_chkCSVDump = new GridBagConstraints();
		gbc_chkCSVDump.anchor = GridBagConstraints.WEST;
		gbc_chkCSVDump.insets = new Insets(0, 0, 5, 5);
		gbc_chkCSVDump.gridx = 1;
		gbc_chkCSVDump.gridy = 6;
		pnlMain.add(chkCSVDump, gbc_chkCSVDump);
		
		JLabel lblCMDCall = new JLabel("cmd call");
		GridBagConstraints gbc_lblCMDCall = new GridBagConstraints();
		gbc_lblCMDCall.anchor = GridBagConstraints.WEST;
		gbc_lblCMDCall.insets = new Insets(0, 0, 0, 5);
		gbc_lblCMDCall.gridx = 0;
		gbc_lblCMDCall.gridy = 7;
		pnlMain.add(lblCMDCall, gbc_lblCMDCall);
		
		lblCMDLine = new JLabel("abook2vcf");
		lblCMDLine.setEnabled(false);
		lblCMDCall.setLabelFor(lblCMDLine);
		lblCMDLine.setFont(lblCMDLine.getFont().deriveFont(lblCMDLine.getFont().getStyle() & ~Font.BOLD, lblCMDLine.getFont().getSize() - 2f));
		GridBagConstraints gbc_lblCMDLine = new GridBagConstraints();
		gbc_lblCMDLine.gridwidth = 3;
		gbc_lblCMDLine.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCMDLine.anchor = GridBagConstraints.WEST;
		gbc_lblCMDLine.insets = new Insets(0, 0, 0, 5);
		gbc_lblCMDLine.gridx = 1;
		gbc_lblCMDLine.gridy = 7;
		pnlMain.add(lblCMDLine, gbc_lblCMDLine);
		
		JPanel pnlBottom = new JPanel();
		frmAbookVcf.getContentPane().add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnConvert = new JButton("Convert");
		btnConvert.setMnemonic('c');
		btnConvert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				executeConversion();
			}
		});
		pnlBottom.add(btnConvert);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setMnemonic('x');
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		pnlBottom.add(btnExit);
	}
	
	/**
	 * Selects abook file.
	 * 
	 * @version 0.2
	 * @since 0.2
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
	 * 
	 * @version 0.2
	 * @since 0.2
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
	 * 
	 * @version 0.2
	 * @since 0.2
	 */
	private void updateCMDLine() {

		StringBuilder sbCMD = new StringBuilder("abook2vcf");
		
		sABookFile = (txtABook.getText().trim().isEmpty()) ? null : txtABook.getText().trim();
		if (sABookFile != null) {
			sbCMD.append(String.format(" --%s %s", ABook2VCF.OPT_ABOOK.getOption().getLongOpt(), sABookFile));
		}
		
		sVCFFile = (txtVCF.getText().trim().isEmpty()) ? null : txtVCF.getText().trim();
		if (sVCFFile != null) {
			sbCMD.append(String.format(" --%s %s", ABook2VCF.OPT_OUTFILE.getOption().getLongOpt(), sVCFFile));
		}
		
		iVCFCount = (Integer) ((SpinnerNumberModel) spnVCFCount.getModel()).getNumber();
		if (iVCFCount > 0) {
			sbCMD.append(String.format(" --%s %d", ABook2VCF.OPT_VCFCOUNT.getOption().getLongOpt(), iVCFCount));
		}
		
		sVersion = (String) cboVersion.getSelectedItem();
		if (!sVersion.equals(ABook2VCF.VERSION_3)) {
			sbCMD.append(String.format(" --%s %s", ABook2VCF.OPT_VERSION.getOption().getLongOpt(), sVersion));
		}
		
		bDoubles = (chkDoubles != null) && chkDoubles.isSelected();
		if (bDoubles) {
			sbCMD.append(String.format(" --%s", ABook2VCF.OPT_DOUBLES.getOption().getLongOpt()));
		}
		
		bTextDump = (chkTextDump != null) && chkTextDump.isSelected();
		if (bTextDump) {
			sbCMD.append(String.format(" --%s", ABook2VCF.OPT_TEXTDUMP.getOption().getLongOpt()));
		}
		
		bCSVDump = (chkCSVDump != null) && chkCSVDump.isSelected();
		if (bCSVDump) {
			sbCMD.append(String.format(" --%s", ABook2VCF.OPT_CSVDUMP.getOption().getLongOpt()));
		}
		
		if (lblCMDLine != null) {
			lblCMDLine.setText(sbCMD.toString());
		}
	}
	
	/**
	 * Executes conversion.
	 */
	private void executeConversion() {
		btnConvert.setEnabled(false);
		try {
			ByteArrayOutputStream stmOut = new ByteArrayOutputStream();
			try {
				ABook2VCF.setLoggingStream(stmOut);
				ABook2VCF.convertABook(sABookFile, sVCFFile, iVCFCount, sVersion, bDoubles, bTextDump, bCSVDump);
				JOptionPane.showMessageDialog(frmAbookVcf, 
						"Conversion successful.",
						ABook2VCF.class.getSimpleName(),
						JOptionPane.INFORMATION_MESSAGE);
			} catch (ABookException e) {
				ABook2VCF.log(e);
				ABook2VCF.flushLog();
				JOptionPane.showMessageDialog(frmAbookVcf, 
						String.format("Conversion failed:%n%s", e.getMessage()),
						ABook2VCF.class.getSimpleName(),
						JOptionPane.ERROR_MESSAGE);
				JOptionPane.showMessageDialog(frmAbookVcf, 
						String.format("Log:%n%s", stmOut.toString(StandardCharsets.UTF_8.name())),
						ABook2VCF.class.getSimpleName(),
						JOptionPane.ERROR_MESSAGE);
			} finally {
				stmOut.close();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frmAbookVcf, 
					String.format("Conversion failed:%n%s", e.getMessage()),
					ABook2VCF.class.getSimpleName(),
					JOptionPane.ERROR_MESSAGE);
		}
		btnConvert.setEnabled(true);
	}

}
