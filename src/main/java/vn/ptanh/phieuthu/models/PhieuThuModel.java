package vn.ptanh.phieuthu.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author tuanhpham.
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PhieuThuModel {
    Long id;

    Integer soPhieuThu;

    String hoTen;

    String ngaySinh;

    String lop;

    String lydo;

    BigDecimal tienPhaiNop;

    BigDecimal thu;

    String tienBangChu;

    BigDecimal tienConLai;

    boolean deleted;


    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Timestamp ngayGioThu;

    Timestamp ngayGioTao;
}
