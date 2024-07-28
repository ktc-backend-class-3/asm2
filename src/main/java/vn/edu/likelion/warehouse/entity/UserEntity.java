package vn.edu.likelion.warehouse.entity;

/*
 * @Name: UserEntity
 * @Description: Entity sau này dùng để mapping với database
 * Entity của 1 đối tượng có thể có đủ hoặc ko đủ các field như trong database
 * Tuy nhiên để có thể mapping giữa thuộc tính trong đối Entity và field trong DB, thì tên và kiểu dữ liệu là phải giống nhau
 */
public class UserEntity extends BaseEntity {
    private String username;
    private String password;
    private RoleEntity role;
    private WarehouseEntity warehouse;

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

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public WarehouseEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
    }
}
