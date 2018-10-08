package ir.fanap.zamiri.project5.data.repository;

import ir.fanap.zamiri.project5.data.HibernateUtils;
import ir.fanap.zamiri.project5.data.model.Course;
import ir.fanap.zamiri.project5.data.model.Student;
import ir.fanap.zamiri.project5.data.modelVO.CourseVO;
import ir.fanap.zamiri.project5.data.modelVO.MasterVO;
import ir.fanap.zamiri.project5.data.modelVO.StudentVO;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CourseCRUD {
    public static List<CourseVO> getAll (){

        // Zamiri: i wrote this because Criterias are better! but not for this project!
        //we should use hql i think!

        Session session = HibernateUtils.SESSION_FACTORY.openSession();

        CriteriaQuery<Course> criteriaQuery = session.getCriteriaBuilder().createQuery(Course.class);
        criteriaQuery.from(Course.class);
        List<Course> courses = session.createQuery(criteriaQuery).getResultList();
        session.close();

        return coursesToCoursesVos(courses) ;
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
    private static List<CourseVO> coursesToCoursesVos(List<Course> courses){
        List<CourseVO> courseVOS = new ArrayList<>();
        CourseVO courseVO = new CourseVO();
        courses.forEach(crs -> {
            try {
                BeanUtils.copyProperties(courseVO, crs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            courseVOS.add(courseVO);
        });


        return courseVOS;
    }

}
