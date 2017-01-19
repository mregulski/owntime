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
                }.bind(this), 500);
            }, 500),

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
                            start: this.startPoint.query,
                            end: this.endPoint.query,
                            departure: {
                                planned: moment().add(10, 'min'),
                                predicted: "11:33",//moment().format(),
                            },
                            arrival: {
                                planned: "11:33",//moment().add(15, 'min').format(),
                                predicted: "11:33",//moment().add(20, 'min').format()
                            },
                            route: [
                                {
                                    stops: [
                                        {displayName: "a", id: 1, coords: "50.12312, 17.26137", transports: [{type: "autobus", line: "666"}]},
                                        {displayName: "b", id: 2, coords: "50.12301, 17.26130", transports: [{type: "autobus", line: "666"}]},
                                        {displayName: "c", id:3 , coords: "50.12287, 17.26120", transports: [{type: "autobus", line: "666"}]}],
                                    transport: {type: "autobus", line: "666"},
                                    departure: {
                                        planned: "11:33",//moment().format(),
                                        predicted: "11:33",//moment().format(),
                                    },
                                    arrival: {
                                        planned: "11:33",//moment().add(15, 'min').format(),
                                        predicted: "11:33",//moment().add(20, 'min').format()
                                    }
                                },
                            ]
                        },
                        {
                            start: this.startPoint.query,
                            end: this.endPoint.query,
                            departure: {
                                planned: "11:33",//moment().format(),
                                predicted: "11:33",//moment().format(),
                            },
                            arrival: {
                                planned: "11:33",//moment().add(15, 'min').format(),
                                predicted: "11:33",//moment().add(20, 'min').format()
                            },
                            route: [
                                {
                                    stops: [
                                        {displayName: "a", id: 1, coords: "50.12312, 17.26137", transports: [{type: "autobus", line: "666"}]},
                                        {displayName: "b", id: 2, coords: "50.12301, 17.26130", transports: [{type: "autobus", line: "666"}]},
                                        {displayName: "c", id:3 , coords: "50.12287, 17.26120", transports: [{type: "autobus", line: "666"}]}],
                                    transport: {type: "autobus", line: "666"},
                                    departure: {
                                        planned: "11:33",//moment().format(),
                                        predicted: "11:33",//moment().format(),
                                    },
                                    arrival: {
                                        planned: "11:33",//moment().add(15, 'min').format(),
                                        predicted: "11:33",//moment().add(20, 'min').format()
                                    }
                                },
                            ]
                        },
                        {
                            start: {
                                displayName: this.startPoint.query,
                                coords: "50.12312, 17.26137"
                            },
                            end: {
                                displayName: this.endPoint.query,
                                coords: "50.12200, 17.26320"
                            },
                            departure: {
                                planned: "11:33",//moment().format(),
                                predicted: "11:33",//moment().format(),
                            },
                            arrival: {
                                planned: "11:33",//moment().add(15, 'min').format(),
                                predicted: "11:33",//moment().add(20, 'min').format()
                            },
                            route: [
                                {
                                    stops: [
                                        {displayName: "a", id: 1, coords: "50.12312, 17.26137", transports: [{type: "autobus", line: "666"}]},
                                        {displayName: "b", id: 2, coords: "50.12301, 17.26130", transports: [{type: "autobus", line: "666"}]},
                                        {displayName: "c", id:3 , coords: "50.12287, 17.26120", transports: [{type: "autobus", line: "666"}]},
                                        {displayName: "d", id:4 , coords: "50.12387, 17.25120", transports: [{type: "autobus", line: "666"}]},
                                        {displayName: "e", id:5 , coords: "50.12487, 17.23120", transports: [{type: "autobus", line: "666"}]},
                                        {displayName: "f", id:6 , coords: "50.12200, 17.26320", transports: [{type: "autobus", line: "666"}]},
                                        ],
                                    transport: {type: "autobus", line: "666"},
                                    departure: {
                                        planned: "11:33",//moment().format(),
                                        predicted: "11:33",//moment().format(),
                                    },
                                    arrival: {
                                        planned: "11:33",//moment().add(15, 'min').format(),
                                        predicted: "11:33",//moment().add(20, 'min').format()
                                    }
                                },
                            ]
                        },
                    ]
                    app.hub.$emit('routes-update', newRoutes);
                }.bind(this), 500);
            }, 500)
        }
    });
})(app)
