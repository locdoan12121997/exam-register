package com.loc;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;

@Path("/modules")
public class Module {		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModuleList(@DefaultValue("false") @QueryParam("overlap") boolean overlap) throws Exception{
		String query = (overlap) ? "CALL GetOverlapModules();" : "CALL GetAllModulesDetails();";
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}

	@GET
	@Path("/{moduleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModules(@PathParam("moduleId") int moduleId) throws Exception {
		String query = String.format("CALL GetModuleById(%d);", moduleId);
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}

	// CHECKED
	@GET
	@Path("/{moduleId}/sessions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModuleSessionsByModuleId(@PathParam("moduleId") int moduleId) throws Exception {
		String query = String.format("CALL GetModuleSessionsByModuleId(%d);", moduleId);
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}

	// CHECKED
	@GET
	@Path("/{moduleId}/exam")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExamByModuleId(@PathParam("moduleId") int moduleId) throws Exception {
		String query = String.format("CALL GetExamsByModuleId(%d);", moduleId);
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}

	// CHECKED
	@GET
	@Path("/{moduleId}/exam/{examId}/registers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRegistersByExamId(@PathParam("examId") int examId) throws Exception {
		String query = String.format("CALL GetRegistersByExamId(%d);", examId);
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}

	@PUT
	@Path("/{moduleId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateModule(com.model.Module module, @PathParam("moduleId") int moduleId) throws Exception {
		String query = String.format("CALL UpdateModule(%d, '%s', '%s');", moduleId, module.getCode(),
				module.getName());
		Util.executeQuery(query);
		return Response.ok().build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createModule(com.model.Module module) throws Exception {
		String query = String.format("CALL CreateModule('%s', '%s', %d);", module.getCode(), module.getName(),
				module.getSemesterId());
		Util.executeQuery(query);
		return Response.ok().status(201).build();
	}

	@DELETE
	@Path("/{moduleId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteModule(@PathParam("moduleId") int moduleId) throws Exception {
		String query = String.format("CALL DeleteModule(%d);", moduleId);
		Util.executeQuery(query);
		return Response.ok().status(204).build();
	}

	@GET
	@Path("/{moduleId}/examList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExamList(@PathParam("moduleId") int moduleId) throws Exception {
		String query = String.format("CALL GetEnrollsByModuleId(%d);", moduleId);
		JSONArray jsonArray = Util.getQueryArray(query);
		JSONArray resultArray = new JSONArray();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject studentObject = jsonArray.getJSONObject(i);
			int studentId = studentObject.getInt("id");
			if (Util.isQualified(studentId, moduleId)){
				resultArray.put(studentObject);
			}
		}
		
		return Response.ok().entity(resultArray.toString()).build();
	}

	@GET
	@Path("/{moduleId}/students")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEnrollmentList(@PathParam("moduleId") int moduleId) throws Exception {
		String query = String.format("CALL GetEnrollsByModuleId(%d);", moduleId);
		JSONArray jsonArray = Util.getQueryArray(query);
		return Response.ok().entity(jsonArray.toString()).build();
	}
}
