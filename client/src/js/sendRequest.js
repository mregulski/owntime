(function(app) {
	let log = app.getLog("sendRequest");
    let sendRequest = {}

    app.sendRequest = function(addressURL, dataToSend){
    	return new Promise((resolve, reject) => {	
    		var jsonFromServer = $.ajax({
		  	url: addressURL,
		  	type: "get", 
			data: { dataToSend },
		});
		if(!jsonFromServer){
			reject(Error("Blad"));
		}
		else{	
			resolve(jsonFromServer);
		}
	});
    }

    app.route = function (addressURL,data){
    	return app.getData(addressURL,data).then(res =>{
    		console.log(res.result.route.stops)
    	}).catch(e =>{
    		console.log("err - getRoute", e)
    	});
    }

    app.stopsByName = function (addressURL, data){
    	return app.getData(addressURL,data).then(res =>{
    		var arr = res.result.route.stops;
    		var stops = new Array();
    		for(i = 0; i < arr.length; i++){
    			stops.push(arr[i].displayName);
    		}
    		console.log(stops)
    	}).catch(e =>{
    		console.log("err - getStopsByName", e)
    	});
    }

    app.getData = function (addressURL, dataToSend){
    	return app.sendRequest(addressURL, dataToSend);
    }
})(app);


    
