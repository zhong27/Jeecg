package org.jeecg.modules;

import org.jeecg.common.exception.JeecgBootException;

public class JeecgException  extends JeecgBootException {

        public JeecgException(String message) {
            super(message);
        }

        public JeecgException(Throwable cause) {
            super(cause);
        }

        public JeecgException(String message, Throwable cause) {
            super(message, cause);
        }

}
