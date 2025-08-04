# Digital Banking Application

This is a web-based digital banking system that allows users to perform essential banking operations in a secure and user-friendly environment.
The application focuses on core banking functionalities, robust security practices, and a clean user interface.

## 🚀 Project Overview

The Digital Banking Application provides a platform for users to manage their bank accounts online.
Key features include creating accounts, managing funds through deposits and withdrawals, 
transferring money between accounts, and updating profile information. 
The system ensures data integrity and security through session-based authentication and a secure Oracle database backend.

## ✨ Features

This application implements the following features:

* **Authentication System**
    * **User Registration:** New users can register with a unique username and password.
    * **Secure Login/Logout:** Users can log in to access their accounts, with session management ensuring secure access.

* **Account Management**
    * **Create Account:** Once registered, users can create a new bank account with an initial deposit.
    * **Update Profile:** Users have the ability to modify their account name.

* **Transaction Management**
    * **Deposit:** Add funds directly into your account.
    * **Withdraw:** Securely withdraw money. This process is handled by a database stored procedure.
    * **Fund Transfer:** Transfer funds between different accounts with automatic balance verification.
    * **Check Balance:** View the current account balance at any time.

## 🛠️ Technologies Used

The project is built with the following technologies:

* **Backend:** Java, Servlets, JSP, JDBC
* **Frontend:** HTML, CSS, JavaScript
* **Database:** Oracle Database
* **Web Server:** Apache Tomcat

## 🗃️ Database Design

The application uses two primary tables to manage user and account data:

* `register`: Stores user login credentials.
    * `username`
    * `password`
* `account`: Stores account holder details and financial information.
    * `num` (Account Number)
    * `name`
    * `balance`

## 🔒 Security Measures

Security was a top priority. The following measures were implemented to protect user data and ensure transactional integrity:

* **Session Management:** Secure session handling is used to ensure that only authenticated users can access banking features.
* **Input Validation:** The application uses JavaScript for client-side validation and JDBC Prepared Statements on the backend to prevent SQL injection attacks.
* **Transactional Integrity:** `commit` and `rollback` operations are used for fund transfers and withdrawals to ensure that transactions are atomic and the database remains consistent.

## 🎯 Challenges & Solutions

Several challenges were addressed during the development of this project:

* **Concurrency in Fund Transfers:**
    * **Problem:** Preventing race conditions or double-spending issues during simultaneous transactions.
    * **Solution:** Implemented database transactions with `commit` and `rollback` to ensure atomicity.
* **Session Handling:**
    * **Problem:** Ensuring sessions were correctly invalidated upon logout to prevent unauthorized access.
    * **Solution:** Used `HttpSession.invalidate()` in the logout servlet to properly terminate user sessions.
* **SQL Injection Risks:**
    * **Problem:** Protecting the database from malicious SQL queries.
    * **Solution:** Used `PreparedStatements` in all JDBC queries to neutralize SQL injection threats.

## 🔮 Future Enhancements

To further improve the application, the following features are planned for future development:

* **Email & SMS Notifications:** Send alerts for transactions.
* **Role-Based Access Control:** Introduce distinct roles for 'Admin' and 'User'.
* **Graphical Dashboard:** Implement a visual summary of account activity.
* **Third-Party API Integration:** Connect with external payment gateways.
