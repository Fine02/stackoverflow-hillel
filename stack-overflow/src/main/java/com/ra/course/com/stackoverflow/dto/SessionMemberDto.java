package com.ra.course.com.stackoverflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@AllArgsConstructor
@Data
public class SessionMemberDto implements Serializable {

    private static final long serialVersionUID = 99L;

    private Long id;

    private String name;

    private Map<Long, String> viewedQuestions;
}
