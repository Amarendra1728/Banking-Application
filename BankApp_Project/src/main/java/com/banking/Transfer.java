package com.banking;

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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Transfer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);
        if (session != null) {
            Connection con = null;
            try {
                con = DBConnection.get();
                int senderAccount = Integer.parseInt(request.getParameter("senderNumber").trim());
                int receiverAccount = Integer.parseInt(request.getParameter("receiverNumber").trim());
                int amount = Integer.parseInt(request.getParameter("money").trim());
                con.setAutoCommit(false);
                String senderQuery = "SELECT balance FROM account WHERE num = ?";
                PreparedStatement senderPs = con.prepareStatement(senderQuery);
                senderPs.setInt(1, senderAccount);
                ResultSet senderRs = senderPs.executeQuery();

                if (senderRs.next())
                {
                    int senderBalance = senderRs.getInt("balance");
                    if (senderBalance >= amount) 
                    {
                        String senderUpdateQuery = "UPDATE account SET balance = balance - ? WHERE num = ?";
                        PreparedStatement senderUpdatePs = con.prepareStatement(senderUpdateQuery);
                        senderUpdatePs.setInt(1, amount);
                        senderUpdatePs.setInt(2, senderAccount);
                        senderUpdatePs.executeUpdate();

                        String receiverUpdateQuery = "UPDATE account SET balance = balance + ? WHERE num = ?";
                        PreparedStatement receiverUpdatePs = con.prepareStatement(receiverUpdateQuery);
                        receiverUpdatePs.setInt(1, amount);
                        receiverUpdatePs.setInt(2, receiverAccount);
                        int count=receiverUpdatePs.executeUpdate();
                        if(count>0)
                        {
                        pw.print("<h3 align='center'>Transaction Successful</h3>");
                        RequestDispatcher rd = request.getRequestDispatcher("/user.html");
                        rd.include(request, response);
                        con.commit();
                        }
                        else
                        {
                        	pw.print("<h3 align='center'>Destination Account is not Found</h3>");
                            RequestDispatcher rd = request.getRequestDispatcher("/user.html");
                            rd.include(request, response);
                            con.rollback();
                        }
                    } 
                    else {
                        pw.print("<h3 align='center'>Insufficient Balance in Sender's Account</h3>");
                        RequestDispatcher rd = request.getRequestDispatcher("/user.html");
                        rd.include(request, response);
                    }
                } 
                else 
                {
                    pw.print("<h3 align='center'>Invalid Sender Account Number</h3>");
                    RequestDispatcher rd = request.getRequestDispatcher("/user.html");
                    rd.include(request, response);
                }
            } 
            catch (Exception e) 
            {
                pw.print("<h3 align='center'>An error occurred. Please try again later.</h3>");
                e.printStackTrace();
                RequestDispatcher rd = request.getRequestDispatcher("/user.html");
                rd.include(request, response);
            } 
            finally 
            {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } 
        else 
        {
            pw.print("<h3>You logged out from previous Session - Please Login</h3>");
            request.getRequestDispatcher("login.html").include(request, response);
        }
    }
}
