package vn.ptanh.phieuthu.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author tuanhpham.
 */
@Setter@Getter
@AllArgsConstructor
public class PhieuThuModel {
    Long stt;

    String hoTen;

    String ngaySinh;

    String lop;

    String lydo;

    BigDecimal tienPhaiNop;

    BigDecimal thu;

    String tienBangChu;

    BigDecimal tienConLai;

    boolean deleted;
    Timestamp ngayGioThu;
    Timestamp ngayGioTao;
}
