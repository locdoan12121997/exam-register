package com.loc;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;

@Path("/students")
public class Student {
	
	//CHECKED
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentList() throws Exception{
		String query = "CALL GetStudents();";
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}
	
	@GET
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployee(@PathParam("studentId") int studentId) throws Exception{
		String query = String.format("CALL GetStudentById(%d);", studentId);
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}
	
	
	//CHECKED
	@GET
	@Path("/{studentId}/modules")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModulesByStudentId(@PathParam("studentId") int studentId) throws Exception{
		String query = String.format("CALL GetModulesByStudentId(%d);", studentId);
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}
	
	
	//CHECKED
	@GET
	@Path("/{studentId}/modules/{moduleId}/sessions/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModuleSessionByStudentIDModuleId(@PathParam("studentId") int studentId, @PathParam("moduleId") int moduleId) throws Exception{
        String query = String.format("CALL getModuleSessionByStudentIDModuleId(%d, %d);", studentId, moduleId);
        JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}
	
	@GET
	@Path("/{studentId}/modules/{moduleId}/exam/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExamByStudentIDModuleId(@PathParam("studentId") int studentId, @PathParam("moduleId") int moduleId) throws Exception{
		String query = String.format("CALL GetExamByStudentIDModuleId(%d, %d);", studentId, moduleId);
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}

	//CHECKED
	@PUT
	@Path("/{studentId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateStudent(com.model.Student student, @PathParam("studentId") int studentId) throws Exception{
		String query = String.format("CALL UpdateStudent(%d, '%s', '%s', '%s', '%s', '%s');", studentId, student.getUserName(), student.getPassword(), student.getFirstName(), student.getLastName(), student.getCode());
		Util.executeQuery(query);
		return Response.ok().build();
	}

	
	//CHECKED
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createStudentAccount(com.model.Student student) throws Exception{
		String query = String.format("CALL CreateStudentAccount('%s', '%s', '%s', '%s', '%s');", student.getUserName(), student.getPassword(), student.getFirstName(), student.getLastName(), student.getCode());
		Util.executeQuery(query);
		return Response.ok().status(201).build();
	}

	//CHECKED
	@DELETE
	@Path("/{studentId}")
	public Response deleteStudentAccount(@PathParam("studentId") int studentId) throws Exception{
		String query = String.format("CALL DeleteStudentAccount(%d);", studentId);
		Util.executeQuery(query);
		return Response.ok().status(204).build();	
	}
	
	@DELETE
	@Path("/{studentId}/sessions/{sessionId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteAttendance(@PathParam("studentId") int studentId, @PathParam("sessionId") int sessionId) throws Exception{
		String query = String.format("CALL DeleteAttendance('%s', '%s');", studentId, sessionId);
		Util.executeQuery(query);
		return Response.ok().status(204).build();	
	}
	
	@DELETE
	@Path("/{studentId}/exams/{examId}")
	public Response deleteRegister(@PathParam("studentId") int studentId, @PathParam("examId") int examId) throws Exception{
		String query = String.format("CALL DeleteRegister(%d, %d);", studentId, examId);
		Util.executeQuery(query);
		return Response.ok().status(204).build();	
	}
}
