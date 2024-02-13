package com.example.spring;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_user") public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    private String email;
    private String password;
    private int score;



    // Getters and setters
    public Long getId() {
        return id;
    }
    public int getScore() {
        return score;
    }

    public String getName() { return name;
    }
    public String getEmail() { return email;
    }
    public String getPassword() { return password;
    }
    public void setId(Long id) { this.id = id;
    }
    public void setName(String name) { this.name = name;
    }
    public void setEmail(String email) { this.email = email;
    }
    public void setPassword(String password) { this.password = password;
    }
    public void setScore(int score) {this.score = score;
    }
}