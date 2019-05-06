package com.loc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

@Path("/hello")
public class Hello {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public com.model.Student sayPlainTextHello() {
		//System.out.println("AAAA");
		//com.model.Student student = new com.model.Student("AAA", "BBB", "CCC", "DDD", "EEE");
		//return student;
		return null;
	}
}
