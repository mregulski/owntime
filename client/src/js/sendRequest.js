(function(app) {
	let log = app.getLog("sendRequest");

    let addressURL = "http://localhost:3000/api";

    app.getData = function (dataToSend){
    	return app.sendRequest(app.dataTypeMode(dataToSend)).then;
    }

	app.dataTypeMode = function(dataToSend){
		var reg = /^([0-9]{1,3})\.([0-9]+)$/;
		var start, target, mode;
		start = dataToSend.start;
		target = dataToSend.target;

		if(!reg.test(start) && !reg.test(target)){
			mode = 1;
		}
		else if(reg.test(start) && reg.test(target)){
			mode = 4;
		}
		else if(!reg.test(start) && reg.test(target)){
			mode = 3;
		}
		else{
			mode = 2;
		}
		
		var data = new Array();
		data.push(mode);
		data.push(start);
		data.push(target);
		data.push(dataToSend.time);
		data.push(dataToSend.flag);
		return data;
	}

    app.sendRequest = function(dataToSend){
    	return new Promise((resolve, reject) => {	
    		var jsonFromServer = $.ajax({
		  		url: addressURL,
		  		type: "get", 
			  	data: { 
		  			mode: dataToSend[0], 
		  			start: dataToSend[1], 
		  			target: dataToSend[2],
		  			time: dataToSend[3],
		  			mode2: dataToSend[4]
			  	},
			});
			if(!jsonFromServer){
				reject(Error("Blad"));
			}
			else{	
				resolve(jsonFromServer);
			}
		});
    }

    app.route = function (data){
    	return app.getData(data).then(res =>{
    		var routs = new Array();
    		routs.push(res.result.route.stops);
    		var x = res.result.route.alternatives;
    		while(x){
    			routs.push(x.stops);
    			x = x.alternatives;
    		}
    		console.log(routs)
    	}).catch(e =>{
    		console.log("err - getRoute", e)
    	});
    }

    app.stopsByName = function (data){
    	return app.getData(data).then(res =>{
    		var arr, routeStops, stops; 
    		stops = new Array(); routeStops = new Array();
    		arr = res.result.route;
    		for(i = 0; i < arr.stops.length; i++){
	    		routeStops.push(arr.stops[i].displayName);
	    	}
    		stops.push(routeStops);
    		routeStops = [];
    		arr = arr.alternatives;
    		while(arr){
	    		for(i = 0; i < arr.stops.length; i++){
	    			routeStops.push(arr.stops[i].displayName);
	    		}
	    		stops.push(routeStops);
	    		arr = arr.alternatives;
	    		routeStops = [];
    		}
    		console.log(stops)
    	}).catch(e =>{
    		console.log("err - getStopsByName", e)
    	});
    }

    app.getStops = function (name){
    	return new Promise((resolve, reject) => {	
    		var stops = $.ajax({
		  		url: addressURL,
		  		type: "get", 
			  	data: name,
			});
			if(!stops){
				reject(Error("Blad"));
			}
			else{	
				resolve(stops);
			}
		}).then(res=>{
			//TODO
			data = new Array();
			for (i = 0; i < res.length; i++)
				data.push([i, res[i]])
			console.log(data)
		})
    }

})(app);
