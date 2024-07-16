# Conversor_Divisas
Challege || Calculadora de equivalencia de divisas


```java
public Optional<String> get(String uri)
{
    HttpRequest request = HttpRequest
            .newBuilder()
            .GET()
            .uri(URI.create(uri))
            .timeout(Duration.ofSeconds(10))
            .header("Content-Type", "application/json")
            .build();

    try
    {
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
        if (response.statusCode() == 200)
            return Optional.of(response.body());
        else
        {
            System.err.println("Error: " + response.statusCode() + " - " + response.body());
            return Optional.empty();
        }
    } catch (IOException | InterruptedException e)
    {
        System.err.println("Exception: " + e.getMessage());
        return Optional.empty();
    }
}
```
