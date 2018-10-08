package ir.fanap.zamiri.project5.data.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Student extends BaseEntity{

    private String firstName;
    private String lastName;
    private String code;
    @OneToMany
    private List<StudentCourse> courseList;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<StudentCourse> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<StudentCourse> courseList) {
        this.courseList = courseList;
    }


}
