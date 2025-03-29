package com.hecker.exam.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hecker.exam.dto.request.user.UserCreationRequest;
import com.hecker.exam.entity.enums.Gender;
import com.hecker.exam.entity.enums.Type;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelInputService {
    public static void main(String[] args) throws IOException {
        final String excelFilePath = "input.xlsx";
//        final List<UserCreationRequest> requests = readUserRequestFromExcel(excelFilePath);
//        for (UserCreationRequest request : requests) {
//            System.out.println(request);
//        }
    }

    public static List<UserCreationRequest> readUserRequestFromExcel(MultipartFile excelFile) throws IOException {
        List<UserCreationRequest> listUsers = new ArrayList<>();

        // Get file
        InputStream inputStream = excelFile.getInputStream();

        // Get workbook
        Workbook workbook = getWorkbook(excelFile);

        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }

            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            // Read cells and set value for book object
            UserCreationRequest request = new UserCreationRequest();
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case 1:
                        request.setFullName((String) getCellValue(cell));
                        break;
                    case 2:
                        request.setDob(LocalDate.of(1900, 1, 1).plusDays(((Double) getCellValue(cell)).longValue() - 2));
                        break;
                    case 3:
                        request.setGender(Gender.fromString((String) getCellValue(cell)));
                        break;
                    case 4:
                        request.setPhoneNumber((String) getCellValue(cell));
                        break;
                    case 5:
                        request.setMail((String) getCellValue(cell));
                        break;
                    case 6:
                        request.setType(Type.fromString((String) getCellValue(cell)));
                        break;
                    case 7:
                        request.setHometown((String) getCellValue(cell));
                        break;
                }
            }
            listUsers.add(request);
        }

        workbook.close();
        inputStream.close();

        return listUsers;
    }

    // Get Workbook
    private static Workbook getWorkbook(MultipartFile excelFile) throws IOException {
        Workbook workbook = null;
        if (excelFile.getOriginalFilename().endsWith("xlsx")) {
            workbook = new XSSFWorkbook(excelFile.getInputStream());
        } else if (excelFile.getOriginalFilename().endsWith("xls")) {
            workbook = new HSSFWorkbook(excelFile.getInputStream());
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellType();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }
}
