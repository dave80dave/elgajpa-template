package at.htlklu.elga.utils;

import java.awt.*;
import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Christian Stingl
 * @version 1.20 <br>
 * <b> Änderungen </b>
 * <ul>
 *    <li> 1.1 (03.10.2019): Rückgabewert bei toString(LocalDateTime) </li>
 *    <li> 1.2 (06.10.2019): getFilePath, existsFile, getRandomIntArray, getRandomDoubleArray </li>
 *    <li> 1.3 (07.10.2019): getRandomIntArray (Hilfe), getRandomDoubleArray (Hilfe) </li>
 *    <li> 1.4 (13.10.2019): getRandomBoolean (+Hilfe), generell die Hilfe verbessert </li>
 *    <li> 1.5 (25.10.2019): isLocalDate (+Hilfe) </li>
 *    <li> 1.6 (08.11.2019): parseLocalDate (+Hilfe), parseLocalTime (+Hilfe) </li>
 *    <li> 1.7 (11.11.2019): getRandomValue (+Hilfe) </li>
 *    <li> 1.8 (17.11.2019): getFeverCurve, getGrades (+Hilfe) </li>
 *    <li> 1.9 (26.11.2019): getGrades (+Hilfe) </li>
 *    <li> 1.10 (03.12.2019): getPoints (+Hilfe) </li>
 *    <li> 1.11 (16.12.2019): startTime (+Hilfe), stopTime (+Hilfe) </li>
 *    <li> 1.12 (12.01.2020): getRandomSign </li>
 *    <li> 1.13 (21.01.2020): requireNonNullAndNonEmpty (+Hilfe) </li>
 *    <li> 1.14 (22.01.2020): neue Methode requireNonNullAndNonEmpty (+Hilfe) </li>
 *    <li> 1.15 (31.03.2020): isInt, parseInt - Errorhandling verbessert </li>
 *    <li> 1.16 (07.05.2020): getRandomLocalDate </li>
 *    <li> 1.17 (22.06.2020): getFilePath </li>
 *    <li> 1.18 (28.08.2020): toString(char c) </li>
 *    <li> 1.19 (22.01.2021): getBankNotes(int length), getCoins(int length) </li>
 *    <li> 1.20 (05.03.2021): getFever* (für Charts) </li>
 * </ul>
 */
public class HTLUtilities
{

	//region static properties
	private static final Random rNG = new Random();

	private static final List<Integer> COINS = Stream.of(1, 2, 5, 10, 20, 50, 100, 200).collect(Collectors.toList());
	private static final List<Integer> BANKNOTES = Stream.of(5, 10, 20, 50, 100, 200, 500).collect(Collectors.toList());
	//endregion


	//region Constructors
	// der private Konstruktor verhindert, dass die Klasse instanziiert werden kann
	private HTLUtilities()
	{
	}
	//endregion


	//region Integrity constraints
	/**
	 * Überprüft, ob der String {@code text} {@code null} oder leer (empty) ist
	 * @param text der String
	 * @return Gibt {@code true} zurück, wenn der String {@code text} {@code null} oder leer ist, sonst {@code false}
	 */
	public static boolean isNullOrEmpty(String text)
	{
		return ((text == null) || (text.isEmpty()));
	}

	/**
	 * Überprüft, ob {@code s} nicht null und nicht leer ist.
	 * @return {@code s}, wenn {@code s} nicht null und nicht leer ist
	 */
	public static String requireNonNullAndNonEmpty(String s)
	{
		return HTLUtilities.requireNonNullAndNonEmpty(s, "String");
	}

	/**
	 * Überprüft, ob {@code property} nicht null und nicht leer (zumindest 1 Buchstaben) ist. <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> this.surname = HTLUtilities.requireNonNullAndNonEmpty(surname, "surname"); </li>
	 *    <li> this.className = HTLUtilities.requireNonNullAndNonEmpty(className, "className"); </li>
	 * </ul>
	 * @param property der String, der überprüft wird
	 * @param propertyName die Bezeichnung des Strings {@code property}, Beispiel: property="Joe", propertyName="firstname". Dieser wird für die mögliche Fehlermeldung benötigt!
	 * @return {@code property}, wenn {@code property} nicht null und nicht leer ist, sonst wird ein Fehler geworfen
	 */
	public static String requireNonNullAndNonEmpty(String property,
												   String propertyName)
	{
		return HTLUtilities.requireNonNullAndNonEmpty(property, propertyName, 1);
	}


	/**
	 * Überprüft, ob {@code property} nicht null ist und zumindest {@code numberOfCharacters} Buchstaben hat. <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> this.surname = HTLUtilities.requireNonNullAndNonEmpty(surname, "surname", 2); </li>
	 *    <li> this.className = HTLUtilities.requireNonNullAndNonEmpty(className, "className", 4); </li>
	 * </ul>
	 * @param property der String, der überprüft wird
	 * @param propertyName die Bezeichnung des Strings {@code property}, Beispiel: property="Mustermann", propertyName="surname". Dieser wird für die mögliche Fehlermeldung benötigt!
	 * @param numberOfCharacters die Anzahl der Buchstaben, die der String {@code property} haben muss
	 * @return {@code property}, wenn {@code property} nicht null und nicht leer ist, sonst wird ein Fehler geworfen
	 */
	public static String requireNonNullAndNonEmpty(String property,
			   									   String propertyName,
			   									   int numberOfCharacters)
	{
		if (property == null)
		{
			throw new NullPointerException(propertyName + " is null");
		}
		else if (property.length() < numberOfCharacters)
		{
			if (numberOfCharacters == 1)
				throw new  IllegalArgumentException(String.format("%s must not be empty", propertyName));
			else
				throw new  IllegalArgumentException(String.format("%s must have at least %d characters", propertyName, numberOfCharacters));
		}
		return property;
	}


	/**
	 * Überprüft, ob {@code property} nicht null ist, zumindest {@code numberOfCharactersFrom} Buchstaben, aber maximal {@code numberOfCharactersFrom} Buchstaben hat. <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> this.className = HTLUtilities.requireNonNullAndNonEmpty(className, "className", 4, 4); </li>
	 *    <li> this.telephoneNumber = HTLUtilities.requireNonNullAndNonEmpty(telephoneNumber, "telephoneNumber", 8, 12); </li>
	 * </ul>
	 * @param property der String, der überprüft wird
	 * @param propertyName die Bezeichnung des Strings {@code property}, Beispiel: property="Mustermann", propertyName="surname". Dieser wird für die mögliche Fehlermeldung benötigt!
	 * @param numberOfCharactersFrom die Anzahl der Buchstaben, die der String {@code property} zumindest haben muss
	 * @param numberOfCharactersTo die Anzahl der Buchstaben, die der String {@code property} maximal haben darf
	 * @return {@code property}, wenn {@code property} nicht null und nicht leer ist, sonst wird ein Fehler geworfen
	 */
	public static String requireNonNullAndNonEmpty(String property,
			   									   String propertyName,
			   									   int numberOfCharactersFrom,
			   									   int numberOfCharactersTo)
	{
		if (property == null)
		{
			throw new NullPointerException(propertyName + " is null");
		}
		else if (!(numberOfCharactersFrom <= property.length() && property.length() <= numberOfCharactersTo))
		{
			if (numberOfCharactersFrom == 1)
				throw new  IllegalArgumentException(String.format("%s must not be empty", propertyName, numberOfCharactersFrom, numberOfCharactersTo));
			else if (numberOfCharactersFrom == numberOfCharactersTo)
				throw new  IllegalArgumentException(String.format("%s must habe exactly %d characters", propertyName, numberOfCharactersFrom, numberOfCharactersTo));
			else
				throw new  IllegalArgumentException(String.format("%s must have between %d and %d characters", propertyName, numberOfCharactersFrom, numberOfCharactersTo));
		}
		return property;
	}
	//endregion


	//region Random 1
	/**
	 * Gibt eine int-Zufallszahl zurück.
	 * @return die int-Zufallszahl
	 */
	public static int getRandom()
	{
		return rNG.nextInt();
	}

	/**
	 * Gibt eine int-Zufallszahl zurück, die kleiner als oder gleich {@code maxValue} ist
	 * @param maxValue der Endwert
	 * @return die int-Zufallszahl
	 */
	public static int getRandom(int maxValue)
	{
		return rNG.nextInt(maxValue) + 1;
	}

	/**
	 * Gibt eine int-Zufallszahl zurück, die zwischen {@code minValue} und {@code maxValue} (Grenzen inklusive) <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> Zufallszahl zwischen 10 und 20: {@code int element = HTLUtilities.getRandomArray(10, 20)}} </li>
	 *    <li> Zufallszahl zwischen -10 und 10: {@code int element = HTLUtilities.getRandomArray(-10, 10)}} </li>
	 * </ul>
	 * @param minValue der Startwert
	 * @param maxValue der Endwert
	 * @return die int-Zufallszahl
	 */
	public static int getRandom(int minValue, int maxValue)
	{
		if (minValue <= maxValue)
			return rNG.nextInt(maxValue - minValue + 1) + minValue;
		else
			throw new IllegalArgumentException(String.format("Der Startwert %d muss echt kleiner als der Endwert %d sein!", minValue, maxValue));
	}

	/**
	 * Gibt eine double-Zufallszahl zurück, die zwischen {@code minValue} und {@code maxValue} (Grenzen inklusive)<br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> Zufallszahl zwischen 10.0 und 20.0: {@code double element = HTLUtilities.getRandomArray(10.0, 20.0)}} </li>
	 *    <li> Zufallszahl zwischen -10.0 und 10.0: {@code double element = HTLUtilities.getRandomArray(-10.0, 10.0)}} </li>
	 *    <li> Zufallszahl zwischen 2.0 und 2.5: {@code double element = HTLUtilities.getRandomArray(2.0, 2.5)}} </li>
	 * </ul>
	 * @param minValue der Startwert
	 * @param maxValue der Endwert
	 * @return die double-Zufallszahl
	 */
	public static double getRandomDouble(double minValue, double maxValue)
	{
		if (minValue <= maxValue)
			return rNG.nextDouble() * (maxValue - minValue) + minValue;
		else
			throw new IllegalArgumentException(String.format("Der Startwert %f muss echt kleiner als der Endwert %f sein!", minValue, maxValue));
	}


