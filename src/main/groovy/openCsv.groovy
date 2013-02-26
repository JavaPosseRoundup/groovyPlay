import au.com.bytecode.opencsv.CSVReader
import au.com.bytecode.opencsv.CSVWriter

def TEST_FILE_NAME = './../resources/test.csv'
def TEST_OUTPUT_FILE_NAME = 'testOut.csv'

def reader = new CSVReader(new FileReader(new File(TEST_FILE_NAME)))
def header = reader.readNext()

def rows = reader.readAll().collect { row ->
    (0..(row.size()-1)).collectEntries { [header[it], row[it]] }
}

Map rowsGroupedByDepHour = rows.groupBy { it.DEP_TIME_BLK }
println "Grouped: " + rowsGroupedByDepHour

rowsGroupedByDepHour.each { departureHour, departures ->
    println "DepartureHour (" + departureHour + ") : "
    def departureDelays = departures.collect {it.DEP_DELAY}
    println departureDelays
    def avgDelayMin = departureDelays.findAll{ delay -> delay =~ /\-?[0-9]+(.\?[0-9]+)?/ }.collect{it.toFloat()}.sum() / departureDelays.size()
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
