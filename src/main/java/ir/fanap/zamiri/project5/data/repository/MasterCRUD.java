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
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.omg.CORBA.MARSHAL;

import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ir.fanap.zamiri.project5.data.repository.CourseCRUD.getCourseByIdnotVO;

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

    public static List<Student> getMasterStudents(long mid){
/*
        Session session = HibernateUtils.SESSION_FACTORY.openSession();
        Master master =  (Master) session.get(Master.class, mid);
        if (master == null)
            return null;
        session.close();
        Hibernate.initialize(master.getCourseList());
        List<Course> courses = master.getCourseList();
        if (courses == null)
            return null;
        List<Student> students = new ArrayList<>();
        courses.forEach(course -> {
            Hibernate.initialize();
            students.addAll(CourseCRUD.ge)
        });
*/

        List<Student> students = new ArrayList<>();
        List<StudentCourse> studentCourses = null;
        Transaction transaction = null;
        try (Session session = HibernateUtils.SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();
            Query query= session.
                    createQuery("from StudentCourse where masterId=:mid");
            query.setParameter("mid", mid);
            studentCourses = query.getResultList();
            if (studentCourses == null)
                return null;

            studentCourses.forEach(sc->{
                students.add(sc.getStudent());
            });

            transaction.commit();

        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }

        //Using java 8 to remove duplicates:
        List<Student> students2 = students.stream()
                .distinct()
                .collect(Collectors.toList());

        return students2 ;


    }

    public static void addCourse(long mid, long cid /*CourseVO courseVO*/){

        Master master = getMasterByIdnotVO(mid);
        Course course = getCourseByIdnotVO(cid);

        if (master == null) return;
        if (course == null) return;

        master.getCourseList().add(course);
        course.getMasterList().add(master);

        Session session = HibernateUtils.SESSION_FACTORY.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(master);
        session.save(course);

        transaction.commit();
        session.close();


    }

    public static MasterVO getMasterById (long mid){

        Master master = getMasterByIdnotVO(mid);
        return masterToMastersVO(master);
    }

    public static List<MasterVO> findMasterByName (String name){

        List<Master> masters = null;
        Transaction transaction = null;

        try (Session session = HibernateUtils.SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();
            Query query= session.
                    createQuery("from Master where name=:name");
            query.setParameter("name", name);
            masters = query.getResultList();

            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }

        return mastersToMastersVos(masters);

    }

    public static Master getMasterByIdnotVO (long mid){

        Master mstr = null;
        Transaction transaction = null;
        try (Session session = HibernateUtils.SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();
            mstr = (Master) session.get(Master.class,
                    mid);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return mstr;
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

    static MasterVO masterToMastersVO(Master master){
        MasterVO masterVO = new MasterVO();
        if (master == null)
            return null;
            try {
                BeanUtils.copyProperties(masterVO, master);
            } catch (Exception e) {
                e.printStackTrace();
            }

        return masterVO;
    }

}
