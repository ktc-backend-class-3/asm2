package vn.edu.likelion.warehouse.controller;

import vn.edu.likelion.warehouse.application.WarehouseApplication;
import vn.edu.likelion.warehouse.entity.ProductEntity;
import vn.edu.likelion.warehouse.entity.WarehouseEntity;
import vn.edu.likelion.warehouse.fileHandling.ExcelHandling;
import vn.edu.likelion.warehouse.model.WarehouseDTO;
import vn.edu.likelion.warehouse.service.WarehouseService;
import vn.edu.likelion.warehouse.service.impl.WarehouseServiceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

/*
 * @Name: WarehouseController
 * @Description: Class điều khiển các chức năng liên quan tới Warehouse
 * Các controller chủ yếu xử lý dữ liệu đầu vào, sau đó gửi cho các service xử lý tiếp
 */
public class WarehouseController {

    // Khởi tạo đối tượng service để tiếp tục xử lý sau khi controller xử lý xong dữ liệu đầu vào
    private WarehouseService warehouseService = new WarehouseServiceImpl();

    /*
     * @Name: createWarehouse
     * @Description: Admin tạo kho mới
     * @Input: Ko có đầu vào. Đọc dữ liệu từ console
     * @Output: Ko có dữ liệu trả về. Kho đc lưu vào Database
     * @Exception: Ko có exception cần kiểm tra
     */
    public void createWarehouse() {
        WarehouseDTO warehouseModel = new WarehouseDTO();
        System.out.print("Nhập tên kho: ");
        String warehouseName = WarehouseApplication.scanner.nextLine().trim();
        if (warehouseName.isEmpty()) {
            System.out.println("Tên kho không được để trống.");
            return;
        }
        System.out.print("Nhập địa chỉ kho: ");
        String warehouseAddress = WarehouseApplication.scanner.nextLine().trim();
        if (warehouseAddress.isEmpty()) {
            System.out.println("Địa chỉ kho không được để trống.");
            return;
        }

        warehouseModel.setName(warehouseName);
        warehouseModel.setAddress(warehouseAddress);
        warehouseModel.setCreated_by(WarehouseApplication.user.getId());
        warehouseModel.setCreated_date(Date.valueOf(LocalDate.now()));

        System.out.println("Đang trong quá trình tạo kho, vui lòng chờ trong giây lát.");
        // Gửi dữ liệu (ở đây là warehouseModel) đã đc xử lý tới service create để tạo kho mới
        warehouseService.create(warehouseModel);
    }

    /*
     * @Name: updateWarehouse
     * @Description: Admin cập nhật thông tin kho
     * @Input: Ko có đầu vào. Đọc dữ liệu từ console
     * @Output: Ko có dữ liệu trả về. Kho đc cập nhật vào Database
     * @Exception: Ko có exception cần kiểm tra
     */
    public void updateWarehouse() {
        // cái đoạn hiển thị danh sách kho giống 1 số functino khác nên tách ra để các function dùng chung cho đỡ phải viết nhiều
        listWarehouse();
        System.out.print("Vui lòng nhập mã kho để sửa: ");
        String warehouseID = WarehouseApplication.scanner.nextLine().trim();
        int warehouse_id;
        try {
            warehouse_id = Integer.parseInt(warehouseID);
        } catch (NumberFormatException e) {
            System.out.println("Mã kho phải là một số nguyên.");
            return;
        }
        // Gửi dữ liệu (ở đây là warehouse_id) tới service để tìm kho theo id
        WarehouseEntity warehouseEntity = warehouseService.findById(warehouse_id);
        if (warehouseEntity != null) {
            WarehouseDTO warehouseModel = new WarehouseDTO();
            System.out.print("Nhập tên kho: ");
            String warehouseName = WarehouseApplication.scanner.nextLine().trim();
            if (warehouseName.isEmpty()) {
                System.out.println("Tên kho không được để trống.");
                return;
            }
            System.out.print("Nhập địa chỉ kho: ");
            String warehouseAddress = WarehouseApplication.scanner.nextLine().trim();
            if (warehouseAddress.isEmpty()) {
                System.out.println("Địa chỉ kho không được để trống.");
                return;
            }
            warehouseModel.setId(warehouseEntity.getId());
            warehouseModel.setName(warehouseEntity.getName());
            warehouseModel.setAddress(warehouseEntity.getAddress());
            warehouseModel.setUpdated_by(WarehouseApplication.user.getId());
            warehouseModel.setUpdated_date(Date.valueOf(LocalDate.now()));

            System.out.println("Đang trong quá trình cập nhật, vui lòng chờ trong giây lát.");
            // Gửi dữ liệu (ở đây là warehouseModel) đã đc xử lý tới service update để cập nhật thông tin kho
            warehouseService.update(warehouseModel);
        }
    }

    /*
     * @Name: deleteWarehouse
     * @Description: Admin xoá kho
     * @Input: Ko có đầu vào. Đọc dữ liệu từ console
     * @Output: Ko có dữ liệu trả về. Kho bị xoá khỏi Database
     * @Exception: Ko có exception cần kiểm tra
     */
    public void deleteWarehouse() {
        // cái đoạn hiển thị danh sách kho giống 1 số functino khác nên tách ra để các function dùng chung cho đỡ phải viết nhiều
        listWarehouse();
        System.out.print("Vui lòng nhập mã kho để xoá: ");
        String warehouseID = WarehouseApplication.scanner.nextLine().trim();
        int warehouse_id;
        try {
            warehouse_id = Integer.parseInt(warehouseID);
        } catch (NumberFormatException e) {
            System.out.println("Mã kho phải là một số nguyên.");
            return;
        }

        System.out.println("Đang trong quá trình xoá kho, vui lòng chờ trong giây lát.");
        // Gửi dữ liệu (ở đây là id kho) tới service delete để xoá kho khỏi database
        warehouseService.remove(warehouse_id);
    }

