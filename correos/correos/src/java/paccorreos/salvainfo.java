package paccorreos;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class salvainfo {
    protected void chekconformed(String ruta,PrintWriter out)
    {
        try
        {
            SAXBuilder builder=new SAXBuilder();
            builder.build(ruta);
        }catch(Exception e)
        {out.println("<br>el documento no esta bien conformado<br>"+e.getMessage());out.close();}
    }
    protected void chekvalidation(String ruta,PrintWriter out)
    {
        try
        {
            SAXBuilder builder=new SAXBuilder();
            builder.setValidation(true);
            builder.build(ruta);
        }catch(Exception e)
        {out.println("<br>el documento no es valido<br>"+e.getMessage());out.close();}
    }
    protected void addmensaje(Element para,String msn,String asunto){
        GregorianCalendar hourfech=new GregorianCalendar();
        Element mensaje=new Element("mensaje");
        mensaje.setAttribute("fechahora",""+hourfech.get(Calendar.YEAR)+":"+(hourfech.get(Calendar.MONTH)+1)+":"+hourfech.get(Calendar.DAY_OF_MONTH)+"-"+hourfech.get(Calendar.HOUR)+":"+hourfech.get(Calendar.MINUTE)+":"+hourfech.get(Calendar.SECOND));
        mensaje.setAttribute("asunto",asunto);
        mensaje.setText(msn);
        para.addContent(mensaje);
    }
    protected void addpara(Element usuario,String dato[]){
        Element para=new Element("para");
        para.setAttribute("nombre",dato[6]);
        para.setAttribute("email",dato[1]);//email a quien se lo enviamos
        addmensaje(para,dato[3],dato[2]);
        usuario.addContent(para);
    }
    protected Document chekinfo(String ruta,String dato[],PrintWriter out)
    {
        int i,j;
        try
        {
            SAXBuilder builder=new SAXBuilder();
            Document doc=builder.build(ruta);
            Element raiz=doc.getRootElement();
            Element enviado=raiz.getChild(dato[7]);
            List para,usuario=enviado.getChildren(dato[5]);
            for(i=0;i<usuario.size();i++){
                if(((Element)usuario.get(i)).getAttributeValue("email").equals(dato[0])){
                    //de si entro se ignifica que existe el usuario
                    para=((Element)usuario.get(i)).getChildren("para");
                    for(j=0;j<para.size();j++){
                        if(((Element)para.get(j)).getAttributeValue("email").equals(dato[1])){
                            //para si entro se ignifica que exite el usuario y el para
                            addmensaje((Element)para.get(j),dato[3],dato[2]);//mensaje
                            return doc;
                        }
                    }
                    addpara((Element)usuario.get(i),dato);
                    return doc;
                }
            }
            //si no entro a nada se ignifica que no exite el usurio
            Element usu=new Element(dato[5]);
            usu.setAttribute("email",dato[0]);
            addpara(usu,dato);
            enviado.addContent(usu);
            return doc;
        }
        catch(Exception e)
        {out.println("<br>error al guardar el email :(<br>"+e.getMessage());out.close();}
        return null;
    }
    protected Document agrega(String ruta,String dato[],PrintWriter out){//ruta contactos
        try{
            SAXBuilder builder=new SAXBuilder();
            Document doc=builder.build(ruta);
            Element raiz=doc.getRootElement();//obtengo contactos
            Element nuevo=new Element(dato[4]);
            nuevo.setAttribute("nombre",dato[0]);
            nuevo.setAttribute("grupo",dato[1]);
            nuevo.setAttribute("email",dato[2]);
            nuevo.setAttribute("contrasena",dato[3]);
            raiz.addContent(nuevo);
            out.println("todo echo correctamente :D");
            return doc;
        }
        catch(Exception e)
        {out.println("<br>error al guardar agregar usuario :(<br>"+e.getMessage());out.close();}
        return null;
    }
    protected Document elimina(String ruta,String dato[],PrintWriter out){//ruta contactos
        int i;
        try{
            SAXBuilder builder=new SAXBuilder();
            Document doc=builder.build(ruta);
            Element aux,raiz=doc.getRootElement();//obtengo contactos
            List contactos=raiz.getChildren();
            out.println("antes de funcion"+dato[0]);
            for(i=0;i<contactos.size();i++){
                //out.println("<br>"+((Element)contactos.get(i)).getAttributeValue("nombre"));
                if(((Element)contactos.get(i)).getAttributeValue("email").equals(dato[6])){
                    aux=(Element)contactos.get(i);
                    raiz.removeContent(aux);
                    agrega(ruta,dato, out);
                    out.println("todo echo correctamente :D");
                    return doc;
                }
            }
        }
        catch(Exception e)
        {out.println("<br>error al eliminar<br>"+e.getMessage());out.close();}
        out.println("no existe el usuario con email"+dato[0]);
        return null;
    }
    protected void modifica(String ruta,String dato[],PrintWriter out){//ruta contactos
        int i;
        try{
            SAXBuilder builder=new SAXBuilder();
            Document doc=builder.build(ruta);
            Element aux,raiz=doc.getRootElement();//obtengo contactos
            List contactos=raiz.getChildren();
            for(i=0;i<contactos.size();i++){
                if(((Element)contactos.get(i)).getAttributeValue("email").equals(dato[7])){
                    aux=(Element)contactos.get(i);
                    out.println("<br><br>ponga el nuevo valor dentro de la caja de texto<br><br>");
                    out.println("<br>tipo usuario:   "+aux.getName()+"                       <input type=\"text\" name=\"par4\">");
                    out.println("<br>nombre:   "+aux.getAttributeValue("nombre")+"           <input type=\"text\" name=\"par0\">");
                    out.println("<br>grupo:   "+aux.getAttributeValue("grupo")+"             <input type=\"text\" name=\"par1\">");
                    out.println("<br>email:   "+aux.getAttributeValue("email")+"             <input type=\"text\" name=\"par2\">");
                    out.println("<br>contrasena:   "+aux.getAttributeValue("contrasena")+"   <input type=\"text\" name=\"par3\">");
                    out.println("<br><br>"+"<input type=\"submit\">");
                    return;
                }
            }
            out.println("no existe el usuario con email"+dato[5]);
        }
        catch(Exception e)
        {out.println("<br>error al modificar usuario<br>"+e.getMessage());out.close();}
        return;
    }
    protected Document modificafinal(String ruta,String dato[],PrintWriter out){//ruta contactos
        int i;
        try{
            SAXBuilder builder=new SAXBuilder();
            Document doc=builder.build(ruta);
            Element aux,raiz=doc.getRootElement();//obtengo contactos
            List contactos=raiz.getChildren();
            for(i=0;i<contactos.size();i++){
                if(((Element)contactos.get(i)).getAttributeValue("email").equals(dato[3])){
                    aux=(Element)contactos.get(i);
                    for(i=0;i<6;i++){
                        if(dato[i].equals("")){
                            out.println("<br>"+ i+"esta vasio");
                        }
                        else{
                            switch(i){
                                case 0:
                                    aux.setAttribute("nombre",dato[i]);
                                break;
                                case 1:
                                    aux.setAttribute("grupo",dato[i]);
                                break;
                                case 2:
                                    aux.setAttribute("email",dato[i]);
                                break;
                                case 3:
                                    aux.setAttribute("contrasena",dato[i]);
                                break;
                                default:
                                break;
                            }
                            
                        }
                    }
                    return doc;
                }
            }
        }
        catch(Exception e)
        {out.println("<br>error al modificar usuario 2<br>"+e.getMessage());out.close();}
        out.println("no existe el usuario con email"+dato[5]);
        return null;
    }    
    protected void main(String dato[],String ruta,PrintWriter out)
    {
        Document doc=null;
        chekconformed(ruta,out);
        chekvalidation(ruta,out);
        if(dato[5].equals("administrador")||dato[5].equals("alumno")||dato[5].equals("profesor"))//se ignifica que vaa guardar correo
            {doc=chekinfo(ruta,dato,out);}
        else{
            if(dato[5].equals("agregar"))//se ignifica que va a agregar
                {doc=agrega(ruta,dato,out);}
            if(dato[5].equals("eliminar"))//se ignifica que va a eliminar
                {doc=elimina(ruta, dato, out);}
            if(dato[5].equals("modificarfinal"))//se ignifica que va a eliminar
                {doc=modificafinal(ruta, dato, out);}
        }
        out.println("<br><a href=\"usuario.html?usuario=administrador&email=triple_aayani@hotmail.com&nombre=ayan\">regresar</a>");
        try
        {
            XMLOutputter xmlrec=new XMLOutputter(Format.getPrettyFormat());
            FileWriter writer = new FileWriter(ruta);
            xmlrec.output(doc,writer);
            writer.flush();
            writer.close();
        }
        catch(Exception e)
        {out.println("error al guardar registro"+e.getMessage());out.close();}
    }
}