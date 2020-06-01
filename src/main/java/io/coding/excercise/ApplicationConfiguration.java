/**
 * 
 */
package io.coding.excercise;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author subha
 *
 */
@Configuration
@ComponentScan(basePackages = {"io.coding.excercise"})
@PropertySource(value = "application.properties")
public class ApplicationConfiguration {

}