	public static double getRandomDouble(double minValue, double maxValue, int fractionDigits)
	{
		return HTLUtilities.round(HTLUtilities.getRandomDouble(minValue, maxValue), fractionDigits);
	}

	private static double getRandomDoubleError(double minValue, double maxValue,
			   								   double percentError, double errorValue, int fractionDigits)
	{
		double result;

		if (HTLUtilities.getRandomBoolean(percentError))
			result = errorValue;
		else
			result = HTLUtilities.getRandomDouble(minValue, maxValue, fractionDigits);

		return result;
	}




	public static int getRandomBinary(int NumberOfBits)
	{
		int random = getRandom(0, NumberOfBits - 1);
		int number = 1<<random;
		return number;
	}


	public static int getRandomSign()
	{
		return HTLUtilities.getRandomSign(2);
	}

	public static int getRandomSign(int maxValue)
	{
		int sign = 1;
		if (HTLUtilities.getRandom(1, maxValue) == 1) sign = -1;
		return sign;
	}

	/**
	 * Rundet die double-Zahl {@code number} auf {@code digits} Nachkommastellen und gibt diese zurück
	 * @param number die double-Zahl
	 * @param digits die Anzahl der Nachkommastellen
	 * @return die gerundete Zahl
	 */
	public static double round(double number, int digits)
	{
		double power = Math.pow(10, digits);
		return Math.round(number * power) / power;
	}

	/**
	 * Gibt einen boolean-Zufallswerts zurück, wobei {@code true} oder {@code false} gleichverteilt sind.
	 * @return {@code true} oder {@code false}
 	 */
	public static boolean getRandomBoolean()
	{
		return (HTLUtilities.getRandomBoolean(0.5));
	}

	/**
	 * Gibt einen boolean-Zufallswerts zurück, wobei {@code true} mit {@code percent} und {@code false} mit {@code 1-percent} Wahrscheinlichkeit auftritt.
	 * @param percent mit welcher Wahrscheinlichkeit soll {@code true} zurückgegeben werden
	 * @return {@code true} oder {@code false}
	 */
	public static boolean getRandomBoolean(double percent)
	{
		if (percent > 1)
			percent /= 100;
		return (HTLUtilities.getRandomDouble(0, 1) <= percent);
	}

	/**
	 * Gibt ein int-Zufallsarray mit {@code length} Elementen zurück, die zwischen {@code minValue} und {@code maxValue} liegen <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> Zufallsarray mit 5 Elementen zwischen 10 und 20: {@code int[] data = HTLUtilities.getRandomArray(5, 10, 20);} </li>
	 *    <li> Zufallsarray mit 10 Elementen zwischen 20 und 30: {@code int[] data = HTLUtilities.getRandomArray(10, 20, 30);} </li>
	 * </ul>
	 * @param length die Anzahl der Elemente im Array
	 * @param minValue die Elemente müssen größer als oder gleich {@code minValue} sein
	 * @param maxValue die Elemente müssen kleiner als oder gleich {@code maxValue} sein
	 * @return das ein int-Zufallsarray mit {@code count} Elementen, die zwischen {@code minValue} und {@code maxValue} liegen
	 */
	public static int[] getRandomArray(int length, int minValue, int maxValue)
	{
		int[] data = new int[length];
		for (int i = 0; i < data.length; i++)
		{
			data[i] = HTLUtilities.getRandom(minValue, maxValue);
		}
		return data;
	}

	/**
	 * Gibt ein int-Zufallsarray mit {@code length} Elementen zurück, die zwischen {@code minValue} und {@code maxValue} liegen <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> Zufallsarray mit 5 Elementen zwischen 10 und 20: {@code int[] data = HTLUtilities.getRandomIntArray(5, 10, 20);} </li>
	 *    <li> Zufallsarray mit 10 Elementen zwischen 20 und 30: {@code int[] data = HTLUtilities.getRandomIntArray(10, 20, 30);} </li>
	 * </ul>
	 * @param length die Anzahl der Elemente im Array
	 * @param minValue die Elemente müssen größer als oder gleich {@code minValue} sein
	 * @param maxValue die Elemente müssen kleiner als oder gleich {@code maxValue} sein
	 * @return das int-Zufallsarray
	 */
	public static int[] getRandomIntArray(int length, int minValue, int maxValue)
	{
		return HTLUtilities.getRandomArray(length, minValue, maxValue);
	}

	/**
	 * Gibt ein int-Zufallsarray zurück, wobei die Anzahl der Elemente zwischen {@code minLength} und {@code maxLength} liegt und die Elemente zwischen {@code minValue} und {@code maxValue} liegen. <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> 10 bis 20 Zufallszahlen, die zwischen 30 und 40 liegen: {@code int[] data = HTLUtilities.getRandomArray(10, 20, 30, 40);} </li>
	 * </ul>
	 * @param minLength die Anzahl der Elemente müssen größer als oder gleich {@code minCount} sein
	 * @param maxLength die Anzahl der Elemente müssen kleiner als oder gleich {@code maxCount} sein
	 * @param minValue die Elemente müssen größer als oder gleich {@code minValue} sein
	 * @param maxValue die Elemente müssen kleiner als oder gleich {@code maxValue} sein
	 * @return das int-Zufallsarray
	 */
	public static int[] getRandomArray(int minLength, int maxLength, int minValue, int maxValue)
	{
		int length = HTLUtilities.getRandom(minLength, maxLength);
		int[] data = new int[length];
		for (int i = 0; i < data.length; i++)
		{
			data[i] = HTLUtilities.getRandom(minValue, maxValue);
		}
		return data;
	}


	/**
	 * Gibt ein int-Zufallsarray mit {@code length} Elementen zurück, wobei die Werte zwischen {@code minValue} und {@code maxValue} liegen und
	 * ca. {@code percentError} der Elemente den Wert {@code valueError} (für ungültig) annehmen. <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> 10 Zufallszahlen zwischen 20 und 30 mit 10% ungültigen Werten, die mit -1 kodiert werden: {@code int[] data = HTLUtilities.getRandomArray(10, 20, 30, 0.1, -1);} </li>
	 *    <li> 20 Zufallszahlen zwischen 1 und 10 mit 5% ungültigen Werten, die mit 0 kodiert werden: {@code int[] data = HTLUtilities.getRandomArray(20, 1, 10, 0.05, 0);} </li>
	 * </ul>
	 * @param length die Anzahl der Elemente im Array
	 * @param minValue die Elemente müssen größer als oder gleich {@code minValue} sein
	 * @param maxValue die Elemente müssen kleiner als oder gleich {@code maxValue} sein
	 * @param percentError der Prozentsatz der Elemente im Array, die ungültig sind
	 * @param valueError ungültige Werte werden mit {@code errorValue} kodiert
	 * @return das int-Zufallsarray
	 */
	private static int[] getRandomArray(int length, int minValue, int maxValue,
										double percentError, int valueError)
	{
		int[] data = HTLUtilities.getRandomArray(length, minValue, maxValue);
		for (int i = 0; i < data.length; i++)
		{
			if (HTLUtilities.getRandomBoolean(percentError)) data[i] = valueError;
		}
		return data;
	}


	public static Color getRandomColor()
	{
		return new Color(HTLUtilities.getRandom(256),
						 HTLUtilities.getRandom(256),
						 HTLUtilities.getRandom(256));
	}

	public static String getRandomColorName()
	{
		return (String)HTLUtilities.getRandomValue("Blau", "Gelb", "Grün", "Orange", "Rot", "Schwarz", "Weiß");
	}

	//endregion


	//region Points
	/**
	 * Gibt ein Array von Zufallspunkten (als {@code int-Array}) mit {@code length} Elementen zurück, wobei die Werte zufällig ermittelt werden.
	 * @param length die Anzahl der Elemente im Array
	 * @return die Zufallspunkte
	 */
	public static int[] getPoints(int length)
	{
		return HTLUtilities.getPoints(length, 0);
	}

	/**
	 * Gibt ein Array von Zufallspunkten (als {@code int-Array}) mit {@code length} Elementen zurück, wobei die Werte zufällig ermittelt werden und
	 * ca. {@code percentError} der Elemente den Wert -1 (für ungültig) annehmen.
	 * @param length die Anzahl der Elemente im Array
	 * @return die Zufallspunkte
	 */
	public static int[] getPoints(int length, double percentError)
	{
		return HTLUtilities.getPoints(length, percentError, -1);
	}

	public static int[] getPoints(int length, double percentError, int errorValue)
	{
		int minValue = HTLUtilities.getRandom(0, 10);
		int maxValue = minValue + HTLUtilities.getRandom(1, 10);

		return HTLUtilities.getRandomArray(length, minValue, maxValue, percentError, errorValue);
	}
	//endregion


	//region Grades
	/**
	 * Gibt eine zufällige Schulnote (1 bis 5) zurück
	 * @return die Schulnote
	 */
	public static int getGrade()
	{
		return HTLUtilities.getGrade(0);
	}

	/**
	 * Gibt eine zufällige Schulnote (1 bis 5) zurück, wobei mit Wahrscheinlichkeit von ca. {@code percentError} die Schulnote den Wert 0 (für ungültig) annimmt. <br>
	 * @param percentError die Wahrscheinlichkeit für einen fehlerhaften Wert
	 * @return die Schulnote oder ein ungültiger Wert
	 */
	public static int getGrade(double percentError)
	{
		int grade;

		if (HTLUtilities.getRandomBoolean(percentError))
			grade = 0;
		else
			grade = HTLUtilities.getRandom(1, 5);

		return grade;
	}

	/**
	 * Gibt ein Array von Zufallsnoten (als {@code int-Array}) mit {@code length} Elementen zurück, wobei die Werte zwischen 0 und 5 liegen.
	 * @param length die Anzahl der Elemente im Array
	 * @return die Schulnote
	 */
	public static int[] getGrades(int length)
	{
		return HTLUtilities.getGrades(length, 0);
	}


