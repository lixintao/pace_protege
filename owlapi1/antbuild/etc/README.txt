3.5.1 10 September 2014

Bugs:
#278 AutoIRIMapper is not namespace aware
3.5.1 OSGi distro
#275 DL Syntax rendering of disjoint classes missing comma
#277 Direct imports result not updated correctly after manual load 
#254 OWLAsymmetricObjectPropertyAxiom not rendered in DL Syntax
#253 StructuralReasoner.getSameIndividuals does not behave as advertised
Reintroduced OBO parser for 1.2 from OWLAPITOOLS for backwards compatibility
#251 OBO parser for 1.4 cannot handle some 1.2 ontologies
More misbehaving comparators fixed
OBO: missing [] for synonyms
OBO: incomplete OBO writes. Always flush the buffer after writing
#217 Manchester abstract renderer in 3.5+ swallows exceptions but prints stacktrace
#207 OBO parser fails when a comment is in the date tag
#198 A SubClassOf B will not parse
#146 An IRI with a space: hard parse error or %20 escape?
#187 RDF/XML files with a UTF-8 BOM fail to parse

Features:
Use Trove collections for ontology internals
Guava 17.0 dependency added
OBO updated to 5.5 release
Don't cache any literals in OWLDataFactoryInternalsImpl.
#190 OWLLiteralImpl improvements on memory footprint
do not actually open a connection to verify if an URL points to an ontology


3.5.0  12 April 2014

Bugs:
#150 Property type inference fixes
#143 Role Chain compareTo skips every other element of the role chain.
Switching data factory IllegalArgumentExceptions to NullPointerExceptions #131
Fix Are properties generally defined to be copies or immutable #143
Bug in SWRL body ordering: the parsing handler uses HashSets.
#36 trying to read an ontology from a web page url hangs
Manchester syntax renderer (various bugs)
#121 set turtle default prefix slash aware
#128 Turtle parser fails to parse literal with escaped double quote right at the end of the literal
#109 Datatypes on literals used in annotations cause profile violations
#117 ManchesterOWLSyntaxFrameRenderer adds too many brackets
#114 Escaping characters in default OBOParser 3.4.10 is fixed
#116 import statement erroneously names anonymous ontology
#47 Input with character = 0240 (octal) should fail
annotations on datatype definitin axioms fixed in OWL/XML
Avoid adding inferred TransitiveObjectProperty assertions if transitivity is only vacuously true.
#53 No warning for missing xml:base causes unexpected import
#90 DTD processing slows down XML parse
#88 imported turtle graph does not parse correctly
#87 URIs with genid string are considered blank node ids
#71 Misleading message on resolving illegal IRI
#83 RDFTranslator: ID collision for anonymous individuals
#78 functional parser swallows ontology already exists exception
#74 RDF/XML invalid in ManchesterOWLSyntaxParserTestCase
Fix swrl-built-in atom rendering

Features:
Implements Add a GWT compatible method to get the pattern for an OWL2Datatype as a raw string #152
Implement Consider retries when loading from URLs #66 with hardcoded 5 retries and increasing timeout limit.
Added some micro interfaces for accessing ontologies and applying changes to ontologies.


3.4.10 18 January 2014

Bugs:
Fixes #72 Manchester syntax roundtrip of doubles and SWRL rules fails
Fixes #24 Manchester Syntax writer generates unparsable file
simple renderer writes duplicate annotations
Optimised functional renderer and prefix manager
Fix #63 null pointer exception loading an ontology and fix #64 empty ontology returned
Fixes #63 OBO Ontology Parsing throws NullPointer
updated OSGi settings for oboformat, module name, dependencies
fix bundle configuration for oboformat

Features:
Improved feature 32 implementation (simple SWRL variables rendering)
javadoc warnings and errors removed to allow building with Java 8
refactored OWLAxiomVisitorExAdapter for general use
performance improvement for rdf/xml
Renamed variables, added explicit flush. Fixes #67 slow functional sytnax renderer on Java 6
default, final and abstract classes changed to allow interface verification
add oraclejdk8 early access build to travis
update oboformat class names to sync with oboformat project
make OBOFormatException extend OWLParserException

