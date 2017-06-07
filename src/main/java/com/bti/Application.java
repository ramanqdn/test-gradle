package com.bti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.security.oauth2.sso.EnableOAuth2Sso;
import org.springframework.cloud.security.oauth2.sso.OAuth2SsoConfigurerAdapter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration
@EnableOAuth2Sso
@EnableZuulProxy
public class Application {
	private static Logger LOG = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
    	LOG.info("In GatewayApplication main");
    	System.out.println("=============In GatewayApplication main===============");
        SpringApplication.run(Application.class, args);
    }

    @Configuration
    protected static class SecurityConfiguration extends OAuth2SsoConfigurerAdapter {
    	private static Logger LOG = LoggerFactory.getLogger(SecurityConfiguration.class);
        @Override
        public void configure(HttpSecurity http) throws Exception {
        	LOG.info("In configure method");
            http.logout().and().antMatcher("/**").authorizeRequests()
                    .antMatchers(
                    		"/user",
                    		"/group/**",
                    		"/login/**",
                    		"/company/**"
                            ).permitAll()
                    .anyRequest().authenticated()
                    .and().csrf().disable();
            /*
                    .and().csrf()
                    .csrfTokenRepository(csrfTokenRepository()).and()
                    .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
                    //Add back for CSRF support
            */
        }
    }
}
