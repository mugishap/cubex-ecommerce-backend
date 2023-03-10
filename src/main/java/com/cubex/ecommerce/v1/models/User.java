package com.cubex.ecommerce.v1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cubex.ecommerce.v1.audits.TimestampAudit;
import com.cubex.ecommerce.v1.enums.EGender;
import com.cubex.ecommerce.v1.enums.EUserStatus;
import com.cubex.ecommerce.v1.fileHandling.File;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;



@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users", uniqueConstraints = { @UniqueConstraint(columnNames={ "email" }), @UniqueConstraint(columnNames={ "mobile" })})
public class User extends TimestampAudit {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2",strategy = "uuid2")
    @Column(name="id")
    private UUID id;

    @NotBlank(message = "Email is mandatory")
    @Column(name="email")
    private String email;

    @NotBlank(message = "First name is mandatory")
    @Column(name="first_name")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Column(name="last_name")
    private String lastName;

    @Column(name="mobile")
    private String mobile;

    @JsonIgnore
    @NotBlank(message = "Password is mandatory")
    @Column(name="password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    private EGender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EUserStatus status = EUserStatus.PENDING;

    @JoinColumn(name="profile_image_id")
    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private File profileImage;

    @Column(name="activation_code")
    private String activationCode;

    @OneToMany()
    private List<Purchase> purchases;

    @OneToOne()
    private Cart cart;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String email, String firstName, String lastName, String mobile, EGender gender, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.gender = gender;
        this.password = password;
    }
}
