(function(app) {
	let log = app.getLog("sendRequest");
    let sendRequest = {}
    
	document.addEventListener('DOMContentLoaded',()=>{
		document.getElementById("sendButton").addEventListener('click', (e) => { 
			e.preventDefault();
			var jsonFromServer = $.ajax({
				  url: "http://localhost:3000/api",
				  type: "get", 
				  data: {},
				});
				jsonFromServer.done(function() {
			    		console.log(jsonFromServer.responseText);
		  		});
		  		jsonFromServer.fail(function(msg) {
		  			alert("error  "+ msg);
		  		});
		 });
	});
	
})(app);