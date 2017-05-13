import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UI_notification extends Shell {
	protected static Shell shell;
	protected static String notification;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main() {
		try {
			Display display = Display.getDefault();
			shell = new UI_notification(display);
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
	
	public static void setNotification (String ntfc) {
		notification = ntfc;
	}

	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public UI_notification(Display display) {
		super(display, SWT.SHELL_TRIM);
		setLayout(new FormLayout());

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(null);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(0, 141);
		fd_composite.right = new FormAttachment(0, 234);
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0);
		composite.setLayoutData(fd_composite);

		Label lbl1 = new Label(composite, SWT.NONE);
		lbl1.setBounds(0, 10, 229, 86);
		lbl1.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lbl1.setAlignment(SWT.CENTER);
		lbl1.setText(notification);

		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UI_main.shell.setEnabled(true);
				shell.close();
			}
		});
		btnNewButton.setBounds(71, 99, 88, 32);
		btnNewButton.setText("OK");
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
