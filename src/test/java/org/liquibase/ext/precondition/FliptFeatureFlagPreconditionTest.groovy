package org.liquibase.ext.precondition

import liquibase.changelog.ChangeSet
import liquibase.exception.PreconditionErrorException
import liquibase.exception.PreconditionFailedException
import liquibase.parser.ChangeLogParserFactory

class FliptFeatureFlagPreconditionTest extends AbstractFeatureFlagPreconditionTest {

    def dbChangeLog = ChangeLogParserFactory.getInstance().getParser("xml", clra)
            .parse("changelogs/flipt.xml", null, clra)
    def changeSet = new ChangeSet(dbChangeLog)
    def fliptFeatureFlagPrecondition = dbChangeLog.getChangeSets()[0].getPreconditions().getNestedPreconditions()[0]

    def "GetName"() {
        expect:
        fliptFeatureFlagPrecondition.getName() == "fliptFeatureFlag"
    }

    def "Flipt feature flag is not available"() {
        when:
        fliptFeatureFlagPrecondition.flipt = Mock(Flipt)
        fliptFeatureFlagPrecondition.flipt.Enabled(*_) >> { throw new IOException("Unable to connect to Flipt API.") }
        fliptFeatureFlagPrecondition.check(database, dbChangeLog, changeSet, changeExecListener)

        then:
        thrown(PreconditionErrorException)
    }

    def "Flipt feature flag is not found"() {
        when:
        fliptFeatureFlagPrecondition.flipt = Mock(Flipt)
        fliptFeatureFlagPrecondition.flipt.Enabled(*_) >> { false }
        fliptFeatureFlagPrecondition.check(database, dbChangeLog, changeSet, changeExecListener)

        then:
        thrown(PreconditionFailedException)
    }

    def "Flipt feature flag is not enabled"() {
        when:
        fliptFeatureFlagPrecondition.flipt = Mock(Flipt)
        fliptFeatureFlagPrecondition.flipt.Enabled(*_) >> { false }
        fliptFeatureFlagPrecondition.check(database, dbChangeLog, changeSet, changeExecListener)

        then:
        thrown(PreconditionFailedException)
    }

    def "Flipt feature flag is enabled"() {
        given:
        fliptFeatureFlagPrecondition.flipt = Mock(Flipt)
        fliptFeatureFlagPrecondition.flipt.Enabled(*_) >> { true }

        expect:
        fliptFeatureFlagPrecondition.check(database, dbChangeLog, changeSet, changeExecListener)
    }
}
