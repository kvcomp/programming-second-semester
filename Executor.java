package Lab5;

import java.util.*;

import Lab5.Baby;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Класс служит для обработки и выполнения комманд
 *
 * @version 1.0.7
 * @autor Vladimir Smirnov
 */

public class Executor {
    /**
     * Набор возможных комманд
     */
    private final static String LOAD = "load";
    private final static String ADDIFMAX = "add_if_max";
    private final static String INFO = "info";
    private final static String REMOVEGREATER = "remove_greater";
    private final static String IMPORT = "import";
    private final static String SAVE = "save";
    private final static String REMOVE = "remove";
    private final static String REMOVEALL = "remove_all";
    private final static String REMOVELAST = "remove_last";
    private final static String ADDIFMIN = "add_if_min";
    private final static String CLEAR = "clear";
    private final static String ADD = "add";
    private final static String REMOVEFIRST = "remove_first";
    private final static String REMOVELOWER = "remove_lower";
    private static Gson gson = new Gson();

    /**
     * Определение и выполнение комманды
     */
    void execute(String rqst, LinkedList<Baby> myList) {
        if (rqst.equals("")) {
            System.out.println("Пожалуйста, введите команду");
        } else {
            String[] rqstArr = rqst.split(" ", 2);
            String arg = null;
            if (rqstArr.length > 1) {
                if (rqstArr[1].trim().startsWith("{") && rqstArr[1].trim().endsWith("}")) {
                    arg = rqstArr[1];
                } else {
                    System.out.println("Укажите аргумент");
                }
            }
            switch (rqstArr[0]) {
                case LOAD:
                    load();

                    break;
                case ADDIFMAX:
                    addIfMax(arg, myList);

                    break;
                case INFO:
                    info(myList);

                    break;
                case REMOVEGREATER:
                    removeGreater(arg, myList);

                    break;
                case IMPORT:
                    imp(arg, myList);

                    break;
                case SAVE:
                    String path = Main.getPath();
                    save(myList, path);

                    break;
                case REMOVE:
                    remove(arg, myList);

                    break;
                case REMOVEALL:
                    removeAll(arg, myList);

                    break;
                case REMOVELAST:
                    removeLast(myList);

                    break;
                case ADDIFMIN:
                    addIfMin(arg, myList);

                    break;
                case CLEAR:
                    clear(myList);

                    break;
                case ADD:
                    add(arg, myList);

                    break;
                case REMOVEFIRST:
                    removeFirst(myList);

                    break;
                case REMOVELOWER:
                    removeLower(arg, myList);

                    break;
                default:
                    System.out.println("Комманда с таким названием не была найдена. Приносим свои извенения");
                    autocomplete(rqstArr[0]);
                    break;
            }
            Main.writer("backup.csv", myList);
        }
    }

    /**
     * перечитать коллекцию из файла
     */
    private void load() {
        Main.reloadList();
        System.out.println("Коллекция перечитана");
    }

