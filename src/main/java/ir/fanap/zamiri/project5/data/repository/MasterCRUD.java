package ir.fanap.zamiri.project5.data.repository;

import ir.fanap.zamiri.project5.data.modelVO.CourseVO;
import ir.fanap.zamiri.project5.data.modelVO.MasterVO;
import ir.fanap.zamiri.project5.data.modelVO.StudentVO;

import java.util.List;

public class MasterCRUD {
    public static List<MasterVO> getAll (){

        return null;
    }
    public static MasterVO saveMaster(MasterVO masterVOVO){
        return masterVOVO;
    }
    public static List<StudentVO> getMasterStudents(long mid){

        return null;
    }

    public static List<CourseVO> getMasterCourses(long mid){

        return null;
    }
    public static void addCourse(long mid, long cid /*CourseVO courseVO*/){

    }
    public static MasterVO getMasterById (long mid){

        return null;
    }
    public static List<Long> findMasterByName (String name){
        return null;
    }
}
