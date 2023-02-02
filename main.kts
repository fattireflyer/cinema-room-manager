fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsInRow = readln().toInt()
    val cinema = MutableList(rows) {MutableList<Char>(seatsInRow){'S'}}
    var showMenu = true
    
    while (showMenu) {
        printMenu()
        val choice = readln().toInt()
        when (choice) {
            1 -> showSeats(cinema)
            2 -> buyTicket(cinema)
            3 -> showStatistics(cinema)
            else -> showMenu = false
        }
    }
        
}
fun showStatistics(cinema: MutableList<MutableList<Char>>){
    println("Number of purchased tickets: ${getTicketsSold(cinema)}")
    println("Percentage: ${getPercentage(cinema)}%")
    println("Current income: \$${getCurrentIncome(cinema)}")
    println("Total income: \$${getTotalIncome(cinema)}")
}

fun getTotalIncome (cinema: MutableList<MutableList<Char>>): Int {
    val seats = countSeats(cinema)
    var income = seats * 10
    var rows = cinema.size
    var seatsInRow = cinema[0].size
    if (seats > 60) income = ( rows / 2 * seatsInRow ) * 10 + ((rows - rows / 2) * seatsInRow) * 8 
    return income
}

fun getTicketsSold(cinema: MutableList<MutableList<Char>>): Int {
    return cinema.flatten().filter { it == 'B' }.size
}

fun getPercentage (cinema: MutableList<MutableList<Char>>): String {
    var percentage = 0.00f
    if (getTicketsSold(cinema) != 0) {
        percentage = (getTicketsSold(cinema).toFloat() / countSeats(cinema).toFloat()) * 100.00f
    }   
    return "%.2f".format(percentage)
}

fun getCurrentIncome(cinema: MutableList<MutableList<Char>>): Int {
    var currentIncome = 0
    for(i in 0 until cinema.size) {
        for(j in 0 until cinema[i].size) {
            if (cinema[i][j]== 'B') {
                currentIncome += getTicketPrice(countSeats(cinema), i+1, cinema.size) 
            }
        }
    }
    return currentIncome
    
}

fun showSeats(cinema: MutableList<MutableList<Char>>) {
    print("Cinema:\n  ")
    for(i in 0 until cinema[0].size) print("${i+1} ")
    println("")
    for (i in 0 until cinema.size) println(cinema[i].joinToString(" ", "${i+1} "))
}

fun setSeat(cinema: MutableList<MutableList<Char>>, rowNumber: Int, seatNumber: Int) {
    try {
        if (cinema[rowNumber-1][seatNumber-1] == 'B') {
            throw Exception("That ticket has already been purchased!")      
        } else {
            cinema[rowNumber-1][seatNumber-1] = 'B'      
        }
    } catch (e: IndexOutOfBoundsException) {
        throw Exception("Wrong input!") 
    }    
}

fun buyTicket(cinema: MutableList<MutableList<Char>>) {
    do {
        val seats = countSeats(cinema)
        println("Enter a row number:")
        val rowNumber = readln().toInt()
        println("Enter a seat number in that row:")
        val seatNumber = readln().toInt()

        try {
            setSeat(cinema, rowNumber, seatNumber)
            println("Ticket price: \$${getTicketPrice(seats, rowNumber, cinema.size)}")
            break
        } catch (e: Exception) {
            println(e.message)
        }
    } while (true)
    
}

fun countSeats(cinema: MutableList<MutableList<Char>>): Int {
    return cinema.size * cinema[0].size
}

fun getTicketPrice (seats: Int, rowNumber: Int, rows: Int): Int {
     var ticketPrice = if (seats > 60) { if (rowNumber > rows / 2) 8 else 10} else 10
    return ticketPrice
}

fun printMenu() {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}
