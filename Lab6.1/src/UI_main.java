import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
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

import java.util.Comparator;
import java.util.LinkedList;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.wb.swt.SWTResourceManager;

public class UI_main {

	protected static Shell shell;
	public static volatile LinkedList<Baby> myList;
	private String headers[] = { "Name", "Free", "Sex", "Age" };
	protected static volatile Table table;
	protected static boolean isWorking = false;
	private static long creationTime;
	static boolean sName = false;
	static boolean sAge = false;
	static boolean sFree = false;
	static boolean sSex = false;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 * @wbp.parser.entryPoint
	 */

	public static void main(String[] args) {
		// Display.getDefault().syncExec(new Runnable(){
		// public void run() {
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
		// }
		// });
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
		shell.setImage(SWTResourceManager.getImage("C:\\Users\\Vitaliy\\Documents\\GitHub\\programming-second-semester\\Lab6.1\\1494873885_law_16x16.gif"));
		shell.setSize(469, 289);
		shell.setLocation(200, 200);
		;
		shell.setText("SWT Application");
		shell.setLayout(new FormLayout());
		Composite composite_2 = new Composite(shell, SWT.NONE);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.right = new FormAttachment(0, 184);
		fd_composite_2.top = new FormAttachment(0);
		fd_composite_2.bottom = new FormAttachment(0, 23);
		composite_2.setLayoutData(fd_composite_2);

		ToolBar toolBar = new ToolBar(composite_2, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(0, 0, 174, 23);

		ToolItem tltmSave = new ToolItem(toolBar, SWT.PUSH);
		tltmSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Writer outThread = new Writer("out.csv");
				outThread.start();
				do {
					try {
						outThread.join();
					} catch (InterruptedException ex) {
					}
				} while (outThread.isAlive());
				if (outThread.getSuccess()) {
					shell.setEnabled(false);
					UI_notification.setNotification("Collection has been succefully saved");
					UI_notification.main();
				}
			}
		});
		tltmSave.setText("Save");

		ToolItem tltmLoad = new ToolItem(toolBar, SWT.PUSH);
		tltmLoad.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				myList.clear();
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
					refresh();
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
		
		ToolItem tltmFeedback = new ToolItem(toolBar, SWT.NONE);
		tltmFeedback.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UI_feedback.main();
			}
		});
		tltmFeedback.setText("FeedBack");

		Composite composite_1 = new Composite(shell, SWT.NONE);
		fd_composite_2.left = new FormAttachment(composite_1, 0, SWT.LEFT);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.left = new FormAttachment(0, 10);
		fd_composite_1.right = new FormAttachment(100, -10);
		fd_composite_1.bottom = new FormAttachment(100);
		composite_1.setLayoutData(fd_composite_1);
		composite_1.setLayout(new GridLayout(5, false));

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
					if (impPath == null) {
						return;
					}
					Reader impThread = new Reader(impPath);
					impThread.start();
					do {
						try {
							impThread.join();
						} catch (InterruptedException ex) {
						}
					} while (impThread.isAlive());
				} catch (Exception ex) {
				}
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
		
		Button btnFilter = new Button(composite_1, SWT.NONE);
		btnFilter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UI_filter.main();
			}
		});
		GridData gd_btnFilter = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnFilter.widthHint = 82;
		btnFilter.setLayoutData(gd_btnFilter);
		btnFilter.setText("Filter");
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
		Listener sortListener = new Listener() {
			public void handleEvent(Event e) {
				TableColumn column = (TableColumn) e.widget;
				switch (column.getText()) {
				case "Name":
					sName = !sName;
					myList.sort(new Comparator<Baby>() {
						public int compare(Baby b1, Baby b2) {
							if (sName)
								return b1.getName().length() - b2.getName().length();
							else
								return b2.getName().length() - b1.getName().length();
						}
					});
					refresh();
					break;
				case "Age":
					sAge = !sAge;
					myList.sort(new Comparator<Baby>() {
						public int compare(Baby b1, Baby b2) {
							if (sAge)
								return Integer.parseInt(b1.getAge()) - Integer.parseInt(b2.getAge());
							else
								return Integer.parseInt(b2.getAge()) - Integer.parseInt(b1.getAge());
						}
					});
					refresh();
					break;
				case "Free":
					sFree = !sFree;
					myList.sort(new Comparator<Baby>() {
						public int compare(Baby b1, Baby b2) {
							if (sFree) {
								if (Boolean.parseBoolean(b1.getFree())) {
									return 1;
								} else
									return -1;

							} else {
								if (Boolean.parseBoolean(b1.getFree())) {
									return -1;
								} else
									return 1;
							}
						}
					});
					refresh();
					break;
				case "Sex":
					sSex = !sSex;
					myList.sort(new Comparator<Baby>() {
						public int compare(Baby b1, Baby b2) {
							Integer sex1;
							if (b1.getSex().equals("male")) {
								sex1 = 1;
							} else if (b1.getSex().equals("female")) {
								sex1 = 0;
							} else
								sex1 = -1;
							Integer sex2;
							if (b2.getSex().equals("male")) {
								sex2 = 1;
							} else if (b2.getSex().equals("female")) {
								sex2 = 0;
							} else
								sex2 = -1;
							if (sSex)
							return sex1 - sex2;
							else return sex2 - sex1;
						}
					});
					refresh();
					break;
				}
			}
		};
		for (

				int i = 0; i < headers.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(headers[i]);
			column.addListener(SWT.Selection, sortListener);
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
