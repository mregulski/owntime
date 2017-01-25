(function(app){

    Vue.component('yak-routes', {
        template:
        `<section class="connections">
			<div class="connectionListHeader">
			<span>wyjdź o 	Trasa	na miejscu</span>
			</div>
			<div class="routes" v-for="route in routes">
				<div><span>{{ route.departure.planned }}</span></div>
				<div><span><ul class = "lines"> <li v-for="lines in route.route">{{lines.transport.line}} </li></ul></span></div>
				<div><span> {{ route.arrival.planned }}</span></div>
				<div v-for="conChange in route.route">
					<div v-on:click="listRollout('busStops')">
						<div><span>{{ conChange.departure.planned }}</span></div>
						<div><span>{{ conChange.transport.line}}</span></div>
						<div><span>{{ conChange.stops[0].displayName}}</span></div>
						<div id="busStops">
							<div v-for="i in conChange.stops.length-2"><span>{{ conChange.stops[i].displayName}}</span></div>
						</div>
						<div><span>{{ conChange.stops[conChange.stops.length-1].displayName}}</span></div>
						<div><span>{{ conChange.arrival.planned }}</span></div>
					</div>
				</div>
			</div>
		</section>`,
        props: [ 'activeRoute'],
        data: function() {
            return {
                routes: [],
                log: app.getLog('routes')
            };
        },
        created: function() {
			app.hub.$on('routes-and-active-update', this.setRoutes);
			app.hub.$on('change-active', this.changeActive);
        },
        methods: {
            setRoutes: function(routes, activeRoute) {
                this.routes = routes;
				// this.activeRoute = activeRoute;
				//this.log(this.activeRoute);
            },
			changeActive: function(activeRoute) {
				this.activeRoute = activeRoute.orginalObject;
				this.log("drawing route");
				app.hub.$emit('active-update', this.activeRoute);
			},
			listRollout: function(sDivId){
                var oDiv = document.getElementById(sDivId);
                oDiv.style.display = (oDiv.style.display == "none") ? "block" : "none";
            },
			log: app.getLog('routes')
        }
    });

})(app)
