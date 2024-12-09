# PetPal
# Követelményspecifikáció

## 1. Áttekintés
Az alkalmazás célja, hogy segítse a háziállat-tulajdonosokat kedvencük életkorának és egészségi állapotának nyomon követésében. A PetPal Android platformokon érhető el, reszponzív felülettel, így különböző mobileszközökön is kényelmesen használható.

## 2. Jelenlegi helyzet
A háziállattulajdonosok számára nehézséget okoz kedvenceik életkorának és egészségi állapotának nyomon követése. A piacon jelenleg hiányoznak olyan alkalmazások, amelyek átfogó és könnyen használható megoldást kínálnak erre a problémára. A **PetPal** célja, hogy modern technológiával támogassa a felhasználókat, lehetővé téve számukra, hogy könnyedén hozzáférjenek a háziállatukra vonatkozó fontos információkhoz. Az alkalmazás lehetőséget biztosít a felhasználóknak, hogy nyomon követhessék háziállatuk fejlődését, valamint emlékeztetőket kapjanak a fontos eseményekre. A **PetPal** az első lépés a háziállatokkal kapcsolatos tudás és információk digitalizálása felé, amely a jövőbeni fejlesztések alapjául szolgálhat.

## 3. Vágyálom rendszer
Az alkalmazás célja, hogy egyszerű, felhasználóbarát eszközt biztosítson a következő funkciókkal:
- **Életciklusok**: A háziállatok fejlődési szakaszainak nyomon követése.
- **Mérföldkövek**: Születésnapi és örökbefogadási évfordulós emlékeztetők.
- **Növekedési Grafikon**: A háziállat fejlődésének vizuális nyomon követése (pl.: magasság, súly).
- **Időkapszula**: Digitális idővonal a háziállat életéről képekkel és emlékekkel.
- **Fajtaspecifikus tudnivalók**: Fajtákhoz kapcsolódó érdekességek és tippek.
- **Állatorvosi Adatok Kezelése**: Állatorvosi vizsgálatok és egészségügyi nyilvántartások kezelése.

## 4. Igényelt üzleti folyamatok

### Életciklusok
- A felhasználó láthatja a háziállat különböző életfázisait (pl. kölyök, felnőtt, idős).

### Mérföldkövek
- A felhasználó beállíthatja a háziállat születésnapját vagy örökbefogadási dátumát.
- Az alkalmazás értesítéseket küld a közelgő születésnapokról és évfordulókról.

### Növekedési Grafikon
- A felhasználó rendszeresen beírhatja a háziállat méreteit (pl.:súly, magasság).
- Az alkalmazás vizuális grafikont készít a háziállat növekedéséről, amelyen látható, hogyan fejlődik a háziállat az idő múlásával.

### Időkapszula
- A felhasználó képeket tölthet fel háziállatáról különböző életkorokban.
- Az alkalmazás idővonalon jeleníti meg a képeket és a kapcsolódó emlékeket, lehetőséget adva a visszatekintésre.
- A felhasználó jegyzeteket is fűzhet a képekhez.


### Állatorvosi Adatok Kezelése
- A felhasználó rögzítheti a háziállat állatorvosi látogatásait, oltásait és egyéb egészségügyi eseményeit.
- Az alkalmazás értesítéseket küld az esedékes állatorvosi ellenőrzésekről.
- Lehetőség van a fontos dokumentumok, például oltási kiskönyv digitális másolatainak feltöltésére.


## 5. A rendszerre vonatkozó szabályok
- **Reszponzív dizájn**: Az alkalmazás reszponzív felülettel rendelkezzen, amely mobiltelefonokon és tableteken is jól használható.
- **Felhasználóbarát felület**: Egyszerű, intuitív használatot biztosító dizájn.

## 6. Követelménylista
| Modul               | ID  | Név                                   | Verzió | Kifejtés |
|---------------------|-----|---------------------------------------|--------|----------|
| Felhasználói élmény | K01 | Könnyen kezelhető rendszer            | 1.0    | Könnyen kezelhető, felhasználóbarát rendszer. |
| Felület             | K02 | Android támogatás                    | 1.0    | Az alkalmazás működjön Android rendszeren. |
| Reszponzív felület  | K03 | Mobil és tablet optimalizálás        | 1.0    | A felület legyen reszponzív, kényelmesen használható mobil és tablet eszközökön. |
| Értesítések         | K05 | Emlékeztetők                         | 1.0    | Nyújtson értesítéseket és emlékeztetőket (születésnapok, évfordulók, állatorvosi ellenőrzések). |
| Digitális idővonal  | K06 | Képfeltöltés és idővonal            | 1.0    | Legyen lehetőség képek feltöltésére és digitális idővonal készítésére. |
| Növekedési diagram   | K07 | Grafikus fejlődésnyomon követés      | 1.0    | Tartalmazzon grafikus növekedési diagramot, amely a háziállat fizikai fejlődését mutatja. |