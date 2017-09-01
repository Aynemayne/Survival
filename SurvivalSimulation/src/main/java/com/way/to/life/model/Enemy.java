package com.way.to.life.model;

public class Enemy {

	private String name;
	private Integer power;
	private Integer attackPower;
	private Integer position;
	
	public Enemy(String name, Integer power, Integer attackPower, Integer position) {
		super();
		this.name = name;
		this.power = power;
		this.attackPower = attackPower;
		this.position = position;
	}

	public Enemy() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(Integer attackPower) {
		this.attackPower = attackPower;
	}

}
