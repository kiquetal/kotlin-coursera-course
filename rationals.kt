package rationals

import java.lang.IllegalArgumentException
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext


data class Rational(var numerator:BigInteger, var denominator:BigInteger)
{
    init {
        if (denominator.equals(0))
            throw IllegalArgumentException()

        if (denominator<0.toBigInteger())
        {
            numerator=numerator.multiply(-1.toBigInteger())
            denominator=denominator.multiply(-1.toBigInteger())
        }

    }


    operator fun plus(a: Rational): Rational {

        val (numerator, denominator) = a.gcd()

        val d = when (this.denominator) {
            denominator -> this.denominator
            else -> this.denominator.multiply(denominator)
        }


        return when {
            d > denominator -> Rational(
                this.numerator.multiply(denominator).plus(numerator.multiply(this.denominator)),
                d
            )
            else -> Rational(this.numerator.plus(numerator), d)
        }.gcd()

    }

    override fun toString(): String {

        return if (this.denominator==1.toBigInteger())
            this.numerator.toString()
        else {
            val calculated = this.gcd()
            if (calculated.denominator==1.toBigInteger())
                return calculated.numerator.toString()
            return "${calculated.numerator}/${calculated.denominator}"
        }
    }

    operator fun minus(a: Rational): Rational {

        val (numerator, denominator) = a

        val d = when (this.denominator) {
            denominator -> this.denominator
            else -> this.denominator.multiply(denominator)
        }


        return when {
            d > denominator -> Rational(
                this.numerator.multiply(denominator).minus(numerator.multiply(this.denominator)),
                d
            )
            else -> Rational(this.numerator.plus(numerator), d)
        }.gcd()

    }

    operator fun div(a:Rational):Rational{

        val numerator= this.numerator.multiply(a.denominator)
        val denominator = this.denominator.multiply(a.numerator)

        return Rational(numerator,denominator).gcd()
    }

    operator fun times(a:Rational):Rational {

        val numerator= this.numerator.multiply(a.numerator)
        val denominator = this.denominator.multiply(a.denominator)

        return Rational(numerator,denominator).gcd()
    }

    override fun equals(other: Any?): Boolean {

        if (other is Rational){
            val (numerator,denominator) = other.gcd()

            val calculated=this.gcd()

            if (numerator == calculated.numerator && denominator == calculated.denominator) {
                return true
            }
        }
        return false
    }
    operator fun compareTo(a:Rational): Int {

        val div1= this.numerator.toDouble() / this.denominator.toDouble()
        val div2=a.numerator.toDouble() / a.denominator.toDouble()
        if (div1 > div2)
            return 1
        if (div1<div2)
            return -1
        return 0
    }
    operator fun unaryMinus():Rational {

        return Rational(this.numerator.multiply(-1.toBigInteger()),this.denominator).gcd()
    }


  private fun gcd():Rational {

      val gcd=this.numerator.gcd(this.denominator)
      val numerator = this.numerator.divide(gcd)
      val denominator =this.denominator.divide(gcd)
      return Rational(numerator,denominator )

  }
    operator fun rangeTo(a:Rational):Pair<Rational,Rational>{

        return Pair(this,a)

    }




}




fun main() {

    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)


    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
     println(-1 divBy 2 == negation)
    println((2 divBy 1).toString() == "2")

    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half <= twoThirds)
    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)


    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)

}

