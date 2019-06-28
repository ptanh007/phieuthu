package vn.ptanh.phieuthu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptanh.phieuthu.entities.YearSequence;

/**
 * @author tuanhpham.
 */
public interface YearSequenceRepository extends JpaRepository<YearSequence, Integer> {
}
