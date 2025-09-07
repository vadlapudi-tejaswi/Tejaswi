
package com.vsv;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import  jakarta.servlet.ServletException;
import  jakarta.servlet.annotation.WebServlet;
import  jakarta.servlet.http.HttpServlet;
import  jakarta.servlet.http.HttpServletRequest;
import  jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");   // make sure response is HTML
        PrintWriter out = response.getWriter();

        String n = request.getParameter("txtName");
        String p = request.getParameter("txtPwd");

        try {
            // 1. Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Connect to DB
            Connection con = DriverManager.getConnection(
            	    "jdbc:mysql://localhost:3306/todo_tasks", "root", "root");


            // 3. Prepare SQL statement
            PreparedStatement ps = con.prepareStatement(
                "select uname from login where uname=? and password=?");
            ps.setString(1, n);
            ps.setString(2, p);

            // 4. Execute query
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //  Login success
            	request.getSession().setAttribute("username", n);
                RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
                rd.forward(request, response);
            } else {
                //  Login failed
                out.println("<h2 style='color:red'>Login Failed!!</h2>");
                out.println("<a href='Login.jsp'>Try AGAIN!!</a>");
            }
            

            con.close();

        } catch (ClassNotFoundException e) {
            out.println("<h3 style='color:red'>Error: JDBC Driver not found!</h3>");
            out.println("<pre>" + e.getMessage() + "</pre>");
            e.printStackTrace();
        } catch (SQLException e) {
            out.println("<h3 style='color:red'>Database Error Occurred!</h3>");
            out.println("<pre>" + e.getMessage() + "</pre>");
            e.printStackTrace();
        } catch (Exception e) {
            out.println("<h3 style='color:red'>Unexpected Error!</h3>");
            out.println("<pre>" + e.getMessage() + "</pre>");
            e.printStackTrace();
        }

        out.close();
    }
}
