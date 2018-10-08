package ir.fanap.zamiri.project5.data.repository;

import ir.fanap.zamiri.project5.data.HibernateUtils;
import ir.fanap.zamiri.project5.data.model.Course;
import ir.fanap.zamiri.project5.data.model.Master;
import ir.fanap.zamiri.project5.data.model.Student;
import ir.fanap.zamiri.project5.data.modelVO.CourseVO;
import ir.fanap.zamiri.project5.data.modelVO.MasterVO;
import ir.fanap.zamiri.project5.data.modelVO.StudentVO;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MasterCRUD {

    public static List<MasterVO> getAll (){

        // Zamiri: i wrote this because Criterias are better! but not for this project!
        //we should use hql i think!

        Session session = HibernateUtils.SESSION_FACTORY.openSession();

        CriteriaQuery<Master> criteriaQuery = session.getCriteriaBuilder().createQuery(Master.class);
        criteriaQuery.from(Master.class);
        List<Master> masters = session.createQuery(criteriaQuery).getResultList();
        session.close();

        return mastersToMastersVos(masters) ;

    }

    public static MasterVO saveMaster(MasterVO masterVO){
        Session session = HibernateUtils.SESSION_FACTORY.openSession();

        session.beginTransaction();

        Master master = new Master();
        try {
            BeanUtils.copyProperties(master,masterVO);
        } catch (Exception e) {
            e.printStackTrace();
            return null;//todo
        }

        session.save(master);

        session.getTransaction().commit();

        session.close();

        try {
            BeanUtils.copyProperties(masterVO,master);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


        return masterVO;
    }

    public static List<CourseVO> getMasterCourses(long mid){
        Session session = HibernateUtils.SESSION_FACTORY.openSession();
        Master master =  (Master) session.get(Master.class, mid);
        if (master == null)
            return null;
        session.close();
        Hibernate.initialize(master.getCourseList());
        List<Course> courses = master.getCourseList();
        return CourseCRUD.coursesToCoursesVos(courses);
    }

    public static List<StudentVO> getMasterStudents(long mid){

//        Session session = HibernateUtils.SESSION_FACTORY.openSession();
//        Master master =  (Master) session.get(Master.class, mid);
//        if (master == null)
//            return null;
//        session.close();
//        Hibernate.initialize(master.getCourseList());
//        List<Course> courses = master.getCourseList();
//        if (courses == null)
//            return null;
//        List<Student> students = new ArrayList<>();
//        courses.forEach(course -> {
//            Hibernate.initialize();
//            students.addAll(CourseCRUD.ge)
//        });

        List<Student> students = null;
        Transaction transaction = null;
        try (Session session = HibernateUtils.SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();
            Query query= session.
                    createQuery("from StudentCourse where masterId=:mid");
            query.setParameter("mid", mid);
            students = query.get;

            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return studentsToStudentVos(students) ;


    }

    public static void addCourse(long mid, long cid /*CourseVO courseVO*/){

    }

    public static MasterVO getMasterById (long mid){

        return null;
    }

    public static List<Long> findMasterByName (String name){
        return null;
    }

    static List<MasterVO> mastersToMastersVos(List<Master> masters){
        List<MasterVO> masterVOS = new ArrayList<>();
        MasterVO masterVO = new MasterVO();
        if (masters == null)
            return null;
        masters.forEach(mstr -> {
            try {
                BeanUtils.copyProperties(masterVO, mstr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            masterVOS.add(masterVO);
        });

        return masterVOS;
    }

}
