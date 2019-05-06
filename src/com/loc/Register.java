package com.loc;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/registers")
public class Register {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createRegister(com.model.Register resgister) throws Exception{
		String query = String.format("CALL CreateRegister('%s', '%s');", resgister.getStudentId(), resgister.getExamId());
		Util.executeQuery(query);
		return Response.ok().status(201).build();
	}
}
