import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

public class UI_warning extends Shell {
	
	private static String warning;
	private static Shell shell;


	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main() {
		try {
			Display display = Display.getDefault();
			shell = new UI_warning(display);
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
	
	public static void setWarning (String wrng) {
		warning = wrng;	
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public UI_warning(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblNewLabel.setBounds(0, 0, 234, 100);
		lblNewLabel.setText(warning);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UI_main.shell.setEnabled(true);
				shell.close();;
			}
		});
		btnNewButton.setBounds(78, 106, 75, 25);
		btnNewButton.setText("ОК");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(250, 180);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
