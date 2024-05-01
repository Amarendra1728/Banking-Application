package com.banking;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UpdateProcess extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");

        int accountNumber = Integer.parseInt(request.getParameter("accNum"));
        String newName = request.getParameter("name");

        Connection con = null;
        try {
            con = DBConnection.get();
            String query = "UPDATE account SET name = ? WHERE num = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, newName);
            ps.setInt(2, accountNumber);

            int count = ps.executeUpdate();
            if (count > 0) {
                pw.print("<h3 align='center'>Name updated successfully for Account Number " + accountNumber + "</h3>");
                RequestDispatcher rd = request.getRequestDispatcher("/user.html");
                rd.include(request, response);
            } else {
                pw.print("<h3 align='center'>Failed to update name for Account Number " + accountNumber + "</h3>");
                RequestDispatcher rd = request.getRequestDispatcher("/user.html");
                rd.include(request, response);
            }
        } catch (Exception e) {
            pw.print("<h3 align='center'>Error occurred: " + e.getMessage() + "</h3>");
            RequestDispatcher rd = request.getRequestDispatcher("/user.html");
            rd.include(request, response);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