3.4.9 25 November 2013
Features:
Added default methods and return values for a few visitor adapters
#57 Reimplement NodeID so that it doesn't use an inner class
#56 parse things like xsd:string easily

3.4.8 02 November 2013

Bug fixes:
Fixes #40 Fixes #7 RDFGraph.getSortedTriplesForSubject throws exception in Java7 Refactored TripleComparator into RDFTriple and RDFNode as Comparable
DLQueryExample did not print the StringBuilder with the answers
Fixes #50 Null anonymous individual parsing ObjectHasValue from OWL/XML
Fixes SF 313 Man Syntax Parser gives facets unexpected datatype
Fixes SF 101 Manchester OWL Syntax Parser doesn't do precedence correctly
Fixes #43 Functional Syntax Parser does not support comments
Fixes #41 Parsing error of SWRL atom.
Fixes #46 Round tripping error in functional syntax
Fixes #37 setAddMissingTypes logging if disabled
Missed serialization transients and tests, serialization bug fixes
Patch IRI.create(String,String) to match IRI.create(String)

Features:
GZip read/write ability. Compressed gzip input and output streams, but make sure to close the stream in the calling code.
OSGI dependency removed
Added Namespaces.inNamespace
Made ParserException to extend OWLParserException, and OWLParserException a runtime exception.
Tidying up Manchester OWL Syntax parser code
Added to the documentation on getOWLDatatype(OWLDataFactory)
Added a convenience method to convert an OWL2Datatype object to an OWLDatatype.
mvn install and mvn deploy now make aggregate sources available.
Added more well known namespaces, removed non-explanatory comments
Added RDFa Core Initial Context prefixes and other well known prefixes and prefix names.
GITHUB-10 : Add RDFa Core Initial Context prefixes
Extend Namespaces enum with common prefixes

3.4.5 25 July 2013

Mostly a maintenance release. Two important bug fixes: 
- a literal parsing error was stopping HermiT builds with 3.4.4.
- errors in OSGI support were stopping Protege from using the feature.

Bug fixes:
Fix #370 Typo in website/htdocs/reasoners.html.
Fixed a bug with large integer values being parsed as negative ints.
Fix infinite recursion on cyclic import structures.
Reverted the use of ThreadLocal as this causes memory leaks in webapps.
Bug #343 SWRL roundtrip in manchester syntax

Features and improvements:
Refactored DefaultPrefixManager to expose addPrefixes
Updated pom files for OSGI compatible artifacts.
Allow override of DefaultPrefixManager in PrefixOWLOntologyFormat
Add CollectionFactory.getExpectedThreads
Reduce expected threads from 16 to 8 to improve general performance

3.4.4 19 May 2013

Features and updates implemented:
feature 103 - Add methods to get EntityType names.
Updated IRI prefix caching to use thread local caches rather than synchronized caches.
added test for annotation retrieval for anonymous individuals.
WeakIndexCache vulnerability fixed.
Improved redirect following.
Added method to OWLOntologyLoaderConfiguration to disable cross protocol redirects on URLConnections.
Added parameter to OWLOntologyFormat for temp files, default disabled.
Update all JavaCharStream versions to fix the UTF BOM issue.
Travis Integration (on github).
Update KE interface with getBlocker method.
OSGI compatible build.
Removed IRI.toString() call when checking blank nodes.
Change logging of remaining triples to use less memory.
updates for javadoc.
Update Mockito to 1.9.5.

Bugs fixed:

