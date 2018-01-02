package io.github.davidwadden.storeapi;

import org.springframework.data.cassandra.config.AbstractClusterConfiguration;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

import java.util.List;

import static java.util.Collections.singletonList;

//@Configuration
//@EnableReactiveCassandraRepositories
public class TestCassandraConfiguration extends AbstractClusterConfiguration {

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification keyspaceSpecification = CreateKeyspaceSpecification
            .createKeyspace("showcase")
            .with(KeyspaceOption.DURABLE_WRITES, false)
            .withSimpleReplication();
        return singletonList(keyspaceSpecification);
    }

    @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
        DropKeyspaceSpecification keyspaceSpecification = DropKeyspaceSpecification
            .dropKeyspace("showcase");
        return singletonList(keyspaceSpecification);
    }
}
