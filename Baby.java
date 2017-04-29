package Lab5;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Vladimir on 15/02/2017.
 */
    protected String Name;
    protected String Free;
    protected String Sex;

    public Baby(String name, String free, String sex) {
        Name = name;
        Free = free;
        Sex = sex;
    }

    public void setUnidentified() {
        Sex = "не определился";
    }

    public String getName() {
        return Name;
    }

    public String getFree() {
        if (this.Free.equals("free")) {
            return "free";
        } else {
            return "notfree";
        }
    }

    public String getSex() {
        return this.Sex;
    }

    public void walk() {
        System.out.println("Малышка " + this.toString() + " гуляет");
    }

    public void watch() {
        System.out.println("Малышка " + this.toString() + " смотрит");
    }

    public String toCSV() {
        String s = this.getName() + "," + this.getFree() + "," + this.getSex();
        return s;
    }

    @Override
    public String toString() {
        return Name;
    }

    @Override
    public int compareTo(@NotNull Baby b) {

        if (this.Name.length() > b.Name.length()) {
            return 1;
        } else if (this.Name.length() < b.Name.length()) {
            return -1;
        } else if (this.Free.length() == b.Free.length()) {
            if (this.Sex.length() == b.Sex.length()) {
                return 0;
            } else if (this.Sex == "male") {
                return 1;
            } else return -1;
        } else if (this.Free == "free") {
            return 1;
        } else return -1;

    }

}

