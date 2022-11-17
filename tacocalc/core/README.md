# Core

Denne modulen inneholder kjernelogikken til kalkulatoren. Det er her vi utfører skalering, endring av ingredienser og samling av oppskrifter (recipes). Alle de andre modulene er avhengig av denne, enten direkte eller indirekte via en annen modul.

## Struktur

Main:

- `Ingredient`-klasse
- `Recipe`-klasse
- `RecipeBook`-klasse

Test:

- `IngredientTest`-testklasse for Ingredient
- `RecipeTest`-testklasse for Recipe
- `RecipeBookTest`-testklasse for RecipeBook
