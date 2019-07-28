package vn.ptanh.phieuthu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.ptanh.phieuthu.entities.PhieuThu;
import vn.ptanh.phieuthu.entities.YearSequence;
import vn.ptanh.phieuthu.repositories.PhieuThuRepository;
import vn.ptanh.phieuthu.repositories.YearSequenceRepository;

/**
 * @author tuanhpham.
 */
@Component
public class PhieuThuService {
    @Autowired
    YearSequenceRepository sequenceRepo;

    @Autowired
    PhieuThuRepository phieuThuRepos;

    public PhieuThu dangkyPhieuThu(PhieuThu phieuThu){
        int year = phieuThu.getNgayGioTao().toLocalDateTime().toLocalDate().getYear();
        YearSequence yearSequence = sequenceRepo.findById(year).orElse(new YearSequence(year,0));
        //TODO: default is max
        if(yearSequence.getNextVal() == 0){

        }
        yearSequence.setNextVal(yearSequence.getNextVal()+1);
        sequenceRepo.save(yearSequence);

        phieuThu.setSoPhieuThu(yearSequence.getNextVal());
        phieuThuRepos.save(phieuThu);

        return phieuThu;
    }

}
