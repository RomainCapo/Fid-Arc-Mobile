package ch.hearc.fidarc.ui.data.model

data class Company (
    val id:Int,
    val company_name:String,
    val company_description:String,
    val latitude:Double,
    val longitude:Double,
    val number_fidelity_points:Int,
    val message_to_user:String,
    val card_color_id:Int,
    val user_id:Int
)