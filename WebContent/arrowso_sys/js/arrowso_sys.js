window.onload = function(){
	document.getElementById("clear-login-input-btn").addEventListener("click",function(){
		document.getElementById("login-user-name").value = ""
		document.getElementById("login-user-pwd").value = ""
	});
	
	document.getElementById("login-btn").addEventListener("click",function(){
		window.location.href = "index.html"
	});
}