package TypeRacerPackage;

import java.awt.*;
import java.awt.event.*;

public class TypeFrame extends Frame {

	public TypeFrame() {

		MenuBar bar = new MenuBar();
		Menu file = new Menu();
		MenuItem fileExit = new MenuItem();

		file.setLabel("File");
		fileExit.setLabel("Exit");

		// action listener for menu listener
		fileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TypeFrame.this.windowClosed();
			}
		});
		file.add(fileExit);
		bar.add(file);

		setTitle("TypingGame");
		setMenuBar(bar);
		setSize(new Dimension(400, 400));

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				TypeFrame.this.windowClosed();
			}
		});
	}

	/**
	 * Shutdown procedure when run as an application.
	 */
	private void windowClosed() {

		// TODO: Check if it is safe to close the application

		// Exit application.
		System.exit(0);
	}
}
