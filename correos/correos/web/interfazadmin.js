var contactos=new Array();
function dametodos(xmlcon){
    var prof,alu,i,j=0;
    prof=xmlcon.getElementsByTagName("profesor");
    alu=xmlcon.getElementsByTagName("alumno");
    for(i=0;i<alu.length;i++){
                contactos[j]=alu[i].getAttribute("email");
                j++;
                continue;
    }
    for(i=0;i<prof.length;i++){
            contactos[j]=prof[i].getAttribute("email");
            j++;
            continue;
    }
}
function recargaauto(quien){
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
                //alert(quien);
                dametodos(documentoxml);
            }
        }
}
function completa(quien){
    if(quien=="mod"){
     $(function() {
        recargaauto(quien);
        $( "#tagss" ).autocomplete({
          source: contactos
        });
      });   
    }
    if(quien=="eli"){
        $(function() {
        recargaauto(quien);
        $( "#tags" ).autocomplete({
          source: contactos
        });
      });
    }
}