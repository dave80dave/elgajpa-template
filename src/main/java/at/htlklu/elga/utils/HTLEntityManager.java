package at.htlklu.elga.utils;

import org.hibernate.engine.spi.EntityEntry;
import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class HTLEntityManager
{
	//region static Variables and Methods
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager getLastEntityManager;

	public static EntityManager createEntityManager()
	{
		HTLEntityManager htlEntityManager = new HTLEntityManager();
		HTLEntityManager.getLastEntityManager = htlEntityManager.entityManager;
		return htlEntityManager.entityManager;
	}
	//endregion


	//region Properties
	private EntityManager entityManager;
	//endregion


	//region Constructors
	private HTLEntityManager()
	{
		if (entityManagerFactory == null)
		{
			HTLEntityManager.entityManagerFactory = Persistence.createEntityManagerFactory("elga");
		}
		entityManager = entityManagerFactory.createEntityManager();
	}
	//endregion


	//region Methods
	private static Map.Entry<Object,org.hibernate.engine.spi.EntityEntry>[] getPersistenceContext(EntityManager entityManager)
	{
		org.hibernate.engine.spi.SessionImplementor session = entityManager.unwrap(org.hibernate.engine.spi.SessionImplementor.class);
		org.hibernate.engine.spi.PersistenceContext pc = session.getPersistenceContext();
		Map.Entry<Object,org.hibernate.engine.spi.EntityEntry>[] entityEntries = pc.reentrantSafeEntityEntries();
		return entityEntries;
	}

	public static String getPersistenceContextContent()
	{
		return HTLEntityManager.getPersistenceContextContent(HTLEntityManager.getLastEntityManager);
	}

	public static String getPersistenceContextContent(EntityManager entityManager)
	{
		String result = String.format("%s" ,"PersistenceContext:");
		Map.Entry<Object,org.hibernate.engine.spi.EntityEntry>[] entityEntries = HTLEntityManager.getPersistenceContext(entityManager);

		Map<String, String> entries = new HashMap<String, String>();
		for (Map.Entry<Object, EntityEntry> entityEntry : entityEntries)
		{
//			result += String.format("%n   %s: %s", entityEntry.getValue(), entityEntry.getKey());
//			result += String.format("%n   %s: %s %s", getClassName(entityEntry.getValue().toString()), entityEntry.getKey(), getJPAState(entityEntry.getValue().toString()));

			entries.put(entityEntry.getKey() + " " + getJPAState(entityEntry.getValue().toString()), getClassName(entityEntry.getValue().toString()));
		}

		Map<String, String> entriesGrouped = entries.entrySet()
												    .stream()
													.sorted(Map.Entry.<String,String>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
												    .collect(Collectors.groupingBy(item -> item.getValue(),
																				   Collectors.mapping(item -> item.getKey(), Collectors.joining(", "))));

		result += entriesGrouped.entrySet()
				                .stream()
							    .map(entry -> String.format("%n   %-15s : %s", entry.getKey(), entry.getValue()))
							    .sorted()
							    .collect(Collectors.joining(""));

		return result;
	}

	private static String getClassName(String value)
	{
		String result = value;
		result = result.substring(result.indexOf("model.") + 6);
		result = result.substring(0, result.indexOf("#"));
		return result;
	}
	private static String getJPAState(String value)
	{
		String result = value;
		result = result.substring(result.indexOf("("));
		result = result.replace("(", "[");
		result = result.replace(")", "]");
		return result;
	}
	//endregion


}
