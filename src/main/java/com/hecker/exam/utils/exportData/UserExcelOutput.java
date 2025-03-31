package com.hecker.exam.utils.exportData;

import java.io.IOException;
import java.util.List;

import com.hecker.exam.entity.User;
import lombok.Getter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Getter
enum ColumnIndex {
    STT(0),
    FULL_NAME(1),
    USERNAME(2),
    DOB(3),
    TYPE(4),
    GENDER(5),
    PHONE_NUMBER(6),
    MAIL(7),
    HOMETOWN(8);

    final int index;
    ColumnIndex(int index) {
        this.index = index;
    }
}

public class UserExcelOutput {
    private static CellStyle cellStyleFormatNumber = null;
    private static int rowIndex = 0;

    public static Workbook toExcel(List<User> users, String excelFilePath) throws IOException {
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

        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        return workbook;
    }

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

        cell = row.createCell(ColumnIndex.FULL_NAME.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Họ và tên");

        cell = row.createCell(ColumnIndex.USERNAME.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Tài khoản");

        cell = row.createCell(ColumnIndex.DOB.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Ngày sinh");

        cell = row.createCell(ColumnIndex.TYPE.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Loại quân nhân");

        cell = row.createCell(ColumnIndex.GENDER.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Giới tính");

        cell = row.createCell(ColumnIndex.PHONE_NUMBER.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("SĐT");

        cell = row.createCell(ColumnIndex.MAIL.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Mail");

        cell = row.createCell(ColumnIndex.HOMETOWN.getIndex());
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Quê quán");
    }

    // Write data
    private static void writeUser(User user, Row row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");

            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }

        Cell cell = row.createCell(ColumnIndex.STT.getIndex());
        cell.setCellValue(rowIndex);

        cell = row.createCell(ColumnIndex.FULL_NAME.getIndex());
        cell.setCellValue(user.getFullName());

        cell = row.createCell(ColumnIndex.USERNAME.getIndex());
        cell.setCellValue(user.getUsername());

        cell = row.createCell(ColumnIndex.DOB.getIndex());
        cell.setCellValue(user.getDob());

        cell = row.createCell(ColumnIndex.TYPE.getIndex());
        cell.setCellValue(user.getType().toJson());

        cell = row.createCell(ColumnIndex.GENDER.getIndex());
        cell.setCellValue(user.getGender().toJson());

        cell = row.createCell(ColumnIndex.PHONE_NUMBER.getIndex());
        cell.setCellValue(user.getPhoneNumber());

        cell = row.createCell(ColumnIndex.MAIL.getIndex());
        cell.setCellValue(user.getMail());

        cell = row.createCell(ColumnIndex.HOMETOWN.getIndex());
        cell.setCellValue(user.getHometown());
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
        return cellStyle;
    }

    // Auto resize column width
    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }
}