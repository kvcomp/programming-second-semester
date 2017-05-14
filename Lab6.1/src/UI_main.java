import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import java.util.LinkedList;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

public class UI_main {

	protected static Shell shell;
	public static volatile LinkedList<Baby> myList;
	private String headers[] = { "Name", "Free", "Sex", "Age" };
	protected static volatile Table table;
	protected static boolean isWorking = false;
	private static long creationTime;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		isWorking = true;
		creationTime = System.currentTimeMillis();
		myList = new LinkedList<Baby>();
		Thread inThread = new Reader("in.csv");
		inThread.start();
		do {
			try {
				inThread.join();
			} catch (InterruptedException ex) {
			}
		} while (inThread.isAlive());
		try {
			UI_main window = new UI_main();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		isWorking = false;
	}

	public static void refresh() {
		if (isWorking) {
			table.removeAll();
			for (int i = 0; i < myList.size(); i++) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(0, myList.get(i).getName());
				item.setText(1, myList.get(i).getFree());
				item.setText(2, myList.get(i).getSex());
				item.setText(3, myList.get(i).getAge());
			}
		}
		if (UI_remove.isWorking) {
			UI_remove.table.removeAll();
			for (int i = 0; i < UI_main.myList.size(); i++) {
				TableItem item = new TableItem(UI_remove.table, SWT.NONE);
				item.setText(0, UI_main.myList.get(i).getName());
				item.setText(1, UI_main.myList.get(i).getFree());
				item.setText(2, UI_main.myList.get(i).getSex());
				item.setText(3, UI_main.myList.get(i).getAge());
			}
		}
		if (UI_update.isWorking) {
			UI_update.table.removeAll();
			for (int i = 0; i < UI_main.myList.size(); i++) {
				TableItem item = new TableItem(UI_update.table, SWT.NONE);
				item.setText(0, UI_main.myList.get(i).getName());
				item.setText(1, UI_main.myList.get(i).getFree());
				item.setText(2, UI_main.myList.get(i).getSex());
				item.setText(3, UI_main.myList.get(i).getAge());
			}
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(385, 289);
		shell.setLocation(200, 200);
		;
		shell.setText("SWT Application");
		shell.setLayout(new FormLayout());

		Composite composite_2 = new Composite(shell, SWT.NONE);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.right = new FormAttachment(0, 182);
		fd_composite_2.top = new FormAttachment(0);
		fd_composite_2.bottom = new FormAttachment(0, 23);
		composite_2.setLayoutData(fd_composite_2);

		ToolBar toolBar = new ToolBar(composite_2, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(0, 0, 152, 23);

		ToolItem tltmSave = new ToolItem(toolBar, SWT.PUSH);
		tltmSave.setText("Save");

		ToolItem tltmLoad = new ToolItem(toolBar, SWT.PUSH);
		tltmLoad.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Reader inThread = new Reader("in.csv");
				inThread.start();
				do {
					try {
						inThread.join();
					} catch (InterruptedException ex) {
					}
				} while (inThread.isAlive());
				if (inThread.getSuccess()) {
					UI_main.shell.setEnabled(false);
					UI_notification.setNotification("Collection has been succefully reloaded from file");
					UI_notification.main();
					shell.close();
					try {
						UI_main window = new UI_main();
						window.open();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		tltmLoad.setText("Load");

		ToolItem tltmInfo = new ToolItem(toolBar, SWT.PUSH);
		tltmInfo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UI_main.shell.setEnabled(false);
				UI_notification.setNotification("myList:\n" + "тип: " + myList.getClass().getName() + "\n"
						+ "кол-во элементов: " + myList.size() + "\n" + "создана: "
						+ (System.currentTimeMillis() - UI_main.getCreationTime()) + " мс назад");
				UI_notification.main();
			}
		});
		tltmInfo.setText("Info");

		ToolItem tltmHelp = new ToolItem(toolBar, SWT.PUSH);
		tltmHelp.setText("Help");

		Composite composite_1 = new Composite(shell, SWT.NONE);
		fd_composite_2.left = new FormAttachment(composite_1, 0, SWT.LEFT);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.left = new FormAttachment(0, 10);
		fd_composite_1.right = new FormAttachment(100, -10);
		fd_composite_1.bottom = new FormAttachment(100);
		composite_1.setLayoutData(fd_composite_1);
		composite_1.setLayout(new GridLayout(4, false));

		Button btnAdd = new Button(composite_1, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UI_add.main();
			}
		});
		GridData gd_btnAdd = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAdd.widthHint = 80;
		btnAdd.setLayoutData(gd_btnAdd);
		btnAdd.setText("Add");

		Button btnRemove = new Button(composite_1, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UI_remove.main();
			}
		});
		GridData gd_btnRemove = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnRemove.widthHint = 80;
		btnRemove.setLayoutData(gd_btnRemove);
		btnRemove.setText("Remove");

		Button btnImport = new Button(composite_1, SWT.NONE);
		btnImport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell, SWT.OPEN);
				dialog.setFilterNames(new String[] { "CSV Files", "All Files (*.*)" });
				dialog.setFilterExtensions(new String[] { "*.csv", "*.*" }); // Windows
				// wild
				// cards
				dialog.setFilterPath("c:\\"); // Windows path
				dialog.setFileName("import.csv");
				try {
					String impPath = dialog.open();
					if (impPath == null) {return;}
					Reader impThread = new Reader(impPath);
					impThread.start();
					do {
						try {
							impThread.join();
						} catch (InterruptedException ex) {
						}
					} while (impThread.isAlive());
				} catch (Exception ex) {}
				refresh();
			}
		});
		GridData gd_btnImport = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnImport.widthHint = 80;
		btnImport.setLayoutData(gd_btnImport);
		btnImport.setText("Import");

		ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.BORDER);

		Button btnUpdate = new Button(composite_1, SWT.NONE);
		btnUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UI_update.main();
			}
		});
		GridData gd_btnUpdate = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnUpdate.widthHint = 80;
		btnUpdate.setLayoutData(gd_btnUpdate);
		btnUpdate.setText("Update");
		FormData fd_scrolledComposite = new FormData();
		fd_scrolledComposite.top = new FormAttachment(composite_2, 6);
		fd_scrolledComposite.bottom = new FormAttachment(composite_1);
		fd_scrolledComposite.left = new FormAttachment(0, 10);
		fd_scrolledComposite.right = new FormAttachment(100, -10);
		fd_scrolledComposite.height = 5;
		fd_scrolledComposite.width = 5;
		scrolledComposite.setLayoutData(fd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		table = new Table(scrolledComposite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		data.widthHint = 300;
		table.setLayoutData(data);
		for (int i = 0; i < headers.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(headers[i]);
			table.getColumn(i).pack();
		}
		for (int i = 0; i < myList.size(); i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, myList.get(i).getName());
			item.setText(1, myList.get(i).getFree());
			item.setText(2, myList.get(i).getSex());
			item.setText(3, myList.get(i).getAge());
		}
		for (int i = 0; i < headers.length; i++) {
			table.getColumn(i).pack();
		}

		scrolledComposite.setContent(table);
		scrolledComposite.setMinSize(table.computeSize(SWT.DEFAULT, SWT.DEFAULT));

	}

	public static long getCreationTime() {
		return creationTime;
	}
}
