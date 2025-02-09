package com.franmunozbetanzos.portfolio.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import static com.franmunozbetanzos.portfolio.model.TableColumnsConstants.CREATED_AT;
import static com.franmunozbetanzos.portfolio.model.TableColumnsConstants.UPDATED_AT;
import static org.hibernate.annotations.SoftDeleteType.ACTIVE;


@MappedSuperclass
@SoftDelete(strategy = ACTIVE, columnName = TableColumnsConstants.ENABLED)
@Getter
@Setter
@ToString
@Data
public abstract class BaseEntity {


    @CreationTimestamp
    @Column(name = CREATED_AT, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = UPDATED_AT)
    private LocalDateTime updatedAt;
}
