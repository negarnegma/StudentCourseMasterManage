package ir.fanap.zamiri.project5.data.repository;

import ir.fanap.zamiri.project5.data.HibernateUtils;
import ir.fanap.zamiri.project5.data.model.Student;
import ir.fanap.zamiri.project5.data.modelVO.StudentVO;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class StudentCRUD {

    public static List<StudentVO> getAll() {

        // Zamiri: i wrote this because Criterias are better! but not for this project!

        // Open a session
        Session session = HibernateUtils.SESSION_FACTORY.openSession();

        // Create Criteria
        CriteriaQuery<Student> criteriaQuery = session.getCriteriaBuilder().createQuery(Student.class);
        criteriaQuery.from(Student.class);

        // Get a list of Contact objects according to the Criteria object
        List<Student> students = session.createQuery(criteriaQuery).getResultList();
        // Close the session
        session.close();

        List<StudentVO> studentVOS = new ArrayList<>();
        StudentVO studentVO = new StudentVO();
        students.forEach(std -> {
            try {
                BeanUtils.copyProperties(studentVO, std);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            studentVOS.add(studentVO);
        });


        return studentVOS;
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
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return studentVO;
    }

    public static File getStudentImage(long sid) {

        return null;
    }

    public static StudentVO getStudentById(long sid) {

        return null;
    }

    //    public static void addCourse(long sid, long cid /*CourseVO courseVO*/){
//
//    }
    public static List<Long> findStudentByCode(String code) {
        return null;
    }
}
