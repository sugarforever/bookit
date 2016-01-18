package com.wiysoft.mvc.c;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by weiliyang on 1/18/16.
 */
public class ControllerUtils {

    public static void fillTemplate(HttpServletRequest request, List modules, boolean requireHeader, boolean requireLeading, boolean requireFooter) {
        if (request == null)
            return;

        request.setAttribute("modules", modules);
        request.setAttribute("requireHeader", requireHeader);
        request.setAttribute("requireLeading", requireLeading);
        request.setAttribute("requireFooter", requireFooter);
    }

    public static void fillTemplate(HttpServletRequest request, List modules) {
        fillTemplate(request, modules, true, true, true);
    }
}
