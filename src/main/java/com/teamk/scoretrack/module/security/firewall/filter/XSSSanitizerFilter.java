package com.teamk.scoretrack.module.security.firewall.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Xss sanitizer. Does not allow any html tags by default.
 * This filter is meant for general xss protection.
 * If the user input html content should be displayed as an output, consider sanitizing via {@link com.teamk.scoretrack.module.security.firewall.service.XSSOutputEncodingSanitizerService}.
 */
@Component
public class XSSSanitizerFilter extends OncePerRequestFilter {
    private static final PolicyFactory NONE = new HtmlPolicyBuilder().toFactory();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(new FilteredRequest(request), response);
    }

    // TODO: this only sanitizes application/x-www-form-urlencoded. Add sanitization for application/json.
    static class FilteredRequest extends HttpServletRequestWrapper {
        public FilteredRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String paramName) {
            return NONE.sanitize(super.getParameter(paramName));
        }

        @Override
        public String[] getParameterValues(String paramName) {
            return getSanitizedParameterValues(super.getParameterValues(paramName));
        }

        private String[] getSanitizedParameterValues(String[] value) {
            return value != null ? Arrays.stream(value).map(v -> htmlDecode(NONE.sanitize(v))).toArray(String[]::new) : null;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return super.getParameterMap().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> getSanitizedParameterValues(e.getValue())));
        }

        @Override
        public String getQueryString() {
            String queryString = super.getQueryString();
            return queryString != null ? htmlDecode(NONE.sanitize(URLDecoder.decode(queryString, Charset.defaultCharset()))) : null;
        }

        /**
         * Sanitized string is not meant to be displayed in html. Not ideal though
         */
        private String htmlDecode(String queryString) {
            return HtmlUtils.htmlUnescape(queryString);
        }

        /*@Override
        public ServletInputStream getInputStream() {
            throw new UnsupportedOperationException();
        }*/
    }
}
