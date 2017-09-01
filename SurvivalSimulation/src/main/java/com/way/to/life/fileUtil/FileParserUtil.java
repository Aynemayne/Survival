package com.way.to.life.fileUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.way.to.life.model.Enemy;
import com.way.to.life.model.Hero;

public class FileParserUtil {


	public static void simulate(File file) throws Exception {

		Scanner scanner = null;
		Integer resourcePosition = null;
		BufferedReader bufferedReader = null;
		String outputString = null;
		BufferedWriter bw = null;
		File outputFile = new File(file.getParentFile() + File.separator + "OutputSample.txt" );

		try {
			bw = new BufferedWriter(new FileWriter(outputFile));
			FileReader fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);

			Hero hero = new Hero();
			List<Enemy> enemyList = new ArrayList<Enemy>();
			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				scanner = new Scanner((line));
				scanner.useDelimiter("\\D+");
				if (scanner.hasNextInt()) {

					Integer scannerIntegerValue = Integer.valueOf(scanner.nextInt());
					if (line.startsWith("Resources are")) {
						resourcePosition = scannerIntegerValue;
					} else if (line.startsWith("Hero has")) {
						hero.setPower(scannerIntegerValue);
					} else if (line.startsWith("Hero attack")) {
						hero.setAttackPower(scannerIntegerValue);
					} else if ((line.contains("has")) && (!line.startsWith("Hero"))) {
						for (Enemy enemy : enemyList) {
							if (line.contains(enemy.getName())) {
								enemy.setPower(scannerIntegerValue);
								break;
							}
						}

					} else if ((line.contains("attack")) && (!line.startsWith("Hero"))) {
						for (Enemy enemy : enemyList) {
							if (line.contains(enemy.getName())) {
								enemy.setAttackPower(scannerIntegerValue);
								break;
							}
						}
					} else {
						for (Enemy enemy : enemyList) {
							if (line.contains(enemy.getName())) {
								if (enemy.getPosition() == null) {
									enemy.setPosition(scannerIntegerValue);
									break;
								} else {
									Enemy newEnemy = new Enemy(enemy.getName(), enemy.getPower(),
											enemy.getAttackPower(), scannerIntegerValue);
									enemyList.add(newEnemy);
									break;
								}

							}
						}
					}

				} else {
					if (line.contains("Enemy")) {
						Enemy enemy = new Enemy();
						enemy.setName(line.substring(0, line.indexOf(' ')));
						enemyList.add(enemy);
					}
				}
				scanner.close();
			}

			outputString = calculateChanceOfSurvival(hero, enemyList, resourcePosition);
			bw.write(outputString);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (bw != null) {
				bw.close();
			}
		}

	}

	public static String calculateChanceOfSurvival(Hero hero, List<Enemy> enemyList, Integer position)
			throws IOException {
		
		String content = "Hero started journey with " + hero.getPower() + " HP!\n";
		try {
			Integer spentPowerOfHero = new Integer(0);
			if (enemyList != null && enemyList.size() > 0 && position != null) {
				for (Integer i = 0; i.compareTo(position) < 0; i = i + new Integer(1)) {
					for (Enemy enemy : enemyList) {
						if (i.equals(enemy.getPosition())) {
							if (hero.getPower().intValue() > 0) {
								Integer heroPower = hero.getPower();
								spentPowerOfHero = ((enemy.getPower() / hero.getAttackPower())
										* enemy.getAttackPower());
								if (heroPower.compareTo(spentPowerOfHero) > 0) {
									hero.setPower(heroPower - spentPowerOfHero);
									content = content + "Hero defeated " + enemy.getName() + " with " + hero.getPower()
											+ " HP remaining\n";
								} else {
									Integer enemyPower = enemy.getPower();
									Integer spentPowerOfEnemy =  ((hero.getPower() / enemy.getAttackPower())
											* hero.getAttackPower());
									enemy.setPower(enemyPower - spentPowerOfEnemy );
									hero.setPower(new Integer(0));
									content = content + enemy.getName() + " defeated Hero with " + enemy.getPower() + " HP remaining\n";
									content = content + "Hero is Dead!! Last seen at position " + enemy.getPosition()
											+ "!!";
									break;
								}
							}else {
								break;
							}
						} else {
							continue;
						}
					}

				}
				if (hero.getPower().intValue() > 0) {
					content = content + "Hero Survived!";
				}
			}
			System.out.println(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return content;
	}

}
