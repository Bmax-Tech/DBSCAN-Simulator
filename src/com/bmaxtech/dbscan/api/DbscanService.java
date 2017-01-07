package com.bmaxtech.dbscan.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bmaxtech.dbscan.logic.Dbscan;
import com.bmaxtech.dbscan.logic.Point;

@Path("/")
public class DbscanService {
	@POST
	@Path("/getScanned")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getScanned(InputStream inputStream) {
		StringBuilder stringBuilder = new StringBuilder();
		JSONArray res = new JSONArray();
		try {
			/**
			 * convert input stream from HTTP request into string and then build
			 * json object
			 */
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = in.readLine()) != null) {
				stringBuilder.append(line);
			}
			/**
			 * build json object
			 */
			JSONObject data = new JSONObject(stringBuilder.toString());
			System.out.println("JSon Data - " + data.toString());

			/**
			 * Perform DBSCAN operation
			 */
			Dbscan.hset.clear();
			Dbscan.setMinPoints(data.getInt("minimumPoints"));
			Dbscan.setTDistance(data.getInt("thresholdDistance"));

			for (int i = 0; i < data.getJSONArray("pointsList").length(); i++) {
				JSONObject point = data.getJSONArray("pointsList").getJSONObject(i);
				Dbscan.addPoint(point.getInt("x"), point.getInt("y"));
			}
			
			res = performDBSCAN();

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(200).entity(res.toString()).build();
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JSONArray performDBSCAN(){
		Vector<List> trl = Dbscan.applyDbscan();
		int index1 = 0;
		JSONArray clusters = new JSONArray();
		for(List l : trl){
			JSONObject cluster = new JSONObject();
			try {
				cluster.put("cluster", (index1 + 1));
				List<JSONObject> points = new ArrayList<>();
				
				Iterator<Point> j = l.iterator();
				while (j.hasNext()) {
					Point w = j.next();
					
					JSONObject point = new JSONObject();
					point.put("x", w.getX());
					point.put("y", w.getY());
					
					points.add(point);
				}
				
				cluster.put("points", points);
				clusters.put(cluster);
				
				index1++;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return clusters;
	}
}
