package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.Scsp.com.Enum.Role;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "Users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long userId;

    @Column(name = "Name", nullable = false, columnDefinition = "NVARCHAR(100)", unique = true)
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


}