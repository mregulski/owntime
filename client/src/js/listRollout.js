(function(app) {
	let log = app.getLog("listRollout");
	app.listRollout = function(sDivId){
                var oDiv = document.getElementById(sDivId);
                oDiv.style.display = (oDiv.style.display == "none") ? "block" : "none";
            }
})(app);