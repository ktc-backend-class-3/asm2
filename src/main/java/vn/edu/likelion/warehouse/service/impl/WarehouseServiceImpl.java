package vn.edu.likelion.warehouse.service.impl;

import vn.edu.likelion.warehouse.entity.ProductEntity;
import vn.edu.likelion.warehouse.entity.WarehouseEntity;
import vn.edu.likelion.warehouse.model.WarehouseDTO;
import vn.edu.likelion.warehouse.repository.WarehouseRepo;
import vn.edu.likelion.warehouse.service.WarehouseService;

import java.util.ArrayList;

/*
 * @Name: WarehouseServiceImpl
 * @Description: Đây chính là lớp Service mà Controller gọi tới. Các chức năng của service đều được định nghĩa ở lớp giao diện
 * Lớp service này sẽ thực hiện toàn bộ chức năng của BaseService và lớp giao diện của riêng nó (nếu có)
 * Lớp service đảm nhiệm điều hướng các function thao tác với database và xử lý dữ liệu đầu ra
 * Ở trong bài này thì ko có xử lý dữ liệu đầu ra nên chỉ return kết quả thôi
 */
public class WarehouseServiceImpl implements WarehouseService {

    // Repository là lớp xử lý query tới database
    private WarehouseRepo repo = new WarehouseRepo();

    @Override
    public ArrayList<WarehouseEntity> findAll() { return repo.findAll(); }

    @Override
    public WarehouseEntity findById(int id)  {
        return repo.findById(id);
    }

    @Override
    public void create(WarehouseDTO warehouseModel) { repo.create(warehouseModel); }

    @Override
    public WarehouseEntity update(WarehouseDTO warehouseModel) {
        return null;
    }

    @Override
    public void remove(int id) { repo.remove(id); }

    @Override
    public void importProduct(ArrayList<ProductEntity> productList, int warehouse_id) { repo.importProduct(productList, warehouse_id); }

}
