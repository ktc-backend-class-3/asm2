package vn.edu.likelion.warehouse.model;

import vn.edu.likelion.warehouse.entity.UserEntity;

/*
 * @Name: WarehouseModel
 * @Description: Model thường là để lưu dữ liệu gửi đến từ client hoặc console nên có thể cấu trúc sẽ khác Entity
 */
public class WarehouseModel extends BaseModel {
    private UserEntity user;
    private String name;
    private String address;

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
}
