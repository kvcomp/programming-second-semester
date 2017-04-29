package Lab5;

import Lab5.Baby;
import Lab5.Executor;
import com.opencsv.CSVReader;

import java.io.*;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.IOException;


/**
 * Created by Vladimir on 15/02/2017.
 */
public class Main {
    private static String path;
    private static long creationTime;
    private static LinkedList<Baby> myList;

    public static void main(String[] args) throws NullPointerException, ArrayIndexOutOfBoundsException {
        class Scn {
            String scan() {
                String request = null;
                try {
                    Scanner in = new Scanner(System.in);
                    StringBuilder sb = new StringBuilder();
                    sb.append(in.nextLine());
                    if (sb.codePointAt(sb.length() - 1) == "{".charAt(0))
                        do {
                            sb.append(in.nextLine());
                        } while (sb.codePointAt(sb.length() - 1) != "}".charAt(0));
                    request = sb.toString();
                    return request;
                } catch (NoSuchElementException ex) {
                    System.exit(0);
                }
                return request;
            }
        }
        try {
            path = args[0];
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            System.out.println("Не найден файл. Напишите название файла \"in.csv\" при запуске. Вот так вот: java18 -jar Lab5.jar in.csv");
            System.exit(1);
        }
        myList = reader(path);
        creationTime = System.currentTimeMillis();
        System.out.println("Список комманд:\n" + "load: перечитать коллекцию из файла\n" +
                "add_if_max {element}: добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "remove_greater {element}: удалить из коллекции все элементы, превышающие заданный\n" +
                "import {String path}: добавить в коллекцию все данные из файла\n" +
                "save: сохранить коллекцию в файл\n" +
                "remove {element}: удалить элемент из коллекции по его значению\n" +
                "remove_all {element}: удалить из коллекции все элементы, эквивалентные заданному\n" +
                "remove_last: удалить последний элемент из коллекции\n" +
                "add_if_min {element}: добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "clear: очистить коллекцию\n" +
                "add {element}: добавить новый элемент в коллекцию\n" +
                "remove_first: удалить первый элемент из коллекции\n" +
                "remove_lower {element}: удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "exit: выход из программы");

        for (; ; ) {
            String rqst = new Scn().scan();
            if (rqst.equals("exit")) {
                writer(path, myList);
                break;
            } else {
                new Executor().execute(rqst, myList);
            }
        }
        System.exit(0);
    }

    public static LinkedList<Baby> reader(String path) {
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
                Reader in = new InputStreamReader(inputStream, "UTF-8");
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
                        someList.add(new Baby(line[0], line[1], line[2]));
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

    public static void writer(String path, LinkedList<Baby> myList) {
        BufferedWriter bw = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Файл был создан");
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
            } else {
                System.out.println("Нет права на запись");
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи");
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

    public static String getPath() {
        return path;
    }

    public static long getCreationTime() {
        return creationTime;
    }

    public static void reloadList() {
        myList = reader(getPath());
    }
}
