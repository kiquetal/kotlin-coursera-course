package taxipark

import java.util.HashSet
import java.util.function.BiFunction
import java.util.function.Consumer
import java.util.function.Function

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =

    allDrivers.filter { driver ->
        trips.none { trip ->
            trip.driver == driver
        }
    }.toSet()


/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> {

    val newHashMap:HashMap<Passenger,Int> = hashMapOf();
    val allPassenger:HashSet<Passenger> = hashSetOf();
    val entriesMap = trips.fold(newHashMap){
            acc, trip ->
        val passenger=trip.passengers
        passenger.forEach {
            val trips = acc.getOrElse(it) { 0 }
            acc.put(it, trips + 1)
            allPassenger.add(it)

        }
        acc
    }
    if (minTrips<1) return allPassengers

    return entriesMap.filterValues { it>=minTrips }.keys
}

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> {

    val newHashMap:HashMap<Passenger,ArrayList<Driver>> = hashMapOf();
//    print("driver"+ driver)
    val entriesMap = trips.fold(newHashMap){
            acc, trip ->
        val passenger=trip.passengers
        val n=passenger.map {
            if (acc.containsKey(it))
            {
                if (trip.driver.name == driver.name) {
                    val l = acc.get(it);
                    if (l != null) {
                        l.add(driver)
                    }
                    if (l != null) {
                        acc.put(it, l)
                    }
                }
            }
            else
            {
                if (trip.driver.name == driver.name)
                    acc.put(it, arrayListOf(driver))
            }

        }
        acc
    }

    println(entriesMap)
    return entriesMap.filterValues { it.size>1}.keys
}

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =   allPassengers.filter { p ->
    trips.count { t ->
        p in t.passengers && t.discount != null
    } > trips.count { t ->
        p in t.passengers && t.discount == null
    }
}.toSet()


/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    if (trips.isEmpty())
        return null

    return trips.groupBy {
        val start = it.duration / 10 * 10
        val end = start + 9
        start..end
    }.maxBy { (_, group) -> group.size }?.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    val totalIncome = trips.sumByDouble { it.cost }

    if (totalIncome.equals(0.0)) {
        return false
    }

    val topDriversIncome =
        trips.groupBy { it.driver }
            .map { (_, list) ->
                list.sumByDouble { it.cost }
            }
            .sortedDescending()

    val top20 = (allDrivers.size * 0.2).toInt()

    return topDriversIncome.take(top20).sum() >= totalIncome * 0.8
}
