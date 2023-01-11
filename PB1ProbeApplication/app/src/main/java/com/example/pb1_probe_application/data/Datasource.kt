package com.example.pb1_probe_application.data

import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.dataClasses.Diagnoses
import com.example.pb1_probe_application.dataClasses.DropDownType
import com.example.pb1_probe_application.dataClasses.UserInfo
import com.example.pb1_probe_application.dataClasses.UserInfoTypes

class Datasource {

    fun loadProfilePatientInfo(): List<UserInfo> {
        return listOf(
            UserInfo(R.string.navn,UserInfoTypes.FirstName),
            UserInfo(R.string.efternavn,UserInfoTypes.LastName),
            UserInfo(R.string.koen,UserInfoTypes.Gender),
            UserInfo(R.string.alder,UserInfoTypes.Age),
            UserInfo(R.string.vaegt,UserInfoTypes.Weight),
            UserInfo(R.string.diagnose,UserInfoTypes.Diagnosis),
            UserInfo(R.string.email,UserInfoTypes.Email),
            UserInfo(R.string.telefon,UserInfoTypes.Phone),
        )
    }

    fun loadProfileResearcherInfo(): List<UserInfo> {
        return listOf(
            UserInfo(R.string.navn,UserInfoTypes.FirstName),
            UserInfo(R.string.efternavn,UserInfoTypes.LastName),
            UserInfo(R.string.email,UserInfoTypes.Email),
            UserInfo(R.string.telefon,UserInfoTypes.Phone),
            UserInfo(R.string.forskningsenhed,UserInfoTypes.Department),
        )
    }

    fun loadKommuner(): List<String> {
        return listOf(
            "Brønderslev" ,
            "Frederikshavn" ,
            "Hjørring" ,
            "Jammerbugt" ,
            "Læsø" ,
            "Mariagerfjord" ,
            "Morsø" ,
            "Rebild" ,
            "Thisted" ,
            "Vesthimmerlands" ,
            "Aalborg",
            "Favrskov" ,
            "Hedensted" ,
            "Herning" ,
            "Holstebro" ,
            "Horsens" ,
            "Ikast-Brande" ,
            "Lemvig" ,
            "Norddjurs" ,
            "Odder" ,
            "Randers" ,
            "Ringkøbing-Skjern" ,
            "Samsø" ,
            "Silkeborg" ,
            "Skanderborg" ,
            "Skive" ,
            "Struer" ,
            "Syddjurs" ,
            "Viborg" ,
            "Århus",
            "Assens" ,
            "Billund" ,
            "Esbjerg" ,
            "Fanø" ,
            "Fredericia" ,
            "Faaborg-Midtfyn" ,
            "Haderslev" ,
            "Kerteminde" ,
            "Kolding" ,
            "Langeland" ,
            "Middelfart" ,
            "Nordfyns" ,
            "Nyborg" ,
            "Odense" ,
            "Svendborg" ,
            "Sønderborg" ,
            "Tønder" ,
            "Varde" ,
            "Vejen" ,
            "Vejle" ,
            "Ærø" ,
            "Aabenraa",
            "Faxe" ,
            "Greve" ,
            "Guldborgsund" ,
            "Holbæk" ,
            "Kalundborg" ,
            "Køge" ,
            "Lejre" ,
            "Lolland" ,
            "Næstved" ,
            "Odsherred" ,
            "Ringsted" ,
            "Roskilde" ,
            "Slagelse" ,
            "Solrød" ,
            "Sorø" ,
            "Stevns" ,
            "Vordingborg",
            "Albertslund" ,
            "Allerød" ,
            "Ballerup" ,
            "Bornholms" ,
            "Brøndby" ,
            "Dragør" ,
            "Egedal" ,
            "Fredensborg" ,
            "Frederiksberg C" ,
            "Frederikssund" ,
            "Furesø" ,
            "Gentofte" ,
            "Gladsaxe" ,
            "Glostrup" ,
            "Gribskov" ,
            "Halsnæs" ,
            "Helsingør" ,
            "Herlev" ,
            "Hillerød" ,
            "Hvidovre" ,
            "Høje-Taastrup" ,
            "Hørsholm" ,
            "Ishøj" ,
            "København" ,
            "Lyngby-Taarbæk" ,
            "Rudersdal" ,
            "Rødovre" ,
            "Tårnby" ,
            "Vallensbæk",
            "Hellerup"
        )
    }

    fun loadDropDownList(dropDownType: DropDownType): List<String>? {
        if (dropDownType == DropDownType.KOEN) {
            return listOf(
                "Mand",
                "Kvinde"
            )
        }
        if (dropDownType == DropDownType.JA_NEJ) {
            return listOf(
                "Ja",
                "Nej"
            )
        }
        if (dropDownType == DropDownType.KOMMUNE) {
            return loadKommuner()
        }

        if (dropDownType == DropDownType.DIAGNOSER) {
            val list = ArrayList<String>()
            enumValues<Diagnoses>().forEach {
                list.add(it.diagnosis)
            }
            return list
        }
        return null
    }
}