package servlets;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mysql.Mysql;
public class Consulta extends HttpServlet {
    String atributo;
    Mysql sql;
    final String userMysql="root";
    final String passwordMysql="1234";
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String matches="",patron=request.getParameter("patron");
        atributo=request.getParameter("atributo");
        try {
            sql=new Mysql("localhost",userMysql,passwordMysql,"pharmacy");
            sql.consulta("select "+atributo+" from medicine where "+atributo+" like '"+patron+"%'");
            while(sql.response.next())
                matches+=sql.response.getString(atributo)+"|";
            out.println(matches);
            sql.desconectar();
        }catch(Exception e){out.println("error en el servlet Consulta.java :(\n"+e.getMessage());} 
        finally{out.close();}
    }
    public void createTable(PrintWriter out){
        out.println("<table border=\"3\" cellspacing=\"11\" align=\"center\">");
        out.println("<tr><th colspan=\"8\">medicamentos</th></tr>");
        out.println("<tr>");
        out.println("<th>nombre</th>");
        out.println("<th>codigo</th>");
        out.println("<th>existencia</th>");
        out.println("<th>subtotal</th>");
        out.println("<th>descuento</th>");
        out.println("<th>total</th>");
        out.println("<th>indicaciones</th>");
        out.println("<th>docificacion</th>");
        out.println("</tr>");
    }
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        String medicamento=request.getParameter(atributo);
        String flat=request.getParameter("flat");
        float subtotal,total;
        int rebate;
        try{
            sql=new Mysql("localhost",userMysql,passwordMysql,"pharmacy");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>"+medicamento+"</title>");
            out.println("</head>");
            out.println("<body style=\"color:brown;font-size:31px;background:#E6E6E6;\">");
            if(medicamento!=null)
                sql.consulta("select * from medicine where "+atributo+"=\""+medicamento+"\"");
            else
                sql.consulta("select * from medicine");
            createTable(out);
            while(sql.response.next()){
                subtotal=sql.response.getFloat("subtotal");
                rebate=sql.response.getInt("rebate");
                total=(subtotal-((subtotal*rebate)/100));
                out.println("<tr>");
                out.println("<td>"+sql.response.getString("name")+"</td>");
                out.println("<td>"+sql.response.getString("barcode")+"</td>");
                out.println("<td>"+sql.response.getInt("parts")+"</td>");
                out.println("<td>"+subtotal+"</td>");
                out.println("<td>"+rebate+"</td>");
                out.println("<td>"+total+"</td>");
                out.println("<td>"+sql.response.getString("indications")+"</td>");
                out.println("<td>"+sql.response.getString("docificacion")+"</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            sql.desconectar();
            if(flat!=null){
                HttpSession consulta=request.getSession();
                consulta.setAttribute("barcode",medicamento);
                out.println("<form action=\"Gestion\" method=\"POST\">");
                out.println("<br>¿seguro que desea eliminarlo?");
                out.println("<input type=\"submit\" value=\"si\" />");
                out.println("</form>");
            }
            out.println("</body>");
            out.println("</html>");
        }catch(Exception e){out.println("error en post :( "+e.getMessage());}
        finally{out.close();}
    }
}