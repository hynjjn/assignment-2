# Assignment 2 — Favorite Fruit Survey (JSP + CSS + JDBC, MVC)

An opinion-poll web app: vote for your favorite fruit and see live results as a
sorted bar chart. Built with the **MVC pattern** (for the up-to-30% bonus).

## Architecture

```
View        src/main/webapp/opinion.html        Home page (VOTE / OUTCOME links)
            src/main/webapp/opinion-poll.html    Vote form (radio buttons → POST /vote)
            src/main/webapp/WEB-INF/views/result.jsp   Bar-chart result (JSTL, no scriptlets)
            src/main/webapp/css/style.css

Controller  com.survey.controller.SurveyServlet  Shared: load + rank results, forward to view
            com.survey.controller.VoteServlet     POST /vote    → record vote, show result
            com.survey.controller.OutcomeServlet  GET  /outcome → show result only
            com.survey.controller.ResetServlet    GET  /reset   → re-run schema.sql, show result

Model       com.survey.model.Fruit               DTO
            com.survey.model.FruitDAO             All JDBC SQL for the fruit table
            com.survey.model.DBConnection         Connection factory (reads db.properties)
```

Requirements covered:
- **Reset** — the home page **RESET** link re-runs `sql/schema.sql` (bundled into
  the WAR on the classpath). It executes the *same* script as the manual
  `mysql < sql/schema.sql` setup, so the initial state has a single source of
  truth: no seed values are duplicated in Java.
- **Repeated voting** — `addVote` just increments the counter every call.
- **Sorted results** — `findAllOrderByVotesDesc` orders by votes DESC.
- **25% start** — each of the 4 fruits seeded with 1 vote (`sql/schema.sql`).

## Setup

1. **Create the database** (MySQL / MariaDB):
   ```bash
   mysql -u root -p < sql/schema.sql
   ```
2. **Set credentials** in `src/main/resources/db.properties` (default user `root`,
   empty password). Adjust to match your local server.
3. **Build the WAR:**
   ```bash
   mvn clean package
   ```
   Produces `target/opinion-poll.war`.
4. **Deploy** to Tomcat **9** (javax.servlet namespace) — drop the WAR into
   `webapps/`, or run via the IDE's Tomcat integration.
5. Open <http://localhost:8080/opinion-poll/> (or `/opinion-poll/opinion.html`).

> Note: built against the `javax.servlet` API, so use **Tomcat 9.x**.
> Tomcat 10+ uses the `jakarta.*` namespace and would need dependency changes.

## Flow

`opinion.html` → **VOTE** → `opinion-poll.html` → submit → `POST /vote`
→ `result.jsp` (highlights your choice).
`opinion.html` → **OUTCOME** → `GET /outcome` → `result.jsp` (no highlight).
