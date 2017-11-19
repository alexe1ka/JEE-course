import main.java.Mailer;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.mail.*;

@WebServlet("/MailServlet")
public class MailServlet extends HttpServlet {
    private String host = "smtp.rambler.ru";
    private String port = "465";
    private String user = "servlet-test@rambler.ru";
    private String pass = "****";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String subject = req.getParameter("name");
        String recipient = req.getParameter("mail_address");
        String content = req.getParameter("message");

        String resultMessage = "";

        try {
            Mailer.sendEmail(host, port, user, pass, recipient, subject, content);
            resultMessage = "The e-mail was sent successfully";
        } catch (Exception ex) {
            ex.printStackTrace();
            resultMessage = "There were an error: " + ex.getMessage();
        } finally {
            req.setAttribute("Message", resultMessage);
            getServletContext().getRequestDispatcher("/Result.jsp").forward(req, resp);
        }
    }
}
