var peticionHttp,coincidencias=new Array(),actives;
function initXhr(){
    if(window.XMLHttpRequest){
        return (new XMLHttpRequest());
    }else{
        if(window.ActiveXObject){
            return (new ActiveXObject("Microsoft.XMLHTTP"));
        }
    }
}
function cargarContenido(url,metodo,funcion){
    peticionHttp=initXhr();
    if(peticionHttp){
        peticionHttp.onreadystatechange=funcion;
        peticionHttp.open(metodo,url,true);
        peticionHttp.send(null);
    }
}
function muestraContenido(){
    if(peticionHttp.readyState==4){
        if(peticionHttp.status==200){
            coincidencias=peticionHttp.responseText.split("|");
        }else{
            alert("peticion="+peticionHttp.status);
        }
    }
}
function completa(){
    atributo=document.getElementById("tags").getAttribute("name");
    patron=document.getElementById("tags").value;
    $(function() {
        cargarContenido("http://localhost:8080/pharmacy/Consulta?patron="+patron+"&atributo="+atributo,"GET",muestraContenido);
        $( "#tags" ).autocomplete({
          source: coincidencias
        });
      });
      //coincidencias.length=0;
}
//window.onload=descargarFile();
function actives(){
    i=1;
    numberActives=parseInt(document.getElementById("numberActives").value);
    /*while(document.getElementById("divActives").childNodes.length>3){
        document.getElementById("divActives").removeChild(document.getElementById("active"+(i++)));
    }*/
    i=0;
    while((i++)<numberActives){
        //document.getElementById("divActives").innerHTML+="<br>sal numero "+i+": ";
        tr=document.createElement("tr");
        tdName=document.createElement("td");
        tdDosis=document.createElement("td");
        inputName=document.createElement("input");
        inputName.setAttribute("type","text");
        //input.setAttribute("id","active"+i);
        inputName.setAttribute("name","active"+(i-1));
        tdName.appendChild(inputName);
       
        inputDosis=document.createElement("input");
        inputDosis.setAttribute("type","text");
        inputDosis.setAttribute("name","dosis"+(i-1));
        tdDosis.appendChild(inputDosis);
        tr.appendChild(tdName);
        tr.appendChild(tdDosis);
        document.getElementById("tableActives").appendChild(tr);
    }
}
