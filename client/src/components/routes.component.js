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
					<div class="route" v-on:click="changeActive(route)">
					<span class="departureTime">{{ plannedTime(route.departure) }}</span>
					<ul class = "lines"> <li v-for="lines in route.route">{{lines.transport.details.line}} </li></ul>
					<span class="arrivalTime"> {{ predictedTime(route.arrival) }}</span>
					</div>
					<div class="connectionChanges" v-if="route == activeRoute">
						<div v-for="conChange in route.route">
							<div class="onClickListRollOut" v-on:click="number=conChange.transport.details.line">
								<div class="col row">
									<span class="line">{{ conChange.transport.details.line}}</span>
								</div>
								<div class="lineTimes col row">
									<span class="departureTime">{{ plannedTime(conChange.departure) }}</span>
									<span class="arrivalTime">{{ predictedTime(conChange.arrival) }}</span>
								</div>
								<div class="busStops col row">
									<span class="stopFrom">{{ conChange.stops[0].displayName}}</span>
									<div class="hiddenStops" v-if="conChange.stops.length>2 && number == conChange.transport.details.line">
										<div v-for="i in conChange.stops.length-2"><span>{{ conChange.stops[i].displayName}}</span></div>
									</div>
									<span class="stopTo"> {{ conChange.stops[conChange.stops.length-1].displayName}}</span>
								</div>
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
				number: "",
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
			plannedTime: function(time) {
                this.log("plannedTime:", time);
                return moment(time.planned).format("HH:mm");
                // return {};
            },
            predictedTime: function(time) {
                this.log("predictedTime:", time);
                return moment(time.predicted).format("HH:mm");

            },
			changeActive: function(Route) {
				this.activeRoute = Route;
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
