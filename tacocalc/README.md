# Beskrivelse av applikasjon

Applikasjonen er en redigerbar taco-kalkulator. Når man åpner appen skal man kunne skrive inn antall personer som skal spise, og da vil mengden av hver ingrediens oppdateres. Samtidig fungerer applikasjonen som en handleliste, hvor man kan huke av ingrediense ettersom man har kjøpt de. Det være veldig lett å redigere skaleringen av ingrediensene i applikasjonen, hvis man for eksempel fant ut av at man fikk alt for mye kjøttdeig denne gangen. Til slutt skal det også være mulig å legge til nye ingredienser og fjerne ingredienser.

## Oppbygging av prosjektet
(Ikke fiksa enda)

Prosjektet består av to moduler:
* `Core`: grunnleggende logikk og håndtering av data
* `UI`: JavaFX brukergrensesnitt

## Kjøring av kode
1. Bytt mappe til `tacocalc`
2. Kjør `mvn install`
3. Bytt mappe til `fxui`
3. Kjør `mvn javafx:run`

"One liner" hvis du er på rotnivå i kildekoden:
`cd tacocalc; mvn install; cd fxui; mvn javafx:run; cd ../..`


## Brukerhistorier
Du finner brukerhistorier til release 1 [her](/docs/release%201/brukerhistorier.md).
