;(function(app) {
    /* yak-route */
    Vue.component('yak-route', {
        template: `
        <div class="searchbox__message">
            <div v-if="routeSelected">
                <p>Linia <span class="chosen-connection">{{connection}}</span> w kierunku <span
                        class="chosen-destination">{{destination}}</span> o <span class="chosen-starting-time">{{startTime}}</span></p>
                <p>Do wyjazdu pozostało Ci</p>
                <p><span class="chosen-starting-time-left">{{timeLeft}}</span></p>
                <p>Na miejsce przyjedziesz najpóźniej o
                    <span class="chosen-arrival-time">{{arrivalTime}} {{arrivalTime}}</span>
                </p>
            </div>
        </div>
        `,
        props: [ 'route' ],
        data: function() { return {}},
        computed: {
            /**
             * Check if there is a route by checking times.
             */
            routeSelected: function() {
                this.log("routeSelected:",this.startTime && this.arrivalTime)
                return this.startTime && this.arrivalTime;
            },
            connection: function() {
                return _.get(this, 'route.route[0].transport.details.line', "");

            },
            destination: function() {
                return _.get(this, 'route.end.displayName', "");
            },
            startTime: function() {
                // make sure there IS a ctime to display
                let time = _.get(this, 'route.departure', "");
                if (time == "") return "";

                this.log("startTime:", time);
                return moment(time.planned).format("HH:mm");
                // return {};
            },
            arrivalTime: function() {
                let time = _.get(this, 'route.arrival', "");
                if (time == "") return "";
                this.log("arrivalTime:", time);
                return moment(time.predicted).format("HH:mm");

            },
            timeLeft: function() {
                // startTime = _.get(this, 'route.departure', null);
                startTime = moment().add(10, 'm');
                return startTime.fromNow();
            }

        },
        methods: {
            log: app.getLog('route')
        }
    });
})(app);