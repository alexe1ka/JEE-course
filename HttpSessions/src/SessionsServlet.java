import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/SessionsServlet")
public class SessionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        Map<String, String> userData = readFromFile();
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        PrintWriter printWriter = resp.getWriter();

        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
        //если логин существует
        if (userData.containsKey(login)) {
            if (userData.containsValue(password)) {
                printWriter.println("<html>\n" +
                        "<head><title>" + "Successful!" + "</title></head>\n" +
                        "<body bgcolor = \"#FFFFFF\">\n" +
                        "<h1 align = \"center\">" + "Hello," + "</h1>\n" +
                        "<h2 align = \"center\" <b>" + login + "!" + "</b> </h2>" + "\n" +
                        "<ul>\n" +
                        "<center><img  src=\"welcome.jpg\" ></center>" +

                        "</ul>\n" +
                        "</body></html>");
            } else {
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
        } else {
            resp.sendRedirect("create.html");

        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private Map<String, String> readFromFile() {
        Map<String, String> map = new HashMap<String, String>();
        BufferedReader in = null;
        FileReader fileReader = null;
        String line = "";
        ServletContext context = getServletConfig().getServletContext();
        String fullPath = context.getRealPath("users.txt");
        try {
            in = new BufferedReader(new FileReader(fullPath));

            while ((line = in.readLine()) != null) {
                String parts[] = line.split("#");
                map.put(parts[0], parts[1]);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            System.out.println(map.toString());
        }
        return map;
    }


}
