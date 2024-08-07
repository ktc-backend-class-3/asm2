package vn.edu.likelion.warehouse.entity;

import java.sql.Date;

/*
 * @Name: BaseEntity
 * @Description: Những lớp khái quát dùng chung các thuộc tính như bên dưới thì sẽ kế thừa lớp trừu tượng BaseEntity này
 * Entity sau này dùng để mapping với database
 */
public abstract class BaseEntity {
    private int id;
    private int created_by;
    private Date created_date;
    private int updated_by;
    private Date updated_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public int getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(int updated_by) {
        this.updated_by = updated_by;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }
}
