package com.loc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

@Path("/hello")
public class Hello {
	@Path("/text")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "{\"text\":\"Hello World RESTful Jersey!\"}";
	}
	

	@Path("/json")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJson() {
		JSONObject jo = new JSONObject();
		jo.put("name", "jon doe");
		jo.put("age", "22");
		jo.put("city", "chicago");
		System.out.println(jo.toString());
		return Response.ok().entity(jo).build();
	}
	
	@Path("/json1")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJson1() {
		JSONObject jo = new JSONObject();
		jo.put("name", "jon doe");
		jo.put("age", "22");
		jo.put("city", "chicago");
		return Response.ok().entity(jo.toString()).build();
	}
}
