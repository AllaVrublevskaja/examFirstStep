package model;

public class Profession {
    private String department;
    private String profession;

    public Profession(String department, String profession) {
        this.department = department;
        this.profession = profession;
    }

    public String getDepartment() {
        return department;
    }

    public String getProfession() {
        return profession;
    }
}
