package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Seller implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nameSeller;
	private Date birthDate;
	private Double baseSalary;
	private Department department;
	private WorkLevel workLevel;

	public Seller() {
		this.nameSeller = "N/D";
		this.baseSalary = 0.0;
		this.birthDate = null;
		this.department = null;
		this.workLevel = null;

	}

	public Seller(String nameSeller, Date birthDate, Double baseSalary, Department department, WorkLevel workLevel) {
		this.baseSalary = baseSalary;
		this.birthDate = birthDate;
		this.nameSeller = nameSeller;
		this.department = department;
		this.workLevel = workLevel;
	}

	public void assignId(Integer id) {
		if (this.id == null) {
			this.id = id;
		} else {
			throw new IllegalStateException("O ID já foi atribuído a este vendedor.");
		}
	}

	public WorkLevel getWorkLevel() {
		return workLevel;
	}

	public void setWorkLevel(WorkLevel workLevel) {
		this.workLevel = workLevel;
	}

	public int getId() {
		return this.id;
	}

	public String getNameSeller() {
		return nameSeller;
	}

	public void setNameSeller(String nameSeller) {
		this.nameSeller = nameSeller;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Double bonusSalary() {
		return workLevel.calculateBaseSalary(baseSalary);
	}

	public Double totalSalary() {
		return baseSalary += bonusSalary();
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Seller other = (Seller) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("ID Funcionario: " + id + "\n");
		sb.append("Nome: " + nameSeller + "\n");
		sb.append("Salario: " + baseSalary + "\n");
		sb.append("Bonus: " + bonusSalary() + "\n");
		sb.append("Salario Total: " + totalSalary() + "\n");
		;
		sb.append("Aniversario: " + birthDate + "\n");
		sb.append("Senioridade: " + workLevel.getDisplayName() + "\n");
		sb.append("\n");
		sb.append("Informações departamento: " + "\n");
		sb.append(department);
		sb.append("\n");
		return sb.toString();
	}

}
