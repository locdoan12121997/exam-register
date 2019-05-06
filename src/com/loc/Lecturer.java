package com.loc;

import javax.ws.rs.GET;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;

@Path("/lecturers")
public class Lecturer {

	//CHECKED
	@GET
	@Path("/{lecturerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLecturerById(@PathParam("lecturerId") int lecturerId) throws Exception{
		String query = String.format("CALL GetLecturerById(%d);", lecturerId);
		JSONArray jsonArray = Main.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public Response getLecturers() throws Exception{
		String query = String.format("CALL GetLecturers();");
		JSONArray jsonArray = Main.getQueryArray(query);
    	return Response.ok().entity(jsonArray.toString()).build();
	}
	
	
	//CHECKED
	@GET
	@Path("/{lecturerId}/modules")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getModulesByLecturerId(@PathParam("lecturerId") int lecturerId) throws Exception{
		String query = String.format("CALL GetModulesByLecturerId(%d);", lecturerId);
        JSONArray jsonArray = Main.getQueryArray(query);
        return Response.ok().entity(jsonArray.toString()).build();
	}
	
	//CHECKED
	@GET
	@Path("/{lecturerId}/modules/{moduleId}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getLecturerModuleSessionByLecturerIDModuleId(@PathParam("lecturerId") int lecturerId, @PathParam("moduleId") int moduleId) throws Exception{
		String query = String.format("CALL GetModuleSessionByLecturerIDModuleId(%d, %d);", lecturerId, moduleId);
		JSONArray jsonArray = Main.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}

	@PUT
	@Path("/{lecturerId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateLecturer(com.model.Lecturer lecturer, @PathParam("lecturerId") int lecturerId) throws Exception{
		String query = String.format("CALL UpdateLecturer(%d, '%s', '%s', '%s', '%s');", lecturerId, lecturer.getUserName(), lecturer.getPassword(), lecturer.getFirstName(), lecturer.getLastName());
		Main.executeQuery(query);
		return Response.ok().build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createLecturerAccount(com.model.Lecturer lecturer) throws Exception{
		String query = String.format("CALL CreateLecturerAccount('%s', '%s', '%s', '%s');", lecturer.getUserName(), lecturer.getPassword(), lecturer.getFirstName(), lecturer.getLastName());
		Main.executeQuery(query);
		return Response.ok().status(201).build();			
	}

	@DELETE
	@Path("/{lecturerId}")
	public Response deleteLecturerAccount(@PathParam("lecturerId") int lecturerId) throws Exception{
		String query = String.format("CALL DeleteLecturerAccount(%d);", lecturerId);
		Main.executeQuery(query);
		return Response.ok().status(204).build();	
	}
}