    /**
     * добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
     */
    private void addIfMax(String arg, LinkedList<Baby> myList) {
        try {
            if (arg != null) {
                Baby baby = gson.fromJson(arg, Baby.class);
                if (baby.Name != null && baby.Free != null && baby.Sex != null) {
                    myList.sort(new Comparator<Baby>() {
                        public int compare(Baby b1, Baby b2) {
                            return b1.compareTo(b2);
                        }
                    });
                    if (baby.compareTo(myList.getLast()) > 0) {
                        myList.addLast(baby);
                        System.out.println("Элемент был добавлен в коллекцию");
                    } else {
                        System.out.println("Элемент не был добавлен в коллекцию");
                    }
                } else {
                    System.out.println("Неправильный формат ввода параметров");
                }
            } else {
                System.out.println("Не был указан аргумент");
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Неправильный формат ввода параметров");
        }
    }

    /**
     * вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
     */
    private void info(LinkedList myList) {
        System.out.println("myList:\n" +
                "тип: " + myList.getClass().getName() + "\n" +
                "кол-во элементов: " + myList.size() + "\n" +
                "создана: " + (System.currentTimeMillis() - Main.getCreationTime()) + " мс назад");
        ListIterator<Baby> it = myList.listIterator();
        for (int i = 0; i < myList.size(); i++) {
            Baby b = (Baby) myList.get(i);
            int n = myList.indexOf(b);
            System.out.println(i + ". " + b.getName() + " " + b.getFree() + " " + b.getSex());
        }
    }

    /**
     * удалить из коллекции все элементы, превышающие заданный
     */
    private void removeGreater(String arg, LinkedList<Baby> myList) {
        try {
            if (arg != null) {
                Baby baby = gson.fromJson(arg, Baby.class);
                int i = 0;
                ListIterator<Baby> it = myList.listIterator();
                while (it.hasNext()) {
                    if (baby.compareTo(it.next()) < 0) {
                        it.remove();
                        i++;
                    }
                }
                System.out.println("Было удалено " + i + " элементов");
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Неправильный формат ввода параметров");
        }
    }

    /**
     * удалить из коллекции все элементы, меньшие, чем заданный
     */
    private void removeLower(String arg, LinkedList<Baby> myList) {
        try {
            if (arg != null) {
                Baby baby = gson.fromJson(arg, Baby.class);
                int i = 0;
                ListIterator<Baby> it = myList.listIterator();
                while (it.hasNext()) {
                    if (baby.compareTo(it.next()) > 0) {
                        it.remove();
                        i++;
                    }
                }
                System.out.println("Было удалено " + i + " элементов");
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Неправильный формат ввода параметров");
        }
    }

    /**
     * добавить в коллекцию все данные из файла
     */
    private void imp(String arg, LinkedList<Baby> myList) {
        String newArg = arg.substring(1, arg.length() - 1);
        myList.addAll(Main.reader(newArg));
        System.out.println("Данные добавлены");
    }

    /**
     * сохранить коллекцию в файл
     */
    private void save(LinkedList<Baby> myList, String path) {
        Main.writer(path, myList);
        System.out.println("Запись осуществлена");
    }

    /**
     * удалить элемент из коллекции по его значению
     */
    private void remove(String arg, LinkedList<Baby> myList) {
        try {
            if (arg != null) {
                Baby baby = gson.fromJson(arg, Baby.class);
                ListIterator<Baby> it = myList.listIterator();
                boolean checkCritearia = false;
                while (it.hasNext()) {
                    if (baby.compareTo(it.next()) == 0) {
                        it.remove();
                        System.out.println("Элемент был удалён");
                        checkCritearia = true;
                        break;
                    }
                }
                if (!checkCritearia)
                    System.out.println("Элемент не найден");
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Неправильный формат ввода параметров");
        }
    }

    /**
     * удалить из коллекции все элементы, эквивалентные заданному
     */
    private void removeAll(String arg, LinkedList<Baby> myList) {
        try {
            if (arg != null) {
                Baby baby = gson.fromJson(arg, Baby.class);
                ListIterator<Baby> it = myList.listIterator();
                boolean checkCritearia = false;
                int i = 0;
                while (it.hasNext()) {
                    if (baby.compareTo(it.next()) == 0) {
                        it.remove();
                        i++;
                        checkCritearia = true;
                    }
                }
                System.out.println("Было удалено " + i + " элементов");
                if (!checkCritearia)
                    System.out.println("Элемент не найден");
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Неправильный формат ввода параметров");
        }
    }

    /**
     * удалить последний элемент из коллекции
     */
    private void removeLast(LinkedList<Baby> myList) {
        myList.removeLast();
    }

    /**
     * удалить первый элемент из коллекции
     */
    private void removeFirst(LinkedList<Baby> myList) {
        myList.removeFirst();
    }

    /**
     * добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
     */
    private void addIfMin(String arg, LinkedList<Baby> myList) {
        try {
            if (arg != null) {
                Baby baby = gson.fromJson(arg, Baby.class);
                if (baby.Name != null && baby.Free != null && baby.Sex != null) {
                    myList.sort(new Comparator<Baby>() {
                        public int compare(Baby b1, Baby b2) {
                            return b1.compareTo(b2);
                        }
                    });
                    if (baby.compareTo(myList.getFirst()) < 0) {
                        myList.addFirst(baby);
                        System.out.println("Элемент был добавлен в коллекцию");
                    } else {
                        System.out.println("Элемент не был добавлен в коллекцию");
                    }
                } else {
                    System.out.println("Неправильный формат ввода параметров");
                }
            } else {
                System.out.println("Не был указан аргумент");
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Неправильный формат ввода параметров");
        }
    }

    /**
     * очистить коллекцию
     */
    private void clear(LinkedList<Baby> myList) {
        myList.clear();
        System.out.println("Коллекция очищена");
    }

    /**
     * добавить новый элемент в коллекцию
     */
    private void add(String arg, LinkedList<Baby> myList) {
        try {
            if (arg != null) {
                Baby baby = gson.fromJson(arg, Baby.class);
                if (baby.Name != null && baby.Free != null) {
                    if (baby.Sex == null) {
                        baby.setUnidentified();
                    }
                    myList.add(baby);
                    System.out.println("Элемент был добавлен в коллекцию");
                } else {
                    System.out.println("Неправильный формат ввода параметров. Поля Name и Free являются обязательными");
                }
            } else {
                System.out.println("Не был указан аргумент");
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Неправильный формат ввода параметров");
        }
    }

    /**
     * Автодополнение команды
     */
    private void autocomplete(String rqst) {
        HashSet<String> matches = new HashSet<String>();
        String[] cases = new String[14];
        cases[0] = LOAD;
        cases[1] = ADD;
        cases[2] = ADDIFMAX;
        cases[3] = ADDIFMIN;
        cases[4] = INFO;
        cases[5] = REMOVE;
        cases[6] = REMOVEALL;
        cases[7] = REMOVEFIRST;
        cases[8] = REMOVELAST;
        cases[9] = REMOVEGREATER;
        cases[10] = REMOVELOWER;
        cases[11] = IMPORT;
        cases[12] = CLEAR;
        cases[13] = SAVE;
        boolean found = false;
        for (String s : cases) {
            if (s.startsWith(rqst)) {
                found = true;
                matches.add(s);
            }
        }
        if (found) {
            Iterator<String> it2 = matches.iterator();
            System.out.println("Возможно вы имели в виду:");
            while (it2.hasNext()) {
                System.out.println(it2.next());
            }
        }
    }
}



