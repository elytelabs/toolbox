package com.elytelabs.toolbox

/**
 * Generates random colors from Material Design palette.
 * Useful for avatars, backgrounds, and UI accents.
 */
object ColorGenerator {

    private val colorsList = listOf(
        0xFFF44336.toInt(),  // Red
        0xFFE91E63.toInt(),  // Pink
        0xFF9C27B0.toInt(),  // Purple
        0xFF673AB7.toInt(),  // Deep Purple
        0xFF3F51B5.toInt(),  // Indigo
        0xFF2196F3.toInt(),  // Blue
        0xFF03A9F4.toInt(),  // Light Blue
        0xFF00BCD4.toInt(),  // Cyan
        0xFF009688.toInt(),  // Teal
        0xFF4CAF50.toInt(),  // Green
        0xFF8BC34A.toInt(),  // Light Green
        0xFFCDDC39.toInt(),  // Lime
        0xFFFF9800.toInt(),  // Orange
        0xFFFF5722.toInt(),  // Deep Orange
        0xFF795548.toInt(),  // Brown
        0xFF607D8B.toInt()   // Blue Grey
    )

    /**
     * Returns the full list of available colors.
     */
    fun getColorList(): List<Int> = colorsList

    /**
     * Returns a random color from the palette.
     */
    fun getRandomColor(): Int = colorsList.random()

    /**
     * Returns a consistent color for a given string.
     * Useful for generating avatar colors based on username/email.
     */
    fun getColorForString(text: String): Int {
        val hash = text.hashCode().let { if (it < 0) -it else it }
        return colorsList[hash % colorsList.size]
    }

    /**
     * Returns a random color with specified alpha (0-255).
     */
    fun getRandomColorWithAlpha(alpha: Int): Int {
        val color = getRandomColor()
        return (alpha shl 24) or (color and 0x00FFFFFF)
    }

    /**
     * Returns the color at a specific index (wraps around).
     */
    fun getColorAt(index: Int): Int = colorsList[index % colorsList.size]
}