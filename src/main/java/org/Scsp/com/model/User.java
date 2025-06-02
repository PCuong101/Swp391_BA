package org.Scsp.com.model;

import jakarta.persistence.*;
import org.Scsp.com.Enum.Role;

import java.util.Date;

@Entity
@Table(name = "[User]")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "Name", nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "Email", nullable = false, length = 100,unique = true)
    private String email;

    @Column(name = "Password", nullable = false, length = 50)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false, length = 10)
    private Role role;

    @Column(name = "RegistrationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @Column(name = "ProfilePicture", length = 255)
    private String profilePicture;

    @Column(name = "MemberPlanID")
    private Integer memberPlanId;



    public User(Integer userId, Integer memberPlanId, Date registrationDate, String name, String email, String password, String profilePicture) {
        this.userId = userId;
        this.memberPlanId = memberPlanId;
        this.registrationDate = registrationDate;
        this.name = name;
        this.email = email;
        this.password = password;
        role = Role.USER;
        this.profilePicture = profilePicture;
    }

    public User() {
        // Default constructor
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMemberPlanId() {
        return memberPlanId;
    }

    public void setMemberPlanId(Integer memberPlanId) {
        this.memberPlanId = memberPlanId;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    // Getters and setters omitted for brevity
}