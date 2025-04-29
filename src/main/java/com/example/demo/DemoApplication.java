package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	final static private String key = "cbe1435dabc740a1954cc0694fd2b728";
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		scrap();
	}

	public static void scrap(){

		try {
			String urlString = "https://api.bls.gov/publicAPI/v2/timeseries/data/";
			URL urkl = new URL(urlString);

			HttpURLConnection conn = (HttpURLConnection) urkl.openConnection();
			conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

			//black women wages, women with jobs with a child under 3, housework hours 
String jsonInputString = "{\"seriesid\":[\"FMUP1378869\",\"LEU0252885100\",\"TUU10101AA01001023\",\"TUU10101AA01000944\",\"LEU0257926700\"],\"startyear\":\"2003\",\"endyear\":\"2024\",\"registrationkey\":\"" + key + "\"}";


			try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);//sends json data to get the data we want. The series ID
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));//recives server response. BufferReader reads it line by line obv
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {//reads response line by line
                content.append(inputLine);//add line to string builder
            }
            in.close();//closes reader
            conn.disconnect();//clossed http connection

            System.out.println(content.toString());//prints respone from server
		} catch (Exception e) {
		}

	}

}
