package vn.edu.likelion.warehouse.entity;

/*
 * @Name: RoleEntity
 * @Description: Entity sau này dùng để mapping với database
 * Entity của 1 đối tượng có thể có đủ hoặc ko đủ các field như trong database
 * Tuy nhiên để có thể mapping giữa thuộc tính trong đối Entity và field trong DB, thì tên và kiểu dữ liệu là phải giống nhau
 */
public class RoleEntity {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
