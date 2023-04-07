package movielens.model;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CSVReader {
    BufferedReader reader;
    String delimiter;
    String[]current;
    boolean hasHeader;
    List<String> columnLabels = new ArrayList<>();
    Map<String,Integer> columnLabelsToInt = new HashMap<>();
    int recordLength;

    /**
     *
     * @param filename - nazwa pliku
     * @param delimiter - separator pól
     * @param hasHeader - czy plik ma wiersz nagłówkowy
     */

    public CSVReader(String filename,String delimiter,boolean hasHeader) throws IOException {
        this(new FileReader(filename),delimiter,hasHeader);
    }
    public CSVReader(String filename,String delimiter) throws IOException {
        this(filename,delimiter,true);
    }
    public CSVReader(String filename) throws IOException {
        this(filename,",",true);
    }
    public CSVReader(Reader reader,String delimiter,boolean hasHeader) throws IOException {
        this.reader = new BufferedReader(reader);
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if(hasHeader){
            parseHeader();
        }
    }
    void parseHeader() throws IOException {
        // wczytaj wiersz
        String line  = reader.readLine();
        if(line==null){
            return;
        }
        // podziel na pola
        String[]header = line.split(delimiter);
        // przetwarzaj dane w wierszu
        for(int i=0;i<header.length;i++){
            // dodaj nazwy kolumn do columnLabels i numery do columnLabelsToInt
            columnLabels.add(header[i]);
            columnLabelsToInt.put(header[i],i);

        }
    }
    boolean next() throws IOException {
        String line = reader.readLine();
        if(line==null){
            return false;
        }
        recordLength ++;
        current = line.split(delimiter);
        return true;

    }

    List<String> getColumnLabels(){
        return columnLabels;
    }
    int getRecordLength(){
        return current.length;
    }
    boolean isMissing(int columnIndex){
        if (current.length<=columnIndex || current[columnIndex].isEmpty() || current[columnIndex].equals(""))
        {
            throw new IllegalArgumentException("Brak danych");
        }
        return false;
    }

    boolean isMissing(String columnLabel){
        if (columnLabels.contains(columnLabel) && columnLabelsToInt.containsKey(columnLabel))
            return isMissing(columnLabelsToInt.get(columnLabel));
        throw new IllegalArgumentException("Brak danych");
    }

    String get(int columnIndex){
        return current[columnIndex];
    }
    String get(String columnLabel){
        if (columnLabelsToInt.containsKey(columnLabel)) {
            if (current[columnLabelsToInt.get(columnLabel)].contains("\"")) {
                return current[columnLabelsToInt.get(columnLabel)]+current[columnLabelsToInt.get(columnLabel)+1];
            }

            return current[columnLabelsToInt.get(columnLabel)];
        }
        else{
            return "";
        }
    }

    int getInt(int columnIndex){
        if(current[columnIndex].isEmpty() || current[columnIndex].equals("")){
            return 0;
        }
        //isMissing(columnIndex);
        else return Integer.parseInt(current[columnIndex]);

    }
    int getInt(String columnLabel){
        //isMissing(columnLabel);
        if(current[columnLabelsToInt.get(columnLabel)].isEmpty() || current[columnLabelsToInt.get(columnLabel)].equals("")){
            return 0;
        }
        else return Integer.parseInt(current[columnLabelsToInt.get(columnLabel)]);
//        int c = columnLabelsToInt.get(columnLabel);
//        return Integer.parseInt(current[c]);
    }

    long getLong(int columnIndex){
        String value = current[columnIndex];
        return Long.parseLong(value);

    }

    long getLong(String columnLabel){
            String value = current[columnLabelsToInt.get(columnLabel)];
            return Long.parseLong(value);



    }





}


