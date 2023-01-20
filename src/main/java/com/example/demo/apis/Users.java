package com.example.demo.apis;

import com.example.demo.domain.LocalUser;
import com.example.demo.dto.pagination.UserSearch;
import com.example.demo.dto.user.UserModifyDto;
import org.springframework.data.domain.Page;

public interface Users {

    void modify(UserModifyDto modifyDto);

    LocalUser get(Long id);

    Page<LocalUser> search(UserSearch search);

    void delete(Long id);
}
