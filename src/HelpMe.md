
# Meine Notizen

## 1. Schritte

- Clonen der Vorlage 
- Anpassen von persistence.xml
  - User und Passwort eintragen
  - persistence-unit name anpassen
  - Die javax.persistence.jdbc.url anpassen, d.h. "elga" durch Ihren Projektnamen (ZZ_NN_VN) ersetzen
- Anlegen einer neuen Klasse für eine Tabelle in der Datenbank
  - Zwei Annotation anlegen: @Entity und @Table
  - Für den Primärschlüssel benötigen wir zwei Annotation
    - @Id: kennzeichnet den Primärschlüssel
    - @GeneratedValue(strategy = GenerationType.IDENTITY): wenn AI (Auto Increment), dann GenerationType.IDENTITY auswählen
  - Für jede andere Property
    - @Column: um den Namen der Spalte in der Tabelle zu spezifizieren
    - @NotNull: bei Zahlen, wenn diese verpflichtend sind
    - @NotBlank: bei Strings, wenn diese verpflichtend sind
  - Implementieren der toString-Methode
  - Implementieren des Getters für den Primärschlüssel (oder alle Getter implementieren)
  - Implementieren der Methoden hashCode und equals

## Entity implementieren 
- Properties
  - alle relevanten
  - keine Fremdschlüssel!
  - Master-Detail-Beziehung
- Standardkonstruktor
  - "Später" tritt ein Fehler auf, nachdem der 1. Nicht-Standardkonstruktur implementiert wurde
- Getter und Setter
- equals und hashCode implementieren
  - nicht generieren lassen!
  - Vorlage von Hospital adaptieren

## Implementierung einer Master-Detail-Beziehung (zwischen HOSPITAL und DEPARTMENT)

- Änderungen in der Master-Klasse
  - Property für die Details: HashSet (ist eine Menge)
    - private Set<Department> departments = new HashSet<>();
  - Annotation @OneToMany eintragen
    - mappedBy: ist der Variablenname der korrespondierenden Property in der Detail-Klasse
    - fetch
      - LAZY: bedeutet, dass die Daten (z.B. Departments) werden automatisch nachgeladen, wenn der 1. Zugriff auf diese durchgeführt wird
      - EAGER: bedeutet, dass die Daten sofort, wenn der Master instanziiert wird, geladen werden
      - Standard: LAZY
    - cascade: 
  - Getter für die Property anlegen
- Änderungen in der Detail-Klasse
  - Property für den Master
  - Annotation @ManyToOne eintragen
    - fetch
  - Annotation @JoinColumn einfügen
    - name: Name des Fremdschlüssels (HOSPITAL_ID)
  - Getter für die Property anlegen
- Fragestellung: wie viele SQL-Statements werden abgesetzt, 
  wenn wird alle Hospitals laden (fetch: LAZY) und die Departments ausgeben?
  - SELECT n+1 Problem: 
    - bedeutet, dass 1 SQL-Statement für die Masterdatensätze benötigt wird und
    - bedeutet, dass n SQL-Statements für die Detaildatensätze benötigt werden.
    - Bespiel: 1 SQL-Statement für alle Hospitals und n (13) SQL-Statement für alle Departments der Hospitals
    - **Lösung**: MGIN5

## Fehlermeldungen

- Access denied for user '...':  
  **Lösung**: Username falsch oder Passwort falsch
- ERROR Table 'elga.HOSPITAL1' doesn't exist  
  **Lösung**: in @Table den Namen der Tabelle richtig schreiben
- ERROR Unknown column 'hospital0_.CD_ID_CARRIER' in 'field list'  
  **Lösung**: Feldname richtig stellen (statt CD muss CS sein)
- Unable to locate persister: at.htlklu.elga.model.Department  
  **Lösung**: in Persistence.xml muss das "Department" eingetragen werden
- 'Basic' attribute type should not be a container
  **Lösungen**: wenn beide Seiten der Master-Detail-Beziehung implementiert wurden

# Select-Insert-Update-Delete
- Master
  - Beispiel: Doctor (fertig)
- Detail
  - Beispiel: DoctorHospital (to do)
  - Fremdschlüssel
    - DOCTOR_ID
    - HOSPITAL_ID
  - Implementierung (bedeutet, dass wir eine Anstellung angelegen möchten)
    - In welcher KA? Werden wir gleich wählen.
    - Für welchen Doctor? Wir legen einen Doctor an.
    - Konstruktor für DoctorHospital implementieren
        - Nicht relevante Spalten (systeminterne Spalten): CREATED_BY, DATE_CREATED
          - Primärschlüssel (ID)
        - Verpflichtende Spalten: HOSPITAL_ID, DOCTOR_ID, DATE_FROM
        - Optional Spalten: DATE_TO, INCOME
        - Regel: Fremdschlüssel werden nicht berücksichtigt
        - Alle Attribute: DATE_FROM, DATE_TO, INCOME
        - Alle verpfl. Attribute: DATE_FROM
          - Defaultwerte: 1.1.2100, 0
    - Standardkonstruktor implementieren 

## ToDos
- Implementierung der folgenden Entities
  - Doctor
  - DoctorHospital
  - Patient
  - Address
  - Region
- Folgende Punkte beachten
  - Java Naming Convention
  - keine Fremdschlüssel implementieren
- 

## Fehler im Schema "ZZ_NN_VN" (SELECT * FROM AdminDb.V_EXERCISE_MY_SCHEMA1)

- Rechtschreibfehler in Tabellen und Spalten.  
  **Lösung**: Kritisch durchlesen (auch andere Schüler/innen bitten) und korrigieren.
- Falscher Datentyp bei der ID, z.B. VARCHAR statt INT.  
  **Lösung**: Den Datentyp bei der ID ändern.
- Kein künstlicher Schlüssel (Surrugatkey) vorhanden.   
  **Lösung**: Eine künstliche ID bei der Tabelle einfügen.
- Auto-Increment bei der ID nicht aktiviert.  
  **Lösung**: ERD nochmals ...
- Unique Index bei der ID aktiviert.   
  **Lösung**: Das Häkchen "UQ" bei der ID deaktivieren. 

