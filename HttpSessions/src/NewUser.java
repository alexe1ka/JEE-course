import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/NewUser")
public class NewUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String newLogin = req.getParameter("login");
        String newPassword = req.getParameter("password");
        writeToFile(newLogin, newPassword);
        PrintWriter printWriter = resp.getWriter();
        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
        printWriter.println(docType +
                "<html>\n" +
                "<head><title>" + "Create" + "</title></head>\n" +
                "<body bgcolor = \"#f0f0f0\">\n" +
                "<h1 align = \"center\">" + "Create new user!!!" + "</h1>\n" +
                "<ul>\n" +
                "<li><b>Your login: </b>: "
                + req.getParameter("login") + "\n" +
                "<p><a href=\"/login.html\">On authorization page</a></p>"   +
                ""+
                "</ul>\n" +
                "</body></html>"
        );

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void writeToFile(String login, String password) {
        BufferedWriter out = null;
        FileWriter fileWriter = null;
        ServletContext context = getServletConfig().getServletContext();
        String fullPath = context.getRealPath("users.txt");//будет храниться в artifacts
        System.out.println(fullPath);
        try {
            fileWriter = new FileWriter(fullPath, true);
            out = new BufferedWriter(fileWriter);
            out.write(login + "#" + password);
            out.newLine();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
