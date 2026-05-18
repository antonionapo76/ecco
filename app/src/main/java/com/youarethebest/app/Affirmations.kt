package com.youarethebest.app

object Affirmations {

    private val messages = listOf(
        "You are the BEST! Never forget it!",
        "You are absolutely incredible today!",
        "The world is better because you're in it.",
        "You are unstoppable. Keep going!",
        "You radiate confidence and greatness.",
        "Everything you touch turns to gold!",
        "You are a masterpiece in progress.",
        "Your potential is limitless!",
        "You are stronger than you think.",
        "Today is YOUR day to shine!",
        "You are worthy of all the good things in life.",
        "You make a difference just by being you.",
        "You are brave, bold, and brilliant!",
        "The best is yet to come for you!",
        "You are a force of nature!",
        "You inspire everyone around you.",
        "Your smile lights up the room!",
        "You are enough, exactly as you are.",
        "Believe in yourself \u2014 you are amazing!",
        "You are living proof that awesome exists!",
        "You are a champion in every way!",
        "Your energy is magnetic and powerful.",
        "You are built for greatness!",
        "Nothing can dim the light inside you.",
        "You are one of a kind \u2014 literally the best!",
        "Your hard work is paying off. Keep it up!",
        "You handle everything with grace and power.",
        "You are the main character. Own it!",
        "Great things are coming your way!",
        "You are a gift to everyone who knows you.",
        "You have the heart of a lion!",
        "Your determination is unmatched.",
        "You are a walking inspiration!",
        "Today, you will conquer the world!",
        "You are phenomenal and don't you forget it!",
        "Success follows you everywhere you go.",
        "You were born to do extraordinary things.",
        "Your positivity is contagious!",
        "You are a rockstar in disguise!",
        "Every day you get better and better!",
    )

    fun random(): String = messages.random()

    fun forNotification(): Pair<String, String> {
        val titles = listOf(
            "Hey Superstar! \u2B50",
            "Daily Reminder \u2764\uFE0F",
            "Good Morning, Champion! \uD83C\uDFC6",
            "Hey Beautiful Soul! \u2728",
            "Your Daily Boost! \uD83D\uDE80",
            "Rise & Shine! \u2600\uFE0F",
            "Quick Reminder! \uD83D\uDCAA",
            "You Need To Hear This! \uD83D\uDC96",
        )
        return titles.random() to random()
    }
}
