# Beskrivelse av applikasjon

<p>
    <figure align="center">
        <img src="../docs/release%202/resources/prototypeIngredientMenu.png" alt="Mockup" width="600"/>
        <figcaption><i>Mockup av hvordan vi ser for oss at applikasjonen skal bli</i></figcaption>
    </figure>
</p>

Applikasjonen er en redigerbar oppskrifts-kalkulator. Når man åpner appen kan man velge en oppskrift og antall personer som skal spise. Da vil du få oppgitt riktig mengde av hver ingrediens som trengs i oppskriften. Samtidig fungerer applikasjonen som en handleliste, hvor man kan huke av ingrediense etterhvert som man kjøper de. Det skal være veldig lett å redigere skaleringen av ingrediensene i applikasjonen, hvis man for eksempel fant ut av at man fikk alt for mye kjøttdeig denne gangen. Det er også mulig å legge til nye ingredienser og fjerne ingredienser. I senere iterasjoner av applikasjonen skal det også være mulig å kunne lage nye oppskrifter, slette gamle oppskrifter og dele oppskrifter med andre personer.

## Oppbygging av prosjektet

Prosjektet består av to moduler:

- `Core`: grunnleggende logikk
- `Data`: håndtering av lesing og lagring av data
- `FxUI`: JavaFX brukergrensesnitt

## Kjøring av kode

1. Bytt mappe til `tacocalc`
2. Kjør `mvn clean install`
3. Bytt mappe til `fxui`
4. Kjør `mvn javafx:run` i tacocalc

Alternativt kan man bruke en "one liner" hvis du er på rotnivå i kildekoden:
`cd tacocalc; mvn clean install; cd fxui; mvn javafx:run; cd ../..`

## Lage en .exe (executable) for Windows

1. Installer .NET Framework 3.5 [link](https://www.microsoft.com/en-in/download/confirmation.aspx?id=22)
2. Installer Wix 3.11 [link](https://github.com/wixtoolset/wix3/releases)
3. Kjør `mvn javafx:jlink -f ./fxui/pom.xml` i /tacocalc
4. Kjør `mvn jpackage:jpackage -f ./fxui/pom.xml` i /tacocalc
5. Gå inn i fxui -> target -> dist. Her finner du en .exe fil.
6. Kjør denne, og følg oppsettet. Du skal nå ha en app ved navn `RecipeCalc.exe`

## Tester

Når du kjører `mvn clean install` så vil JaCoCo lage test rapporter. Hver modul har hver sin rapport som ligger i target mappen i modulen. For å se rapportene for testdekningsgrad, åpne HTML filene.

- [`tacocalc/core/target/site/jacoco/index.html`](/tacocalc/core/target/site/jacoco/)
- [`tacocalc/data/target/site/jacoco/index.html`](/tacocalc/data/target/site/jacoco/)
- [`tacocalc/fxui/target/site/jacoco/index.html`](/tacocalc/fxui/target/site/jacoco/).

## Dokumentasjon og brukerhistorier

Her finner du dokumentasjon og brukerhistoriene til applikasjonen:

**Release 1**

- [Dokumentasjon](/docs/release%201/dokumentasjon.md)
- [Brukerhistorier](/docs/release%201/brukerhistorier.md)

**Release 2**

- [Dokumentasjon](/docs/release%202/dokumentasjon.md)
- [Brukerhistorier](/docs/release%202/brukerhistorier.md)

**Release 3**

- _Dokumentasjon (kommer senere)_
- _Brukerhistorier (kommer senere)_