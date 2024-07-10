package model.entities;

public enum WorkLevel {

	JUNIOR(1, "Junior", 0.05), PLENO(2, "Pleno", 0.10), SENIOR(3, "Senior", 0.20);

	private Integer id;
	private String displayName;
	private double bonusPercentage;

	private WorkLevel(Integer id, String displayName, double bonusPercentege) {

		this.id = id;
		this.displayName = displayName;
		this.bonusPercentage = bonusPercentege;
	}

	public int getId() {
		return id;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public double getBonusPercentage() {
		return this.bonusPercentage;
	}

	public Double calculateBaseSalary(Double salary) {
		return bonusPercentage * salary;

	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("ID: " + getId() + "\n");
		sb.append("Nivel: " + getDisplayName() + "\n");
		sb.append("Bonus: " + getBonusPercentage() + "\n");

		return sb.toString();

	}

}
