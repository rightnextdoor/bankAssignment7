package com.meritamerica.bank2.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.meritamerica.bank2.models.Role;

public class SignUpRequest {
    
	@NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(min = 2, max = 20)
    private String password;
    
    
    private boolean active;
    
   
    private Set<String> roles = new HashSet<>();
   
    public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	
    
}

