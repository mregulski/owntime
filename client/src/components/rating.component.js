(function(app){

    Vue.component('yak-rating', {
        template:
        `<div>
		<div v-if="active" class="searchbox__form" style="height:230px">
		<h3>Pomóż nam ulapszać naszą aplikację</h3>
		<h5>Oceń zaproponowaną przez nas trasę </h5>
        <tr style="font-size:40px">
            <th style="color:grey">
				<td v-on:click.prevent="stars=1" v-bind:class="{ 'glowingStar':stars>=1}">
					\u22C6
				</td>
				<td v-on:click.prevent="stars=2" v-bind:class="{ 'glowingStar':stars>=2}">
					\u22C6
				</td>
				<td v-on:click.prevent="stars=3" v-bind:class="{ 'glowingStar':stars>=3}">
					\u22C6
				</td>
				<td v-on:click.prevent="stars=4" v-bind:class="{ 'glowingStar':stars>=4}">
					\u22C6
				</td>
				<td v-on:click.prevent="stars=5" v-bind:class="{ 'glowingStar':stars>=5}">
					\u22C6
				</td>
				<td>
				</td>
				<td>
                <button style="margin:10px" v-on:click.prevent="send()">&gt;</button>
				</td>
			</th>
		</tr>
				<form>
<textarea rows="4" cols="30" name="comment" form="usrform"  v-model="comment">
Napisz co o niej sądzisz</textarea>
				</form>
			</div></div>`    ,
        data: function() {
            return {
				stars: 0,
				active: false,
				comment: "Napisz co o niej sądzisz"
			};
		},
        created: function() {
			app.hub.$on('routes-and-active-update', this.setRoutes);
        },
        methods: {
            setRoutes: function(routes, activeRoute) {
				this.active = true;
            },
			send: function()
			{
				this.active = false;
				app.sendRequest.postRating(this.stars,this.comment);
			}
		}
	});
})(app)
