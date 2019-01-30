package com.github.spring.grpc.grpc.service;

import com.github.spring.grpc.entity.UserEntity;
import com.github.spring.grpc.interceptor.JwtInterceptor;
import com.github.spring.grpc.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import org.grpc.proto.*;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;


@Log4j2
@GRpcService(interceptors = {
        JwtInterceptor.class
})
public class UserService extends UserServiceGrpc.UserServiceImplBase {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void register(org.grpc.proto.UserEntity request, StreamObserver<org.grpc.proto.UserEntity> responseObserver) {
        com.github.spring.grpc.entity.UserEntity user = UserEntity.fromProtoUserEntity(request);
        responseObserver.onNext(user.toUserEntityProto());
        responseObserver.onCompleted();
    }

    @Override
    public void listUsers(QueryParams request, StreamObserver<Users> responseObserver) {
        List<UserEntity> userRepositoryAll = userRepository.findAll();
        Users.Builder userBuilder = Users.newBuilder();
        userRepositoryAll.forEach(user -> {
            userBuilder.addUserEntity(user.toUserEntityProto());
        });

        responseObserver.onNext(userBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void streamUsers(QueryParams request, StreamObserver<org.grpc.proto.UserEntity> responseObserver) {
        List<UserEntity> repositoryAll = userRepository.findAll();
        repositoryAll.forEach(user -> {
            log.info("send request " + user.getClass());
            responseObserver.onNext(user.toUserEntityProto());
        });
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<org.grpc.proto.UserEntity> streamRegistration(StreamObserver<StreamFromClient> responseObserver) {
        return new StreamObserver<org.grpc.proto.UserEntity>() {
            @Override
            public void onNext(org.grpc.proto.UserEntity value) {
                UserEntity user = UserEntity.fromProtoUserEntity(value);
                userRepository.save(user);
            }

            @Override
            public void onError(Throwable t) {
                log.error(t.getMessage());
            }

            @Override
            public void onCompleted() {
                StreamFromClient streamFromClient = StreamFromClient.newBuilder()
                        .setMessage("stream finish").build();
                responseObserver.onNext(streamFromClient);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<org.grpc.proto.UserEntity> streamBidirectional(StreamObserver<org.grpc.proto.UserEntity> responseObserver) {
        return super.streamBidirectional(responseObserver);
    }
}
