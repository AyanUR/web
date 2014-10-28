var contactos=new Array(),email,usuario;
function dametodos(xmlcon,grupo){
    var prof,alu,i,j=0;
    prof=xmlcon.getElementsByTagName("profesor");
    alu=xmlcon.getElementsByTagName("alumno");
    for(i=0;i<alu.length;i++){
            if(usuario=="administrador"){
                contactos[j]=alu[i].getAttribute("email");
                j++;
                continue;
            }
            if(alu[i].getAttribute("grupo")==grupo){
                contactos[j]=alu[i].getAttribute("email");
                j++;
            }
    }
    for(i=0;i<prof.length;i++){
        if(usuario=="administrador"){
            contactos[j]=prof[i].getAttribute("email");
            j++;
            continue;
        }
        if(prof[i].getAttribute("grupo")==grupo){
            contactos[j]=prof[i].getAttribute("email");
            j++;
        }
    }
}
function recargaauto(){
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
                var documentoxml=peticionHttp.responseXML.documentElement;
                var i,nodo=documentoxml.getElementsByTagName(usuario);
                for(i=0;i<nodo.length;i++){
                    if(nodo[i].getAttribute("email") == email){
                        dametodos(documentoxml,nodo[i].getAttribute("grupo"));
                        return;
                    }
                }
            }
        }
}
function datos(){
    email=document.getElementById("de").value;
    usuario=document.getElementById("usu").value;
}
function completa(){
    $(function() {
        datos();
        recargaauto();
        $( "#tags" ).autocomplete({
          source: contactos
        });
      });
}