Bug #369 IRI.create(String) uses / and # to look for namespace and fragments. 
Bug #348 ManchesterOWLSyntax round trip problem with disjoint classes.
Bug #269 single anonymous individual split in two on save to RDF.
Bug #319 redirects from http to https are not followed.
Bug #367 Serving ontologies from github as well.
Bug 4 (github) Missing method in OWLIndividual.
Bug 359 Incorrect Javadoc in OWL2DatatypeImpl and redundant javadoc.
Bug 360 - Axiom annotations are not parsed correctly.
Bug 364 Null pointer checking for containment of anonymous ontologies.
Bug #363 Turtle parser fails when loading valid Turtle document.
Bug 362 - owlapi files in temp folder.
Bug 357 duplicate axioms saving in OWL functional syntax.
Bug 355 - added assertions. Ability to parse 1+e7 as 10000000.
Bug 355 - Turtle Parser + Integer format Problem.
OntologyAlreadyExist bug when calls are made too fast.


3.4.3 26 Jan 2013

Features implemented:
Updated OWLFunctionalSyntaxFactory with more methods.
Removed deprecated method calls.
Test package broken up in smaller packages.
Web site updated and added to version management.

Bugs fixed:
Changed VersionInfo message default to provide more information.
Bug 354 fixed: There is AnnotationChange, but no AnnotationChangeData
Bug 353 fixed: SimpleRenderer renders SubDataPropertyOfAxiom wrong.
Bug 351 fixed: QNameShortFormProvider swapped prefix and iri.
Bug 352 fixed: SimpleRenderer does not escape double quotes in literals.


3.4.2 4 Dec 2012

Features implemented:

Repository migrated to Git.
Fixed memory leak with OWLObject signatures never being released once cached.
Improved Serializable implementation.
Made timeout property functional.

3585007 	Make OWLAxiomChange.isAdd().
3579488 	Clean documentation page.
3578004 	Javadoc OWLRDFVocabulary.
3578003 	Add OWLOntologyManager.removeOntology(OntologyID).
3578002 	Add new constructor for SetOntologyID.
3576182 	Bump minimum java version to 1.6.
3575834 	Improvements to OWLOntologyManager and related contrib.
3566810 	Lack of support in OWLOntologyManager for version IRIs.
3521809 	KRSS parser throws Error instead of RuntimeException.

Bugs closed:

Some OWLLiterals cannot be optimised: -0.0^^xsd:float, 01^^xsd:int (optimisation with primitive types makes reasoners queasy in combination with W3C tests).

3590243 	hashCode for literals inconsistent.
3590084     misuse of XMLUtils.getNCNameSuffix().
3581575 	OWLOntologyDocumentAlreadyExistsException in OWL API 3.4.1.
3580114 	IRI isAbsolute method.
3579862 	OWLDataFactory.getRDFPlainLiteral creates new objects.
3579861 	rdf:PlainLiteral is not in the allowed OWL2EL datatypes.

3.4.1 16 Sep 2012

Features implemented:
3578004    OWLRDFVocabulary Javadoc
3575834    Improvements to OWLOntologyManager and related contrib

Bugs closed:
3463200    rdf/functional round trip problem (anonymous individuals)
3341637    round trip problem with owl functional or rdf/xml syntax
3576182    Bump minimum java version to 1.6 (Fixed the bugs, moved the request to feature requests)
3497161    Manchester syntax imports
3403855    Imports of multiple ont docs containing the same ont fails
3491516    unhelpful Manchester parse exception
3536150    TypeOntologyHandler/TPVersionIRIHandler overwrite ontologyID
3314432    Manchester OWL parser does not handle data ranges in rules
3186250    annotation assertion lost when annotation prop undeclared
3309666    [Turtle] Parsing turtle files containing relative IRIs
3562296    Switch version 3.4.1 to 3.4.1-SNAPSHOT
3560287    Is QNameShortFormProvider deprecated?
3566820    Missing Imports are not reported
3306980    file in Manchester OWL Syntax with BOM doesn't parse
3559116    Wrong VersionInfo in OWL-API 3.4 release
2887890    Parsing hasValue restrictions with punned properties fails
3178902    RDF/XML round trip problem with 3-way equivalent classes
3174734    ManSyntax fails to read ontology with single data property
3440117    Turtle parser doesn't handle qnames with empty name part

----------------------------------------------------------------------
Release 3.4
----------------------------------------------------------------------

