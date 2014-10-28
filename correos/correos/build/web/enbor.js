var usuario,email,nombre,eb;//la persona que se logeo
function datos(){//nombre
    var aux=document.getElementById("datosusuario").textContent;
    usuario=aux.substring(0,aux.indexOf('|'));
    email=aux.substring((aux.indexOf('|')+1),(aux.lastIndexOf('|')));
}
function tabla(usuf){
    /*var l,k,para,mensaje,json={"dato":[[]]};nombre email 
    para=usuf.getElementsByTagName("para");
    for(l=0;l<para.length;l++){
        json.dato[l]={"nombre":para[l].getAttribute("nombre"),"email":para[l].getAttribute("email")};
        mensaje=para[0].getElementsByTagName("mensaje");
        for(k=0;k<mensaje.length;k++){
            json.dato[l][k]={"asunto":mensaje[k].getAttribute("asunto"),"fechahora"}
        }
    }*/
    var j,k,tr,td,a,href,texto,mensaje;
    var para=usuf.getElementsByTagName("para");
    for(j=0;j<para.length;j++){
        mensaje=para[j].getElementsByTagName("mensaje");
        for(k=0;k<mensaje.length;k++){
            tr=document.createElement("tr");
            td=document.createElement("td");
            a=document.createElement("a");
            href=document.createAttribute("href");
            href.nodeValue="verenvbor.html?usuario="+usuario+"&email="+email+"&para="+para[j].getAttribute("email")+"&fh="+mensaje[k].getAttribute("fechahora")+"&enbor="+eb;
            a.attributes.setNamedItem(href);
            texto=document.createTextNode("para: "+para[j].getAttribute("nombre")+" asunto: "+mensaje[k].getAttribute("asunto"));
            a.appendChild(texto);
            td.appendChild(a);
            tr.appendChild(td);
            document.getElementById("tabla").appendChild(tr);   
        }
    }
}
function busca(docregistro){
    var i,usu;
    usu=docregistro[0].getElementsByTagName(usuario);
    for(i=0;i<usu.length;i++){
        if(usu[i].getAttribute("email")==email){
            tabla(usu[i]);
            return;
        }
    }
}
function ajax(envobor){
    eb=envobor;
    //document.data.reset();
    if(window.XMLHttpRequest){
        peticionHttp=new XMLHttpRequest();
    }
    else{
        if(window.ActiveXObject){
            peticionHttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    peticionHttp.onreadystatechange=muestraContenido;
    peticionHttp.open("GET","registro.xml",true);
    peticionHttp.send(null);
    function muestraContenido(){
        if(peticionHttp.readyState==4 && peticionHttp.status==200){
            datos();
            if(eb=="enviados"){
                busca(peticionHttp.responseXML.documentElement.getElementsByTagName("enviado"));
            }
            if(eb=="borradores"){
                busca(peticionHttp.responseXML.documentElement.getElementsByTagName("borrador"));
            }
        }
    }
}