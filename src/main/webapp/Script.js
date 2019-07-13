window.onload=function(){
    viewProfile();
}
function viewProfile(){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function(){
        if(xmlhttp.readyState == 4 && xmlhttp.responseText){
            let data = JSON.parse(xmlhttp.responseText);
            console.log(data);
            document.getElementById("employeeId").innerHTML = data.employeeId;
            document.getElementById("firstName").innerHTML = data.firstName;
            document.getElementById("lastName").innerHTML = data.lastName;
            document.getElementById("username").innerHTML = data.username;
        }
    }
    xmlhttp.open("GET", "getEmployee.do");
    xmlhttp.send();
}
