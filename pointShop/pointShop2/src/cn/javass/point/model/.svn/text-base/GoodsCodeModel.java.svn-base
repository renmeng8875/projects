package cn.javass.point.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * 商品兑换码表
 * @author Zhang Kaitao
 *
 */
@Entity
@Table(name = "tb_goods_code")
public class GoodsCodeModel implements java.io.Serializable {

    private static final long serialVersionUID = -8270666768071879501L;

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10)
    private int id;

    /** 所属商品 */
    @ManyToOne
    private GoodsModel goods;

    /** 兑换码*/
    @Column(name = "code", nullable = false, length = 100)
    private String code;
    
    /** 兑换人,实际环境中应该和用户表进行对应*/
    @Column(name = "username", nullable = true, length = 100)
    private String username;

    /** 兑换时间*/
    @Column(name = "exchange_time")
    private Date exchangeTime;
    
    /** 是否已经兑换*/
    @Column(name = "exchanged")
    private boolean exchanged = false;
    
    /** 版本 */
    @Version
    @Column(name = "version", nullable = false, length = 10)
    private int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GoodsModel getGoods() {
        return goods;
    }

    public void setGoods(GoodsModel goods) {
        this.goods = goods;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(Date exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    public boolean isExchanged() {
        return exchanged;
    }

    public void setExchanged(boolean exchanged) {
        this.exchanged = exchanged;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GoodsCodeModel other = (GoodsCodeModel) obj;
        if (id != other.id)
            return false;
        return true;
    }

}
