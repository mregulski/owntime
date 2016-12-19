(function(app){
    log: app.getLog('alert');

    Vue.component('yak-alert', {
        template: `
        <section class="alert">'
            <input v-model="minutes" type="text">\
            <button v-on:click="addAlert">Alert</button>\
        </section>
        `,
        /* function, not object, because it's a component.
        can't use arrow functions because they treat this differently */
        props: [
            'activeRoute'
        ],
        data: function() { return {
                route: this.activeRoute,
                minutes: 0,
                alertSet: false
            }},
        methods: {
            addAlert: function() {
                app.notification.setAlarm(moment().add(5, 'seconds'), this.route);
                this.alertSet = true
            }
        }
    });
})(app)