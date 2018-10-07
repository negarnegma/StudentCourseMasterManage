package ir.fanap.zamiri.project5.data.repository;

import ir.fanap.zamiri.project5.data.modelVO.CourseVO;
import ir.fanap.zamiri.project5.data.modelVO.MasterVO;

import java.util.List;

public class CourseCRUD {
    public static List<CourseVO> getAll (){

        return null;
    }
    public static CourseVO saveCourse(CourseVO courseVO){
        return courseVO;
    }

    public static List<MasterVO> getCourseMasters(long cid){

        return null;
    }

    public static CourseVO getCourseById (long cid){

        return null;
    }
    public static List<Float> getCourseMasterScores(long cid,long mid){

        return null;
    }
    public static List<Long> findCourseByName (String name){
        return null;
    }

}
