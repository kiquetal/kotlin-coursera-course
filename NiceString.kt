package nicestring

fun String.isNice(): Boolean {
    val nList= this.toCharArray().toList()

    val zipped= nList.zipWithNext()


    val hasIllegal = zipped.none {
            pair -> checkIllegalSubstring(pair)
    }
    val mininumVowels= nList.fold(0){
            acc, character ->
        if (character.isVowel())
            acc+1
        else
            acc
    }
    val hasMinimumVowel= if (mininumVowels>=3) true else false
    val hasDoubleLetter = zipped.any{pair -> isDoubleLetter(pair) }

    val resultsOperations= listOf<Boolean>(hasIllegal,hasMinimumVowel,hasDoubleLetter)

    val onlyTrue= resultsOperations.filter { b ->  b==true }.size
    return onlyTrue>=2
}


fun checkIllegalSubstring(p:Pair<Char,Char>):Boolean
{
    val illegalSubstring= listOf<Pair<Char,Char>>(Pair('b','u'), Pair('b','a'), Pair('b','e'))

    return illegalSubstring.any { pair -> pair==p  }
}




fun Char.isVowel():Boolean {
    val vowels= listOf<Char>('a','e','i','o','u')
    return vowels.contains(this)

}
fun isDoubleLetter(p:Pair<Char,Char>):Boolean {
    return (p.first == p.second)
}

fun main()
{
    val nList= "sisxxjwlkbu".toCharArray().toList()
    println(nList);
    val zipped= nList.zipWithNext()

    print(zipped)


    val hasNoIllegal = zipped.none {
            pair -> checkIllegalSubstring(pair)
    }
    val mininumVowels= nList.fold(0){
            acc, character ->
        if (character.isVowel())
            acc+1
        else
            acc
    }
    val hasMinimumVowel= if (mininumVowels>=3) true else false
    val hasDoubleLetter = zipped.any{pair -> isDoubleLetter(pair) }

    val resultsOperations= listOf<Boolean>(hasNoIllegal,hasMinimumVowel,hasDoubleLetter)

    val onlyTrue= resultsOperations.filter { b ->  b==true }.size
    val isNice= if (onlyTrue>=2) true else false

    println("isNice"+ isNice)






}
fun checkPair(p:Pair<Char,Char>):Boolean{
    println(p.first+"-"+p.second)
    return false
}



