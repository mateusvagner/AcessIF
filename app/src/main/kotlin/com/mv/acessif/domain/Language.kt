package com.mv.acessif.domain

enum class Language(val shortName: String, val fullName: String) {
    EN("en", "English"),
    PT("pt", "Portuguese"),
    ES("es", "Spanish"),
    FR("fr", "French"),
    DE("de", "German"),
    IT("it", "Italian"),
    ZH("zh", "Chinese"),
    JA("ja", "Japanese"),
    KO("ko", "Korean"),
    RU("ru", "Russian"),
    AR("ar", "Arabic"),
    HI("hi", "Hindi"),
    BN("bn", "Bengali"),
    PA("pa", "Punjabi"),
    JV("jv", "Javanese"),
    MS("ms", "Malay"),
    UR("ur", "Urdu"),
    VI("vi", "Vietnamese"),
    TL("tl", "Tagalog"),
    FA("fa", "Persian"),
    SW("sw", "Swahili"),
    TH("th", "Thai"),
    TR("tr", "Turkish"),
    UNKNOWN("unknown", "Unknown"),
    ;

    companion object {
        fun fromShortName(shortName: String): Language {
            return entries.firstOrNull { it.shortName == shortName } ?: UNKNOWN
        }
    }
}
