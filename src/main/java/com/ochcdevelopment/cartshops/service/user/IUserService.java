package com.ochcdevelopment.cartshops.service.user;

import com.ochcdevelopment.cartshops.dto.UserDto;
import com.ochcdevelopment.cartshops.model.User;
import com.ochcdevelopment.cartshops.request.CreateUserRequest;
import com.ochcdevelopment.cartshops.request.UserUpdateRequest;


public interface IUserService {

    //obtener  usuario por id
    User getUserById(Long userId);

    //crear usario y me va de respuesta lo que contenga la clase CreateUserRequest
    User createUser(CreateUserRequest request);

    //actualizar user por id
    User updateUser(UserUpdateRequest request, Long userId);

    //eliminar user por su id
    void deleteUser(Long userId);

    //convertir usuario a datos
    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();

    //obtener user por email
    //User getUserByEmail(String email);


}
