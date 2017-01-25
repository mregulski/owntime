;(function(app) {
    /* yak-route */
    Vue.component('yak-route', {
        template: `
        <div class="searchbox__message">
                <p>Linia <span class="chosen-connection">{{connection}}</span> w kierunku <span
                        class="chosen-destination">{{destination}}</span> o <span class="chosen-starting-time">{{startTime.planned}}</span></p>
                <p>Do wyjazdu pozostało Ci</p>
                <p><span class="chosen-starting-time-left">{{timeLeft}}</span></p>
                <p>Na miejsce przyjedziesz najpóźniej o
                    <span class="chosen-arrival-time">{{arrivalTime.planned}} {{arrivalTime.estimated}}</span>
                </p>
            </div>
        `,
        props: [ 'route' ],
        data: function() { return {}},
        computed: {
            connection: function() {
                return _.get(this, 'route.route[0].transport.line', "");

            },
            destination: function() {
                return _.get(this, 'route.end', "");
            },
            startTime: function() {
                return _.get(this, 'route.departure', {planned: "plan", predicted: "estimate"});
            },
            arrivalTime: function() {
                return _.get(this, 'route.arrival', {planned: "plan", predicted: "estimate"});
            },
            timeLeft: function() {
                // startTime = _.get(this, 'route.departure', null);
                startTime = moment().add(10, 'm');
                return startTime.fromNow();
            }

        },
        methods: {
            log: app.getLog('yak-route')
        }
    });
})(app);