	/**
	 * Gibt ein Array von Zufallsnoten (als {@code int-Array}) mit {@code length} Elementen zurück, wobei die Werte zwischen 0 und 5 liegen und
	 * ca. {@code percentError} der Elemente den Wert 0 (für ungültig) annehmen. <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> 10 Zufallsnoten mit 20% ungültigen Werten: {@code int[] grades = HTLUtilities.getGrades(10, 0.2);} </li>
	 *    <li> 20 Zufallsnoten mit 10% ungültigen Werten: {@code int[] grades = HTLUtilities.getGrades(20, 0.1);} </li>
	 * </ul>
	 * @param length die Anzahl der Elemente im Array
	 * @param percentError der Prozentsatz der Elemente im Array, die ungültig sind, d.h. den Wert 0 haben
	 * @return die Zufallsnoten
	 */
	public static int[] getGrades(int length, double percentError)
	{
		return HTLUtilities.getRandomArray(length, 1, 5, percentError, 0);
	}
	//endregion


	//region Banknote
	/**
	 * Gibt eine zufällige Eurobanknote (5, 10, 20, 50, 100, 200, 500) zurück
	 * @return der Geldbetrag der zufällige in Euro
	 */
	public static int getBanknote()
	{
		return HTLUtilities.getBanknote(0);
	}

	/**
	 * Gibt eine zufällige Eurobanknote (5, 10, 20, 50, 100, 200, 500) zurück, wobei mit Wahrscheinlichkeit von
	 * ca. {@code percentError} die Eurobanknote den Wert 0 (für ungültig) annimmt. <br>
	 * @param percentError die Wahrscheinlichkeit für einen ungültigen Wert
	 * @return der Geldbetrag der zufällige in Euro oder ein ungültiger Wert
	 */
	public static int getBanknote(double percentError)
	{
		return HTLUtilities.getBanknote(percentError, -1);
	}

	public static int getBanknote(double percentError, int errorValue)
	{
		return HTLUtilities.getRandomListElement(BANKNOTES, percentError, errorValue);
	}

	/**
	 * Gibt ein Array mit zufälligen Eurobanknoten (als {@code int-Array}) mit {@code length} Elementen zurück.
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> 10 Banknoten: {@code int[] bankNotes = HTLUtilities.getBanknotes(10);} </li>
	 * </ul>
	 * @param length die Anzahl der Elemente im Array
	 * @return die Geldbeträge der Eurobanknoten
	 */
	public static int[] getBanknotes(int length)
	{
		return HTLUtilities.getBanknotes(length, 0);
	}

	/**
	 * Gibt ein Array von zufälligen Banknoten (als {@code int-Array}) mit {@code length} Elementen zurück und
	 * ca. {@code percentError} der Elemente den Wert 0 (für ungültig) annehmen. <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> 10 Banknoten ohne ungültige Werte: {@code int[] bankNotes = HTLUtilities.getBanknotes(10, 0);} </li>
	 *    <li> 20 Banknoten mit 10% ungültigen Werten: {@code int[] bankNotes = HTLUtilities.getBanknotes(20, 0.1);} </li>
	 *    <li> 30 Banknoten mit 20% ungültigen Werten: {@code int[] bankNotes = HTLUtilities.getBanknotes(30, 0.2);} </li>
	 * </ul>
	 * @param length die Anzahl der Elemente im Array
	 * @param percentError der Prozentsatz der Elemente im Array, die ungültig sind, d.h. den Wert 0 haben
	 * @return die Geldbeträge der Banknoten
	 */
	public static int[] getBanknotes(int length, double percentError)
	{
		return HTLUtilities.getBanknotes(length, percentError, -1);
	}

	public static int[] getBanknotes(int length, double percentError, int errorValue)
	{
		return HTLUtilities.getRandomList(length, BANKNOTES, percentError, errorValue);
	}
	//endregion


	//region Coins
	/**
	 * Gibt eine zufällige Euromünze (1, 2, 5, 10, 20, 50, 100, 200) zurück
	 * @return der Geldbetrag der Euromünze in Cent
	 */
	public static int getCoin()
	{
		return HTLUtilities.getCoin(0);
	}

	/**
	 * Gibt eine zufällige Euromünze (1, 2, 5, 10, 20, 50, 100, 200) zurück, wobei mit Wahrscheinlichkeit von
	 * ca. {@code percentError} die Euromünze den Wert 0 (für ungültig) annimmt. <br>
	 * @param percentError die Wahrscheinlichkeit für einen ungültigen Wert
	 * @return der Geldbetrag der Euromünze in Cent oder ein ungültiger Wert
	 */
	public static int getCoin(double percentError)
	{
		return HTLUtilities.getCoin(percentError, -1);
	}

	public static int getCoin(double percentError, int errorValue)
	{
		return HTLUtilities.getRandomListElement(COINS, percentError, errorValue);
	}


	/**
	 * Gibt ein Array mit zufälligen Euromünzen (als {@code int-Array}) mit {@code length} Elementen zurück
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> 10 Münzen: {@code int[] coins = HTLUtilities.getCoins(10);} </li>
	 * </ul>
	 * @param length die Anzahl der Elemente im Array
	 * @return die Geldbeträge der Euromünzen in Cent
	 */
	public static int[] getCoins(int length)
	{
		return HTLUtilities.getCoins(length, 0);
	}


	/**
	 * Gibt ein Array von zufälligen Euromünzen (als {@code int-Array}) mit {@code length} Elementen zurück und
	 * ca. {@code percentError} der Elemente den Wert 0 (für ungültig) annehmen. <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> 10 Münzen ohne ungültige Werte: {@code int[] coins = HTLUtilities.getCoins(10, 0);} </li>
	 *    <li> 20 Münzen mit 10% ungültigen Werten: {@code int[] coins = HTLUtilities.getCoins(20, 0.1);} </li>
	 *    <li> 30 Münzen mit 20% ungültigen Werten: {@code int[] coins = HTLUtilities.getCoins(30, 0.2);} </li>
	 * </ul>
	 * @param length die Anzahl der Elemente im Array
	 * @param percentError der Prozentsatz der Elemente im Array, die ungültig sind, d.h. den Wert 0 haben
	 * @return die Geldbeträge der Euromünzen in Cent
	 */
	public static int[] getCoins(int length, double percentError)
	{
		return HTLUtilities.getCoins(length, percentError, -1);
	}

	public static int[] getCoins(int length, double percentError, int errorValue)
	{
		return HTLUtilities.getRandomList(length, COINS, percentError, errorValue);
	}
	//endregion


	//region Random 2
	/**
	 * Gibt ein Array von Zufallszahlen von den übergebenen Werten {@code ints} mit {@code length} Elementen zurück und
	 * ca. {@code percentError} der Elemente den Wert 0 (für ungültig) annehmen. <br>
	 * @param length die Anzahl der Elemente im Array
	 * @param percentError der Prozentsatz der Elemente im Array, die ungültig sind, d.h. den Wert 0 haben
	 * @param ints die Liste der übergebenen Werte
	 * @return das Array
	 */
	private static int[] getRandomList(int length, List<Integer> ints, double percentError, int errorValue)
	{
		int[] data = new int[length];

		for (int i = 0; i < data.length; i++)
		{
			if (HTLUtilities.getRandomBoolean(percentError))
				data[i] = 0;
			else
				data[i] = HTLUtilities.getRandomListElement(ints, percentError, errorValue);
		}

		return data;
	}

	/**
	 * Gibt einen zufälligen Übergabeparameter zurück
	 * @param ints die Liste der übergebenen Werte
	 * @return der Wert aus der Liste der Übergabeparameter
	 */
	private static int getRandomListElement(List<Integer> ints, double percentError, int errorValue)
	{
		int value;

		if (HTLUtilities.getRandomBoolean(percentError))
			value = errorValue;
		else
		{
			int randomPosition = HTLUtilities.getRandom(0, ints.size() - 1);
			value = ints.get(randomPosition);
		}

		return value;
	}


	/**
	 * Gibt ein Array von Zufallszahlen von den übergebenen Werten {@code ints} mit {@code length} Elementen zurück und
	 * ca. {@code percentError} der Elemente den Wert 0 (für ungültig) annehmen. <br>
	 * @param length die Anzahl der Elemente im Array
	 * @param percentError der Prozentsatz der Elemente im Array, die ungültig sind, d.h. den Wert 0 haben
	 * @param ints die Liste der übergebenen Werte
	 * @return das Array
	 */
	private static int[] getRandomList(int length, double percentError, Integer ... ints)
	{
		int[] data = new int[length];

		for (int i = 0; i < data.length; i++)
		{
			if (HTLUtilities.getRandomBoolean(percentError))
				data[i] = 0;
			else
				data[i] = HTLUtilities.getRandomListElement(ints);
		}

		return data;
	}


	/**
	 * Gibt einen zufälligen Übergabeparameter zurück
	 * @param ints die Liste der übergebenen Werte
	 * @return der Wert aus der Liste der Übergabeparameter
	 */
	private static int getRandomListElement(Integer ... ints)
	{
		int randomPosition = HTLUtilities.getRandom(0, ints.length - 1);
		return (int)ints[randomPosition];
	}




	/**
	 * Gibt ein double-Zufallsarray mit {@code length} Elementen zurück, die zwischen {@code minValue} und {@code maxValue} liegen und maximal zwei Nachkommastellen aufweisen <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> Zufallsarray mit 5 Elementen zwischen 2.0 und 5.0: {@code double[] data = HTLUtilities.getRandomArray(5, 2.0, 5.0);} </li>
	 *    <li> Zufallsarray mit 10 Elementen zwischen 2.5 und 3.9: {@code double[] data = HTLUtilities.getRandomArray(10, 2.5, 3.9);} </li>
	 * </ul>
	 * @param length die Anzahl der Elemente im Array
	 * @param minValue die Elemente müssen größer als oder gleich {@code minValue} sein
	 * @param maxValue die Elemente müssen kleiner als oder gleich {@code maxValue} sein
	 * @return das double-Zufallsarray
	 */
	public static double[] getRandomDoubleArray(int length, double minValue, double maxValue)
	{
		return HTLUtilities.getRandomDoubleArray(length, minValue, maxValue, 2);
	}

