package io.github.beast3.migration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Single-shot fetch of {@code maven-metadata.xml} from Maven Central for a
 * package. Non-fatal: any error becomes a diagnostic string on the report.
 */
public final class MavenCentralProbe {

    private static final String BASE = "https://repo1.maven.org/maven2";

    private static final Pattern RELEASE = Pattern.compile(
            "<release>([^<]+)</release>");
    private static final Pattern LATEST = Pattern.compile(
            "<latest>([^<]+)</latest>");

    private final HttpClient client;

    public MavenCentralProbe() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public void probe(Report report) {
        if (!report.entry.hasMavenCoords()) return;

        String url = BASE + "/"
                + report.entry.mavenGroupId().replace('.', '/') + "/"
                + report.entry.mavenArtifactId() + "/maven-metadata.xml";

        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(15))
                    .header("Accept", "application/xml")
                    .GET()
                    .build();
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() == 404) {
                report.mavenCentralError = "not published (404)";
                return;
            }
            if (resp.statusCode() != 200) {
                report.mavenCentralError = "HTTP " + resp.statusCode();
                return;
            }
            String body = resp.body();
            Matcher rel = RELEASE.matcher(body);
            if (rel.find()) {
                report.mavenCentralLatest = rel.group(1).trim();
                return;
            }
            Matcher lat = LATEST.matcher(body);
            if (lat.find()) {
                report.mavenCentralLatest = lat.group(1).trim();
                return;
            }
            report.mavenCentralError = "no <release>/<latest> in metadata";
        } catch (Exception e) {
            report.mavenCentralError = e.getClass().getSimpleName() + ": " + e.getMessage();
        }
    }
}
