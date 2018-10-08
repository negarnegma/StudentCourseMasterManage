package ir.fanap.zamiri.project5.data.repository;

import ir.fanap.zamiri.project5.data.HibernateUtils;
import ir.fanap.zamiri.project5.data.model.Course;
import ir.fanap.zamiri.project5.data.model.Master;
import ir.fanap.zamiri.project5.data.model.Student;
import ir.fanap.zamiri.project5.data.modelVO.CourseVO;
import ir.fanap.zamiri.project5.data.modelVO.MasterVO;
import ir.fanap.zamiri.project5.data.modelVO.StudentVO;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
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

    private static List<MasterVO> mastersToMastersVos(List<Master> master){
        List<MasterVO> masterVOS = new ArrayList<>();
        MasterVO masterVO = new MasterVO();
        master.forEach(mstr -> {
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
