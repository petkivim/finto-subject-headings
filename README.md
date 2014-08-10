Finto Subject Headings
======================

This project contains a group of OSGi bundles that publish a RESTful service that consumes MarcXML records. The service checks all the 650 fields and adds the URI identifier of each subject heading into 650 $0 subfield if the subject heading can be found from Finto, Finnish thesaurus and ontology service. The code of the thesaurus is defined in the 650 $2 subfield. Supported thesauruses are YSA (General Finnish thesaurus) and Allärs (Allmän tesaurus på svenska). If the 650 $2 subfield is missing or the given subject heading is not found from Finto, the record is not modified.

### Software Requirements

* Linux or Windows
* Java 6 or 7
* Maven 3.x
* Apache ServiceMix (>= 4.5.3)

### Installation

* [Download](http://servicemix.apache.org/downloads.html) Apache ServiceMix and extract the dowloaded zip package.
  * Create a new ```datastore``` directory: ```apache-servicemix-5.1.1/datastore```

* Compile marc-record and vocabulary OSGi bundles.

```
cd marc-record
mvn install
cd..
cd vocabulary
mvn install
```

* Start Apache ServiceMix.

```
cd apache-servicemix-5.1.1/bin
servicemix
```

* Install marc-record and vocabulary OSGi bundles in the following order by using Karaf console.

```
karaf@root> osgi:install -s mvn:com.pkrete/marc-record-model/0.0.1-SNAPSHOT
karaf@root> osgi:install -s mvn:com.pkrete/marc-record-api/0.0.1-SNAPSHOT
karaf@root> osgi:install -s mvn:com.pkrete/vocabulary-model/0.0.1-SNAPSHOT
karaf@root> osgi:install -s mvn:com.pkrete/vocabulary-api/0.0.1-SNAPSHOT
karaf@root> osgi:install -s mvn:com.pkrete/marc-record-handler/0.0.1-SNAPSHOT
karaf@root> osgi:install -s mvn:com.pkrete/marc-record-endpoint/0.0.1-SNAPSHOT
karaf@root> osgi:install -s mvn:com.pkrete/vocabulary-util/0.0.1-SNAPSHOT
karaf@root> osgi:install -s mvn:com.pkrete/vocabulary-datastore/0.0.1-SNAPSHOT
karaf@root> osgi:install -s mvn:com.pkrete/vocabulary-index/0.0.1-SNAPSHOT
karaf@root> osgi:install -s mvn:com.pkrete/vocabulary-maintenance/0.0.1-SNAPSHOT
karaf@root> osgi:install -s mvn:com.pkrete/vocabulary-search/0.0.1-SNAPSHOT
```

* The list of available web services can now be accessed at [http://localhost:8181/cxf](http://localhost:8181/cxf). The WADL file of the vocabulary service can be accessed at [http://localhost:8181/cxf/vocabulary?_wadl](http://localhost:8181/cxf/vocabulary?_wadl). 

* Stop ServiceMix. Hit 'ctrl-d' or 'osgi:shutdown' to shutdown ServiceMix.

* Copy ```deploy/maintenance-cron.xml``` to  ```apache-servicemix-4.5.3/deploy``` folder. This file defines how often YSA and Allärs are updated from Finto. When you install the bundles for the first time, you need to modify hours and minutes (the default is 8.00 AM), and set them let's say two minutes ahead from current time. You're not able to use the RESTful service until the data is loaded from Finto. After the initial load you're free to choose when and how often the data is updated.

```
<task:scheduled-tasks scheduler="taskScheduler">
  <task:scheduled ref="vocabularyMaintenanceService" method="doMaintenance" cron="0 0 8 * * *"/> 
</task:scheduled-tasks>
```

* Start ServiceMix.

* After two minutes (or the time that you defined in ```deploy/maintenance-cron.xml```) check the ServiceMix log file ```apache-servicemix-5.1.1/data/log/servicemix.log``` and make sure that YSA and Allärs have been dowloaded from Finto. If the vocabularies have been downloaded succesfully, ```apache-servicemix-5.1.1/datastore``` folder should contain four files: ```ALLARS.datastore.ttl```, ```ALLARS.index.data```, ```YSA.datastore.ttl```, ```YSA.index.data```.

### Using the service

Single MarcXML record stored in record.xml file.

```
curl -X POST -d @record.xml "http://localhost:8181/cxf/vocabulary/record" --header "Content-Type:application/xml"
```

Multiple MarcXML records stored in records.xml file.

```
curl -X POST -d @records.xml "http://localhost:8181/cxf/vocabulary/records" --header "Content-Type:application/xml"
```
