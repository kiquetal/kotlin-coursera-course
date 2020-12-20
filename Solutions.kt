data class Evaluation(val rightPosition:Int, val wrongPosition:Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {

    val rightPosition = secret.zip(guess).count {
        it.first == it.second
    }
    val commonLetters = "ABCDEF".sumBy {
        Math.min(secret.count {
            it == ch
        },guess.count{
            it == ch
        })
    }

    return Evaluation(rightPosition, commonLetters - rightPosition);

}
