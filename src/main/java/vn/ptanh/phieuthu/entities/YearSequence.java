package vn.ptanh.phieuthu.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author tuanhpham.
 */
@Entity
@Table(name = "year_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YearSequence {
    @Id
    int year;

    int nextVal;
}
