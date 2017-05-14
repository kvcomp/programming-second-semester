import java.io.*;
import java.util.LinkedList;

import org.eclipse.swt.widgets.Display;

import java.io.IOException;

public class Writer extends Thread {

	private String Path;
	private volatile boolean success;

	public Writer(String path) {
		Path = path;
		success = false;
	}

	public boolean getSuccess() {
		return this.success;
	}

	public void setPath(String path) {
		this.Path = path;
	}

	public String getPath() {
		return this.Path;
	}

	public void run() {
		this.write(Path, UI_main.myList);
	}

	private void write(String path, LinkedList<Baby> myList) {
		BufferedWriter bw = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						UI_main.shell.setEnabled(false);
						UI_notification.setNotification("File was created");
						UI_notification.main();
						return;
					}
				});
			}
			if (file.canWrite()) {
				FileWriter fw = new FileWriter(file);
				bw = new BufferedWriter(fw);
				StringBuilder out = new StringBuilder();
				for (int i = 0; i < myList.size(); i++) {
					out.append(myList.get(i).toCSV());
					out.append("\n");
				}
				String outs = out.toString();
				bw.write(outs);
				bw.flush();
				success = true;
			} else {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						UI_main.shell.setEnabled(false);
						UI_warning.setWarning("No writing rights exists");
						UI_warning.main();
						return;
					}
				});
				;
			}
		} catch (IOException e) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					UI_main.shell.setEnabled(false);
					UI_warning.setWarning("Writing error. Check writing rights.");
					UI_warning.main();
					return;
				}
			});
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (Exception ex) {
				System.out.println("Error in closing the BufferedWriter" + ex);
			}
		}
	}
}
