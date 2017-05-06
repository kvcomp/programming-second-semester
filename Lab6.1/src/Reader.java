import java.util.LinkedList;
import com.opencsv.CSVReader;
import java.io.*;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Reader implements Runnable {
	private String Path;
	
	public void setPath(String path){
		this.Path = path;
	}
	
	@Override
	public void run() {
		read(Path);
	}
	
	public LinkedList<Baby> read (String path) {
		LinkedList someList = new LinkedList<Baby>();
        String inp = null;
        try {
            File inputfile = new File(path);
            if (!inputfile.exists()) {
                System.out.println("Файл не существует");
                System.exit(0);
            }
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
                        someList.add(new Baby(line[0], Boolean.valueOf(line[1]), line[2], Integer.parseInt(line[3]) ) );
                    }
                }
            } else {
                System.out.println("Отсутствуют права на чтение");
                System.exit(0);
            }
        } catch (IOException e) {
            System.out.println("Ошибка ввода. Проверьте существует ли ваш файл");
            System.exit(1);
        }
        return someList;
	}

}
