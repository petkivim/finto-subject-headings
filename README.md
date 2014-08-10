Finto Subject Headings
======================

This project contains a group of OSGi modules that publish a RESTful service that consumes MarcXML records. The service checks all the 650 fields and adds the URI identifier of each subject heading into 650 $0 subfield if the subject heading can be found from Finto, Finnish thesaurus and ontology service. The code of the thesaurus is defined in the 650 $2 subfield. Supported thesauruses are YSA (General Finnish thesaurus) and Allärs (Allmän tesaurus på svenska). If the 650 $2 subfield is missing or the given subject heading is not found from Finto, the record is not modified.
