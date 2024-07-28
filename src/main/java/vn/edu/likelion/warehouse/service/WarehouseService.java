package vn.edu.likelion.warehouse.service;

import vn.edu.likelion.warehouse.entity.ProductEntity;
import vn.edu.likelion.warehouse.entity.WarehouseEntity;
import vn.edu.likelion.warehouse.model.WarehouseModel;

import java.util.ArrayList;

/*
 * @Name: WarehouseService
 * @Description: Đây là 1 lớp interface service của Warehouse, kế thừa từ BaseService
 * đây cũng là lớp interface service nên chỉ khai báo bổ sung các function dành riêng cho warehouse mà trong BaseService ko có
 * ko override lại các function trong BaseService
 */
public interface WarehouseService extends BaseService<WarehouseEntity, WarehouseModel> {
    WarehouseEntity findById(int warehouse_id);
    void importProduct(ArrayList<ProductEntity> productList, int warehouse_id);
}
