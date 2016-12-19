(function(app){
    log: app.getLog('V.alert');

    Vue.component('yak-alert', {
        template: '#yak-alert',
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