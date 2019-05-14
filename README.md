# Colini 커뮤니티 사이트 : 계정관련 API

## 주요기능 
- 회원가입
- 사용자 정보 관리
- 로그인, 로그아웃


## 기능 세부설명
1. 회원가입
- 일반 회원가입
- oAuth2 인증방식을 활용한 회원가입 
    - Google, Github
- 비밀번호는 암호화하며, 식별은 이메일로 한다.

2. 로그인, 로그아웃
- 일반 로그인
- oAuth2 인증방식 활용하여 로그인 인증 후 token 발급
    - token store : 임메모리 > 추후, DB로 변경  
    - jwt
3. 사용자 정보 관리
- 해당하는 유저 정보
- 비밀번호 변경 : 본인 인증 후 변경 (본인이 아닌경우 401)
    - optional : kakao or email 인증
- 회원탈퇴 : 상태만 변경
    - optional : 탈퇴한지 1년된 회원은 삭제한다. batch 사용
- 프로필 이미지(url) 변경

---

## 개발환경
- IntelliJ
- Spring Boot 2.1.4.RELEASE
- Gradle 
- Java 8
- Lombok
- JPA
- MySQL 5.6
- H2 (test)

## 환경구성
```
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    
    compile 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    
    compile 'com.h2database:h2'
    runtimeOnly 'mysql:mysql-connector-java'
       
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
    
//    implementation 'org.springframework.boot:spring-boot-starter-hateoas'
//    compile('org.springframework.boot:spring-boot-starter-security')
//    compile group: 'org.springframework.security.oauth.boot', name: 'spring-security-oauth2-autoconfigure', version: '2.1.4.RELEASE'
//  + Spring security oAuth2, jwt
//  + Spring data redis
//  + hateoas 
```

## Package
- controller
    - SessionController 
    - AccountController  
- domain
    - Account
    - Role 
- dto
    - AccountDto
    - SessionDto 
- service
    - SessionService
    - AccountService 
- repository
    - AccountRepository
     
- security : security 설정 테스트 후 통합 
    - config
        - AuthorizationServerConfig
        - ResourceServerConfig
        - SecurityConfig
    - service
        - UserService
    - repository 
        - UserRepository
---

## Rest API
__Service name : account__

| Resources Name | Path | Method | Detail |
|--------|--------|--------|--------|
|session|new|GET| gets the webpage that has the login
|session| |POST| session authenticates credentials against database
|session| |DELETE| destroys seession and redirect to / 
|users| |POST| 계정 생성 (회원가입)
|users|{id}|GET| 해당 {id}계정 정보
|users|{id}|POST| 해당 {id}계정 정보수정
|users|{id}|DELETE| 해당 {id}계정 탈퇴

---
## DataBase modeling
![db_modeling](/assert/markdown-img-paste-20190505003048528.png)
---