This version restructures the Maven modules into six modules: api, impl, parsers, 
tools, apibinding and contract. Code is unchanged, only its organization in 
the SVN is changed.

To split the dependencies accordingly, DefaultExplanationOrderer is in 
contract and is only a shell for ExplanationOrdererImpl, which does not 
depend on OWLManager directly. This enables the tools module to be 
used with a different apibinfing without recompilation.

Main bugs fixed:

 3554073 	Manchester Syntax Parser won't parse DisjointUnionOf
 3552028 	DataFactory returns integer instead of double restrictions
 3550607 	property cycle detection does not work
 3545194 	OWLEquivalentObjectProperties as SubObjectPropertyOfAxioms
 3541476 	OWLRDFConsumer.getErrorEntity counter is not thread safe
 3541475 	Remove shared static in OWLOntologyXMLNamespaceManager
 3535046 	Use ArrayList instead of TreeSet in TurtleRenderer
 3532600 	Use AtomicInteger in OWLOntologyID for counter

----------------------------------------------------------------------
Release 3.3 (15 May 2012)
----------------------------------------------------------------------

This version wraps together some minor bug fixes and some performance improvements for loading time and memory footprint for large ontologies.
Maven support has been much improved, thanks to Thomas Scharrenbach's efforts and Peter Ansell's feedback and suggestions.

Main bugs fixed:

- OBO Parser updated to be more compliant with the latest draft of the OBO syntax and semantics spec.
- OBO Parser doesn't expand XREF values  (3515525)
- Integrating with OpenRDF  (3512217)
- maven should use snapshot versions during development  (3511755)
- junit should be in maven test scope  (3511754)
- mvn clean install requires gpg key  (3511732)
- OWLOntologyManager.contains(IRI) bug (3497086)
- Functional and Manchester syntax writers prefix problem  (3479677)
- SyntacticLocalityModuleExtractor and SubAnnotationPropertyOf  (3477470)
- Ignored Imports List uses the wrong IRIs  (3472712)
- OWLLiteralImpl corrupts non-ascii unicode (3452932)
- OWLOntologyXMLNamespaceManager QName problem  (3449316)
- MultiImportsTestCase assumes incorrect working directory  (3448125)
- Mismatch between HasKeyTestCase and corresponding resource  (3447280)
- functional Renderer: OWLObjectRenderer swaps SWRL variables (3442060)
- maven build fails because of dependency issue  (3440757)
- manchester owl syntax doesn't handle rules with anon classes  (3421317)
- RDFTurtleFormatter doesn't escape '\'  (3415108)
- RDF consumer does not check for all annotated axiom triples  (3405822)
- mis-parsing/mis-serialization of haskey (3403359)
- Literal not a builtin  (3305113)
- OWL API throws NullPointerException loading ontology  (3302982)

----------------------------------------------------------------------
Release 3.2.4 (22 July 2011)
----------------------------------------------------------------------

This version of the API is released under both LGPL v3 and Apache license v2; developers are therefore free to choose which one to use.

This version of the API includes minor bug fixes and a couple of new features;
- Tutorial code has been added following the OWLED 2011 tutorial (subpackage tutorialowled2011);
- A visitor to determine whether a set of axioms is a Horn-SHIQ ontology has been added (HornAxiomVisitorEx.java).

Some test files and extra modules had not had their copyright notices updated to the new license; this has now been fixed.



----------------------------------------------------------------------
Release 3.2.3
----------------------------------------------------------------------

This version of the API is released under both LGPL v3 and Apache license v2; developers are therefore free to choose which one to use.

This version of the API includes various bug fixes:

- MAVEN support has been fixed (3296393).
- Some minor problems with rdfs:Literal have been fixed (3305113).
- Parsers and renderers for various languages have had some corner case errors fixed (3302982, 3300090, 3207844, 3235181, 3277496, 3294069, 3293620, 3235198).
- An error in OWLOntologyManager.removeAxioms() which was throwing ConcurrentModificationExceptions when called on a specific axiom type has been fixed (3290632).
- OWLOntologyLoaderConfiguration has been introduced to provide ontology wise parsing configuration (strict and lax parsing modes now enabled) (3203646) More options coming up.
- Parsers no longer leave streams open when a parsing error is detected (3189947).


