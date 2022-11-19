# Data

Denne modulen behandler alt av lokal persistens. Vi lagrer tilstandene i applikasjonen som JSON-filer. Til dette bruker vi biblioteket Gson fra Google. Dette gjør det enkelt å både skrive og lese.

## Struktur

Main:

- **`TacocalcFileHandler`-klasse**: Klassen som gjør alt av lesing og skriving til fil lokalt. REST API-et delegerer til denne klassen for lagring og lesing.

Test:

- **`FilehandlingTest`-testklasse for TacocalcFilehandler**
