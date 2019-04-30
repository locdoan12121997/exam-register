package com.loc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import java.sql.ResultSet;

import org.json.JSONObject;

@Path("/semester")
public class Semester {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSemesterList() {
		try {
            Main.establishConnection();
	        String query = "CALL GetSemesters();";	
	        ResultSet resultSet = Main.getResultSet(query);
	        System.out.println(resultSet);
	        JSONObject jsonObject = JsonSerializer.convertToJSON(resultSet);
	        resultSet.close();
			return Response.ok().entity(jsonObject.toString()).build();
		}catch (Exception e) {
            e.printStackTrace();
            return null;
		}
	}
	
	@GET
	@Path("/{semesterId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSemesterbyID(@PathParam("semesterId") int semesterId) {
		try {
            String query = String.format("CALL GetSemesterById(%d);", semesterId);
            ResultSet resultSet = Main.getResultSet(query);
            JSONObject jsonObject = JsonSerializer.convertToJSON(resultSet);
            resultSet.close();
			return Response.ok().entity(jsonObject.toString()).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
	}
	
	@GET
	@Path("/{semesterId}/exams")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExambyID(@PathParam("semesterId") int semesterId) {
		try {
            String query = String.format("CALL GetExamBySemesterId(%d);", semesterId);
            ResultSet resultSet = Main.getResultSet(query);
            JSONObject jsonObject = JsonSerializer.convertToJSON(resultSet);
            resultSet.close();
			return Response.ok().entity(jsonObject.toString()).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
	}
}