	/**
	 * Gibt ein double-Zufallsarray mit {@code length} Elementen zurück, die zwischen {@code minValue} und {@code maxValue} liegen und maximal {@code fractionDigits} Nachkommastellen aufweisen <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> Zufallsarray mit 5 Elementen zwischen 2.0 und 5.0 mit max. 2 Nachommastellen: {@code double[] data = HTLUtilities.getRandomArray(5, 2.0, 5.0, 2);} </li>
	 *    <li> Zufallsarray mit 10 Elementen zwischen 2.5 und 3.9 mit max. 2 Nachommastellen: {@code double[] data = HTLUtilities.getRandomArray(10, 2.5, 3.9, 3);} </li>
	 * </ul>
	 * @param length die Anzahl der Elemente im Array
	 * @param minValue die Elemente müssen größer als oder gleich {@code minValue} sein, d.h. Elemente {@code >= minimum}
	 * @param maxValue die Elemente müssen kleiner als oder gleich {@code maxValue} sein, d.h. Elemente {@code <= maximum}
	 * @param fractionDigits die Elemente haben maximal {@code fractionDigits} Nachkommastellen
	 * @return das double-Zufallsarray
	 */
	public static double[] getRandomDoubleArray(int length, double minValue, double maxValue, int fractionDigits)
	{
		double[] data = new double[length];
		for (int i = 0; i < data.length; i++)
		{
			data[i] = HTLUtilities.round(HTLUtilities.getRandomDouble(minValue, maxValue), fractionDigits);
		}
		return data;
	}


	/**
	 * Gibt ein double-Zufallsarray mit {@code length} Elementen zurück, die zwischen {@code minValue} und {@code maxValue} liegen und maximal 2 Nachkommastellen aufweisen.
	 * Weiters nehmen die Elemente des Arrays mit einer Wahrscheinlichkeit von {@code percentError} den Wert {@code errorValue} an.<br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> Zufallsarray mit 5 Elementen zwischen 2.0 und 5.0 mit max. 2 Nachommastellen: {@code double[] data = HTLUtilities.getRandomArray(5, 2.0, 5.0, 2);} </li>
	 *    <li> Zufallsarray mit 10 Elementen zwischen 2.5 und 3.9 mit max. 2 Nachommastellen: {@code double[] data = HTLUtilities.getRandomArray(10, 2.5, 3.9, 3);} </li>
	 * </ul>
	 * @param length die Anzahl der Elemente im Array
	 * @param minValue die Elemente müssen größer als oder gleich {@code minValue} sein, d.h. Elemente {@code >= minimum}
	 * @param maxValue die Elemente müssen kleiner als oder gleich {@code maxValue} sein, d.h. Elemente {@code <= maximum}
	 * @param percentError die Wahrscheinlichkeit für Datenfehler
	 * @param errorValue der Wert bei einem Datenfehler
	 * @return das double-Zufallsarray
	 */
	public static double[] getRandomDoubleArray(int length, double minValue, double maxValue,
												 double percentError, double errorValue, int fractionDigits)
	{
		double[] data = HTLUtilities.getRandomDoubleArray(length, minValue, maxValue, fractionDigits);
		for (int i = 0; i < data.length; i++)
		{
			if (HTLUtilities.getRandomBoolean(percentError)) data[i] = errorValue;
		}
		return data;
	}

	/**
	 * Gibt einen zufälligen Großbuchstaben zurück, der zwischen 'A' und 'Z' liegt
	 * @return der Großbuchstabe
	 */
	public static char getRandomCharAZ()
	{
		return HTLUtilities.getRandomChar('A', 'Z');
	}

	/**
	 * Gibt einen zufälligen Kleinbuchstaben zurück, der zwischen 'a' und 'z'
	 * @return der Kleinbuchstabe
	 */
	public static char getRandomCharaz()
	{
		return HTLUtilities.getRandomChar('a', 'z');
	}

	/**
	 * Gibt einen zufälligen Buchstaben zurück, der zwischen {@code minChar} und {@code maxChar}
	 * @param minChar der Startbuchstabe
	 * @param maxChar der Endbuchstabe
	 * @return der Buchstabe
	 */
	public static char getRandomChar(char minChar, char maxChar)
	{
		return (char)HTLUtilities.getRandom(minChar, maxChar);
	}

	/**
	 * Gibt einen Zufallsstring der Länge {@code count} zurück, der aus Kleinbuchstaben zwischen 'a' und 'z' besteht
	 * @param count die Länge des Zufallsstrings
	 * @return der Zufallsstring
	 */
	public static String getRandomString(int count)
	{
		return getRandomString(count, 'a', 'z');
	}

	/**
	 * Gibt einen Zufallsstring der Länge {@code count} zurück, der aus Buchstaben zwischen {@code minChar} und {@code maxChar} besteht
	 * @param count die Länge des Zufallsstrings
	 * @param minChar der Startbuchstabe
	 * @param maxChar der Endbuchstabe
	 * @return der Zufallsstring
	 */
	public static String getRandomString(int count, char minChar, char maxChar)
	{
		String result = "";
		if (count > 0)
		{
			StringBuilder buffer = new StringBuilder(count);
			for (int i = 1; i <= count; i++)
			{
				buffer.append(HTLUtilities.getRandomChar(minChar, maxChar));
			}
			result = buffer.toString();
		}
		return result;
	}

	/**
	 * Gibt einen Zufallsnamen der Länge {@code count} zurück, wobei der erste Buchstabe von 'A' bis 'Z' ist und alle weiteren von 'a' bis 'z'
	 * @param count die Länge des Zufallsnamens
	 * @return der Zufallsname
	 */
	public static String getRandomName(int count)
	{
		String result = "";
		if (count > 0)
		{
			result = HTLUtilities.getRandomCharAZ() + HTLUtilities.getRandomString(count - 1);
		}
		return result;
	}


	/**
	 * Gibt ein Zufallsdatum ca. der letzten {@code years} Jahre zurück, d.h. ein plausibles Geburtsdatum
	 * @return das Zufallsdatum
	 */
	public static LocalDate getRandomLocalDate()
	{
		return HTLUtilities.getRandomLocalDate(100);
	}

	/**
	 * Gibt ein Zufallsdatum ca. der letzten {@code years} Jahre zurück
	 * @return das Zufallsdatum
	 */
	public static LocalDate getRandomLocalDate(int years)
	{
		LocalDate ld = LocalDate.now();
		ld = ld.minusDays(HTLUtilities.getRandom(0, years*365));		// ca. years Jahre zurück
		return ld;
	}

	/**
	 * Gibt ein Zufallsdatum ca. von {@code yearsFrom} und {@code yearsTo} Jahre zurück
	 * @return das Zufallsdatum
	 */
	public static LocalDate getRandomLocalDate(int yearsFrom, int yearsTo)
	{
		LocalDate ld = LocalDate.now();
		ld = ld.minusDays(HTLUtilities.getRandom(yearsFrom*365, yearsTo*365));		// ca. years Jahre zurück
		return ld;
	}

	/**
	 * Liefert eine Zufallszeit, wobei die Stunde kleiner als oder gleich wie {@code maxHours} ist
	 * @param maxHours die maximale Stunde
	 * @return die Zufallszeit
	 */
	public static LocalTime getRandomLocalTime(int maxHours)
	{
		return HTLUtilities.getRandomLocalTime(0, maxHours);
	}


	/**
	 * Liefert eine Zufallszeit, wobei die Stunde größer als oder gleich wie {@code minHours} und kleiner als oder gleich wie {@code maxHours} ist
	 * @param minHours die untere Grenze für die Stunde (inklusive)
	 * @param maxHours die obere Grenze für die Stunde (inklusive)
	 * @return die Zufallszeit
	 */
	public static LocalTime getRandomLocalTime(int minHours, int maxHours)
	{
		LocalTime lt = LocalTime.of(HTLUtilities.getRandom(minHours, maxHours), HTLUtilities.getRandom(0, 59), HTLUtilities.getRandom(0, 59));
		return lt;
	}


	/**
	 * Liefert eine Zufallszeit, wobei die Stunde größer als oder gleich wie {@code minHours} und kleiner als oder gleich wie {@code maxHours} ist und das Format {@code format} entspricht
	 * @param minHours die untere Grenze für die Stunde (inklusive)
	 * @param maxHours die obere Grenze für die Stunde (inklusive)
	 * @param format das Format der Zufallszeit, z.B. "HH:mm"
	 * @return die Zufallszeit
	 */
	public static LocalTime getRandomLocalTime(int minHours, int maxHours, String format)
	{
		LocalTime lt = LocalTime.of(HTLUtilities.getRandom(minHours, maxHours), HTLUtilities.getRandom(0, 59), HTLUtilities.getRandom(0, 59));
		String ltFormat = HTLUtilities.toString(lt, format);
		lt = HTLUtilities.parseLocalTime(ltFormat, format);
		return lt;
	}


	/**
	 * Liefert per Zufall gleichverteilt ein Geschlecht, d.h. 'm' oder 'w'
	 * @return das Geschlecht
	 */
	public static char getRandomSex()
	{
		char sex = 'm';
		if (HTLUtilities.getRandom(0, 1) == 0) sex = 'w';
		return sex;
	}


	/**
	 * Liefert zufällig (gleichverteilt) einen String aus den variablen Argumenten {@code strings}
	 * @param objects die variablen Argumente (Variable Arguments (Varargs)
	 * @return der gewählte String
	 */
	public static Object getRandomValue(Object ... objects)
	{
		int randomNumber = HTLUtilities.getRandom(0, objects.length - 1);
		return objects[randomNumber];
	}

	public static Object getRandomValueIndex(int randomNumber, Object ... objects)
	{
//		randomNumber %= objects.length;
		randomNumber = (randomNumber % objects.length + objects.length) % objects.length;
		return objects[randomNumber];
	}


	/**
	 * Gibt mit der Wahrscheinlichkeit {@code percent} das Objekt {@code value1} bzw. mit der Wahrscheinlichkeit {@code 1-percent} das Objekt {@code value2} zurück <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> {@code getRandomValue(0.5, "A", "B"} : liefert mit 50% Wahrscheinlichkeit {@code "A"} oder {@code "B"} </li>
	 *    <li> {@code getRandomValue(0.8, "A", "B"} : liefert mit 80% Wahrscheinlichkeit {@code "A"} und mit 20% Wahrscheinlichkeit {@code "B"} </li>
	 *    <li> {@code getRandomValue(0.2, "A", "B"} : liefert mit 20% Wahrscheinlichkeit {@code "A"} und mit 80% Wahrscheinlichkeit {@code "B"} </li>
	 * </ul>
	 * @param percent der Prozentsatz, der zwischen 0 und 1 liegen soll (muss)
	 * @param value1 das erste Objekt
	 * @param value2 das zweite Objekt
	 * @return {@code value1} oder {@code value2} in Abhängigkeit von {@code percent}
	 */
	public static Object getRandomValue(double percent, Object value1, Object value2)
	{
		Object result;
		if (HTLUtilities.getRandomBoolean(percent))
			result = value1;
		else
			result = value2;
		return result;
	}
	//endregion


