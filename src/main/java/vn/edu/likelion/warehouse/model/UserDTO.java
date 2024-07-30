package vn.edu.likelion.warehouse.model;

/*
 * @Name: UserModel
 * @Description: Model thường là để lưu dữ liệu gửi đến từ client hoặc console nên có thể cấu trúc sẽ khác Entity
 */
public class UserDTO extends BaseDTO {
    private String username;
    private String password;
    private int role_id;
    private int warehouse_id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(int warehouse_id) {
        this.warehouse_id = warehouse_id;
    }
}
