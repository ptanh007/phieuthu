package vn.ptanh.phieuthu.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author tuanhpham.
 */
@Entity
@Table(name = "phieu_thu_2")
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhieuThu2 {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    Long id;

    @Transient
    String hoTen;

    @Transient
    String ngaySinh;

    @Transient
    String lop;

    @Transient
    BigDecimal tienPhaiNop;

    BigDecimal thu;

    @Temporal(TemporalType.DATE)
    Date ngayGioThu;

    Timestamp ngayGioTao;
}
