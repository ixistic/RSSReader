package view;

/**
 * Main class. Start the program from here.
 * 
 * @author Veerapat Threeravipark 5510547022
 * 
 */
public class Main {
	/**
	 * Construct user interface.
	 * 
	 * @param args
	 *            command-line arguments.
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new RSSReaderUI();
			}
		});
	}
}
