import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/SessionsServlet")
public class SessionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        HttpSession session = req.getSession();
        System.out.println("isNew : " + session.isNew());
        System.out.println("id : " + session.getId());

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        System.out.println(login);
        System.out.println(password);

        Map<String, String> userData = readFromFile();

//        с помощью сессии
//        if (session.isNew()) {
//            session.setAttribute("login", login);
//            session.setAttribute("password", password);
//        } else {
//            login = (String) session.getAttribute("login");
//            password = (String) session.getAttribute("password");
//        }

        //с помощью cookie
        Cookie cookie = null;
        Cookie[] cookies = null;
        cookies = req.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals("login")) {
                    login = cookie.getValue();
                }
                if (cookie.getName().equals("password")) {
                    password = cookie.getValue();
                }

                System.out.println("Name : " + cookie.getName());
                System.out.println("Value: " + cookie.getValue());
            }
            System.out.println("login in cookie checker:" + login);
            System.out.println("password in cookie checker:" + password);
        } else {
            System.out.println("No cookies founds");
        }


        PrintWriter printWriter = resp.getWriter();

        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
        //если логин существует
        if (userData.containsKey(login)) {
            if (userData.containsValue(password)) {

                Cookie loginCookie = new Cookie("login", login);
                Cookie passwordCookie = new Cookie("password", password);
                loginCookie.setMaxAge(60 * 60);
                passwordCookie.setMaxAge(60 * 60);

                resp.addCookie(loginCookie);
                resp.addCookie(passwordCookie);

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
//                printWriter.println ("<html><body><script>alert('Password invalid!');</script></body></html>");
                printWriter.println(docType +
                        "<html>\n" +
                        "<head><title>" + "Error" + "</title></head>\n" +
                        "<body bgcolor = \"#D02727\">\n" +
                        "<h1 align = \"center\">" + "Sorry!" + "</h1>\n" +
                        "<ul>\n" +
                        "<center><img  src=\"oops.jpg\" width=\"500\" height=\"400\"></center>" +
                        "<h2 align = \"center\" <b>Your password invalid</b> </h2>" + "\n" +
                        "</ul>\n" +
                        "<h2 align = \"center\" <b>You will be redirect to login form in 5 seconds...</b> </h2>" +
                        "<script>\n" +
                        "  setTimeout(function() {\n" +
                        "      document.location = \"/login.html\";\n" +
                        "  }, 5000); \n" +
                        "</script>" +
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
