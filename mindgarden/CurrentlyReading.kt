package hr.ferit.matejajakic.mindgarden

import com.google.firebase.database.Exclude
import java.util.*

data class CurrentlyReading(
    var title : String ?=null,
    var author : String ?=null,
    var startDate : String ?=null,
    var image : String ?=null,
){

}
