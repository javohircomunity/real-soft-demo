package com.example.demo.domain;

import com.example.demo.dto.enums.ActionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "product_reports")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductReport extends AbstractEntity {

    private Double count;

    @Column(name = "action_type")
    @Enumerated(EnumType.ORDINAL)
    private ActionType actionType;

    @ManyToOne(fetch = FetchType.LAZY)
    private LocalUser localUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;
}
