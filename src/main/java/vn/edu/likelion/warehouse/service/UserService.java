package vn.edu.likelion.warehouse.service;

import vn.edu.likelion.warehouse.entity.UserEntity;
import vn.edu.likelion.warehouse.model.UserDTO;

/*
 * @Name: UserService
 * @Description: Đây là 1 lớp interface service của User, kế thừa từ BaseService
 * đây cũng là lớp interface service nên chỉ khai báo bổ sung các function dành riêng cho user mà trong BaseService ko có
 * ko override lại các function trong BaseService
 */
public interface UserService extends BaseService<UserEntity, UserDTO> {
    UserEntity login(String username, String password);
    UserEntity findById(int id);
}
