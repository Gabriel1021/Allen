package com.gabriel.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * help categories
 * </p>
 *
 * @author Z.XX
 * @since 2020-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HelpCategory extends Model<HelpCategory> {

    private static final long serialVersionUID = 1L;

    private Integer helpCategoryId;

    private String name;

    private Integer parentCategoryId;

    private String url;


    @Override
    protected Serializable pkVal() {
        return this.helpCategoryId;
    }

}
