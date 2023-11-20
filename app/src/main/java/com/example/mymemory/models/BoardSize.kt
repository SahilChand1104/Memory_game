package com.example.mymemory.models


enum class BoardSize(val numCards: Int){
    EASY(8),
    Medium(18),
    HARD(24),
    EXTREME(36);

    fun getWidth(): Int {
        return when(this){
            EASY -> 2
            Medium -> 3
            HARD -> 4
            EXTREME -> 6
        }
    }

    fun getHeight(): Int {
        return numCards / getWidth()
    }



    fun getNumPairs(): Int {
        return numCards/ 2
    }
}