----------------------------------------------------------------------
Release 3.2.2
----------------------------------------------------------------------

This version of the API includes various bug fixes:

- In RDF based serialisations, type triples for entities that don't have "defining axioms" don't get added even if the
renderer is instructed to type undeclared entities (related to 3184131). Fixed.
- The RDF parser would not recognise XSD datatypes that aren't OWL 2 Datatypes even in lax parsing mode (related to 3184131). Fixed.
- OWLOntologyManager.createOntology() methods don't set the document IRI of the created ontologies as adverstised if
there aren't any ontology IRI mappers installed (3184878). Fixed.


----------------------------------------------------------------------
Release 3.2.1
----------------------------------------------------------------------

This version of the API includes various bug fixes

- Issues with -INF as serialisation for -infinity for floating point literals. Fixed.
- Null pointer exception can be thrown by OBO parser (3162800). Fixed.
- OWLOntologyManager.loadOntology() can throw unchecked exceptions (IllegalArgumentException) (3158293). Fixed.
- OWLOntologyAlreadyExistsException get wrapped as as a parse exception by the OBO parser. (3165446). Fixed.
- OWLRDFConsumer (OWL RDFParser) used owl:Datatype instead of rdfs:Datatype. Fixed.
- OWLOntology.getDisjointUnionAxioms() does not work as expected. (3165000). Fixed.
- Anonymous ontologies do not get typed as owl:Ontology during saving in RDF. (3158177). Fixed.
- OWLOntology hashCode is not implemented properly. (3165583). Fixed.

----------------------------------------------------------------------
Release 3.2.0
----------------------------------------------------------------------

This version of the API includes various bug fixes and performance enhancements since version 3.1.0

Main Bug Fixes:

- Various round tripping problems for various syntaxes (3155509, 3154524, 3149789, 3141366, 3140693, 3137303, 3121903)
- Plain Literals are rendered incorrectly. Fixed.
- Cyclic imports cause an OntologyAlreadyExistsException to be thrown. Fixed.
- OWLAxiom.getAnnotatedAxiom() does not merge annotations.  Fixed.
- OWLObject.getSignature() returns an unmodifiable collection. Fixed.
- Various problems with character encodings (3068076, 3077637, 3096546, 3140693). Fixed.
- IRI.create resulted in a memory leak.  Fixed.
- Imports by location does not work. Fixed.
- Various problems with anonymous individuals (2998616, 2943908, 3073742). Fixed.
- Dublin Core Vocabulary is built into OWL API parsers.  Fixed.
- Files are left open on parse errors.  Fixed.

----------------------------------------------------------------------
Release 3.1.0
----------------------------------------------------------------------


This version of the API includes various bug fixes and enhancements since version 3.0.0.

Changes to the representation of literals:

Please note that there is a slight incompatibility between version 3.1.0 and version 3.0.0.  This is due to some changes
in the way that literals are represented and handled in the API.  There was a disparity between how they are described
in the OWL 2 specification and how they are represented in version 3.0.0 of the API.  The changes bring 3.1.0 inline
with the OWL 2 specification.  The changes are as follows: OWLStringLiteral and OWLTypedLiteral have been removed and
replaced OWLLiteral.  In version 3.0.0 OWLLiteral was a super-interface of OWLStringLiteral and OWLTypedLiteral.
Clients therefore need to replace occurrences of OWLStringLiteral and OWLTypedLiteral with OWLLiteral.  Method calls
need not be changed - methods on OWLStringLiteral and OWLTypedLiteral have corresponding methods on OWLLiteral.  Note
that all literals are now typed.  What used to be regarded as OWLStringLiterals are now OWLLiterals with the datatype
rdf:PlainLiteral.  Although this change introduces a slight backward incompatibility with the previous version of the
API we believe that the handling of literals in 3.1.0 is much cleaner and follows the specification more closely.

