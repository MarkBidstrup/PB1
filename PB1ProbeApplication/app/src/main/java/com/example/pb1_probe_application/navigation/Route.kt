package com.example.pb1_probe_application.navigation
// routes ( bottom bar items routes not included )
sealed class Route(val route: String) {

    object Setting: Route("Setting")
    object EditProfile: Route("EditProfile")
    object Notification: Route("Notification")
    object LogInd: Route("logInd")
    object Applied : Route("Applied")
    object Filter : Route("Filter")
    object DeltagerInfo : Route("DeltagerInfo")
    object NotLoggedIn : Route("NotLoggedIn")
    object Register : Route("Register")
    object CreateTrial : Route("CreateTrial")
    object EditTrial : Route("EditTrial")
    object ManageTrial : Route("ManageTrial")
    object FurtherInformation : Route("FurtherInformation")
    object ReadMoreTrialPost: Route("ReadMoreTrialPost")
    object DeleteProfileScreen: Route("DeleteProfileScreen")
    object ForgottenPassword: Route("ForgottenPassword")
    object DeltagerListe: Route("DeltagerListe")

}