# ArangoDB Plugin for Pentaho Data Integration (PDI)

This plugin adds various custom Steps for ArangoDB support in Pentaho Data Integration.

## Custom Steps/Jobs supported

- Collection Scan Input - Retrieves all JSON documents and their keys from a named collection. 
- Document Input - Retrieves JSON documents by their key. Assumes one key field per input row in PDI. 
- Document Output - Writes JSON documents to a collection. Assumes one document per input row in PDI.
- Foxx Invoke - Invokes a Foxx Service on ArangoDB and returns its output as JSON to PDI. Assumes one invocation per input row.

## Usage notes

An ArangoDB Database Wizard set up page is included within this plugin. This enables all steps to share an ArangoDB database connection configuration within each transformation. This also makes using the plugin easier when deploying to dev/test/uat/production environments.

## Installation

1. Download the distribution zip from the releases page: https://github.com/Pentaho-SE-EMEA-APAC/pdi-arangodb/releases
2. Unpack the zip file
3. Take the pdi-arangodb-VERSION* folder and rename it to pdi-arangodb
4. Move the folder in to PENTAHO_HOME/client-tools/data-integration/plugins folder
5. Restart PDI
6. You will find the ArangoDB steps under the 'Big Data' folder in the step explorer

## Building from source

1. Clone this repo
2. Install maven
3. Run mvn -B verify -Dcentral -e
4. Take the ./target/pdi-arangodb-VERSION.zip file and unzip it's contents in to the PENTAHO_HOME/client-tools/data-integration/plugins folder
5. Delete the pdi-arangodb/lib/slf4j*.jar file (it clashes with Pentaho's library)
6. Restart PDI
7. You will find the ArangoDB steps under the 'Big Data' folder in the step explorer

## Samples

Sample transforms are available in the ZIP release, within the samples folder.

## License and Copyright

All material in this repository are Copyright 2002-2018 Hitachi Vantara. All code is licensed as Apache 2.0 unless explicitly stated. See the LICENSE file for more details.

## Support Statement

This work is at Stage 1 : Development Phase: Start-up phase of an internal project. Usually a Labs experiment. (Unsupported)
