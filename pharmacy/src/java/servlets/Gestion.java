package servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mysql.Mysql;
public class Gestion extends HttpServlet {
    protected Mysql sql;
    final String userMysql="root";
    final String passwordMysql="1234";
    public void createTableActives(PrintWriter out){
        out.println("<table id=\"tableActives\" border=\"3\" cellspacing=\"11\" align=\"center\">");
        out.println("<tr><th colspan=\"2\">activos</th></tr>");
        out.println("<tr>");
        out.println("<th>nombre</th>");
        out.println("<th>miligramos/mililitros</th>");
        out.println("</tr>");
        out.println("</table>");
    }
    public void createTableSale(PrintWriter out){
        out.println("<table id=\"tableSale\" border=\"3\" cellspacing=\"11\" align=\"center\">");
        out.println("<tr><th colspan=\"8\">medicamentos</th></tr>");
        out.println("<tr>");
        out.println("<th>nombre</th>");
        out.println("<th>codigo</th>");
        out.println("<th>existencia</th>");
        out.println("<th>precio</th>");
        out.println("<th>descuento</th>");
        out.println("<th>total X pieza</th>");
        out.println("<th>cantidad</th>");
        out.println("<th>subtotal</th>");
        out.println("</tr>");
        out.println("</table>");
    }
    public void add(PrintWriter out) throws ClassNotFoundException, SQLException{
        int i,numberColumn;
        String atribute="";
        out.println("<br>ingres los campos para el nuevo medicamento");
        sql=new Mysql("localhost",userMysql,passwordMysql,"pharmacy");
        sql.consulta("select *from medicine where barcode='error'");
        sql.metadata=sql.response.getMetaData();
        numberColumn=sql.metadata.getColumnCount();
        for(i=1;i<=numberColumn;i++){
            atribute=sql.metadata.getColumnName(i);
            out.println("<br>"+atribute+"<input type=\"text\" name=\""+atribute+"\" />");
        }
        sql.desconectar();
        out.println("<br><div>");
        out.println("ingrese el numero de activos: <input id=\"numberActives\" type=\"text\" onkeyup=\"actives();\" />");
        createTableActives(out);
        out.println("</div>");
    }
    public void add(PrintWriter out,HttpServletRequest request) throws ClassNotFoundException, SQLException{
        int i=0,numberColumn;
        String query="insert into medicine value(",type,atribute;
        sql=new Mysql("localhost",userMysql,passwordMysql,"pharmacy");
        sql.consulta("select *from medicine where barcode='error'");
        sql.metadata=sql.response.getMetaData();
        numberColumn=sql.metadata.getColumnCount();
        for(i=1;i<=numberColumn;i++){
            type=sql.metadata.getColumnTypeName(i);
            atribute=sql.metadata.getColumnName(i);
            if(type.equals("INT")||type.equals("FLOAT"))
                query+=request.getParameter(atribute);
            else
                query+="'"+request.getParameter(atribute)+"'";
            if(i<numberColumn)
                query+=",";
        }
        query+=")";
        sql.update(query);
        for(i=0;request.getParameter("active"+i)!=null;i++){
            query="insert into active value(";
            query+="'"+request.getParameter("active"+i)+"',";//nombre activo
            query+="'"+request.getParameter("barcode")+"',";//barcode del medicamento
            query+=request.getParameter("dosis"+i)+")";
            sql.update(query);
        }
        sql.desconectar();
        out.println("operacion exitosa se añadido el medicamento :D");
    }
    public void remove(PrintWriter out){
        out.println("<form action=\"Consulta\" method=\"POST\">");
        out.println("<br>ingrese el codigo de barras del medicamento a eliminar");
        out.println("<div id=\"serch\">");
        out.println("<label id=\"sugerencias\" for=\"tags\"></label>");
        out.println("<input id=\"tags\" type=\"text\" name=\"barcode\" onkeyup=\"completa();\"/>");
        out.println("</div>");
        out.println("<input type=\"submit\" value=\"mostrar\" name=\"flat\"/>");
        out.println("</form>");
    }
    public void remove(PrintWriter out,HttpServletRequest request) throws ClassNotFoundException, SQLException{
        HttpSession gestion=request.getSession();
        String barcode=(String)gestion.getAttribute("barcode");
        sql=new Mysql("localhost","root","1234","pharmacy");
        sql.update("delete from medicine where barcode='"+barcode+"'");
        //sql.desconectar();
        out.println("se elimino correctamente ");
    }
    public void change(PrintWriter out){
        out.println("<div id=\"serch\">");
        out.println("<label id=\"sugerencias\" for=\"tags\"></label>");
        out.println("<input id=\"tags\" type=\"text\" name=\"barcode\" onkeyup=\"completa();\"/>");
        out.println("</div>");
    }
    public void change(PrintWriter out,HttpServletRequest request) throws ClassNotFoundException, SQLException{
;
    }
    public void sale(PrintWriter out){
        createTableSale(out);
        out.println("<div>cuenta Total: <input id=\"countTotal\" type=\"text\" name=\"countTotal\" readonly/></div>");
        out.println("<br>ingrese el codigo de barras del medicamento a vender");
        out.println("<div id=\"serch\">");
        out.println("<label id=\"sugerencias\" for=\"tags\"></label>");
        out.println("<input id=\"tags\" type=\"text\" name=\"barcode\" onkeyup=\"completa();\"/>");
        out.println("</div>");
        out.println("<input type=\"button\" onclick=\"addtoCar();\" value=\"agregar\" />");
        out.println("<input type=\"submit\" value=\"cerrar venta\">");
    }
    public void selector(String accion,PrintWriter out,HttpServletRequest request,boolean flat) throws ClassNotFoundException, SQLException{
        if(accion.equals("add")){
            if(flat)
                add(out);
            else
                add(out,request);
        }
        if(accion.equals("remove")){
            if(flat)
                remove(out);
            else
                remove(out,request);
        }
        if(accion.equals("change")){
            if(flat)
                change(out);
            else
                change(out,request);
        }
        if(accion.equals("sale")){
            out.println("<form action=\"Sale\" method=\"POST\">");
                sale(out);
            out.println("</form>");
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession gestion=request.getSession();
        String accion=(String)gestion.getAttribute("accion");
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Ejecuta Gestion</title>");
            out.println("</head>");
            out.println("<body style=\"color:brown;font-size:31px;\">");
            selector(accion,out,request,false);
            out.println("</body>");
            out.println("</html>");
        }catch(Exception e){out.println("error servlet Gestion post :(\n"+e.getMessage());} 
        finally{out.close();}
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String accion=request.getParameter("accion");
        HttpSession gestion=request.getSession();
        gestion.setAttribute("accion",accion);
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>gestion de medicamentos</title>");            
            out.println("<link rel=\"stylesheet\" href=\"http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css\">");
            out.println("<script src=\"http://code.jquery.com/jquery-1.9.1.js\"></script>");
            out.println("<script src=\"http://code.jquery.com/ui/1.10.3/jquery-ui.js\"></script>");
            out.println("<script type=\"text/javascript\" src=\"autocompletado.js\"></script>");
            out.println("<script type=\"text/javascript\" src=\"sale.js\"></script>");
            out.println("</head>");
            out.println("<body style=\"color:brown;font-size:31px;\">");
            if(!accion.equals("remove")&&!accion.equals("sale"))
                out.println("<form action=\"Gestion\" method=\"POST\">");
            selector(accion,out,request,true);
            if(!accion.equals("remove")&&!accion.equals("sale"))
                out.println("<br><br><br><input type=\"submit\" value=\""+accion+"\" /></form>");
            out.println("</body>");
            out.println("</html>");
        }catch(Exception e){out.println("error en servlet Gestion GET :( \n"+e.getMessage());}
        finally{out.close();}
    }
}
