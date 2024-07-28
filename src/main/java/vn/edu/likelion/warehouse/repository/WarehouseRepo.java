package vn.edu.likelion.warehouse.repository;

import vn.edu.likelion.warehouse.application.WarehouseApplication;
import vn.edu.likelion.warehouse.connection.ConnectDB;
import vn.edu.likelion.warehouse.entity.ProductEntity;
import vn.edu.likelion.warehouse.entity.WarehouseEntity;
import vn.edu.likelion.warehouse.model.WarehouseModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class WarehouseRepo {

    private ConnectDB conn = new ConnectDB();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public ArrayList<WarehouseEntity> findAll() {
        ArrayList<WarehouseEntity> warehouseList = null;
        WarehouseEntity warehouseEntity = null;
        ArrayList<ProductEntity> productList;
        try {
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("select h.name, h.address, p.name as product_name, p.description, p.quantity, p.price ");
            sqlQuery.append(", p.created_date ");
            sqlQuery.append("from tbl_warehouses h ");
            sqlQuery.append("join tbl_products p on p.warehouse_id = h.id ");
            sqlQuery.append("order by h.id");
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(sqlQuery.toString());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                productList = new ArrayList<>();
                ProductEntity productEntity;
                while (resultSet.next()) {
                    int warehouseId = resultSet.getInt("warehouse_id");

                    if (warehouseEntity == null || warehouseEntity.getId() != warehouseId) {
                        if (warehouseEntity != null) {
                            warehouseEntity.setProductList(productList);
                            warehouseList.add(warehouseEntity);
                        }

                        warehouseEntity = new WarehouseEntity();
                        warehouseEntity.setId(warehouseId);
                        warehouseEntity.setName(resultSet.getString("name"));
                        warehouseEntity.setAddress(resultSet.getString("address"));

                        productList = new ArrayList<>();
                    }

                    productEntity = new ProductEntity();
                    productEntity.setName(resultSet.getString("product_name"));
                    productEntity.setDescription(resultSet.getString("description"));
                    productEntity.setQuantity(resultSet.getInt("quantity"));
                    productEntity.setPrice(resultSet.getDouble("price"));
                    productEntity.setCreated_date(resultSet.getDate("created_date"));

                    productList.add(productEntity);
                }

                if (warehouseEntity != null) {
                    warehouseEntity.setProductList(productList);
                    warehouseList.add(warehouseEntity);
                }
                return warehouseList;
            }
        } catch (SQLException sqlException) {
            System.out.println("Có lỗi trong quá trình thống kê kho.");
        } finally {
            try {
                if (conn != null) conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
                if (resultSet != null) resultSet.close();
            } catch (SQLException sqlException) {
                System.out.println("Có lỗi trong quá trình đóng kết nối.");
            }
        }
        System.out.println("Không có kho nào trong hệ thống.");
        return null;
    }

    public WarehouseEntity findById(int warehouse_id) {
        WarehouseEntity warehouseEntity = null;
        ArrayList<ProductEntity> productList;
        try {
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("select h.name, h.address, p.name as product_name, p.description, p.quantity, p.price ");
            sqlQuery.append(", p.created_date ");
            sqlQuery.append("from tbl_warehouses h ");
            sqlQuery.append("join tbl_products p on p.warehouse_id = h.id ");
            sqlQuery.append("where h.id = ? ");
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(sqlQuery.toString());
            preparedStatement.setInt(1, warehouse_id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                productList = new ArrayList<>();
                ProductEntity productEntity;
                while (resultSet.next()) {
                    if (warehouseEntity == null) {
                        warehouseEntity = new WarehouseEntity();
                        warehouseEntity.setName(resultSet.getString("name"));
                        warehouseEntity.setAddress(resultSet.getString("address"));
                    }
                    productEntity = new ProductEntity();
                    productEntity.setName(resultSet.getString("product_name"));
                    productEntity.setDescription(resultSet.getString("description"));
                    productEntity.setQuantity(resultSet.getInt("quantity"));
                    productEntity.setPrice(resultSet.getDouble("price"));
                    productEntity.setCreated_date(resultSet.getDate("created_date"));
                    productList.add(productEntity);
                }
                warehouseEntity.setProductList(productList);
                return warehouseEntity;
            }
        } catch (SQLException sqlException) {
            System.out.println("Có lỗi trong quá trình thống kê kho.");
        } finally {
            try {
                if (conn != null) conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
                if (resultSet != null) resultSet.close();
            } catch (SQLException sqlException) {
                System.out.println("Có lỗi trong quá trình đóng kết nối.");
            }
        }
        System.out.println("Không có kho nào trong hệ thống.");
        return null;
    }

    public void create(WarehouseModel warehouseModel) {
        try {
            String sqlQuery = "insert into tbl_warehouses (name, address,created_by, created_date) values (?, ?)";
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(sqlQuery);
            preparedStatement.setString(1, warehouseModel.getName());
            preparedStatement.setString(2, warehouseModel.getAddress());
            preparedStatement.setInt(2, warehouseModel.getCreated_by());
            preparedStatement.setDate(2, warehouseModel.getCreated_date());
            int status = preparedStatement.executeUpdate();

            if (status > 0) {
                System.out.println("Tạo kho thành công.");
            } else System.out.println("Có lỗi trong quá trình tạo kho.");
        } catch (SQLException sqlException) {
            System.out.println("Có lỗi trong quá trình tạo kho.");
        } finally {
            try {
                if (conn != null) conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException sqlException) {
                System.out.println("Có lỗi trong quá trình tạo kho.");
            }
        }
    }

    public void remove(int id) {
        try {
            String sqlQuery = "delete from tbl_warehouses where id = ? ";
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);
            int status = preparedStatement.executeUpdate();

            if (status > 0) {
                System.out.println("Xoá kho thành công.");
            } else System.out.println("Có lỗi trong quá trình xoá kho.");
        } catch (SQLException sqlException) {
            System.out.println("Có lỗi trong quá trình xoá kho.");
        } finally {
            try {
                if (conn != null) conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException sqlException) {
                System.out.println("Có lỗi trong quá trình xoá kho.");
            }
        }
    }

    public void importProduct(ArrayList<ProductEntity> productList, int warehouse_id) {
        try {
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("insert into tbl_products ");
            sqlQuery.append("(warehouse_id, name, description, quantity, price, created_by, created_date) ");
            sqlQuery.append("values (?, ?, ?, ?, ?, ?, ?)");
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(sqlQuery.toString());
            for (ProductEntity p: productList) {
                preparedStatement.setInt(1, warehouse_id);
                preparedStatement.setString(2, p.getName());
                preparedStatement.setString(3, p.getDescription());
                preparedStatement.setInt(4, p.getQuantity());
                preparedStatement.setDouble(5, p.getPrice());
                preparedStatement.setInt(6, WarehouseApplication.user.getId());
                preparedStatement.setDate(7, Date.valueOf(LocalDate.now()));
                preparedStatement.addBatch();
            }
            int[] results = preparedStatement.executeBatch();

            if (results.length > 0) {
                System.out.println("Nhập kho " + results.length + " sản phẩm thành công.");
            } else System.out.println("Có lỗi trong quá trình nhập.");
        } catch (SQLException sqlException) {
            System.out.println("Có lỗi trong quá trình nhập kho.");
        } finally {
            try {
                if (conn != null) conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException sqlException) {
                System.out.println("Có lỗi trong quá trình nhập kho.");
            }
        }
    }

}
