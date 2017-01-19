(function(app){

    Vue.component('yak-routes', {
        template:
        `<section class="connections">
    <table>
        <thead>
        <tr>
                    <th>Linia</th>
                    <th colspan="5">Trasa</th>
                    <th>Przesiadki</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="route in routesToShow" v-on:click="changeActive(route);"  v-bind:class="{ 'active-sub-connection': route.isChange, 'active-connection': route.isActive }">
            <td><span class="connection">{{ route.line }}</span></td>
                    <td><span class="starting-time">{{ route.departure }}</span></td>
                    <td><span class="starting-point">{{ route.start }}</span></td>
                    <td></td>
                    <td><span class="arrival-time">{{ route.arrival }}</span></td>
                    <td><span class="arrival-point">{{ route.end }}</span></td>
            <td><span class="changes">{{ route.changes }}</span></td>
        </tr>
        </tbody>
    </table>
</section>`,
        props: [ 'activeRoute'],
        data: function() {
            return {
                routes: [],
				routesToShow: [],
                log: app.getLog('routes')
            };
        },
		computed: {
			routesToShow: function() {
				toShow = [];
				for (a = 0; a < this.routes.length; a++) {
					var route =
					{
						departure : this.routes[a].departure.planned,
						start : this.routes[a].start,
						arrival : this.routes[a].arrival.planned,
						end : this.routes[a].start,
						line : this.routes[a].route[0].transport.line,
						changes : this.routes[a].route.length,
						isActive : false,
						isChange : false,
						orginalObject : this.routes[a]
					};
					toShow.push(route);
					if(this.routes[a] == this.activeRoute)
					{
						toShow[a].isActive = true;
						for (b = 0; b < this.routes[a].route.length; b++)
						{
							var subroute =
							{
								departure : this.routes[a].route[b].departure.planned,
								start : this.routes[a].route[b].stops[0].displayName,
								arrival : this.routes[a].route[b].arrival.planned,
								end : this.routes[a].route[b].stops[this.routes[a].route[b].stops.length-1].displayName,
								line : this.routes[a].route[b].transport.line,
								changes : 0,
								isActive : false,
								isChange : true,
								orginalObject : this.routes[a]
							};
							toShow.push(subroute);
						}
					}
				}
				return toShow;
			}
		},
        created: function() {
			app.hub.$on('routes-and-active-update', this.setRoutes);
			app.hub.$on('change-active', this.changeActive);
        },
        methods: {
            setRoutes: function(routes, activeRoute) {
                this.routes = routes;
				this.activeRoute = activeRoute;
				//this.log(this.activeRoute);
            },
			changeActive: function(activeRoute) {
				this.activeRoute = activeRoute.orginalObject;
			}
        }
    });

})(app)
