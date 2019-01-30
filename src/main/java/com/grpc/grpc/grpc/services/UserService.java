package com.grpc.grpc.grpc.services;

import com.grpc.grpc.entity.User;
import com.grpc.grpc.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import org.grpc.proto.*;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@GRpcService
@Log4j2
public class UserService extends UserServiceGrpc.UserServiceImplBase {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void register(UserEntity request, StreamObserver<UserEntity> responseObserver) {
        User user = User.fromProtoUserEntity(request);
        responseObserver.onNext(user.toUserEntityProto());

        //   responseObserver.onError(Status.INTERNAL.withDescription("berak").asRuntimeException());
        responseObserver.onCompleted();
    }

    @Override
    public void listUsers(QueryParams request, StreamObserver<Users> responseObserver) {
        List<User> userRepositoryAll = userRepository.findAll();
        Users.Builder userBuilder = Users.newBuilder();
        userRepositoryAll.forEach(user -> {
            userBuilder.addUserEntity(user.toUserEntityProto());
        });

        responseObserver.onNext(userBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void streamUsers(QueryParams request, StreamObserver<UserEntity> responseObserver) {
        List<User> repositoryAll = userRepository.findAll();
        repositoryAll.forEach(user -> {
            log.info("send request " + user.getClass());
            responseObserver.onNext(user.toUserEntityProto());
        });
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<UserEntity> streamRegistration(StreamObserver<StreamFromClient> responseObserver) {
        return new StreamObserver<UserEntity>() {
            @Override
            public void onNext(UserEntity value) {
                User user = User.fromProtoUserEntity(value);
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
    public StreamObserver<UserEntity> streamBidirectional(StreamObserver<UserEntity> responseObserver) {
        return super.streamBidirectional(responseObserver);
    }
}
