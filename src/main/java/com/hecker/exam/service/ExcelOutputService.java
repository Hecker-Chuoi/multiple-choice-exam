package com.hecker.exam.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.hecker.exam.entity.enums.Role;
import com.hecker.exam.entity.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelOutputService {
    enum ColumnIndex {
        STT(0),
        ID(1),
        USERNAME(2),
        FULL_NAME(3),
        DOB(4),
        GENDER(5),
        PHONE_NUMBER(6),
        MAIL(7),
        UNIT(8),
        HOMETOWN(9);

        final int index;
        ColumnIndex(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    private static CellStyle cellStyleFormatNumber = null;
    private static int rowIndex = 0;

//    public static void main(String[] args){
//        List<User> users = new ArrayList<>();
//        users.add(new User(1, "hecker", "423424", "Nguyễn Văn Hải", LocalDate.of(2004, 10, 19), "Nam", "0123456789", "nguyentienht1910@gmail.com", "Phao binh", "Hà Nội", Role.CANDIDATE));
//        users.add(new User(1, "hecker", "423424", "Nguyễn Văn Hải", LocalDate.of(2004, 10, 19), "Nam", "0123456789", "nguyentienht1910@gmail.com", "Phao binh", "Hà Nội", Role.CANDIDATE));
//        users.add(new User(1, "hecker", "423424", "Nguyễn Văn Hải", LocalDate.of(2004, 10, 19), "Nam", "0123456789", "nguyentienht1910@gmail.com", "Phao binh", "Hà Nội", Role.CANDIDATE));
//        users.add(new User(1, "hecker", "423424", "Nguyễn Văn Hải", LocalDate.of(2004, 10, 19), "Nam", "0123456789", "nguyentienht1910@gmail.com", "Phao binh", "Hà Nội", Role.CANDIDATE));
//
//        try {
//            writeExcel(users, "public/output.xlsx");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void writeExcel(List<User> users, String excelFilePath) throws IOException {
        // Create Workbook
        Workbook workbook = getWorkbook(excelFilePath);

        // Create sheet
        Sheet sheet = workbook.createSheet("Books"); // Create sheet with sheet name

        rowIndex = 0;

        // Write header
        writeHeader(sheet, rowIndex);

        // Write data
        rowIndex++;
        for (User user : users) {
            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data on row
            writeUser(user, row);
            rowIndex++;
        }

        // Write footer
//        writeFooter(sheet, rowIndex);

        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Create file excel
        createOutputFile(workbook, excelFilePath);
        System.out.println("Done!!!");
    }

//    // Create dummy data
//    private static List<Book> getBooks() {
//        List<Book> listBook = new ArrayList<>();
//        Book book;
//        for (int i = 1; i <= 5; i++) {
//            book = new Book(i, "Book " + i, 5, i * 2, i * 1000);
//            listBook.add(book);
//        }
//        return listBook;
//    }

    // Create workbook
    private static Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Write header with format
    private static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(ColumnIndex.STT.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("STT");

        cell = row.createCell(ColumnIndex.ID.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Account ID");

        cell = row.createCell(ColumnIndex.USERNAME.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Tài khoản");

        cell = row.createCell(ColumnIndex.FULL_NAME.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Họ và tên");

        cell = row.createCell(ColumnIndex.DOB.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Ngày sinh");

        cell = row.createCell(ColumnIndex.GENDER.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Giới tính");

        cell = row.createCell(ColumnIndex.PHONE_NUMBER.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("SĐT");

        cell = row.createCell(ColumnIndex.MAIL.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Mail");

        cell = row.createCell(ColumnIndex.UNIT.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Đơn vị");

        cell = row.createCell(ColumnIndex.HOMETOWN.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Quê quán");
    }

    // Write data
    private static void writeUser(User user, Row row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");

            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }

        Cell cell = row.createCell(ColumnIndex.STT.getIndex());
        cell.setCellValue(rowIndex);

        cell = row.createCell(ColumnIndex.ID.getIndex());
        cell.setCellValue(user.getUserId());

        cell = row.createCell(ColumnIndex.USERNAME.getIndex());
        cell.setCellValue(user.getUsername());

        cell = row.createCell(ColumnIndex.FULL_NAME.getIndex());
        cell.setCellValue(user.getFullName());

        cell = row.createCell(ColumnIndex.DOB.getIndex());
        cell.setCellValue(user.getDob());

        cell = row.createCell(ColumnIndex.GENDER.getIndex());
        cell.setCellValue(user.getGender());

        cell = row.createCell(ColumnIndex.PHONE_NUMBER.getIndex());
        cell.setCellValue(user.getPhoneNumber());

        cell = row.createCell(ColumnIndex.MAIL.getIndex());
        cell.setCellValue(user.getMail());

        cell = row.createCell(ColumnIndex.UNIT.getIndex());
        cell.setCellValue(user.getUnit());

        cell = row.createCell(ColumnIndex.HOMETOWN.getIndex());
        cell.setCellValue(user.getHometown());

//        // Create cell formula
//        // totalMoney = price * quantity
//        cell = row.createCell(COLUMN_INDEX_TOTAL, CellType.FORMULA);
//        cell.setCellStyle(cellStyleFormatNumber);
//        int currentRow = row.getRowNum() + 1;
//        String columnPrice = CellReference.convertNumToColString(COLUMN_INDEX_PRICE);
//        String columnQuantity = CellReference.convertNumToColString(COLUMN_INDEX_QUANTITY);
//        cell.setCellFormula(columnPrice + currentRow + "*" + columnQuantity + currentRow);
    }

    // Create CellStyle for header
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    // Write footer
//    private static void writeFooter(Sheet sheet, int rowIndex) {
//        // Create row
//        Row row = sheet.createRow(rowIndex);
//        Cell cell = row.createCell(COLUMN_INDEX_TOTAL, CellType.FORMULA);
//        cell.setCellFormula("SUM(E2:E6)");
//    }

    // Auto resize column width
    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }

}