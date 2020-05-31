
package ModelClasses;

import java.io.Serializable;

public class Education implements Serializable {
    private String title,year,grade;

    public Education(String title, String year, String grade) {
        this.title = title;
        this.year = year;
        this.grade = grade;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getGrade() {
        return grade;
    }
}
