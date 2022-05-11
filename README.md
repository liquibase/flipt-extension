# Flipt Feature Flags Extension
[Precondition](https://docs.liquibase.com/concepts/changelogs/preconditions.html) to control the execution of a changelog or changeset based on the state of the feature flag in Flipt.

## Supported Editions
[![Community](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/mcred/liquibase-header-footer/feature/badges/badges/community.json)](https://liquibase.org/)
[![Pro](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/mcred/liquibase-header-footer/feature/badges/badges/pro.json)](https://www.liquibase.com/pricing/pro)

## Installation
The easiest way to install this extension is with `lpm` [liquibase package manager](https://github.com/liquibase/liquibase-package-manager).
```shell
lpm update
lpm add flipt
```

## Setup
URL is required for the extension to locate the Flipt API.
```
--flipt-url=PARAM
     URL for Flipt API
     (liquibase.flipt.url)
     (LIQUIBASE_FLIPT_URL)
     [deprecated: --fliptUrl]
```

## Usage
To use this extension, add the `fliptFeatureFlag` precondition to your Changelog or Changeset with an `enabledFlags` attribute. The value for `enabledFlags` is either a string with one feature flag key or a comma separated string with multiple feature flag keys. All feature flags must be enabled for the precondition to pass.   

## Example
```yaml
databaseChangeLog:
  -  preConditions:
     -  fliptFeatureFlag:
          enabledFlags: changelog-testing
```
```xml
<changeSet id="1" author="example">
    <preConditions>
        <ext:fliptFeatureFlag enabledFlags="changelog-testing"/>
    </preConditions>
    ...
</changeSet>
```

## Feedback and Issues
Please submit all feedback and issues to [this idea board](https://ideas.liquibase.com/c/70-flipt-feature-flags-extension).