# Client

Client modulen inneholder kommunikasjonslaget mellom brukeren og REST API-et. Det er i denne modulen alt av requests til serveren sendes og mottas for å vises til brukeren. `RecipeCalcAccess` er et brukergrensesnitt som kan implementeres på to måter, slik at man enten kan sende requests remotely eller lokalt.

## Struktur

Main:

- **`RecipeCalcAccess`-grensesnitt**: Et grensesnitt med funksjoner som client-siden av applikasjonen bruker for å lagre data. Disse metodene kan implementeres på forskjellige måter. Vår implementasjon `RemoteRecipeCalcAccess` bruker et Rest API. Det hadde også vært mulig å lage en annen implementasjon, med det tenkte navnet `LocalRecipeCalcAccess`, som baserer seg på lokal fillagring i stedet
- **`RemoteRecipeCalcAccess`-klasse**: Klassen som sender forespørsler til serveren. Klientklassen blir kalt på fra UI-et, og lager deretter en request som den sender til serveren via REST API-et. Deretter mottar den resultatet av forespørselen og sender denne til brukeren.
