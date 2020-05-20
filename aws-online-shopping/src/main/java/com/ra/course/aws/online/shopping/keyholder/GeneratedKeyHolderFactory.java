package com.ra.course.aws.online.shopping.keyholder;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

@Service
public class GeneratedKeyHolderFactory implements KeyHolderFactory {
    public KeyHolder newKeyHolder() {
        return new GeneratedKeyHolder();
    }
}
