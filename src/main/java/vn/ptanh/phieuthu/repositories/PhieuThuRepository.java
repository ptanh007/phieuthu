package vn.ptanh.phieuthu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ptanh.phieuthu.entities.PhieuThu;

import java.util.Date;
import java.util.List;

/**
 * @author tuanhpham.
 */
public interface PhieuThuRepository extends JpaRepository<PhieuThu, Long> {
    @Query("SELECT p FROM PhieuThu p WHERE p.ngayGioThu = ?1 ORDER BY id")
    List<PhieuThu> findByNgayThu(Date ngaytheodoi);

    /**
     * Đóng tạm theo
     * @param ngaytheodoi
     * @return
     */
    @Query("SELECT p FROM PhieuThu p WHERE p.ngayGioThu  = ?1 AND p.tienConLai > 0 ORDER BY id")
    List<PhieuThu> findByNgayThu1(Date ngaytheodoi);

    /**
     * Đóng đủ lần 2 theo ngày
     * @param ngaytheodoi
     * @return
     */
    @Query("SELECT p FROM PhieuThu p WHERE p.ngayGioThu = ?1 AND p.thu < p.tienPhaiNop AND p.tienConLai = 0 ORDER BY id")
    List<PhieuThu> findByNgayThu2(Date ngaytheodoi);

    /**
     * Đóng đủ lần 1 theo ngày
     * @param ngaytheodoi
     * @return
     */
    @Query("SELECT p FROM PhieuThu p WHERE p.ngayGioThu = ?1 AND p.thu = p.tienPhaiNop AND p.tienConLai = 0 ORDER BY id")
    List<PhieuThu> findByNgayThu3(Date ngaytheodoi);

    /**
     * DS Đóng tạm
     * @param hoten
     * @param lop
     * @return
     */
    @Query("SELECT p FROM PhieuThu p LEFT JOIN PhieuThu WHERE (:ten is null OR p.hoTen LIKE %:ten%) AND (:lop is null OR p.lop LIKE %:lop%) AND p.thu < p.tienPhaiNop AND p.tienConLai > 0 " +
            "AND p.phieuThu2 is null ORDER BY p.id")
    List<PhieuThu> findByDongLan1(@Param("ten")String hoten, @Param("lop")String lop);

}
