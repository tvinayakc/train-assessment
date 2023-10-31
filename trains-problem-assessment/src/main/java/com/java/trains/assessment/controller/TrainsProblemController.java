package com.java.trains.assessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.trains.assessment.service.TrainsProblemService;

@RestController
@RequestMapping("/trains")
public class TrainsProblemController {
	
	@Autowired
	private TrainsProblemService trainsProblemService;
	
	@GetMapping("/caliculate/distance")
	public ResponseEntity<String> caliculateDistance(@RequestParam String route){
		return new ResponseEntity<>(trainsProblemService.caliculateDistance(route.toUpperCase()), HttpStatus.OK);
	}
	
	@GetMapping("/count/route/with/max/stops")
	public ResponseEntity<String> countRoutesWithMaxStops(@RequestParam String start, @RequestParam String end, @RequestParam String maxStops){
		return new ResponseEntity<>(trainsProblemService.countRoutesWithMaxStops(start.toUpperCase(), end.toUpperCase(), maxStops), HttpStatus.OK);
	}
	
	@GetMapping("/count/route/with/exact/stops")
	public ResponseEntity<String> countRoutesWithExactStops(@RequestParam String start, @RequestParam String end, @RequestParam String exactStops){
		return new ResponseEntity<>(trainsProblemService.countRoutesWithExactStops(start.toUpperCase(), end.toUpperCase(), exactStops), HttpStatus.OK);
	}
	
	@GetMapping("/shortest/route")
	public ResponseEntity<String> shortestRoute(@RequestParam String start, @RequestParam String end){
		return new ResponseEntity<>(trainsProblemService.shortestRoute(start.toUpperCase(), end.toUpperCase()), HttpStatus.OK);
	}
	
	@GetMapping("/route/distance/limit")
	public ResponseEntity<String> countRoutesWithDistanceLimit(@RequestParam String start, @RequestParam String end, @RequestParam String maxDistance){
		return new ResponseEntity<>(trainsProblemService.countRoutesWithDistanceLimit(start.toUpperCase(), end.toUpperCase(), maxDistance), HttpStatus.OK);
	}
}
