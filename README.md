# JavaTech

## Lab 1

* https://profs.info.uaic.ro/~acf/tj/labs/lab_01.html

[Servletul](https://github.com/llalexandru00/JavaTech/blob/main/src/main/java/ro/uaic/info/jt/MainServlet.java) implementeaza metoda de get si este disponibil la /hello conform [web.xml](https://github.com/llalexandru00/JavaTech/blob/main/src/main/webapp/WEB-INF/web.xml). Servletul proceseaza requesturi ce contin cei 4 parametii: key, value, sync si mock. Daca datale nu sunt bine formatate, se raspunde prin SC_BAD_REQUEST.

Servletul contine un [repository](https://github.com/llalexandru00/JavaTech/blob/main/src/main/java/ro/uaic/info/jt/Repository.java) ce este repsonsabil pentru scrierea in fisier a cerintelor clientului. Accesul la acest repository este sincronizat sau nu (depinzand de parametrul sync). Ca raspuns la un request, se ofera lista de linii din fisierul repository-ului, sortate dupa cheie. In log se va afisa fiecare request cu datele aferente.

In afara de [clientul web](https://github.com/llalexandru00/JavaTech/blob/main/src/main/webapp/index.jsp), a fost implementat si un [client](https://github.com/llalexandru00/JavaTech/blob/main/async.js) cu requesturi aleatorii in NodeJS. De asemenea, pentru performanta, un [client](https://github.com/llalexandru00/JavaTech/blob/main/async2.js) full sync/full-async a fost tesat. Rezultatele analizei sunt gasite in [analiza.pdf](https://github.com/llalexandru00/JavaTech/blob/main/analiza.pdf).
