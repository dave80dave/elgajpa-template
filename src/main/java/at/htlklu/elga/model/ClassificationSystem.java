package at.htlklu.elga.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CLASSIFICATION_SYSTEM")
public class ClassificationSystem implements Serializable {
    //region static Properties
    //endregion

    //region Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CS_ID")
    private Integer classificationSystemId;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "DATE_CREATED")
    private LocalDateTime dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CS_ID_PARENT")
    private ClassificationSystem parent;

    @Column(name = "SEQUENCE")
    private Integer sequence;

    @NotBlank
    @Column(name = "NAME")
    private String name;

    @Column(name = "NUMBER")
    private String number;

    @Column(name = "FREQUENCY")
    private Integer frequency;
    //endregion

    //region Constructors
    public ClassificationSystem() {
    }

    public ClassificationSystem(Integer sequence, String name, String number, Integer frequency) {
        this.sequence = sequence;
        this.name = name;
        this.number = number;
        this.frequency = frequency;
    }
    //endregion

    //region Getter and Setter
    public Integer getClassificationSystemId() { return classificationSystemId; }
    public void setClassificationSystemId(Integer classificationSystemId) { this.classificationSystemId = classificationSystemId; }
    public String getCreatedBy() { return createdBy; }
    public LocalDateTime getDateCreated() { return dateCreated; }
    public ClassificationSystem getParent() { return parent; }
    public void setParent(ClassificationSystem parent) { this.parent = parent; }
    public Integer getSequence() { return sequence; }
    public void setSequence(Integer sequence) { this.sequence = sequence; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public Integer getFrequency() { return frequency; }
    public void setFrequency(Integer frequency) { this.frequency = frequency; }
    //endregion

    //region toString
    @Override
    public String toString() {
        return String.format("%s (%d)", name, classificationSystemId);
    }
    //endregion

    //region Add and Remove
    //endregion

    //region HashCode and Equals
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ClassificationSystem classificationSystem)) return false;
        return classificationSystemId != null
                && Objects.equals(classificationSystemId, classificationSystem.getClassificationSystemId());
    }
    //endregion
}
