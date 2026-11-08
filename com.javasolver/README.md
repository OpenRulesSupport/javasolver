# Java Solver

Java Solver is a small Java API for modeling and solving optimization problems with JSR-331-compatible constraint and linear solvers.

## Requirements

- Java 21 or newer
- Maven 3.9 or newer
- A JSR-331 implementation at runtime

## Maven dependency

After version `3.0.0` is published to Maven Central, add:

```xml
<dependency>
  <groupId>com.javasolver</groupId>
  <artifactId>javasolver</artifactId>
  <version>3.0.0</version>
</dependency>
```

This library depends on `com.javasolver:jsr331:3.0.0`, which must be available from Maven Central before this artifact can be consumed from a clean environment.

## Build

```shell
mvn clean verify
```

## Publish to Maven Central

MvnRepository is a search/index site, not a deployment repository. Publish to Maven Central through the [Central Publisher Portal](https://central.sonatype.com/); MvnRepository will index the released artifact afterward.

Before the first release:

1. Sign in to the Central Publisher Portal and verify that your account can publish the `com.javasolver` namespace.
2. Generate a Central user token.
3. Configure that token in `~/.m2/settings.xml` under the server ID `central`:

```xml
<settings>
  <servers>
    <server>
      <id>central</id>
      <username>YOUR_TOKEN_USERNAME</username>
      <password>YOUR_TOKEN_PASSWORD</password>
    </server>
  </servers>
</settings>
```

4. Install GnuPG, create or import the release signing key, publish its public key to a supported key server, and make sure `gpg` is on `PATH`.
5. Publish `com.javasolver:jsr331:3.0.0` and any other release dependencies first.

Build, sign, and upload the release bundle:

```shell
mvn clean deploy -Prelease
```

The release profile attaches source and Javadoc JARs, signs all artifacts, and uploads them for validation. It intentionally does not publish automatically. Review the validated deployment in the Central Publisher Portal and select **Publish**. A released version is immutable, so use a new version for every later change.

## License

Java Solver is licensed under the [GNU Lesser General Public License v3.0](../LICENSE).

## Links

- [Project site](https://javasolver.com/)
- [Source repository](https://github.com/OpenRulesSupport/javasolver)
- [Maven Central search](https://central.sonatype.com/search?q=g%3Acom.javasolver%20a%3Ajavasolver)
