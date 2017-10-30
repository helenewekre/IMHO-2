# Test af server
- *Opdateret d. 29/10/2017 kl. 17:18*
- *Server på Master testet: OK!*

## Intro
- Før I kan teste server skal I have have et værktøj installeret, som kan agere klient. I gennemgangen vil *Advanced REST client* - en udvidelse til Chrome blive anvendt. Af andre kan *Postman* anbefales. 
- Det anbefales yderligere at anvende at værktøj, som kan administrere database - eventuelt *SequelPro* eller *Workbench*. Denne kan I starte op med det samme. 
- Det kan være forvirrende at følge guiden, især med tal - har siddet med disse test fejl i flere timer. Det handler hele tiden om at kigge tilbage til jeres database, og se om jeres tal giver mening. Eksempelvis giver det ikke mening at sætte course id til 10000, når vi kun har 1-4 id i courses. 
- Sidst, husk at starte jeres server med *Tomcat*. 

### Kryptering 
- Når I tester skal I overveje, om det der bliver returneret skal være krypteret eller ikke.
1. Navigér til `IMHO-2/src/main/java/resources/config.json`
2. Sæt kryptering til/fra ved at skrive `true` eller `false`. 

## UserEndpoint
### signup
- Det første i en naturlig use case er, at en bruger opretter sig. Når en bruger opretter sig, kommer der naturligvis en ny bruger i databasen. Herfra starter guiden i post-værktøjet.
1. Ændre **Request URL** til `http://localhost:8080/api/user/signup`
2. Ændre **Method** til `POST`
3. Under **Body** fanen i **Body content type** vælges `application/json`
4. I tekstfeltet skrives `{"username":"MitBrugernavn","password":"MinKode"}`
5. Sidst, tryk **Send**

### login
- Det næste naturlige step i use case vil være, at man logger ind med ens oplysninger. Når man logger ind, får brugeren en unik token, som bliver slettet igen, når brugeren logger ud. 
1. Ændre **Request URL** til `http://localhost:8080/api/user/login`
2. I tekstfeltet skal intet ændres
3. Sidst, tryk **Send**

### profile
- Metoden gør det muligt at se sin egne oplysninger. Herfra skal tokens anvendes. 
1. Åbn jeres database værktøj. Naviger til **User** tabellen og find id'et på den nyoprettede bruger. 
2. Naviger til **tokens** tabellen og find den token der tilhører selvsamme bruger id. Kopier denne. 
3. Ændre **Method** til **GET** 
4. Naviger til **Headers** i jeres post-værktøj. Tryk **ADD HEADER**. I **Header name** skriv da `authorization`. I **Header value** indsæt da token. 
5. Ændre **Request URL** til `http://localhost:8080/api/user/myuser`
6. Sidst, tryk **Send**

## CourseEndpoint
### loadCourses
- Metoden gør det muligt at se alle fag. 
0. Husk nu, at det token i headeren skal forblive, ellers har i ikke rettigheder. 
1. Ændre **Request URL** til `http://localhost:8080/api/course`
2. Sidst, tryk **Send**

## QuizEndpont
### loadQuizzes
- Metoden gør det muligt at se alle quiz i databasen 
1. Ændre **Request URL** til `http://localhost:8080/api/quiz/2`
2. Sidst, tryk **Send**
2. Bemærk, at tallet til sidst i URL angiver fagets ID og ikke quizzens, hvorfor i kan få tomme arrays tilbage. 

### createQuiz
- Metoden gør det muligt at lave en ny quiz. 
1. Åbn dit database værktøj. Naviger til **user** tabellen. Lokaliser den nuværende bruger, og ændrer type til **1**. Hvis ikke, har brugeren ikke admin-rettigheder til at lave ny quiz.
2. Ændre **Method** til **POST** 
3. Ændre **Request URL** til `http://localhost:8080/api/quiz`
4. I body skriv da `{"createdBy":"Andy Tran","questionCount":10,"quizTitle":"HejsaHejsa","quizDescription":"tester","courseId":2}`
5. Sidst, tryk **Send**

### deleteQuiz
- Metode til at slette en quiz
1. Ændre **Method** til **DELETE**
2. Ændre **Request URL** til `http://localhost:8080/api/quiz/2`
3. Sidst, tryk **Send**
3. Bemærk, at tallet til sidst i URL angiver ID på den quiz, som i sletter. Kan være fordel at tjekke i jeres database, om det ID overhovedet findes - hvis ikke, returnerer den fejl. 

## QuestionEndpoint
### loadQuestion
- Metode til at indlæse alle spørgsmål relateret til en quiz
1. Ændre **Method** til **GET**
2. Ændre **Request URL** til `http://localhost:8080/api/question/2`
3. Sidst, tryk **Send**
3. Bemærk, at tallet til sidst i URL angiver quiz ID, og ikke question ID. Kan være fordel at tjekke i jeres databse, om det ID overhovedet findes - hvis ikke, returnerer den fejl. 

### createQuestion
- Metode til at lave en ny quiz
1. Ændre **Method** til **POST**
2. Ændre **Request URL** til `http://localhost:8080/api/question`
3. I body skriv da `{"question":"Hvad er defftte?","questionToQuizId":1}`
4. Sidst, tryk **Send**
4. Bemærk, at tallet **quizIdQuiz** betegner en quiz som spørgsmålet skal høre under. Hvis id'et ikke findes i databasen får i en fejl. 

## OptionEndpoint
### loadOptions
- Metode til at hente alle svarmuligheder til et spørgsmål
1. Ændre **Method** til **GET**
2. Ændre **Request URL** til `http://localhost:8080/api/option/1`
3. Sidst, tryk **Send**
4. Bemærk, at tallet til sidst i URL angiver spørgsmålets ID. Hvis dette spørgsmåls ID ikke findes får i en fejl. 

### createOption
- Metode til at lave en ny valgmulighed
1. Ændre **Method** til **POST**
2. Ændre **Request URL** til `http://localhost:8080/api/option`
3. I body skriv da `{"option":"Valgmulighed1","optionToQuestionId":1,"isCorrect":0}`
4. Sidst, tryk **Send**
4. Bemærk, at **questionIdQuestion** er ID'et på spørgsmålet. Hvis spørgsmåls-ID ikke findes, får i en fejl. Derudover kan **isCorrect** kun differentiere mellem 0 og 1. 

### deleteAnswer
- Metode til at slette et svar
1. Ændre **Method** til **DELETE**
2. Ændre **Request URL** til `http://localhost:8080/api/option/1`
3. Sidst, tryk **Send**
3. Tjek i databasen under **Answer** tabellen om der er nogle svar - hvis ikke får i fejl. 
4. Bemærk, at det sidste tal i URL betegner brugerens ID (skal laves om)

## ResultEndpoint
### getUserScore
- Metode til at få mine point udprintet
1. Ændre **Method** til **GET**
2. Ændre **Request URL** til `http://localhost:8080/api/result/1/1`
3. Naviger til databaseværktøjet. Åbn **User** tabellen, og ændrer din nuværende bruger type til **2**. 
4. Naviger tilbage til **POST** værktøjet. 
5. Sidst, tryk **Send**
5. **OBS OBS OBS**: Her er det særligt vigtigt at skelne mellem tallene i URL. Det første tal fortæller om quiz id. Det quiz ID skal findes i databasen, ellers fejler den. Derudover fortæller det sidste tal om den bruger, vis token du har i headeren. Derfor er det vigtigt, at hans brugertype er 2. I detet tilfælde er brugeren med id 1 en brugertype 2. Andet virker ikke. 



