package paccorreos;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class administra extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String dato[]=new String[8],ruta="C:\\Users\\Erick\\Documents\\NetBeansProjects\\correos\\web\\contactos.xml";
        int i;
        for(i=0;i<8;i++)
            {dato[i]=request.getParameter("par"+i);}
        HttpSession sesadmin=request.getSession();
        sesadmin.setAttribute("datos",dato);
        salvainfo xml=new salvainfo();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet administra</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<form action=\"modifica\" method=\"post\">");
            if(dato[5].equals("modificar")){
                xml.modifica(ruta, dato, out);
            }
            else{
                xml.main(dato,ruta, out);   
            }
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }
}