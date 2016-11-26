# Rincl ResourceBundle

Rincl implementation based on Java resource bundles.

This repository has the capability to produce two artifacts:

- `io.rincl:rincle-resourcebundle`: (default) Rincl implementation based on Java resource bundles.
- `io.rincl:rincle-resourcebundle-provider`: Csar provider for Rincl implementation based on Java resource bundles; includes `io.rincl:rincle-resourcebundle` as a dependency.

## Download

Rincl ResourceBundle is available in the [Maven Central Repository](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22io.rincl%22%20AND%20a%3A%22rincl-resourcebundle%22).

## Issues

Issues tracked by [JIRA](https://globalmentor.atlassian.net/browse/RINCL).

## Build

The standard Maven build only creates the `rincl-resourcebundle` artifacts. The files necessary for the `rincl-resourcebundle-provider` artifacts are contained in this same repository, so as not to maintain a separate repository just for a single provider file. To build the `rincl-resourcebundle-provider` artifacts, separately clean and build using `provider-pom.xml`:

    mvn clean install -f provider-pom.xml

**You _must_ include the `clean` lifecycle so that the resulting artifacts do not include the source code.**

## Changelog

- 0.7.2:
	* [RINCL-16](https://globalmentor.atlassian.net/browse/RINCL-16): Add separate concern provider dependency.
	* [RINCL-15](https://globalmentor.atlassian.net/browse/RINCL-15): Improve fallback configuration for resource bundles.
- 0.7.1:
	* [RINCL-4](https://globalmentor.atlassian.net/browse/RINCL-4): Standard way for providing parent/fallback resources.
- 0.7.0:
	* [RINCL-8](https://globalmentor.atlassian.net/browse/RINCL-8): Removed `ResourceBundleResourceI18nConcern` singleton instance and provided convenience constructors.
	* [RINCL-7](https://globalmentor.atlassian.net/browse/RINCL-7): Make parent resource resolution more efficient by not relying on try/catch.
	* [RINCL-3](https://globalmentor.atlassian.net/browse/RINCL-3): Add support for UTF-8, UTF-16, and UTF-32 in .properties files.
	* [RINCL-2](https://globalmentor.atlassian.net/browse/RINCL-2): Add support for XML properties resource bundles.
	* [RINCL-1](https://globalmentor.atlassian.net/browse/RINCL-1): Add plugin support for loading custom resource bundle file types.
- 0.6.0: First release.
