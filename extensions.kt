
package rationals

import java.math.BigInteger


operator fun Pair<Rational,Rational>.contains(a:Rational):Boolean {

    if (a>=this.first && a<=this.second)
        return true
    return false

}
infix fun Int.divBy(a:Int): Rational
{
    return Rational(this.toBigInteger(),a.toBigInteger())
}
infix fun Long.divBy(a:Long): Rational
{

    return Rational(BigInteger.valueOf(this),BigInteger.valueOf(a))
}
infix fun BigInteger.divBy(a:BigInteger): Rational
{
    return Rational(this,a)
}
fun String.toRational(): Rational {

    return if (this.length>1 && this.contains("/")) {
        val (numerator, denominator) = this.split("/")
        Rational(numerator.toBigInteger(), denominator.toBigInteger())
    }
    else
        Rational(this.toBigInteger(),1.toBigInteger())
}