	// region Check (is), parse und toString von primitven Datentypen
	// String <-> int
	// Gute Quelle: https://www.ibm.com/developerworks/java/library/j-numberformat/index.html

	/**
	 * Gibt {@code true} zurück, wenn {@code source} eine ganze Zahl bzgl. der Ländereinstellung {@code Locale.GERMAN} ist, sonst {@code false}.
	 * @param source der String, der überprüft wird
	 * @return {@code true}, wenn {@code source} eine ganze Zahl ist, sonst {@code false}
	 //* @see HTLUtilities.isInt
	 */
	public static boolean isInt(String source)
	{
		return HTLUtilities.isInt(source, Locale.GERMAN);
	}

	/**
	 * Gibt {@code true} zurück, wenn {@code source} eine ganze Zahl bzgl. der Ländereinstellung {@code inLocale} ist, sonst {@code false}.
	 * @param source der String, der überprüft wird
	 * @param inLocale die Ländereinstellung, die herangezogen wird
	 * @return {@code true}, wenn {@code source} eine ganze Zahl ist, sonst {@code false}
	 */
	public static boolean isInt(String source,
			   					Locale inLocale)
	{
		boolean result = true;
	    try
	    {
	        HTLUtilities.parseInt(source, inLocale);
	    }
	    catch (Exception e)
	    {
//	    	e.printStackTrace();
	    	result = false;
	    }
	    return result;
	}

	/**
	 * Konvertiert den String {@code source} bzgl. der Ländereinstellung {@code Locale.GERMAN} in eine int-Zahl
	 * @param source der String, der eine Zahl im deutschen Format beinhaltet, z.B. "1.234"
	 * @return die int-Zahl
	 */
	public static int parseInt(String source)
	{
		return HTLUtilities.parseInt(source,
							         Locale.GERMAN);
	}

	/**
	 * Konvertiert den String {@code source} bzgl. der Ländereinstellung {@code inLocale} in eine int-Zahl
	 * @param source der String, der eine Zahl beinhaltet, z.B. "1.234" oder "1,234"
	 * @param inLocale die Ländereinstellung, z.B. Locale.GERMAN oder Locale.ENGLISH
	 * @return die int-Zahl
	 */
	public static int parseInt(String source,
							   Locale inLocale)
	{
		NumberFormat nf = NumberFormat.getInstance(inLocale);
		nf.setParseIntegerOnly(true);
		ParsePosition parsePosition = new ParsePosition(0);
		Number number = nf.parse(source, parsePosition);
		if (parsePosition.getIndex() != source.length())
		{
			throw new RuntimeException(String.format("Wrong format (%s - %s)", source, inLocale.getDisplayName()));		// Fehlermeldung verbessert
		}
		int i = number.intValue();
	    return i;
	}

	private static int parseInteger2(String source) throws ParseException
	{
		DecimalFormat df = new DecimalFormat("0,00");
//			DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.GERMAN);
	    Number number = df.parse(source);
	    return number.intValue();
	}

//	public static Integer parseInteger(String source)
//	{
//		if (HTLUtilities.isNullOrEmpty(source))
//		{
//			return null;
//		}
//		else
//		{
//			return HTLUtilities.parseInt(source);
//		}
//	}

	/**
	 * Formatiert {@code number} bzgl. der Ländereinstellung {@code Locale.GERMAN} in einen {@code String} <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> {@code number=12345678} : liefert {@code 12.345.678} </li>
	 * </ul>
	 * @param number die int-Zahl, die umgewandelt wird
	 * @return der formatierte String
	 */
	public static String toString(int number)
	{
		return HTLUtilities.toString(number, Locale.GERMAN);
	}

	/**
	 * Formatiert {@code number} bzgl. der Ländereinstellung {@code inLocale} in einen {@code String} <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> {@code number=12345678, inLocale=Locale.GERMAN} : liefert {@code 12.345.678} </li>
	 *    <li> {@code number=12345678, inLocale=Locale.ENGLISH} : liefert {@code 12,345,678} </li>
	 * </ul>
	 * @param number die int-Zahl, die umgewandelt wird
	 * @param inLocale die Ländereinstellung, die herangezogen wird
	 * @return der formatierte String
	 */
	public static String toString(int number,
			  					  Locale inLocale)
	{
		NumberFormat nf = NumberFormat.getInstance(inLocale);
		return nf.format(number);
	}

	/**
	 * Formatiert {@code number} bzgl. der Ländereinstellung {@code Locale.GERMAN} in einen {@code String} <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> {@code number=12345678} : liefert {@code 12.345.678} </li>
	 * </ul>
	 * @param number die int-Zahl, die umgewandelt wird
	 * @return der formatierte String
	 */
	public static String toString(long number)
	{
		return HTLUtilities.toString(number, Locale.GERMAN);
	}

	/**
	 * Formatiert {@code number} bzgl. der Ländereinstellung {@code inLocale} in einen {@code String} <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> {@code number=12345678, inLocale=Locale.GERMAN} : liefert {@code 12.345.678} </li>
	 *    <li> {@code number=12345678, inLocale=Locale.ENGLISH} : liefert {@code 12,345,678} </li>
	 * </ul>
	 * @param number die int-Zahl, die umgewandelt wird
	 * @param inLocale die Ländereinstellung, die herangezogen wird
	 * @return der formatierte String
	 */
	public static String toString(long number,
			  					  Locale inLocale)
	{
		NumberFormat nf = NumberFormat.getInstance(inLocale);
		return nf.format(number);
	}



	// String <-> double
	// Gute Quelle: https://www.ibm.com/developerworks/java/library/j-numberformat/index.html

	/**
	 * Gibt {@code true} zurück, wenn {@code source} eine Gleitkommazahl bzgl. der Ländereinstellung {@code Locale.GERMAN} ist, sonst {@code false}.
	 * {@code isDouble} überprüft, ob die übergebene {@code source} eine Gleitkommazahl im deutschen Format ist.
	 * @param source der String, der überprüft wird
	 * @return {@code true}, wenn {@code source} eine Gleitkommazahl ist, sonst {@code false}
	 */
	public static boolean isDouble(String source)
	{
		return HTLUtilities.isDouble(source, Locale.GERMAN);
	}

	/**
	 * Gibt {@code true} zurück, wenn {@code source} eine Gleitkommazahl bzgl. der Ländereinstellung {@code inLocale} ist, sonst {@code false}.
	 * @param source der String, der überprüft wird
	 * @param inLocale die Ländereinstellung, die herangezogen wird
	 * @return {@code true}, wenn {@code source} eine Gleitkommazahl ist, sonst {@code false}
	 */
	public static boolean isDouble(String source,
		     					   Locale inLocale)
	{
		boolean result = true;
	    try
	    {
	        HTLUtilities.parseDouble(source, inLocale);
	    }
	    catch (NumberFormatException | NullPointerException e)
	    {
//	    	System.out.println(e.getMessage());
	    	result = false;
	    }
	    return result;
	}

	/**
	 * Konvertiert den String {@code source} bzgl. der Ländereinstellung {@code Locale.GERMAN} in eine double-Zahl
	 * @param source der String, der eine Zahl beinhaltet, z.B. "1.234,56" oder "1,234.56"
	 * @return die double-Zahl
	 */
	public static double parseDouble(String source)
	{
		return HTLUtilities.parseDouble(source, 						// Standardsprache: deutsch
								   		Locale.GERMAN);
	}

	/**
	 * Konvertiert den String {@code source} bzgl. der Ländereinstellung {@code inLocale} in eine double-Zahl
	 * @param source der String, der eine Zahl beinhaltet, z.B. "1.234,56" oder "1,234.56"
	 * @param inLocale die Ländereinstellung, z.B. Locale.GERMAN oder Locale.ENGLISH
	 * @return die double-Zahl
	 */
	public static double parseDouble(String source,
								     Locale inLocale)
	{
		NumberFormat nf = NumberFormat.getInstance(inLocale);		// einstellen der Sprache
		ParsePosition parsePosition = new ParsePosition(0);
		Number number = nf.parse(source.trim(), parsePosition);		// Konvertierung in eine allgemeine Zahl
//	    if (parsePosition.getIndex() != source.length())
//		{
//			throw new RuntimeException("Wrong format");
//		}
		double d = number.doubleValue();
	    return d;													// Rückgabe der Gleitkommazahl
	}

	private static double parseDouble2(String doubleS)
	{
		DecimalFormat df = new DecimalFormat("0,00");
//		DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.GERMAN);
	    Number number;
		try
		{
			number = df.parse(doubleS);
		}
		catch (ParseException e)
		{
			throw new RuntimeException("Wrong format");
		}
	    return number.doubleValue();
	}

	/**
	 * Formatiert {@code number} bzgl. der Ländereinstellung {@code Locale.GERMAN} mit 2 Nachkommastellen in einen {@code String} <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> {@code d=1234.5678, inLocale=Locale.GERMAN} : liefert {@code 1.234,57} </li>
	 * </ul>
	 * @param number die double-Zahl, die umgewandelt wird
	 * @return der formatierte String
	 */
	public static String toString(double number)
	{
		return HTLUtilities.toString(number, Locale.GERMAN);
	}

	/**
	 * Formatiert {@code number} bzgl. der Ländereinstellung {@code inLocale} mit 2 Nachkommastellen in einen {@code String} <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> {@code d=1234.5678, inLocale=Locale.GERMAN} : liefert {@code 1.234,57} </li>
	 *    <li> {@code d=1234.5678, inLocale=Locale.ENGLISH} : liefert {@code 1,234.57} </li>
	 * </ul>
	 * @param number die double-Zahl, die umgewandelt wird
	 * @param inLocale die Ländereinstellung, die herangezogen wird
	 * @return der formatierte String
	 */
	public static String toString(double number,
			  					  Locale inLocale)
	{
		return HTLUtilities.toString(number, inLocale, 2);
	}

