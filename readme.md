# Rincl

The _Resource I18n Concern Library_ ([**Rincl**](http://rincl.io/)) facilitates internationalization by providing access to localization resources via [Csar](http://csar.io).

Rincl uses the Csar `ConcernProvider` mechanism, so that an application can have access to a globally configured default Rincl implementation simply by including that implementation's dependency. For example merely including the dependency `io.rincl:rincl-resourcebundle-provider:x.x.x` will automatically provide resources from resource bundle property file lookup. Classes desiring resource access may then then implement `Rincled` for simplified retrieval of `Resources`. 

More complex configuration may be using `Rincl.setDefaultResourceI18nConcern(ResourceI18nConcern)` with the concern of choice, as in the following example:

```java
Rincl.setDefaultResourceI18nConcern(new MyResourceI18nConcern());
```

The `Rincl` class also provides methods for retrieving and setting the locale, with improvements over the native Java approach.

## Quick Start

An application can easily integrate Rincl resource bundle support with just a few steps:

1. Include the dependency `io.rincl:rincl-resourcebundle-provider:x.x.x`. _This automatically includes the dependencies `io.rincl:rincl-resourcebundle:x.x.x` and `io.rincl:rincl:x.x.x`, and registers support for resource bundles._
2. (optional) Set the preferred locale using `Rincl.setLocale(Locale locale)`.
3. Have `MyClass` implement the mixin interface `Rincled`.
4. Store resources in `MyClass.properties` or `MyClass_xx.properties`, etc. according to the desired locale.
5. Inside `MyClass` call `getResources().getXXX(String resourceKey)` to retrieve the type of resource you desire.

Note: If you do not want to implement `Rincled`, you can access resources for any class using `Rincl.getResourceI18nConcern(MyClass.class).getResources().getXXX(String resourceKey)`. 

Other Rincl implementations in addition to resource bundles will be available in the future.

## Download

Rincl is available in the Maven Central Repository in group [io.rincl](https://search.maven.org/#search|ga|1|g%3A%22io.rincl%22).

## Issues

Issues tracked by [JIRA](https://globalmentor.atlassian.net/projects/RINCL).

## Changelog

- 0.7.1:
	* [RINCL-14](https://globalmentor.atlassian.net/browse/RINCL-14): Parent resources fallback for strings drops format arguments.
	* [RINCL-13](https://globalmentor.atlassian.net/browse/RINCL-13): Add ability to look up resources for actual class instances.
	* [RINCL-10](https://globalmentor.atlassian.net/browse/RINCL-10): Add support to request messages from a specific locale.
	* [RINCL-9](https://globalmentor.atlassian.net/browse/RINCL-9): Support additional resource types.
	* [RINCL-4](https://globalmentor.atlassian.net/browse/RINCL-4): Standard way for providing parent/fallback resources.
- 0.7.0:
	* [RINCL-6](https://globalmentor.atlassian.net/browse/RINCL-6): Add Rincl convenience method for retrieving resources statically.
	* [RINCL-5](https://globalmentor.atlassian.net/browse/RINCL-5): Add default empty resources for unconfigured uses.
- 0.6.0: First release.
