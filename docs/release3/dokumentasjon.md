# Dokumentasjon

<p>
    <figure align="center">
        <img src="resources/ingredientMenu.png" alt="Mockup" width="600"/>
        <figcaption><i>Skjermbilde fra applikasjonen.</i></figcaption>
    </figure>
</p>

**Hello I am aspiring technology professional.**

## Struktur

Appen består av fem moduler, der hver samhandler med hverandre i henhold til pakkediagrammet under. Vi har også laget et klassediagram som beskriver hvordan noen av de viktigste klassene samhandler. Dette er eksemplifisert med et sekvensdiagram, som du også kan se under.

<br/>
<p>
    <figure align="center">
        <img src="resources/pakkediagram.png" alt="Pakkediagram" width="600"/>
        <figcaption><i>Pakke-diagrammet viser en oversikt over hvordan modulene og pakkene i applikasjonen henger sammen.</i></figcaption>
    </figure>
</p>

<br/>
<p>
    <figure align="center">
        <img src="resources/klassediagram.png" alt="Klassediagram" width="600"/>
        <figcaption><i>Klassediagram som viser de to viktigste core klassene
    </figure>
</p>

Vi har lagt beskrivelsen av strukturen inne i de forskjellige modulene i egne README.md filer i hver av modulene. Disse finner du her:

- [core](tacocalc\core\README.md)
- [data](tacocalc\data\README.md)
- [fxui](tacocalc\fxui\README.md)
- [restapi](tacocalc\restapi\README.md)

## Implementert

Følgende _features_ er implementert i denne versjonen av applikasjonen:

- Henting og opplasting av oppskrifter til en database via et REST API
- Nye klasser for å samhandle med klient
- Ny stil på UI
- Skalering av hele oppskrifter
- Mulighet til å både sette og endre avrunding av individuelle ingredienser
-

<p>
    <figure align="center">
        <img src="resources/recipieMenu.png" alt="Mockup" width="600"/>
        <figcaption><i>Bilde 1: Start-meny hvor man har oversikt over alle oppskrifter.</i></figcaption>
    </figure>
</p>
<p>
    <figure align="center">
        <img src="resources/ingredientMenu.png" alt="Mockup" width="600"/>
        <figcaption><i>Bilde 2: Menyen hvor man kan huke av ingredienser etterhvert som man har kjøpt de. Her kan man også legge til nye ingredienser og gå inn på en bestemt ingrediens å redigere navn og antall.</i></figcaption>
    </figure>
</p>
<p>
    <figure align="center">
        <img src="resources/ingredientEditMenu.png" alt="Mockup" width="600"/>
        <figcaption><i>Bilde 3: Meyen hvor man kan redigere navn, måleenhet og antall av enkelte ingredienser. Her kan man også slette ingrediensen.</i></figcaption>
    </figure>
</p>

## Ikke implementert

Ved neste iterasjon har vi planer om å utvide funksjonaliteten til appen ytterlige. Dette er de foreløpige ideene vi ser for oss å implementere:

- Skalering av hele _recipes_
- Skybasert deling av _repices_ samt _recipebooks_
- Sletting av _recipes_
- Mulighet til å forandre måleenhet til en ingrediens

## Arbeidsflyt

Vi har jobbet på individuelle branches, hovedsakelig delt opp i `feature/` eller `bug/` etterfulgt av en beskrivelse av hva branchen implementerer/fikser. Disse er laget basert på issues vi har på Gitlab. Istedenfor å jobbe i satte par har vi en løs arbeidsstruktur med fokus på å jobbe sammen på større ting, her ved flittig bruk av LiveShare funksjonen i VSCode. Master-branchen skal som tommelfingerregel alltid være en fungerende applikasjon, derav må en feature close et issue før den kan merges. `Bug` branches brukes for å fikse eventuelle feil som oppstår på master.

I tillegg til å assigne issues til enkeltpersoner på Gitlab har vi hatt flere ukentlige møter, både fysiske og digitale, der vi jobber sammen.

## Tester

Vi har laget tester til appen som har som formål å oppdage eventuelle feil i applikasjonen. Testene er laget for å kjøre med maven, og vi bruker jacoco for å generere en testrapport. Guide for hvordan man kan skrive ut en test-rapport finner du [her](/tacocalc/README.md#kjøring-av-tester). I tillegg har vi i denne iterasjonen begynt å benytte oss av en testrobot for brukergrensesnittet. Dette gjør at en lettere kan se hva og hvor testene eventuelt feiler, og enklere følge testene mens de utføres.

## Brukerhistorier

Vi har laget to nye brukerhistorier som du kan finne [her](/docs/release%202/brukerhistorier.md), i tillegg til de gamle fra release 1 som du finner [her](/docs/release%201/brukerhistorier.md).

## Mockup

I likhet med første iterasjon har vi benyttet oss av Figma for å lage nye _mockups_ av appen. Disse har fungert som en guide under utviklingen av appen. Prototypen i Figma er nå ganske lik den fungerende appen, med noen unntak. Det som er forskjellig er det vi ønsker å få på plass i neste iterasjon. I bilde 1 har vi en oversikt over alle oppskrifter i din _RecipeBook_. Her ligger både standard recipes hentet fra den universelle RecipeBook-en, samt dine egne recipes. Som en kan se i bilde 2 ønsker vi å få på plass skalering så fort som mulig i neste iterasjon.

<p>
    <figure align="center">
        <img src="resources/prototypeRecipieMenu.png" alt="Mockup" width="600"/>
        <figcaption><i>Bilde 1: Start-meny hvor man har oversikt over alle oppskrifter.</i></figcaption>
    </figure>
</p>
<br/>
<p>
    <figure align="center">
        <img src="resources/prototypeIngredientMenu.png" alt="Mockup" width="600"/>
        <figcaption><i>Bilde 2: Liste over alle ingredienser til en bestemt oppskrift. Antall personer kan forrandres for å oppdatere mengden ingredienser.</i></figcaption>
    </figure>
</p>
<br/>
<p>
    <figure align="center">
        <img src="resources/prototypeEditMode.png" alt="Mockup" width="600"/>
        <figcaption><i>Bilde 3: Ved å trykke på "Edit"-knappen (se bilde 2) så vil man gå inn i redigeringsmodus hvor man kan legge til nye ingredienser og gå inn på allerede eksisterende ingredienser for å redigere navn, mengde, etc.</i></figcaption>
    </figure>
</p>
<br/>
<p>
    <figure align="center">
        <img src="resources/prototypeIngredientEditMenu.png" alt="Mockup" width="600"/>
        <figcaption><i>Bilde 4: Når man trykker på en ingrediens mens man er i redigeringsmodus så åpnes dette vinduet, hvor man kan endre navn, måleenhet og mengde til en ingrediens. Her finnes også "delete"-knappen hvis man ønsker å fjerne ingrediensen.</i></figcaption>
    </figure>
</p>
