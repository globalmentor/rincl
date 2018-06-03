# Rincl ResourceBundle

Rincl implementation based on Java resource bundles.

This artifact only supplies a Rincl implementation. To have this implementation automatically installed as a Csar default concern, you must include `io.rincl:rincle-resourcebundle-provider` as a dependency.

## Download

Rincl ResourceBundle is available in the [Maven Central Repository](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22io.rincl%22%20AND%20a%3A%22rincl-resourcebundle%22).

## Issues

Issues tracked by [JIRA](https://globalmentor.atlassian.net/projects/RINCL).

## Changelog

- 0.8.0:
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
