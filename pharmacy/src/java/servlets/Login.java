package servlets;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mysql.Mysql;
public class Login extends HttpServlet {
    protected void interfazEmpleado(PrintWriter out,String user,boolean flat){
        out.println("<center>Bienvenido "+user+" ¿Qué desea hacer? </center>");
        out.println("<br><a href=\"consulta.html\" target=\"blank\">Consulta de un medicamento</a>");
        out.println("<form action=\"Consulta\" method=\"POST\">");
        out.println("<input type=\"submit\" value=\"Lista completa de medicamentos\" />");
        out.println("</form>");
        
        out.println("<a href=\"http://localhost:8080/pharmacy/Gestion?accion=add\" target=\"blank\">Añadir medicamento</a>");
        out.println("<br><a href=\"http://localhost:8080/pharmacy/Gestion?accion=remove\" target=\"blank\">Eliminar medicamento</a>");
        out.println("<br><a href=\"http://localhost:8080/pharmacy/Gestion?accion=change\" target=\"blank\">Modificar medicamento</a>");
        if(flat)
            out.println("<br><a href=\"http://localhost:8080/pharmacy/Gestion?accion=sale\" target=\"blank\">Iniciar venta</a>");
    }
    protected void interfazJefe(PrintWriter out,String user){
        interfazEmpleado(out, user,false);
        
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession login=request.getSession();
        String user=request.getParameter("user"),password=request.getParameter("password");
        String type=request.getParameter("selector");
        Mysql sql;
        try {
            sql=new Mysql("localhost","root","1234","pharmacy");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Bienvenido</title>");            
            out.println("</head>");
            out.println("<body style=\"color:brown;font-size:31px; background:#E6E6E6;\">");
            if(type.equals("administrador")){
                sql.consulta("select name,contrasena from owner where name='"+user+"' and contrasena='"+password+"'");
                if(sql.response.next()){
                    login.setAttribute("user",user);
                    interfazJefe(out,user);
                }
                else
                    out.println("<br>administrador no valido");
            }
            if(type.equals("vendedor")){
                sql.consulta("select name,contrasena from employee where name='"+user+"' and contrasena='"+password+"'");
                if(sql.response.next()){
                    login.setAttribute("user",user);
                    interfazEmpleado(out,user,true);
                }
                else
                    out.println("<br>empleado no valido");
            }
            out.println("</body>");
            out.println("</html>");
        }catch(Exception e){out.println("error en el servlet Login :(\n"+e.getMessage());}
        finally{out.close();}
    }
}
