package com.friday.colini.firdaycoliniaccountapi.controller;

import com.friday.colini.firdaycoliniaccountapi.dto.JwtAuthenticationResponse;
import com.friday.colini.firdaycoliniaccountapi.dto.SessionDto;
import com.friday.colini.firdaycoliniaccountapi.security.JwtTokenProvider;
import com.friday.colini.firdaycoliniaccountapi.security.UserPrincipal;
import com.friday.colini.firdaycoliniaccountapi.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/session")
public class SessionController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    private static String authorizationRequestBaseUri = "/oauth2/authorize";

    @GetMapping
    public String loginPage(Model model) {

        Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        clientRegistrations.forEach(
                registration -> oauth2AuthenticationUrls.put(registration.getClientName(), authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);
        return "login";
    }

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/loginSuccess")
    public String getLoginInfo(Model model,
                               OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        //...
        return "loginSuccess";
    }


    @PostMapping("/sign-in")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponse signIn(@RequestBody SessionDto.SignInReq signInReq) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInReq.getUserName(), signInReq.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        // todo : UserPrincipal -> Account
        // todo : history
        String accessToken = tokenProvider.generateToken(userPrincipal);
        return new JwtAuthenticationResponse(accessToken);
    }
}

