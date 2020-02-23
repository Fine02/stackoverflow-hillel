package com.ra.course.com.stackoverflow.entity.implementations;

import java.time.LocalDateTime;

public class Bounty {
    private int reputation;
    private LocalDateTime expiry;

    private Member creator;

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }

    public Member getCreator() {
        return creator;
    }

    public void setCreator(Member creator) {
        this.creator = creator;
    }

    public Bounty(int reputation, LocalDateTime expiry, Member creator) {
        this.reputation = reputation;
        this.expiry = expiry;
        this.creator = creator;
    }
}
