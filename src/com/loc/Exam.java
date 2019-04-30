package com.loc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import java.sql.ResultSet;

import org.json.JSONObject;

@Path("/exam")
public class Exam {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExamList() {
		try {
            Main.establishConnection();
	        String query = "CALL GetExams();";	
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
	@Path("/{examId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExambyID(@PathParam("examId") int examId) {
		try {
            String query = String.format("CALL GetExamById(%d);", examId);
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
