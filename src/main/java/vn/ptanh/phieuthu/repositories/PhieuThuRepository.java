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
    /**
     * Đóng tạm theo ngay
     * @param ngaytheodoi
     * @return
     */
    @Query("SELECT p FROM PhieuThu p LEFT JOIN p.phieuThu2 p2 WHERE p.ngayGioThu  = ?1 OR p2.ngayGioThu = ?1 ORDER BY p.id")
    List<PhieuThu> getNhatKy(Date ngaytheodoi);

    /**
     * Đóng tạm theo ngay
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
    @Query("SELECT p FROM PhieuThu p JOIN p.phieuThu2 p2 WHERE p2.ngayGioThu = ?1 ORDER BY p2.id")
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
    @Query("SELECT p FROM PhieuThu p LEFT JOIN p.phieuThu2 p2 WHERE (:ten is null OR p.hoTen LIKE %:ten%) AND (:lop is null OR p.lop LIKE %:lop%) AND p.thu < p.tienPhaiNop AND p.tienConLai > 0 " +
            "AND p2 is null ORDER BY p.id")
    List<PhieuThu> findByDongLan1(@Param("ten")String hoten, @Param("lop")String lop);



    /**
     * Đóng đủ lần 1 theo ngày
     * @return
     */
    @Query("SELECT p.lop FROM PhieuThu p WHERE p.lop like '%19' GROUP BY p.lop")
    List<String> getDSLop();

    /**
     * Đóng đủ lần 1 theo ngày
     * @return
     */
    @Query("SELECT p FROM PhieuThu p WHERE p.lop like ?1")
    List<PhieuThu> getDSHocvien(String lop);

    @Query("SELECT p FROM PhieuThu p LEFT JOIN p.phieuThu2 p2 WHERE NOT p.phieuThu2 is null ORDER BY p.phieuThu2.ngayGioTao")
    List<PhieuThu> getLatestPhieuThu2(int limit);
}