(function(app){
    log: app.getLog('V.alert');

    Vue.component('yak-alert', {
        template: '#yak-alert',
        /* function, not object, because it's a component.
        can't use arrow functions because they treat this differently */
        data: function() { return {
                minutes: 0,
                alertSet: false
            }},
        methods: {
            addAlert: function() {
                app.notification.setAlarm(moment().add(5, 'seconds'), app.state.getRoute());
                this.alertSet = true
            }
        }
    });
})(app)