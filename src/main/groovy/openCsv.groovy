import au.com.bytecode.opencsv.CSVReader
import au.com.bytecode.opencsv.CSVParser
import au.com.bytecode.opencsv.CSVWriter

def TEST_FILE_NAME = './../resources/test.csv'
def TEST_OUTPUT_FILE_NAME = 'testOut.csv'

List<String[]> rows = new CSVReader(new FileReader(new File(TEST_FILE_NAME)), CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_ESCAPE_CHARACTER, CSVParser.DEFAULT_QUOTE_CHARACTER, 1).readAll()

List<Float> delayMinutes = rows.drop(1).collect{
    try {
        it[33].toFloat()
    } catch (NumberFormatException e) {
        0
    }}

def avgDelayMin = delayMinutes.sum() / delayMinutes.size()

println(avgDelayMin)

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