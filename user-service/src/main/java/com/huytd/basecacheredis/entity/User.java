package com.huytd.basecacheredis.entity;

import com.huytd.basecacheredis.constant.RoleTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "\"user\"", schema = "public")
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role_type")
    @Builder.Default
    private Integer roleType = RoleTypeEnum.END_USER.getCode();

}
