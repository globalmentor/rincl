# Rincl

The Resource I18n Concern Library (Rincl) facilitates internationalization by providing access to localization resources via [Csar](http://csar.io).

Rincl uses the Csar `ConcernProvider` mechanism, so that an application can have access to a globally configured default Rincl implementation simply by including that implementation's dependency. For example merely including the dependency `io.rincl:rincl-resourcebundle:x.x.x` will automatically provide resources from resource bundle property file lookup. Classes desiring resource access may then then implement `Rincled` for simplified retrieval of Resources. 

More complex configuration may be using `Rincl.setDefaultResourceI18nConcern(ResourceI18nConcern)` with the concern of choice, as in the following example:

	Rincl.setDefaultResourceI18nConcern(new MyResourceI18nConcern());

## Download

Rincl is available in the Maven Central Repository in group [io.rincl](http://search.maven.org/#search|ga|1|g%3A%22io.rincl%22).

## Changelog

- 0.6.0: First release.
