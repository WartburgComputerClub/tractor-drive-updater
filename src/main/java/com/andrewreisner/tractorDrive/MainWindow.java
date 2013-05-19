package com.andrewreisner.tractorDrive;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ProgressMonitor;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextPane;

public class MainWindow {

    private JFrame frmTractordriveUpdater;
    private TractorRepo repo;

    /**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())){
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmTractordriveUpdater.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTractordriveUpdater = new JFrame();
		frmTractordriveUpdater.setTitle("TractorDrive Updater");
		frmTractordriveUpdater.setBounds(100, 100, 450, 300);
		frmTractordriveUpdater.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JFileChooser fchoose = new JFileChooser();
		fchoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int retval = fchoose.showOpenDialog(frmTractordriveUpdater.getContentPane());
		
		JPanel panel = new JPanel();
		frmTractordriveUpdater.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblStatus = new JLabel("Ready");
		panel.add(lblStatus, BorderLayout.WEST);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		panel.add(btnClose, BorderLayout.EAST);
		
		JTextPane textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setEditable(false);
		
		JScrollPane jsp = new JScrollPane(textPane);
		frmTractordriveUpdater.getContentPane().add(jsp, BorderLayout.CENTER);
		
		if (retval == JFileChooser.APPROVE_OPTION) {
			try {
					repo = new TractorRepo(fchoose.getSelectedFile().getAbsolutePath());
					StringBuilder html = new StringBuilder();
					ProgressMonitor pm = new ProgressMonitor(frmTractordriveUpdater,"Fetching Updates", "",0, 100);
					repo.fetchUpdates(pm);
					html.append("<html><body><h1>Update Summary: " + repo.getVersion() + " &rarr; " + repo.getLatestVersion() + "</h1>");
					String[] updates = repo.getUpdateVersions();
					html.append("<h2>Installed " +  updates.length + " updates.</h2><hr />");
					for (int i=updates.length-1;i>=0;i--) {
						html.append("<pre>");
						html.append(repo.getReleaseNotes(updates[i]));
						html.append("</pre><hr />");
					}
					html.append("</body></html>");
					textPane.setText(html.toString());
					textPane.setCaretPosition(0);
			}catch (Exception ex){
				ex.printStackTrace();
				JOptionPane.showMessageDialog(frmTractordriveUpdater, "Invalid TractorDrive directory!");
				System.exit(1);
			}
		} else {
			JOptionPane.showMessageDialog(frmTractordriveUpdater, "You must select the tractorDrive directory to update!");
			System.exit(1);
		}
		
		
	}

}