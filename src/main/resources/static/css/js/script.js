let dark=false;

document.getElementById("themeBtn").onclick=function(){

if(!dark){

document.body.style.background="#1c1c1c";

document.body.style.color="white";

this.innerHTML="Light Mode";

dark=true;

}
else{

document.body.style.background="#f4f6f9";

document.body.style.color="black";

this.innerHTML="Dark Mode";

dark=false;

}

}