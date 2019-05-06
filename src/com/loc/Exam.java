package com.loc;

import javax.ws.rs.DELETE;
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

@Path("/exams")
public class Exam {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExamList() throws Exception{
		String query = "CALL GetExams();";	
		JSONArray jsonArray = Main.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}
	
	@GET
	@Path("/{examId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExambyID(@PathParam("examId") int examId) throws Exception{
		String query = String.format("CALL GetExamById(%d);", examId);
		JSONArray jsonArray = Main.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}
	
	@PUT
	@Path("/{examId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateExam(@PathParam("examId") int examId, com.model.Exam exam) throws Exception{
		String query = String.format("CALL UpdateExam(%d,'%s', '%s','%s', '%s);", examId, exam.getExamDate(), exam.getFromTime(), exam.getToTime(), exam.getDeadline());
		Main.executeQuery(query);
		return Response.ok().build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createExam(com.model.Exam exam) throws Exception{
		String query = String.format("CALL CreateExam('%s', '%s', '%s', '%s', %d);", exam.getExamDate(), exam.getFromTime(), exam.getToTime(), exam.getDeadline(), exam.getModuleId());
		Main.executeQuery(query);
		return Response.ok().status(201).build();
	}
	
	@DELETE
	@Path("/{examId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteExam(@PathParam("examId") int examId) throws Exception{
		String query = String.format("CALL DeleteExam(%d);", examId);
		Main.executeQuery(query);
		return Response.ok().status(204).build();
	}
}
