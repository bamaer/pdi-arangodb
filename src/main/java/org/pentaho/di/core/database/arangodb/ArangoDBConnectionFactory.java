/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2018 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.core.database.arangodb;

import org.pentaho.di.core.database.DatabaseFactoryInterface;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleDatabaseException;

import com.arangodb.ArangoDBAsync;
import com.arangodb.ArangoDatabaseAsync;

/**
 * A ConnectionFactory for ArangoDB
 * 
 * @author Adam Fowler <adam.fowler@hitachivantara.com>
 * @since 1.0 2018-03-06
 */
public class ArangoDBConnectionFactory implements DatabaseFactoryInterface {
  // TODO add Kerberos auth context support
  public enum AuthScheme { BASIC, DIGEST };

  public static ArangoDBAsync create( String host, int port, String username, String password, String databaseName) throws Exception {
    ArangoDBAsync arangoDB = new ArangoDBAsync.Builder()
    /*.loadBalancingStrategy(LoadBalancingStrategy.ROUND_ROBIN)*/
        .maxConnections(8).acquireHostList(true).user(username).password(password).build(); // TODO parameterise the number of connections in pool
    /*
    try {
      arangoDB.db(DB_NAME).drop().get();
    } catch (final Exception e) {
    }
    arangoDB.createDatabase(databaseName).get(); // TODO verify this is not destructive, or permission limited
    */
    //ArangoDatabaseAsync db = arangoDB.db(databaseName);
    return arangoDB;
  }

  /**
   * The ArangoDB connection to test
   */
  public String getConnectionTestReport(DatabaseMeta databaseMeta) throws KettleDatabaseException {

    StringBuilder report = new StringBuilder();

    // TODO test the connection

    return report.toString();
  }
}
