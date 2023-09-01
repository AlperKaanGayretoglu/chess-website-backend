package com.alpergayretoglu.chess_website_backend.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private String id;

    @Column(name = "created")
    @CreatedDate
    @Setter(AccessLevel.NONE)
    private ZonedDateTime created;

    @Column(name = "updated")
    @LastModifiedDate
    @Setter(AccessLevel.NONE)
    private ZonedDateTime updated;

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;

        if (!Objects.equals(getClass(), o.getClass())) {
            return false;
        }

        BaseEntity that = (BaseEntity) o;
        return this.id != null && Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
