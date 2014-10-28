package paccorreos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class enviamail extends HttpServlet {
        protected void propiedadesmail(Properties promail,String de){
            String aux=de.substring(de.indexOf('@'));
            if(aux.substring(1,aux.indexOf('.')).equals("hotmail")){
                promail.setProperty("mail.smtp.host","smtp.live.com");
            }
            if(aux.substring(1,aux.indexOf('.')).equals("gmail")){
             /*Nombre del host de correo es smtp.gmail.com*/
            promail.setProperty("mail.smtp.host","smtp.gmail.com");
            }
            if(aux.substring(1,aux.indexOf('.')).equals("yahoo")){
                promail.setProperty("mail.smtp.host","smtp.mail.yahoo.com");
            }
        /*tls si esta disponible*/
        promail.setProperty("mail.smtp.starttls.enable","true");   
        /*puerto de email */
        promail.setProperty("mail.smtp.port","587");
        /*nombre del usuario*/
        promail.setProperty("mail.smtp.user",de);
        /*si requiere o no usuario y passwort para conectarse*/
        promail.setProperty("mail.smtp.auth","true");
    }
    protected void constructormail(MimeMessage builmsn,String de,String para,String asunto,String mensaje,PrintWriter out)
    {
        try
          {
              builmsn.setFrom(new InternetAddress(de));
              builmsn.addRecipient(Message.RecipientType.TO,new InternetAddress(para));
              /*
              Message.RecipientType.TO !Destinatario prinsipal del mensaje
              Message.RecipientType.CC !Destinatario al que se le envia copia del mensaje
              Message.RecipientType.BCC !Destinatario al que se le envia copia, pero sinque los demas destinatario puedan verlo
              */
              builmsn.setSubject(asunto);
              builmsn.setText(mensaje);
              /*
              message.setText(
            "Mensajito con Java Mail<br>" + "<b>de</b> los <i>buenos</i>." + "poque si", !mensaje con html 
            "ISO-8859-1", !juedo de carateres a utilizar
            "html"); !tipo de texto
              */
          }catch(Exception e)
          {out.println("<br>error al contruir el mail<br><br>"+e.getMessage());}
    }
    protected void main(String dato[],PrintWriter out)
    {
        /*java mail*/
        Properties promail=new Properties();
        propiedadesmail(promail,dato[0]);
        /*creamos una instancia de session */
        Session sesmail=Session.getDefaultInstance(promail);
        sesmail.setDebug(true);//obtener informacion por pantalla
        /*construir el mensaje*/
        MimeMessage builmsn=new MimeMessage(sesmail);
        constructormail(builmsn,dato[0],dato[1],dato[2],dato[3],out);
        try {          
            //for(i=0;i<5;i++)
              //  out.println("<br>"+dato[i]);
            Transport send=sesmail.getTransport("smtp");
            send.connect(dato[0],dato[4]);
            send.sendMessage(builmsn,builmsn.getAllRecipients());
            send.close();
        }catch(Exception a) 
        {
            out.println("<br>error al envia el mail<br><br>"+a.getMessage());
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String dato[]=new String[8],ruta="C:\\Users\\Erick\\Documents\\NetBeansProjects\\correos\\web\\registro.xml";
        int i;
        for(i=0;i<8;i++)
            dato[i]=request.getParameter("par"+i);
        HttpSession sesmain=request.getSession();
        sesmain.setAttribute("datos",dato);
        if(dato[7].equals("enviado")){
         main(dato,out);   
        }
        salvainfo xml=new salvainfo();
        xml.main(dato,ruta, out);
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet enviamail</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<br>mensaje envia con exito :D");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
}