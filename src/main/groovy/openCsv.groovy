import au.com.bytecode.opencsv.CSVReader
import au.com.bytecode.opencsv.CSVWriter

// Data file comes from : http://www.transtats.bts.gov/DL_SelectFields.asp?Table_ID=236&DB_Short_Name=On-Time

// Proposed features:

// Aggregation
    // grouping over columns
    // entire dataset
// average/min/max
// histograms

// Traveller: optimize X based on Y
// airline/flight for shortest travel time based on departure date
// airline/flight for least delay based
// airline least prone to delay

def TEST_FILE_NAME = './../resources/test.csv'
//def TEST_OUTPUT_FILE_NAME = 'testOut.csv'
def aggregateBy = 'DEP_TIME_BLK'
// TODO: need to be able to specify multiple columns to aggregate. For now, it only works w/ one.
def columnsToAggregate = ['DEP_DELAY']

def reader = new CSVReader(new FileReader(new File(TEST_FILE_NAME)))
def header = reader.readNext()

def rows = reader.readAll().collect { row ->
    (0..(row.size()-1)).collectEntries { [header[it], row[it]] }
}

// TODO: need a generic way to filter out rows that don't fit
def filteredRows = rows.findAll{ it.get(columnsToAggregate[0]) =~ /\-?[0-9]+(.\?[0-9]+)?/ }

Map rowsGroupedByDepHour = filteredRows.groupBy { it.get(aggregateBy) }
println "Grouped: " + rowsGroupedByDepHour

rowsGroupedByDepHour.sort().each { departureHour, departures ->
    println "DepartureHour (" + departureHour + ") : "
    def departureDelays = departures.collect {it.get(columnsToAggregate[0])}
    // TODO: need a way to convert certain columns to a particular type, from string data.
    def avgDelayMin = departureDelays.collect{it.toFloat()}.sum() / departureDelays.size()
    println(String.format("Average for this hour: %.2f", avgDelayMin))
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
