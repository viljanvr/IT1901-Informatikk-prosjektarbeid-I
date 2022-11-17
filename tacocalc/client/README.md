# Client

Client modulen inneholder kommunikasjonslaget mellom brukeren og REST API-et. Det er i denne modulen alt av requests til serveren sendes og mottas for å vises til brukeren. `RecipeCalcAccess` er et brukergrensesnitt som kan implementeres på to måter, slik at man enten kan sende requests remotely eller lokalt.

## Struktur

Main:

- `RecipeCalcAccess`-Grensesnitt
- `RemoteRecipeCalcAccess`-Klasse
