package com.banking;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UpdateName extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);
        if (session != null) {
            String user = (String) session.getAttribute("name");
            pw.print("<h1 align='center'>Welcome, " + user + ". You can update your name here.</h1>");
            Connection con = null;
            try {
                con = DBConnection.get();
                int num = Integer.parseInt(request.getParameter("accountNumber").trim());

                String query = "select name from account where num=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, num);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String oldName = rs.getString("name");
                    pw.print("<h3 align='center'>Old Name for Account Number " + num + ": " + oldName + "</h3>");
                    pw.println("<html>");
    				pw.println("<body>");
    				pw.println("<form action='UpdateProcess' method='post'>");
    				pw.println("Ac Number:<input type='text' value='"+num+"' name='accNum'>");
    				pw.println("Name:<input type='text' value='"+oldName+"' name='name'>");
    				pw.println("<input type='submit' value='Update Name'>");
    				pw.println("</form>");
    				pw.println("</html>");
    				pw.println("</body>");
                } else {
                    pw.print("<h3 align='center'>Invalid Account Number Given - Try again</h3>");
                    RequestDispatcher rd = request.getRequestDispatcher("/user.html");
                    rd.include(request, response);
                }
            } catch (Exception e) {
                pw.print("<h3 align='center'>Error occurred: " + e.getMessage() + "</h3>");
                request.getRequestDispatcher("/user.html").include(request, response);
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            pw.print("<h3>You logged out from previous Session - Please Login</h3>");
            request.getRequestDispatcher("login.html").include(request, response);
        }
    }
}
