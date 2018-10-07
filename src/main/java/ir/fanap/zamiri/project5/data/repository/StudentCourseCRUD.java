package ir.fanap.zamiri.project5.data.repository;

import ir.fanap.zamiri.project5.data.model.Student;
import ir.fanap.zamiri.project5.data.modelVO.CourseVO;
import ir.fanap.zamiri.project5.data.modelVO.StudentVO;

import java.util.List;

public class StudentCourseCRUD {

    public static List<StudentVO> getCorseStudents(long cid){

        return null;
    }

    public static List<CourseVO> getStudentCourses(long sid){

        return null;
    }

    public static float getStudentCourseScore(long sid,long cid){

        return 0;
    }

    public static CourseVO getStudentCourse(long sid,long cid){

        return null;
    }

    public static Float changeScore (long sid,long cid,float score){
      return score;
    }

    public static void addStudentCourse(long sid, CourseVO courseVO){

    }

}
