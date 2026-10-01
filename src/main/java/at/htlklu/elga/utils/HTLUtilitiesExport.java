package at.htlklu.elga.utils;

import java.awt.Desktop;
import java.io.*;

public class HTLUtilitiesExport 
{
		
	/**
	 * Erstellt auf Basis des �bergebenen Body {@code body} eine tempor�re HTML-Datei und �ffnet diese mit dem Standardexplorer 
	 * @param body der Body der HTML-Datei
	 * @return der Pfad der tempor�ren Datei
	 */
	public static String openHTMLBody(String body)
	{
		return HTLUtilitiesExport.openHTMLBody("", body);
	}
	
	/**
	 * Erstellt auf Basis eines CSS-Styles und dem �bergebenen Body {@code body} eine tempor�re HTML-Datei und �ffnet diese mit dem Standardexplorer 
	 * @param body der Body der HTML-Datei
	 * @return der Pfad der tempor�ren Datei
	 */
	public static String openHTMLBodyWithDefaultStyle(String body)
	{
		String style = HTLUtilities.tabs(1) + "<style>%n" +
							HTLUtilities.tabs(2) + "table {font-family: arial, sans-serif; border: solid 1px black; border-collapse: collapse; }%n" +
							HTLUtilities.tabs(2) + "thead {border: 1px solid red; background-color: green; color: white; font-weight: bold; font-size: 120%%}%n" +
							HTLUtilities.tabs(2) + "thead td {border: 1px solid white; text-align: left; padding: 8px; }%n" +
							HTLUtilities.tabs(2) + "tr:nth-child(even) {background-color: #dddddd; }%n" +
							HTLUtilities.tabs(2) + "th {border: 1px solid white; background-color: green; color: white;  padding: 8px;}%n" +
							HTLUtilities.tabs(2) + "tbody td {border: 1px solid green; text-align: left; padding: 8px; }%n" +
							HTLUtilities.tabs(2) + "th.left, td.left {text-align: left; }%n"  + 
							HTLUtilities.tabs(2) + "th.center, td.center {text-align: center; }%n"  + 
							HTLUtilities.tabs(2) + "th.right, td.right {text-align: right; }%n"  + 
							HTLUtilities.tabs(2) + ".separator{border-left: 1px solid #CCCCCC;}%n" + 
							HTLUtilities.tabs(2) + ".primary{font-weight: bold;  font-size: 120%%;}%n" +
					   HTLUtilities.tabs(1) + "</style>%n";
		// ACHTUNG: hier kein String.format verwenden, sonst wird beispielsweise %% in % umgewandelt und bei nochmaligem (in der n�chsten Methode)String.format bewirkt das einzelne '%' einen Fehler
		// Beispiel: statt '120%' muss '120%%' (escaped) geschrieben werden, durch String.format w�rde aus '120%%' nun '120%' 
		return HTLUtilitiesExport.openHTMLBody(style, body);
	}
	
	/**
	 * Erstellt auf Basis der CSS-Styles {@code style} und dem Body {@code body} eine tempor�re HTML-Datei und �ffnet diese mit dem Standardexplorer 
	 * @param style der CSS-Style
	 * @param body der Body der HTML-Datei
	 * @return der Pfad der tempor�ren Datei
	 */
	public static String openHTMLBody(String style,
								      String body)
	{
		String html = "<!DOCTYPE html>%n" +
					  "<html>%n" +
					  "<head>%n%s</head>%n" +
					  "<body>%n%s</body>%n" +
					  "</html>";
		
//		String replacedStyle = String.format(style);
//		if (replacedStyle.contains("%"))
//			throw new IllegalArgumentException("Achtung: Ihr Style behinaltet ...");	

		body = body.replace("�", "&Auml;");
		body = body.replace("�", "&Ouml;");
		body = body.replace("�", "&Uuml;");
		body = body.replace("�", "&auml;");
		body = body.replace("�", "&ouml;");
		body = body.replace("�", "&uuml;");
		
		html = String.format(html, style, body);					
		
		return HTLUtilitiesExport.openHTML(String.format(html));
	}
	
	/**
	 * Speichert den String {@code html} in eine tempor�re Datei mit dem Erweiterung "html" und gibt den Pfad zur Datei zur�ck.
	 * @param html die vollst�ndige HTML-Datei 
	 * @return der Pfad der tempor�ren Datei
	 */
	public static String openHTML(String html)
	{
		return HTLUtilitiesExport.openContent(html, "html");
	}
	
	/**
	 * Speichert den String {@code xml} in eine tempor�re Datei mit dem Erweiterung "xml" und gibt den Pfad zur Datei zur�ck.
	 * @param xml der String 
	 * @return der Pfad zur tempor�ren Datei
	 */
	public static String openXML(String xml)
	{
		return HTLUtilitiesExport.openContent(xml, "xml");
	}
	
	/**
	 * Speichert den String {@code csv} in eine tempor�re Datei mit dem Erweiterung "txt" und gibt den Pfad zur Datei zur�ck.
	 * @param csv der String 
	 * @return der Pfad der tempor�ren Datei
	 */
	public static String openCSV(String csv)
	{
		return HTLUtilitiesExport.openCSV(csv, "txt");
	}
	
	/**
	 * Speichert den String {@code csv} in eine tempor�re Datei mit dem Erweiterung {@code extension} und gibt den Pfad zur Datei zur�ck.
	 * @param csv der String 
	 * @param extension die Erweiterung der tempor�ren Datei
	 * @return der Pfad der tempor�ren Datei
	 */
	public static String openCSV(String csv, String extension)
	{
		return HTLUtilitiesExport.openContent(csv, extension);
	}
	
	/**
	 * Speichert den String {@code content} in der Datei {@code fileName} und gibt den Pfad zur Datei zur�ck.
	 * @param fileName der Namen der Datei
	 * @param content der String
	 * @return der Pfad zur Datei
	 */
	public static String saveContent(String fileName, String content)
	{
		File file = new File(fileName);
		String path = HTLUtilitiesExport.saveContent(file, content);
		return path;
	}
	
	private static String saveContent(File file, String content)
	{
		String path = "";
		
		if (file != null)
		{
			path = file.getPath();
			
//			try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getPath()), StandardCharsets.UTF_8)))					
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file.getPath())))	// schreibt den Inhalt der Variable 'content' in die Datei
			{		
				bw.write(content);	
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return path;
	}
	
	/**
	 * >Ermittelt eine tempor�re Datei mit der Erweiterung {@code extension}
	 * @param extension die Erweiterung
	 * @return der Pfad zur Datei 
	 */
	private static File getTempFile(String extension)
	{
		File temp = null;
		
		try
		{
			temp = File.createTempFile("temp", "." + extension);							// legt eine tempor�re Datei mit dem Pr�fix (Vorsilbe) 'temp' an
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		} 
		
		return temp;
	}
	
	
	/**
	 * Speichert den Inhalt {@code content} in einer tempor�ren Datei mit der Erweiterung {@code extension} und �ffnet diese
	 * @param content der Inhalt
	 * @param extension die Erweiterung
	 * @return der Pfad zur Datei 
	 */
	private static String openContent(String content, String extension)
	{
		File temp = HTLUtilitiesExport.getTempFile(extension);
		String path = HTLUtilitiesExport.saveContent(temp, content);
		HTLUtilitiesExport.openFile(temp);									
		return path;
	}
	
	
	/**
	 * �ffnet die Datei {@code file}
	 * @param file die Datei
	 */
	private static void openFile(File file)
	{
		try
		{
			Desktop.getDesktop().browse(file.toURI());				// �ffnet die Datei mit einem Webbrowser
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
		
}
