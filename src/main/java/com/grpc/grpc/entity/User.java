package com.grpc.grpc.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.grpc.proto.UserEntity;

import javax.persistence.*;

@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    public String email;
    private String addres;
    private String password;

    public UserEntity toUserEntityProto() {
        return UserEntity.newBuilder()
                .setEmail(this.getEmail())
                .setName(this.getName())
                .setId(this.getId())
                .setAddres(this.getAddres())
                .setPassword(this.getPassword())
                .build();
    }

    public static User fromProtoUserEntity(UserEntity userEntity) {
        User user = new User();
        user.setName(userEntity.getName());
        user.setAddres(userEntity.getAddres());
        user.setEmail(userEntity.getEmail());
        user.setId(userEntity.getId());
        user.setPassword(userEntity.getPassword());
        return user;
    }

}