package ir.fanap.zamiri.project5.data.repository;

import ir.fanap.zamiri.project5.data.modelVO.CourseVO;
import ir.fanap.zamiri.project5.data.modelVO.StudentVO;

import java.io.File;
import java.util.List;

public class StudentCRUD {

    public static List<StudentVO> getAll (){

        return null;
    }
    public static StudentVO saveStudent(StudentVO studentVO){
        return studentVO;
    }

    public static File getStudentImage (long sid){

        return null;
    }

    public static StudentVO getStudentById (long sid){

        return null;
    }

    public static void addCourse(long sid, long cid /*CourseVO courseVO*/){

    }
    public static List<Long> findStudentByCode (String code){
        return null;
    }
}
