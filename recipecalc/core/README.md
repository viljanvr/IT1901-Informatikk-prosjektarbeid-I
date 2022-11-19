# Core

Denne modulen inneholder kjernelogikken til kalkulatoren. Det er her vi utfører skalering, endring av ingredienser og samling av oppskrifter (recipes). Alle de andre modulene er avhengig av denne, enten direkte eller indirekte via en annen modul. Vi finner her to mapper: `main` og `test`.

## Struktur

Main:

- **`Ingredient`-klasse**: Grunnobjektet som brukes. En ingrediens har flere attributter som kan endres på, men koden er bygd opp på en slik måte at man aldri direkte caller på et ingrediens-objekt, da det heller er `Recipe`-klassen som delegerer til ingrediens objektet.
- **`Recipe`-klasse**: Har et navn og en liste med Ingredienser. Gjennom denne kan man kalle på metoder som endrer på ingrediensene som ligger i oppskriften basert på navnet på ingrediensen.

Test:

- **`IngredientTest`-testklasse for Ingredient**
- **`RecipeTest`-testklasse for Recipe**
