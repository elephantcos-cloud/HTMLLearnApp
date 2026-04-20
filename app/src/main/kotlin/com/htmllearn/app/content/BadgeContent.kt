package com.htmllearn.app.content

data class BadgeInfo(
    val id: String,
    val title: String,
    val description: String,
    val iconKey: String,
    val colorHex: Long,
    val xpValue: Int
)

object BadgeContent {

    fun getAllBadges(): List<BadgeInfo> = listOf(
        BadgeInfo("first_lesson", "প্রথম পদক্ষেপ", "প্রথম lesson সম্পন্ন করেছো", "badge_first", 0xFF58A6FF, 100),
        BadgeInfo("five_lessons", "পাঁচের যোদ্ধা", "৫টি lesson সম্পন্ন করেছো", "badge_five", 0xFF3FB950, 150),
        BadgeInfo("ten_lessons", "দশের রাজা", "১০টি lesson সম্পন্ন করেছো", "badge_ten", 0xFFFFB74D, 200),
        BadgeInfo("twenty_five_lessons", "রত্ন শিক্ষার্থী", "২৫টি lesson সম্পন্ন করেছো", "badge_25", 0xFFBA68C8, 300),
        BadgeInfo("all_lessons", "HTML গুরু", "সব lesson সম্পন্ন করেছো", "badge_all", 0xFFF78166, 500),
        BadgeInfo("week_streak", "সাত দিনের বীর", "৭ দিন ধারাবাহিকভাবে পড়েছো", "badge_streak7", 0xFF26C6DA, 200),
        BadgeInfo("month_streak", "মাসের সেরা", "৩০ দিন ধারাবাহিকভাবে পড়েছো", "badge_streak30", 0xFFFFD54F, 500),
        BadgeInfo("xp_1000", "XP হাজারী", "১০০০ XP অর্জন করেছো", "badge_xp1000", 0xFF4FC3F7, 100),
        BadgeInfo("xp_5000", "XP পাঁচ হাজার", "৫০০০ XP অর্জন করেছো", "badge_xp5000", 0xFFE57373, 200),
        BadgeInfo("level_5", "পঞ্চম স্তর", "Level 5 পৌঁছেছো", "badge_level5", 0xFF9575CD, 300),
        BadgeInfo("quiz_master", "কুইজ মাস্টার", "১০টি কুইজ পাস করেছো", "badge_quiz", 0xFF4DB6AC, 200),
        BadgeInfo("perfect_quiz", "নিখুঁত উত্তর", "কোনো quiz-এ ১০০% পেয়েছো", "badge_perfect", 0xFFFFD54F, 150),
        BadgeInfo("speed_learner", "দ্রুত শিক্ষার্থী", "একদিনে ৫টি lesson শেষ করেছো", "badge_speed", 0xFFF06292, 200),
        BadgeInfo("early_bird", "ভোরের পাখি", "সকাল ৬টার আগে পড়েছো", "badge_bird", 0xFFAED581, 100),
        BadgeInfo("night_owl", "রাতের পেঁচা", "রাত ১২টার পরে পড়েছো", "badge_owl", 0xFF7986CB, 100)
    )

    fun getBadgeById(id: String): BadgeInfo? = getAllBadges().find { it.id == id }
}
