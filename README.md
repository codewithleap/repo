<h1>Leap: An Ant Library Plugin for Salesforce Apex Development</h1>

<h2>Project Goals</h2>
<ul>
<li>Accelerate the Apex learning and development process</li>
<li>Catalog Apex patterns and best practices in a template library</li>
<li>Enable continuous integration / Built around the Apex development lifecycle</li>
<li>Extensible and open</li>
</ul>

<h2>Overview</h2>
Leap is a Java Ant library that integrates with the Salesforce migration toolkit to help Developers with common tasks, such as generating trigger handlers and wrapper classes. The leap template library is an open source catalog of Apex best practices and examples, compiled from the Salesforce Development community, that encourages coding styles that work well on the Salesforce platform.

<h2>Usage</h2>
From the command line of any Salesforce development environment:
<pre>
	~/ant leapTargetName
</pre>

<h2>List of Leap Tasks</h2>
<ul>
	<li>leapsfields: Generates a class of static fields for all SObjects</li>
	<li>leaptriggers: Generates triggers and Apex trigger handler class(es) for SObjects</li>
	<li>leapwrappers: Generates wrapper class(es) (in development)</li>
</ul>

<h2>Getting started</h2>
<ul>
	<li>Download and copy bin/ant-leap.jar into the local Ant library folder (on a Mac this is located at /usr/share/ant/lib).</li>
	<li>Create build.properties and build.xml files within the root of any Salesforce development project (see example templates below).</li>
	<li>Enter Salesforce development credentials into build.properties.
	<li>To test, type "ant leapsfields" to run the leap task that builds a class of field names.
</ul>

<h2>Example build.properties</h2>
<pre>
	# build.properties
	#
	# For server URL properties...
	# Use 'https://login.salesforce.com' for production or developer edition.
	# Use 'https://test.salesforce.com for sandbox.

	# Specify the login credentials for the Salesforce development organization
	sf.dev.username = developername@domain.com
	sf.dev.password = password
	sf.dev.url = https://login.salesforce.com

	# Specify the login credentials for the Salesforce staging/test organization
	sf.test.username = 
	sf.test.password = 
	sf.test.url = https://login.salesforce.com

	# Specify the login credentials for the Salesforce production/packaging organization
	sf.prod.username = 
	sf.prod.password = 
	sf.prod.url = https://login.salesforce.com
</pre>

<h2>Example build.xml</h2>

<pre>
&lt;project name="Project Name" default="test" basedir="." xmlns:sf="antlib:com.salesforce" xmlns:leap="antlib:org.leap"&gt;
	&lt;target name="leapTaskName"&gt;
		&lt;leap:leapTaskName username="${sf.dev.username}" password="${sf.dev.password}" serverurl="${sf.dev.url}" /&gt;
	&lt;/target&gt;

	&lt;target name="leapsfields"&gt;
		&lt;leap:leapsfields username="${sf.dev.username}" password="${sf.dev.password}" token="${sf.dev.token}" serverurl="${sf.dev.url}" /&gt;
	&lt;/target&gt;

	&lt;target name="leaptriggers"&gt;
		&lt;leap:leaptriggers username="${sf.dev.username}" password="${sf.dev.password}" token="${sf.dev.token}" serverurl="${sf.dev.url}" /&gt;
	&lt;/target&gt;

	&lt;target name="leapwrappers"&gt;
		&lt;leap:leapwrappers username="${sf.dev.username}" password="${sf.dev.password}" token="${sf.dev.token}" serverurl="${sf.dev.url}" /&gt;
	&lt;/target&gt;
&lt;/project&gt;
</pre>
