(function(app) {
	let log = app.getLog("sendRequest");
	let sendRequest = {};

    sendRequest.getStops = getStops;
    sendRequest.getRoute = getRoute;
    sendRequest.postRating = postRating;

    app.sendRequest = sendRequest;

//	let addressURL = "194.181.240.69:4567/";
    let addressURL = "http://localhost:3000/api/";

    function getStops(name){
    	return new Promise((resolve, reject) => {
    		var url = getURL("stops");
    		var stops = $.ajax({
		  		url: url,
		  		type: "get",
			  	data: name,
			});
			if(!stops){
				reject(Error("Blad"));
			}
			else{
				resolve(stops);
			}
		})
		// not really necessary
		// .then(res=>{
			// data = new Array();
			// for (i = 0; i < res.length; i++)
			// 	data.push([i, res[i]])
			// log("got data:", res)
			// log("passing on:", data)
			// return data;
		// })
    }

	/**
	 * startPoint, targetPoint - start and target of route, as stop names or GPS coords
	 */
    function getRoute(startPoint, targetPoint, time){
    	return sendInfo(dataTypeMode(startPoint, targetPoint, time));
    }

    function sendInfo(dataToSend){
    	return new Promise((resolve, reject) => {
    		var url = getURL("route");
    		var jsonFromServer = $.ajax({
		  		url: url,
		  		type: "get",
			  	data: {
		  			mode: dataToSend[0],
		  			start: dataToSend[1],
		  			target: dataToSend[2],
		  			time: dataToSend[3]
			  	},
			});
			if(!jsonFromServer){
				reject(Error("Blad"));
			}
			else{
				resolve(jsonFromServer);
			}
		}).then(res => {
			return res;
			// return route(res);
		}).catch(e =>{
    		log.e("getRoute", e)
    	});
    }

    function route(data){
    		var routs = new Array();
		for(i=0; i < data.result.route.length; i++)
			routs.push(data.result.route[i].stops);
    		console.log(routs)
    		return routs;
    }

    function dataTypeMode(start, target, time){
		var reg = /^([0-9]{1,3})\.([0-9]+)$/;

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
		data.push(time);
		return data;
	}

	function getURL(url){
		return addressURL + url;
	}

	function postRating(rating, comment){
		return new Promise((resolve, reject) => {
    		var url = getURL("rating");
    		var routeRating = $.ajax({
		  		url: url,
		  		type: "post",
			  	data: {
		  			rating : rating,
		  			comment : comment
			  	},
			});
			if(!routeRating){
				reject(Error("Blad"));
			}
			else{
				resolve(routeRating);
			}
		}).then(res => {
			console.log(res);
			return (Math.random() > 0.5 ? "Sukces" : "blad");
		}).catch(e =>{
    		console.log("err - postRating", e)
    	});
	}

})(app);
