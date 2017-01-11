# JakSzybciejDojade

## Moduły
[O co w ogóle chodzi](http://eloquentjavascript.net/10_modules.html).

Nie chcemy mieć wszystkiego w global scope, 1) dla zasady, 2) żeby nie musieć aż tak pilnować unikalności nazw.
Design jest taki, że jest globalny obiekt `app`, który zbiera funkcjonalności, które potrzebne są w różnych miejscach,
np. `app.notification`, `app.geolocation`. 

Szablon jest mniej więcej taki:
```javascript
// thing.js
(function(app) {  // definiujemy funkcję, przyjmującą jeden parametr
   let thing = {};
   
   thing.public1 = public1;
   thing.public2 = publicVar;
   
   app.thing = thing;
   
   let privateVar = ...;
   let publicVar = ....;
   function public(...) {...}
   function private1(...) {...}
   function private2(...) {...}
   
})(app) // i od razu ją wywołujemy, przekazując globalny app
```

`app.thing` to interfejs modułu, cała reszta to wewnętrzna implementacja.

### Logowanie

`app.getLog(tag)` zwraca wersję `console.log` z automatycznymi prefixami `[yak-tag]`, żeby było widać skąd dany log pochodzi.
Jest ogólny loger `app.log` (prefix `[yak]`).
Użycie:
```javascript
// thing.js
...
let log = app.getLog("thing");
log.l("stuff"); // console.log("[yak-thing] stuff)
log("stuff");   // to samo co log.l
log.e("stuff"); // error
log.w("stuff"); // warning
...
```

## Komponenty

Na `#ui` jest stworzony główny komponent Vue (w `app.init.js`), któy trzyma globalny stan, na razie tylko wybraną trasę. Jeśli jakiś komponent potrzebuje się update'ować razem z tym globalnym stanem, powinien go zbindować przez `props`, wtedy zmiany ładnie się propagują w obie strony.

Przykładowy komponent z opisem: `components/alert.component.js`.

### Template'y
Template niestety musi być stringiem w obiekcie Vue. Jeśli jest dłuższy niż jedna linia, trzeba użyć backticków: \`\`, a nie ''.

### Debugowanie
Do Chrome polecam addona [Vue.js devtools](https://chrome.google.com/webstore/detail/vuejs-devtools/nhdogjmejiglipccpnnnanhbledajbpd), pozwala łatwo podejrzeć hierarchię komponentów + ich stan. Pozwala też podpiąć się do wybranego na liście komponentu z konsoli (`$vm0`).

<sup><sub><sup><sub>ciekawe, czy ktoś to w ogóle przeczyta :/</sub></sup></sub></sup>
