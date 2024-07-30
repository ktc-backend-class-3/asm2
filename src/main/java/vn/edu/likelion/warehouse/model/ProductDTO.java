package vn.edu.likelion.warehouse.model;

import org.apache.poi.hpsf.Decimal;
import vn.edu.likelion.warehouse.entity.WarehouseEntity;

/*
 * @Name: ProductModel
 * @Description: Model thường là để lưu dữ liệu gửi đến từ client hoặc console nên có thể cấu trúc sẽ khác Entity
 */
public class ProductDTO {
    private WarehouseEntity warehouseEntity;
    private String name;
    private String description;
    private int quantity;
    private Decimal price;

    public WarehouseEntity getWarehouseEntity() {
        return warehouseEntity;
    }

    public void setWarehouseEntity(WarehouseEntity warehouseEntity) {
        this.warehouseEntity = warehouseEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Decimal getPrice() {
        return price;
    }

    public void setPrice(Decimal price) {
        this.price = price;
    }
}
