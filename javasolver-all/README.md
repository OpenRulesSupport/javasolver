# Java Solver All

`javasolver-all` is a convenience POM that brings together Java Solver, JSR-331 scheduling, Log4j, and a selectable constraint or linear solver implementation. It contains no Java classes of its own.

## Requirements

- Java 21 or newer
- Maven 3.9 or newer

## Maven dependency

After version `3.0.0` is published to Maven Central, add:

```xml
<dependency>
  <groupId>com.javasolver</groupId>
  <artifactId>javasolver-all</artifactId>
  <version>3.0.0</version>
</dependency>
```

Constrainer is the default solver. Select another implementation with the Maven property `solver`, for example:

```shell
mvn verify -Dsolver=OJALGO
```

Supported values are `CONSTRAINER`, `SUGAR`, `CHOCO`, `MAXICP`, `JACOP`, `JSETL`, `SCIP`, `GLPK`, `CLP`, `COIN`, and `OJALGO`.

## Publish to Maven Central

MvnRepository is a search/index site, not a deployment repository. Publish to Maven Central through the [Central Publisher Portal](https://central.sonatype.com/); MvnRepository will index the released artifact afterward.

Before the first release:

1. Sign in to the Central Publisher Portal and verify that your account can publish the `com.javasolver` namespace.
2. Generate a Central user token and place it in `~/.m2/settings.xml` under the server ID `central`:

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

3. Install GnuPG, create or import the release signing key, publish its public key to a supported key server, and make sure `gpg` is on `PATH`.
4. Publish every `3.0.0` artifact referenced by this POM before publishing `javasolver-all`. Maven Central validates that release dependencies are resolvable.

Build, sign, and upload the POM:

```shell
mvn clean deploy -Prelease
```

The release profile signs the POM and uploads it for validation. It intentionally does not publish automatically. Review the validated deployment in the Central Publisher Portal and select **Publish**. A released version is immutable, so use a new version for every later change.

## License

Java Solver is licensed under the [GNU Lesser General Public License v3.0](../LICENSE).

## Links

- [Project site](https://javasolver.com/)
- [Source repository](https://github.com/OpenRulesSupport/javasolver)
- [Maven Central search](https://central.sonatype.com/search?q=g%3Acom.javasolver%20a%3Ajavasolver-all)
