import org.eclipse.swt.widgets.Display;
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
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UI_main {

	protected Shell shell;
	private Table table;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UI_main window = new UI_main();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
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
		shell.setSize(450, 282);
		shell.setText("SWT Application");
		shell.setLayout(new FormLayout());
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0);
		fd_composite.bottom = new FormAttachment(0, 251);
		fd_composite.right = new FormAttachment(0, 434);
		composite.setLayoutData(fd_composite);
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayoutData(new RowData(429, 23));
		
		ToolBar toolBar = new ToolBar(composite_2, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(0, 0, 152, 23);
		
		ToolItem tltmSave = new ToolItem(toolBar, SWT.PUSH);
		tltmSave.setText("Save");
		
		ToolItem tltmLoad = new ToolItem(toolBar, SWT.PUSH);
		tltmLoad.setText("Load");
		
		ToolItem tltmInfo = new ToolItem(toolBar, SWT.PUSH);
		tltmInfo.setText("Info");
		
		ToolItem tltmHelp = new ToolItem(toolBar, SWT.PUSH);
		tltmHelp.setText("Help");
		
		Composite composite_3 = new Composite(composite, SWT.NONE);
		composite_3.setLayoutData(new RowData(430, 175));
		
		table = new Table(composite_3, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(0, 0, 430, 175);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new GridLayout(5, false));
		composite_1.setLayoutData(new RowData(430, 43));
		
		Button btnAdd = new Button(composite_1, SWT.NONE);
		GridData gd_btnAdd = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAdd.widthHint = 80;
		btnAdd.setLayoutData(gd_btnAdd);
		btnAdd.setText("Add");
		
		Button btnRemove = new Button(composite_1, SWT.NONE);
		GridData gd_btnRemove = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnRemove.widthHint = 80;
		btnRemove.setLayoutData(gd_btnRemove);
		btnRemove.setText("Remove");
		
		Button btnClear = new Button(composite_1, SWT.NONE);
		GridData gd_btnClear = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnClear.widthHint = 80;
		btnClear.setLayoutData(gd_btnClear);
		btnClear.setText("Clear");
		
		Button btnUpdate = new Button(composite_1, SWT.NONE);
		GridData gd_btnUpdate = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnUpdate.widthHint = 80;
		btnUpdate.setLayoutData(gd_btnUpdate);
		btnUpdate.setText("Update");
		
		Button btnImport = new Button(composite_1, SWT.NONE);
		GridData gd_btnImport = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnImport.widthHint = 80;
		btnImport.setLayoutData(gd_btnImport);
		btnImport.setText("Import");

	}
}
