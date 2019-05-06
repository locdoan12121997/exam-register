package com.loc;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Attendance {
	@POST
	@Path("/attendances")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAttendance(com.model.Attendance attendance) throws Exception{
		String query = String.format("CALL CreateAttendance('%s', '%s');", attendance.getStudentId(), attendance.getSessionId());
		Main.executeQuery(query);
		return Response.ok().status(201).build();
	}

	//CHECKED
	@DELETE
	@Path("/students/{studentId}/sessions/{sessionId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteAttendance(@PathParam("studentId") int studentId, @PathParam("sessionId") int sessionId) throws Exception{
		String query = String.format("CALL DeleteAttendance('%s', '%s');", studentId, sessionId);
		Main.executeQuery(query);
		return Response.ok().status(204).build();	
	}
}
