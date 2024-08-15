package dev.melodies.utils

object ProgressBarBuilder {
    fun buildProgressBar(progress: Double, width: Int): String {
        val completedBars = (progress * width).toInt()
        val remainingBars = width - completedBars
        return "<green>|</green>".repeat(completedBars) + "<gray>|</gray>".repeat(remainingBars)
    }
}