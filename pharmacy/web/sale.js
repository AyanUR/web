var peticionHttp,coincidencias=new Array();
function initXhr(){
    if(window.XMLHttpRequest){
        return (new XMLHttpRequest());
    }else{
        if(window.ActiveXObject){
            return (new ActiveXObject("Microsoft.XMLHTTP"));
        }
    }
}
function givemeMedicine(url,metodo,funcion){
    peticionHttp=initXhr();
    if(peticionHttp){
        peticionHttp.onreadystatechange=funcion;
        peticionHttp.open(metodo,url,true);
        peticionHttp.send(null);
    }
}
function recivMedicine(){
    if(peticionHttp.readyState==4){
        if(peticionHttp.status==200){
            coincidencias=peticionHttp.responseText.split("|");
            addCar();
        }else{
            alert("peticion="+peticionHttp.status);
        }
    }
}
var ctamedicine=0;
function calcSubtotal(idMedicine,cantidad){
    i=0,total=0;
    precio=parseFloat(document.getElementById(idMedicine).value);
    numeroProductos=parseInt(cantidad);
    existencias=parseInt(document.getElementById("existencia"+idMedicine.charAt(idMedicine.length-1)).value);
    if(numeroProductos>existencias){
        alert("no puedes vender "+numeroProductos+" solo cuentas con "+existencias);
        return;
    }
    if(cantidad==""||cantidad==" "||cantidad=="  ")
        numeroProductos=0;
    document.getElementById("totalxpiesas"+idMedicine.charAt(idMedicine.length-1)).value=(precio*numeroProductos).toFixed(2);
    while((i++)<ctamedicine)
        total+=parseFloat(document.getElementById("totalxpiesas"+(i-1)).value);
    document.getElementById("countTotal").value=total;
}
function addCar(){
    i=0;
    table=document.getElementById("tableSale");
    tr=document.createElement("tr");
    while((i++)<coincidencias.length){
        td=document.createElement("td");
        input=document.createElement("input");
        input.setAttribute("type","text");
        if(i==1)
            input.setAttribute("name","nombre"+ctamedicine);
        if(i==2)
            input.setAttribute("name","barcode"+ctamedicine);
        if(i==3){
            input.setAttribute("id","existencia"+ctamedicine);
            input.setAttribute("name","existencia"+ctamedicine);
        }
        if(i==4)
            input.setAttribute("name","subtotal"+ctamedicine);
        if(i==5)
            input.setAttribute("name","rebate"+ctamedicine);
        if(i==6){
            input.setAttribute("id","medicine"+(ctamedicine++));
            input.setAttribute("name","total"+ctamedicine);
        }
        input.setAttribute("value",coincidencias[i-1]);
        input.setAttribute("readonly","");
        td.appendChild(input);
        tr.appendChild(td);
    }
    td=document.createElement("td");
    input=document.createElement("input");
    input.setAttribute("type","text");
    input.setAttribute("name","medicine"+(ctamedicine-1));
    input.setAttribute("onkeyup","calcSubtotal(name,value);");
    td.appendChild(input);
    tr.appendChild(td);
    td=document.createElement("td");
    input=document.createElement("input");
    input.setAttribute("type","text");
    input.setAttribute("id","totalxpiesas"+(ctamedicine-1))
    input.setAttribute("name","totalxpiesas"+(ctamedicine-1));
    input.setAttribute("readonly","");
    td.appendChild(input);
    tr.appendChild(td);
    table.appendChild(tr);
}
function addtoCar(){
    barcode=document.getElementById("tags").value;
    givemeMedicine("http://localhost:8080/pharmacy/Sale?barcode="+barcode,"GET",recivMedicine);
}
//window.onload=descargarFile();