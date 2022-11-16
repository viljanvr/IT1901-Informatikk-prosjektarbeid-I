# Dokumentasjon

<p>
    <figure align="center">
        <img src="resources/ingredientMenu.png" alt="FORSIDE" width="600"/>
        <figcaption><i>Skjermbilde fra applikasjonen.</i></figcaption>
    </figure>
</p>

**Hello I am aspiring technology professional.**

## Struktur

Appen består av fem moduler, der hver samhandler med hverandre i henhold til pakkediagrammet under. Vi har også laget et klassediagram som beskriver hvordan noen av de viktigste klassene samhandler. Dette er eksemplifisert med et sekvensdiagram, som du også kan se under.

DENNE ER IKKE GJORT ENDA:

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
        <figcaption><i>Klassediagram som viser de to viktigste core klassene og deres dependencies.</i></figcaption>
    </figure>
</p>

Vi har lagt beskrivelsen av strukturen inne i de forskjellige modulene i egne README.md filer i hver av modulene. Disse finner du her:

- [client](../../tacocalc/client/README.md)
- [core](../../tacocalc/core/README.md)
- [data](../../tacocalc/data/README.md)
- [fxui](../../tacocalc/fxui/README.md)
- [restapi](../../tacocalc/restapi/README.md)

## Implementert

Følgende _features_ er implementert i denne versjonen av applikasjonen:

- Henting og opplasting av oppskrifter til en database via et REST API
- Nye klasser for å samhandle med klient
- Ny stil på UI
- Skalering av hele oppskrifter
- Mulighet til å både sette og endre avrunding av individuelle ingredienser

<p>
    <figure align="center">
        <img src="resources/OversiktRecipes.png" alt="OversiktRecipes" width="600"/>
        <figcaption><i>Bilde 1: Start-meny hvor man har oversikt over alle oppskrifter.</i></figcaption>
    </figure>
</p>
<br/>
<p>
    <figure align="center">
        <img src="resources/CreateRecipe.png" alt="Mockup" width="600"/>
        <figcaption><i>Bilde 2: Menyen som kommer opp når man trykker "Create Recipe". Her lager man en ny oppskrift som lagres, og man kan deretter gå inn og legge inn nye ingredienser.</i></figcaption>
    </figure>
</p>

<p>
    <figure align="center">
        <img src="resources/Forskalering.png" alt="Forskalering" width="600"/>
        <img src="resources/Etterskalering.png" alt="Etterskalering" width="600">
        <figcaption><i>Bilde 3: Eksempeloppskriften "Amogus" før og etter skalering.</i></figcaption>
    </figure>
</p>

<p>
    <figure align="center">
        <img src="resources/EditIngredient.png" alt="EditIngredient" width="600"/>
        <figcaption><i>Bilde 4: Dette er menyen man får opp når man trykker på "Edit" knappen. Her kan man skrive inn i feltene og trykke "Add Ingredient" for å legge til en ny ingrediens, eller redigere eksisterende (Se Bilde 5).</i></figcaption>
    </figure>
</p>

<p>
    <figure align="center">
        <img src="resources/EditIngredient.png" alt="EditIngredient" width="600"/>
        <figcaption><i>Bilde 5: Når man trykker pila til den valgte ingrediensen er dette menyen man får opp.</i></figcaption>
    </figure>
</p>

## Arbeidsflyt

I likhet med tidligere iterasjoner har vi lagd lokale som vi har delt opp i `feature/` og `bug/`. Nytt under denne iterasjonen er at vi i større grad har fokusert på mer omfattende commit-meldinger, mer beskrivende merge-requests og det å bruke kommentarer på disse. Dette førte for eksempel til at vi oppdaget feil i en merge request, da en annen reviewer fant en feil som den første revieweren ikke gjorde.

Vi har fortsatt med vår relativt løse arbeidsstruktur, med god bruk av LiveShare. `master`-branchen er som alltid _off limits_, og vi har hele tiden hatt som mål at `master` alltid skal kunne kjøre, noe implementasjonen av `pipelines` har sørget for at skjer. Vi begynte også å sette som regel at issues ikke skulle være noe mer generelle enn at en arbeider kunne åpne den og med en gang skjønne hvor i prosjektet problemet eksisterte. Slik ble samarbeidet forbedret, da hvem som helst kunne åpne en issue uten at en annen måtte spørre hva det var snakk om. Det ble også lettere for alle å følge med på hva som ble gjort av hvem når utenfor møtetider.

Når det gjelder møter har vi i større grad hatt digitale møter, med stor suksess, da dette senket terskelen for å ha møter betraktelig. Vi kunne dermed møtes oftere på flere "upassende" tidspunkt, da alle på gruppa har hatt veldig travle hverdager. Eksempelvis klarte vi å presse inn et møte selvom et medlem hadde vært på noe før møtetidspunktet, grunnet at personen skulle hjemom. Med fysisk oppmøte hadde dette vært umulig, men møtet viste seg å være både viktig og produktivt.

## Tester

Vi har laget tester til appen som har som formål å oppdage eventuelle feil i applikasjonen. Testene er laget for å kjøre med maven, og vi bruker jacoco for å generere en testrapport. Guide for hvordan man kan skrive ut en test-rapport finner du [her](/tacocalc/README.md#kjøring-av-tester). I tillegg har vi i denne iterasjonen begynt å benytte oss av en testrobot for brukergrensesnittet. Dette gjør at en lettere kan se hva og hvor testene eventuelt feiler, og enklere følge testene mens de utføres.

## Brukerhistorier

Vi valgte å skrive to nye historier til to brukere vi allerede har møtt i release1 ([her](../release1/brukerhistorier.md)) og release2 ([her](../release2/brukerhistorier.md)). De nye historiene finner du [her](brukerhistorier.md)
