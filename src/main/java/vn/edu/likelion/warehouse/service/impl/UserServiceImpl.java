package vn.edu.likelion.warehouse.service.impl;

import vn.edu.likelion.warehouse.entity.UserEntity;
import vn.edu.likelion.warehouse.model.UserModel;
import vn.edu.likelion.warehouse.repository.UserRepo;
import vn.edu.likelion.warehouse.service.UserService;

import java.util.ArrayList;

/*
 * @Name: UserServiceImpl
 * @Description: Đây chính là lớp Service mà Controller gọi tới. Các chức năng của service đều được định nghĩa ở lớp giao diện
 * Lớp service này sẽ thực hiện toàn bộ chức năng của BaseService và lớp giao diện của riêng nó (nếu có)
 * Lớp service đảm nhiệm điều hướng các function thao tác với database và xử lý dữ liệu đầu ra
 * Ở trong bài này thì ko có xử lý dữ liệu đầu ra nên chỉ return kết quả thôi
 */
public class UserServiceImpl implements UserService {

    // Repository là lớp xử lý query tới database
    private UserRepo repo = new UserRepo();

    @Override
    public UserEntity login(String username, String password) {
        return repo.login(username, password);
    }

    @Override
    public UserEntity findById(int id) {
        return repo.findById(id);
    }

    @Override
    public ArrayList<UserEntity> findAll() { return repo.findAll(); }

    @Override
    public void create(UserModel userModel) { repo.create(userModel); }

    @Override
    public UserEntity update(UserModel userModel) { return repo.update(userModel); }

    @Override
    public void remove(int id) { repo.remove(id); }
}
