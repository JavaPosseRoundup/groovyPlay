import au.com.bytecode.opencsv.CSVReader
import au.com.bytecode.opencsv.CSVWriter

// Agregation
    // grouping over columns
    // entire dataset
// average/min/max
// histograms

// Traveller: optimize X based on Y
// airline/flight for shortest travel time based on departure date
// airline/flight for least delay based
// airline least prone to delay

def TEST_FILE_NAME = './../resources/test.csv'
def TEST_OUTPUT_FILE_NAME = 'testOut.csv'

def reader = new CSVReader(new FileReader(new File(TEST_FILE_NAME)))
def header = reader.readNext()

def rows = reader.readAll().findAll{ it[33] =~ /\-?[0-9]+(.\?[0-9]+)?/ }.collect { row ->
    (0..(row.size()-1)).collectEntries { [header[it], row[it]] }
}

Map rowsGroupedByDepHour = rows.groupBy { it.DEP_TIME_BLK }
println "Grouped: " + rowsGroupedByDepHour

rowsGroupedByDepHour.sort().each { departureHour, departures ->
    println "DepartureHour (" + departureHour + ") : "
    def departureDelays = departures.collect {it.DEP_DELAY}
    println departureDelays
    def avgDelayMin = departureDelays.collect{it.toFloat()}.sum() / departureDelays.size()
    println "Average for this hour: " + avgDelayMin
}


//def avgDelayMin = ourImportantColums.sum() / ourImportantColums.size()
//println(avgDelayMin)

//File output = new File(TEST_OUTPUT_FILE_NAME)
//if (output.exists())
//{
//    output.delete()
//}
//output.createNewFile()
//output.withWriter { writer ->
//    new CSVWriter(writer).writeAll(rowsOver100)
//
//}
