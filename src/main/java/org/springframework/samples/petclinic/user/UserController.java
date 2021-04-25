package org.springframework.samples.petclinic.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.samples.petclinic.user.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {

    @PostMapping("/user")
    public UserDTO login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
        String token = getJWTToken(username);
        UserDTO user = new UserDTO();
        user.setUser(username);
        user.setToken(token);
        return user;
        /*User userAux = this.userRepo.findByEmail(username);
        UserDTO user = new UserDTO();
        String idForEncode = "bcrypt";
        Map enconders = new HashMap<>();
        enconders.put(idForEncode, new BCryptPasswordEncoder());
        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, enconders);

        if(userAux != null && userAux.getActive().equals("1")){
            int ownerId = userAux.getId();
            OwnerDTO owner = new OwnerDTO();

            Owner aux = this.owners.findOwnerByUser(ownerId);

            if(aux == null) {
                owner = modelMapper.map(userAux, OwnerDTO.class);
                owner.setAddress(userAux.getCity()+", "+userAux.getZipcode());
            }else{
                owner = owner = modelMapper.map(aux, OwnerDTO.class);
            }

        if (passwordEncoder.matches(pwd, userAux.getPassword())) {
            String token = getJWTToken(username);
            user.setToken(token);
            user.setUser(username);
            user.setOwner(owner);
            user.setPwd(userAux.getPassword());
        }
    }
        return user;*/

    }

    private String getJWTToken(String username) {
        String secretKey = "secreto";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
            .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
            .builder()
            .setId("petJWT")
            .setSubject(username)
            .claim("authorities",
                grantedAuthorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()))
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 6000000))
            .signWith(SignatureAlgorithm.HS512,
                secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
