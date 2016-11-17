#Format JSONa z trasą:
```
{
    ...,
    "result": {
        "start": <Point>,
        "end": <Point>,
        "departure": <AdjustedTime>,
        "arrival": <AdjustedTime>,
        "route": [<Node>]
    }
}
```

## `AdjustedTime`
Czas planowany zestawiony z przewidywanym czasem odjazdu/przyjazdu.
```
{
    "planned": "2016-11-17T17:08:36.136Z",
    "predicted": "2016-11-17T17:08:36.136Z"
}
```

* `planned`  czas dotarcia wg planu
* `predicted`  czas dotarcia wg magicznego algorytmu. Może być ten sam co `planned`.

Czasy tutaj są w formacie ISO 8601, ale jak wam bardzo nie pasuje to niech będzie inny standardowy, byle wszędzie ten sam.

## `Node`
Fragment trasy. Np. "z punktu A na piechotę do przystanku B", "z przystanku B linią X na przystanek C", itd.
```
{
    "stops": [<Point>],
    "transport": <Transport>,
    "departure": <AdjustedTime>,
    "arrival": <AdjustedTime>,
    "alternatives": [<Node>],
    "details": {},
}
```

* `stops` - lista przystanków na fragmencie trasy
* `transport` - środek transportu na tym fragmencie
* `departure` - czas odjazdu z punktu startowego fragmentu
* `arrival` - czas dotarcia do punktu końcowego
* `alternatives` - alternatywy dla danego fragmentu. Drugie podejście, to żeby result był tablicą możliwych tras, a nie pojedynczą trasą, trzecie - że istnieje jedna jedyna słuszna trasa :)
* `details` - opcjonalne dodatkowe inforamcje, np. cena biletu, info że jest zmieniona trasa, warning o kontrolerze na trasie, itp.

## `Point`
Przystanek lub punkt trasy
```
{
    "displayName": "Pl. Grunwaldzki",
    "coords": "51.111562, 17.060132",
    "id": "1"
    "transports": [<Transport>]
}
```
* `displayName` - nazwa punktu do wyświetlenia. Nazwa przystanku ("Pl. Grunwaldzki"), charakterystycznego miejsca ("PWr A-1"), jak się nie da to koordynaty GPS.
* `coords` - koordynaty GPS punktu
* `id` - id przystanku z naszej bazy, np. do ściągania rozkładów czy coś.
* `transports` - środki transportu dostępne z przystanku

## `Transport`
Środek transportu
```
{
    "type"  "autobus",
    "details": {
        "line": "136"
    }
}
```

* `type`  typ środka transportu: autobus, tramwaj, pieszo, rower, teleport, itd.
* `details`  worek na dodatkowe informacje. Co najmniej numer linii dla komunikacji miejskiej.