package com.example.pb1_probe_application.data

import androidx.compose.ui.res.stringResource
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.model.TrialState
import com.example.pb1_probe_application.model.UserInfo
import java.util.*

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
    fun loadProfileResercherInfo(): List<UserInfo> {
        return listOf<UserInfo>(
            UserInfo(R.string.navn,""),
            UserInfo(R.string.efternavn,""),
            UserInfo(R.string.forskningsenhed,""),
            UserInfo(R.string.email,""),
        )
    }

    // TODO: fix/replace dummy data
    fun loadTrials(): List<TrialState> {
        return listOf<TrialState>(
            TrialState("Tidlige abnormiteter i nethinden ved diabetes","At undersøge tidlige forandringer i nethinden, som kan relateres til diabetes",
                    "22.11.2022",200,"Diabetikere", "Børn under 3", 200,
            "Herlev Hospital", false, false, "32 dage", 3, "30 minutter", "22.12.2022",
                    "22.1.2023", listOf<String>("Diabetes type 1", "Diabetes type 2"),13,142,
                    "Jens Larsen", "Herlev Hospital", "+45 43562343"
            ),
            TrialState("Forsøgsnavn","Formål", "23.1.2023",20,"Inklusion", "Eksklusion", 0,
                "Hvidovre Hospital", true, false, "10 dage", 1, "1 time", "23.4.2023",
                "23.2.2023", listOf<String>("Diagnose 1", "Diagnose 2"),4,20,
                "Jens Larsen", "Herlev Hospital", "+45 43562343"
            ),
            TrialState("Tidlige abnormiteter i nethinden ved diabetes","At undersøge tidlige forandringer i nethinden, som kan relateres til diabetes",
                "22.11.2022",200,"Diabetikere", "Børn under 3", 200,
                "Herlev Hospital", false, false, "32 dage", 3, "30 minutter", "22.12.2022",
                "22.1.2023", listOf<String>("Diabetes type 1", "Diabetes type 2"),13,142,
                "Jens Larsen", "Herlev Hospital", "+45 43562343"
            ),
            TrialState("Forsøgsnavn","Formål", "23.1.2023",20,"Inklusion", "Eksklusion", 0,
                "Hvidovre Hospital", true, false, "10 dage", 1, "1 time", "23.4.2023",
                "23.2.2023", listOf<String>("Diagnose 1", "Diagnose 2"),4,20,
                "Jens Larsen", "Herlev Hospital", "+45 43562343"
            ),
            TrialState("Tidlige abnormiteter i nethinden ved diabetes","At undersøge tidlige forandringer i nethinden, som kan relateres til diabetes",
                "22.11.2022",200,"Diabetikere", "Børn under 3", 200,
                "Herlev Hospital", false, false, "32 dage", 3, "30 minutter", "22.12.2022",
                "22.1.2023", listOf<String>("Diabetes type 1", "Diabetes type 2"),13,142,
                "Jens Larsen", "Herlev Hospital", "+45 43562343"
            ),
            TrialState("Forsøgsnavn","Formål", "23.1.2023",20,"Inklusion", "Eksklusion", 0,
                "Hvidovre Hospital", true, false, "10 dage", 1, "1 time", "23.4.2023",
                "23.2.2023", listOf<String>("Diagnose 1", "Diagnose 2"),4,20,
                "Jens Larsen", "Herlev Hospital", "+45 43562343"
            ),
        )
    }

    fun loadDeltagerInfo(): String {
        return "Hvem kan deltage i forsøget? \n" +
                "• Personer med type 1-diabetes\n" +
                "• Både børn og unge i alderen fra 5-18 år, og voksne over 18 år (myndige voksne)\n" +
                "• Personer uden væsentlige kroniske sygdomme ud over diabetes\n" +
                "• Personer uden væsentlige øjensygdomme ud over diabetisk nethindesygdom\n" +
                "Derudover søges også raske kontrolpersoner, både børn og unge i alderen 5-18 år og myndige voksne " +
                "over 18 år, uden væsentlige kroniske sygdomme eller øjensygdomme.\n" +
                "\n" +
                "Forsøgsperiode \n" +
                "Fra 11. august 2020 til 30. september 2022.\n" +
                "\n" +
                "Resume \n" +
                "Det er velkendt, at diabetes på langt sigt kan medføre komplikationer i øjet, oftest i form af " +
                "nethindesygdom. Forud for disse senkomplikationer forekommer symptomfrie abnormiteter i nethindens " +
                "nervevæv og blodkar. Projektet vil opspore disse forandringer i nethinden tidligere end hidtil og i større " +
                "detaljegrad med nye metoder til øjenundersøgelse.\n" +
                "\n" +
                "Lokationer \n" +
                "Rigshospitalets Afdeling for Øjensygdomme\n" +
                "Valdemar Hansens Vej 13\n" +
                "2600 Glostrup\n" +
                "\n" +
                "Forsøgets forløb for forsøgspersonen \n" +
                "Øjenundersøgelsen foregår på Rigshospitalets Afdeling for Øjensygdomme i Glostrup, Valdemar " +
                "Hansens Vej 13, 2600 Glostrup. Som deltager vil du få foretaget: måling af synsstyrke og brillestyrke, " +
                "undersøgelser af øjets nattesyn og nervefunktion, samt skanninger og fotos af nethinden og dens " +
                "blodforsyning. Øjenundersøgelsen forventes at vare ca. 2 timer.\n" +
                "Der vil også blive taget en blodprøve.\n" +
                "Hvis barnet/den unge er under 18 år, skal forældrene give samtykke til deltagelse i projektet. Inden endelig " +
                "stillingtagen til deltagelse vil du/I modtage mundtlig " +
                "og skriftlig information om projektet.\n" +
                "\n" +
                "Tilbydes der betaling eller dækning af udgifter for forsøgspersonen? \n" +
                "Transportudgifter til øjenundersøgelsen på Rigshospitalets Afdeling for Øjensygdomme i " +
                "Glostrup dækkes med op til 500 kr. mod forevisning " +
                "af kvitteringer efter statens fastsatte takst.\n"
    }
}