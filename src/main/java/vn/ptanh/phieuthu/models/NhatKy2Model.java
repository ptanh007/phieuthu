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
public class NhatKy2Model {
    Long id;

    Integer soPhieuThu;

    String hoTen;

    String ngaySinh;

    String lop;

    BigDecimal hocPhi;

    BigDecimal tienLan1;

    BigDecimal tienLan2;

    BigDecimal tienConLai;

    BigDecimal tien1Lan;


    Timestamp ngayGioThu;
    Timestamp ngayGioTao;

    boolean isThuLan2;
    Timestamp ngayGioThu1;

    String so_hoa_don;

}
