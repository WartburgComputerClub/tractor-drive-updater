import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UIManager;

public class MainWindow {

    private JFrame frame;

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
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JFileChooser fchoose = new JFileChooser();
		fchoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int retval = fchoose.showOpenDialog(frame.getContentPane());
		if (retval == JFileChooser.APPROVE_OPTION) {
			try {
				TractorRepo tractorRepo = new TractorRepo(fchoose.getSelectedFile().getAbsolutePath());
			}catch (Exception ex){
				JOptionPane.showMessageDialog(frame, "Invalid TractorDrive directory!");
				System.exit(1);
			}
		} else {
			JOptionPane.showMessageDialog(frame, "You must select the tractorDrive directory to update!");
			System.exit(1);
		}
		
		
	}

}
