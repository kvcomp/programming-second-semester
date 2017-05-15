import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UI_feedback extends Shell {
	private Text text;
	private Text text_1;
	private Label label;
	private Label label_1;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main() {
		try {
			Display display = Display.getDefault();
			UI_feedback shell = new UI_feedback(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public UI_feedback(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		text = new Text(this, SWT.SINGLE);
		text.setBounds(93, 10, 187, 21);
		
		text_1 = new Text(this, SWT.MULTI);
		text_1.setBounds(92, 52, 188, 115);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String fName = text.getText();
				String fFB = text_1.getText();
					BufferedWriter bw = null;
					try {
						File file = new File("fb.txt");
						if (!file.exists()) {
							file.createNewFile();
						}
						if (file.canWrite()) {
							FileWriter fw = new FileWriter(file);
							bw = new BufferedWriter(fw);
							bw.write(fName + ":" + fFB);
							bw.flush();
						} else {
			}
					} catch (IOException ex) {

					} finally {
						try {
							if (bw != null) {
								bw.close();
							}
						} catch (Exception ex) {
							System.out.println("Error in closing the BufferedWriter" + ex);
						}
					}
					text.setText("");
					text_1.setText("");
					UI_notification.setNotification("Success");
					UI_notification.main();
				}
		});

	btnNewButton.setBounds(11,142,75,25);btnNewButton.setText("Send");

	label=new Label(this,SWT.NONE);label.setBounds(32,10,55,15);label.setText("Ваше имя");

	label_1=new Label(this,SWT.RIGHT);label_1.setText("Ваш отзыв");label_1.setBounds(21,56,65,15);

	createContents();

	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(304, 207);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
