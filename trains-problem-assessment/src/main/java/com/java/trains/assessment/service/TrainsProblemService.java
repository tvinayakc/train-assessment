package com.java.trains.assessment.service;

public interface TrainsProblemService {

	String caliculateDistance(String route);

	String countRoutesWithMaxStops(String start, String end, String maxStops);
	
	String countRoutesWithExactStops(String start, String end, String exactStops);

	String shortestRoute(String start, String end);
	
	String countRoutesWithDistanceLimit(String start, String end, String maxDistance);
}
