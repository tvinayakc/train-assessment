package com.java.trains.assessment.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.java.trains.assessment.service.TrainsProblemService;

@Service
public class TrainsProblemServiceImpl implements TrainsProblemService {

	private static Map<String, Map<String, Integer>> graph = new HashMap<>();
	private static final String NO_ROUTE = "NO ROUTE FOUND";
	private Integer count = 0;

	static {
		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/files/input.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(", ");
				for (String part : parts) {
					addRoute(part);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param route
	 */
	private static void addRoute(String route) {
		String start = route.substring(0, 1);
		String end = route.substring(1, 2);
		int distance = Integer.parseInt(route.substring(2));
		graph.putIfAbsent(start, new HashMap<>());
		graph.get(start).put(end, distance);
	}

	/**
	 * function to find the distance of a route
	 * 
	 * @param route
	 * @return distance
	 */
	@Override
	public String caliculateDistance(String route) {
		String[] towns = route.split("-");
		int distance = 0;
		for (int i = 0; i < towns.length - 1; i++) {
			if (!graph.containsKey(towns[i]) || !graph.get(towns[i]).containsKey(towns[i + 1])) {
				return NO_ROUTE;
			}
			distance += graph.get(towns[i]).get(towns[i + 1]);
		}

		return String.valueOf(distance);

	}

	/**
	 * function to count trips with a maximum number of stops
	 * 
	 * @param start
	 * @param end
	 * @param maxStops
	 * @return stops count
	 */
	@Override
	public String countRoutesWithMaxStops(String start, String end, String maxStops) {
		return String.valueOf(countRoutesWithMaxStopsHelper(start, end, Integer.parseInt(maxStops), start, 0));
	}

	/**
	 * @param current
	 * @param end
	 * @param maxStops
	 * @param route
	 * @param stops
	 * @return stops count
	 */
	private int countRoutesWithMaxStopsHelper(String current, String end, Integer maxStops, String route,
			Integer stops) {
		try {
			if (stops > maxStops) {
				return 0;
			}
			if (current.equals(end) && stops > 0) {
				return 1;
			}
			int count = 0;
			if (graph.containsKey(current)) {
				for (String neighbor : graph.get(current).keySet()) {
					count += countRoutesWithMaxStopsHelper(neighbor, end, maxStops, route + neighbor, stops + 1);
				}
			}
			return count;
		} finally {
			count = 0;
		}
	}

	/**
	 * function to count trips with an exact number of stops
	 * 
	 * @param start
	 * @param end
	 * @param exactStops
	 * @return no of stops
	 */
	@Override
	public String countRoutesWithExactStops(String start, String end, String exactStops) {
		return String.valueOf(countRoutesWithExactStopsHelper(start, end, Integer.parseInt(exactStops), start, 0));
	}

	/**
	 * @param current
	 * @param end
	 * @param exactStops
	 * @param route
	 * @param stops
	 * @return no of stops
	 */
	private int countRoutesWithExactStopsHelper(String current, String end, Integer exactStops, String route,
			Integer stops) {
		try {
			if (stops.equals(exactStops) && current.equals(end)) {
				return 1;
			}
			if (stops >= exactStops) {
				return 0;
			}
			int count = 0;
			if (graph.containsKey(current)) {
				for (String neighbor : graph.get(current).keySet()) {
					count += countRoutesWithExactStopsHelper(neighbor, end, exactStops, route + neighbor, stops + 1);
				}
			}
			return count;
		} finally {
			count = 0;
		}
	}

	/**
	 * function to find the shortest distance
	 * 
	 * @param start
	 * @param end
	 * @return shortest route
	 */
	@Override
	public String shortestRoute(String start, String end) {
		Map<String, Integer> distances = new HashMap<>();
		Set<String> visited = new HashSet<>();

		for (String town : graph.keySet()) {
			distances.put(town, Integer.MAX_VALUE);
		}
		distances.put(start, 0);

		while (true) {
			String current = findMinDistanceVertex(distances, visited);
			if (current == null) {
				break;
			}

			visited.add(current);

			if (current.equals(end)) {
				return String.valueOf(distances.get(current));
			}

			for (String neighbor : graph.get(current).keySet()) {
				int distanceToNeighbor = distances.get(current) + graph.get(current).get(neighbor);
				if (distanceToNeighbor < distances.get(neighbor)) {
					distances.put(neighbor, distanceToNeighbor);
				}
			}
		}

		return NO_ROUTE;
	}

	/**
	 * @param distances
	 * @param visited
	 * @return shortest route
	 */
	private String findMinDistanceVertex(Map<String, Integer> distances, Set<String> visited) {
		int minDistance = Integer.MAX_VALUE;
		String minVertex = null;

		for (Entry<String, Integer> town : distances.entrySet()) {
			if (!visited.contains(town.getKey()) && distances.get(town.getKey()) < minDistance) {
				minDistance = distances.get(town.getKey());
				minVertex = town.getKey();
			}
		}

		return minVertex;
	}

	/**
	 * function to count routes with a distance less than a given value
	 * 
	 * @param start
	 * @param end
	 * @param maxDistance
	 * @return no of different routes
	 */
	@Override
	public String countRoutesWithDistanceLimit(String start, String end, String maxDistance) {
		return String.valueOf(countRoutesWithDistanceLimitHelper(start, end, 0, Integer.parseInt(maxDistance)));
	}

	/**
	 * @param currentTown
	 * @param endTown
	 * @param currentDistance
	 * @param maxDistance
	 * @return no of different routes
	 */
	private int countRoutesWithDistanceLimitHelper(String currentTown, String endTown, Integer currentDistance,
			Integer maxDistance) {
		try {
			if (currentDistance >= maxDistance) {
				return 0;
			}
			if (currentTown.equals(endTown) && currentDistance > 0) {
				return 1;
			}
			if (graph.containsKey(currentTown)) {
				for (String neighbor : graph.get(currentTown).keySet()) {
					Integer newDistance = currentDistance + graph.get(currentTown).get(neighbor);
					count += countRoutesWithDistanceLimitHelper(neighbor, endTown, currentDistance + newDistance,
							maxDistance);
				}
			}
			return count;
		} finally {
			count = 0;
		}
	}

}
