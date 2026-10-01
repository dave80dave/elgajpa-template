package at.htlklu.elga.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "HOSPITAL")
public class Hospital implements Serializable
{
	//region static Properties
	public static Comparator<Hospital> BY_NAME = Comparator.comparing(Hospital::getName);
	//endregion


	//region Properties
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HOSPITAL_ID")
	private Integer hospitalId;

	@NotBlank
	private String name;
	@NotBlank
	@Column(name = "NAME_SHORT")
	private String nameShort;
	@NotBlank
	private String city;
	@NotBlank
	@Column(name = "ZIP_CODE")
	private String zipCode;
	@NotBlank
	private String street;
	@NotBlank
	private String web;


	// unidirektional navigierbar, d.h.
	// vom Hospital kann ich auf die zugehörigen Departments zugreifen aber nicht umgekehrt
	@OneToMany(mappedBy = "hospital",
			   cascade = CascadeType.ALL,
			   fetch = FetchType.LAZY)
	private Set<Department> departments = new HashSet<Department>();
	//endregion




    @OneToMany(mappedBy = "hospital",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	private Set<DoctorHospital> doctorHospitals = new HashSet<>();

	//region Constructors
	public Hospital()
	{
	}

	public Hospital(String name,
					String nameShort,
					String city,
					String zipCode,
					String street,
					String web)
	{
		this.name = name;
		this.nameShort = nameShort;
		this.city = city;
		this.zipCode = zipCode;
		this.street = street;
		this.web = web;
	}
	//endregion


	//region toString
	@Override
	public String toString()
	{
		return String.format("%s (%d)", this.nameShort, this.hospitalId);
	}
	//endregion


	//region Methods


	//endregion


	//region Getter and Setter


    public Set<DoctorHospital> getDoctorHospitals() {
        return doctorHospitals;
    }
	public static Comparator<Hospital> getByName() {
		return BY_NAME;
	}

    public Integer getHospitalId()
	{
		return hospitalId;
	}
	public void setHospitalId(Integer hospitalId)
	{
		this.hospitalId = hospitalId;
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public String getNameShort()
	{
		return nameShort;
	}
	public void setNameShort(String nameShort)
	{
		this.nameShort = nameShort;
	}

	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}

	public String getZipCode()
	{
		return zipCode;
	}
	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}

	public String getStreet()
	{
		return street;
	}
	public void setStreet(String street)
	{
		this.street = street;
	}

	public String getWeb()
	{
		return web;
	}
	public void setWeb(String web)
	{
		this.web = web;
	}

	public Set<Department> getDepartments()
	{
		return departments;
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
	public boolean equals(Object obj)
	{
		boolean equal;
		Hospital hospital1 = this;
		if (hospital1 == obj)
		{
			equal = true;
		}
		else if ((obj == null) || (!(obj instanceof Hospital)))
		{
			equal = false;
		}
		else
		{
			Hospital hospital2 = (Hospital)obj;
			equal = hospital1.hospitalId != null && Objects.equals(hospital1.hospitalId, hospital2.getHospitalId());
		}
		return equal;
	}
	//endregion
	
}
