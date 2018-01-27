import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.ERROR

def TOMCAT_LOG_DIR = System.properties['catalina.base']

appender("EXCEPTION", RollingFileAppender) {
    file = "${TOMCAT_LOG_DIR}/logs/sdxd-app-error.log"
    encoder(PatternLayoutEncoder) {
        pattern = "%date %level [%thread] %logger{10} [%file:%line] %msg%n"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        FileNamePattern = "${TOMCAT_LOG_DIR}/logs/sdxd-error-%d{yyyy-MM-dd}.zip"
    }
}

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%date %level [%thread] %logger{30} [%file:%line] %msg%n"
    }
}

appender("ROLLING", RollingFileAppender) {
    file = "${TOMCAT_LOG_DIR}/logs/sdxd-app-web.log"
    encoder(PatternLayoutEncoder) {
        Pattern = "%date %level [%thread] %logger{30} [%file:%line] %msg%n"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        FileNamePattern = "${TOMCAT_LOG_DIR}/logs/sdxd-app-%d{yyyy-MM-dd}.zip"
    }
}

root(ERROR, ["EXCEPTION"])

//logger("org.springframework", WARN, ["STDOUT"])
logger("springfox.documentation", ERROR, ["STDOUT"], false)
logger("com.sdxd", DEBUG, ["ROLLING"], false)
logger("com.sdxd.common.web.rest.GlobalHandlerExceptionResolver", DEBUG, ["EXCEPTION"])