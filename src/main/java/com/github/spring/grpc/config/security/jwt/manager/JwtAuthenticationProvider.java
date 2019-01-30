package com.github.spring.grpc.config.security.jwt.manager;

import com.github.spring.grpc.config.security.UsernameAuthenticationToken;
import com.github.spring.grpc.config.security.model.UserDetailModel;
import com.github.spring.grpc.view.model.JwtUserModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        UsernameAuthenticationToken authenticationToken = (UsernameAuthenticationToken) usernamePasswordAuthenticationToken;
        JwtUserModel jwtUserModel = JwtManager.validateToken(authenticationToken.getToken());
        if (jwtUserModel == null) throw new RuntimeException("token invalid");
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(jwtUserModel.getRole());
        return new UserDetailModel(jwtUserModel.getId(), jwtUserModel.getUsername(), grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernameAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
