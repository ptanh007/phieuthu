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
@Table(name = "phieu_thu")
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhieuThu {
    @Id
    @GeneratedValue
    Long id;

    String hoTen;

    String ngaySinh;

    String lop;

    BigDecimal tienPhaiNop;

    BigDecimal thu;

    BigDecimal tienConLai;

    boolean deleted;

    @Temporal(TemporalType.DATE)
    Date ngayGioThu;

    Timestamp ngayGioTao;

    Integer stt;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name="id")
    PhieuThu2 phieuThu2;
}
