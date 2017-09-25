package com.samples;

import org.utils.timer.TimeTaken;
import org.utils.timer.TimeTakenHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class LInSearch {

	public static void main(String[] args) {
		final int size = 100000;
		final int[] l = new int[size];
		final int f = 428;
		
		TimeTakenHelper.calculateTime("prepareArray", new TimeTaken() {

			@Override
			public void calculateTimeTaken() {
				prepareArray(size, l);
			}
		});

		TimeTakenHelper.calculateTime("shuffle", new TimeTaken() {

			@Override
			public void calculateTimeTaken() {
				List al = new LinkedList();
				for(int i = 0; i < l.length; i++) {
					al.add(l[i]);
				}
				Collections.shuffle(al);
				for(int i = 0; i < al.size(); i++) {
					l[i] = Integer.parseInt(al.get(i).toString());
				}
			}
		});


		TimeTakenHelper.calculateTime("highPerformance time", new TimeTaken() {

			@Override
			public void calculateTimeTaken() {
				highPerformance(l, f);
			}
		});

		TimeTakenHelper.calculateTime("linear time", new TimeTaken() {

			@Override
			public void calculateTimeTaken() {
				linear(l, f);
			}
		});
		
		TimeTakenHelper.calculateTime("sort time", new TimeTaken() {

			@Override
			public void calculateTimeTaken() {
				Arrays.sort(l);
			}
		});

		TimeTakenHelper.calculateTime("sorted bin time", new TimeTaken() {

			@Override
			public void calculateTimeTaken() {
				System.out.println("sortAndBin : " + Arrays.binarySearch(l, f));
			}
		});

	}
	
	private static void prepareArray(int size, int[] l) {
		for(int i = 0; i < size; i++) {
			l[i] = i;
		}
	}

	private static void linear(int[] l, int f) {
		for(int i = 0; i < l.length; i++) {
			if(l[i] == f) {
				System.out.println("linear : " + i);
				break;
			}
		}
	}

	private static void highPerformance(int[] l, int f) {
		int s = 0, mids = l.length/2, mide = l.length/2, e = l.length-1;
		for(; s < l.length; s++, mids--, mide++, e--) {
			if(l[s] == f) {
				System.out.println("highPerformance i : " + s);
				break;
			}
			else if(l[e] == f) {
				System.out.println("highPerformance k : " + e);
				break;
			}
			else if(l[mids] == f) {
				System.out.println("highPerformance mids : " + mids);
				break;
			}
			else if(l[mide] == f) {
				System.out.println("highPerformance mide : " + mide);
				break;
			}
		}
	}

}
