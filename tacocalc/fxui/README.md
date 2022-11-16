# FXUI

Brukergrensesnittet blir definert i denne modulen. Her har vi en kontroller for hvert vindu, i tillegg til en `App` klasse. Her delegeres alt av logikk til `core`-modulen. I tillegg har vi en mappe resources med det som er av styling.

## Struktur

Main:

- `AddRecipeController`-klasse
- `App`-klasse
- `AppController`-klasse
- `IngredientEditController`-klasse
- `RecipeBookController`-klasse

- resources:
  - `AddRecipePopup.fxml`-brukergrensesnitt
  - `PopupMenu.fxml`-brukergrensesnitt
  - `RecipeBook.fxml`-brukergrensesnitt
  - `ShoppingList.fxml`-brukergrensesnitt
  - `NoLabelCheckbox.css`-stylesheet
  - `Styling.css`-stylesheet

Test:

- `AppControllerTest`-testklasse for AppController
- `AppTest`-superklasse som de andre testklassene arver
- `IngredientEditControllerTest`-testklasse for IngredientEditController
- `RecipeBookControllerTest`-testklasse for RecipeBookController
