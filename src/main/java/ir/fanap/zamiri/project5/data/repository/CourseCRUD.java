package ir.fanap.zamiri.project5.data.repository;

import ir.fanap.zamiri.project5.data.HibernateUtils;
import ir.fanap.zamiri.project5.data.model.Course;
import ir.fanap.zamiri.project5.data.model.Master;
import ir.fanap.zamiri.project5.data.model.Student;
import ir.fanap.zamiri.project5.data.model.StudentCourse;
import ir.fanap.zamiri.project5.data.modelVO.CourseVO;
import ir.fanap.zamiri.project5.data.modelVO.MasterVO;
import ir.fanap.zamiri.project5.data.modelVO.StudentVO;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

        Session session = HibernateUtils.SESSION_FACTORY.openSession();

        session.beginTransaction();

        Course course = new Course();
        try {
            BeanUtils.copyProperties(course,courseVO);
        } catch (Exception e) {
            e.printStackTrace();
            return null;//todo
        }

        session.save(course);

        session.getTransaction().commit();

        session.close();

        try {
            BeanUtils.copyProperties(courseVO,course);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


        return courseVO;
    }

    public static List<MasterVO> getCourseMasters(long cid){

        Course crs = getCourseByIdnotVO(cid);

        return MasterCRUD.mastersToMastersVos(crs.getMasterList());
    }

    public static CourseVO getCourseById (long cid){

        return courseToCourseVO (getCourseByIdnotVO(cid));
    }

    public static HashMap<String, Float> getCourseMasterScores(long cid,long mid){

        HashMap<String, Float> StudentScores = new HashMap<>();
        List<StudentCourse> studentCourses = null;
        Course course = getCourseByIdnotVO(cid);

        Transaction transaction = null;
        try (Session session = HibernateUtils.SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();
            Query query= session.
                    createQuery("from StudentCourse where masterId=:mid AND course=:course");
            query.setParameter("mid", mid);
            query.setParameter("course", course);
            studentCourses = query.getResultList();
            if (studentCourses == null)
                return null;

            studentCourses.forEach(sc->{
                StudentScores.put(sc.getStudent().getCode(), sc.getScore());
            });

            transaction.commit();

        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }


        return StudentScores ;
    }

    public static List<CourseVO> findCourseByName (String name){

        List<Course> courses = null;
        Transaction transaction = null;

        try (Session session = HibernateUtils.SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();
            Query query= session.
                    createQuery("from Course where name=:name");
            query.setParameter("name", name);
            courses = query.getResultList();

            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }

        return coursesToCoursesVos(courses);    }

    static List<CourseVO> coursesToCoursesVos(List<Course> courses){
        List<CourseVO> courseVOS = new ArrayList<>();
        CourseVO courseVO = new CourseVO();
        if (courses == null)
            return null;
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

    public static Course getCourseByIdnotVO (long cid){

        Course course = null;
        Transaction transaction = null;
        try (Session session = HibernateUtils.SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();
            course = (Course) session.get(Course.class,
                    cid);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return course;
    }

    static CourseVO courseToCourseVO(Course course){
        CourseVO courseVO = new CourseVO();
        if (course == null)
            return null;
            try {
                BeanUtils.copyProperties(courseVO, course);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return courseVO;
    }

}
