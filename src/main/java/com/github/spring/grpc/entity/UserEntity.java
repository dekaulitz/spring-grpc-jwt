package com.github.spring.grpc.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;

@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    public String email;
    private String addres;
    private String password;
    private String role;

    public org.grpc.proto.UserEntity toUserEntityProto() {
        return org.grpc.proto.UserEntity.newBuilder()
                .setEmail(this.getEmail())
                .setName(this.getName())
                .setId(this.getId())
                .setAddres(this.getAddres())
                .setPassword(this.getPassword())
                .build();
    }

    public static UserEntity fromProtoUserEntity(org.grpc.proto.UserEntity userEntity) {
        UserEntity user = new UserEntity();
        user.setName(userEntity.getName());
        user.setAddres(userEntity.getAddres());
        user.setEmail(userEntity.getEmail());
        user.setId(userEntity.getId());
        user.setPassword(userEntity.getPassword());
        return user;
    }

}
