package com.uniroma3.it.gastroguide.constants;

import java.util.HashMap;
import java.util.Map;

public class MailSubjects {
    public static final String STANDARD_ACTIVATE_ACCOUNT_SUBJECT = "[GASTRO GUIDE] ACCOUNT ACTIVATION";
    public static final String STANDARD_RESET_PASSWORD_SUBJECT = "[GASTRO GUIDE] RESET PASSWORD";

    public static final Map<String, String> PATH_TO_MAIL_HTML_TEMPLATE;

    static {
        PATH_TO_MAIL_HTML_TEMPLATE = new HashMap<>();
        PATH_TO_MAIL_HTML_TEMPLATE.put(STANDARD_ACTIVATE_ACCOUNT_SUBJECT, "/templates/mails/activation_email.html");

    }
    public static final Map<String, String> PATH_TO_RESET_MAIL_HTML_TEMPLATE;

    static {
        PATH_TO_RESET_MAIL_HTML_TEMPLATE = new HashMap<>();
        PATH_TO_RESET_MAIL_HTML_TEMPLATE.put(STANDARD_RESET_PASSWORD_SUBJECT, "/templates/mails/password_reset_email.html");
    }
}
