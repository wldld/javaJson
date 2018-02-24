package v1;

import java.io.IOException;
import java.io.PrintWriter;

public class Servlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        imgShow imgs= new imgShow();
        PrintWriter writer = null;
        try {
            String jsonStr = imgs.imgShow();
            writer =response.getWriter();
            writer.append(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
