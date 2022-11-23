package com.example.pb1_probe_application.navigation
// routes ( bottombar items routes not included )
sealed class Route(val route: String) {

    object Setting: Route("Setting")
    object EditProfile: Route("EditProfile")
    object Notification: Route("Notification")
    object LogInd: Route("logInd")
    object Applied : Route("Applied")
    object Filter : Route("Filter")
    object DeltagerInfo : Route("DeltagerInfo/{trialID}")
    object NotLoggedIn : Route("NotLoggedIn")
    object Register : Route("Register")

//    {
//        fun createRoute(trialID: String) = "DeltagerInfo/$trialID"
//    }

}