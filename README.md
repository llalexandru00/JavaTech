# JavaTech

## Lab 1

* https://profs.info.uaic.ro/~acf/tj/labs/lab_01.html

[Servletul](https://github.com/llalexandru00/JavaTech/blob/main/lab1/src/main/java/ro/uaic/info/jt/MainServlet.java) implementeaza metoda de get si este disponibil la /hello conform [web.xml](https://github.com/llalexandru00/JavaTech/blob/main/lab1/src/main/webapp/WEB-INF/web.xml). Servletul proceseaza requesturi ce contin cei 4 parametii: key, value, sync si mock. Daca datale nu sunt bine formatate, se raspunde prin SC_BAD_REQUEST.

Servletul contine un [repository](https://github.com/llalexandru00/JavaTech/blob/main/lab1/src/main/java/ro/uaic/info/jt/Repository.java) ce este repsonsabil pentru scrierea in fisier a cerintelor clientului. Accesul la acest repository este sincronizat sau nu (depinzand de parametrul sync). Ca raspuns la un request, se ofera lista de linii din fisierul repository-ului, sortate dupa cheie. In log se va afisa fiecare request cu datele aferente.

In afara de [clientul web](https://github.com/llalexandru00/JavaTech/blob/main/lab1/src/main/webapp/index.jsp), a fost implementat si un [client](https://github.com/llalexandru00/JavaTech/blob/main/lab1/async.js) cu requesturi aleatorii in NodeJS. De asemenea, pentru performanta, un [client](https://github.com/llalexandru00/JavaTech/blob/main/lab1/async2.js) full sync/full-async a fost tesat. Rezultatele analizei sunt gasite in [analiza.pdf](https://github.com/llalexandru00/JavaTech/blob/main/lab1/analiza.pdf).


## Lab 2

* https://profs.info.uaic.ro/~acf/tj/labs/lab_02.html

[Servletul Web controller](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/java/ro/uaic/info/mt2/WebController.java) implementeaza in spiritul MVC:
* metoda GET pentru a gestiona requesturi ce vor fi trimise catre [input page](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/webapp/input.jsp). Aceste requesturi vor avea ca atribute categoria preselectata (daca utilizatorul are un cookie ce sa specifice acest lucru) si multimea categoriilor stocate in baza de date. Mai mult, la nivel de sesiune se va adauga un atribut pentru generarea de captcha. In sfarsit, pagina va fi construita cu JSTL si va incorpora Bootstrap. 
* method POST pentru a gestiona requesturi ce vor fi trimise catre [result page](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/webapp/result.jsp). Un request va oferi parametrii pentru category, key si value. Daca category nu exista, acesta va fi initializat cu ajutorul unui atribut la nivel de aplicatie "default-category". De asemnea, captcha-ul va fi verificat si un cookie ce contine alegerea categoriei va fi adaugat. In final un [record bean](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/java/ro/uaic/info/mt2/beans/Record.java) va fi creat si persistat in baza de date.

[Servletul Captcha](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/java/ro/uaic/info/mt2/CaptchaHelper.java) implementeaza metoda GET ce ofera o imagine captcha generata pe baza unui text stocat la nivel de sesiune.

Aplicatia contine de asemenea doua filtre web si un listener web:
* [Decorator de request](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/java/ro/uaic/info/mt2/decorators/RequestDecorator.java) ce va adauga in logging toate requesturile primite.
* [Decorator de response](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/java/ro/uaic/info/mt2/decorators/ResponseDecorator.java) ce va atasa raspunsului dat de catre aplicatie, prin intermediul unui response, un header si un footer.
* [Listenerul](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/java/ro/uaic/info/mt2/listeners/CoreWebListener.java) va citi un parametru de context si il va adauga in scope-ul aplicatiei sub forma de atribut. Acest parametru de context este categoria default ce trebuie setata daca aceasta nu apare in requesturile POST.

In final, aplicatia fololseste o baza de date gestionata prin intermediul unui [layer de persistenta](https://github.com/llalexandru00/JavaTech/blob/main/lab2/src/main/java/ro/uaic/info/mt2/Persistence.java) ce foloseste Hibernate.
