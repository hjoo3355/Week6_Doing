package com.sparta.doing.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class TimeStamp {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private String createdAt;

    // @CreatedBy
    // @Column(nullable = false, updatable = false, length = 50)
    // private String createdBy;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(nullable = false)
    private String modifiedAt;

    // @LastModifiedBy
    // @Column(nullable = false, length = 50)
    // private String modifiedBy;

    // 날짜 포맷 변경
    // 엔티티 insert 이전에 실행
    @PrePersist
    public void onPrePersist() {
        this.createdAt =
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.modifiedAt = this.createdAt;
    }

    // 엔티티 update 이전에 실행
    @PreUpdate
    public void onPreUpdate() {
        this.modifiedAt = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

