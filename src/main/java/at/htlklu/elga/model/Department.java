package at.htlklu.elga.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

@Entity																
@Table(name = "DEPARTMENT")
public class Department implements Serializable
{
	//region static Properties
	//endregion


	//region Properties
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DEPARTMENT_ID")
	private Integer departmentId;

	// Ziel: Unidirektionale Implementierung zwischen Department und Hospital
	@NotNull		// verpflichtend
	@ManyToOne(fetch = FetchType.LAZY)
	// Wir müssen den Fremdschlüssel von Hospital in der Tabelle DEPARTMENT angeben
	// Hier: HOSPITAL_ID
	@JoinColumn(name = "HOSPITAL_ID")
	// In der Variable "hospital" wird die Instanz des zugehörigen Hospitals gespeichert
	private Hospital hospital;

	@NotBlank
	private String name;
	//endregion


	//region Constructors
	public Department()
	{
	}

	public Department(String name)
	{
		this.name = name;
	}
	//endregion


	//region toString
	@Override
	public String toString()
	{
		return String.format("%s (%d)", this.name, this.departmentId);
	}
	//endregion


	//region Methods
	//endregion


	//region Getter and Setter
	public Integer getDepartmentId()
	{
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId)
	{
		this.departmentId = departmentId;
	}

	public Hospital getHospital()
	{
		return hospital;
	}
	public void setHospital(Hospital hospital)
	{
		this.hospital = hospital;
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
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
		Department department1 = this;
		if (department1 == obj)
		{
			equal = true;
		}
		else if ((obj == null) || (!(obj instanceof Department)))
		{
			equal = false;
		}
		else
		{
			Department department2 = (Department)obj;
			equal = department1.departmentId != null && Objects.equals(department1.departmentId, department2.getDepartmentId());
		}
		return equal;
	}
	//endregion
	
}
