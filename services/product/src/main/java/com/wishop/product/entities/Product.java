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
@Table("product")
public class Product {

    @Id
    @Column("product_id")
    private Long id;

    private String name;

    private Integer status;

    private String note;

    @Column("main_list_image")
    private String mainListImage;

    @Column("main_comment")
    private String mainComment;

    @Column("main_image")
    private String mainImage;

    @Column("main_large_image")
    private String mainLargeImage;

    @Column("del_flg")
    private Integer delFlag;

    @Column("available_flg")
    private Integer availableFlag;

    private String authority;

    @Column("create_date")
    private Date createDate;

    @Column("update_date")
    private Date updateDate;

    @Column("overseas_ng_flg")
    private Integer overseasNGFlag;

    @Column("overseas_ng_force_flg")
    private Integer overseasNGForceFlag;

}