Changes to the OWLReasoner interface:

The OWLReasoner interface has been updated.  There are now methods which allow for fine-grained control over reasoning
tasks.  For example, it is now possible to request that a reasoner just classifies the class hierarchy, when in version
3.0.0 the prepare reasoner method caused realisation to occur as well.  The prepareReasoner method has been removed from
the interface due to the huge amount of confusion it caused (it was not necessary to call this method to get correct
results, but the name suggested that it was necessary).  Clients should update their code to replace any calls to
prepareReasoner with appropriate calls to precomputeInferences.

JavaDoc for various methods has been cleaned up.

Main Bug Fixes:

- Annotations on Declaration axioms were not save.  Fixed.
- OWLObjectMaxCardinality restrictions incorrectly resulted in a profile violation of the OWL2RL profile. Fixed.
- OWLOntology.containsAxiom() failed various round trip tests. Fixed.
- OWLObjectPropertyExpression.getInverses() did not include properties that were asserted to be inverses of themselves. Fixed.
- Writing large rdf:Lists can cause a stack overflow on saving. Fixed.
- System.out is closed when using SystemOutDocumentTarget. Fixed.
- An OWLOntologyAlreadyExists exception is thrown when an imports graph contains multiple imports of the same ontology. Fixed.
- OWLReasoner.getSubclasses() is missing declarations in the throws list for runtime exceptions that get thrown. Fixed.
- Parsing RDF graphs containing blank nodes with NodeIDs is broken.  Fixed.
- Some methods on OWLOntology return sets of object that change with ontology changes. Fixed.
- Double dashes (--) are not escaped in XML comments output by the XMLWriter. Fixed.
- The API sometimes prints to System.err.  Fixed.
- The functional syntax writer writes out annotation assertions twice. Fixed.
- Ontologies with DataSomeValuesFrom restrictions are incorrectly considered non-OWL2QL. Fixed.
- Ontologies with Functional data properties are incorrectly considered non-OWL2RL. Fixed.
- OWL2Datatype.XSD_NAME has the wrong regex pattern. Fixed.
- RDF rendering of GCIs with multiple elements is broken. Fixed.
- SubObjectPropertyOf axioms with annotations and property chains don't get parsed correctly from RDF.  Fixed.
- StructuralReasoner sometimes crashes when determining superclasses. Fixed.
- SimpleRenderer renders declarations incorrectly.  Fixed.
- Object property characteristics are not answered correctly.  Fixed.
- OWLOntology.getReferencingAxioms gives mutable and incorrect results.  Fixed.
- StructuralReasoner call OWLClassExpression.asOWLClass() on anonymous classes. Fixed.
- SKOSVocabulary is not up to date.  Fixed.
- Regular expression for xsd:double has extra white space. Fixed.
- Structural reasoner sometimes misses subclasses of owl:Thing. Fixed.
- OWLOntologyManager.getImportsClosure() fails on reload. Fixed.
- PrefixOWLOntologyFormat has not methods to remove prefixes.  Fixed.
- RDF/XML rendering of unicode characters is ugly. Fixed.
- Turtle parser does parse shared blank nodes correctly. Fixed.

----------------------------------------------------------------------
Release 3.0.0
----------------------------------------------------------------------

Version 3.0.0 is incompatible with previous releases.  Many interface names have been changed in order to acheive a
close alignment with the names used in the OWL 2 Structural Specification and Functional Style Syntax.
(See http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/)

The opportunity to clean up method names was also taken.


----------------------------------------------------------------------
Release 2.2.0  (17th April 2008)
----------------------------------------------------------------------

Bug Fixes:

