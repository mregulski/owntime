/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zespolowe.server.PathFindingClasses;

import com.zespolowe.server.dataFormats.Path;
import com.zespolowe.server.dataFormats.Request;
import com.zespolowe.server.interfaces.RequestProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cirben
 */
public class ConsoleRequestProvider implements RequestProvider {

	@Override
	public Request getRequest() {
		while (true) {
			try {
				int idA;
				int idB;
				LocalDateTime start;
				System.out.println("Awaiting request from console");
				System.out.println("Gimme request type: 1-(id,id), 2-(Coords, id), 3-(id, coords), 4-(coord, coords)");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				int type = Integer.parseInt(br.readLine());
				if (type == 1) {
					System.out.println("gimme first stop id");
					idA = Integer.parseInt(br.readLine());
					System.out.println("gimme Second stop id");
					idB = Integer.parseInt(br.readLine());
					System.out.println("Give LocalDateTime String in format:\nYYYY MM DD HH MM SS");
					String date = br.readLine();
					String parts[] = date.split(" ");
					int nums[] = new int[6];
					for(int q = 0; q < 6; q++){
						nums[q] = Integer.parseInt(parts[q]);
					}
					start = LocalDateTime.of(nums[0],nums[1],nums[2],nums[3],nums[4],nums[5]);
					System.out.println("Got "+idA+" " + idB + " " + start);
					return new Request(1,idA,idB,start);
				} else {
					System.out.println("not supported yet :D");
					continue;
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				continue;
			}
		}
		//return null;
	}

	@Override
	public boolean shouldStop() {
		return false;
	}

	@Override
	public void addPath(Path path) {
		//path.print();
	}

}
