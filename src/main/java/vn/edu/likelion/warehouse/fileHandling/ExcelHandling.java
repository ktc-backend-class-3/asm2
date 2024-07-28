package vn.edu.likelion.warehouse.fileHandling;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vn.edu.likelion.warehouse.entity.ProductEntity;
import vn.edu.likelion.warehouse.entity.WarehouseEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/*
 * @Name: ExcelHandling
 * @Description: Xử lý đọc dữ liệu từ file excel để lưu vào database.
 * Xử lý ghi dữ liệu từ database vào file excel.
 * Trong này chủ yếu xử lý đọc ghi file excel, ko liên quan đến xử lý dữ liệu
 */
public class ExcelHandling {

    /*
     * @Name: readExcel
     * @Description: Đọc dữ liệu từ file Excel và trả về danh sách các đối tượng ProductEntity
     * @Input: Ko có đầu vào. Đọc dữ liệu từ file "DanhSachSP.xlsx"
     * @Output: ArrayList<ProductEntity> - Danh sách các đối tượng ProductEntity được đọc từ file Excel
     * @Exception: IOException - nếu xảy ra lỗi trong quá trình đọc file Excel.
     */
    public ArrayList<ProductEntity> readExcel() {
        // Nếu ko cần thiết, thì các đối tượng nên được tạo ngoài vòng for, instance thì tạo trong vòng for
        // Điều này sẽ giúp tối ưu memory cho chương trình, tránh tạo ra quá nhiều đối tượng dư thừa
        ArrayList<ProductEntity> productList = new ArrayList<>();
        ProductEntity productEntity;
        FileInputStream fis = null;
        Workbook workbook = null;
        try {
            fis = new FileInputStream(new File("DanhSachSP.xlsx"));
            workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            // Duyệt các hàng trong sheet
            for (Row row : sheet) {
                // bỏ qua 5 dòng đầu tiên do 5 dòng đó là kiểu tiêu đề, ko phải data
                if (row.getRowNum() < 5) {
                    continue;
                }
                productEntity = new ProductEntity();
                // Duyệt các ô trong hàng
                for (Cell cell : row) {
                    if (cell.getColumnIndex() == 0) {
                        continue;
                    }
                    switch (cell.getColumnIndex()) {
                        case 1:
                            productEntity.setName(cell.getStringCellValue());
                            break;
                        case 2:
                            productEntity.setDescription(cell.getStringCellValue());
                            break;
                        case 3:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                productEntity.setQuantity((int) cell.getNumericCellValue());
                            }
                            break;
                        case 4:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                productEntity.setPrice(cell.getNumericCellValue());
                            }
                            break;
                        default:
                            break;
                    }
                    // Kết thúc 1 row là đã có 1 đối tượng Product, lưu đối tượng đó vào list trước khi duyệt row tiếp theo
                    productList.add(productEntity);
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            try {
                // thường đóng cái gì thì cũng nên cho vào finally để nhỡ có lỗi chương trình nó vẫn close đc
                if (fis != null) fis.close();
                if (workbook != null) workbook.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        // Trả về danh sách sản phẩm sau khi duyệt hết các row trong file excel
        return productList;
    }

    /*
     * @Name: writeExcel
     * @Description: Tạo file Excel với nhiều sheet, mỗi sheet đại diện cho một kho từ danh sách kho cho trước
     * @Input: List<String> warehouseNames - Danh sách tên các kho, trong đó bao gồm danh sách sản phẩm của mỗi kho
     * @Output: File Excel "ReportWarehouse.xlsx" được tạo với các sheet theo tên các kho
     * @Exception: IOException - nếu xảy ra lỗi trong quá trình ghi file Excel
     */
    public void writeExcel(ArrayList<WarehouseEntity> warehouseList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        FileOutputStream fos = null;

        // Duyệt qua danh sách các kho và tạo sheet cho mỗi kho
        for (WarehouseEntity h: warehouseList) {
            // Đặt tên sheet theo tên kho
            Sheet sheet = workbook.createSheet(h.getName());

            // Tạo row đầu tiên làm tiêu đề
            Row headerRow = sheet.createRow(0);
            Cell headerCell = headerRow.createCell(0);  // Cột tên sản phẩm
            headerCell.setCellValue("Name");
            Cell headerCell2 = headerRow.createCell(1); // Cột mô tả sản phẩm
            headerCell2.setCellValue("Description");
            Cell headerCell3 = headerRow.createCell(2); // Cột số lượng sản phẩm
            headerCell3.setCellValue("Quantity");
            Cell headerCell4 = headerRow.createCell(3); // Cột đơn giá sản phẩm
            headerCell4.setCellValue("Price");
            Cell headerCell5 = headerRow.createCell(4); // Cột ngày nhập sản phẩm
            headerCell5.setCellValue("Import date");

            // Tạo row thứ n trở đi để ghi ra thông tin sản phẩm
            // Sắp xếp các cell đúng theo thứ tự row tiêu đề ở trên
            // row tiêu đề bắt đầu từ cell thứ 0 thì row data cũng bắt đầu từ cell thứ 0
            int rowNum = 1;
            for (ProductEntity p: h.getProductList()) {
                // rowNum ở ngoài for vì ta cần bắt đầu từ 1, mỗi lần for thì row sẽ tăng lên 1 dòng
                // còn cellNum ở trong for vì mỗi for là 1 row, cell sẽ luôn bắt đầu từ 0 ở mỗi vòng for
                int cellNum = 0;
                Row dataRow = sheet.createRow(rowNum++);
                Cell dataCell1 = dataRow.createCell(cellNum++);
                dataCell1.setCellValue(p.getName());
                Cell dataCell2 = dataRow.createCell(cellNum++);
                dataCell2.setCellValue(p.getDescription());
                Cell dataCell3 = dataRow.createCell(cellNum++);
                dataCell3.setCellValue(p.getQuantity());
                Cell dataCell4 = dataRow.createCell(cellNum++);
                dataCell4.setCellValue(p.getPrice());
                Cell dataCell5 = dataRow.createCell(cellNum++);
                dataCell5.setCellValue(p.getCreated_date());
            }
        }

        try {
            // Tạo file
            fos = new FileOutputStream("ReportWarehouse.xlsx");
            // Ghi cái workbook ở trên ra file
            workbook.write(fos);
            System.out.println("Đã tạo file report thành công!");
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            try {
                // thường đóng cái gì thì cũng nên cho vào finally để nhỡ có lỗi chương trình nó vẫn close đc
                if (fos != null) fos.close();
                if (workbook != null) workbook.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
