package by.bsuir.config;

import by.bsuir.entity.security.NoSecureUrl;
import by.bsuir.entity.security.SecurityProperties;
import by.bsuir.filter.SecurityFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties restSecProps;

    @Autowired
    public SecurityFilter tokenAuthenticationFilter;

    private final static String ERROR_MESSAGE = "Unauthorized access of protected resource, invalid credentials";
    private final static String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Bean
    public NoSecureUrl noSecureUrl() {
        return NoSecureUrl.builder()
                .url(List.of(
                        "/swagger",
                        "/test/**",
                        "/registration/user/**",
                        "/firebase/**",
                        "/registration/email/**",
                        "favicon.ico"
                ))
                .build();
    }

    @Bean
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return (httpServletRequest, httpServletResponse, e) -> {
            Map<String, Object> errorObject = new HashMap<>();
            int errorCode = 401;
            errorObject.put("message", ERROR_MESSAGE);
            errorObject.put("error", HttpStatus.UNAUTHORIZED);
            errorObject.put("code", errorCode);
            errorObject.put("timestamp", new Timestamp(new Date().getTime()));
            httpServletResponse.setContentType(CONTENT_TYPE);
            httpServletResponse.setStatus(errorCode);
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(errorObject));
        };
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(restSecProps.getAllowedOrigins());
        configuration.setAllowedMethods(restSecProps.getAllowedMethods());
        configuration.setAllowedHeaders(restSecProps.getAllowedHeaders());
        configuration.setAllowCredentials(restSecProps.isAllowCredentials());
        configuration.setExposedHeaders(restSecProps.getExposedHeaders());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry =
                http.csrf().disable()
                            .cors().configurationSource(corsConfigurationSource())
                        .and()
                            .formLogin().disable()
                            .httpBasic().disable()
                            .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint())
                        .and()
                            .authorizeRequests();

        List<String> permitAllUrl = noSecureUrl().getUrl();

        for (String url : permitAllUrl) {
            expressionInterceptUrlRegistry = expressionInterceptUrlRegistry.antMatchers(url).permitAll();
        }

        expressionInterceptUrlRegistry
                .antMatchers(restSecProps.getAllowedPublicApis().toArray(String[]::new)).permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated().and()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

}