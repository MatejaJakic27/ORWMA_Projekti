package hr.ferit.matejajakic.mindgarden

data class ReadBooks(
    val title : String ?=null,
    val author : String ?=null,
    val startDate : String ?=null,
    val endDate : String ?=null,
    val rating : String ?=null,
    val image : String ?=null
)
