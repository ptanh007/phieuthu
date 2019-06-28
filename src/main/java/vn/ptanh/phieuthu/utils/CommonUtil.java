package vn.ptanh.phieuthu.utils;

import java.math.BigDecimal;

/**
 * @author tuanhpham.
 */
public class CommonUtil {
    static final String[] SO = new String[]{
            "không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"
    };

    /**
     * So tien bang chu
     * @param tien
     * @return
     */
    public static String tienBangChu(BigDecimal tien) {
        String trieu = chu((int)Math.round(tien.doubleValue()/1000000));
        int nganInt = (int)Math.round(tien.doubleValue()%1000000)/1000;
        String ngan = chu(nganInt);

        String tienStr = "";
        if (!trieu.isEmpty()){
            tienStr += (trieu+ " triệu ");
        }
        if (!ngan.isEmpty()) {
            tienStr += (ngan + " ngàn");
        }
        tienStr = tienStr.substring(0,1).toUpperCase() + tienStr.substring(1);
        return tienStr + " đồng";
    }

    private static String chu(int so){
        String tram = so/100==0 ? "" : SO[so/100]+ " trăm";
        String muoi = so%100/10 == 0 ? "" : SO[so%100/10]+" mươi";
        String dv = so%10 == 0 ? "" : SO[so%10];
        return tram + muoi + dv;
    }
}
