var usuario,email,emailpara,fh,enbor;
function modificainputs(elemtmensaje){
    //alert(email+"¡"+usuario+"¡"+emailpara+"¡"+fh);
    document.getElementById("de").setAttribute("value",email);
    document.getElementById("de").readOnly=true;
    document.getElementById("usu").setAttribute("value",usuario);
    document.getElementById("nom").setAttribute("value","adan");
    document.getElementById("tags").setAttribute("value",emailpara);
    document.getElementById("asunto").setAttribute("value",elemtmensaje.getAttribute("asunto"));
    document.getElementById("mensaje").value=elemtmensaje.textContent;
}
function buscamensaje(usuf){
    var para,mensaje,j,k;
    para=usuf.getElementsByTagName("para");
    for(j=0;j<para.length;j++){
        mensaje=para[j].getElementsByTagName("mensaje");
        for(k=0;k<mensaje.length;k++){
            if(para[j].getAttribute("email")==emailpara&&mensaje[k].getAttribute("fechahora")==fh){
                modificainputs(mensaje[k]);
            }
        }
    }
}
function buscausuario(doc){
    var i,usu;
    usu=doc[0].getElementsByTagName(usuario);
    for(i=0;i<usu.length;i++){
        if(usu[i].getAttribute("email")==email){
            buscamensaje(usu[i]);
            return;
        }
    }
}
function ajax(){
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
            if(enbor=="enviados"){
                buscausuario(peticionHttp.responseXML.documentElement.getElementsByTagName("enviado"));
                document.getElementById("pass").setAttribute("hidden","");
                document.getElementById("envia").setAttribute("hidden","");
                document.getElementById("limpia").setAttribute("hidden","");
            }
            if(enbor=="borradores"){
                buscausuario(peticionHttp.responseXML.documentElement.getElementsByTagName("borrador"));
                document.getElementById("contrasena").innerHTML="contrasena";
            }
        }
    }
}
function retrollamada(){  
    var aux,url=location.href;
    aux=url.split("&")
    usuario=aux[0].substring(aux[0].indexOf('=')+1);
    email=aux[1].substring(aux[1].indexOf('=')+1);
    emailpara=aux[2].substring(aux[2].indexOf('=')+1);
    fh=aux[3].substring(aux[3].indexOf('=')+1);
    enbor=aux[4].substring(aux[4].indexOf('=')+1);
    ajax();
}