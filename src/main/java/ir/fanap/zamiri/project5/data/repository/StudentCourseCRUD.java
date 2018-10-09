package ir.fanap.zamiri.project5.data.repository;

import ir.fanap.zamiri.project5.Exceptions;
import ir.fanap.zamiri.project5.data.HibernateUtils;
import ir.fanap.zamiri.project5.data.model.*;
import ir.fanap.zamiri.project5.data.modelVO.CourseVO;
import ir.fanap.zamiri.project5.data.modelVO.StudentVO;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentCourseCRUD {

    public static List<StudentVO> getCourseStudents(long cid){

        Course course = CourseCRUD.getCourseByIdnotVO(cid);
        List<Student> students = null;
        List<StudentCourse> studentCourses = null;

        Transaction transaction = null;

        try (Session session = HibernateUtils.SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();
            Query query= session.
                    createQuery("from StudentCourse where  course=:course");
            query.setParameter("course", course);
            studentCourses = query.getResultList();
            students = studentCourses.stream()
                    .map(StudentCourse::getStudent)
                    .collect(Collectors.toList());

            ;
            if (students == null)
                return null;
            transaction.commit();

        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }

        return StudentCRUD.studentsToStudentVos(students);

    }

    public static List<CourseVO> getStudentCourses(long sid){

        Student student = StudentCRUD.getStudentByIdnotVO(sid);
        List<Course> courses = null;
        List<StudentCourse> studentCourses = null;

        Transaction transaction = null;

        try (Session session = HibernateUtils.SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();
            Query query= session.
                    createQuery("from StudentCourse where  student=:student");
            query.setParameter("student", student);
            studentCourses = query.getResultList();
            courses = studentCourses.stream()
                    .map(StudentCourse::getCourse)
                    .collect(Collectors.toList());
            if (courses == null)
                return null;
            transaction.commit();

        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }

        return CourseCRUD.coursesToCoursesVos(courses);

    }

    public static float getStudentCourseScore(long sid,long cid){

        Student student = StudentCRUD.getStudentByIdnotVO(sid);
        Course  course  = CourseCRUD .getCourseByIdnotVO (cid);

        StudentCourse studentCourse = null;

        Transaction transaction = null;

        try (Session session = HibernateUtils.SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();
            Query query= session.
                    createQuery("from StudentCourse where  student=:student AND course=:course ");
            query.setParameter("student", student);
            query.setParameter("course", course);
            studentCourse = (StudentCourse) query.getSingleResult();

            transaction.commit();

        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return studentCourse != null ? studentCourse.getScore() : 0;

    }

    public static Float changeScore (long sid,long cid,float score){

        Student student = StudentCRUD.getStudentByIdnotVO(sid);
        Course  course  = CourseCRUD .getCourseByIdnotVO (cid);

        StudentCourse studentCourse = null;

        Transaction transaction = null;

        try (Session session = HibernateUtils.SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();
            Query query= session.
                    createQuery("from StudentCourse where  student=:student AND course=:course ");
            query.setParameter("student", student);
            query.setParameter("course", course);
            studentCourse = (StudentCourse) query.getSingleResult();
            studentCourse.setScore(score);

            session.save(studentCourse);
            transaction.commit();

        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return studentCourse != null ? studentCourse.getScore() : 0;



    }

    public static void addStudentCourse(long sid, long cid, long mid) throws Exceptions.NotMasterInList, Exceptions.NotFound {

        Course course   = CourseCRUD.getCourseByIdnotVO(cid)  ;
        Master master   = MasterCRUD.getMasterByIdnotVO(mid)  ;
        Student student = StudentCRUD.getStudentByIdnotVO(sid);

        if (master == null)  throw new Exceptions.NotFound("master not found");
        if (course == null)  throw new Exceptions.NotFound("course not found");
        if (student == null) throw new Exceptions.NotFound("student not found");


       // if (course.getMasterList().contains(master)) { : logic errr
        List<Long> mids = course.getMasterList().stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (mids.contains(mid)) {
            Session session = HibernateUtils.SESSION_FACTORY.openSession();
            course = (Course) session.get(Course.class,
                    cid);
//            Hibernate.initialize(student.getCourseList());
            StudentCourse sc = new StudentCourse();
                sc.setCourse(course);
                sc.setMasterId(mid);
                sc.setStudent(student);
//                student.getCourseList().add(sc);
                course.getStudentCourses().add(sc);

                session.beginTransaction();
                    session.save(sc);
                    session.update(student);
                    session.update(course);
                session.getTransaction().commit();
                session.close();
        } else {
            throw new Exceptions.NotMasterInList("this course is not eraee n by this master!");
        }

    }

}
