package ir.fanap.zamiri.project5.controller;

import ir.fanap.zamiri.project5.Exceptions;
import ir.fanap.zamiri.project5.data.model.Master;
import ir.fanap.zamiri.project5.data.modelVO.CourseVO;
import ir.fanap.zamiri.project5.data.modelVO.MasterVO;
import ir.fanap.zamiri.project5.data.repository.CourseCRUD;
import ir.fanap.zamiri.project5.data.repository.MasterCRUD;
import ir.fanap.zamiri.project5.data.repository.StudentCourseCRUD;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static ir.fanap.zamiri.project5.data.repository.StudentCRUD.studentsToStudentVos;

/**
 * Created by NZamiri
 */

@Path("/master")
public class MasterApi {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMaster(MasterVO masterVO) {

        if (masterVO == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        MasterVO master= MasterCRUD.saveMaster(masterVO);

        return Response.status(201).entity(master).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMasters() {
        return Response.status(200).entity(MasterCRUD.getAll()).build();
    }

    @GET
    @Path("/{mid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMaster(@PathParam("mid") long mid) {
        return Response.status(200).entity(MasterCRUD.getMasterById(mid)).build();
    }

    @GET
    @Path("/{mid}/student")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMasterStudents(@PathParam("mid") long mid) {
        return Response.status(200).entity(
                studentsToStudentVos(MasterCRUD.getMasterStudents(mid))
        ).build();
    }

    @GET
    @Path("/{mid}/course")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMasterCourses(@PathParam("mid") long mid) {
        return Response.status(200).entity(MasterCRUD.getMasterCourses(mid)).build();
    }

    @POST()
    @Path("/{mid}/course")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addCourseToMaster(@PathParam("mid")long mid,long cid) {

        if (cid == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            MasterCRUD.addCourse(mid,cid);
        } catch (Exceptions.NotFound notFound) {
            return Response.status(404).entity(notFound.getMessage()).build();
        }

        return Response.status(201).entity("yep!").build();
    }



}
