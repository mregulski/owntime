(function(app){

    Vue.component('yak-rating', {
        template:
        `<div v-if="active">
    <div class="modal-mask">
      <div class="modal-wrapper">
		<div class="ratingbox__form" id="ratingbox">
		<div style = "margin-top:20px;">
		<h3>Pomóż nam ulapszać naszą aplikację</h3>
		<h4 style="margin-top:10px;">Oceń zaproponowaną przez nas trasę </h4>
        <tr style="font-size:60px">
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
				<td  v-on:click.prevent="stars=5" v-bind:class="{ 'glowingStar':stars>=5}">
					\u22C6
				</td>
				<td>
				</td>
				<td>
                <button v-on:click.prevent="send()">oceń &gt;</button>
				</td>
			</th>
		</tr>
				<form>
					<textarea rows="3" cols="30" name="comment" form="usrform"  v-model="comment">
					</textarea>
				</form>
		</div>
			<a style = "float: right; margin:10px" v-on:click="close();">
				nie teraz
			</a>

		</div>
		</div>
			</div></div>`    ,
        data: function() {
            return {
				stars: 0,
				active: false,
				comment: "Napisz co o niej sądzisz",
				isMobile : {
					Android: function() {
						return navigator.userAgent.match(/Android/i);
					},
					BlackBerry: function() {
						return navigator.userAgent.match(/BlackBerry/i);
					},
					iOS: function() {
						return navigator.userAgent.match(/iPhone|iPad|iPod/i);
					},
					Opera: function() {
						return navigator.userAgent.match(/Opera Mini/i);
					},
					Windows: function() {
						return navigator.userAgent.match(/IEMobile/i);
					},
					any: function() {
						return (this.Android() || this.BlackBerry() || this.iOS() || this.Opera() || this.Windows());
					}
				},
				mobile: false,
				interval: null,
				alreadyPosted: false,
				chanceBeingSelected: 0.5,
				randomlySelected: false,
				activeRoute:null,
				coordX: null,
				coordY: null
			};
		},
        created: function() {
			app.hub.$on('routes-and-active-update', this.setRoutes);
			if( this.isMobile.any() )
			{
				this.mobile = true;
			}
        },
        methods: {
            setRoutes: function(routes, activeRoute) {
				if(this.alreadyPosted)
				{
				}
				else
				{
					if(this.mobile)
					{
						this.activeRoute = activeRoute;
						this.coordX = parseFloat(activeRoute.end.coords.split(", ")[0]);
						this.coordY = parseFloat(activeRoute.end.coords.split(", ")[1]);
						if(this.interval)
						{
							clearInterval(this.interval);
						}
						this.interval = setInterval(this.intervalFunction, 5000);
						
					}
					else
					{
						if(Math.random()<=this.chanceBeingSelected)
						{
							this.randomlySelected = true;
							
						}
						else
						{
							this.randomlySelected = false;
						}
						setTimeout(this.openIfSelected, 3000);
					}
				}
            },
			send: function()
			{
				this.close();
				app.sendRequest.postRating(this.stars,this.comment);
			},
			close: function()
			{
				this.alreadyPosted = true;
				if(this.interval)
				{
					clearInterval(this.interval);
				}
				this.active = false;
			},
			openIfSelected: function()
			{
				if(this.randomlySelected)
				{
					this.active = true;
				}
			},
			open: function()
			{
				this.active = true;
			},
			countDistance: function(cx, cy)
			{
				var R = 6371.0;
				var x = (this.coordsX-cx) * Math.cos((cy+coordsY)/2);
				var y = (coordsY-cy);
				var d = Math.sqrt(x*x + y*y) * R;
				return d;
			},
			intervalFunction: function()
			{
				var f = function(cx1, cy1, cx2, cy2)
				{
					var sx1 = cx1* Math.PI / 180;
					var sy1 = cy1* Math.PI / 180;
					var sx2 = cx2* Math.PI / 180;
					var sy2 = cy2* Math.PI / 180;
					var R = 6371.0;
					var x = (sx2-sx1) * Math.cos((sy1+sy2)/2);
					var y = (sy2-sy1);
					var d = Math.sqrt(x*x + y*y) * R;
					return d;
				}
				var coordX = null;
				var coordY = null;//jezeli odleglosc jest mniejsza od 0.1km pokazuje sie okno z ocena
				var coordX2 = this.coordX;
				var coordY2 = this.coordY;
				var open = this.open;
				var geoloc = app.geolocation.getCurrentPosition();
				geoloc.then(function(result){
				    coordX = result.coords.latitude;
				    coordY = result.coords.longitude;
					var s = f(coordX,coordY,coordX2,coordY2);
					if(s<0.1){open();} //jezeli odleglosc jest mniejsza od 0.1km pokazuje sie okno z ocena
				}, function(err){});
			}
		}
	});
})(app)
