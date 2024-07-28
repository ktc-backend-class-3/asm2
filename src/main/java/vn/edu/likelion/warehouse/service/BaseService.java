package vn.edu.likelion.warehouse.service;

import java.util.ArrayList;

/*
 * @Name: BaseService<T, K>
 * @Description: Đây là 1 lớp giao diện cơ bản
 * Base service định nghĩa các abstract method mà các lớp interface service sẽ dùng chung
 * Ở đây có sử dụng generic <T, K> để tạo 1 interface trung lập, ko xác định trước các đối tượng tham chiếu
 */
public interface BaseService<T, K> {
    ArrayList<T> findAll();
    void create(K k);
    T update(K k);
    void remove(int id);
}
