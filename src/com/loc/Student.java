package com.loc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import java.sql.ResultSet;

import org.json.JSONObject;

@Path("/students")
public class Student {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentList() {
		try {
            Main.establishConnection();
	        String query = "CALL GetStudents();";	
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
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployee(@PathParam("studentId") int studentId) {
		try {
            String query = String.format("CALL GetStudentById(%d);", studentId);
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
	@Path("/{studentId}/modules")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModulebyStudentID(@PathParam("studentId") int studentId) {
		try {
            String query = String.format("CALL GetModulesByStudentId(%d);", studentId);
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
