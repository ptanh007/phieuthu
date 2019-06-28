package vn.ptanh.phieuthu;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.ptanh.phieuthu.utils.CommonUtil;

import java.math.BigDecimal;

/**
 * @author tuanhpham.
 */

public class CommonUtilTest {
    static final Logger log = LoggerFactory.getLogger(CommonUtilTest.class);
    @Test
    public void testChuSo1(){
        BigDecimal tien = new BigDecimal(7000000);
        String chu = CommonUtil.tienBangChu(tien);
        log.info("So tien:{} chu:{}",tien, chu);
    }

    @Test
    public void testChuSo2(){
        BigDecimal tien = new BigDecimal(7500000);
        String chu = CommonUtil.tienBangChu(tien);
        log.info("So tien:{} chu:{}",tien, chu);
    }

    @Test
    public void testChuSo3(){
        BigDecimal tien = new BigDecimal(7540000);
        String chu = CommonUtil.tienBangChu(tien);
        log.info("So tien:{} chu:{}",tien, chu);
    }
}
