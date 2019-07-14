package vn.ptanh.phieuthu.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author tuanhpham.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchModel {
    String lop;
    String hoTen;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date ngayGioThu;
}
