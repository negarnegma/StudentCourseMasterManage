package ir.fanap.zamiri.project5.controller;


import ir.fanap.zamiri.project5.data.modelVO.StudentVO;
import ir.fanap.zamiri.project5.data.repository.CourseCRUD;
import ir.fanap.zamiri.project5.data.repository.MasterCRUD;
import ir.fanap.zamiri.project5.data.repository.StudentCRUD;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by NZamiri
 */

@Path("/find")
public class FindApi {

    @POST
    @Path("/student")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findStudent(String code) {

        if (code == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(201).entity(StudentCRUD.findStudentByCode(code)).build();
    }

    @POST
    @Path("/master")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findMaster(String name) {

        if (name == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(201).entity(MasterCRUD.findMasterByName(name)).build();
    }

    @POST
    @Path("/course")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findCourse(String name) {

        if (name == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(201).entity(CourseCRUD.findCourseByName(name)).build();
    }

}
