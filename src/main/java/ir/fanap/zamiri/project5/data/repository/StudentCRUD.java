package ir.fanap.zamiri.project5.data.repository;

import ir.fanap.zamiri.project5.data.HibernateUtils;
import ir.fanap.zamiri.project5.data.model.Student;
import ir.fanap.zamiri.project5.data.modelVO.StudentVO;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaQuery;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class StudentCRUD {

    public static List<StudentVO> getAll() {

        // Zamiri: i wrote this because Criterias are better! but not for this project!
            //we should use hql i think!

        Session session = HibernateUtils.SESSION_FACTORY.openSession();

        CriteriaQuery<Student> criteriaQuery = session.getCriteriaBuilder().createQuery(Student.class);
        criteriaQuery.from(Student.class);
        List<Student> students = session.createQuery(criteriaQuery).getResultList();
        session.close();

        return studentsToStudentVos(students) ;
    }

    public static StudentVO saveStudent(StudentVO studentVO) {

        Session session = HibernateUtils.SESSION_FACTORY.openSession();

        session.beginTransaction();

        Student student = new Student();
        try {
            BeanUtils.copyProperties(student,studentVO);
        } catch (Exception e) {
            e.printStackTrace();
            return null;//todo
        }

        session.save(student);

        session.getTransaction().commit();

        session.close();

        try {
            BeanUtils.copyProperties(studentVO,student);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


        return studentVO;
    }

    public static File getStudentImage(long sid) {

        return null;//todo
    }

    public static StudentVO getStudentById(long sid) {

    //ye rahe dige baraye fetch be joz hql!!

        Student stu = null;
        Transaction transaction = null;
        try (Session session = HibernateUtils.SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();
            stu = (Student) session.get(Student.class,
                    sid);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        StudentVO studentVO = new StudentVO();
        try {
            BeanUtils.copyProperties(studentVO,stu);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return studentVO;
    }


    public static List<StudentVO> findStudentByCode(String code) {

        List<Student> students = null;
        Transaction transaction = null;
        try (Session session = HibernateUtils.SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();
            Query query= session.
                    createQuery("from Student where code=:code");
            query.setParameter("code", code);
            students = query.getResultList();

            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return studentsToStudentVos(students) ;
    }
     private static List<StudentVO> studentsToStudentVos(List<Student> students){
        List<StudentVO> studentVOS = new ArrayList<>();
        StudentVO studentVO = new StudentVO();
        if(students == null)
            return null;
        students.forEach(std -> {
            try {
                BeanUtils.copyProperties(studentVO, std);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            studentVOS.add(studentVO);
        });


        return studentVOS;
    }
}
