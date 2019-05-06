package com.loc;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;

@Path("/semesters")
public class Semester {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSemesterList() throws Exception{
		String query = "CALL GetSemesters();";	
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}
	
	@GET
	@Path("/{semesterId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSemesterbyID(@PathParam("semesterId") int semesterId) throws Exception{
		String query = String.format("CALL GetSemesterById(%d);", semesterId);
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();        
	}
	
	

	@GET
	@Path("/{semesterId}/exams")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExambySemesterId(@PathParam("semesterId") int semesterId) throws Exception{
		String query = String.format("CALL GetExamBySemesterId(%d);", semesterId);
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}
	
	
	//CHECKED
	@GET
	@Path("/{semesterId}/modules")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModulesBySemesterId(@PathParam("semesterId") int semesterId) throws Exception{
		String query = String.format("CALL GetModulesBySemesterId(%d)", semesterId);
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}
	
	@PUT
	@Path("/{semesterId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response UpdateSemester(com.model.Semester semester, @PathParam("semesterId") int semesterId) throws Exception{
		String query = String.format("CALL UpdateSemester(%d, '%s', '%s');", semesterId, semester.getFromDate(), semester.getToDate());
		Util.executeQuery(query);
		return Response.ok().build();	
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createSemester(com.model.Semester semester) throws Exception{
		String query = String.format("CALL CreateSemester('%s', '%s');", semester.getFromDate(), semester.getToDate());
		Util.executeQuery(query);
		return Response.ok().status(201).build();
	}
	
	@DELETE
	@Path("/{semesterId}")
	public Response deleteSemester(@PathParam("semesterId") int semesterId) throws Exception{
		String query = String.format("CALL DeleteSemester(%d);", semesterId);
		Util.executeQuery(query);
		return Response.ok().status(204).build();	
	}
}
