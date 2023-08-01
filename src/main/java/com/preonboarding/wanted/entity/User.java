package com.preonboarding.wanted.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;

@Setter
@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "email")
    @Email(message = "이메일 형식에 맞지 않습니다. '@'를 포함하여 입력해주세요.")
    private String email;

    @Column(name = "password")
    @Pattern(regexp="^.{8,}$",
            message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    // User<->Board
    @OneToMany(mappedBy = "user")
    private List<Board> boardList = new ArrayList<>();

}
