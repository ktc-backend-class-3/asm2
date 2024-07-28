package vn.edu.likelion.warehouse.entity;

/*
 * @Name: ProductEntity
 * @Description: Entity sau này dùng để mapping với database
 * Entity của 1 đối tượng có thể có đủ hoặc ko đủ các field như trong database
 * Tuy nhiên để có thể mapping giữa thuộc tính trong đối Entity và field trong DB, thì tên và kiểu dữ liệu là phải giống nhau
 */
public class ProductEntity extends BaseEntity {
    private String name;
    private String description;
    private int quantity;
    private Double price;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
