# Busrouten-Service

- Ihre Aufgabe ist die Implementierung eines Domain-Service für Busrouten. Dieser Service sollte in der Lage sein, Informationen über Busrouten zu speichern und abzurufen. Jede Route sollte mindestens folgende Informationen enthalten:
  - Buskennung
  - Name des Busfahrers
  - Preis pro Kilometer
  - Durchschnittliche Geschwindigkeit
  - Route (z.B. von A nach B)

## Kernfunktionalität

- Hinzufügen einer Route
  - Möglichkeit, neue Routen für Busse hinzuzufügen
- Aktualisieren einer Route
  - Möglichkeit, Daten einer vorhandenen Route zu aktualisieren
- Löschen einer Route
  - Möglichkeit, eine Route aus der Datenbank zu löschen
- Abfragen von Routen
  - Möglichkeit, Routen anhand eines Start- und Endpunktes abzurufen
  - Filter-Optionen für günstigster Preis oder höchste Geschwindigkeit

## Aufgabenstellung

- Implementierung einer Microservice-basierten Applikation zur Verwaltung von Routen für Fortbewegungsmittel (Taxi, Boot, Flugzeug, Bus)
- Als „Frontend“ kommt Postman zum Einsatz
- Über die spezifizierten Kernfunktionalitäten hinausgehende Funktionalitäten sind ausdrücklich erlaubt und werden bei der Bewertung berücksichtigt

## Bewertungskriterien

- Service & Anwendung 70%
  - Funktionalität
  - Code Quality
  - Funktionierende Infrastruktur / CICD
  - Betrieb
  - Integrationtests
  - API-Doku
- Präsentation ("Produktpräsentation") 30%

## Technische Anforderungen

- Microservice-basierten Applikation (Spring Boot)
- Nutzung einer Datenbank (z.B. PostgreSQL)
- Bereitstellung einer API-Dokumentation (OpenAPI)
- Integration in eine bereitgestellte Infrastruktur mittels CI/CD
- Orchestrierung mittels Docker und Kubernetes
- Integrationtests

## Fragen?

Bei Fragen zur Aufgabenstellung wenden Sie sich bitte per Email an das Betreuerteam:

- David Dressler (david.dressler@abatplus.de)
- Kai Jachmann (kai.jachmann@abatplus.de)
- Pascal Bourguignon (pascal.bourguignon@abatplus.de)
- Steffen Schery (steffen.schery@abatplus.de)

# Kubernetes

## Zugang

Für die Vorlesung wurde ein Kubernetes Cluster mit seperatem Namespace für jeden Service erstellt. Um Zugang zum Namespace für diesen Service zu erhalten, muss die bereitgestellte [k8s_bus.config](./k8s_bus.config) mit Ihrem kubectl verwendet werden. Jede Gruppe kann hat nur auf ihren eigenen Namespace Zugriff.

## Ingress

Um die in Kubernetes laufenden Anwendungen von außen erreichen zu können, muss eine Kubernetes Ressource vom Typ `IngressRoute` angelegt werden. Diese kann beispielsweise so aussehen:

```yaml
apiVersion: traefik.containo.us/v1alpha1
kind: IngressRoute
metadata:
  name: bus-ingressroute
  namespace: bus
spec:
  entryPoints:
    - websecure
  routes:
    - kind: Rule
      match: Host(`bus.edu.smef.io`) # Domain ist vorgegeben
      middlewares:
        - name: cors
          namespace: bus
      services:
        - name: svc-location
          namespace: bus
          port: 80
  tls:
    certResolver: default
```

Weiteres Routing innerhalb des Namespaces sollte mittels Pfaden passieren (ggf. mit StripPrefix Middleware). Weitere Informationen und Beispiele sind in der [Dokumentation von Traefik](https://doc.traefik.io/traefik/routing/providers/kubernetes-crd/#kind-ingressroute) zu finden.

# Datenbank

Wie bei Kubernetes wird auch eine Postgres-Datenbank mit folgenden Zugangsdaten bereitgestellt:

```
Host/Port:  edu-db-exoscale-f3838a52-b0a0-4c28-918e-b5ffe8fa297b.a.aivencloud.com:21699
Db-Name:    group4_db
User:       group4
Passwort:   @dhAj;dyHN#k$j.6lN@a
```

# Dockerhub

Die Anwendungen sollen containerisiert gebaut werden. Diese Container müssen nach dem Build zwischengespeichert werden, um diese dann in Kubernetes laden zu können. Die Secrets, um von Github dahin zu pushen und aus dem Kubernetes-Cluster von dort laden zu können, sind in Kubernetes und den Repositories bereits hinterlegt. Zugriff aus den Github Workflows heraus ist mit den folgenden Values (die Yaml-Keys sind nur Platzhalter)

```yaml
exampleYamlKeys:
  image: ${{ vars.DOCKERHUB_REPO }}
  username: ${{ secrets.DOCKERHUB_USER }}
  password: ${{ secrets.DOCKERHUB_TOKEN }}
```

Um Images aus Dockerhub in Kubernetes zu laden muss das Attribut [imagePullSecrets](https://kubernetes.io/docs/tasks/configure-pod-container/pull-image-private-registry/#create-a-pod-that-uses-your-secret) im Deployment gesetzt werden. Bspw:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: private-image-test
  labels:
    app: private-image-test
spec:
  replicas: 1
  selector:
    matchLabels:
      app: private-image-test
  template:
    metadata:
      labels:
        app: private-image-test
    spec:
      containers:
        - name: private-image-test
          image: docker.io/my-private-image:v1.2.3
      imagePullSecrets:
        - name: sec-dockerhub # <-- Name beachten!
```
