# FXUI

Brukergrensesnittet blir definert i denne modulen. Her har vi en kontroller for hvert vindu, i tillegg til en `App` klasse. Her delegeres alt av logikk til `core`-modulen. I tillegg har vi en mappe resources med det som er av grafiske brukergrensesnitt og styling. Det grafiske brukergrensesnittet er koblet opp mot en klientklasse som sender HTTP-forespørsler. Slik kommuniserer applikasjonen med et REST API.

## Struktur

Main:

- **`AddRecipeController`-klasse**: Controller for en popup som lar en legge til oppskrifter.
- **`App`-klasse**: Klasse for å starte applikasjonen.
- **`AppController`-klasse**: Controller for visning av ingrediensene i en oppskrift.
- **`IngredientEditController`-klasse**: Controller for en popup som lar en redigere egenskapene til en ingrediens.
- **`RecipeBookController`-klasse**: Controller for visningen som lar brukeren velge hvilken oppskrift en skal vise eller redigere.

- **Resources**: Her lagres alt av grafiske ressurser som brukes i applikasjonen.
  - **`AddRecipePopup.fxml`-brukergrensesnitt**
  - **`PopupMenu.fxml`-brukergrensesnitt**
  - **`RecipeBook.fxml`-brukergrensesnitt**
  - **`ShoppingList.fxml`-brukergrensesnitt**
  - **`Styling.css`-stylesheet**

Test:

- **`AppControllerTest`-testklasse for AppController**
- **`AppTest`-superklasse som de andre testklassene arver**
- **`IngredientEditControllerTest`-testklasse for IngredientEditController**
- **`RecipeBookControllerTest`-testklasse for RecipeBookController**
