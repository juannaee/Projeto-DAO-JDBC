package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nameDepartment;

	public Department() {

	}

	public Department(String nameDepartment) {
		this.nameDepartment = nameDepartment;

	}

	public void assignId(Integer id) {
		if (this.id == null) {
			this.id = id;
		} else {
			throw new IllegalStateException("O ID já foi atribuído a este departamento");
		}
	}

	public Integer getId() {
		return this.id;
	}

	public String getNameDepartment() {
		return this.nameDepartment;
	}

	public void setNameDepartment(String nameDepartment) {
		this.nameDepartment = nameDepartment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Department other = (Department) obj;
		return Objects.equals(id, other.id);

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( getId() + " - " +  getNameDepartment() + "\n");
		return sb.toString();
	}


}
