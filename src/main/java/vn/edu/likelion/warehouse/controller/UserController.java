package vn.edu.likelion.warehouse.controller;

import vn.edu.likelion.warehouse.application.WarehouseApplication;
import vn.edu.likelion.warehouse.entity.UserEntity;
import vn.edu.likelion.warehouse.model.UserDTO;
import vn.edu.likelion.warehouse.service.UserService;
import vn.edu.likelion.warehouse.service.impl.UserServiceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;

/*
 * @Name: UserController
 * @Description: Class điều khiển các chức năng liên quan tới User
 * Các controller chủ yếu xử lý dữ liệu đầu vào, sau đó gửi cho các service xử lý tiếp
 */
public class UserController {

    // Khởi tạo đối tượng service để tiếp tục xử lý sau khi controller xử lý xong dữ liệu đầu vào
    private final UserService userService = new UserServiceImpl();
    // Đối tượng userList để giả lập session, lưu lại danh sách nhân viên
    // Tuy nhiên cần lưu ý khi admin thêm user mới, thì list này sẽ ko đc reload data mới
    private ArrayList<UserEntity> userList = null;

    /*
     * @Name: createUser
     * @Description: Admin tạo user mới
     * @Input: K có đầu vào. Đọc dữ liệu từ console
     * @Output: Ko có dữ liệu trả về. Nhân viên đc lưu vào Database
     * @Exception: Ko có exception cần kiểm tra
     */
    public void createUser() {
        UserDTO userModel = new UserDTO();
        System.out.print("Nhập tên nhân viên: ");
        String username = WarehouseApplication.scanner.nextLine().trim();
        if (username.isEmpty()) {
            System.out.println("Tên nhân viên không được để trống.");
            return;
        }
        System.out.print("Nhập mật khẩu: ");
        String password = WarehouseApplication.scanner.nextLine().trim();
        if (password.isEmpty()) {
            System.out.println("Mật khẩu không được để trống.");
            return;
        }
        String passwordEncoding = Base64.getEncoder().encodeToString(password.getBytes());

        userModel.setUsername(username);
        userModel.setPassword(passwordEncoding);
        userModel.setRole_id(2);
        userModel.setCreated_by(WarehouseApplication.user.getId());
        userModel.setCreated_date(Date.valueOf(LocalDate.now()));

        System.out.println("Đang trong quá trình đăng ký, vui lòng chờ trong giây lát.");
        // Gửi dữ liệu (ở đây là userModel) đã đc xử lý tới service create để tạo nhân viên mới
        userService.create(userModel);
    }

    /*
     * @Name: updateUser
     * @Description: Admin cập nhật kho cho nhân viên
     * @Input: K có đầu vào. Đọc dữ liệu từ console
     * @Output: Ko có dữ liệu trả về. Nhân viên đc cập nhật kho và lưu vào Database
     * @Exception: Ko có exception cần kiểm tra
     */
    public void updateUser() {
        // cái đoạn hiển thị danh sách user giống vs bên function delete nên tách ra để 2 function dùng chung cho đỡ phải viết nhiều
        listUser();
        System.out.print("Vui lòng nhập mã nhân viên để gán kho: ");
        String userID = WarehouseApplication.scanner.nextLine().trim();
        int user_id;
        try {
            user_id = Integer.parseInt(userID);
        } catch (NumberFormatException e) {
            System.out.println("Mã kho phải là một số nguyên.");
            return;
        }
        // Tìm kiếm user bằng ID
        UserEntity userEntity = userService.findById(user_id);
        if (userEntity != null) {
            UserDTO userModel = new UserDTO();
            System.out.print("Vui lòng nhập nhập mã kho cho nhân viên: ");
            String warehouseID = WarehouseApplication.scanner.nextLine().trim();
            int warehouse_id;
            try {
                warehouse_id = Integer.parseInt(warehouseID);
            } catch (NumberFormatException e) {
                System.out.println("Mã kho phải là một số nguyên.");
                return;
            }
            userModel.setId(userEntity.getId());
            userModel.setUsername(userEntity.getUsername());
            userModel.setWarehouse_id(warehouse_id);
            userModel.setUpdated_by(WarehouseApplication.user.getId());
            userModel.setUpdated_date(Date.valueOf(LocalDate.now()));

            System.out.println("Đang trong quá trình cập nhật, vui lòng chờ trong giây lát.");
            // Gửi dữ liệu (ở đây là userModel) đã đc xử lý tới service update để cập nhật kho cho nhân viên
            userService.update(userModel);
        } else System.out.println("Không tìm thấy nhân viên.");
    }

    /*
     * @Name: deleteUser
     * @Description: Admin xoá nhân viên
     * @Input: K có đầu vào. Đọc dữ liệu từ console
     * @Output: Ko có dữ liệu trả về. Nhân viên bị xoá khỏi Database
     * @Exception: Ko có exception cần kiểm tra
     */
    public void deleteUser() {
        // cái đoạn hiển thị danh sách user giống vs bên function update nên tách ra để 2 function dùng chung cho đỡ phải viết nhiều
        listUser();
        System.out.print("Vui lòng nhập mã nhân viên để xoá: ");
        String userID = WarehouseApplication.scanner.nextLine().trim();
        int user_id;
        try {
            user_id = Integer.parseInt(userID);
        } catch (NumberFormatException e) {
            System.out.println("Mã kho phải là một số nguyên.");
            return;
        }

        System.out.println("Đang trong quá trình xoá nhân viên, vui lòng chờ trong giây lát.");
        // Gửi dữ liệu user ID tới service delete để xoá nhân viên
        userService.remove(user_id);
    }

    /*
     * @Name: listUser
     * @Description: Đoạn code chung mà updateUser và deleteUser đều dùng
     * @Input: K có đầu vào. Đọc dữ liệu từ console
     * @Output: Ko có dữ liệu trả về
     * @Exception: Ko có exception cần kiểm tra
     */
    private void listUser() {
        if (userList == null) userList = userService.findAll();
        System.out.println("Danh sách nhân viên có trong hệ thống:");
        for (UserEntity u: userList) {
            System.out.println("User ID: " + u.getId() + "Username: " + u.getUsername()
                    + "Warehouse: " + u.getWarehouse().getName());
        }
    }
}
