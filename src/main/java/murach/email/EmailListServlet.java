package murach.email;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;


import murach.business.User;
//import murach.data.UserDB;
//@WebServlet("/emailList")
public class EmailListServlet extends HttpServlet  {

    @Override
    protected void doPost(HttpServletRequest request, 
                          HttpServletResponse response) 
                          throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = "/index.html";

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";  // default action
        }
// perform action and set URL to appropriate page
        if (action.equals("join")) {
            url = "/index.html";    // the "join" page
        }
        else if (action.equals("add")) {                
            // get parameters from the request
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            // store data in User object and save User object in db
            User user = new User(firstName, lastName, email);
            //UserDB.insert(user);
            
            // --- GỌI HÀM GỬI EMAIL THỰC TẾ ---
            String to = email;
            String subject = "Xác nhận đăng ký tài khoản thành công!";
            // Có thể dùng thẻ HTML như <h3>, <p>, <br> để trang trí thư
            String body = "<h3>Xin chào " + firstName + " " + lastName + ",</h3>"
                        + "<p>Cảm ơn bạn đã đăng ký tài khoản thành công tại hệ thống của chúng tôi.</p>";
            
            // Gửi email trên một luồng riêng để trang web chuyển hướng nhanh, không bị đơ chờ gửi mail
            new Thread(() -> {
                MailUtil.sendMail(to, subject, body);
            }).start();
            // -----------------------------------

            // set User object in request object and set URL
            request.setAttribute("user", user);
            url = "/thanks.jsp";   // the "thanks" page
        }
        
        // forward request and response objects to specified URL
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }   
    @Override
    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response) 
                         throws ServletException, IOException {
        doPost(request, response);
    }    
}