- The getOntologyURIs method on AutoURIMapper would return physical rather than logic URIs. Fixed.
- Namespaces for annotation URIs weren't generated. Fixed.
- Removing a subclass axiom from an ontology cause the axiom to be added to the ontology as a GCI. Fixed.
- When parsing an ontology, the accept types has been set to include RDF/XML.  This means that ontologies can be parsed correctly from servers that are configured to return RDF or HTML depending on the request type.
- OWL/XML writer has been modified to write the datatype URI attribute name correctly.  Previously the name was written as "Datatype", however it should be "datatypeURI".
- OWL/XML parser. Modified the literal handler to parse literals using the correct datatype URI attribute name (was "Datatype" and should have been "datatypeURI").
- The constructor that required a manager in BidirectionalShortFormProviderAdapter did not rebuild the cache. Fixed.
- Unqualified cardinality restrictions were rendered out as qualified cardinality restrictions. Fixed.
- Saving an ontology would fail if the necessary directories did not exist. Fixed.
- Rendering anonymous property inverses in OWL/XML was incorrect. Fixed.
- Label and Comment annotations in the functional syntax weren't parsed properly, they were parsed as regular annotations. Fixed.
- In the OWLXMLParserHandler, no handler for negative data property assertions was registered. Fixed.
- Annotations that have anonymous individuals as values weren't rendered correctly. Fixed.
- RDFXMLOntologyStorer and RDFXMLRenderer always used the ontology format that is obtainable from the manager, regardless of whether or not a custom ontology format was specified - fixed.
- Rules that contained individual or data value objects couldn't be rendered. Fixed.
- Declaration axioms were automatically added for data properties whether an ontology contained declaredAs triples or not. Fixed.
- Anonymous properties weren't rendered correcty. Fixed.
- RDF rendering for sub property axioms whose sub property is a property chain used an old rendering.  The rendering now complies with the latest OWL 2 specification.  Ontologies that use the old rendering can still be parsed.
- RDF lists were reordered on rendering. Fixed.

Enhancements:

- Added support for building using ANT

- OWL 1.1 namespaces changed to OWL 2.  Old ontologies that are written using the owl11 namespace will still load, but will be converted to use the owl2 namespace.

- Updated the RDF parser and RDF renderer to support AllDisjointClasses and AllDisjointProperties

- Added the ability to save ontologies in Turtle.

- Added the ability to load ontologies that are written in Turtle

- Added explanation code contributed by Clark & Parsia

- Added a KRSS renderer (contributed by Olaf Noppens)

- Added a new, more comprehensive KRSS parser (contributed by Olaf Noppens).  This parser can parser the version of the KRSS syntax that is used by Racer.

- Added the ability to specify a connection timeout for URL connections via a system property (owlapi.connectionTimeOut) - the default value for the timeout is 20 seconds.

- Added a method to OWLOntologyManager to clear all registered URI mappers

- Added a method to OWLOntologyManager so that imports can be obtained by an imports declaration.

- Added a convenience method to OWLOntologyManager to add a set of axioms to an ontology without having to create the AddAxiom changes

- Added a makeLoadImportsRequest method on OWLOntologyManager which should be used by parsers and other loaders in order to load imports

- Added the ability to set an option for silent missing imports handling on OWLOntologyManager.  When this option is set,  exceptions are not thrown when imports cannot be found or cannot be loaded.  It is possible to set a listeners that gets informed when an import cannot be found, so that the exception doesn't get lost entirely.

- Added the ability to add a ontology loader listener to OWLOntologyManager.  The listener gets informed when the loading process for an ontology starts and finishes (which ontology is being loaded, from where and whether it was successfully loaded etc.).

- Added a method to OWLReasonerFactory to obtain the human readable name of the reasoner that a factory creates.

- Added a convenience method to OWLOntology to obtain all referenced entities

- Added convenience methods to OWLEntity that check whether the entity is an OWLClass, OWLObjectProperty, OWLDataProperty, OWLIndividual or OWLDatatype.  Also added asXXX to obtain an entity in its more specific form.

- Added convenience methods to OWLDataFactory for creating disjoint class axioms and equivalent classes axioms.

- Added a general purpose renderer interface for OWLObjects

- Added an OWLInconsistentOntologyException to the inference module.

- Added SKOS core to the list of well known namespaces

- Added a SKOS vocabulary enum

