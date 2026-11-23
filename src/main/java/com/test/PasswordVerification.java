package com.test;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class PasswordVerification
 */
@WebServlet("/PasswordVerification")
public class PasswordVerification extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordVerification() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		
		String pass=request.getParameter("pass");
		String url="jdbc:mysql://localhost:3306/registration";
		String user="root";
		String pass1="Vishwa@1107";
		
		HttpSession session=request.getSession();
		session.setAttribute("p", pass);
		
		String query = "Select * from details where passwordl=?";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn=DriverManager.getConnection(url, user, pass1);
			
			PreparedStatement st=conn.prepareStatement(query);
			
			st.setString(1, pass);
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				
				response.getWriter().println("<h1> Welcome   "+session.getAttribute("e")+" and   "+session.getAttribute("p")+"</h1>");
				
		 		RequestDispatcher rd=request.getRequestDispatcher("home.html");
		 		
		 		rd.include(request, response);
		 		
			}else {
				
				response.getWriter().println("</h3>Not a valid Password..</h3>");
				
				RequestDispatcher rd=request.getRequestDispatcher("pass.html");
				
		 		rd.include(request, response);
			}
			
			st.close();
			rs.close();
			conn.close();
		
		} catch (Exception e) {
			out.println("<h2>"+e.getMessage()+"</h2>");
		}


	}

}
