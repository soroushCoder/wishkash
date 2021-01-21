package com.wishkash;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.wishkash");

        noClasses()
            .that()
                .resideInAnyPackage("com.wishkash.service..")
            .or()
                .resideInAnyPackage("com.wishkash.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.wishkash.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
