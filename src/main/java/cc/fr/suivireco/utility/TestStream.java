package com.exane.anafi.neuron.security;

import java.nio.file.attribute.GroupPrincipal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java Streams
 * @author Boughani_R
 *
 */
public class TestStream {
	
	public static void main(String[] args) {
		
		List<TestPersonne> listP = Arrays.asList(
				new TestPersonne(1.80, 70, "A", "Nicolas", TestCouleur.BLEU),
				new TestPersonne(1.56, 50, "B", "Nicole", TestCouleur.VERRON),
				new TestPersonne(1.75, 65, "C", "Germain", TestCouleur.VERT),
				new TestPersonne(1.68, 50, "D", "Michel", TestCouleur.ROUGE),
				new TestPersonne(1.96, 65, "E", "Cyrille", TestCouleur.BLEU),
				new TestPersonne(2.10, 120, "F", "Denis", TestCouleur.ROUGE),
				new TestPersonne(1.90, 90, "G", "Olivier", TestCouleur.VERRON)
		);		
		
		Stream<TestPersonne> sp = listP.stream();
		sp.forEach(System.out::println);
		
		System.out.println("\nAprès le filtre");
		sp = listP.stream();
		sp.	filter(x -> x.getPoids() > 50)
			.forEach(System.out::println);
		
		System.out.println("\nAprès le filtre et le map");
		sp = listP.stream();
		sp.	filter(x -> x.getPoids() > 50)
			.map(x -> x.getPoids())
			.forEach(System.out::println);
		
		System.out.println("\nAprès le filtre et le map et reduce");
		sp = listP.stream();

		Double sum = sp	.filter(x -> x.getPoids() > 50)
						.map(x -> x.getPoids())
						.reduce(0.0d, (x,y) -> x+y);
		System.out.println(sum);
		
		
		System.out.println("\nAprès le filtre et le map et reduce");
		sp = listP.stream();

		Optional<Double> sum3 = sp	.filter(x -> x.getPoids() > 250)
									.map(x -> x.getPoids())
									.reduce((x,y) -> x+y);
		if(sum3.isPresent())
			System.out.println(sum3.get());
		else
			System.out.println("Aucun aggrégat de poids...");
		
		
		System.out.println("\nAprès le filtre et le map et reduce");
		sp = listP.stream();

		Optional<Double> sums = sp	.filter(x -> x.getPoids() > 250)
									.map(x -> x.getPoids())
									.reduce((x,y) -> x+y);
		System.out.println(sums.orElse(0.0));
		
		sp = listP.stream();

		System.out.println("\nLe count");
		long count = sp	.filter(x -> x.getPoids() > 50)
						.map(x -> x.getPoids())
						.count();

		System.out.println("Nombre d'éléments : " + count);
		
		
		System.out.println("\nCollector list");
		sp = listP.stream();

		List<Double> ld = sp.filter(x -> x.getPoids() > 50)
							.map(x -> x.getPoids())
							.collect(Collectors.toList());
		System.out.println(ld);
		
		List<String> listF = Arrays.asList(
						"apple", 
						"apple", 
						"banana",
		                "apple", 
		                "orange", 
		                "banana", 
		                "papaya"
                );
		
		Stream<String> sf = listF.stream();
		
		
		Map<String, Long> result = sf.collect(
				Collectors.groupingBy(
						Function.identity(), Collectors.counting()
			)
		);
		
		Map<String, List<String>> groupedByName =	listF.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.toList()));
	
		System.out.println(groupedByName);
	
		System.out.println("\n"+result);
		result.forEach((k, v) -> {
	        System.out.printf("%s : %d%n", k, v);
	    });
		
		listP.stream().sorted(Comparator.comparing(TestPersonne::getNom).reversed()).forEach(System.out::println);
	}
}
