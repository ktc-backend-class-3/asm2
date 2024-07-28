package vn.edu.likelion.warehouse.controller;

import vn.edu.likelion.warehouse.application.WarehouseApplication;
import vn.edu.likelion.warehouse.entity.UserEntity;
import vn.edu.likelion.warehouse.service.UserService;
import vn.edu.likelion.warehouse.service.impl.UserServiceImpl;

import java.util.Base64;

/*
 * @Name: AuthenController
 * @Description: Class điều khiển việc đăng nhập, đăng ký, quên mật khẩu, etc...
 * Mặc dù những function này có thể nằm trong UserController,
 * tuy nhiên cứ tách những chức năng cần authentication ra riêng cho tường minh vs sau dễ maintain
 * Các controller chủ yếu xử lý dữ liệu đầu vào, sau đó gửi cho các service xử lý tiếp
 */
public class AuthenController {

    // Khởi tạo đối tượng service để tiếp tục xử lý sau khi controller xử lý xong dữ liệu đầu vào
    private UserService service = new UserServiceImpl();

    public UserEntity login() {
        System.out.print("Nhập tên người dùng: ");
        String username = WarehouseApplication.scanner.nextLine().trim();
        if (username.isEmpty()) {
            System.out.println("Tên người dùng không được để trống.");
            return null;
        }

        System.out.print("Nhập mật khẩu: ");
        String password = WarehouseApplication.scanner.nextLine().trim();
        if (password.isEmpty()) {
            System.out.println("Mật khẩu không được để trống.");
            return null;
        }
        // Phải mã hoá mật khẩu nhập vào từ console để kiểm tra vs mật khẩu đã đc mã hoá trong Database
        String passwordEncoding = Base64.getEncoder().encodeToString(password.getBytes());

        System.out.println("Đang trong quá trình đăng nhập, vui lòng chờ trong giây lát.");
        // Gửi dữ liệu đến service Login để xử lý đăng nhập
        return service.login(username, passwordEncoding);
    }

}
