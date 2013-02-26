import au.com.bytecode.opencsv.CSVReader
import au.com.bytecode.opencsv.CSVParser

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

List<String[]> rows = new CSVReader(new FileReader(new File(TEST_FILE_NAME)), CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_ESCAPE_CHARACTER, CSVParser.DEFAULT_QUOTE_CHARACTER, 1).readAll()

List ourImportantColums = rows.drop(1).findAll{ it[33] =~ /\-?[0-9]+(.\?[0-9]+)?/ }.collect {
    [it[33], it[37]]
}

Map<String, String[]> rowsGroupedByDepHour = ourImportantColums.groupBy { it[1] }
println "Grouped: " + rowsGroupedByDepHour

rowsGroupedByDepHour.sort().each { departureHour, departures ->
    println "DepartureHour (" + departureHour + ") : "
    def departureDelays = departures.collect {it[0]}
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