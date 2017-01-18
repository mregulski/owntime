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
            app.hub.$on('routes-update', this.updateRoute)
        },
        methods: {
            log: app.getLog("root"),
            updateRoute: function(newRoutes) {
                this.log("updating active route:", newRoutes[0]);
                this.activeRoute = newRoutes[0];
                app.hub.$emit('routes-and-active-update', newRoutes, this.activeRoute);
            }
        }
    });
})(app)
