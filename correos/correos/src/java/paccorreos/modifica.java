package paccorreos;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class modifica extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String dato[]=new String[6],ruta="C:\\Users\\Erick\\Documents\\NetBeansProjects\\correos\\web\\contactos.xml";;
        int i;
        for(i=0;i<5;i++)
            {dato[i]=request.getParameter("par"+i);}
        dato[5]="modificarfinal";
        salvainfo xml=new salvainfo();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>modifica</title>");            
            out.println("</head>");
            out.println("<body>");
            xml.main(dato,ruta,out);
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }
}
