package ir.fanap.zamiri.project5.controller;

import ir.fanap.zamiri.project5.data.modelVO.CourseVO;
import ir.fanap.zamiri.project5.data.repository.CourseCRUD;
import ir.fanap.zamiri.project5.data.repository.StudentCourseCRUD;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by NZamiri
 */

@Path("/course")
public class CourseApi {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCourse(CourseVO courseVO) {

        if (courseVO == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        CourseVO course= CourseCRUD.saveCourse(courseVO);

        return Response.status(201).entity(course).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses() {
        return Response.status(200).entity(CourseCRUD.getAll()).build();
    }

    @GET
    @Path("/{cid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourse(@PathParam("cid") long cid) {
        return Response.status(200).entity(CourseCRUD.getCourseById(cid)).build();
    }

    @GET
    @Path("/{cid}/student")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseStudents(@PathParam("cid") long cid) {
        return Response.status(200).entity(StudentCourseCRUD.getCourseStudents(cid)).build();
    }

    @GET
    @Path("/{cid}/master")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseMasters(@PathParam("cid") long cid) {
        return Response.status(200).entity(CourseCRUD.getCourseMasters(cid)).build();
    }

    @GET
    @Path("/{cid}/master/{mid}/score")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseMasterScores(@PathParam("cid") long cid,@PathParam("mid") long mid) {
        return Response.status(200).entity(CourseCRUD.getCourseMasterScores(cid,mid)).build();
    }

    @POST
    @Path("/{cid}/student/{sid}/score")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response getStudentCourseScore(@PathParam("sid") long sid,@PathParam("cid") long cid, String scoreString) {
        Float score = Float.parseFloat(scoreString);
        return Response.status(200).entity(StudentCourseCRUD.changeScore(sid,cid,score)).build();
    }

}
