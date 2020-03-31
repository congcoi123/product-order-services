package com.wishop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("product_class")
public class ProductClass {

    @Id
    @Column("product_class_id")
    private Long id;

    @Column("product_id")
    private Long productId;

    @Column("classcategory_id1")
    private Long classcategoryId1;

    @Column("classcategory_id2")
    private Long classcategoryId2;

    @Column("product_type_id")
    private Long productTypeId;

    @Column("product_code")
    private String productCode;

    private Double price01;

    private Double price02;

    @Column("point_rate")
    private Double pointRate;

    @Column("del_flg")
    private Integer delFlag;

    @Column("available_flg")
    private Integer availableFlag;

    private String authority;

    @Column("create_date")
    private Date createDate;

    @Column("update_date")
    private Date updateDate;

}
