# Dokumentasjon for REST API

## Struktur

Main:

- `RecipecalcApplication`-klasse
- `RecipecalcController`-klasse
- `RecipecalcService`-klasse

RecipeCalc APIet lar RecipeCalc-klienten hente, legge til og oppdatere oppskrifter på en RecipeCalc-server.

## Endepunkter

Følgende endepunkter finnes i APIet.

### Hent alle oppskrifter `GET` `/api/v1/recipes`

Henter en liste over alle oppskriftene som er lagret på serveren.

#### Returnerer

```json
[
  {
    "list": [
      {
        "name": "tomat",
        "perPersonAmount": 0.6,
        "measuringUnit": "stk",
        "roundUpTo": 0.0,
        "bought": false
      }
    ],
    "name": "recipe1",
    "numberOfPeople": 4
  },
  ...
]
```

### Hent oppskrift `GET` `/api/v1/recipes/{name}`

Henter oppskriften med `name` oppgitt i URLen.

#### Returnerer

```json
{
  "list": [
    {
      "name": "tomat",
      "perPersonAmount": 0.6,
      "measuringUnit": "stk",
      "roundUpTo": 0.0,
      "bought": false
    }
  ],
  "name": "recipe1",
  "numberOfPeople": 4
}
```

### Legg til oppskrift `POST` `/api/v1/recipes/recipe`

Legger til en oppskrift til serveren.

#### Forespørsel-body

```json
{
  "list": [
    {
      "name": "tomat",
      "perPersonAmount": 0.6,
      "measuringUnit": "stk",
      "roundUpTo": 0.0,
      "bought": false
    }
  ],
  "name": "recipe1",
  "numberOfPeople": 4
}
```

#### Returnerer

`true` dersom oppskriften ble lagt til, `false` ellers.

### Slett en oppskrift `DELETE` `/api/v1/recipes/{name}`

Sletter oppskriften med `name` oppgitt i URLen fra serveren.

#### Returnerer

`true` dersom oppskriften ble slettet, `false` ellers.

### Gi en oppskrift nytt navn `PUT` `/api/v1/recipes/{recipe}/name?newName={newRecipe}`

Endrer navnet på en oppskrift fra `recipe` til `newRecipe`. Parameteren aksepterer en `String`. Legg merke til at `newRecipe` er en påkrevd forespørselsparameter.

#### Returnerer

`true` dersom navneendringen ble lagret, `false` ellers.

### Legg til ingrediens `POST` `/api/v1/recipes/{recipe}/ingredient`

Legger til en ingrediens til oppskriften med navn `recipe` i URLen.

#### Forespørsel-body

```json
{
  "name": "tomat",
  "perPersonAmount": 0.6,
  "measuringUnit": "stk",
  "roundUpTo": 0.0,
  "bought": false
}
```

#### Returnerer

`true` dersom ingrediensen ble lagt til, `false` ellers.

### Gi en ingrediens nytt navn `PUT` `/api/v1/recipes/{recipe}/{ingredient}/name?newName={newIngredient}`

Endrer navnet på en ingrediens i en oppskrift med navnet `recipe`. Navnet blir endret fra `ingredient` til `newIngredient`. Parameteren aksepterer en `String`. Legg merke til at `newIngredient` er en påkrevd forespørselsparameter.

#### Returnerer

`true` dersom navneendringen ble lagret, `false` ellers.

### Endre mengden per person til en ingrediens `PUT` `/api/v1/recipes/{recipe}/{ingredient}/amount?amount=x`

Endrer mengden til en ingrediens med navnet `ingredient` i oppskrift `recipe`. Den nye mengden er blir `x` i forespørselsparameteren `amount=x`. Parameteren aksepterer en `double`. Legg merke til at `amount` er en påkrevd forespørselsparameter.

#### Returnerer

`true` dersom endringen ble lagret, `false` ellers.

### Endre opprundingsmengden til en ingrediens `PUT` `/api/v1/recipes/{recipe}/{ingredient}/roundUp?roundUp=x`

Endrer mengden man skal runde opp til dersom man ikke når en eksakt mengde. For eksempel kjøper man ikke 0.333 løk på butikken. Man endrer denne mengden til ingrediensen som heter `ingredient` i oppskriften som heter `recipe`. Den nye mengden blir `x` i forespørselsparameteren `roundUp=x`. Parameteren aksepterer en `double`. Legg merke til at `roundUp` er en påkrevd forespørselsparameter.

#### Returnerer

`true` dersom endringen ble lagret, `false` ellers.

### Endre måleenhet `PUT` `/api/v1/recipes/{recipe}/{ingredient}/unit?measuringUnit=mUnit`

Endrer måleenheten til ingrediensen som heter `ingredient` i oppskriften som heter `recipe`. Måleenheten blir satt til `mUnit` i forespørselsparameteren `measuringUnit=mUnit`. Parameteren aksepterer en `String`. Legg merke til at `measuringUnit` er en påkrevd forespørselsparameter.

#### Returnerer

`true` dersom endringen ble lagret, `false` ellers.

### Endre kjøpt-status `PUT` `/api/v1/recipes/{recipe}/{ingredient}/bought?bought=state`

Endrer kjøpt-statusen til ingrediensen med navn `ingredient` i oppskriften med navn `recipe`. Statusen blir satt til `state` i forespørselsparameteren. Parameteren aksepterer en `boolean`. Legg merke til at `bought` er en påkrevd forespørselsparameter.

#### Returnerer

`true` dersom endringen ble lagret, `false` ellers.

### Hent alle oppskriftsmaler `GET` `/api/v1/templates`

Henter en liste med alle oppskriftsmaler.

#### Returnerer

```json
[
  {
    "list": [
      {
        "name": "tomat",
        "perPersonAmount": 0.6,
        "measuringUnit": "stk",
        "roundUpTo": 0.0,
        "bought": false
      }
    ],
    "name": "recipe1",
    "numberOfPeople": 4
  },
  ...
]
```
