package cn.qjm253.quick_android.mvp_demo

data class Weather(
    val `data`: List<Data>,
    val city: String,
    val cityEn: String,
    val cityid: String,
    val country: String,
    val countryEn: String,
    val update_time: String
)

data class Data(
    val air: Int,
    val air_level: String,
    val air_tips: String,
    val alarm: Alarm,
    val date: String,
    val day: String,
    val hours: List<Hour>,
    val humidity: Int,
    val index: List<Index>,
    val tem: String,
    val tem1: String,
    val tem2: String,
    val wea: String,
    val wea_img: String,
    val week: String,
    val win: List<String>,
    val win_speed: String
)

data class Hour(
    val day: String,
    val tem: String,
    val wea: String,
    val win: String,
    val win_speed: String
)

data class Alarm(
    val alarm_content: String,
    val alarm_level: String,
    val alarm_type: String
)

data class Index(
    val desc: String,
    val level: String,
    val title: String
)