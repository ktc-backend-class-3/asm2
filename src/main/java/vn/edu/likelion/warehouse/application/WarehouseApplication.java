package vn.edu.likelion.warehouse.application;
import vn.edu.likelion.warehouse.controller.AuthenController;
import vn.edu.likelion.warehouse.controller.UserController;
import vn.edu.likelion.warehouse.controller.WarehouseController;
import vn.edu.likelion.warehouse.entity.UserEntity;

import java.util.Scanner;

/*
 * @Name: WarehouseApplication
 * @Description: Class chính để chạy chương trình.
 * Hiển thị menu, cấu trúc menu, check quyền, etc...
 */
public class WarehouseApplication {
	// 2 cái này để giả lập, nên mới để access modify là public để các class khác có thể truy cập
	public static Scanner scanner = new Scanner(System.in);
	public static UserEntity user = null;	// Cái này là fake session trên trình duyệt, để lưu thông tin đăng nhập của user

	/*
	 * @Name: main
	 * @Description: Hàm chạy chương trình
	 * Thể hiện cấu trúc menu, check quyền, điều hướng, etc...
	 */
	public static void main(String[] args) {
		// Tạo sẵn các lớp điều khiển để xử lý các chức năng tương ứng của chương trình
		AuthenController authenController = new AuthenController();
		WarehouseController warehouseController = new WarehouseController();
		UserController userController = new UserController();

		while (true) {
			// menu chuong trinh
			mainMenu();
			int choice;
			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập số.");
				continue;
			}
			switch (choice) {
				case 1:
					// dang nhap
					user = authenController.login();
					break;
				case 2:
					// admin: quan ly kho
					if (user != null) {
						// check quyền admin mới cho dùng chức năng
						if (user.getRole().getName().equals("Admin")) {
							warehouseMenu(warehouseController);
						}
					} else System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
					break;
				case 3:
					// admin: quan ly user
					if (user != null) {
						// check quyền admin mới cho dùng chức năng
						if (user.getRole().getName().equals("Admin")) {
							userMenu(userController);
						}
					} else System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
					break;
				case 4:
					// user: xem thong tin kho
					if (user != null) {
						// ko cần check quyền admin, nhưng cần check xem user đã có kho chưa
						if (user.getWarehouse() != null) {
							warehouseController.findById();
						} else System.out.println("Bạn chưa có kho. Vui lòng liên hệ Admin.");
					} else System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
					break;
				case 5:
					// user: nhap kho
					if (user != null) {
						// ko cần check quyền admin, nhưng cần check xem user đã có kho chưa
						if (user.getWarehouse() != null) {
							warehouseController.importProduct();
						} else System.out.println("Bạn chưa có kho. Vui lòng liên hệ Admin.");
					} else System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
					break;
				case 6:
					// user: bao cao kho
					if (user != null) {
						// ko cần check quyền admin, nhưng cần check xem user đã có kho chưa
						if (user.getWarehouse() != null) {
							warehouseController.reportWarehouse();
						} else System.out.println("Bạn chưa có kho. Vui lòng liên hệ Admin.");
					} else System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
					break;
				case 9:
					// dang xuat
					if (user != null) user = null;
					break;
				case 0:
					System.out.println("Thoát chương trình.");
					System.exit(0);
				default:
					System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
			}
		}
	}

	/*
	 * @Name: mainMenu
	 * @Description: Menu chính của chương trình
	 */
	private static void mainMenu() {
		System.out.println("========= MENU =========");
		if (user == null) {
			System.out.println("1. Đăng nhập.");
		}
		if (user != null) {
			if (user.getRole().getName().equals("Admin")) {
				System.out.println("2. Quản lý kho.");
				System.out.println("3. Quản lý nhân viên.");
			} else {
				System.out.println("4. Xem thông tin kho.");
				System.out.println("5. Nhập kho.");
				System.out.println("6. Báo cáo kho.");
			}
			System.out.println("9. Đăng xuất.");
		}
		System.out.println("0. Thoát chương trình.");
		System.out.println("========================");
		System.out.print("Chọn chức năng: ");
	}

	/*
	 * @Name: warehouseMenu
	 * @Description: Menu dành cho quản lý kho
	 * Bao gồm các chức năng tương ứng được xử lý trong Warehouse Controller
	 */
	private static void warehouseMenu(WarehouseController warehouseController) {
		System.out.println("========= WAREHOUSE MANAGEMENT MENU =========");
		System.out.println("1. Thêm kho.");
		System.out.println("2. Sửa kho.");
		System.out.println("3. Xoá kho.");
		System.out.println("4. Xem kho.");
		System.out.println("5. Nhập kho.");
		System.out.println("6. Thống kê toàn kho.");
		System.out.println("7. Quay lại menu chính.");

		// biến flag này là để thoát menu quản lý kho ra menu chính
		boolean flag = true;
		while (flag) {
			int choice;
			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập số.");
				continue;
			}
			switch (choice) {
				case 1:
					// them kho
					warehouseController.createWarehouse();
					break;
				case 2:
					// sua kho
					warehouseController.updateWarehouse();
					break;
				case 3:
					// xoa kho
					warehouseController.deleteWarehouse();
					break;
				case 4:
					// xem kho
					warehouseController.findById();
					break;
				case 5:
					// nhap kho
					warehouseController.importProduct();
					break;
				case 6:
					// nhap kho
					warehouseController.reportWarehouse();
					break;
				case 7:
					// thoat menu
					flag = false;
					break;
				default:
					break;
			}
		}
	}

	/*
	 * @Name: userMenu
	 * @Description: Menu dành cho quản lý nhân viên
	 * Bao gồm các chức năng tương ứng được xử lý trong User Controller
	 */
	private static void userMenu(UserController userController) {
		// biến flag này là để thoát menu quản lý nhân viên ra menu chính
		boolean flag = true;
		while (flag) {
			System.out.println("========= STAFF MANAGEMENT MENU =========");
			System.out.println("1. Thêm nhân viên.");
			System.out.println("2. Sửa nhân viên.");
			System.out.println("3. Xoá nhân viên.");
			System.out.println("4. Quay lại menu chính.");
			System.out.print("Nhập lựa chọn của bạn: ");
			int choice;
			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập số.");
				continue;
			}
			switch (choice) {
				case 1:
					// Thêm user
					userController.createUser();
					break;
				case 2:
					// Sửa user
					userController.updateUser();
					break;
				case 3:
					// Xóa user
					userController.deleteUser();
					break;
				case 4:
					// Thoát menu
					flag = false;
					break;
				default:
					System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
					break;
			}
		}
	}

}