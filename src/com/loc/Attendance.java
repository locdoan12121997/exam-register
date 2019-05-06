package com.loc;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/attendances")
public class Attendance {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAttendance(com.model.Attendance attendance) throws Exception{
		String query = String.format("CALL CreateAttendance('%s', '%s');", attendance.getStudentId(), attendance.getSessionId());
		Util.executeQuery(query);
		return Response.ok().status(201).build();
	}
}
