package com.ivanxc.netcracker.lab.analytics.visualisation;

import com.ivanxc.netcracker.lab.analytics.PerformanceDetails;
import com.lowagie.text.*;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.alignment.VerticalAlignment;
import com.lowagie.text.pdf.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import com.lowagie.text.Document;

public class DocumentReporter {
    private static String outputDirectory = "";

    @SafeVarargs
    public static void generatePdfReport(List<PerformanceDetails>... performanceDetails) {
        Document document = new Document(PageSize.A4);

        try {
            String filename = generateFilename("pdf");
            PdfWriter writer = PdfWriter
                .getInstance(document, new FileOutputStream(filename));

            document.open();

            for(List<PerformanceDetails> collectionPerformanceDetails : performanceDetails) {
                PdfPTable table = generateTable(document, collectionPerformanceDetails);

                document.add(table);
                document.add(new Paragraph(" "));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.close();
    }

    public static PdfPTable generateTable(Document document, List<PerformanceDetails> performanceDetails) {
        List<String> collections = performanceDetails
            .stream()
            .map(PerformanceDetails::getCollectionName)
            .distinct()
            .collect(Collectors.toList());

        List<Integer> elements = performanceDetails
            .stream()
            .map(PerformanceDetails::getElements)
            .distinct()
            .collect(Collectors.toList());

        List<String> operations = performanceDetails
            .stream()
            .map(PerformanceDetails::getOperation)
            .sorted(String::compareTo)
            .distinct()
            .collect(Collectors.toList());

        int numberOfColumns = operations.size() + 2;
        float width = document.getPageSize().getWidth();
        Font fontSize = FontFactory.getFont(FontFactory.HELVETICA, 10);

        PdfPCell headerCell = null;
        PdfPTable table = new PdfPTable(numberOfColumns);

        table.getDefaultCell().setBorder(2);
        table.setTotalWidth(width - 72);
        table.setLockedWidth(true);

        // Add header
        headerCell = new PdfPCell(new Phrase("Collection", fontSize));
        headerCell.setHorizontalAlignment(HorizontalAlignment.CENTER.getId());
        headerCell.setVerticalAlignment(VerticalAlignment.CENTER.getId());
        headerCell.setRowspan(2);
        table.addCell(headerCell);

        headerCell = new PdfPCell(new Phrase("Elements", fontSize));
        headerCell.setHorizontalAlignment(HorizontalAlignment.CENTER.getId());
        headerCell.setVerticalAlignment(VerticalAlignment.CENTER.getId());
        headerCell.setRowspan(2);
        table.addCell(headerCell);

        headerCell = new PdfPCell(new Phrase("Operation", fontSize));
        headerCell.setHorizontalAlignment(HorizontalAlignment.CENTER.getId());
        headerCell.setVerticalAlignment(VerticalAlignment.CENTER.getId());
        headerCell.setColspan(numberOfColumns - 2);
        table.addCell(headerCell);


        // Mapping an operation to a column index to insert
        // values into the table in the correct order.
        Map<String, Integer> operationToColumnIndex = new HashMap<>();
        int idx = 0;

        // Add operations title to table
        for(String operation : operations) {
            headerCell = new PdfPCell(new Phrase(operation, fontSize));
            headerCell.setHorizontalAlignment(HorizontalAlignment.CENTER.getId());
            headerCell.setVerticalAlignment(VerticalAlignment.CENTER.getId());
            table.addCell(headerCell);

            operationToColumnIndex.put(operation, idx++);
        }

        // Add data
        for(String collection : collections) {
            // Add collection name cell
            PdfPCell collectionName = new PdfPCell(new Phrase(collection, fontSize));
            collectionName.setVerticalAlignment(VerticalAlignment.CENTER.getId());
            collectionName.setRowspan(elements.size());
            table.addCell(collectionName);

            for(Integer elementsNumber : elements) {
                // Add elements number cell
                PdfPCell elementsNumberCell =
                    new PdfPCell(new Phrase(elementsNumber.toString(), fontSize));
                elementsNumberCell.setHorizontalAlignment(HorizontalAlignment.CENTER.getId());
                elementsNumberCell.setVerticalAlignment(VerticalAlignment.CENTER.getId());
                table.addCell(elementsNumberCell);

                List<PerformanceDetails> currentPerformanceDetails = performanceDetails
                    .stream()
                    .filter(perfDetails ->
                            Objects.equals(perfDetails.getCollectionName(), collection) &&
                            perfDetails.getElements() == elementsNumber)
                    .collect(Collectors.toList());

                Map<Integer, Integer> columnIndexesToTime = new TreeMap<>();
                for(PerformanceDetails details : currentPerformanceDetails) {
                    columnIndexesToTime.put(
                        operationToColumnIndex.get(details.getOperation()),
                        (int) details.getTime()
                    );
                }

                for(Entry<Integer, Integer> columnIndexToTime : columnIndexesToTime.entrySet()) {
                    String time = columnIndexToTime.getValue().toString();

                    PdfPCell timeCell = new PdfPCell(new Phrase(time, fontSize));
                    timeCell.setHorizontalAlignment(HorizontalAlignment.CENTER.getId());
                    timeCell.setVerticalAlignment(VerticalAlignment.CENTER.getId());

                    table.addCell(timeCell);
                }
            }
        }

        return table;
    }

    public static void exportCSV(List<PerformanceDetails> performanceDetailsList)
        throws FileNotFoundException {
        StringBuilder csvSB = new StringBuilder("collection,elements,operation,time\n");
        for(PerformanceDetails performanceDetails : performanceDetailsList) {
            csvSB.append(performanceDetails.csvRepresentation());
        }
        String csv = csvSB.toString().trim();

        String outputFilename = generateFilename("csv");

        File csvOutputFile = new File(outputFilename);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.write(csv);
        }
    }

    private static String generateFilename(String extension) {
        extension = extension.replace(".", "");
        String currentTime = Instant.now().toString().substring(0, 19).replace(':', '-');
        return outputDirectory + "report" + currentTime + "." + extension;
    }

    public static String getOutputDirectory() {
        return outputDirectory;
    }

    public static void setOutputDirectory(String outputDirectory) {
        DocumentReporter.outputDirectory = outputDirectory;
    }
}
