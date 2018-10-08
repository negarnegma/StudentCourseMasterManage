package ir.fanap.zamiri.project5.data.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class StudentCourse extends BaseEntity{

  @ManyToOne
  private Student student;

  @ManyToOne
  private Course course;

  private float score;

//  @ManyToOne
//  private Master master;

  public long getMasterId() {
    return masterId;
  }

  public void setMasterId(long masterId) {
    this.masterId = masterId;
  }

  private long masterId;

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public float getScore() {
    return score;
  }

  public void setScore(float score) {
    this.score = score;
  }
}
