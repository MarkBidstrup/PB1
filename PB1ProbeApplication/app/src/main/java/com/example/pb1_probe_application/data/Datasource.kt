package com.example.pb1_probe_application.data

import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.model.UserInfo

class Datasource() {

    // TODO: fix state variables
    fun loadProfilePatientInfo(): List<UserInfo> {
        return listOf<UserInfo>(
            UserInfo(R.string.navn,""),
            UserInfo(R.string.efternavn,""),
            UserInfo(R.string.koen,""),
            UserInfo(R.string.alder,""),
            UserInfo(R.string.vaegt,""),
            UserInfo(R.string.diagnose,""),
            UserInfo(R.string.email,""),
            UserInfo(R.string.telefon,""),
        )
    }

    // TODO: fix state variables
    fun loadProfileResearcherInfo(): List<UserInfo> {
        return listOf<UserInfo>(
            UserInfo(R.string.navn,""),
            UserInfo(R.string.efternavn,""),
            UserInfo(R.string.forskningsenhed,""),
            UserInfo(R.string.email,""),
        )
    }



//    fun loadDeltagerInfo(): String {
//        return "Forsøgets titel: Har stoffet cafestol fra kaffe diabetesforebyggende egenskaber?\\n\\n" +
//                "Vi vil spørge om du vil deltage i et videnskabeligt forsøg, der udføres på Steno Diabetes Center" +
//                "Aarhus ved Aarhus Universitetshospital. Læge Fredrik Brustad Mellbye er forsøgsansvarlig. Før du" +
//                "beslutter om du vil deltage, skal du forstå hvad forsøget indebærer og hvorfor vi gennemfører det" +
//                "– vil dig derfor bede dig om at læse denne deltagerinformation grundigt.\\n\\n" +
//                "Du vil blive inviteret til en samtale hvor denne information vil blive uddybet, og hvor du kan stille" +
//                "alle de spørgsmål, du måtte have til forsøget. Du er meget velkommen til at tage en bisidder med" +
//                "til samtalen, for eksempel et familiemedlem eller en ven. Hvis du ønsker at deltage, vil vi bede dig" +
//                "om at underskrive en samtykkeerklæring. Du kan få så lang betænkningstid som du selv ønsker før" +
//                "du beslutter dig for om du vil underskrive eller ej. Det er frivilligt at deltage, og selv efter at du har" +
//                "underskrevet kan du ombestemme dig og trække dit samtykke tilbage – helt uden grund eller" +
//                "yderligere konsekvenser.\\n\\n" +
//                "Personer, der drikker meget kaffe, har mindre risiko for at udvikle type-2 sukkersyge, uden at man" +
//                "præcis ved hvorfor. Vi har forsket i om cafestol, et stof som findes i kaffe og især i kaffegrums, har" +
//                "sukkersygeforebyggende effekter. Det har vi undersøgt ved hjælp af celler i laboratoriet og i" +
//                "dyreforsøg. Resultaterne af disse forsøg er positive og vi ønsker derfor at undersøge hvordan" +
//                "cafestol påvirker sukkeromsætningen i hos mennesker, og om det måske i fremtiden kan bruges til" +
//                "at forebygge sukkersyge. Vi vil både undersøge cafestols umiddelbare effekter (akut-studie) og" +
//                "effekterne af 3 måneders indtagelse (langtidsstudie). Du må selv bestemme om du vil deltage i" +
//                "akut-studiet, langtidsstudiet eller begge studier. Cafestol udvindes fra kaffegrums, og den dosis vi" +
//                "vil undersøge, er ganske lille – svarende til indholdet af cafestol i 1-4 kopper kogekaffe. Der vil" +
//                "være 15 deltagere i akut-studiet og 40 deltagere i langtidsstudiet.\\n\\n" +
//                "Akutstudiet:\\n" +
//                "Her vil du som deltager gennemgå følgende:\\n" +
//                "1. Informationssamtale\\n" +
//                "2. Afgivelse af samtykke og efterfølgende screening (taljemål, vægt og blodprøve)\\n" +
//                "3. Tre forsøgsdage hvor du indtager en pille (cafestol eller placebo) samt 75 g sukker opløst i" +
//                "vand. Femten minutter inden, samtidigt og 15, 30, 60, 90 og 120 minutter efter indtagelse" +
//                "af pille og sukker måler vi med blodprøver hvordan din krop håndterer sukkerdosen. Der vil" +
//                "gå 1 uge mellem de 3 forsøgsdage, og du må ikke drikke kaffe, the eller energidrik i 2 dage" +
//                "op til, eller på selve, undersøgelsesdagen. Efter de 3 besøg har du været igennem to" +
//                "forskellige cafestoldoser (6 og 12mg cafestol) og en placebopille. Hverken du eller vi ved" +
//                "hvilken pille du har fået på hvilken dag, før hele forsøget er færdigt. Undersøgelsen vil" +
//                "fortælle os om cafestol forbedrer kroppens akutte håndtering af sukker.\\n\\n" +
//                "Langtidsstudiet:\\n" +
//                "Her vil du som deltager gennemgå følgende:\\n" +
//                "1. Informationssamtale\\n" +
//                "2. Afgivelse af samtykke og efterfølgende screening (taljemål, vægt og blodprøve)\\n" +
//                "3. En uges automatisk kontinuerlig blodsukkermåling via en sensor på din overarm\\n" +
//                "4. Første forsøgsdag (ved studiets start) bestående af følgende undersøgelser:\\n" +
//                "a. Måltidstest – du møder om morgenen fastende fra midnat, og får serveret" +
//                "morgenmad hos os bestående af brød, ost og juice. Vi tager blodprøver før måltidet" +
//                "begynder og efter 15, 30, 60, 90, 120, 180 og 240 minutter, for at undersøge" +
//                "hvordan din krop omsætter et måltid. Undervejs spørger vi dig hvor sulten du er, og" +
//                "vi undersøger din fedt- og sukkeromsætning indirekte ved at måle på din" +
//                "udåndingsluft.\\n" +
//                "b. MR-scanning – med denne kan vi kortlægge din fedtfordeling, og her er vi særlig" +
//                "interesseret i at måle hvor meget fedt der er i leveren. Undersøgelsen tager ca 60" +
//                "minutter og er ufarlig, men du må ikke have metal indopereret i kroppen." +
//                "Scanneren er udstyret med mikrofon og højtaler så vi kan tale sammen undervejs," +
//                "og du kan til enhver tid afbryde undersøgelsen.\\n" +
//                "c. Urin og afføringsprøve\\n" +
//                "5. Anden forsøgsdag (ved studiets start) bestående af følgende undersøgelser:\\n" +
//                "a. Insulin suppressionstest – du møder om morgenen fastende fra midnat, hvor vi" +
//                "anlægger et drop og tilfører sukker, insulin og et hormon (octreotid) igennem" +
//                "droppet i tre timer. Insulin styrer blodsukkerniveaet i kroppen og sørger for at du" +
//                "optager sukker fra blodet. Octreotid gør at du midlertidigt ophører med at lave dit" +
//                "eget insulin, så vi ved præcis hvor meget insulin din krop har at gøre godt med." +
//                "Undervejs i forsøget holder vi øje med dit blodsukker og efter de tre timer ved vi" +
//                "hvor følsom du er for insulin.\\n" +
//                "b. Blodtryk - Du får et blodtryksapparat med hjem, som skal sidde på i et døgn.\\n" +
//                "6. Kontrolblodprøve efter 6 uger\\n" +
//                "7. En uges automatisk kontinuerlig blodsukkermåling via en sensor på din overarm\\n" +
//                "8. Tredje forsøgsdag (ved studiets slut) bestående af følgende undersøgelser:\\n" +
//                "a. Måltidstest og mål af forbrændning i udåndningsluft (som ved første forsøgsdag)\\n" +
//                "b. MR-Scanning (som ved første forsøgsdag)\\n" +
//                "c. Urin og afføringsprøve\\n" +
//                "9. Fjerde forsøgsdag (ved studiets slut) bestående af følgende undersøgelser:\\n" +
//                "a. Insulin suppressionstest (som ved anden forsøgsdag)\\n" +
//                "b. Blodtryk (som ved anden forsøgsdag)\\n\\n" +
//                "To dage op til og under forsøgsdagene må du ikke drikke alkohol, kaffe, the eller energidrik, og" +
//                "hellere ikke dyrke motion. I studiets alle andre dage må du drikke så meget filterkaffe du ønsker," +
//                "men kun 1 kop espresso-, koge- eller stempelkandekaffe pr dag (kaffe der ikke har været igennem" +
//                "et kaffefilter). Efter langtidsstudiet kan vi se om dagligt cafestolindtag i 12 uger forbedrer" +
//                "kroppens evne til at omsætte et måltid, ændrer insulinfølsomheden, ændrer fedtfordeling i" +
//                "leveren og resten af kroppen, ændrer blodtrykket, vægt, taljemål eller bakteriesammensætningen" +
//                "i tarmen. Hverken du eller vi ved om du får cafestol eller placebopille, før efter studiet er færdigt." +
//                "Om du kommer i den ene eller anden gruppe bestemmes ved lodtrækning.\\n\\n" +
//                "Biobank\\n" +
//                "Under forsøget oprettes en ”forskningsbiobank”, som er et fysisk lager hvor vi opbevarer dine" +
//                "blod, fæces og urinprøver. Alle prøverne fryses ned indtil alle deltagere har færdiggjort forsøget," +
//                "så vi kan analysere dem samtidigt. Når forsøget er afsluttet og prøverne er analyseret, bliver" +
//                "prøverne destrueret. Du kan til enhver tid bede om at få dit biologiske materiale destrueret.\\n\\n" +
//                "Forsøgets nytte\\n" +
//                "Sukkersyge er et hurtigt voksende problem på verdensplan, og det er vigtigt at kunne forebygge" +
//                "sygdommen og dens komplikationer. Vores undersøgelser på celler og dyr tyder på at cafestol fra" +
//                "kaffe eventuelt kan forebygge sukkersyge, og det er vigtigt at studere dette på mennesker.\\n" +
//                "Resultaterne fra dette forsøg kan måske i fremtiden bane vejen for en ny billig og bæredygtig" +
//                "måde at forebygge sukkersyge på. For dig personligt vil du få et godt indblik i din krop og dens" +
//                "evne til at håndtere sukker og din insulinfølsomhed – det vil sige hvor stor risiko du har for at" +
//                "udvikle sukkersyge senere i livet. Du vil få en generel helbredsundersøgelse med en lang række" +
//                "blodprøver, og vi vil kunne kortlægge din fedtprocent og fedtfordeling. Skulle vi opdage tegn på" +
//                "sygdom i dine blodprøver og scanninger vil vi som udgangspunkt informere dig om det og hjælpe" +
//                "dig videre med dette, medmindre du selvfølgelig specifikt frabeder dig sådanne informationer.\\n\\n" +
//                "Bivirkninger, risici, komplikationer og ulemper\\n" +
//                "I denne tabel er anført de risici, bivirkninger, komplikationer og ulemper forbundet med deltagelse" +
//                "i dette projekt: \\n" +
//                "Insulin bruger vi når vi undersøger din insulinfølsomhed og er livsvigtig medicin som" +
//                "sukkersygepatienter bl.a. bruger til at få blodsukkeret ned og ind i kroppens celler. Det kan derfor" +
//                "give lavt blodsukker (meget almindeligt). Ikke almindeligt: hævelser på grund af væskeophobning," +
//                "hudreaktioner, diabetisk betinget nethindelidelse, uskarpt syn. Sjældent: nervebetændelse. Meget" +
//                "sjældent: anafylaktisk chok – en akut alvorlig allergisk tilstand.\\n\\n" +
//                "Octreotid er et stof, som hæmmer, at kroppen producerer en række hormoner, for eksempel" +
//                "insulin. Dette udnytter vi, når vi undersøger din insulinfølsomhed. Når vi har undertrykt din egen" +
//                "insulinproduktion ved vi præcis hvor meget insulin, der cirkulerer i din krop. Vi giver octreotid i 3" +
//                "timer og efterfølgende bliver din egen hormonproduktion normal igen. Nogle patienter får" +
//                "octreotid dagligt som behandling af sygdomme, hvor man har for høj hormonprokution." +
//                "Undersøgelsen i langtidsstudiet hvor vi bruger octreotid og insulin (Insulin suppressionstest) er en" +
//                "hurtig og hyppig anvendt procedure med få rapporterede gener – hovedsagelig følelse af sult og" +
//                "let ubehag i maven. I 2 ud af 400 er der rapporteret diarré. Der er læge tilstede ved" +
//                "undersøgelsen, som til enhver tid kan afbrydes ved ubehag eller øvrige problemer.\\n\\n" +
//                "Vi beder dig fortælle os, hvis du oplever problemer med dit helbred mens forsøget står på. Hvis vi" +
//                "opdager bivirkninger hos de øvrige forsøgspersoner som vi allerede ikke har fortalt dig om, vil du" +
//                "blive orienteret med det samme, og du vil kunne tage stilling til om du vil fortsætte i forsøget.\\n\\n" +
//                "Udelukkelse fra, og afbrydelse af, forsøg\\n" +
//                "Skulle du mod forventning opleve betydelige bivirkninger til cafestol eller ikke er i stand til at" +
//                "gennemføre forsøgsdagene, vil du blive trukket ud af forsøget.\\n\\n" +
//                "Personoplysninger\\n" +
//                "Under forsøget vil vi behandle nogle af dine personoplysninger. Vi informerer dig om hvilke" +
//                "informationer vi indsamler og opbevarer, og det vil kun foregå efter samtykke fra dig. Dine data" +
//                "opbevares sikkert, i overholdelse af databeskyttelsesloven og databeskyttelsesforordningen." +
//                "Forsøgsansvarlig samt eventuel kontrolmyndighed (Videnskabsetisk komité) vil have adgang til at" +
//                "indhente oplysninger fra din patientjournal for at bekræfte egnethed til deltagelse og i" +
//                "kontroløjemed, herunder egenkontrol, kvalitetskontrol og monitorering. Forsøgsansvarlig ser" +
//                "journalen igennem for at sikre os om at du ikke er alvorligt syg, har sukkersyge, misbruger medicin" +
//                "eller rusmidler eller har aktiv kræftsygdom.\\n\\n" +
//                "Oplysninger om økonomiske forhold\\n" +
//                "Som deltager er du berettiget til godtgørelse for transport og parkering. Der er ikke muligt at" +
//                "tilbyde kompensation for tabt arbejdsfortjeneste. Forsøget et støttet af flere private fonde" +
//                "(190.000 DKK fra Frode V. Nyegaard og Hustru’s fond, 45.000 DKK fra A.P. Møller" +
//                "Fonden/Lægefonden til hhv. materialer og blodprøveanalyser), Helsefonden (350.000 DKK) og" +
//                "Steno Diabetes Center Aarhus. Der er også søgt penge fra flere private fonde, som ikke er" +
//                "behandlet endnu. Der er ingen tilknytning (økonomisk eller anden) mellem forskerne og de" +
//                "støttegivende private fonde. Aarhus Universitet støtter projektet med 3 års løn til læge og PhDstuderende Fredrik Brustad Mellbye. Aarhus Universitet og Region Midtjylland har patenteret brug" +
//                "af cafestol til behandling af diabetes. Forskergruppen er registreret som opfindere.\\n\\n" +
//                "Adgang til forsøgsresultater\\n" +
//                "Akutstudiet er afsluttet når 14 deltagere har været igennem alle 3 forsøgsdage og langtidsstudiet" +
//                "er afsluttet når mindst 48 deltagere har været igennem tolv ugers indtag af cafestol/placebo og de" +
//                "indledende og afsluttende undersøgelser. Ved forsøgets afslutning kan du få oplyst om/hvornår du" +
//                "har fået cafestol eller placebo. Efter forsøgets afslutning vil resultaterne blive udgivet i" +
//                "anonymiseret form i internationale, videnskabelige tidsskrifter og præsenteres på relevante" +
//                "konferencer. Cafestol er ikke et lægemiddel, og projektet anmeldes ikke til lægemiddelstyrelsen.\\n\\n" +
//                "Kontaktperson\\n" +
//                "Fredrik Brustad Mellbye\\n" +
//                "Læge, PhD-studerende\\n" +
//                "Steno Diabetes Center Aarhus\\n" +
//                "Aarhus Universitetshospital\\n" +
//                "Palle Juul-Jensens Blvd 99, 8200 Aarhus N\\n" +
//                "+45 2684 4425\\n" +
//                "fbmellbye@clin.au.dk\\n\\n" +
//                "Vi håber, at du med denne information har fået tilstrækkeligt indblik i, hvad det vil sige at deltage i" +
//                "forsøget, og at du føler dig rustet til at træffe beslutningen om din eventuelle deltagelse. Vi beder" +
//                "dig også om at læse det vedlagte materiale ”Forsøgspersonens rettigheder i et" +
//                "sundhedsvidenskabeligt forskningsprojekt”. Hvis du vil vide mere om forsøget, er du meget " +
//                "velkommen til at kontakte os."
//    }
}