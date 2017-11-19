import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/time")
public class DateTimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type
        resp.setContentType("text/html");

        resp.setIntHeader("Refresh", 5);//с автообновлением каждые 5 секунд

        PrintWriter printWriter = resp.getWriter();

        String title = "Current time";
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" HH:mm:ss 'at' dd.MM.yyyy");

        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        printWriter.println(docType +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor = \"#f0f0f0\">\n" +
                "<h1 align = \"center\">" + title + "</h1>\n" +
                "<h2 align = \"center\">" + simpleDateFormat.format(date) + "</h2>\n" +
                "<h2 align = \"center\">" + req.getHeader("User-Agent") + "</h2>\n" +
                "</body> </html>"
        );
    }
}