    /*
     * @Name: findWarehouse
     * @Description: Tìm thông tin kho theo ID, bao gồm cả sản phẩm trong kho đó
     * @Input: Ko có đầu vào. Đọc dữ liệu từ console
     * @Output: Ko có dữ liệu trả về. Hiển thị ra console thông tin kho và danh sách sản phẩm của kho đó
     * @Exception: Ko có exception cần kiểm tra
     */
    public void findById() {
        // cái đoạn hiển thị danh sách kho giống 1 số functino khác nên tách ra để các function dùng chung cho đỡ phải viết nhiều
        listWarehouse();
        int warehouse_id;
        if (WarehouseApplication.user.getWarehouse() != null) {
            warehouse_id = WarehouseApplication.user.getWarehouse().getId();
        } else {
            System.out.print("Vui lòng nhập mã kho để xoá: ");
            String warehouseID = WarehouseApplication.scanner.nextLine().trim();
            try {
                warehouse_id = Integer.parseInt(warehouseID);
            } catch (NumberFormatException e) {
                System.out.println("Mã kho phải là một số nguyên.");
                return;
            }
        }
        // Gửi dữ liệu (ở đây là id kho) tới service để tìm kho
        WarehouseEntity warehouseEntity = warehouseService.findById(warehouse_id);
        if (warehouseEntity != null) {
            System.out.println("Danh sách sản phẩm trong kho: " + warehouseEntity.getName() + "; Địa chỉ: " + warehouseEntity.getAddress());
            for (ProductEntity p: warehouseEntity.getProductList()) {
                System.out.println("================> Sản phẩm " + p.getName());
                System.out.println("Mô tả sản phẩm: " + p.getName());
                System.out.println("Số lượng sản phẩm: " + p.getQuantity());
                System.out.println("Đơn giá sản phẩm: " + p.getPrice());
                System.out.println("Ngày nhập: " + p.getCreated_date());
            }
        }
    }

    /*
     * @Name: importProduct
     * @Description: Nhập sản phẩm vào kho.
     * Trong function này sẽ cần sử dụng chức năng đọc excel từ class ExcelHandling
     * @Input: Ko có đầu vào. Đọc dữ liệu từ console
     * @Output: Ko có dữ liệu trả về. Danh sách sản phẩm đc nhập vào kho tương ứng
     * @Exception: Ko có exception cần kiểm tra
     */
    public void importProduct() {
        // cái đoạn hiển thị danh sách kho giống 1 số functino khác nên tách ra để các function dùng chung cho đỡ phải viết nhiều
        listWarehouse();
        int warehouse_id;
        if (WarehouseApplication.user.getWarehouse() != null) {
            warehouse_id = WarehouseApplication.user.getWarehouse().getId();
        } else {
            System.out.print("Vui lòng nhập mã kho để nhập sản phẩm: ");
            String warehouseID = WarehouseApplication.scanner.nextLine().trim();
            try {
                warehouse_id = Integer.parseInt(warehouseID);
            } catch (NumberFormatException e) {
                System.out.println("Mã kho phải là một số nguyên.");
                return;
            }
        }
        ExcelHandling excelHandling = new ExcelHandling();
        // readExcel() trả về 1 list product đọc đc từ file, khúc này chưa insert vào database
        ArrayList<ProductEntity> productList = excelHandling.readExcel();
        if (productList.isEmpty()) System.out.println("Có sản phẩm nào mới đâu???");
        else {
            System.out.println("Đang trong quá trình nhập kho, vui lòng chờ trong giây lát.");
            // Gửi dữ liệu (ở đây là danh sách product ở trên và id kho) tới service để lưu product vào database với kho tương ứng
            warehouseService.importProduct(productList, warehouse_id);
        }
    }

    /*
     * @Name: reportWarehouse
     * @Description: Thống kê toàn bộ kho trong database
     * Trong function này sẽ cần sử dụng chức năng ghi excel từ class ExcelHandling
     * @Input: Ko có đầu vào.
     * @Output: Ko có dữ liệu trả về. Danh sách sản phẩm đc nhập vào kho tương ứng
     * @Exception: Ko có exception cần kiểm tra
     */
    public void reportWarehouse() {
        ArrayList<WarehouseEntity> warehouseList = new ArrayList<>();
        ExcelHandling excelHandling = new ExcelHandling();
        // Nếu laf user thì check xem user đó đã có kho chưa
        if (WarehouseApplication.user.getRole().getName().equals("User")) {
            if (WarehouseApplication.user.getWarehouse() != null) {
                warehouseList.add(WarehouseApplication.user.getWarehouse());
                excelHandling.writeExcel(warehouseList);
            } else System.out.println("Bạn chưa có kho. Vui lòng liên hệ quản trị viên.");
        } else {
            // Gọi tới service để lấy danh sách toàn bộ
            warehouseList = warehouseService.findAll();
            excelHandling.writeExcel(warehouseList);
        }
    }
    /*
     * @Name: listWarehouse
     * @Description: Đoạn code mà 1 số function đều dùng chung
     * @Input: K có đầu vào
     * @Output: Ko có dữ liệu trả về. In ra console danh sách kho
     * @Exception: Ko có exception cần kiểm tra
     */
    private void listWarehouse() {
        ArrayList<WarehouseEntity> warehouseList = warehouseService.findAll();
        System.out.println("Danh sách kho có trong hệ thống:");
        for (WarehouseEntity h: warehouseList) {
            System.out.println("Warehouse ID: " + h.getId() + "; Name: " + h.getName()
                    + "; Address: " + h.getAddress());
        }
    }

}
