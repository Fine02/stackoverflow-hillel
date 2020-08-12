package com.ra.course.com.stackoverflow.dto.member;

import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import lombok.Data;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class SessionMemberDto implements Serializable {

    private static final long serialVersionUID = 99L;

    private Long id;

    private String name;

    private AccountRole role;

    private Map<Long, String> viewedQuestions = new HashMap<>();
}