- Added methods to the OWLOntologyManager interface, so that ontologies can be saved to an output target as well as a URI.  Added implementations of OWLOntologyOutputTarget to enable writing directly to OutputStreams and Writers.

- Added a StringOutputTarget for writing ontologies into a buffer that can be obtained as a string.

- Added some new input sources:  StreamInputSource, ReaderInputSource, FileInputSource

- RDF Parser. Made the classExpression translator selector more intelligent so that when properties aren't typed as either object or data properties, other triples are examined to make the appropriate choice.

- OWLRestrictedDataRangeFacetVocabulary.  Added methods to obtain facets by their symbolic name (e.g. >=)

- BidirectionalShortFormProvider.  Added a method to obtain all short forms cached by the provider.

- Added an option to turn tabbing on/off when rendering Manchester Syntax

- Added more documentation for the method which adds ontology URI mappers

- Improved error handling when loading ontologies: For errors that have nothing to do with parse errors e.g. unknown host exceptions, the factory will
rethrow the error at the earliest opportunity rather than trying all parsers.

- Updated parser to throw ManchesterOWLSyntaxOntologyParserException which is a more specific type of OWLParserException

- Updated the BidirectionalShortFormProviderAdapter with functionality to track ontology changes and update the rendering cache depending on whether entities are referenced or not.

- Added a latex renderer for rendering ontology axioms in a latex format

- Added the ability to parse ontologies written in ManchesterOWLSyntax

- Added URIShortFormProvider as a general purpose interface for providing short forms for URIs. Changed SimpleShortFormProvider to use the SimpleURIShortFormProvider as a base

- Made the toString rendering of the default implementation pluggable via the ToStringRenderer singleton class.

- Added some convenience methods to the OWLDataFactory to make creating certain types of objects less tedious.  Specifically: ObjectIntersectionOf, ObjectUnionOf, ObjectOneOf and DataOneOf can now be created using methods that take a variable number of arguments (OWLDescriptions, OWLIndividuals or OWLConstants as appropriate).  Also, added convenience methods that create typed literals directly from Java Strings, ints, doubles, floats and booleans.  For example, createOWLTypedConstant(3) will create a typed literal with a lexical value of "3" and a datatype of xsd:integer.  Added convenice methods for creating entity annotations without manually having to create OWLAnnotation objects.

- Added a getAxiomType method on to the OWLAxiom interface for convenience.

- Added functionality to the debugging module for ordering explanations

- Added generics to the inferred axiom generator API

- Added a new constructor to OWLOntologyNamespaceManager so that it is possible to override the ontology format that is used as a hint when generating namespaces.

- Added a dlsyntax renderer module that can renderer axioms etc. in the traditional dlsyntax using unicode for the dlsyntax symbols.

- Modified the RDFXMLNamespaceManager to select the minimal amount of entities for which namespaces need to be generated.  Namespaces are only generated for classes in OWLClassAssertionAxioms, and properties in OWLObjectPropertyAssertionAxioms and OWLDataPropertyAssertionAxioms.  This basically corresponds to the places where valid QNames are needed for entities.

- Added code to add declarations for "dangling entities".  If an RDF graph contains  <ClsA> <rdfs:type> <owl:Class> and ClsA has not been referenced by any other axioms then this would have been dropped by the parser - this has been changed so that declaration axioms are added to the ontology in such cases.  (Hopefully, the OWL 1.1 spec will be updated to do something like this in the mapping to RDF graphs).

- Added a utility class, AxiomSubjectProvider, which given an axiom returns an object which is regarded to be the "subject" of the axioms.  For example given SubClassOf(ClsA ClsB), ClsA is regarded as being the subject.

- Modified the ontology URI short form provider to provide nicer looking short forms.

- Added a convenience method to get the individuals that have been asserted to be an instance of an OWLClass.

- Commons lang is no longer used in the API because it had been replaced with a lightweight utility class in order to escape strings.

- Removed the fragments module and replaced it with the profiles module.  The EL++ profile is currently implemented.

- Added support for extended visitors that can return objects in the visit method.

- Turned off logging in the RDF parser classes by default.

