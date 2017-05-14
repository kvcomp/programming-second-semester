import java.util.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.widgets.Composite;

public class UI_add extends Shell {
	private Text text;
	private Scale scale;
	protected static boolean isWorking = false;
	public static UI_add shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main() {
		isWorking = true;
		try {
			Display display = Display.getDefault();
			shell = new UI_add(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
					isWorking = false;
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
		
		Label lblSex = new Label(this, SWT.NONE);
		lblSex.setBounds(145, 114, 70, 20);
		lblSex.setText("Sex");
		
		Label lblAge = new Label(this, SWT.NONE);
		lblAge.setBounds(145, 175, 33, 20);
		lblAge.setText("Age:");
		
		Label text_age = new Label(this, SWT.NONE);
		text_age.setBounds(184, 175, 70, 20);
		text_age.setText("0");
		
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
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnCancel.setBounds(10, 170, 90, 30);
		btnCancel.setText("Cancel");
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(128, 140, 224, 21);
		
		Button btnMale = new Button(composite, SWT.RADIO);
		btnMale.setBounds(12, 0, 57, 20);
		btnMale.setText("Male");
		
		Button btnFemale = new Button(composite, SWT.RADIO);
		btnFemale.setBounds(75, 0, 72, 20);
		btnFemale.setText("Female");
		
		Button btnDoubt = new Button(composite, SWT.RADIO);
		btnDoubt.setBounds(153, 0, 66, 20);
		btnDoubt.setText("Doubt");
		
		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setBounds(127, 88, 225, 20);
		
		Button btnTrue = new Button(composite_1, SWT.RADIO);
		btnTrue.setBounds(10, 0, 57, 20);
		btnTrue.setText("True");
		
		Button btnFalse = new Button(composite_1, SWT.RADIO);
		btnFalse.setBounds(73, 0, 57, 20);
		btnFalse.setText("False");
		
		Button btnAccept = new Button(this, SWT.NONE);
		btnAccept.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (text.getText().isEmpty()) {
					UI_warning.setWarning("Name can not be empty");
					UI_warning.main();
				} else if (btnTrue.getSelection() == btnFalse.getSelection()) {
					UI_warning.setWarning("'Free' field can not be empty");
					UI_warning.main();
				} else if (btnMale.getSelection() == btnFemale.getSelection() && btnMale.getSelection() == btnDoubt.getSelection()){
					UI_warning.setWarning("'Sex' field can not be empty");
					UI_warning.main();
				} else {
					String sex;
					if (btnMale.getSelection()) {
						sex = new String("male");
					} else if (btnFemale.getSelection()) {
						sex = new String("female");
					} else sex = new String ("doubt");
					Baby baby = new Baby(text.getText(), btnTrue.getSelection() == true, sex, scale.getSelection());
					LinkedList<Baby> cur = new LinkedList<Baby> (UI_main.myList);
					cur.sort(new Comparator<Baby>() {
                        public int compare(Baby b1, Baby b2) {
                            return b1.compareTo(b2);
                        }
                    });
					Boolean isMax = cur.isEmpty()?true:(baby.compareTo(cur.getLast()) > 0);
					Boolean isMin = cur.isEmpty()?true:(baby.compareTo(cur.getFirst()) < 0);
					if (!btnAdd.getSelection() && !btnAddIfMax.getSelection() && !btnAddIfMin.getSelection()) {
						UI_warning.setWarning("Select type");
						UI_warning.main();
					} else if (btnAdd.getSelection() && !btnAddIfMax.getSelection() && !btnAddIfMin.getSelection()) {
						UI_main.myList.add(baby);
					} else if (!btnAddIfMax.getSelection() && btnAddIfMin.getSelection() && isMin) {
						UI_main.myList.addFirst(baby);
					} else if (btnAddIfMax.getSelection() && !btnAddIfMin.getSelection() && isMax) {
						UI_main.myList.addLast(baby);
					} else if (btnAddIfMax.getSelection() && btnAddIfMin.getSelection() && isMax && isMin) {
						UI_main.myList.add(baby);
					}
					UI_main.refresh();
				}
			}
		});
		btnAccept.setText("Accept");
		btnAccept.setBounds(10, 213, 90, 30);
		
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
