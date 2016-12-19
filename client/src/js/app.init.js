/*
Root Vue component.
Handles global application state.
All components must be registered before this point.
*/
new Vue({
    el: '#app',
    data: {
        /* Route currently selected by the user. */
        activeRoute: {}
    }
})