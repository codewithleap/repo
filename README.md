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
	<li>leapmetadiff: Compares org files in 2 directories. Copies diff files into a 3rd directory.</li>
	<li>leaplint: Static analysis of human editable files (*.cls, *.page, *.component, *.trigger, *.resource)</li>
	<li>executeAnonymous: executes Apex code on the target Salesforce environment.</li>
</ul>

<h2>Getting started</h2>
<ul>
	<li>Download and copy bin/ant-leap.jar into the local Ant library folder (on a Mac this is located at /usr/share/ant/lib).</li>
	<li>Create build.properties and build.xml files within the root of any Salesforce development project (see example templates below).</li>
	<li>Enter Salesforce development credentials into build.properties.</li>
	<li>To test, type "ant leapsfields" to run the leap task that builds a class of field names.</li>
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
		&lt;leap:leapsfields username="${sf.dev.username}" password="${sf.dev.password}" token="${sf.dev.token}" serverurl="${sf.dev.url}" objects="Lead,Contact,Account,Opportunity,Order__c" /&gt;
	&lt;/target&gt;

	&lt;target name="leaptriggers"&gt;
		&lt;leap:leaptriggers username="${sf.dev.username}" password="${sf.dev.password}" token="${sf.dev.token}" serverurl="${sf.dev.url}" objects="Lead,Contact,Account,Opportunity,Order__c" /&gt;
	&lt;/target&gt;

	&lt;target name="leapwrappers"&gt;
		&lt;leap:leapwrappers username="${sf.dev.username}" password="${sf.dev.password}" token="${sf.dev.token}" serverurl="${sf.dev.url}" objects="Lead,Contact,Account,Opportunity,Order__c" /&gt;
	&lt;/target&gt;
	
	&lt;target name="leapmetadiff"&gt;
		&lt;leap:leapmetadiff srcFolder="${sf.dev.srcFolder}" destFolder="${sf.prod.srcFolder}" outFolder="${sf.outFolder}"  username="${sf.dev.username}" password="${sf.dev.password}" token="${sf.dev.token}" serverurl="${sf.dev.url}"/&gt;
	&lt;/target&gt;

	&lt;target name="lint"&gt;
		&lt;leap:lint maxFileLines="500" ignoreFiles="(comma separated list of files to be ignored)" failonerror="true|false" /&gt;
	&lt;/target&gt;	

	&lt;target name="execAnon"&gt;
		&lt;leap:executeAnonymous code="MyApexClass.staticMethod();" failonerror="true|false" /&gt;
	&lt;/target&gt;	
&lt;/project&gt;
</pre>

<h2>Attributes</h2>
Notes on attributes.
failonerror: When set to "true" will stop the build process.


<h2>Open Source</h2>
Leap is open sourced under the BSD license.

Copyright (c) 2014, Michael Leach
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
