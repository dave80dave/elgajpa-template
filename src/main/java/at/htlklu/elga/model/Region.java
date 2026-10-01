package at.htlklu.elga.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "REGION")
public class Region implements Serializable {
    //region static Properties
    //endregion

    //region Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REGION_ID")
    private Integer regionId;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "DATE_CREATED")
    private LocalDateTime dateCreated;

    @NotBlank
    @Column(name = "NAME")
    private String name;
    //endregion

    //region Constructors
    public Region() {
    }

    public Region(String name) {
        this.name = name;
    }
    //endregion

    //region Getter and Setter
    public Integer getRegionId() { return regionId; }
    public void setRegionId(Integer regionId) { this.regionId = regionId; }
    public String getCreatedBy() { return createdBy; }
    public LocalDateTime getDateCreated() { return dateCreated; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    //endregion

    //region toString
    @Override
    public String toString() {
        return String.format("%s (%d)", name, regionId);
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
        if (!(obj instanceof Region region)) return false;
        return regionId != null && Objects.equals(regionId, region.getRegionId());
    }
    //endregion
}
