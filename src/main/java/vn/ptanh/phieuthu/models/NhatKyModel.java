package vn.ptanh.phieuthu.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author tuanhpham.
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NhatKyModel {
    Long id;

    Integer soPhieuThu;

    String hoTen;

    String ngaySinh;

    String lop;

    BigDecimal hocPhi;

    BigDecimal tienLan1;

    BigDecimal tienLan2;

    BigDecimal tienThu;

    BigDecimal tienConLai;

    Timestamp ngayGioThu;
    Timestamp ngayGioTao;

    String soHoaDon;
}
