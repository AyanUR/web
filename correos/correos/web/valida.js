var usuario,email,contrasena,nombre;
function saludo(){ 
    if(usuario!="administrador"){
        document.getElementById("soloadmin").setAttribute("hidden","");
        document.getElementById("uladmin").setAttribute("hidden","");
    }
    document.getElementById("saludo").innerHTML="bienvenido "+usuario+" "+nombre;
    document.getElementById("envia").setAttribute("href","envia.html"+"?usuario="+usuario+"&email="+email+"&nombre="+nombre);
    document.getElementById("datosusuario").innerHTML=usuario+"|"+email+"|"+nombre;
}
function input(){
    document.getElementById("de").setAttribute("value",email);
    document.getElementById("de").readOnly=true;
    document.getElementById("usu").setAttribute("value",usuario);
    document.getElementById("nom").setAttribute("value",nombre);
}
function urladmin(){
    var url,tipo;
    url=location.href;
    tipo=url.substring(url.indexOf('=')+1);
    if(tipo=="agregar"){
        document.getElementById("eliminar").setAttribute("hidden","");
        document.getElementById("modificar").setAttribute("hidden","");
    }
    if(tipo=="eliminar"){
        document.getElementById("agregar").setAttribute("hidden","");        
        document.getElementById("modificar").setAttribute("hidden","");
    }
    if(tipo=="modificar"){
        document.getElementById("agregar").setAttribute("hidden","");  
        document.getElementById("eliminar").setAttribute("hidden","");
    }
    document.getElementById("selector").setAttribute("value",tipo);
}
function retrollamada(){
    var url=location.href;
    var limi=url.indexOf('?'),lims=url.indexOf('&');
    usuario=url.substring(limi,lims).substring(9);
    email=url.substring(lims,url.lastIndexOf('&')).substring(7);
    nombre=url.substring(url.lastIndexOf('&'),url.length).substring(8);
}
function parametros(){
    usuario=document.getElementById("usuario").value;
    email=document.getElementById("email").value;
    contrasena=document.getElementById("contrasena").value;
}
function creaEnlaceUsuario(){
    var a,href,texto;
    a=document.createElement("a");
    href=document.createAttribute("href");
    href.nodeValue="usuario.html?usuario="+usuario+"&email="+email+"&nombre="+nombre;   
    a.attributes.setNamedItem(href);
    texto=document.createTextNode("entrar");
    a.appendChild(texto);
    document.getElementById("entrar").appendChild(a);
}
function validaUsuario(){
        if(window.XMLHttpRequest){
            peticionHttp=new XMLHttpRequest();
        }
        else{
            if(window.ActiveXObject){
                peticionHttp=new ActiveXObject("Microsoft.XMLHTTP");
            }
        }
        peticionHttp.onreadystatechange=muestraContenido;
        peticionHttp.open("GET","contactos.xml",true);
        peticionHttp.send(null);
        function muestraContenido(){
            if(peticionHttp.readyState == 4&&peticionHttp.status == 200){
                parametros();
                var documentoxml=peticionHttp.responseXML.documentElement;
                var i,nodo=documentoxml.getElementsByTagName(usuario);
                for(i=0;i<nodo.length;i++){
                    if(nodo[i].getAttribute("email") == email&&contrasena ==nodo[i].getAttribute("contrasena")){
                        nombre=nodo[i].getAttribute("nombre");
                        creaEnlaceUsuario();    
                        return;
                    }
                }
                alert("no existe el "+usuario+" con email "+email+" y contrasena "+contrasena);
            }
        }
}