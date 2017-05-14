
public class Baby implements Walker, Watcher {
	protected String Name;
	protected Boolean Free;
	protected String Sex;
	protected int Age;

	public Baby(String name, Boolean free, String sex, int age) {
		Name = name;
		Free = free;
		Sex = sex;
		Age = age;
	}

	public void setUnidentified() {
		Sex = "не определился";
	}

	public String getName() {
		return Name;
	}

	public String getFree() {
		if (this.Free == true) {
			return "true";
		} else {
			return "false";
		}
	}
	
	public String getAge() {
		String stringAge = String.valueOf(this.Age);
		return stringAge;
	}

	public String getSex() {
		return this.Sex;
	}	

	@Override
	public void watch() {
		System.out.println("Малышка " + this.toString() + " смотрит");

	}

	@Override
	public void walk() {
		System.out.println("Малышка " + this.toString() + " гуляет");
	}

	public String toCSV() {
        String s = this.getName() + "," + this.getFree() + "," + this.getSex()+ "," + this.getAge();
        return s;
    }
	
	public boolean equals(Baby b) {
		return this.Name.equals(b.Name) && this.Age == b.Age && this.Sex.equals(b.Sex) && this.Free == b.Free;	
	}
	
	public int compareTo(Baby b) {
		if (this.Name.length() > b.Name.length()) {
            return 1;
        } else if (this.Name.length() < b.Name.length()) {
            return -1;
        } else if (this.Age == b.Age) {
            if (this.Sex.length() == b.Sex.length()) {
                if (this.Free.equals(b.Free)) {
                	return 0;
                } else if (this.Free == true) {
                	return 1;
                } else return -1;
            } else if (this.Sex == "male") {
                return 1;
            } else return -1;
        } else if (this.Age > b.Age) {
            return 1;
        } else return -1;		
	}

}
