package vn.edu.likelion.warehouse.entity;

import java.util.ArrayList;

/*
 * @Name: WarehouseEntity
 * @Description: Entity sau này dùng để mapping với database
 * Entity của 1 đối tượng có thể có đủ hoặc ko đủ các field như trong database
 * Tuy nhiên để có thể mapping giữa thuộc tính trong đối Entity và field trong DB, thì tên và kiểu dữ liệu là phải giống nhau
 */
public class WarehouseEntity extends BaseEntity {
    private UserEntity user;
    private String name;
    private String address;
    private ArrayList<ProductEntity> productList;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<ProductEntity> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductEntity> productList) {
        this.productList = productList;
    }
}
