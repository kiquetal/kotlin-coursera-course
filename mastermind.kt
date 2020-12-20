package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {

    if (secret.equals(guess))
        return Evaluation(4, 0);

    var idx = 0;
    var right = 0;
    var wrong = 0;
    var memo = mutableListOf<Int>();
    for (c in guess) {
        if (c == secret[idx++]) {
            right++
            memo.add(idx -1);
        }
    }
    var newSecret:CharArray=secret.toCharArray()
    var newGuess:CharArray=guess.toCharArray()
    memo.map {

        newSecret[it]='\u0000'
        newGuess[it]='\u0000'
    }
    for (c in newGuess)
    {
         if (c!='\u0000' && newSecret.contains(c))
        {
           newSecret.set(newSecret.indexOf(c),'-')
        }

    }
    for (i in newSecret)
    {
        if (i=='-')
            wrong++
    }
    return Evaluation(right,wrong)
}

fun main()
{
    println("veamos");
}