	/**
	 * Formatiert {@code number} bzgl. der Ländereinstellung {@code inLocale} mit {@code fractionDigits} Nachkommastellen in einen {@code String} <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> {@code d=1234.5678, inLocale=Locale.GERMAN, fractionDigits=2} : liefert {@code 1.234,57} </li>
	 *    <li> {@code d=1234.5678, inLocale=Locale.GERMAN, fractionDigits=3} : liefert {@code 1.234,568} </li>
	 *    <li> {@code d=1234.5678, inLocale=Locale.ENGLISH, fractionDigits=2} : liefert {@code 1,234.57} </li>
	 *    <li> {@code d=1234.5678, inLocale=Locale.ENGLISH, fractionDigits=3} : liefert {@code 1,234.568} </li>
	 * </ul>
	 * @param number die double-Zahl, die umgewandelt wird
	 * @param inLocale die Ländereinstellung, die herangezogen wird
	 * @param fractionDigits die Anzahl der Nachkommastellen, die verwendet werden
	 * @return der formatierte String
	 */
	public static String toString(double number,
			  					  Locale inLocale,
			  					  int fractionDigits)
	{
		NumberFormat nf = NumberFormat.getInstance(inLocale);
		nf.setMinimumFractionDigits(fractionDigits);
		nf.setMaximumFractionDigits(fractionDigits);
		return nf.format(number);
	}
	//endregion


	//region Check (is), parse und toString von LocalDate, LocalTime und LocalDateTime
	// ********************
	// String <-> LocalDate
	/**
	 * Gibt {@code true} zurück, wenn der String {@code source} im Datumsformat {@code format} bzgl. der Ländereinstellung {@code Locale.GERMAN} vorliegt. <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> {@code source="01.01.2020", format="dd.MM.yyyy"} : liefert {@code true} </li>
	 *    <li> {@code source="01.01.20", format="dd.MM.yy"} : liefert {@code true} </li>
	 *    <li> {@code source="01.Dez.2020", format="dd.MMM.yyyy"} : liefert {@code true} </li>
	 *    <li> {@code source="01.Dezember.2020", format="dd.MMMM.yyyy"} : liefert {@code true} </li>
	 *    <li> {@code source="01.01.2020", format="dd.MM.yy"} : liefert {@code false} </li>
	 *    <li> {@code source="01.Dec.2020", format="dd.MMM.yyyy"} : liefert {@code false} </li>
	 * </ul>
	 * @param source der String, der überprüft wird
	 * @param format das Format, das herangezogen wird
	 * @return {@code true}, wenn {@code source} ein Datum ist, sonst {@code false}
	 */
	public static boolean isLocalDate(String source,
 		   							  String format)
	{
		return HTLUtilities.isLocalDate(source, format, Locale.GERMAN);
	}

	/**
	 * Gibt {@code true} zurück, wenn der String {@code source} im Datumsformat {@code format} bzgl. der Ländereinstellung {@code inLocale} vorliegt. <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> {@code source="01.01.2020", format="dd.MM.yyyy", inLocale=Locale.GERMAN} : liefert {@code true} </li>
	 *    <li> {@code source="01.01.20", format="dd.MM.yy", inLocale=Locale.GERMAN} : liefert {@code true} </li>
	 *    <li> {@code source="01.Dez.2020", format="dd.MMM.yyyy", inLocale=Locale.GERMAN} : liefert {@code true} </li>
	 *    <li> {@code source="01.Dezember.2020", format="dd.MMMM.yyyy", inLocale=Locale.GERMAN} : liefert {@code true} </li>
	 *    <li> {@code source="01.Dec.2020", format="dd.MMM.yyyy", inLocale=Locale.ENGLISH} : liefert {@code true} </li>
	 *    <li> {@code source="01.December.2020", format="dd.MMMM.yyyy", inLocale=Locale.ENGLISH} : liefert {@code true} </li>
	 *    <li> {@code source="01.01.2020", format="dd.MM.yy", inLocale=Locale.GERMAN} : liefert {@code false} </li>
	 *    <li> {@code source="01.Dec.2020", format="dd.MMM.yyyy", inLocale=Locale.GERMAN} : liefert {@code false} </li>
	 * </ul>
	 * @param source der String, der überprüft wird
	 * @param format das Format, das herangezogen wird
	 * @return {@code true}, wenn {@code source} ein Datum ist, sonst {@code false}
	 */
	public static boolean isLocalDate(String source,
		   							  String format,
  								      Locale inLocale)
	{
		boolean result = true;
		try
		{
			LocalDate ld = HTLUtilities.parseLocalDate(source, format, inLocale);
			result = (ld != null);
		}
		catch (Exception e)
		{
			result = false;
		}
		return result;
	}


	/**
	 * Gibt ein {@code LocalDate} zurück, wenn der String {@code source} im Datumsformat {@code format} bzgl. der Ländereinstellung {@code Locale.GERMAN} vorliegt. <br>
	 * @param source der String, der umgewandelt wird
	 * @param format das Format, das herangezogen wird
	 * @return eine Instanz der Klasse {@code LocalDate}, wenn die Umwandlung erfolgreich ist, sonst {@code null}
	 */
	public static LocalDate parseLocalDate(String source,
										   String format)
	{
		return HTLUtilities.parseLocalDate(source, format, Locale.GERMAN);
	}

	/**
	 * Gibt ein {@code LocalDate} zurück, wenn der String {@code source} im Datumsformat {@code format} bzgl. der Ländereinstellung {@code inLocale} vorliegt. <br>
	 * @param source der String, der umgewandelt wird
	 * @param format das Format, das herangezogen wird
	 * @param inLocale die Ländereinstellung, die herangezogen wird
	 * @return eine Instanz der Klasse {@code LocalDate}, wenn die Umwandlung erfolgreich ist, sonst {@code null}
	 */
	public static LocalDate parseLocalDate(String source,
										   String format,
										   Locale inLocale)
	{
		if (HTLUtilities.isNullOrEmpty(source))
		{
			return null;
		}
		else
		{
			return LocalDate.parse(source, DateTimeFormatter.ofPattern(format, inLocale));
		}
	}


	/**
	 * Gibt ein {@code LocalDate} zurück, wenn der String {@code source} in einem gängigen Datumsformat bzgl. der Ländereinstellung {@code Locale.GERMAN} vorliegt. <br>
	 * Folgende Formate werden unterstützt. <br>
	 * <b> Konnte der String nicht umgewandelt werden, dann wird {@code null} zurückgegeben. </b>
	 * <ul>
	 *    <li> für den Tag: d, dd </li>
	 *    <li> für das Monat: M, MM, MMM </li>
	 *    <li> für das Jahr: YY, YYYY </li>
	 * </ul>
	 * Folgende Reihenfolgen werden unterstützt
	 * <ul>
	 *    <li> dd.MM.yyyy </li>
	 *    <li> yyyy.MM.dd </li>
	 * </ul>
	 * Folgende Trennzeichen werden parallel unterstützt: ".", ",", "-", "/", ";", ":"
	 * @param source der String, der umgewandelt wird
	 * @return eine Instanz der Klasse {@code LocalDate}, wenn die Umwandlung erfolgreich ist, sonst {@code null}. Auch wenn {@code source null} oder empty ist, wird {@code null} zurückgegeben. <br>
	 * Besser wäre es eine Instanz von {@code Optional} zurückzugeben. Dafür muss aber diese Klasse (den SchülerInnen) bekannt sein.
	 */
	public static LocalDate parseLocalDate(String source)
	{
		return HTLUtilities.parseLocalDate(source, Locale.GERMAN);
	}


	/**
	 * Gibt ein {@code LocalDate} zurück, wenn der String {@code source} in einem gängigen Datumsformat bzgl. der Ländereinstellung {@code inLocale} vorliegt. <br>
	 * @param source der String, der umgewandelt wird
	 * @param inLocale die Ländereinstellung, die herangezogen wird
	 * @return eine Instanz der Klasse {@code LocalDate}, wenn die Umwandlung erfolgreich ist, sonst {@code null}. Auch wenn {@code source null} oder empty ist, wird {@code null} zurückgegeben. <br>
	 * Besser wäre es eine Instanz von {@code Optional} zurückzugeben. Dafür muss aber diese Klasse (den SchülerInnen) bekannt sein.
	 * @see HTLUtilities#parseLocalDate(String)
	 */
	public static LocalDate parseLocalDate(String source,
			   						       Locale inLocale)
	{
		LocalDate ld = null;

		if (source != null && !source.isEmpty())
		{
			char[] separators = {'.', ',', '-', '/', ';', ':'};
			String[] dayFormats = {"dd", "d"};
			String[] monthFormats = {"MM", "MMM", "MMMM"};
			String[] yearFormats = {"yyyy", "yy"};

			String[] formats = {"D.M.Y", "Y.M.D"};

			source = source.replace(" ", "");
			for (int j = 1; j < separators.length; j++)
			{
				source = source.replace(separators[j], separators[0]);			// ersetze alle Separatoren durch '.'
			}

			for (String dayFormat: dayFormats)
			{
				for (String monthFormat: monthFormats)
				{
					for (String yearFormat: yearFormats)
					{
						for (int i = 0; i < formats.length; i++)
						{
							String format = formats[i];
							format = format.replace("D", dayFormat);
							format = format.replace("M", monthFormat);
							format = format.replace("Y", yearFormat);

							try
							{
								ld = HTLUtilities.parseLocalDate(source, format, inLocale);
//								System.out.printf("%s -> %s: %s%n", source, format, ld);
								return ld;
							}
							catch (Exception e)
							{
								// ignore ;-)
							}
						}
					}
				}
			}
		}

		return ld;
	}



	/**
	 * Gibt den boolesche Wert {@code b} als String zurück <br>
	 * @param b boolesche Wert
	 * @return "-1", wenn {@code b == true}, sonst "-1"
	 */
	public static String toString(boolean b)
	{
		return b ? "-1": "0";
	}

	/**
	 * Gibt das Zeichen {@code c} als String zurück.
	 * @param c das Zeichen
	 * @return ein String, der aus dem Zeichen {@code c} besteht
	 */
	public static String toString(char c)
	{
		return String.valueOf(c);
	}

	/**
	 * Formatiert {@code ld} auf Basis des Formats {@code "dd.MM.yyyy"} bzgl. der Ländereinstellung {@code Locale.GERMAN} in einen {@code String} <br>
	 * @param ld das LocalDate, das umgewandelt wird
	 * @return der formatierte String oder ein Leerstring, wenn ld null ist
	 */
	public static String toString(LocalDate ld)
	{
		return HTLUtilities.toString(ld, "dd.MM.yyyy");
	}

