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
                setTimeout(function() {
                    this.log.d("Fetched stops for " + which + ": '" + query + "'");
                    this[which].dirty = false;
                    this[which].fetching = false;
                    this[which].show = true;
                    this[which].results = [ /* TODO: load from API */
                        {name: "Pl. Grunwaldzki", lines: [1,2,3,4,5,6,7,8,'D']},
                        {name: "Pl. Srzegomski", lines: [123,4,5,33,7,'A']},
                        {name: "Pl. Jakiśtam", lines: [-12, 3.14, 'e']}
                    ]
                }.bind(this), 200);
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
                setTimeout(function () {
                    this.log.d("Routes fetched.");
                    this.isCalculating = false;
                    this.isStartDirty = false;
                    this.isEndDirty = false;
                    newRoutes = [ /* TODO: load from API */
                        {
                            start: {
                                displayName: this.startPoint.query,
                                coords: "51.1012373, 17.10914251"
                            },
                            end: {
                                displayName: this.endPoint.query,
                                coords: "51.10555881, 17.0778882"
                            },
                            departure: {
                                planned: moment().format("HH:mm"),
                                predicted: moment().format("HH:mm"),
                            },
                            arrival: {
                                planned: moment().add(15, 'min').format("HH:mm"),
                                predicted: moment().add(20, 'min').format("HH:mm")
                            },
                            route: [
                                {
                                    stops: [
                                        {displayName: "BISKUPIN", id: 1, coords: "51.1012573, 17.10914151", transports: [{type: "autobus", line: "666"}]},
                                        {displayName: "Spółdzielcza", id: 2, coords: "51.10220572, 17.10232232", transports: [{type: "autobus", line: "666"}]},
                                        {displayName: "Piramowicza", id:3 , coords: "51.1031808, 17.09632744", transports: [{type: "autobus", line: "666"}]},
                                        {displayName: "Chełmońkiego", id:4 , coords: "51.10388356, 17.09071195", transports: [{type: "autobus", line: "666"}]},
                                        {displayName: "Tramwajowa", id:5 , coords: "51.10446682, 17.08466996", transports: [{type: "autobus", line: "666"}]},
                                        {displayName: "ZOO", id:6 , coords: "51.10556081, 17.0778982", transports: [{type: "autobus", line: "666"}]},
                                        ],
                                    transport: {type: "autobus", line: "666"},
                                    departure: {
                                        planned: moment().format("HH:mm"),
                                        predicted: moment().format("HH:mm"),
                                    },
                                    arrival: {
                                        planned: moment().add(15, 'min').format("HH:mm"),
                                        predicted: moment().add(20, 'min').format("HH:mm")
                                    }
                                },
                            ]
                        },
                    ]
                    app.hub.$emit('routes-update', newRoutes);
                    app.hub.$emit('active-update', newRoutes[0]);
                }.bind(this), 200);
            }, 200)
        }
    });
})(app)
