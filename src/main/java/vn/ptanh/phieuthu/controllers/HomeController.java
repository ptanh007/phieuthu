package vn.ptanh.phieuthu.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.ptanh.phieuthu.entities.PhieuThu;
import vn.ptanh.phieuthu.entities.PhieuThu2;
import vn.ptanh.phieuthu.models.PhieuThuModel;
import vn.ptanh.phieuthu.repositories.PhieuThu2Repository;
import vn.ptanh.phieuthu.repositories.PhieuThuRepository;
import vn.ptanh.phieuthu.services.PhieuThuService;
import vn.ptanh.phieuthu.utils.CommonUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        log.info("Ngay : {}", ngay);
        model.addAttribute("thus", phieuThuRepo.findByNgayThu1(ngay));
        //  model.addAttribute("thus", phieuThuRepo.findAll());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
        model.addAttribute("ngay", sdf.format(ngay));

        return "index";
    }

    /***
     *
     * @param phieuthuModel
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String add(@ModelAttribute PhieuThuModel phieuthuModel,@RequestParam(value="action", required = false) String action, Model model)
    {
        if(action != null && action.equals("search")){
            List<PhieuThu> thuList = phieuThuRepo.findByDongLan1(phieuthuModel.getHoTen(), phieuthuModel.getLop());
            model.addAttribute("searchlist", thuList);
            model.addAttribute("latest", getLatestList());
            model.addAttribute("phieuthuModel", phieuthuModel);
            return "add";
        }
        model.addAttribute("phieuthuModel", phieuthuModel);
        model.addAttribute("latest", getLatestList());
        return "add";
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

    private List<PhieuThu> getLatestList() {
        return phieuThuRepo.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"))).getContent();
    }

    @RequestMapping("/phieuthu")
    public String phieuThu(@RequestParam(value = "id", required = true) Long id, Model model){
        //search phiếu thu
        PhieuThu phieuThu = phieuThuRepo.findById(id).get();

        PhieuThuModel phieuModel = convertToModel(phieuThu);
        model.addAttribute("phieuthuModel", phieuModel);

        return "inPhieuThu";
    }


    private PhieuThuModel convertToModel(PhieuThu phieuThu) {
        PhieuThuModel phieuModel = new PhieuThuModel();
        phieuModel.setHoTen(phieuThu.getHoTen());
        phieuModel.setLop(phieuThu.getLop());
        phieuModel.setTienPhaiNop(phieuThu.getTienPhaiNop());
        phieuModel.setThu(phieuThu.getThu());
        phieuModel.setTienBangChu(CommonUtil.tienBangChu(phieuThu.getThu()));
        phieuModel.setTienConLai(phieuThu.getTienConLai());
        phieuModel.setNgayGioTao(phieuThu.getNgayGioTao());
        phieuModel.setNgayGioThu(new Timestamp(phieuThu.getNgayGioThu().getTime()));
        //TODO
        if (phieuThu.isDeleted()){
           phieuModel.setLydo("Thu tạm tiền học phí " + phieuThu.getLop());
        } else {
            phieuModel.setLydo("Thu tạm tiền học phí " + phieuThu.getLop());
        }
        return phieuModel;
    }

    private PhieuThuModel convertToModel(PhieuThu2 phieuThu) {
        PhieuThuModel phieuModel = new PhieuThuModel();
        phieuModel.setHoTen(phieuThu.getHoTen());
        phieuModel.setLop(phieuThu.getLop());
        phieuModel.setTienPhaiNop(phieuThu.getTienPhaiNop());
        phieuModel.setThu(phieuThu.getThu());
        phieuModel.setTienBangChu(CommonUtil.tienBangChu(phieuThu.getThu()));
        //phieuModel.setTienConLai(phieuThu.getTienConLai());
        phieuModel.setNgayGioTao(phieuThu.getNgayGioTao());
        phieuModel.setNgayGioThu(new Timestamp(phieuThu.getNgayGioThu().getTime()));

        phieuModel.setLydo("Thu tiền học phí " + phieuThu.getLop());

        return phieuModel;
    }

    @RequestMapping("/hoadon2")
    public String thuLan2(@RequestParam(value = "id", required = true) Long id, Model model){
        //search phiếu lần đầu, redirect to add
        PhieuThu p1 = phieuThuRepo.findById(id).get();

        //save phieu thu 2
        Timestamp current = new Timestamp(new Date().getTime());
        PhieuThu2 p2 = new PhieuThu2(p1.getId(),
                p1.getHoTen(),
                p1.getNgaySinh(),
                p1.getLop(),
                p1.getTienPhaiNop(),
                p1.getTienConLai(),
                current,
                current,
                null);
        p2 = phieuThu2Repo.save(p2);

        //print
        PhieuThuModel phieuModel = convertToModel(p2);
        phieuModel.setLydo("Thu hoc phi " + p2.getLop());

        model.addAttribute("phieuthuModel", phieuModel);
        return "inPhieuThu";
    }
}
