package com.example.demo.dto.user;

import com.example.demo.dto.AbstractRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements AbstractRequest {

    protected Long id;

    protected String username;

    protected String firstName;

    protected String lastName;

    protected String organizationName;
}
