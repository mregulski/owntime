(function(app){
    /* [yak-alert] */
    log: app.getLog('alert');

    Vue.component('yak-alert', {
        template: `
        <section class="alert">'
            <span>Do wyjścia pozostało {{minutes}} min</span>
            <input v-model="minutes" type="text">
            <button v-on:click="addAlert">Alert</button>
            <button v-on:click="removeAlert">Usuń alert</button>
        </section>
        `,
        props: [
            'activeRoute'
        ],
        /* [Note] data must be a function returning the actual data object, because it's a component. */
        /* [Note] Component's fields cannot be arrow functions because of this' behaviour. Use function() {} instead. */
        data: function() { return {
                minutes: 0,
                alert: 0
            }},
        methods: {
            /* Replace currently set alert with a new one */
            addAlert: function() {
                this.removeAlert();
                app.notification
                    .setAlarm(this.activeRoute.departure.planned.subtract(this.minutes, 'min'), this.activeRoute)
                    /* [Note] Promises need to use arrow functions if they
                        refer to the current object via `this`. */
                    .then((timeoutId) => {
                        log.d("new alert:", timeoutId);
                        log.d("this:",this);
                        this.alert = timeoutId;
                    });
            },
            removeAlert: function() {
                log.d("cancelling alert:", this.alert);
                clearTimeout(this.alert);
                this.alert = 0;
            },
        },
        computed: {
            /* [Note] default: getter only. */
            alertSet: function() {
                return this.alert != 0;
            },
            /* [Note] both getter and setter defined. */
            route: {
                set: function(value) {
                    this.removeAlert();
                    this.activeRoute = value;
                },
                get: function() {
                    return this.activeRoute;
                }
            }
        }
    });
})(app)