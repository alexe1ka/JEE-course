import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
    private String correctPassword = "password";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");

        PrintWriter printWriter = response.getWriter();
        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
        if (req.getParameter("password").equals(correctPassword)) {
            printWriter.println(docType +
                    "<html>\n" +
                    "<head><title>" + "Hello" + "</title></head>\n" +
                    "<body bgcolor = \"#f0f0f0\">\n" +
                    "<h1 align = \"center\">" + "Hello" + "</h1>\n" +
                    "<ul>\n" +
                    "  <li><b>USER</b>: "
                    + req.getParameter("login") + "\n" +
                    "</ul>\n" +
                    "</body></html>"
            );
        } else {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid password!");
            printWriter.println(docType +
                    "<html>\n" +
                    "<head><title>" + "Error" + "</title></head>\n" +
                    "<body bgcolor = \"#D02727\">\n" +
                    "<h1 align = \"center\">" + "Sorry!" + "</h1>\n" +
                    "<ul>\n" +
                    "<center><img  src=\"oops.jpg\" width=\"500\" height=\"400\"></center>" +
                    "<h2 align = \"center\" <b>Your password invalid</b> </h2>" + "\n" +
                    "</ul>\n" +
                    "</body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
