package ir.fanap.zamiri.project5.controller;


import ir.fanap.zamiri.project5.data.modelVO.StudentVO;
import ir.fanap.zamiri.project5.data.repository.StudentCRUD;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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


}
