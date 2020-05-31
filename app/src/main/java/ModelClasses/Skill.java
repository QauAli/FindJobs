package ModelClasses;

public class Skill {
    int id;
    String name;

    public Skill(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Skill() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
