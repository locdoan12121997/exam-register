package com.loc;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import org.json.JSONArray;

@Path("/exams")
public class Exam {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExamList() throws Exception{
		String query = "CALL GetExams();";	
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}
	
	@GET
	@Path("/{examId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExambyID(@PathParam("examId") int examId) throws Exception{
		String query = String.format("CALL GetExamById(%d);", examId);
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}
	
	@PUT
	@Path("/{examId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateExam(@PathParam("examId") int examId, com.model.Exam exam) throws Exception{
		String query = String.format("CALL UpdateExam(%d,'%s', '%s','%s', '%s');", examId, exam.getExamDate(), exam.getFromTime(), exam.getToTime(), exam.getDeadline());
		Util.executeQuery(query);
		return Response.ok().build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createExam(com.model.Exam exam) throws Exception{
		String query = String.format("CALL CreateExam('%s', '%s', '%s', '%s', %d);", exam.getExamDate(), exam.getFromTime(), exam.getToTime(), exam.getDeadline(), exam.getModuleId());
		Util.executeQuery(query);
		return Response.ok().status(201).build();
	}
	
	@DELETE
	@Path("/{examId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteExam(@PathParam("examId") int examId) throws Exception{
		String query = String.format("CALL DeleteExam(%d);", examId);
		Util.executeQuery(query);
		return Response.ok().status(204).build();
	}
}
