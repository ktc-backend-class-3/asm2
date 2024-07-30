package vn.edu.likelion.warehouse.repository;

import vn.edu.likelion.warehouse.configuration.ConnectDB;
import vn.edu.likelion.warehouse.entity.RoleEntity;
import vn.edu.likelion.warehouse.entity.UserEntity;
import vn.edu.likelion.warehouse.entity.WarehouseEntity;
import vn.edu.likelion.warehouse.model.UserDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRepo {

    private ConnectDB conn = new ConnectDB();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public UserEntity login(String username, String password) {
        UserEntity user = null;
        try {
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("select u.username, r.role_name ");
            sqlQuery.append("from tbl_users u ");
            sqlQuery.append("join tbl_roles r on u.role_id = r.id ");
            sqlQuery.append("where u.username = ? and u.password = ? ");
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(sqlQuery.toString());
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                RoleEntity role = null;
                while (resultSet.next()) {
                    if (user == null) {
                        user = new UserEntity();
                        user.setUsername(resultSet.getString("username"));
                    }
                    if (role == null) {
                        role = new RoleEntity();
                        role.setName(resultSet.getString("role_name"));
                        user.setRole(role);
                    }
                }
                return user;
            }
        } catch (SQLException sqlException) {
            System.out.println("Có lỗi trong quá trình đăng nhập.");
        } finally {
            try {
                if (conn != null) conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
                if (resultSet != null) resultSet.close();
            } catch (SQLException sqlException) {
                System.out.println("Có lỗi trong quá trình đóng kết nối.");
            }
        }
        System.out.println("Không tìm thấy tài khoản, vui lòng kiểm tra lại thông tin đăng nhập.");
        return null;
    }

    public ArrayList<UserEntity> findAll() {
        ArrayList<UserEntity> userList = null;
        try {
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("select u.username, r.role_name, h.name as warehouse_name, h.address as warehouse_address ");
            sqlQuery.append("from tbl_users u ");
            sqlQuery.append("join tbl_roles r on u.role_id = r.id ");
            sqlQuery.append("join tbl_warehouses h on u.warehouse_id = h.id ");
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(sqlQuery.toString());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                userList = new ArrayList<>();
                UserEntity user = null;
                RoleEntity role = null;
                WarehouseEntity warehouse = null;
                while (resultSet.next()) {
                    user = new UserEntity();
                    user.setUsername(resultSet.getString("username"));

                    role = new RoleEntity();
                    role.setName(resultSet.getString("role_name"));
                    user.setRole(role);

                    warehouse = new WarehouseEntity();
                    warehouse.setName(resultSet.getString("warehouse_name"));
                    warehouse.setAddress(resultSet.getString("warehouse_address"));

                    userList.add(user);
                }
                return userList;
            }
        } catch (SQLException sqlException) {
            System.out.println("Có lỗi trong quá trình thống kê người dùng.");
        } finally {
            try {
                if (conn != null) conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
                if (resultSet != null) resultSet.close();
            } catch (SQLException sqlException) {
                System.out.println("Có lỗi trong quá trình đóng kết nối.");
            }
        }
        System.out.println("Không có người dùng nào trong hệ thống.");
        return null;
    }

    public UserEntity findById(int id) {
        UserEntity user = null;
        try {
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("select u.id, u.username, r.role_name, h.id as warehouse_id ");
            sqlQuery.append(", h.name as warehouse_name, h.address as warehouse_address ");
            sqlQuery.append("from tbl_users u ");
            sqlQuery.append("join tbl_roles r on u.role_id = r.id ");
            sqlQuery.append("join tbl_warehouses h on u.warehouse_id = h.id ");
            sqlQuery.append("where u.id = ? ");
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(sqlQuery.toString());
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                RoleEntity role = null;
                WarehouseEntity warehouseEntity = null;
                while (resultSet.next()) {
                    if (user == null) {
                        user = new UserEntity();
                        user.setId(resultSet.getInt("id"));
                        user.setUsername(resultSet.getString("username"));
                    }
                    if (role == null) {
                        role = new RoleEntity();
                        role.setName(resultSet.getString("role_name"));
                        user.setRole(role);
                    }
                    if (warehouseEntity == null) {
                        warehouseEntity = new WarehouseEntity();
                        warehouseEntity.setId(resultSet.getInt("warehouse_id"));
                        warehouseEntity.setName(resultSet.getString("warehouse_name"));
                        warehouseEntity.setAddress(resultSet.getString("warehouse_address"));
                    }
                }
                return user;
            }
        } catch (SQLException sqlException) {
            System.out.println("Có lỗi trong quá trình tìm kiếm người dùng.");
        } finally {
            try {
                if (conn != null) conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
                if (resultSet != null) resultSet.close();
            } catch (SQLException sqlException) {
                System.out.println("Có lỗi trong quá trình đóng kết nối.");
            }
        }
        System.out.println("Không tìm thấy tài khoản, vui lòng kiểm tra lại thông tin người dùng.");
        return null;
    }

    public void create(UserDTO userModel) {
        try {
            String sqlQuery = "insert into tbl_users (username, password, role_id, created_by, created_date) values (?, ?, ?, ?, ?)";
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(sqlQuery);
            preparedStatement.setString(1, userModel.getUsername());
            preparedStatement.setString(2, userModel.getPassword());
            preparedStatement.setInt(3, userModel.getRole_id());
            preparedStatement.setInt(3, userModel.getCreated_by());
            preparedStatement.setDate(3, userModel.getCreated_date());
            int status = preparedStatement.executeUpdate();

            if (status > 0) {
                System.out.println("Đăng ký thành công.");
            } else System.out.println("Có lỗi trong quá trình đăng ký.");
        } catch (SQLException sqlException) {
            System.out.println("Có lỗi trong quá trình đăng ký.");
        } finally {
            try {
                if (conn != null) conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException sqlException) {
                System.out.println("Có lỗi trong quá trình đăng ký.");
            }
        }
    }

    public UserEntity update(UserDTO userModel) {
        try {
            String sqlQuery = "update tbl_users set warehouse_id = ?, updated_by = ?, updated_date = ? where id = ? ";
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(sqlQuery);
            preparedStatement.setInt(1, userModel.getWarehouse_id());
            preparedStatement.setInt(2, userModel.getUpdated_by());
            preparedStatement.setDate(3, userModel.getUpdated_date());
            preparedStatement.setInt(4, userModel.getId());
            int status = preparedStatement.executeUpdate();

            if (status > 0) {
                System.out.println("Cập nhật thành công.");
            } else System.out.println("Có lỗi trong quá trình cập nhật.");
        } catch (SQLException sqlException) {
            System.out.println("Có lỗi trong quá trình cập nhật.");
        } finally {
            try {
                if (conn != null) conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException sqlException) {
                System.out.println("Có lỗi trong quá trình cập nhật.");
            }
        }
        return null;
    }

    public void remove(int id) {
        try {
            String sqlQuery = "delete from tbl_users where id = ? ";
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);
            int status = preparedStatement.executeUpdate();

            if (status > 0) {
                System.out.println("Xoá người dùng thành công.");
            } else System.out.println("Có lỗi trong quá trình xoá nhân viên.");
        } catch (SQLException sqlException) {
            System.out.println("Có lỗi trong quá trình xoá nhân viên.");
        } finally {
            try {
                if (conn != null) conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException sqlException) {
                System.out.println("Có lỗi trong quá trình xoá nhân viên.");
            }
        }
    }
}
