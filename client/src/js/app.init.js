(function(app) {

    log = app.getLog("root");

    /* used for event communication */


    /*
    Root Vue component.
    Handles global application state.
    All components must be registered before this point.
    */
    new Vue({
        el: '#app',
        data: {
            /* Route currently selected by the user. */
            activeRoute: {
                line: 123,
                direction: "Mordor",
                stop: "Hammertime!"
            },
        },
        created: function() {
            // update list of routes
            app.hub.$on('routes-update', this.updateRoute);
            // update the active route and notify the map component
            app.hub.$on('active-update', this.drawRoute);
        },
        methods: {
            log: app.getLog("root"),
            updateRoute: function(newRoutes) {
                this.log("updating active route:", newRoutes[0]);
                this.activeRoute = newRoutes[0];
                app.hub.$emit('routes-and-active-update', newRoutes, this.activeRoute);
            },
            drawRoute: function(newActiveRoute) {
                this.activeRoute = newActiveRoute;
                this.log("showing route...")
                app.showRoute(this.activeRoute);
            }
        }
    });
})(app)
