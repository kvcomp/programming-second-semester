import java.util.LinkedList;
import java.util.ListIterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Button;

public class UI_filter extends Shell {
	protected static Table table;
	protected static boolean isWorking = false;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static UI_filter shell;
	
	public static void main() {
		isWorking = true;
		try {
			Display display = Display.getDefault();
			shell = new UI_filter(display);
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
		isWorking = false;
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public UI_filter(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		Label lblName = new Label(this, SWT.NONE);
		lblName.setBounds(277, 12, 70, 20);
		lblName.setText("Enter filter:");
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(277, 70, 84, 110);
		
		Text text = new Text(this, SWT.BORDER);
		text.setBounds(280, 38, 118, 26);
		
		Button btnName = new Button(composite, SWT.NONE);
		btnName.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String cur = text.getText();
				LinkedList<Baby> curList = new LinkedList<Baby>(UI_main.myList);
				ListIterator<Baby> it = curList.listIterator();
                while (it.hasNext()) {
                    if (!it.next().getName().contains(cur)) {
                        it.remove();
                    }
                }
                table.removeAll();
                for (int i = 0; i < curList.size(); i++) {
        			TableItem item = new TableItem(table, SWT.NONE);
        			item.setText(0, curList.get(i).getName());
        			item.setText(1, curList.get(i).getFree());
        			item.setText(2, curList.get(i).getSex());
        			item.setText(3, curList.get(i).getAge());
        		}
			}
		});
		btnName.setSelection(true);
		btnName.setBounds(12, 0, 57, 20);
		btnName.setText("Name");
		
		Button btnFree = new Button(composite, SWT.NONE);
		btnFree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String cur = text.getText();
				LinkedList<Baby> curList = new LinkedList<Baby>(UI_main.myList);
				ListIterator<Baby> it = curList.listIterator();
                while (it.hasNext()) {
                    if (!it.next().getFree().contains(cur)) {
                        it.remove();
                    }
                }
                table.removeAll();
                for (int i = 0; i < curList.size(); i++) {
        			TableItem item = new TableItem(table, SWT.NONE);
        			item.setText(0, curList.get(i).getName());
        			item.setText(1, curList.get(i).getFree());
        			item.setText(2, curList.get(i).getSex());
        			item.setText(3, curList.get(i).getAge());
        		}
			}
		});
		btnFree.setBounds(12, 26, 57, 20);
		btnFree.setText("Free");
		
		Button btnSex = new Button(composite, SWT.NONE);
		btnSex.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String cur = text.getText();
				LinkedList<Baby> curList = new LinkedList<Baby>(UI_main.myList);
				ListIterator<Baby> it = curList.listIterator();
                while (it.hasNext()) {
                    if (!it.next().getSex().contains(cur)) {
                        it.remove();
                    }
                }
                table.removeAll();
                for (int i = 0; i < curList.size(); i++) {
        			TableItem item = new TableItem(table, SWT.NONE);
        			item.setText(0, curList.get(i).getName());
        			item.setText(1, curList.get(i).getFree());
        			item.setText(2, curList.get(i).getSex());
        			item.setText(3, curList.get(i).getAge());
        		}
			}
		});
		btnSex.setBounds(12, 52, 57, 20);
		btnSex.setText("Sex");
		
		Button btnAge = new Button(composite, SWT.NONE);
		btnAge.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String cur = text.getText();
				LinkedList<Baby> curList = new LinkedList<Baby>(UI_main.myList);
				ListIterator<Baby> it = curList.listIterator();
                while (it.hasNext()) {
                    if (!it.next().getAge().contains(cur)) {
                        it.remove();
                        table.removeAll();
                        for (int i = 0; i < curList.size(); i++) {
                			TableItem item = new TableItem(table, SWT.NONE);
                			item.setText(0, curList.get(i).getName());
                			item.setText(1, curList.get(i).getFree());
                			item.setText(2, curList.get(i).getSex());
                			item.setText(3, curList.get(i).getAge());
                		}
                    }
                }
			}
		});
		btnAge.setText("Age");
		btnAge.setBounds(12, 78, 57, 20);
		
		
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 12, 251, 265);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		data.widthHint = 300;
		table.setLayoutData(data);
		
		String headers[] = { "Name", "Free", "Sex", "Age" };
		for (int i = 0; i < headers.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(headers[i]);
			table.getColumn(i).pack();
		}
		for (int i = 0; i < UI_main.myList.size(); i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, UI_main.myList.get(i).getName());
			item.setText(1, UI_main.myList.get(i).getFree());
			item.setText(2, UI_main.myList.get(i).getSex());
			item.setText(3, UI_main.myList.get(i).getAge());
		}
		for (int i = 0; i < headers.length; i++) {
			table.getColumn(i).pack();
		}
		
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Update");
		setSize(425, 329);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
