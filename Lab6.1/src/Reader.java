import java.util.LinkedList;
import com.opencsv.CSVReader;
import java.io.*;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.eclipse.swt.widgets.Display;

public class Reader extends Thread {
	private String Path;
	private LinkedList<Baby> inList;
	private volatile boolean success;

	public Reader(String path) {
		Path = path;
		success = false;
	}

	public void setPath(String path) {
		this.Path = path;
	}

	public String getPath() {
		return this.Path;
	}

	public boolean getSuccess() {
		return this.success;
	}

	@Override
	public void run() {
		inList = read(Path);
		UI_main.myList.addAll(inList);
	}

	private LinkedList<Baby> read(String path) {
		LinkedList<Baby> someList = new LinkedList<Baby>();
		String inp = null;
		try {
			File inputfile = new File(path);
			if (!inputfile.exists()) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						UI_main.shell.setEnabled(false);
						UI_warning.setWarning("File not exists");
						UI_warning.main();
						return;
					}
				});
			} else {
				if (inputfile.canRead()) {
					InputStream inputStream = new FileInputStream(path);
					final int bufferSize = 1024;
					final char[] buffer = new char[bufferSize];
					final StringBuilder out = new StringBuilder();
					InputStreamReader in = new InputStreamReader(inputStream, "UTF-8");
					int rsz;
					do {
						rsz = in.read(buffer, 0, buffer.length);
						if (rsz < 0)
							break;
						out.append(buffer, 0, rsz);
					} while (!(rsz < 0));
					inp = out.toString();
					CSVReader csvreader = new CSVReader(new StringReader(inp));
					String[] line;
					while ((line = csvreader.readNext()) != null) {
						if (!(line[0].equals(""))) {
							someList.add(
									new Baby(line[0], Boolean.valueOf(line[1]), line[2], Integer.parseInt(line[3])));
						}
					}
					in.close();
					success = true;
				} else {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							UI_main.shell.setEnabled(false);
							UI_warning.setWarning("No reading rights exists");
							UI_warning.main();
							return;
						}
					});
				}
			}
		} catch (IOException e) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					UI_main.shell.setEnabled(false);
					UI_warning.setWarning("Input errror");
					UI_warning.main();
					return;
				}
			});
		}
		return someList;
	}

}
