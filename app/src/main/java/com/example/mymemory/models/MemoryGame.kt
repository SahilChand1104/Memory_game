package com.example.mymemory.models

import com.example.mymemory.utils.DEFAULT_ICONS

class MemoryGame(private val boardSize: BoardSize){

    val cards: List<MemoryCard>
    var numPairsFound = 0

    private var numCardFlips = 0

    private var indexOfSingleSelectedCard: Int? = null

    init{
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImages = (chosenImages + chosenImages).shuffled()
        cards = randomizedImages.map { MemoryCard(it) }
    }

    fun flipCard(position: Int): Boolean {
        numCardFlips++
        val card = cards[position]
        //Three cases:
        //0 cards flipped -> restore cards + flip over selected card
        //1 card flipped -> flip over selected card and see if they match
        //2 cards flipped -> restore cards + flip over selected cards
        var foundMatch = false
        if (indexOfSingleSelectedCard == null) {
            // 0 or 2 cards prev flipped over
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            //1 card prev flipped
            foundMatch = checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceup = !card.isFaceup
        return foundMatch

    }

    private fun checkForMatch(position1: Int, position2: Int): Boolean {
        if (cards[position1].identifier != cards[position2].identifier){
            return false
        }
        cards[position1].isMatched = true
        cards[position2].isMatched = true
        numPairsFound++
        return true

    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceup = false
            }
        }
    }

    fun haveWonGame(): Boolean {
        return numPairsFound == boardSize.getNumPairs()
    }

    fun isCardFaceUp(position: Int): Boolean {
        return cards[position].isFaceup
    }

    fun getNumMoves(): Int {
        return numCardFlips / 2

    }

}