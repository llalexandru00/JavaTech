# JavaTech

## Lab 1

* https://profs.info.uaic.ro/~acf/tj/labs/lab_01.html

[Servletul](https://github.com/llalexandru00/JavaTech/blob/main/lab1/src/main/java/ro/uaic/info/jt/MainServlet.java) implementeaza metoda de get si este disponibil la /hello conform [web.xml](https://github.com/llalexandru00/JavaTech/blob/main/lab1/src/main/webapp/WEB-INF/web.xml). Servletul proceseaza requesturi ce contin cei 4 parametii: key, value, sync si mock. Daca datale nu sunt bine formatate, se raspunde prin SC_BAD_REQUEST.

Servletul contine un [repository](https://github.com/llalexandru00/JavaTech/blob/main/lab1/src/main/java/ro/uaic/info/jt/Repository.java) ce este repsonsabil pentru scrierea in fisier a cerintelor clientului. Accesul la acest repository este sincronizat sau nu (depinzand de parametrul sync). Ca raspuns la un request, se ofera lista de linii din fisierul repository-ului, sortate dupa cheie. In log se va afisa fiecare request cu datele aferente.

In afara de [clientul web](https://github.com/llalexandru00/JavaTech/blob/main/lab1/src/main/webapp/index.jsp), a fost implementat si un [client](https://github.com/llalexandru00/JavaTech/blob/main/lab1/async.js) cu requesturi aleatorii in NodeJS. De asemenea, pentru performanta, un [client](https://github.com/llalexandru00/JavaTech/blob/main/lab1/async2.js) full sync/full-async a fost tesat. Rezultatele analizei sunt gasite in [analiza.pdf](https://github.com/llalexandru00/JavaTech/blob/main/lab1/analiza.pdf).


## Lab 2

* https://profs.info.uaic.ro/~acf/tj/labs/lab_02.html

[Servletul Web controller](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/java/ro/uaic/info/mt2/WebController.java) implementeaza in spiritul MVC:
* metoda GET pentru a gestiona requesturi ce sunt trimise catre [input page](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/webapp/input.jsp). Aceste requesturi au ca atribute categoria preselectata (daca utilizatorul are un cookie ce sa specifice acest lucru) si multimea categoriilor stocate in baza de date. Mai mult, la nivel de sesiune se adauga un atribut pentru generarea de captcha. In sfarsit, pagina este construita cu JSTL si incorporeaza Bootstrap. 
* method POST pentru a gestiona requesturi ce sunt trimise catre [result page](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/webapp/result.jsp). Un request ofera parametrii pentru category, key si value. Daca category nu exista, acesta este initializat cu ajutorul unui atribut la nivel de aplicatie "default-category". De asemnea, captcha-ul este verificat si un cookie, ce contine alegerea categoriei, este adaugat. In final, un [record bean](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/java/ro/uaic/info/mt2/beans/Record.java) este creat si persistat in baza de date.

[Servletul Captcha](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/java/ro/uaic/info/mt2/CaptchaHelper.java) implementeaza metoda GET ce ofera o imagine captcha generata pe baza unui text stocat la nivel de sesiune.

Aplicatia contine de asemenea doua filtre web si un listener web:
* [Decorator de request](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/java/ro/uaic/info/mt2/decorators/RequestDecorator.java) ce adauga in logging toate requesturile primite.
* [Decorator de response](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/java/ro/uaic/info/mt2/decorators/ResponseDecorator.java) ce ataseaza raspunsului dat de catre aplicatie, prin intermediul unui response, un header si un footer.
* [Listenerul](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/java/ro/uaic/info/mt2/listeners/CoreWebListener.java) citeste un parametru de context si il adauga in scope-ul aplicatiei sub forma de atribut. Acest parametru de context este categoria default ce trebuie setata daca aceasta nu apare in requesturile POST.

In final, aplicatia fololseste o baza de date gestionata prin intermediul unui [layer de persistenta](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/java/ro/uaic/info/mt2/Persistence.java) ce foloseste Hibernate.


## Lab 3

* https://profs.info.uaic.ro/~acf/tj/labs/lab_03.html

Proiectul foloseste JSF impreuna cu mai multe bean-uri:
* [Language Bean](https://github.com/llalexandru00/JavaTech/blob/main/lab3/src/main/java/ro/uaic/info/mt3/beans/LanguageBean.java) este un bean la nivel de sesiune ce gestioneaza selectia limbii curente (locale). Proiectul suporta doua limbi: [engleza](https://github.com/llalexandru00/JavaTech/blob/main/lab3/src/main/resources/Messages.properties) si [romana](https://github.com/llalexandru00/JavaTech/blob/main/lab3/src/main/resources/Messages_ro_RO.properties).
* [Exam Bean](https://github.com/llalexandru00/JavaTech/blob/main/lab3/src/main/java/ro/uaic/info/mt3/beans/ExamBean.java) este un bean la nivel de request ce gestioneaza work-flow-ul legat de examene. Acesta agrega un model low-level pentru examene ce este persistat in baza din date prin Hibernate folostind [Exam](https://github.com/llalexandru00/JavaTech/blob/main/lab3/src/main/java/ro/uaic/info/mt3/model/Exam.java).
* [Student Bean](https://github.com/llalexandru00/JavaTech/blob/main/lab3/src/main/java/ro/uaic/info/mt3/beans/StudentBean.java) este un bean la nivel de request ce gestioneaza work-flow-ul legat de studenti. Acesta agrega un model low-level pentru studenti ce este peristat in baza de date prin Hibvernate folostind [Student](https://github.com/llalexandru00/JavaTech/blob/main/lab3/src/main/java/ro/uaic/info/mt3/model/Student.java). Relatia examene-studenti este many-to-many, motiv pentru care, fizic, baza de date contine si un tabel ce mapeaza id-uri de studenti la id-uri de examene.
* [Schedule Bean](https://github.com/llalexandru00/JavaTech/blob/main/lab3/src/main/java/ro/uaic/info/mt3/beans/ScheduleBean.java) este un bean la nivel de request ce gestioneaza componenta de grupare a examenelor pe zile. Acesta genereaza un model pentru componenta UI bazandu-se pe solutia problemei orarului rezolvata cu ajutorul SMT-solver-ului [Z3](https://github.com/Z3Prover/z3). [Z3 Schedule Resolver](https://github.com/llalexandru00/JavaTech/blob/main/lab3/src/main/java/ro/uaic/info/mt3/util/Z3ScheduleResolver.java) adauga in contextul Z3 constrangerile legate de examene sub forma de conditii logice. Pentru a optimiza numarul de zile, se aplica cautarea binara a numarul optim de zile folosind satisfiabilitatea formulei ca baza a functiei monotone suport. Modelul este extras doar dupa ce formula este satisfiabila cu numar minim de zile.

Pentru partea de UI, [PrimeFaces](https://www.primefaces.org/) a fost folosit: dataTable, datePicker, selectOneMenu, inputNumber etc. De asemenea, un converter a fost folosit pentru examene pentru a le gestiona intr-un selectManyCheckbox cu selectItems.
