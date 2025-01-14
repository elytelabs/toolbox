package com.elytelabs.toolbox
import java.util.Random

 object ColorGenerator {

       private val colorsList = listOf(
            0xFFF44336.toInt(),  // Red 500
            0xFFEF5350.toInt(),  // Red 600
            0xFFE91E63.toInt(),  // Pink 500
            0xFFEC407A.toInt(),  // Pink 600
            0xFF9C27B0.toInt(),  // Purple 500
            0xFFAB47BC.toInt(),  // Purple 600
            0xFF5E35B1.toInt(),  // Deep Purple 600
            0xFF673AB7.toInt(),  // Deep Purple 500
            0xFF3F51B5.toInt(),  // Indigo 500
            0xFF3949AB.toInt(),  // Indigo 600
            0xFF1E88E5.toInt(),  // Blue 600
            0xFF2196F3.toInt(),  // Blue 500
            0xFF03A9F4.toInt(),  // Light Blue 500
            0xFF039BE5.toInt(),  // Light Blue 600
            0xFF00ACC1.toInt(),  // Cyan 600
            0xFF00BCD4.toInt(),  // Cyan 500
            0xFF009688.toInt(),  // Teal 500
            0xFF00897B.toInt(),  // Teal 600
            0xFF43A047.toInt(),  // Green 600
            0xFF4CAF50.toInt(),  // Green 500
            0xFF7CB342.toInt(),   // Light Green 600
            0xFF8BC34A.toInt(),  // Light Green 500
            0xFFCDDC39.toInt(),  // Lime 500
            0xFFFFEB3B.toInt(),  // Yellow 500
            0xFFFF5722.toInt(),  // Deep Orange 500
            0xFFFF9800.toInt(),  // Orange 500
            0xFFFFC107.toInt(),  // Amber 500
            0xFF795548.toInt(),  // Brown 500
            0xFF9E9E9E.toInt(),  // Grey 500
            0xFF607D8B.toInt()  // Blue Grey 500
        )

    private val random: Random = Random(System.currentTimeMillis())

    fun getColorList() : List<Int> {
        return colorsList
    }

     fun getRandomColor(): Int {
        return colorsList[random.nextInt(colorsList.size)]
    }
}