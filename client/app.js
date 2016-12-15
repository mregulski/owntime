new Vue({


    el: '#przystanki',


    data: {

        przystanek: { name: '', description: '', date: '' },
        przystanki: [],
		przystankiDoWyswietlenia: []
    },

    ready: function() {
		//var d = new Date(year, month, day, hours, minutes, seconds, milliseconds); 
		//window.alert('test1');

        this.przykladowePrzystanki();
		this.$set('przystankiDoWyswietlenia', this.zwrocPrzystankiDoWyswietlenia());
		//zwrocPrzystankiDoWyswietlenia();
    },

    methods: {
        przykladowePrzystanki: function() {
            var przystanki = [{
                id: 0,
				linia: '103',
				czasStartowy: new Date(2016, 2, 3, 4, 5, 6, 7), 
				przystanekStartowy: 'PlacGrunwaldzki',
				czasKoncowy: new Date(2016, 2, 3, 4, 5, 6, 7), 
				przystanekKoncowy: 'PlacStrzegomski',
				przesiadki: [{
		            id: 0,
					linia: '103',
					czasStartowy: new Date(2016, 2, 3, 4, 5, 6, 7), 
					przystanekStartowy: 'PlacGrunwaldzki',
					czasKoncowy: new Date(2016, 2, 3, 4, 5, 6, 7), 
					przystanekKoncowy: 'PlacStrzegomski',
					czyPrzesiadka: true
		        },{
		            id: 0,
					linia: '103',
					czasStartowy: new Date(2016, 2, 3, 4, 5, 6, 7), 
					przystanekStartowy: 'PlacGrunwaldzki',
					czasKoncowy: new Date(2016, 2, 3, 4, 5, 6, 7), 
					przystanekKoncowy: 'PlacStrzegomski',
					czyPrzesiadka: true
				}],
				czyAktywny: true,
				czyPrzesiadka: false
				
            },{
                id: 1,
				linia: '103',
				czasStartowy: new Date(2016, 2, 3, 4, 5, 6, 7), 
				przystanekStartowy: 'PlacGrunwaldzki',
				czasKoncowy: new Date(2016, 2, 3, 4, 5, 6, 7), 
				przystanekKoncowy: 'PlacStrzegomski',
				przesiadki: [],
				czyAktywny: false,
				czyPrzesiadka: false
            }];
            this.$set('przystanki', przystanki);
        },
		zwrocPrzystankiDoWyswietlenia: function() {

			var tab = [];
			for(przystanek in this.przystanki)
			{
				tab.push(this.przystanki[przystanek]);
				if(this.przystanki[przystanek].czyAktywny)
				{
					for(przesiadka in this.przystanki[przystanek].przesiadki)
					{
						tab.push(this.przystanki[przystanek].przesiadki[przesiadka]);
					}
				}
			}
			return tab;
		},
        dodajPrzystanek: function() {
            if (this.przystanek.name) {
                this.przystanki.push(this.przystanek);
                this.przystanek = { name: '', description: '', date: '' };
            }
        },
		zmienAktywny: function(n) {
			this.przystanki[n].czyAktywny ^= true;
			this.$set('przystankiDoWyswietlenia', this.zwrocPrzystankiDoWyswietlenia());
		},
		test: function(n) {
			return true;
		}
    }
});
