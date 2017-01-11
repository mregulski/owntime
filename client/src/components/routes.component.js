(function(app){

    Vue.component('yak-routes', {
        template:
        `
        `,
        props: [ 'activeRoute'],
        data: function() {
            return {
                routes: [],
                log: app.getLog('routes')
            };
        },
        created: function() {
            app.hub.$on('routes-update', this.setRoutes);
        },
        methods: {
            setRoutes: function(routes) {
                this.routes = routes;
            }
        }
    });

})(app)