package com.technicaltest.mobile.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/mobile/features", glue = { "com.technicaltest.mobile.steps",
        "com.technicaltest.mobile.config" }, plugin = {
                "pretty",
                "html:target/ui-report"
        })
public class MobileTestRunner {
}
