package ir.fanap.zamiri.project5.data.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


@Entity
public class Course extends BaseEntity{

  private String name;

  private int unit;

  @OneToMany
  private List<StudentCourse> studentCourses = new ArrayList<StudentCourse>();

  @ManyToMany
  private List<Master> masterList = new ArrayList<Master>(  );

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getUnit() {
    return unit;
  }

  public void setUnit(int unit) {
    this.unit = unit;
  }

  public List<StudentCourse> getStudentCourses() {
    return studentCourses;
  }

  public void setStudentCourses(List<StudentCourse> studentCourses) {
    this.studentCourses = studentCourses;
  }

  public List<Master> getMasterList() {
    return masterList;
  }

  public void setMasterList(List<Master> masterList) {
    this.masterList = masterList;
  }

  @OneToMany(mappedBy = "course")
  private Collection<StudentCourse> studentCourse;

  public Collection<StudentCourse> getStudentCourse() {
    return studentCourse;
  }

  public void setStudentCourse(Collection<StudentCourse> studentCourse) {
    this.studentCourse = studentCourse;
  }
}
