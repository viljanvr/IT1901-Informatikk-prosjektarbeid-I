# Beskrivelse av applikasjon

<p>
    <figure align="center">
        <img src="../docs/release%201/resources/figma_prototype_1.png" alt="Mockup" width="600"/>
        <figcaption><i>Mockup av hvordan vi ser for oss at applikasjonen skal bli</i></figcaption>
    </figure>
</p>

Applikasjonen er en redigerbar taco-kalkulator. Når man åpner appen skal man kunne skrive inn antall personer som skal spise, og da vil mengden av hver ingrediens oppdateres. Samtidig fungerer applikasjonen som en handleliste, hvor man kan huke av ingrediense ettersom man har kjøpt de. Det være veldig lett å redigere skaleringen av ingrediensene i applikasjonen, hvis man for eksempel fant ut av at man fikk alt for mye kjøttdeig denne gangen. Til slutt skal det også være mulig å legge til nye ingredienser og fjerne ingredienser.

## Oppbygging av prosjektet

Prosjektet består av to moduler:
* `Core`: grunnleggende logikk og håndtering av data
* `FxUI`: JavaFX brukergrensesnitt

## Kjøring av kode
1. Bytt mappe til `tacocalc`
2. Kjør `mvn install`
3. Bytt mappe til `fxui`
3. Kjør `mvn javafx:run`

Alternativt kan man bruke en "one liner" hvis du er på rotnivå i kildekoden:
`cd tacocalc; mvn install; cd fxui; mvn javafx:run; cd ../..`

## Kjøring av tester
1. Bytt mappe til `tacocalc`
2. Kjør `mvn install`
3. Kjør `mvn clean test jacoco:report`

JaCoCo vil generere en mappe i hver target mappe. For å se rapportene for testdekningsgrad, åpne HTML filene `tacocalc/core/target/site/jacoco/index.html` og `tacocalc/fxui/target/site/jacoco/index.html`


## Dokumentasjon og brukerhistorier
Her finner du dokumentasjon og brukerhistoriene til applikasjonen:

**Release 1**
* [Dokumentasjon](/docs/release%201/dokumentasjon.md)
* [Brukerhistorier](/docs/release%201/brukerhistorier.md)

**Release 2**
* *Dokumentasjon (kommer senere)*
* *Brukerhistorier (kommer senere)*

**Release 3**
* *Dokumentasjon (kommer senere)*
* *Brukerhistorier (kommer senere)*