	/**
	 * Formatiert {@code ld} auf Basis des Formats {@code format} bzgl. der Ländereinstellung {@code Locale.GERMAN} in einen {@code String} <br>
	 * @param ld das LocalDate, das umgewandelt wird
	 * @param format das Format, das herangezogen wird
	 * @return der formatierte String oder ein Leerstring, wenn ld null ist
	 */
	public static String toString(LocalDate ld,
								  String format)
	{
		return HTLUtilities.toString(ld, format, Locale.GERMAN);
	}

	/**
	 * Formatiert {@code ld} auf Basis des Formats {@code format} bzgl. der Ländereinstellung {@code inLocale} in einen {@code String} <br>
	 * @param ld das LocalDate, das umgewandelt wird
	 * @param format das Format, das herangezogen wird
	 * @param inLocale die Ländereinstellung, die herangezogen wird
	 * @return der formatierte String oder ein Leerstring, wenn ld null ist
	 */
	public static String toString(LocalDate ld,
			  					  String format,
			  					  Locale inLocale)
	{
		if (ld != null)
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, inLocale);
			return ld.format(formatter);
		}
		else
		{
			return "";
		}
	}


	// ************************
	// String <-> LocalDateTime
	/**
	 * Gibt {@code true} zurück, wenn der String {@code source} im Datumsformat {@code format} bzgl. der Ländereinstellung {@code Locale.GERMAN} vorliegt.
	 * @param source der String, der überprüft wird
	 * @param format das Format, das herangezogen wird
	 * @return {@code true}, wenn {@code source} ein Datum-Uhrzeit ist, sonst {@code false}
	 */
	public static boolean isLocalDateTime(String source,
 		   							      String format)
	{
		return HTLUtilities.isLocalDateTime(source, format, Locale.GERMAN);
	}

	/**
	 * Gibt {@code true} zurück, wenn der String {@code source} im Datum- und Zeitformat {@code format} bzgl. der Ländereinstellung {@code inLocale} vorliegt.
	 * @param source der String, der überprüft wird
	 * @param format das Format, das herangezogen wird
	 * @param inLocale die Ländereinstellung, die herangezogen wird
	 * @return {@code true}, wenn {@code source} ein Datum-Uhrzeit ist, sonst {@code false}
	 */
	public static boolean isLocalDateTime(String source,
		   							      String format,
  								          Locale inLocale)
	{
		boolean result = true;
		try
		{
			LocalDateTime ldt = HTLUtilities.parseLocalDateTime(source, format, inLocale);
			result = (ldt != null);
		}
		catch (Exception e)
		{
			result = false;
		}
		return result;
	}


	/**
	 * Gibt ein {@code LocalDateTime} zurück, wenn der String {@code source} im Datum- und Zeitformat {@code format} bzgl. der Ländereinstellung {@code Locale.GERMAN} vorliegt. <br>
	 * @param source der String, der umgewandelt wird
	 * @param format das Format, das herangezogen wird
	 * @return eine Instanz der Klasse {@code LocalDateTime}, wenn die Umwandlung erfolgreich ist, sonst {@code null}
	 */
	public static LocalDateTime parseLocalDateTime(String source,
							    			       String format)
	{
		return HTLUtilities.parseLocalDateTime(source, format, Locale.GERMAN);
	}

	/**
	 * Gibt ein {@code LocalDateTime} zurück, wenn der String {@code source} im Datum- und Zeitformat {@code format} bzgl. der Ländereinstellung {@code inLocale} vorliegt. <br>
	 * @param source der String, der umgewandelt wird
	 * @param format das Format, das herangezogen wird
	 * @param inLocale die Ländereinstellung, die herangezogen wird
	 * @return eine Instanz der Klasse {@code LocalDateTime}, wenn die Umwandlung erfolgreich ist, sonst {@code null}
	 */
	public static LocalDateTime parseLocalDateTime(String source,
		       									   String format,
		       								       Locale inLocale)
	{
		if (HTLUtilities.isNullOrEmpty(source))
		{
			return null;
		}
		else
		{
			return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(format, inLocale));
		}
	}


	/**
	 * Formatiert {@code ldt} auf Basis des Formats {@code "dd.MM.yyyy hh:mm"} bzgl. der Ländereinstellung {@code Locale.GERMAN} in einen {@code String} <br>
	 * @param ldt das LocalDateTime, das umgewandelt wird
	 * @return der formatierte String oder ein Leerstring, wenn ldt null ist
	 */
	public static String toString(LocalDateTime ldt)
	{
		return HTLUtilities.toString(ldt, "dd.MM.yyyy hh:mm");
	}

	/**
	 * Formatiert {@code ldt} auf Basis des Formats {@code format} bzgl. der Ländereinstellung {@code Locale.GERMAN} in einen {@code String} <br>
	 * @param ldt das LocalDateTime, das umgewandelt wird
	 * @param format das Format, das herangezogen wird
	 * @return der formatierte String oder ein Leerstring, wenn ldt null ist
	 */
	public static String toString(LocalDateTime ldt,
			  					  String format)
	{
		return HTLUtilities.toString(ldt, format, Locale.GERMAN);
	}

	/**
	 * Formatiert {@code ldt} auf Basis des Formats {@code format} bzgl. der Ländereinstellung {@code inLocale} in einen {@code String} <br>
	 * @param ldt das LocalDateTime, das umgewandelt wird
	 * @param format das Format, das herangezogen wird
	 * @param inLocale die Ländereinstellung, die herangezogen wird
	 * @return der formatierte String oder ein Leerstring, wenn ldt null ist
	 */
	public static String toString(LocalDateTime ldt,
			  					  String format,
			  					  Locale inLocale)
	{
		if (ldt != null)
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, inLocale);
			return ldt.format(formatter);
		}
		else
		{
			return "";
		}
	}



	// ********************
	// String <-> LocalTime
	/**
	 * Gibt {@code true} zurück, wenn der String {@code source} im Zeitformat {@code format} bzgl. der Ländereinstellung {@code Locale.GERMAN} vorliegt. <br>
	 * <b> Beispiele </b>
	 * <ul>
	 *    <li> {@code source="01:23:34", format="HH:mm:ss"} : liefert {@code true} </li>
	 *    <li> {@code source="11:23:34 AM", format="hh:mm:ss a"} : liefert {@code true} </li>
	 *    <li> {@code source="11:23:34 PM", format="hh:mm:ss a"} : liefert {@code true} </li>
	 *    <li> {@code source="01:23:34", format="HH:mm"} : liefert {@code false} </li>
	 *    <li> {@code source="01:23", format="HH:mm:ss"} : liefert {@code false} </li>
	 *    <li> {@code source="01:23.34", format="HH:mm:ss"} : liefert {@code false} </li>
	 * </ul>
	 * @param source der String, der überprüft wird
	 * @param format das Format, das herangezogen wird
	 * @return {@code true}, wenn {@code source} eine Uhrzeit ist, sonst {@code false}
	 */
	public static boolean isLocalTime(String source,
 		   							  String format)
	{
		boolean result = true;
		try
		{
			LocalTime lt = HTLUtilities.parseLocalTime(source, format, Locale.GERMAN);
			result = (lt != null);
		}
		catch (Exception e)
		{
			result = false;
		}
		return result;
	}

	/**
	 * Gibt ein {@code LocalTime} zurück, wenn der String {@code source} im Zeitformat {@code format} bzgl. der Ländereinstellung {@code Locale.GERMAN} vorliegt. <br>
	 * @param source der String, der umgewandelt wird
	 * @param format das Format, das herangezogen wird
	 * @return eine Instanz der Klasse {@code LocalTime}, wenn die Umwandlung erfolgreich ist, sonst {@code null}
	 */
	public static LocalTime parseLocalTime(String source,
							    		   String format)
	{
		return HTLUtilities.parseLocalTime(source, format, Locale.GERMAN);
	}

	/**
	 * Gibt ein {@code LocalTime} zurück, wenn der String {@code source} im Zeitformat {@code format} bzgl. der Ländereinstellung {@code inLocale} vorliegt. <br>
	 * @param source der String, der umgewandelt wird
	 * @param format das Format, das herangezogen wird
	 * @param inLocale die Ländereinstellung, die herangezogen wird
	 * @return eine Instanz der Klasse {@code LocalTime}, wenn die Umwandlung erfolgreich ist, sonst {@code null}
	 */
	public static LocalTime parseLocalTime(String source,
 		   								   String format,
       								       Locale inLocale)
	{
		if (HTLUtilities.isNullOrEmpty(source))
		{
			return null;
		}
		else
		{
			return LocalTime.parse(source, DateTimeFormatter.ofPattern(format, inLocale));
		}
	}


	/**
	 * Gibt ein {@code LocalTime} zurück, wenn der String {@code source} in einem gängigen Zeitformat vorliegt. <br>
	 * Folgende Formate werden unterstützt
	 * <ul>
	 *    <li> für die Stunde: H, HH </li>
	 *    <li> für die Minuten: m, mm </li>
	 *    <li> für die Sekunden: s, ss </li>
	 * </ul>
	 * Folgende Reihenfolgen werden unterstützt
	 * <ul>
	 *    <li> HH:mm:ss </li>
	 *    <li> HH:mm </li>
	 * </ul>
	 * Folgende Trennzeichen werden unterstützt: ":", ".", ",", "-", "/", ";"
	 * @param source der String, der umgewandelt wird
	 * @return eine Instanz der Klasse {@code LocalTime}, wenn die Umwandlung erfolgreich ist, sonst {@code null}. Auch wenn {@code source null} oder empty ist, wird {@code null} zurückgegeben.
	 * Besser wäre es eine Instanz von Optional zurückzugeben. Dafür muss aber diese Klasse bekannt sein.
	 * @see HTLUtilities#parseLocalDate(String)
	 */
	public static LocalTime parseLocalTime(String source)
	{
		LocalTime lt = null;

		if (source != null && !source.isEmpty())
		{
			char[] separators = {':', '.', ',', '-', '/', ';'};
			String[] hourFormats = {"HH", "H"};
			String[] minuteFormats = {"mm", "m"};
			String[] secondFormats = {"ss", "s"};

			String[] formats = {"H:M:S", "H:M"};

			source = source.replace(" ", "");
			for (int j = 1; j < separators.length; j++)
			{
				source = source.replace(separators[j], separators[0]);			// ersetze alle Separatoren durch '.'
			}

			for (String hourFormat: hourFormats)
			{
				for (String minuteFormat: minuteFormats)
				{
					for (String secondFormat: secondFormats)
					{
						for (int i = 0; i < formats.length; i++)
						{
							String format = formats[i];
							format = format.replace("H", hourFormat);
							format = format.replace("M", minuteFormat);
							format = format.replace("S", secondFormat);

							try
							{
								lt = HTLUtilities.parseLocalTime(source, format);
//								System.out.printf("%s -> %s: %s%n", source, format, lt);
								return lt;
							}
							catch (Exception e)
							{
								// ignore ;-)
							}
						}
					}
				}
			}
		}

		return lt;
	}



	/**
	 * Formatiert {@code lt} auf Basis des Formats {@code "hh:mm"} bzgl. der Ländereinstellung {@code Locale.GERMAN} in einen {@code String} <br>
	 * @param lt die LocalTime, die umgewandelt wird
	 * @return der formatierte String oder ein Leerstring, wenn lt null ist
	 */
	public static String toString(LocalTime lt)
	{
		return HTLUtilities.toString(lt, "hh:mm");
	}

	/**
	 * Formatiert {@code lt} auf Basis des Formats {@code format} bzgl. der Ländereinstellung {@code Locale.GERMAN} in einen {@code String} <br>
	 * @param lt die LocalTime, die umgewandelt wird
	 * @param format das Format, das herangezogen wird
	 * @return der formatierte String oder ein Leerstring, wenn lt null ist
	 */
	public static String toString(LocalTime lt,
			  					  String format)
	{
		return HTLUtilities.toString(lt, format, Locale.GERMAN);
	}

	/**
	 * Formatiert {@code lt} auf Basis des Formats {@code format} bzgl. der Ländereinstellung {@code inLocale} in einen {@code String} <br>
	 * @param lt die LocalTime, die umgewandelt wird
	 * @param format das Format, das herangezogen wird
	 * @param inLocale die Ländereinstellung, die herangezogen wird
	 * @return der formatierte String oder ein Leerstring, wenn lt null ist
	 */
	public static String toString(LocalTime lt,
			  					  String format,
			  					  Locale inLocale)
	{
		if (lt != null)
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, inLocale);
			return lt.format(formatter);
		}
		else
		{
			return "";
		}
	}
	//endregion


	//region JDBC: Date <-> LocalDate
	public static LocalDate toLocalDate(Date d)
	{
		if (d == null)
		{
			return null;
		}
		else
		{
			return d.toLocalDate();
		}
	}

	public static LocalDateTime toLocalDateTime(Timestamp t)
	{
		if (t == null)
		{
			return null;
		}
		else
		{
			return t.toLocalDateTime();
		}
	}

	public static Date toDate(LocalDate ld)
	{
		if (ld == null)
		{
			return null;
		}
		else
		{
			return Date.valueOf(ld);
		}
	}

	public static Timestamp toTimestamp(LocalDateTime ldt)
	{
		if (ldt == null)
		{
			return null;
		}
		else
		{
			return Timestamp.valueOf(ldt);
		}
	}
	//endregion


	//region Duration
	// String <-> Duration
	// gilt nur für das Format 01:12:17
	private static Duration parseDuration(String s)
	{
		String[] arrTime = s.split(":");
		// ISO 8601 Data elements and interchange formats – Information interchange – Representation of dates and times
		return Duration.parse(String.format("PT%sH%sM%sS", arrTime[0], arrTime[1], arrTime[2]));
	}

	private static String toString(Duration d)
	{
		long secondsTotal = d.getSeconds();
		long hours = secondsTotal / 3600;
		secondsTotal -= hours * 3600;
		long minutes = secondsTotal / 60;
		secondsTotal -= minutes * 60;
		return String.format("%02d:%02d:%02d", hours, minutes, secondsTotal);
	}
	//endregion


	//region Misc

	/**
	 * Der Aufruf dieser Methode bewirkt, dass das Programm ca. {@code time} Millisekunden stoppt (pausiert).
	 * @param time die Zeitdauer in Millisekunden
	 */
	public static void sleep(int time)
	{
		try
		{
			TimeUnit.MILLISECONDS.sleep(time);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Wandelt den String {@code text} in einen String um, der nicht {@code null} ist.
	 * @param text der String
	 * @return {@code text}, wenn {@code text != null}, sonst {@code ""}
	 */
	public static String nz(String text)
	{
		String result = text;
		if (result == null)
			result = "";
		return result;
	}


	// Arrays
	public static String toString(int[] data, int minPosition, int maxPosition)
	{
		int[] dataCopied = Arrays.copyOfRange(data, minPosition, maxPosition + 1);
		return Arrays.toString(dataCopied);
	}


	// Splitting
	public static String[] split(String record,
			 					 String fieldSeparator)
	{
		return record.split(fieldSeparator, -1);
	}

	public static String[] splitAndTrim(String record,
										String fieldSeparator,
										String quote)
	{
		String[] fields = HTLUtilities.split(record, fieldSeparator);
		if (!quote.isEmpty())
		{
			for (int i = 0; i < fields.length; i++)
			{
				fields[i] = fields[i].substring(quote.length(), fields[i].length() - quote.length());
			}
		}
//		System.out.println(Arrays.toString(fields));
		return fields;
	}


	// ********
	// Diverses
	public static int min(int value1, int value2, Integer... values)
	{
		int min = Math.min(value1, value2);
		for (Integer value : values)
		{
			min = Math.min(min, value);
		}
		return min;
	}

	public static int max(int value1, int value2, Integer... values)
	{
		int max = Math.max(value1, value2);
		for (Integer value : values)
		{
			max = Math.max(max, value);
		}
		return max;
	}


	// Start und Stop
	/**
	 * Gibt die aktuelle Zeit in Millisekunden zurück.
	 * @return die Zeitdifferenz in Millisekunden zwischen der aktuellen Zeit und dem 01.01.1970 um 00:00.
	 */
	public static long startTime()
	{
		return System.currentTimeMillis();
	}

	/**
	 * Gibt die Zeitdifferenz zwischen der aktuellen Zeit und {@code startTime} in Millisekunden zurück. <br>
	 * <b> Beispiel </b>
	 * <ul>
	 *    <li> {@code long startTime = HTLUtilities.startTime();} </li>
	 *    <li> ... </li>
	 *    <li> Hier steht der Code, der gestoppt werden soll. </li>
	 *    <li> ... </li>
	 *    <li> {@code long delta = HTLUtilities.stopTime(startTime);} </li>
	 *    <li> {@code System.out.printf("Time: %d", delta);} </li>
	 * </ul>
	 * @param startTime die Startzeit
	 * @return die Zeitdifferenz in Millisekunden
	 */
	public static long stopTime(long startTime)
	{
		long stopTime = System.currentTimeMillis();
		return stopTime - startTime;
	}


	// HTML
	public static String tabs(int length)
	{
		String str = "";
		if (length > 0)
		{
			str = new String(new char[length]);
			str = str.replace('\0', '\t');
		}
		return str;
	}

//	public static void copy2Clipboard(String content)
//	{
//		StringSelection selection = new StringSelection(content);
//    	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//    	clipboard.setContents(selection, selection);
//	}
	//endregion


	//region File-Operations
	/**
	 * Gibt {@code true} zurück, wenn der Ordner existiert, sonst {@code false}
	 * @param path der Pfad des Ordners
	 * @return {@code true}, wenn der Ordner existiert, sonst {@code false}
	 */
	public static boolean existsFolder(String path)
	{
		 File folder = new File(path);
	     return folder.exists();
	}

	/**
	 * Ermittelt den vollständigen Pfad der Datei {@code fileName}, wenn diese im selben Verzeichnis ist, wie die Klasse {@code clazz}
	 * @param clazz die Klasse
	 * @param fileName der Name der Datei
	 * @return der vollständige Pfad der Datei oder der Pfad bzgl. dem Eclipse-Ordner "Source" oder "Src"
	 */
	public static String getFilePath(Class clazz,
									 String fileName)
	{
		boolean exists = false;

//		System.out.println(System.getProperty("user.dir"));
		String packageName = clazz.getName();														// at.htlklu.fileIO.school.V1.SchoolStudentLoad
		packageName = packageName.replace(".", System.getProperty("file.separator")) + ".java";		// at\htlklu\fileIO\school\V1\SchoolStudentLoad.java

		String clazzPath = getPath(packageName);													// Source\at\htlklu\fileIO\school\V1\SchoolStudentLoad.java
		File f = new File(clazzPath);
//		System.out.println(f.exists());

		String fileNamePath = "";
		do																							// sucht die Datei rekursiv (nach "oben")
		{
			fileNamePath = f.getParent() + System.getProperty("file.separator") + fileName;
			exists = existsFile(fileNamePath);
			if (!exists)
				f = f.getParentFile();
		} while (f != null && !exists);

		if (!exists) fileNamePath = "Source" + System.getProperty("file.separator") + packageName;

		return fileNamePath;
	}

	/**
	 * Ermittelt der Pfad zur Date {@code fileName} im Ordner "Source" oder "Src"
	 * @param fileName der Name der Datei
	 * @return der vollständige Pfad der Datei oder der Leerstring
	 */
	private static String getPath(String fileName)
	{
		boolean exists = false;

		String fileNamePath = "";
		if (!exists)
		{
			fileNamePath = "Source" + System.getProperty("file.separator") + fileName;
			exists = existsFile(fileNamePath);
		}
		if (!exists)
		{
			fileNamePath = "Src" + System.getProperty("file.separator") + fileName;
			exists = existsFile(fileNamePath);
		}
		if (!exists) fileNamePath = "";

		return fileNamePath;
	}

	/**
	 * Überprüft, ob die Datei {@code path} existiert.
	 * @param path der Pfad zur Datei
	 * @return Gibt {@code true} zurück, wenn die Datei existiert, sonst {@code false}
	 */
	private static boolean existsFile(String path)
	{
		File f = new File(path);
		return f.exists();
	}
	//endregion

}
