(function(app){

    Vue.component('yak-routes', {
        template:
        `<section class="connections">
			<div class="connectionListHeader">
			<span>wyjdź o</span>
			<span>Trasa</span>
			<span>na miejscu</span>	
			</div>
			<div class="listOfConnections">
				<div class="routes" v-for="route in routes">
					<span>{{ time(route.departure) }}</span>
					<span><ul class = "lines"> <li v-for="lines in route.route">{{lines.transport.details.line}} </li></ul></span>
					<span> {{ time(route.arrival) }}</span>
					<div class="connectionChanges">
						<div v-for="conChange in route.route">
							<div v-on:click="listRollout('busStops')">
								<span>{{ time(conChange.departure) }}</span>
								<span>{{ conChange.transport.details.line}}</span>
								<span>{{ conChange.stops[0].displayName}}</span>
								<div v-if="conChange.stops.length>2"id="busStops">
									<div v-for="i in conChange.stops.length-2"><span>{{ conChange.stops[i].displayName}}</span></div>
								</div>
								<span>{{ conChange.stops[conChange.stops.length-1].displayName}}</span>
								<span>{{ time(conChange.arrival) }}</span>
							</div>
						</div>
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
			time: function(moment){
				return moment.planned;
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
