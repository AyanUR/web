package servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mysql.Mysql;
public class Sale extends HttpServlet {
    final String userMysql="root";
    final String passwordMysql="1234";
    String atributo;
    Mysql sql;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String producto=request.getParameter("barcode"),matches="";
        Float subtotal,total;
        int rebate;
        try {
            sql=new Mysql("localhost",userMysql,passwordMysql,"pharmacy");
            sql.consulta("select * from medicine where barcode='"+producto+"'");
            while(sql.response.next()){
                subtotal=sql.response.getFloat("subtotal");
                rebate=sql.response.getInt("rebate");
                total=(subtotal-((subtotal*rebate)/100));
                matches+=sql.response.getString("name")+"|";
                matches+=sql.response.getString("barcode")+"|";
                matches+=sql.response.getInt("parts")+"|";
                matches+=subtotal+"|";
                matches+=rebate+"|";
                matches+=total;
            }
            out.println(matches);
            sql.desconectar();
        }catch(Exception e){out.println("error en el servlet Sale.java get :(\n"+e.getMessage());} 
        finally{out.close();}
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {//barcode,nombreempleado,numeropiesas,fecha
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            HttpSession venta=request.getSession();
            String barcode,user=(String)venta.getAttribute("user");
            int numberPiesas,i;
            Calendar c1 = Calendar.getInstance();
            String fecha=Integer.toString(c1.get(Calendar.YEAR))+":"+Integer.toString(c1.get(Calendar.MONTH))+":"+Integer.toString(c1.get(Calendar.DATE))+":"+c1.get(Calendar.HOUR_OF_DAY)+":"+c1.get(Calendar.MINUTE)+":"+c1.get(Calendar.SECOND);
                try {
                       out.println("<!DOCTYPE html>");
                       out.println("<html>");
                       out.println("<head>");
                       out.println("<title>Servlet adf</title>");            
                       out.println("</head>");
                       out.println("<body style=\"color:brown;font-size:31px;\">");
                       for(i=0;(barcode=request.getParameter("barcode"+i))!=null;i++){
                            sql=new Mysql("localhost",userMysql,passwordMysql,"pharmacy");
                            numberPiesas=Integer.parseInt(request.getParameter("medicine"+i));
                            String query="insert into sale value('"+barcode+"','"+user+"',"+numberPiesas+",'"+fecha+"') ";
                            sql.update(query);
                            out.println("<center>");
                            out.println("farmacia hilda<br>ticket de compra<br>"+fecha+"<br>atendido por :"+user);
                            out.println("<br><br>"+request.getParameter("nombre"+i));
                            out.println("&nbsp;"+barcode+"&nbsp;"+request.getParameter("subtotal"+i));
                            out.println("&nbsp;"+request.getParameter("rebate"+i));
                            out.println("&nbsp;"+numberPiesas+"&nbsp;"+request.getParameter("totalxpiesas"+i));
                            out.println("</center>");
                            query="update medicine set parts=parts-"+request.getParameter("existencia"+i)+" where barcode='"+barcode+"'";
                            sql.update(query);
                            sql.desconectar();
                       }
                       out.println("<br>total a pagar = $"+request.getParameter("countTotal"));
                       out.println("<br><input type=\"button\" value=\"imprimir\" />");
                       out.println("</body>");
                       out.println("</html>");
                }catch(Exception e){out.println("error en el servlet Sale.java post :(\n"+e.getMessage());} 
                finally{out.close();}
        }
}