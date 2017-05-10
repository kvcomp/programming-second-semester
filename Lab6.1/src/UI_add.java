import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.events.TouchListener;
import org.eclipse.swt.events.TouchEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class UI_add extends Shell {
	private Text text;
	private Text text_age;
	private Scale scale;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main() {
		try {
			Display display = Display.getDefault();
			UI_add shell = new UI_add(display);
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
	public UI_add(Display display) {
		setSize(476, 300);
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 70, 20);
		lblNewLabel.setText("Type:");
		
		Button btnAdd = new Button(this, SWT.CHECK);
		btnAdd.setBounds(10, 36, 111, 20);
		btnAdd.setText("add");
		
		Button btnAddIfMin = new Button(this, SWT.CHECK);
		btnAddIfMin.setBounds(10, 62, 111, 20);
		btnAddIfMin.setText("add if min");
		
		Button btnAddIfMax = new Button(this, SWT.CHECK);
		btnAddIfMax.setBounds(10, 88, 111, 20);
		btnAddIfMax.setText("add if max");
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setBounds(145, 10, 70, 20);
		lblNewLabel_1.setText("Name:");
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(145, 33, 207, 26);
		
		Label lblFree = new Label(this, SWT.NONE);
		lblFree.setBounds(145, 62, 70, 20);
		lblFree.setText("Free:");
		
		Button btnTrue = new Button(this, SWT.RADIO);
		btnTrue.setBounds(145, 88, 57, 20);
		btnTrue.setText("True");
		
		Button btnFalse = new Button(this, SWT.RADIO);
		btnFalse.setBounds(208, 88, 57, 20);
		btnFalse.setText("False");
		
		Label lblSex = new Label(this, SWT.NONE);
		lblSex.setBounds(145, 114, 70, 20);
		lblSex.setText("Sex");
		
		Button btnMale = new Button(this, SWT.RADIO);
		btnMale.setBounds(145, 140, 57, 20);
		btnMale.setText("Male");
		
		Button btnFemale = new Button(this, SWT.RADIO);
		btnFemale.setBounds(208, 140, 72, 20);
		btnFemale.setText("Female");
		
		Button btnDoubt = new Button(this, SWT.RADIO);
		btnDoubt.setBounds(286, 140, 66, 20);
		btnDoubt.setText("Doubt");
		
		Label lblAge = new Label(this, SWT.NONE);
		lblAge.setBounds(145, 175, 33, 20);
		lblAge.setText("Age:");
		
		scale = new Scale(this, SWT.NONE);
		scale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text_age.setText(Integer.toString(scale.getSelection()));
			}
		});
		scale.setMaximum(75);
		scale.setBounds(142, 201, 220, 48);
		
		Button btnCancel = new Button(this, SWT.NONE);
		btnCancel.setBounds(10, 170, 90, 30);
		btnCancel.setText("Cancel");
		
		Button btnAccept = new Button(this, SWT.NONE);
		btnAccept.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnAccept.setText("Accept");
		btnAccept.setBounds(10, 213, 90, 30);
		
		text_age = new Text(this, SWT.BORDER);
		text_age.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				scale.setSelection(Integer.parseInt(text_age.getText()));
			}
		});
		text_age.setText("0");
		text_age.setBounds(184, 175, 38, 26);
		
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Add");
		setSize(380, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
