package ir.fanap.zamiri.project5.controller;


import ir.fanap.zamiri.project5.Exceptions;
import ir.fanap.zamiri.project5.data.model.StudentCourse;
import ir.fanap.zamiri.project5.data.modelVO.CourseVO;
import ir.fanap.zamiri.project5.data.modelVO.StudentVO;
import ir.fanap.zamiri.project5.data.repository.StudentCRUD;
import ir.fanap.zamiri.project5.data.repository.StudentCourseCRUD;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by NZamiri
 */

@Path("/student")
public class StudentApi {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(StudentVO studentVO) {

        if (studentVO == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        StudentVO student= StudentCRUD.saveStudent(studentVO);

        return Response.status(201).entity(student).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents() {
        return Response.status(200).entity(StudentCRUD.getAll()).build();
    }

    @GET
    @Path("/{sid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent(@PathParam("sid") long sid) {
        return Response.status(200).entity(StudentCRUD.getStudentById(sid)).build();
    }

    @GET
    @Path("/{sid}/course")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentCourses(@PathParam("sid") long sid) {
        return Response.status(200).entity(StudentCourseCRUD.getStudentCourses(sid)).build();
    }

    @GET
    @Path("/{sid}/image")
    @Produces("image/jpeg")
    public Response getStudentImage(@PathParam("sid") long sid) {
        return Response.status(200).entity(StudentCRUD.getStudentImage(sid)).build();
    }



    @GET
    @Path("/{sid}/course/{cid}/score")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getStudentCourseScore(@PathParam("sid") long sid,@PathParam("cid") long cid) {
        return Response.status(200).entity(StudentCourseCRUD.getStudentCourseScore(sid,cid)).build();
    }

    @POST()
    @Path("/{sid}/addCourse")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCourseToStudent(@PathParam("sid")long sid, ArrayList<Long> cidmid) {

        if (cidmid == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            StudentCourseCRUD.addStudentCourse(sid,cidmid.get(0),cidmid.get(1));
        } catch (Exceptions.notMasterInList notMasterInList) {
            return Response.status(404).entity(notMasterInList.getMessage()).build();
        }

        return Response.status(201).entity("yep!").build();
    }



}
