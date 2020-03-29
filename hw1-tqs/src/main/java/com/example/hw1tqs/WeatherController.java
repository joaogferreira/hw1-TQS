package com.example.hw1tqs;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.TreeMap;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
public class WeatherController extends HttpServlet {

    public static final String ACCESS_KEY = "";
    static CloseableHttpClient httpClient = HttpClients.createDefault();
    public Map<String, Double> rates = new TreeMap<>();
    String city,city2;

    @RequestMapping("/")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(true);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

		String html_code = "<!DOCTYPE html>"+
		"<html lang=\"en\">"+
		"    <head>"+
		"        <title>Weather Forecast</title>"+
		"        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"+
		"        <style>"+
		"            #form{"+
		"                margin: 2%"+
		"            }"+
		""+
		"            #pickCity {"+
		"                margin-bottom: 2%"+
		"            }"+
		""+
		"            #submit {"+
		"                margin-top: 2%"+
		"            }"+
		"        </style>"+
		"    </head>"+
		"    <body>"+
		"        <form action=\"api\" method=\"GET\" id=\"form\">"+
		"            <label>City: </label>"+
		"            <input type=\"string\" name=\"input_city\" id=\"inputCity\" lang=\"en\"><br>"+
		"            <label >Pick City: </label>"+
		"            <select name=\"input_city2\">"+
		"				 <option value='' title=''></option>"+
		"                <option value='PORTO' title='PORTO'>PORTO</option>"+
		"                <option value='AVEIRO' title='AVEIRO'>AVEIRO</option>"+
		"                <option value='BRAGANÇA' title='BRAGANÇA'>BRAGANÇA</option>"+
		"            </select>"+
		"            <input id=\"submit\" type=\"submit\" value=\"Submit\">"+
		"        </form>"+
        "</body></html>";
		
		out.println(html_code); 
        
      
    }
    
    @RequestMapping("/api")
    protected void rand(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	this.city = request.getParameter("input_city");
    	this.city2 = request.getParameter("input_city2");
    	String myvar = "<div id=\"main\">"+
    			"   <div>"+
    			city + "\n" +
    			city2 +
    			"   </div>"+
    			"</div>";
	
    	PrintWriter out = response.getWriter();
    	out.println(myvar);
    }
    
   
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("GET");
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("POST");
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    
}