package vn.ptanh.phieuthu.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.ptanh.phieuthu.entities.PhieuThu;
import vn.ptanh.phieuthu.entities.PhieuThu2;
import vn.ptanh.phieuthu.models.NhatKy2Model;
import vn.ptanh.phieuthu.models.PhieuThuModel;
import vn.ptanh.phieuthu.models.SearchModel;
import vn.ptanh.phieuthu.repositories.PhieuThu2Repository;
import vn.ptanh.phieuthu.repositories.PhieuThuRepository;
import vn.ptanh.phieuthu.services.PhieuThuService;
import vn.ptanh.phieuthu.utils.CommonUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author tuanhpham.
 */
@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    PhieuThuRepository phieuThuRepo;

    @Autowired
    PhieuThu2Repository phieuThu2Repo;

    @Autowired
    PhieuThuService phieuThuService;

    @RequestMapping("/")
    public String home(@RequestParam(required = false)@DateTimeFormat(pattern="dd/MM/yyyy") Date ngay, Model model)
    {
        if (ngay == null){
            ngay = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
        model.addAttribute("ngay", sdf.format(ngay));

        log.info("Ngay : {}", ngay);
        List<PhieuThu> phieuThuList = phieuThuRepo.getNhatKy(ngay);

        List<NhatKy2Model> phieuThuModel2List = convertToNhatKy2(phieuThuList, ngay);
        model.addAttribute("nhatkythu2List", phieuThuModel2List);

        BigDecimal lan1Sum = BigDecimal.ZERO;
        BigDecimal lan2Sum = BigDecimal.ZERO;
        BigDecimal nop1lanSum = BigDecimal.ZERO;
        for (NhatKy2Model nk2 : phieuThuModel2List) {
            if (nk2.getTienLan2()!=null) {
                lan2Sum = lan2Sum.add(nk2.getTienLan2());
            }
            if (nk2.getTien1Lan()!=null) {
                nop1lanSum = nop1lanSum.add(nk2.getTien1Lan());
            }

            if(!nk2.isThuLan2() && nk2.getTienLan1()!=null){
                lan1Sum = lan1Sum.add(nk2.getTienLan1());
            }
        }

        BigDecimal tongTien = lan1Sum.add(lan2Sum).add(nop1lanSum);
        model.addAttribute("sumTienLan1", lan1Sum);
        model.addAttribute("sumTienLan2", lan2Sum);
        model.addAttribute("sumTien1Lan", nop1lanSum);
        model.addAttribute("sumTongTien", tongTien);

        List<NhatKy2Model> bang1List = convertToNhatKy3(phieuThuRepo.findByNgayThu(ngay),ngay);
        BigDecimal tongTienThuTam = bang1List.stream().map(NhatKy2Model::getTienLan1).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal tongTien1Lan = bang1List.stream().map(NhatKy2Model::getTien1Lan).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("bang1SumThuTam", tongTienThuTam);
        model.addAttribute("bang1Sum1Lan", tongTien1Lan);
        model.addAttribute("bang1Sum", tongTien1Lan.add(tongTienThuTam));
        model.addAttribute("bang1List", bang1List);
        model.addAttribute("bang2List", phieuThuRepo.findByNgayThu2(ngay));

        return "index";
    }

    @RequestMapping("/class")
    public String classList(@RequestParam(required = false)String lop, Model model)
    {
        if (lop == null){
            lop = "";
        }
        log.info("Lop : {}", lop);
        model.addAttribute("lop", lop);
        model.addAttribute("lopList", phieuThuRepo.getDSLop());
        if(lop == ""){
            model.addAttribute("hocvienList", new ArrayList<>());
        } else {
            model.addAttribute("hocvienList", phieuThuRepo.getDSHocvien(lop));
        }

        return "class";
    }

    /***
     *
     * @param searchModel
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String add(@ModelAttribute SearchModel searchModel, @RequestParam(value="action", required = false) String action, Model model)
    {
        if(searchModel==null){
            searchModel = new SearchModel();
        }

        if(action != null && action.equals("search")){
            Date ngayGioThu = null;
            if(searchModel.getNgayGioThu()!=null){
                ngayGioThu = new Date(searchModel.getNgayGioThu().getTime());
            }
            List<PhieuThu> thuList = phieuThuRepo.findByDongLan1(searchModel.getHoTen(), searchModel.getLop(), ngayGioThu);
            model.addAttribute("searchlist", thuList);
            //model.addAttribute("latest", getLatestList());
            model.addAttribute("phieuthuModel", searchModel);
            return "add";
        }
        model.addAttribute("phieuthuModel", searchModel);
        //model.addAttribute("latest", getLatestList());
        return "add";
    }

    @PostMapping("/edit")
    @ResponseBody
    public String edit(long id, String hoTen, String lop, @DateTimeFormat(pattern = "dd/MM/yyyy") Date ngayThu){
        //save phieu thu
        Timestamp current = new Timestamp(new Date().getTime());

        //get db object
        PhieuThu phieuThu = phieuThuRepo.findById(id).get();
        if(phieuThu == null){
            return "ERROR 404";
        }
        //update
        phieuThu.setHoTen(hoTen);
        phieuThu.setLop(lop);
        phieuThu.setNgayGioThu(ngayThu);

        phieuThu = phieuThuRepo.save(phieuThu);
        //tra ve id
        return phieuThu.getId().toString();
    }

    @PostMapping("/thulan1")
    @ResponseBody
    public String thuLan1(@ModelAttribute PhieuThuModel phieuthuModel,@RequestParam(value="action", required = false) String action, Model model){
        //save phieu thu
        Timestamp current = new Timestamp(new Date().getTime());


        PhieuThu phieuThu = new PhieuThu(
                null,
                phieuthuModel.getHoTen(),
                phieuthuModel.getNgaySinh(),
                phieuthuModel.getLop(),
                phieuthuModel.getTienPhaiNop(),
                phieuthuModel.getThu(),
                phieuthuModel.getTienConLai(),
                false,
                current,
                current,
                null,
                null);
        phieuThu = phieuThuService.dangkyPhieuThu(phieuThu);
        //tra ve id
        return phieuThu.getId().toString();
    }

    /**
     * In phiếu thu lần 1 & thu 1lần
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/phieuthu")
    public String phieuThu(@RequestParam(value = "id", required = true) Long id, Model model){
        //search phiếu thu
        PhieuThu phieuThu = phieuThuRepo.findById(id).get();

        PhieuThuModel phieuModel = convertToModel(phieuThu);
        model.addAttribute("phieuthuModel", phieuModel);

        return "inPhieuThu";
    }

    /**
     * In phiếu thu lần 2
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/phieuthu2")
    public String phieuThu2(@RequestParam(value = "id", required = true) Long id, Model model){
        //search phiếu thu
        PhieuThu phieuThu = phieuThuRepo.findById(id).get();

        PhieuThuModel phieuModel = convertToModel2(phieuThu);
        model.addAttribute("phieuthuModel", phieuModel);

        return "inPhieuThu";
    }



    private PhieuThuModel convertToModel(PhieuThu phieuThu) {
        PhieuThuModel phieuModel = new PhieuThuModel(
                phieuThu.getId(),
                phieuThu.getSoPhieuThu(),
                phieuThu.getHoTen(),
                phieuThu.getNgaySinh(),
                phieuThu.getLop(),
                "Thu tiền học phí " + phieuThu.getLop(),
                phieuThu.getTienPhaiNop(),
                phieuThu.getThu(),
                CommonUtil.tienBangChu(phieuThu.getThu()),
                phieuThu.getTienConLai(),
                false,
                phieuThu.getNgayGioTao(),
                new Timestamp(phieuThu.getNgayGioThu().getTime())
        );
        if (phieuThu.getThu().compareTo(phieuThu.getTienPhaiNop()) == -1){
           phieuModel.setLydo("Thu tạm tiền học phí " + phieuThu.getLop());
        }
        return phieuModel;
    }

    private PhieuThuModel convertToModel2(PhieuThu phieuThu) {
        return new PhieuThuModel(
                phieuThu.getId(),
                phieuThu.getSoPhieuThu(),
                phieuThu.getHoTen(),
                phieuThu.getNgaySinh(),
                phieuThu.getLop(),
                "Thu tiền học phí " + phieuThu.getLop(),
                phieuThu.getTienPhaiNop(),
                phieuThu.getPhieuThu2().getThu(),
                CommonUtil.tienBangChu(phieuThu.getPhieuThu2().getThu()),
                BigDecimal.ZERO,
                false,
                phieuThu.getPhieuThu2().getNgayGioTao(),
                new Timestamp(phieuThu.getPhieuThu2().getNgayGioThu().getTime())
        );
    }

    private List<NhatKy2Model> convertToNhatKy2(List<PhieuThu> phieuThuList, Date ngay) {
        List<NhatKy2Model> modelList = new ArrayList<>();
        phieuThuList.forEach( phieuThu -> {
            if(ngay.equals(phieuThu.getNgayGioThu())) {
                // thu lan 1
                NhatKy2Model model = new NhatKy2Model(
                        phieuThu.getId(),
                        phieuThu.getSoPhieuThu(),
                        phieuThu.getHoTen(),
                        phieuThu.getNgaySinh(),
                        phieuThu.getLop(),
                        phieuThu.getTienPhaiNop(),
                        phieuThu.getTienPhaiNop().equals(phieuThu.getThu()) ? null : phieuThu.getThu(),  //hocphi=thu, lan1=0
                        null,
                        BigDecimal.ZERO.equals(phieuThu.getTienConLai()) ? null : phieuThu.getTienConLai(),
                        phieuThu.getTienPhaiNop().equals(phieuThu.getThu()) ? phieuThu.getThu(): null,  //hocphi=thu, lan2=thu
                        new Timestamp(phieuThu.getNgayGioThu().getTime()),
                        phieuThu.getNgayGioTao(),
                        false,
                        null,
                        null
                );
                modelList.add(model);
            }
            if (phieuThu.getPhieuThu2() !=null && ngay.equals(phieuThu.getPhieuThu2().getNgayGioThu())){
                // phieu thu lan 2
                NhatKy2Model model = new NhatKy2Model(
                        phieuThu.getId(),
                        phieuThu.getSoPhieuThu(),
                        phieuThu.getHoTen(),
                        phieuThu.getNgaySinh(),
                        phieuThu.getLop(),
                        phieuThu.getTienPhaiNop(),
                        phieuThu.getThu(),
                        phieuThu.getPhieuThu2().getThu(),
                        null,
                        null,
                        new Timestamp(phieuThu.getPhieuThu2().getNgayGioThu().getTime()),
                        phieuThu.getPhieuThu2().getNgayGioTao(),
                        true,
                        new Timestamp(phieuThu.getNgayGioThu().getTime()),
                        null
                );
                modelList.add(model);
            }
        });
        return modelList;
    }

    /**
     *
     * @param phieuThuList
     * @param ngay
     * @return
     */
    private List<NhatKy2Model> convertToNhatKy3(List<PhieuThu> phieuThuList, Date ngay) {
        List<NhatKy2Model> modelList = new ArrayList<>();
        phieuThuList.forEach( phieuThu -> {
            if (ngay.equals(phieuThu.getNgayGioThu())) {
                // thu lan 1
                NhatKy2Model model = new NhatKy2Model(
                        phieuThu.getId(),
                        phieuThu.getSoPhieuThu(),
                        phieuThu.getHoTen(),
                        phieuThu.getNgaySinh(),
                        phieuThu.getLop(),
                        phieuThu.getTienPhaiNop(),
                        phieuThu.getTienPhaiNop().equals(phieuThu.getThu()) ? null : phieuThu.getThu(),  //hocphi=thu, lan1=0
                        null,
                        BigDecimal.ZERO.equals(phieuThu.getTienConLai()) ? null : phieuThu.getTienConLai(),
                        phieuThu.getTienPhaiNop().equals(phieuThu.getThu()) ? phieuThu.getThu() : null,  //hocphi=thu, lan2=thu
                        new Timestamp(phieuThu.getNgayGioThu().getTime()),
                        phieuThu.getNgayGioTao(),
                        false,
                        null,
                        null
                );
                modelList.add(model);
            }
        });
        return modelList;
    }

    @RequestMapping("/hoadon2")
    public String thuLan2(@RequestParam(value = "id", required = true) Long id, Model model){
        //search phiếu lần đầu, redirect to add
        PhieuThu p1 = phieuThuRepo.findById(id).get();
        //lan 2 da ton tai.
        if(p1.getPhieuThu2()!=null) {
            model.addAttribute("errorMsg", "Đã đóng lần 2");
            return "error";

        }
        //save phieu thu 2
        Timestamp current = new Timestamp(new Date().getTime());
        PhieuThu2 p2 = p1.getPhieuThu2();
        if(p2 == null) {
            p2 = new PhieuThu2(p1.getId(),
                    p1.getHoTen(),
                    p1.getNgaySinh(),
                    p1.getLop(),
                    p1.getTienPhaiNop(),
                    p1.getTienConLai(),
                    current,
                    current);
        }
        p2 = phieuThu2Repo.save(p2);
        p1.setPhieuThu2(p2);

        //print
        PhieuThuModel phieuModel = convertToModel2(p1);
        phieuModel.setLydo("Thu học phí " + p1.getLop());

        model.addAttribute("phieuthuModel", phieuModel);
        return "inPhieuThu";
    }
}
