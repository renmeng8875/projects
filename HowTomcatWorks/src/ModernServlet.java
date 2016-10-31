import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModernServlet extends HttpServlet
{
  public void init(ServletConfig paramServletConfig)
  {
    System.out.println("ModernServlet -- init");
  }

  public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    paramHttpServletResponse.setContentType("text/html");
    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
    localPrintWriter.println("<html>");
    localPrintWriter.println("<head>");
    localPrintWriter.println("<title>Modern Servlet</title>");
    localPrintWriter.println("</head>");
    localPrintWriter.println("<body>");

    localPrintWriter.println("<h2>Headers</h2");
    Enumeration localEnumeration = paramHttpServletRequest.getHeaderNames();
    while (localEnumeration.hasMoreElements()) {
      String localObject = (String)localEnumeration.nextElement();
      localPrintWriter.println("<br>" + localObject + " : " + paramHttpServletRequest.getHeader((String)localObject));
    }

    localPrintWriter.println("<br><h2>Method</h2");
    localPrintWriter.println("<br>" + paramHttpServletRequest.getMethod());

    localPrintWriter.println("<br><h2>Parameters</h2");
    Object localObject = paramHttpServletRequest.getParameterNames();
    while (((Enumeration)localObject).hasMoreElements()) {
      String str = (String)((Enumeration)localObject).nextElement();
      localPrintWriter.println("<br>" + str + " : " + paramHttpServletRequest.getParameter(str));
    }

    localPrintWriter.println("<br><h2>Query String</h2");
    localPrintWriter.println("<br>" + paramHttpServletRequest.getQueryString());

    localPrintWriter.println("<br><h2>Request URI</h2");
    localPrintWriter.println("<br>" + paramHttpServletRequest.getRequestURI());

    localPrintWriter.println("</body>");
    localPrintWriter.println("</html>");
  }
}