(function(app) {
    /* yak-search */
    log: app.getLog('search');

    Vue.component('yak-search', {
        mixins: [VueClickaway.mixin],
        template: `
        <div class="searchbox__form">
            <form id="find-connections">
                <input type="text" placeholder="Skąd chcesz jechać?" v-model="startPoint.query">
                <ul v-if="showStartPoints" class="searchbox__stoplist" v-on-clickaway="hideResults('startPoint')">
                    <li v-for="stop of startPoint.results"
                        v-on:click="setQuery('startPoint', stop.name)">
                        {{stop.name}}
                    </li>
                </ul>

                <input type="text" placeholder="Miejsce docelowe" v-model="endPoint.query">
                <ul class="searchbox__stoplist"
                    v-if="showEndPoints"
                    v-on-clickaway="hideResults('endPoint')">
                    <li v-for="stop of endPoint.results"
                        v-on:click="setQuery('endPoint', stop.name)">
                        {{stop.name}}
                    </li>
                </ul>

                <button v-on:click.prevent="getRoutes">&gt;</button>
            </form>
        </div>
        `,
        props: [ ],
        data: function() { return {
            startPoint: {
                query: "",
                dirty: false,
                fetching: false,
                results: [],
                show: false,
                set: false,
            },
            endPoint: {
                query: "",
                dirty: false,
                fetching: false,
                results: [],
                show: false,
                set: false,
            },
            fetchingRoute: false,
            log: app.getLog('search')
        }},
        computed: {
            showStartPoints: function() {
                return this.startPoint.show
                        && this.startPoint.query != ""
                        && this.startPoint.results != []
                        && !this.endPoint.show;
            },
            showEndPoints: function() {
                return this.endPoint.show
                        && this.endPoint.query != ""
                        && this.endPoint.results != []
                        && !this.startPoint.show;
            }
        },
        watch: {
            'startPoint.query': function() {
                if (this.startPoint.set) {
                    this.startPoint.set = false;
                    return;
                }
                this.endPoint.show = false;
                this.startPoint.dirty = true;
                this.getStops('startPoint');
            },
            'endPoint.query': function() {
                if (this.endPoint.set) {
                    this.endPoint.set = false;
                    return;
                }
                this.startPoint.show = false;
                this.endPoint.dirty = true;
                this.getStops('endPoint');
            }
        },
        methods: {
            /* load stops matching user's query */
            getStops: _.debounce(function(which) {
                if (! this[which].query) { return; }
                let query = this[which].query
                this.log.d("Fetching stops for " + which + ": '" + query + "'");
                this[which].fetching = true;
                app.sendRequest.getStops(query).then((stops) => {
                    this.log.d("Fetched stops for " + which + ": '" + query + "'");
                    this[which].dirty = false;
                    this[which].fetching = false;
                    this[which].show = true;
                    this[which].results = stops;
                });
            }, 200),

            hideResults: function(which) {
                if(which == 'startPoint' || which == 'endPoint') {
                    return function() {
                        this[which].show = false;
                    }.bind(this)
                }
                return function() {};
            },

            setQuery: function(which, newQuery) {
                this.log.d("Setting query for " + which + ": " + newQuery);
                this[which].query = newQuery;
                this[which].show = false;
                this[which].set = true;
            },

            /* search for routes from startPoint to endPoint */
            getRoutes: _.debounce(function () {
                if (! (this.startPoint.query && this.endPoint.query)) {
                    return;
                }
                this.isFetching = true;
                this.log.d("Fetching routes...");
                app.sendRequest.getRoute(this.startPoint.query,
                    this.endPoint.query,
                    moment().format()
                ).then(routes => {
                    this.log.d("Routes fetched.");
                    this.isCalculating = false;
                    this.isStartDirty = false;
                    this.isEndDirty = false;
                    this.log("got routes: ", routes);
                    app.hub.$emit('routes-update', routes.result);
                });
            }, 200)
        }
    });
})(app)
