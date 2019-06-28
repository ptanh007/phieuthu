package vn.ptanh.phieuthu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ptanh.phieuthu.entities.PhieuThu2;

import java.util.Date;
import java.util.List;

/**
 * @author tuanhpham.
 */
public interface PhieuThu2Repository extends JpaRepository<PhieuThu2, Long> {
    /*@Query("SELECT p FROM PhieuThu2 p WHERE p.ngayGioThu = ?1 ORDER BY id")
    List<PhieuThu2> findByNgayThu(Date ngaytheodoi);

    *//**
     * Đóng tạm
     * @param ngaytheodoi
     * @return
     *//*
    @Query("SELECT p FROM PhieuThu2 p WHERE p.ngayGioThu  = ?1 AND p.tienConLai > 0 ORDER BY id")
    List<PhieuThu2> findByNgayThu1(Date ngaytheodoi);

    *//**
     * Đóng đủ lần 2
     * @param ngaytheodoi
     * @return
     *//*
    @Query("SELECT p FROM PhieuThu2 p WHERE p.ngayGioThu = ?1 AND p.thu < p.tienPhaiNop AND p.tienConLai = 0 ORDER BY id")
    List<PhieuThu2> findByNgayThu2(Date ngaytheodoi);

    *//**
     * Đóng đủ lần 1
     * @param ngaytheodoi
     * @return
     *//*
    @Query("SELECT p FROM PhieuThu2 p WHERE p.ngayGioThu = ?1 AND p.thu = p.tienPhaiNop AND p.tienConLai = 0 ORDER BY id")
    List<PhieuThu2> findByNgayThu3(Date ngaytheodoi);

    *//**
     * DS Đóng đủ lần 2
     * @param hoten
     * @param lop
     * @return
     *//*
    @Query("SELECT p FROM PhieuThu2 p WHERE (:ten is null OR p.hoTen LIKE %:ten%) AND (:lop is null OR p.lop LIKE %:lop%) AND p.thu < p.tienPhaiNop AND p.tienConLai > 0 " +
            "AND NOT EXISTS (SELECT p1 FROM PhieuThu2 p1 WHERE p1.idLan1 = p.id) ORDER BY p.id")
    List<PhieuThu2> findByDongLan1(@Param("ten") String hoten, @Param("lop") String lop);*/

}
